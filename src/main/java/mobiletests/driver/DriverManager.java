package mobiletests.driver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import mobiletests.config.ConfigReader;

import java.io.File;
import java.net.URL;
import java.time.Duration;

public final class DriverManager {

    private static final ThreadLocal<AndroidDriver> DRIVER = new ThreadLocal<>();

    private DriverManager() {
    }

    private static final int SESSION_START_ATTEMPTS = 2;

    public static AndroidDriver startDriver() {
        File appFile = new File(ConfigReader.get("appPath")).getAbsoluteFile();
        if (!appFile.exists()) {
            throw new IllegalStateException("APK not found at " + appFile
                    + ". Download it into the apps/ folder as described in the README.");
        }

        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName(ConfigReader.get("deviceName"))
                .setAutomationName(ConfigReader.get("automationName"))
                .setApp(appFile.getAbsolutePath())
                .setAutoGrantPermissions(true)
                .setNewCommandTimeout(Duration.ofSeconds(120))
                .setAppWaitDuration(Duration.ofSeconds(30))
                .setFullReset(false)
                .setNoReset(false);

        // A cold emulator occasionally misses the app's launch window and reports the
        // activity as "never started" on the first attempt; one retry clears it up.
        Exception lastFailure = null;
        for (int attempt = 1; attempt <= SESSION_START_ATTEMPTS; attempt++) {
            try {
                URL appiumUrl = new URL(ConfigReader.get("appiumServerUrl"));
                AndroidDriver driver = new AndroidDriver(appiumUrl, options);
                DRIVER.set(driver);
                return driver;
            } catch (Exception e) {
                lastFailure = e;
            }
        }
        throw new RuntimeException("Failed to start Appium session. Is the Appium server running at "
                + ConfigReader.get("appiumServerUrl") + "?", lastFailure);
    }

    public static AndroidDriver getDriver() {
        AndroidDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("Driver has not been initialised for this thread. Call startDriver() first.");
        }
        return driver;
    }

    public static void quitDriver() {
        AndroidDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }
}

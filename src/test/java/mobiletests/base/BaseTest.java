package mobiletests.base;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Allure;
import mobiletests.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.ByteArrayInputStream;
import java.time.Duration;

public abstract class BaseTest {

    protected AndroidDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = DriverManager.startDriver();
        dismissAndroidCompatibilityDialogIfPresent();
    }

    // The emulator's arm64 system image flags this APK's native libs as not
    // 16 KB page size aligned and shows an OS-level warning on cold start.
    // It has nothing to do with the app under test, so clear it out of the way.
    private void dismissAndroidCompatibilityDialogIfPresent() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(By.id("android:id/button1")))
                    .click();
        } catch (TimeoutException ignored) {
            // Dialog did not show up for this session, nothing to dismiss.
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE && driver != null) {
            attachScreenshot();
        }
        DriverManager.quitDriver();
    }

    private void attachScreenshot() {
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment("Failure screenshot", new ByteArrayInputStream(screenshot));
    }
}

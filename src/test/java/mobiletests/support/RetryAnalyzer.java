package mobiletests.support;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

// Emulator sessions occasionally hiccup for reasons that have nothing to do with the app under
// test (a slow boot, a transient UiAutomator2 timeout). One retry absorbs that without masking a
// test that's actually broken - a second consecutive failure still fails the build.
public class RetryAnalyzer implements IRetryAnalyzer {

    private static final int MAX_RETRIES = 1;

    private int retryCount = 0;

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < MAX_RETRIES) {
            retryCount++;
            return true;
        }
        return false;
    }
}

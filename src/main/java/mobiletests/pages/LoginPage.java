package mobiletests.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/loginTV")
    private WebElement screenTitle;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/nameET")
    private WebElement usernameField;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/loginBtn")
    private WebElement loginButton;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/passwordErrorTV")
    private WebElement loginErrorMessage;

    @AndroidFindBy(accessibility = "Tap to use this username for login")
    private WebElement firstSuggestedUsername;

    @AndroidFindBy(accessibility = "Visual User Login")
    private WebElement visualUserUsername;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/nameErrorTV")
    private WebElement usernameErrorMessage;

    public LoginPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isDisplayedSafely(waitVisible(screenTitle));
    }

    public LoginPage enterUsername(String username) {
        waitVisible(usernameField).clear();
        usernameField.sendKeys(username);
        return this;
    }

    public CatalogPage submitValidLogin() {
        waitClickable(loginButton).click();
        return new CatalogPage(driver);
    }

    public LoginPage submitInvalidLogin() {
        waitClickable(loginButton).click();
        return this;
    }

    public LoginPage useFirstSuggestedCredentials() {
        waitClickable(firstSuggestedUsername).click();
        return this;
    }

    public LoginPage useLockedOutUserCredentials() {
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().textContains(\"locked out\")")).click();
        return this;
    }

    public String getErrorMessage() {
        return waitVisible(loginErrorMessage).getText();
    }

    public boolean isErrorMessageDisplayed() {
        return isDisplayedSafely(loginErrorMessage);
    }

    public LoginPage useVisualUserCredentials() {
        waitClickable(visualUserUsername).click();
        return this;
    }

    public String getUsernameErrorMessage() {
        return waitVisible(usernameErrorMessage).getText();
    }

    public boolean isUsernameErrorDisplayed() {
        return isDisplayedSafely(usernameErrorMessage);
    }
}

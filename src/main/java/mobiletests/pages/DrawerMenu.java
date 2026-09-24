package mobiletests.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class DrawerMenu extends BasePage {

    @AndroidFindBy(accessibility = "View menu")
    private WebElement menuButton;

    @AndroidFindBy(accessibility = "Login Menu Item")
    private WebElement loginMenuItem;

    @AndroidFindBy(accessibility = "Logout Menu Item")
    private WebElement logoutMenuItem;

    @AndroidFindBy(id = "android:id/button1")
    private WebElement confirmationDialogPositiveButton;

    public DrawerMenu(AndroidDriver driver) {
        super(driver);
    }

    public void open() {
        waitClickable(menuButton).click();
    }

    public LoginPage openLoginScreen() {
        open();
        waitClickable(loginMenuItem).click();
        return new LoginPage(driver);
    }

    // Logging out shows a native "Are you sure you want to logout" dialog;
    // confirming it sends the user straight to the Login screen, not back to the catalog.
    public LoginPage logout() {
        open();
        waitClickable(logoutMenuItem).click();
        waitClickable(confirmationDialogPositiveButton).click();
        return new LoginPage(driver);
    }

    public boolean isUserLoggedIn() {
        open();
        boolean loggedIn = isDisplayedSafely(logoutMenuItem);
        driver.navigate().back();
        return loggedIn;
    }

    public void openMenuItem(String label) {
        open();
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"" + label + "\")")).click();
    }
}

package mobiletests.tests.negative;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import mobiletests.base.BaseTest;
import mobiletests.pages.CatalogPage;
import mobiletests.pages.DrawerMenu;
import mobiletests.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

@Feature("Authentication")
public class LoginNegativeTest extends BaseTest {

    @Test(description = "A locked out user cannot log in and sees an explanatory error", groups = "smoke")
    @Story("Locked out user")
    @Description("Attempts to log in with the demo app's locked-out account and verifies the inline error message")
    public void lockedOutUser_seesErrorMessage() {
        CatalogPage catalog = new CatalogPage(driver);
        DrawerMenu drawer = catalog.openDrawer();
        LoginPage loginPage = drawer.openLoginScreen();

        loginPage.useLockedOutUserCredentials();
        loginPage.submitInvalidLogin();

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Expected an error message for the locked out account");
        Assert.assertEquals(loginPage.getErrorMessage(), "Sorry this user has been locked out.");
    }

    @Test(description = "Submitting the login form with no username shows a required-field error")
    @Story("Empty login form")
    @Description("Taps Login with both fields left blank and verifies the username-required error message")
    public void emptyCredentials_seesUsernameRequiredError() {
        CatalogPage catalog = new CatalogPage(driver);
        DrawerMenu drawer = catalog.openDrawer();
        LoginPage loginPage = drawer.openLoginScreen();

        loginPage.submitInvalidLogin();

        Assert.assertTrue(loginPage.isUsernameErrorDisplayed(), "Expected a username-required error on an empty form");
        Assert.assertEquals(loginPage.getUsernameErrorMessage(), "Username is required");
    }

    @Test(description = "Submitting the login form with a username but no password does not log in")
    @Story("Missing password")
    @Description("Fills in a username, leaves the password blank, and confirms the app does not proceed to the catalog")
    public void missingPassword_staysOnLoginScreen() {
        CatalogPage catalog = new CatalogPage(driver);
        DrawerMenu drawer = catalog.openDrawer();
        LoginPage loginPage = drawer.openLoginScreen();

        loginPage.enterUsername("someone@example.com");
        loginPage.submitInvalidLogin();

        Assert.assertTrue(loginPage.isDisplayed(), "Should remain on the login screen when the password is blank");
    }
}

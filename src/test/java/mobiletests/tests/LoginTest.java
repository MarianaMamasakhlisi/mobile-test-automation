package mobiletests.tests;

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
public class LoginTest extends BaseTest {

    @Test(description = "A user with valid credentials can log in and see the Logout option in the menu")
    @Story("Valid login")
    @Description("Logs in with the demo app's standard user and confirms the drawer menu switches from Log In to Log Out")
    public void validCredentials_logsUserIn() {
        CatalogPage catalog = new CatalogPage(driver);
        DrawerMenu drawer = catalog.openDrawer();
        LoginPage loginPage = drawer.openLoginScreen();

        loginPage.useFirstSuggestedCredentials();
        CatalogPage catalogAfterLogin = loginPage.submitValidLogin();

        Assert.assertTrue(catalogAfterLogin.isDisplayed(), "Expected to land back on the product catalog after login");
        Assert.assertTrue(catalogAfterLogin.openDrawer().isUserLoggedIn(),
                "Drawer menu should show 'Log Out' once the user is authenticated");
    }

    @Test(description = "A locked out user cannot log in and sees an explanatory error")
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
}

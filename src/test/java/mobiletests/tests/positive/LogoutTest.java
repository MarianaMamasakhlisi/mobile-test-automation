package mobiletests.tests.positive;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import mobiletests.base.BaseTest;
import mobiletests.pages.CatalogPage;
import mobiletests.pages.DrawerMenu;
import mobiletests.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

@Feature("Authentication")
public class LogoutTest extends BaseTest {

    @Test(description = "A logged in user can log out after confirming the prompt, and lands back on the login screen")
    @Description("Logs in, then logs out again from the drawer menu, confirms the native logout prompt, and checks the session was cleared")
    public void loggedInUser_canLogOut() {
        CatalogPage catalog = new CatalogPage(driver);
        LoginPage loginPage = catalog.openDrawer().openLoginScreen();
        loginPage.useFirstSuggestedCredentials();
        CatalogPage loggedInCatalog = loginPage.submitValidLogin();

        DrawerMenu drawer = loggedInCatalog.openDrawer();
        LoginPage loginPageAfterLogout = drawer.logout();

        Assert.assertTrue(loginPageAfterLogout.isDisplayed(),
                "Confirming logout should return the user to the login screen");
    }

    @Test(description = "Cancelling the logout confirmation keeps the user logged in")
    @Description("Logs in, starts logging out, then cancels the native prompt and confirms the session is untouched")
    public void cancellingLogoutPrompt_keepsUserLoggedIn() {
        CatalogPage catalog = new CatalogPage(driver);
        LoginPage loginPage = catalog.openDrawer().openLoginScreen();
        loginPage.useFirstSuggestedCredentials();
        CatalogPage loggedInCatalog = loginPage.submitValidLogin();

        DrawerMenu drawer = loggedInCatalog.openDrawer();
        CatalogPage catalogAfterCancel = drawer.cancelLogout();

        Assert.assertTrue(catalogAfterCancel.isDisplayed(), "Cancelling logout should return to the catalog");
        Assert.assertTrue(catalogAfterCancel.openDrawer().isUserLoggedIn(),
                "Drawer menu should still show 'Log Out' after cancelling the logout prompt");
    }
}

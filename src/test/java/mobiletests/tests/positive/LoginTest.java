package mobiletests.tests.positive;

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

    @Test(description = "A user with valid credentials can log in and see the Logout option in the menu",
            groups = "smoke")
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

    @Test(description = "The visual-testing demo account can also log in successfully")
    @Story("Visual user login")
    @Description("Logs in with the demo app's visual-testing account, used elsewhere to demo visual-regression bugs")
    public void visualUserCredentials_logsUserIn() {
        CatalogPage catalog = new CatalogPage(driver);
        DrawerMenu drawer = catalog.openDrawer();
        LoginPage loginPage = drawer.openLoginScreen();

        loginPage.useVisualUserCredentials();
        CatalogPage catalogAfterLogin = loginPage.submitValidLogin();

        Assert.assertTrue(catalogAfterLogin.isDisplayed(), "Expected to land back on the product catalog after login");
        Assert.assertTrue(catalogAfterLogin.openDrawer().isUserLoggedIn(),
                "Drawer menu should show 'Log Out' once the visual user is authenticated");
    }
}

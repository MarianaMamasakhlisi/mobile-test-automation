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

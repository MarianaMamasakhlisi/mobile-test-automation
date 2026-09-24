package mobiletests.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import mobiletests.base.BaseTest;
import mobiletests.pages.CartPage;
import mobiletests.pages.CatalogPage;
import mobiletests.pages.CheckoutInfoPage;
import mobiletests.pages.LoginPage;
import mobiletests.pages.ProductDetailsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

@Feature("Checkout")
public class CheckoutValidationTest extends BaseTest {

    @Test(description = "Submitting the shipping form with the City field empty shows an inline validation message")
    @Description("Clears the required City field on the checkout form and confirms the app blocks submission with an explanatory message")
    public void missingCity_showsValidationMessage() {
        CheckoutInfoPage checkoutInfo = reachCheckoutInfoScreen();

        checkoutInfo.clearCity();
        checkoutInfo.submitCheckoutInfo();

        Assert.assertTrue(checkoutInfo.isDisplayed(),
                "Form should not submit while a required field (City) is empty");
        Assert.assertEquals(checkoutInfo.getCityErrorMessage(), "Please provide your city.");
    }

    @Test(description = "Submitting the shipping form with the Full Name field empty shows an inline validation message")
    @Description("Clears the required Full Name field on the checkout form and confirms the app blocks submission with an explanatory message")
    public void missingFullName_showsValidationMessage() {
        CheckoutInfoPage checkoutInfo = reachCheckoutInfoScreen();

        checkoutInfo.clearFullName();
        checkoutInfo.submitCheckoutInfo();

        Assert.assertTrue(checkoutInfo.isDisplayed(),
                "Form should not submit while a required field (Full Name) is empty");
        Assert.assertEquals(checkoutInfo.getFullNameErrorMessage(), "Please provide your full name.");
    }

    // Checkout requires an authenticated session: adding items and tapping "Proceed to
    // Checkout" while logged out routes the user to the Login screen instead of the form.
    private CheckoutInfoPage reachCheckoutInfoScreen() {
        CatalogPage catalog = new CatalogPage(driver);
        LoginPage loginPage = catalog.openDrawer().openLoginScreen();
        loginPage.useFirstSuggestedCredentials();
        CatalogPage loggedInCatalog = loginPage.submitValidLogin();

        ProductDetailsPage details = loggedInCatalog.openFirstProduct();
        details.addToCart();

        CartPage cart = new CatalogPage(driver).openCart();
        return cart.proceedToCheckout();
    }
}

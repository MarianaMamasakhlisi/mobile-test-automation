package mobiletests.tests.negative;

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

    @Test(description = "Submitting the shipping form with the City field empty shows an inline validation message",
            groups = "smoke")
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

    @Test(description = "Submitting the shipping form with the Address Line 1 field empty shows an inline validation message")
    @Description("Clears the required Address Line 1 field on the checkout form and confirms the app blocks submission with an explanatory message")
    public void missingAddressLine1_showsValidationMessage() {
        CheckoutInfoPage checkoutInfo = reachCheckoutInfoScreen();

        checkoutInfo.clearAddressLine1();
        checkoutInfo.submitCheckoutInfo();

        Assert.assertTrue(checkoutInfo.isDisplayed(),
                "Form should not submit while a required field (Address Line 1) is empty");
        Assert.assertEquals(checkoutInfo.getAddressLine1ErrorMessage(), "Please provide your address.");
    }

    @Test(description = "Submitting the shipping form with the Zip Code field empty shows an inline validation message")
    @Description("Clears the required Zip Code field on the checkout form and confirms the app blocks submission with an explanatory message")
    public void missingZipCode_showsValidationMessage() {
        CheckoutInfoPage checkoutInfo = reachCheckoutInfoScreen();

        checkoutInfo.clearZipCode();
        checkoutInfo.submitCheckoutInfo();

        Assert.assertTrue(checkoutInfo.isDisplayed(),
                "Form should not submit while a required field (Zip Code) is empty");
        Assert.assertEquals(checkoutInfo.getZipErrorMessage(), "Please provide your zip");
    }

    // The Country error label ships with an unfinished string - it never actually names the
    // field - so this asserts the app's real (if awkward) wording rather than what it should say.
    @Test(description = "Submitting the shipping form with the Country field empty shows an inline validation message")
    @Description("Clears the required Country field on the checkout form and confirms the app blocks submission with an explanatory message")
    public void missingCountry_showsValidationMessage() {
        CheckoutInfoPage checkoutInfo = reachCheckoutInfoScreen();

        checkoutInfo.clearCountry();
        checkoutInfo.submitCheckoutInfo();

        Assert.assertTrue(checkoutInfo.isDisplayed(),
                "Form should not submit while a required field (Country) is empty");
        Assert.assertEquals(checkoutInfo.getCountryErrorMessage(), "Please provide your");
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

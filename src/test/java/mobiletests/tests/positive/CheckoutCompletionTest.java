package mobiletests.tests.positive;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import mobiletests.base.BaseTest;
import mobiletests.pages.CartPage;
import mobiletests.pages.CatalogPage;
import mobiletests.pages.CheckoutInfoPage;
import mobiletests.pages.LoginPage;
import mobiletests.pages.OrderCompletePage;
import mobiletests.pages.PaymentInfoPage;
import mobiletests.pages.ProductDetailsPage;
import mobiletests.pages.ReviewOrderPage;
import org.testng.Assert;
import org.testng.annotations.Test;

@Feature("Checkout")
public class CheckoutCompletionTest extends BaseTest {

    @Test(description = "A logged in user can complete checkout end to end", groups = "smoke")
    @Description("Walks through shipping info, payment info and order review to confirm a purchase, then checks the cart is cleared")
    public void loggedInUser_canCompleteCheckout() {
        CatalogPage catalog = new CatalogPage(driver);
        LoginPage loginPage = catalog.openDrawer().openLoginScreen();
        loginPage.useFirstSuggestedCredentials();
        CatalogPage loggedInCatalog = loginPage.submitValidLogin();

        ProductDetailsPage details = loggedInCatalog.openFirstProduct();
        details.addToCart();

        CartPage cart = new CatalogPage(driver).openCart();
        CheckoutInfoPage checkoutInfo = cart.proceedToCheckout();

        PaymentInfoPage paymentInfo = checkoutInfo.submitValidCheckoutInfo();
        Assert.assertTrue(paymentInfo.isDisplayed(), "Expected to reach the payment info screen");

        ReviewOrderPage reviewOrder = paymentInfo.submitValidPaymentInfo();
        Assert.assertTrue(reviewOrder.isDisplayed(), "Expected to reach the order review screen");

        OrderCompletePage orderComplete = reviewOrder.placeOrder();

        Assert.assertTrue(orderComplete.isDisplayed(), "Expected to reach the order confirmation screen");
        Assert.assertEquals(orderComplete.getCompleteTitle(), "Checkout Complete");

        CatalogPage catalogAfterOrder = orderComplete.continueShopping();
        Assert.assertEquals(catalogAfterOrder.getCartItemCount(), 0,
                "Cart should be empty after completing an order");
    }
}

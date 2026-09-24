package mobiletests.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import mobiletests.base.BaseTest;
import mobiletests.pages.CartPage;
import mobiletests.pages.CatalogPage;
import mobiletests.pages.ProductDetailsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

@Feature("Shopping cart")
public class CartTest extends BaseTest {

    @Test(description = "Adding a product to the cart updates the header badge and the cart contents")
    @Description("Adds a product from the details screen and verifies it shows up in the cart with the right item count")
    public void addingProduct_updatesCartBadgeAndContents() {
        CatalogPage catalog = new CatalogPage(driver);
        Assert.assertEquals(catalog.getCartItemCount(), 0, "Cart should start empty");

        ProductDetailsPage details = catalog.openFirstProduct();
        String addedProduct = details.getProductTitle();
        details.addToCart();

        CatalogPage catalogAfterAdd = new CatalogPage(driver);
        Assert.assertEquals(catalogAfterAdd.getCartItemCount(), 1, "Cart badge should reflect one item after adding a product");

        CartPage cart = catalogAfterAdd.openCart();
        Assert.assertEquals(cart.getItemsCountLabel(), "1 Items");
        Assert.assertFalse(cart.isEmpty(), "Cart page should list the product that was just added: " + addedProduct);
    }

    @Test(description = "Increasing the quantity before adding to cart is reflected in the cart total")
    @Description("Bumps the quantity selector on the product details screen and confirms the cart reflects two items")
    public void increasingQuantity_addsMultipleUnitsToCart() {
        CatalogPage catalog = new CatalogPage(driver);
        ProductDetailsPage details = catalog.openFirstProduct();

        details.increaseQuantity(1);
        Assert.assertEquals(details.getQuantity(), 2, "Quantity selector should increase to 2");

        details.addToCart();

        CatalogPage catalogAfterAdd = new CatalogPage(driver);
        Assert.assertEquals(catalogAfterAdd.getCartItemCount(), 2, "Cart badge should reflect both units of the product");
    }

    @Test(description = "Removing the only item from the cart leaves it empty")
    @Description("Adds a product, removes it again from the cart screen, and confirms nothing is left behind")
    public void removingOnlyItem_leavesCartEmpty() {
        CatalogPage catalog = new CatalogPage(driver);
        ProductDetailsPage details = catalog.openFirstProduct();
        details.addToCart();

        CartPage cart = new CatalogPage(driver).openCart();
        cart.removeFirstItem();

        Assert.assertTrue(cart.isEmpty(), "Cart should be empty after removing its only item");
    }
}

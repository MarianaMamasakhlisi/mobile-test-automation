package mobiletests.tests.positive;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import mobiletests.base.BaseTest;
import mobiletests.pages.CartPage;
import mobiletests.pages.CatalogPage;
import mobiletests.pages.ProductDetailsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

@Feature("Shopping cart")
public class CartTest extends BaseTest {

    @Test(description = "Adding a product to the cart updates the header badge and the cart contents", groups = "smoke")
    @Description("Adds a product from the details screen and verifies the cart lists that exact product")
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
        Assert.assertEquals(cart.getLineItemTitles(), List.of(addedProduct),
                "Cart should list exactly the product that was added, not a different or empty one");
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

    // Picking a different color swatch for a product that's already been added doesn't create a
    // second cart line - it just bumps the quantity of the existing one.
    @Test(description = "Re-adding the same product in a different color updates its quantity, not a new line")
    @Description("Adds a product, picks a different color swatch, adds again, and confirms the cart still has one line")
    public void reAddingWithDifferentColor_updatesQuantityInsteadOfDuplicating() {
        CatalogPage catalog = new CatalogPage(driver);
        ProductDetailsPage details = catalog.openFirstProduct();
        Assert.assertTrue(details.getColorOptionCount() >= 2, "Expected at least two color options to choose between");

        details.selectColor(0);
        details.addToCart();
        details.selectColor(1);
        details.addToCart();

        CatalogPage catalogAfterAdd = new CatalogPage(driver);
        catalogAfterAdd.waitForCartItemCount(2);

        CartPage cart = catalogAfterAdd.openCart();
        Assert.assertEquals(cart.getLineItemCount(), 1,
                "Adding the same product again in a different color should update the existing line, not add a new one");
    }
}

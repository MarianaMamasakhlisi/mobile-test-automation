package mobiletests.tests.negative;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import mobiletests.base.BaseTest;
import mobiletests.pages.CatalogPage;
import mobiletests.pages.ProductDetailsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

@Feature("Shopping cart")
public class CartNegativeTest extends BaseTest {

    @Test(description = "The quantity selector cannot be decreased below zero")
    @Description("Decreases the quantity selector past its minimum and confirms it clamps at 0 instead of going negative")
    public void decreasingQuantity_clampsAtZero() {
        CatalogPage catalog = new CatalogPage(driver);
        ProductDetailsPage details = catalog.openFirstProduct();

        details.decreaseQuantity(3);

        Assert.assertEquals(details.getQuantity(), 0, "Quantity should clamp at 0, not go negative");
    }

    @Test(description = "The Add to Cart button is disabled when quantity is zero")
    @Description("Decreases quantity to 0 and confirms Add to Cart becomes disabled, preventing an empty add")
    public void zeroQuantity_disablesAddToCartButton() {
        CatalogPage catalog = new CatalogPage(driver);
        ProductDetailsPage details = catalog.openFirstProduct();

        details.decreaseQuantity(1);

        Assert.assertFalse(details.isAddToCartButtonEnabled(),
                "Add to Cart should be disabled once quantity reaches 0");
    }
}

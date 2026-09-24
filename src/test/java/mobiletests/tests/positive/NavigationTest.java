package mobiletests.tests.positive;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import mobiletests.base.BaseTest;
import mobiletests.pages.CartPage;
import mobiletests.pages.CatalogPage;
import mobiletests.pages.ProductDetailsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

@Feature("Navigation")
public class NavigationTest extends BaseTest {

    @Test(description = "Selecting a product from the catalog opens its details screen")
    @Description("Confirms the product details screen shows the same product that was tapped in the catalog")
    public void catalog_navigatesToProductDetails() {
        CatalogPage catalog = new CatalogPage(driver);
        Assert.assertTrue(catalog.isDisplayed(), "Catalog screen should be shown on app launch");
        Assert.assertTrue(catalog.getProductCount() > 0, "Catalog should list at least one product");

        ProductDetailsPage details = catalog.openFirstProduct();

        Assert.assertEquals(details.getProductTitle(), "Sauce Labs Backpack");
    }

    @Test(description = "The cart icon in the header navigates to the cart screen")
    @Description("Confirms the header cart shortcut is reachable from the catalog screen")
    public void catalog_navigatesToCart() {
        CatalogPage catalog = new CatalogPage(driver);
        CartPage cart = catalog.openCart();

        Assert.assertTrue(cart.isDisplayed(), "Expected to land on the cart screen after tapping the cart icon");
    }
}

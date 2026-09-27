package mobiletests.tests.positive;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import mobiletests.base.BaseTest;
import mobiletests.pages.CatalogPage;
import mobiletests.pages.ProductDetailsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

@Feature("Drawer menu")
public class ResetAppStateTest extends BaseTest {

    @Test(description = "Reset App State clears the cart")
    @Description("Adds a product to the cart, resets app state from the drawer menu, and confirms the cart is emptied")
    public void resetAppState_clearsCart() {
        CatalogPage catalog = new CatalogPage(driver);
        ProductDetailsPage details = catalog.openFirstProduct();
        details.addToCart();

        // The header (with the cart badge and drawer menu) is shared across screens, so it can
        // be read and driven from here without first navigating back to the catalog fragment.
        CatalogPage catalogWithItem = new CatalogPage(driver);
        catalogWithItem.waitForCartItemCount(1);

        catalogWithItem.openDrawer().openMenuItem("Reset App State");

        catalogWithItem.waitForCartItemCount(0);
    }
}

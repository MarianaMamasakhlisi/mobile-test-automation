package mobiletests.tests.positive;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import mobiletests.base.BaseTest;
import mobiletests.pages.CatalogPage;
import org.testng.Assert;
import org.testng.annotations.Test;

@Feature("Catalog sorting")
public class CatalogSortTest extends BaseTest {

    @Test(description = "Sorting by name descending changes the first product shown")
    @Description("Applies the Name - Descending sort option and confirms the catalog re-orders")
    public void sortByNameDescending_changesFirstProduct() {
        CatalogPage catalog = new CatalogPage(driver);
        String defaultFirstProduct = catalog.getFirstProductName();

        catalog.sortByNameDescending();

        Assert.assertNotEquals(catalog.getFirstProductName(), defaultFirstProduct,
                "Sorting by name descending should change which product appears first");
    }

    @Test(description = "Sorting by price ascending shows the cheapest product first")
    @Description("Applies the Price - Ascending sort option and confirms the cheapest item is listed first")
    public void sortByPriceAscending_showsCheapestFirst() {
        CatalogPage catalog = new CatalogPage(driver);

        catalog.sortByPriceAscending();

        Assert.assertEquals(catalog.getFirstProductName(), "Sauce Labs Onesie",
                "The cheapest product ($7.99) should be listed first after sorting by price ascending");
    }
}

package mobiletests.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CatalogPage extends BasePage {

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/productTV")
    private WebElement screenTitle;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/titleTV")
    private List<WebElement> productTitles;

    @AndroidFindBy(accessibility = "Product Image")
    private List<WebElement> productImages;

    @AndroidFindBy(accessibility = "View cart")
    private WebElement cartButton;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/cartTV")
    private WebElement cartBadgeCount;

    @AndroidFindBy(accessibility = "Shows current sorting order and displays available sorting options")
    private WebElement sortButton;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/nameDesCL")
    private WebElement sortNameDescendingOption;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/priceAscCL")
    private WebElement sortPriceAscendingOption;

    public CatalogPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isDisplayedSafely(waitVisible(screenTitle));
    }

    public String getScreenTitle() {
        return waitVisible(screenTitle).getText();
    }

    // Tapping the title label directly doesn't trigger navigation in this app;
    // only the product image inside the same card is wired up to open the details screen.
    public ProductDetailsPage openFirstProduct() {
        return openProductAt(0);
    }

    public ProductDetailsPage openProductAt(int index) {
        waitVisible(productTitles.get(index));
        waitClickable(productImages.get(index)).click();
        return new ProductDetailsPage(driver);
    }

    public int getProductCount() {
        return productTitles.size();
    }

    public CartPage openCart() {
        waitClickable(cartButton).click();
        return new CartPage(driver);
    }

    public int getCartItemCount() {
        if (!isDisplayedSafely(cartBadgeCount)) {
            return 0;
        }
        return Integer.parseInt(cartBadgeCount.getText());
    }

    // The badge updates asynchronously after actions like adding an item or resetting app
    // state, so checking right after such an action needs to poll rather than read once.
    public void waitForCartItemCount(int expectedCount) {
        wait.until(driver -> getCartItemCount() == expectedCount);
    }

    public DrawerMenu openDrawer() {
        return new DrawerMenu(driver);
    }

    public String getFirstProductName() {
        return waitVisible(productTitles.get(0)).getText();
    }

    public CatalogPage sortByNameDescending() {
        waitClickable(sortButton).click();
        waitClickable(sortNameDescendingOption).click();
        return this;
    }

    public CatalogPage sortByPriceAscending() {
        waitClickable(sortButton).click();
        waitClickable(sortPriceAscendingOption).click();
        return this;
    }
}

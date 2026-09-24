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
    public ProductDetailsPage openProduct(String productName) {
        for (int i = 0; i < productTitles.size(); i++) {
            if (productTitles.get(i).getText().equals(productName)) {
                waitClickable(productImages.get(i)).click();
                return new ProductDetailsPage(driver);
            }
        }
        throw new IllegalArgumentException("Product not found in catalog: " + productName);
    }

    public ProductDetailsPage openFirstProduct() {
        waitVisible(productTitles.get(0));
        waitClickable(productImages.get(0)).click();
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

    public DrawerMenu openDrawer() {
        return new DrawerMenu(driver);
    }
}

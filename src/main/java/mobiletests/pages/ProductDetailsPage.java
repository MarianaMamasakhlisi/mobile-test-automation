package mobiletests.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class ProductDetailsPage extends BasePage {

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/productTV")
    private WebElement productTitle;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/priceTV")
    private WebElement productPrice;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/plusIV")
    private WebElement increaseQuantityButton;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/noTV")
    private WebElement quantityValue;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/cartBt")
    private WebElement addToCartButton;

    public ProductDetailsPage(AndroidDriver driver) {
        super(driver);
    }

    public String getProductTitle() {
        return waitVisible(productTitle).getText();
    }

    public String getProductPrice() {
        return waitVisible(productPrice).getText();
    }

    public int getQuantity() {
        return Integer.parseInt(waitVisible(quantityValue).getText());
    }

    public ProductDetailsPage increaseQuantity(int times) {
        for (int i = 0; i < times; i++) {
            waitClickable(increaseQuantityButton).click();
        }
        return this;
    }

    public ProductDetailsPage addToCart() {
        waitClickable(addToCartButton).click();
        return this;
    }
}

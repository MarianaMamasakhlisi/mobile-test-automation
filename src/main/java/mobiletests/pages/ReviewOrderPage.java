package mobiletests.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class ReviewOrderPage extends BasePage {

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/placeOrderRV")
    private WebElement orderItemsList;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/paymentBtn")
    private WebElement placeOrderButton;

    public ReviewOrderPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isDisplayedSafely(waitVisible(orderItemsList));
    }

    public OrderCompletePage placeOrder() {
        waitClickable(placeOrderButton).click();
        return new OrderCompletePage(driver);
    }
}

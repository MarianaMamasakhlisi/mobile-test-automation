package mobiletests.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePage {

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/productTV")
    private WebElement screenTitle;

    // The app renders a completely different layout when the cart has no items,
    // so "displayed" has to recognise either state.
    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/noItemTitleTV")
    private WebElement emptyCartMessage;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/itemsTV")
    private WebElement itemsCountLabel;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/cartBt")
    private WebElement proceedToCheckoutButton;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/removeBt")
    private List<WebElement> removeItemButtons;

    public CartPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        wait.until(d -> isDisplayedSafely(screenTitle) || isDisplayedSafely(emptyCartMessage));
        return true;
    }

    public String getItemsCountLabel() {
        return waitVisible(itemsCountLabel).getText();
    }

    public CheckoutInfoPage proceedToCheckout() {
        waitClickable(proceedToCheckoutButton).click();
        return new CheckoutInfoPage(driver);
    }

    public void removeFirstItem() {
        waitClickable(removeItemButtons.get(0)).click();
    }

    public boolean isEmpty() {
        return removeItemButtons.isEmpty() || isDisplayedSafely(emptyCartMessage);
    }
}

package mobiletests.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class PaymentInfoPage extends BasePage {

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/enterPaymentTitleTV")
    private WebElement screenTitle;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/nameET")
    private WebElement cardholderNameField;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/cardNumberET")
    private WebElement cardNumberField;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/expirationDateET")
    private WebElement expirationDateField;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/securityCodeET")
    private WebElement securityCodeField;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/paymentBtn")
    private WebElement reviewOrderButton;

    public PaymentInfoPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isDisplayedSafely(waitVisible(screenTitle));
    }

    // Same quirk as the shipping form: the pre-filled card details need to be retyped
    // before the form will accept them.
    public ReviewOrderPage submitValidPaymentInfo() {
        retype(waitVisible(cardholderNameField));
        retype(waitVisible(cardNumberField));
        retype(waitVisible(expirationDateField));
        retype(waitVisible(securityCodeField));
        waitClickable(reviewOrderButton).click();
        return new ReviewOrderPage(driver);
    }
}

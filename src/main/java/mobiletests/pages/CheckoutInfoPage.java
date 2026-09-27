package mobiletests.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class CheckoutInfoPage extends BasePage {

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/checkoutTitleTV")
    private WebElement screenTitle;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/fullNameET")
    private WebElement fullNameField;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/address1ET")
    private WebElement addressLine1Field;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/cityET")
    private WebElement cityField;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/cityErrorTV")
    private WebElement cityErrorMessage;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/fullNameErrorTV")
    private WebElement fullNameErrorMessage;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/address1ErrorTV")
    private WebElement address1ErrorMessage;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/zipET")
    private WebElement zipCodeField;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/zipErrorTV")
    private WebElement zipErrorMessage;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/countryET")
    private WebElement countryField;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/countryErrorTV")
    private WebElement countryErrorMessage;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/paymentBtn")
    private WebElement toPaymentButton;

    public CheckoutInfoPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isDisplayedSafely(waitVisible(screenTitle));
    }

    public CheckoutInfoPage clearCity() {
        waitVisible(cityField).clear();
        return this;
    }

    public CheckoutInfoPage clearFullName() {
        waitVisible(fullNameField).clear();
        return this;
    }

    public CheckoutInfoPage clearAddressLine1() {
        waitVisible(addressLine1Field).clear();
        return this;
    }

    public CheckoutInfoPage clearZipCode() {
        waitVisible(zipCodeField).clear();
        return this;
    }

    public CheckoutInfoPage clearCountry() {
        waitVisible(countryField).clear();
        return this;
    }

    public void submitCheckoutInfo() {
        waitClickable(toPaymentButton).click();
    }

    // Every field arrives pre-filled with valid sample data, but none of it is recognised by the
    // form's own validation until it's actually been typed - retype each one before submitting.
    public PaymentInfoPage submitValidCheckoutInfo() {
        retype(waitVisible(fullNameField));
        retype(waitVisible(addressLine1Field));
        retype(waitVisible(cityField));
        retype(waitVisible(zipCodeField));
        retype(waitVisible(countryField));
        waitClickable(toPaymentButton).click();
        return new PaymentInfoPage(driver);
    }

    public String getCityErrorMessage() {
        return waitVisible(cityErrorMessage).getText();
    }

    public String getFullNameErrorMessage() {
        return waitVisible(fullNameErrorMessage).getText();
    }

    public String getAddressLine1ErrorMessage() {
        return waitVisible(address1ErrorMessage).getText();
    }

    public String getZipErrorMessage() {
        return waitVisible(zipErrorMessage).getText();
    }

    public String getCountryErrorMessage() {
        return waitVisible(countryErrorMessage).getText();
    }
}

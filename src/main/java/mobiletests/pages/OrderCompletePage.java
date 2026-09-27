package mobiletests.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class OrderCompletePage extends BasePage {

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/completeTV")
    private WebElement completeTitle;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/shoopingBt")
    private WebElement continueShoppingButton;

    public OrderCompletePage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isDisplayedSafely(waitVisible(completeTitle));
    }

    public String getCompleteTitle() {
        return waitVisible(completeTitle).getText();
    }

    public CatalogPage continueShopping() {
        waitClickable(continueShoppingButton).click();
        return new CatalogPage(driver);
    }
}

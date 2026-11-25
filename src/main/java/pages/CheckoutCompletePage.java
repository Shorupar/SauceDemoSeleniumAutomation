
package pages;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutCompletePage extends BaseClass {

    private final By confirmationHeader =
            By.className("complete-header");

    private final By confirmationMessage =
            By.className("complete-text");

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    public String getConfirmationHeader() {
        return getText(confirmationHeader);
    }

    public String getConfirmationMessage() {
        return getText(confirmationMessage);
    }

    public boolean isOrderConfirmed() {
        return isDisplayed(confirmationHeader);
    }
}

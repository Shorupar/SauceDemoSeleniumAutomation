package pages;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutOverviewPage extends BaseClass {

    private final By finishButton = By.id("finish");
    private final By cartItems = By.className("cart_item");
    private final By summaryTotal = By.className("summary_total_label");

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
    }

    public int getOverviewItemCount() {
        return driver.findElements(cartItems).size();
    }

    public boolean waitForOverviewItemCount(int expected) {
        try {
            return wait.until(
                    d -> getOverviewItemCount() == expected
            );
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public String getOrderTotal() {
        return getText(summaryTotal);
    }

    public void clickFinish() {
        click(finishButton);
        sleep(1000);
    }
}

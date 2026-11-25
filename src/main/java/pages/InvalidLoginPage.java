package pages;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InvalidLoginPage extends BaseClass {

    private final By errorMessage =
            By.cssSelector("[data-test='error']");

    private final By usernameField =
            By.id("user-name");

    public InvalidLoginPage(WebDriver driver) {
        super(driver);
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessage);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isStillOnLoginPage() {
        return isDisplayed(usernameField)
                && !getCurrentUrl().contains("inventory.html");
    }
}

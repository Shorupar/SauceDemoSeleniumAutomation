package pages;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BaseClass {

    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoginPageDisplayed() {
        return isDisplayed(usernameField)
                && isDisplayed(passwordField)
                && isDisplayed(loginButton);
    }

    public void enterUsername(String username) {
        type(usernameField, username);
        sleep(1000);
    }

    public void enterPassword(String password) {
        type(passwordField, password);
        sleep(1000);
    }

    public void clickLogin() {
        click(loginButton);
        sleep(1000);
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorMessage);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }
}

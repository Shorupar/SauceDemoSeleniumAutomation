package pages;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutInformationPage extends BaseClass {

    private final By firstNameField = By.id("first-name");
    private final By lastNameField = By.id("last-name");
    private final By postalCodeField = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public CheckoutInformationPage(WebDriver driver) {
        super(driver);
    }

    public void fillCheckoutInformation(
            String firstName,
            String lastName,
            String postalCode) {

        type(firstNameField, firstName);
        sleep(1000);

        type(lastNameField, lastName);
        sleep(1000);

        type(postalCodeField, postalCode);
        sleep(1000);
    }

    public void clickContinue() {
        click(continueButton);
        sleep(1000);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }
}

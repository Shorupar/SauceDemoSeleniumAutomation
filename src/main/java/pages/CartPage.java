package pages;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends BaseClass {

    private final By cartItems = By.className("cart_item");
    private final By itemNames = By.className("inventory_item_name");
    private final By checkoutButton = By.id("checkout");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public int getCartProductCount() {
        return driver.findElements(cartItems).size();
    }

    public boolean waitForCartProductCount(int expected) {
        try {
            return wait.until(d -> getCartProductCount() == expected);
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public List<String> getCartProductNames() {
        List<String> names = new ArrayList<>();

        for (WebElement element : driver.findElements(itemNames)) {
            names.add(element.getText().trim());
        }

        return names;
    }

    public boolean isProductInCart(String productName) {
        return getCartProductNames().stream()
                .anyMatch(n -> n.equalsIgnoreCase(productName));
    }

    public void clickCheckout() {
        click(checkoutButton);
        sleep(1000);
    }
}

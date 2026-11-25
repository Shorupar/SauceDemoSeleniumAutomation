package pages;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InventoryPage extends BaseClass {

    private final By inventoryList = By.className("inventory_list");
    private final By inventoryItem = By.className("inventory_item");
    private final By sortDropdown = By.className("product_sort_container");
    private final By productNames = By.className("inventory_item_name");
    private final By productPrices = By.className("inventory_item_price");
    private final By cartBadge = By.className("shopping_cart_badge");
    private final By cartIcon = By.className("shopping_cart_link");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public boolean isInventoryDisplayed() {
        return isDisplayed(inventoryList);
    }

    public void sortByNameDescending() {
        selectSort("za");
    }

    public void sortByPriceLowToHigh() {
        selectSort("lohi");
    }

    private void selectSort(String value) {

        WebElement dropdown = find(sortDropdown);

        new Select(dropdown).selectByValue(value);

        sleep(500);

        wait.until(d -> value.equals(
                new Select(d.findElement(sortDropdown))
                        .getFirstSelectedOption()
                        .getAttribute("value")));

        if ("za".equals(value)) {
            wait.until(d -> areNamesDescending());
        } else if ("lohi".equals(value)) {
            wait.until(d -> arePricesAscending());
        }

        sleep(1000);
    }

    public List<String> getProductNames() {

        List<String> names = new ArrayList<>();

        for (WebElement element : findAll(productNames)) {
            names.add(element.getText().trim());
        }

        return names;
    }

    public List<Double> getProductPrices() {

        List<Double> prices = new ArrayList<>();

        for (WebElement element : findAll(productPrices)) {

            prices.add(Double.parseDouble(
                    element.getText().replace("$", "").trim()));
        }

        return prices;
    }

    public boolean areNamesDescending() {

        List<String> actual = getProductNames();

        List<String> expected = new ArrayList<>(actual);

        expected.sort(Collections.reverseOrder(
                String.CASE_INSENSITIVE_ORDER));

        return !actual.isEmpty() && actual.equals(expected);
    }

    public boolean arePricesAscending() {

        List<Double> actual = getProductPrices();

        List<Double> expected = new ArrayList<>(actual);

        Collections.sort(expected);

        return !actual.isEmpty() && actual.equals(expected);
    }

    public void addProductToCart(String productName) {

        for (WebElement product : findAll(inventoryItem)) {

            String name = product.findElement(productNames)
                    .getText().trim();

            if (name.equalsIgnoreCase(productName)) {

                new Actions(driver)
                        .moveToElement(product)
                        .perform();

                sleep(1000);

                By addButton = By.cssSelector("button.btn_inventory");

                // Locate button inside the selected product
                WebElement button = product.findElement(addButton);

                wait.until(ExpectedConditions.elementToBeClickable(button));

                button.click();

                sleep(1000);

                // Verify button changed to Remove
                wait.until(d -> {

                    List<WebElement> buttons =
                            product.findElements(addButton);

                    return !buttons.isEmpty()
                            && buttons.get(0).getText()
                            .trim().equalsIgnoreCase("Remove");
                });

                return;
            }
        }

        throw new IllegalArgumentException(
                "Product not found on inventory page: " + productName);
    }

    public String getCartItemCount() {

        List<WebElement> badges = driver.findElements(cartBadge);

        return badges.isEmpty()
                ? "0"
                : badges.get(0).getText().trim();
    }

    public boolean waitForCartCount(String expected) {

        try {

            boolean result = wait.until(
                    d -> expected.equals(getCartItemCount()));

            sleep(1000);

            return result;

        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public void openCart() {
        click(cartIcon);
    }
}

package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;

import java.util.List;

public class ProductSortingTests extends BaseTest {

    @Test(description = "Sort products by name Z-A and then price low-high")
    public void productSortingTest() {

        // Login with valid credentials
        new LoginPage(driver).login(VALID_USER, PASSWORD);

        InventoryPage inventory = new InventoryPage(driver);

        // Verify inventory page
        Assert.assertTrue(
                inventory.waitForUrlContains("inventory.html"),
                "Inventory page did not open."
        );

        Assert.assertTrue(
                inventory.isInventoryDisplayed(),
                "Inventory list is not visible."
        );

        // Sort products by name Z-A
        inventory.sortByNameDescending();

        List<String> names = inventory.getProductNames();

        Assert.assertTrue(
                inventory.areNamesDescending(),
                "Expected Z-A product names. Actual: " + names
        );

        // Sort products by price low-high
        inventory.sortByPriceLowToHigh();

        List<Double> prices = inventory.getProductPrices();

        Assert.assertTrue(
                inventory.arePricesAscending(),
                "Expected low-to-high prices. Actual: " + prices
        );
    }
}

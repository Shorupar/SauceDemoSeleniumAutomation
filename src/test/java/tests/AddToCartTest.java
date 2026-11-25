package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;

public class AddToCartTest extends BaseTest {

    private static final String PRODUCT_1 = "Sauce Labs Backpack";
    private static final String PRODUCT_2 = "Sauce Labs Bike Light";

    @Test(description = "Add two products and verify both appear in the cart")
    public void addItemsToCartTest() {

        // Step 1: Login
        new LoginPage(driver).login(VALID_USER, PASSWORD);

        InventoryPage inventory = new InventoryPage(driver);

        // Step 2: Verify inventory page
        Assert.assertTrue(
                inventory.waitForUrlContains("inventory.html"),
                "Inventory page did not open."
        );

        // Step 3: Add first product
        inventory.addProductToCart(PRODUCT_1);

        Assert.assertTrue(
                inventory.waitForCartCount("1"),
                "Cart badge should show 1 after first item."
        );

        // Step 4: Add second product
        inventory.addProductToCart(PRODUCT_2);

        Assert.assertTrue(
                inventory.waitForCartCount("2"),
                "Cart badge should show 2 after second item."
        );

        // Step 5: Open cart
        inventory.openCart();

        CartPage cart = new CartPage(driver);

        Assert.assertTrue(
                cart.waitForUrlContains("cart.html"),
                "Cart page did not open."
        );

        // Step 6: Verify cart products
        Assert.assertTrue(
                cart.waitForCartProductCount(2),
                "Cart should contain exactly two products."
        );

        Assert.assertTrue(
                cart.isProductInCart(PRODUCT_1),
                PRODUCT_1 + " should be in cart."
        );

        Assert.assertTrue(
                cart.isProductInCart(PRODUCT_2),
                PRODUCT_2 + " should be in cart."
        );
    }
}

package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

public class CheckoutTests extends BaseTest {

    @Test(description = "Complete checkout and verify the order confirmation")
    public void performCheckoutTest() {

        // Step 1: Login
        new LoginPage(driver).login(VALID_USER, PASSWORD);

        sleep(1000);

        InventoryPage inventory = new InventoryPage(driver);

        Assert.assertTrue(
                inventory.waitForUrlContains("inventory.html"),
                "Inventory page did not open."
        );

        // Step 2: Add first product
        inventory.addProductToCart("Sauce Labs Backpack");

        Assert.assertTrue(
                inventory.waitForCartCount("1"),
                "First product was not added."
        );

        // Step 3: Add second product
        inventory.addProductToCart("Sauce Labs Bike Light");

        Assert.assertTrue(
                inventory.waitForCartCount("2"),
                "Second product was not added."
        );

        // Step 4: Open cart
        inventory.openCart();

        sleep(1000);

        CartPage cart = new CartPage(driver);

        Assert.assertTrue(
                cart.waitForUrlContains("cart.html"),
                "Cart page did not open."
        );

        Assert.assertTrue(
                cart.waitForCartProductCount(2),
                "Expected two products in cart."
        );

        // Step 5: Click checkout
        cart.clickCheckout();

        sleep(1000);

        // Step 6: Fill checkout information
        CheckoutInformationPage information =
                new CheckoutInformationPage(driver);

        Assert.assertTrue(
                information.waitForUrlContains("checkout-step-one.html"),
                "Checkout information page did not open."
        );

        information.fillCheckoutInformation(
                "Shorupa", "Rai", "44600"
        );

        sleep(1000);

        information.clickContinue();

        sleep(1000);

        // Step 7: Verify checkout overview
        CheckoutOverviewPage overview =
                new CheckoutOverviewPage(driver);

        Assert.assertTrue(
                overview.waitForUrlContains("checkout-step-two.html"),
                "Checkout overview page did not open."
        );

        Assert.assertTrue(
                overview.waitForOverviewItemCount(2),
                "Overview should show two products."
        );

        Assert.assertTrue(
                overview.getOrderTotal().matches("Total: \\$\\d+\\.\\d{2}"),
                "Order total should be displayed. Actual: "
                        + overview.getOrderTotal()
        );

        // Step 8: Finish checkout
        overview.clickFinish();

        sleep(1000);

        // Step 9: Verify order confirmation
        CheckoutCompletePage complete =
                new CheckoutCompletePage(driver);

        Assert.assertTrue(
                complete.waitForUrlContains("checkout-complete.html"),
                "Checkout completion page did not open."
        );

        Assert.assertTrue(
                complete.isOrderConfirmed(),
                "Order confirmation should be visible."
        );

        Assert.assertEquals(
                complete.getConfirmationHeader(),
                "Thank you for your order!"
        );

        Assert.assertTrue(
                complete.getConfirmationMessage()
                        .toLowerCase()
                        .contains("dispatched"),
                "Expected dispatch confirmation text."
        );
    }
}

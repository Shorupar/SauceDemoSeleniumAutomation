package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.InvalidLoginPage;
import pages.LoginPage;

public class LoginTests extends BaseTest {

    @Test(description = "Locked-out user cannot log in and sees the expected error")
    public void invalidLoginTest() {

        LoginPage loginPage = new LoginPage(driver);

        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
                "Login form should be visible.");

        loginPage.enterUsername(LOCKED_USER);
        loginPage.enterPassword(PASSWORD);
        loginPage.clickLogin();

        InvalidLoginPage invalidLoginPage = new InvalidLoginPage(driver);

        Assert.assertTrue(invalidLoginPage.isErrorDisplayed(),
                "An error should appear for the locked user.");

        Assert.assertEquals(invalidLoginPage.getErrorMessage(),
                "Epic sadface: Sorry, this user has been locked out.",
                "Unexpected locked-user error.");

        Assert.assertTrue(invalidLoginPage.isStillOnLoginPage(),
                "Locked user must remain on the login page.");
    }

    @Test(description = "Valid user can log in and see the inventory")
    public void validLoginTest() {

        LoginPage loginPage = new LoginPage(driver);

        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
                "Login form should be visible.");

        loginPage.enterUsername(VALID_USER);
        loginPage.enterPassword(PASSWORD);
        loginPage.clickLogin();

        InventoryPage inventoryPage = new InventoryPage(driver);

        Assert.assertTrue(inventoryPage.waitForUrlContains("inventory.html"),
                "User should be redirected to inventory.");

        Assert.assertTrue(inventoryPage.isInventoryDisplayed(),
                "Product list should be visible.");

        Assert.assertEquals(driver.getTitle(), "Swag Labs",
                "Unexpected page title.");
    }
}

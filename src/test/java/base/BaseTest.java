package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.DriverFactory;

import java.time.Duration;

public class BaseTest {

    protected static final String BASE_URL =
            System.getProperty("baseUrl", "https://www.saucedemo.com/");

    protected static final String VALID_USER = "standard_user";
    protected static final String LOCKED_USER = "locked_out_user";
    protected static final String PASSWORD = "secret_sauce";

    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {

        driver = DriverFactory.createDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get(BASE_URL);

        sleep(500);

        wait.until(d -> "complete".equals(
                ((org.openqa.selenium.JavascriptExecutor) d)
                        .executeScript("return document.readyState")));

        sleep(500);
    }

    protected void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread sleep interrupted", e);
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}

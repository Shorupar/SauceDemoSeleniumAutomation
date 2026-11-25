package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.Map;

public final class DriverFactory {

    private DriverFactory() {
    }

    public static WebDriver createDriver() {

        ChromeOptions options = new ChromeOptions();

        options.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false
        ));

        options.addArguments(
                "--disable-notifications",
                "--disable-popup-blocking"
        );

        if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {

            options.addArguments(
                    "--headless=new",
                    "--window-size=1440,1000",
                    "--no-sandbox",
                    "--disable-dev-shm-usage"
            );

        } else {
            options.addArguments("--start-maximized");
        }

        WebDriver driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(15));

        return driver;
    }
}

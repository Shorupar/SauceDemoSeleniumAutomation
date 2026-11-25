package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class BaseClass {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BaseClass(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.wait.pollingEvery(Duration.ofMillis(250));
    }

    protected void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread sleep interrupted", e);
        }
    }

    protected WebElement find(By locator) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator));

        sleep(500);

        return element;
    }

    protected List<WebElement> findAll(By locator) {
        List<WebElement> elements = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));

        sleep(500);

        return elements;
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();

        sleep(500);
    }

    protected void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();

        sleep(500);
    }

    protected void type(By locator, String value) {

        WebElement element = find(locator);

        wait.until(ExpectedConditions.elementToBeClickable(element));

        element.clear();

        sleep(500);

        element.sendKeys(value);

        wait.until(d -> value.equals(element.getAttribute("value")));

        sleep(500);
    }

    protected String getText(By locator) {

        String text = find(locator).getText().trim();

        sleep(500);

        return text;
    }

    protected boolean isDisplayed(By locator) {
        try {
            return find(locator).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean waitForUrlContains(String text) {
        try {

            boolean result = wait.until(
                    ExpectedConditions.urlContains(text));

            sleep(500);

            return result;

        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    protected void waitForPageReady() {

        wait.until(d -> "complete".equals(
                ((org.openqa.selenium.JavascriptExecutor) d)
                        .executeScript("return document.readyState")));

        sleep(500);
    }
}

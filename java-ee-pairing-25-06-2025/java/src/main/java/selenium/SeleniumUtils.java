package selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Iterator;
import java.util.Map;

public class SeleniumUtils {
    private static final Logger log = LoggerFactory.getLogger(SeleniumUtils.class);

    public static WebElement scrollToElement(WebElement element, WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        return element;
    }
    
    public static void scrollBy(WebDriver driver, int x, int y) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(arguments[0], arguments[1]);", x, y);
    }
    
    public static void scrollToTop(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
    }

    public static void scrollToBottom(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    public static void scrollDownUsingPageDown(WebDriver driver) {
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.PAGE_DOWN).build().perform();
    }

    public static void scrollToTheElement(WebDriver driver, By element) {
        WebElement webElement = driver.findElement(element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", webElement);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * This gives us a way to add some self-correcting mechanism to find elements.
     * It will try to find the element with the provided locators in the order they are given.
     * If the element is not found with one locator, it will try the next one.
     * If the element is not found with any of the locators, it will throw an exception.
     * @param findBy
     * @param driver
     * @return
     * @throws SeleniumCustomException
     */
    public static WebElement findElementWithOptions(Map<SeleniumTypes, String> findBy, WebDriver driver) throws SeleniumCustomException {
        return findElementWithOptions(findBy, driver, "Element not found with any of the provided locators." );
    }

    public static WebElement findElementForTypeValue(SeleniumTypes key, String value, WebDriver driver) {
        switch (key) {
            case SeleniumTypes.ID:
                return driver.findElement(By.id(value));
            case SeleniumTypes.NAME:
                return driver.findElement(By.name(value));
            case SeleniumTypes.CLASS_NAME:
                return driver.findElement(By.className(value));
            case SeleniumTypes.TAG_NAME:
                return driver.findElement(By.tagName(value));
            case SeleniumTypes.LINK_TEXT:
                return driver.findElement(By.linkText(value));
            case SeleniumTypes.PARTIAL_LINK_TEXT:
                return driver.findElement(By.partialLinkText(value));
            case SeleniumTypes.CSS:
                return driver.findElement(By.cssSelector(value));
            case SeleniumTypes.XPATH:
                return driver.findElement(By.xpath(value));
            case SeleniumTypes.TEXT:
                return driver.findElement(By.xpath("//*[text()='" + value + "']"));
            case SeleniumTypes.PARTIAL_TEXT:
                return driver.findElement(By.xpath("//*[contains(text(),'" + value + "')]"));
            default:
                throw new IllegalArgumentException("Invalid locator type: " + key);
        }
    }

    public static WebElement findElementWithOptions(Map<SeleniumTypes,
            String> findBy, WebDriver driver, String errorMessage) throws SeleniumCustomException {
        WebElement webElement = null;
        Iterator<SeleniumTypes> findByIterator = findBy.keySet().iterator();
        while(findByIterator.hasNext()) {
            SeleniumTypes key = findByIterator.next();
            String value = findBy.get(key);
            if(value != null && !value.isEmpty()) {
                try {
                    webElement = findElementForTypeValue(key, value, driver);
                    if (webElement != null)
                        break;
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error(e.getMessage());
                    if (!findByIterator.hasNext())
                        throw new SeleniumCustomException(errorMessage);
                }
            }
        }
        return webElement;
    }


    public static WebElement waitUntilElementIsClickable(WebDriverWait wait, By by) throws SeleniumCustomException {
        return waitUntilElementIsClickable(wait, by, String.format("Element %s wasn't clickable after wait", by.toString()));
    }


    public static WebElement waitUntilElementIsClickable(WebDriverWait wait, By by, String errorMessage) throws SeleniumCustomException {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(by));
        } catch (Exception e) {
            throw new SeleniumCustomException(errorMessage, e);
        }
    }

    public static WebElement waitForElementToBeVisible(WebDriverWait wait, By by, String errorMessage) throws SeleniumCustomException {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Exception e) {
            throw new SeleniumCustomException(errorMessage, e);
        }
    }

    public static WebElement waitForElementToBeVisible(WebDriverWait wait, By by) throws SeleniumCustomException {
        return waitForElementToBeVisible(wait, by, String.format("The element %s wasn't visible even after waiting", by.toString()));
    }

    public static WebElement findElementWithoutExceptions(WebDriver driver, By nextPage) {
        try {
            return driver.findElement(nextPage);
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}

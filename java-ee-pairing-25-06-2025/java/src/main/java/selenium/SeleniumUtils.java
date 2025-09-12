package selenium;

import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
                        throw new SeleniumCustomException("Element not found with any of the provided locators. " +
                                "Last tried with " + key + " : " + value);
                }
            }
        }
        return webElement;
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
}

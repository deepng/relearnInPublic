package selenium.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.SeleniumCustomException;
import selenium.SeleniumUtils;

import java.time.Duration;

public class HomePage extends BasePage {

    private final By nextPage = By.xpath("//button[@aria-label='Next page']");
    private final By basket = By.xpath("//button[@routerlink='/basket']");

    public WebElement getTheBasket(WebDriver driver) {
        return driver.findElement(basket);
    }

    public void clearWantIn(WebDriver driver) {
        // or use //a[@aria-label='dismiss cookie message']
        String meWantIT = "Me want it!";
        WebElement wantInElement = SeleniumUtils.findElementWithoutExceptions(driver,
                By.xpath("//a[normalize-space()='"+ meWantIT +"']"));
//                By.xpath("//*[text()='"+meWantIT+"']"));
        if(wantInElement != null && wantInElement.isDisplayed())
            wantInElement.click();
    }

    public WebElement openProduct(WebDriver driver, String productToReview) throws SeleniumCustomException {
        boolean pagesDone = false;
        while(!pagesDone) {
            WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));
            clearWantIn(driver);
            try { // //div[normalize-space()='OWASP Juice Shop "King of the Hill" Facemask']
                return SeleniumUtils.waitForElementToBeVisible(webDriverWait,
                        By.xpath("//div[normalize-space()='" + productToReview +"']"));
//                        By.xpath("//*[text()='" + productToReview.trim() + "'"));
            } catch (SeleniumCustomException e) {
                if (pagesDone)
                    throw new SeleniumCustomException(e);
                SeleniumUtils.scrollDownUsingPageDown(driver);
                WebElement nextPageElement = SeleniumUtils.findElementWithoutExceptions(driver, nextPage);
                if(nextPageElement != null && nextPageElement.isDisplayed() && nextPageElement.isEnabled()) {
                    Actions actions = new Actions(driver);
                    actions.moveToElement(nextPageElement).build().perform();
                    nextPageElement.click();
                } else
                    pagesDone = true;
            }
        }
        return null;
    }
}

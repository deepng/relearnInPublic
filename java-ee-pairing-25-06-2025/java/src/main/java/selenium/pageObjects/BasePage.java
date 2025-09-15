package selenium.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.SeleniumCustomException;
import selenium.SeleniumTypes;
import selenium.SeleniumUtils;

import java.util.HashMap;
import java.util.Map;

public abstract class BasePage {

    public void closeWelcomePopup(WebDriver driver) throws SeleniumCustomException {

//        driver.findElement(By.xpath("//*[text()='Dismiss']")).click();
        WebElement dismissButton = getDismissButton(driver);
        dismissButton.click();
    }

    private WebElement getDismissButton(WebDriver driver) throws SeleniumCustomException {
        Map<SeleniumTypes, String> findBy = new HashMap<>();
        findBy.put(SeleniumTypes.TEXT, "Dismiss");
         return SeleniumUtils.findElementWithOptions(findBy, driver);
    }

    public boolean checkWelcomePage(WebDriver driver) throws SeleniumCustomException {
        WebElement dismissButton = getDismissButton(driver);
        return dismissButton.isEnabled();
    }

    public boolean checkForWelcomeMessage(WebDriver driver) {
        return true; // TODO: update
    }
}

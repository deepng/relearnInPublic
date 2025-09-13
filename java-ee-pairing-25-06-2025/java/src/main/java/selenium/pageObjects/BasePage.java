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
        Map<SeleniumTypes, String> findBy = new HashMap<>();
        findBy.put(SeleniumTypes.TEXT, "Dismiss");
        WebElement dismissButton = SeleniumUtils.findElementWithOptions(findBy, driver);
//        driver.findElement(By.xpath("//*[text()='Dismiss']")).click();
        dismissButton.click();
    }

}

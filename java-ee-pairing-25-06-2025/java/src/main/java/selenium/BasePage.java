package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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

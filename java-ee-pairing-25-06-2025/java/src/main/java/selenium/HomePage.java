package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {

    public WebElement getTheBasket(WebDriver driver) {
        return driver.findElement(By.xpath("//button[@routerlink='/basket']"));
    }

}

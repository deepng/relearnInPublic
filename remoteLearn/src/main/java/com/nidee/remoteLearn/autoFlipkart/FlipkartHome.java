package com.nidee.remoteLearn.autoFlipkart;

import com.fasterxml.jackson.databind.ser.Serializers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class FlipkartHome extends BaseClass {

    public FlipkartHome(WebDriver driver) {
        super(driver);
    }

    public boolean checkHomePage() {
        return driver.findElement(By.name("q")).isDisplayed();
    }

    public void searchProductAndSelectFromSuggestions(String productName, String suggestion) {
        driver.findElement(By.name("q")).sendKeys(productName);
        // check for the options
        List<WebElement> querySuggestions = driver.findElements(By.className("_3D0G9a"));
        querySuggestions.stream().filter(x -> x.getText().contains(suggestion)).findFirst().get().click();
    }
}

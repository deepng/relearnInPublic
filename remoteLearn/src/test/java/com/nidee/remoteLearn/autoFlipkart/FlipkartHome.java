package com.nidee.remoteLearn.autoFlipkart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FlipkartHome {
    WebDriver driver;

    public FlipkartHome(WebDriver driver) {
        this.driver = driver;
    }

    public void checkHomePage() {
        driver.findElement(By.name("q")).isDisplayed();
    }
}

package com.nidee.remoteLearn.autoFlipkart;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;

public class SeleniumUtil {

    public void openUrlInNewWindow(WebDriver driver, String url) {
        driver.switchTo().newWindow(WindowType.WINDOW);
        driver.navigate().to(url);
    }

    public void openNewTab(WebDriver driver, String url) {
        driver.switchTo().newWindow(WindowType.TAB);
        driver.navigate().to(url);
    }

}

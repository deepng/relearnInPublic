package com.nidee.remoteLearn.autoFlipkart;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Create common functions to navigate
 */
public class BaseClass {
    public static final String FLIPKART_URL = "https://www.flipkart.com/";

    public WebDriver setDriver(String browser) {
        WebDriver driver;
        if (browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            // set firefox driver
            driver = new FirefoxDriver();
        } else if (browser.equals("edge")) {
            WebDriverManager.edgedriver().setup();
            // set edge driver
            driver = new EdgeDriver();
        } else {
            // default to chrome
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;
    }

    public void loadFlipkart(WebDriver driver) {
        // Load flipkart
        driver.get(FLIPKART_URL);
    }
}

package com.nidee.remoteLearn.autoFlipkart;

import com.nidee.remoteLearn.exceptions.CustomException;
import com.nidee.remoteLearn.utils.CommonUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static java.util.ResourceBundle.getBundle;

public class BaseTest {
    static {
        LogManager.getLogManager().reset();
    }
    Logger logger = Logger.getLogger(BaseTest.class.getName());
    public static final String TEST_PROPERTIES = "test.properties";
    WebDriver driver;
    String browser = "chrome";

    @BeforeSuite
    public void suiteSetup() throws CustomException {
        BaseClass baseClass = new BaseClass(driver);
//        readTestProperties(); // Setup the browser from properties file
        if(browser.equalsIgnoreCase("firefox")) {
            driver = baseClass.setDriver("firefox");
        } else if(browser.equalsIgnoreCase("edge")) {
            driver = baseClass.setDriver("edge");
        } else {
            driver = baseClass.setDriver("chrome");
        }
        baseClass.loadFlipkart(driver);
    }

    /**
     * read the test properties file to get the browser to use
     */
    public void readTestProperties() throws CustomException {
        try {
            ResourceBundle resourceBundle = getBundle(TEST_PROPERTIES);
            if(resourceBundle.containsKey("browser")) {
                Enumeration<String> keys = resourceBundle.getKeys();
                while(keys.hasMoreElements()) {
                    String key = keys.nextElement();
                    if(key.equals("browser")) {
                        browser = resourceBundle.getString(key);
                    }
                }
            }
            logger.log(Level.INFO, "Browser is " + browser);
        } catch (Exception e) {
            throw new CustomException(CommonUtils.generateErrorCode(this.getClass().getCanonicalName(),
                    String.valueOf(Thread.currentThread().getStackTrace()[1].getLineNumber())), e);
        }
    }

    @AfterSuite
    public void suiteTearDown() {
        if(driver != null) {
            driver.quit();
        }
    }

}

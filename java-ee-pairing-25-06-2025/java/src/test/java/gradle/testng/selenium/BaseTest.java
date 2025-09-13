package gradle.testng.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import java.time.Duration;
import java.util.*;

public abstract class BaseTest {

    public WebDriver driver;
    public static Map<String, String> testData;

    public void setUpTestData() {
        // load test data from properties file
        testData = new HashMap<>();
        ResourceBundle bundle = ResourceBundle.getBundle("test");//.getKeys().collectEntries { key -> [ (key) : ResourceBundle.getBundle('testData.json').getString(key) ] }
        bundle.keySet().forEach(key -> {
            testData.put(key, bundle.getString(key));
        });
        Properties properties = System.getProperties();
        for(String key : properties.stringPropertyNames()) {
            // if key starts with "test.", usually used to differentiate system properties
            if(key.startsWith("test."))
                testData.put(key.replaceFirst("test.", ""), properties.getProperty(key));
        }
    }

    @BeforeSuite
    public void setUp() {
        setUpTestData();
    }


    public void setUpDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        if(testData != null)
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(testData.get("implicitWaitInSec") != null ?
                Integer.parseInt(testData.get("implicitWaitInSec")) : 10));
    }

    @AfterSuite
    public void teardown() {
        if(this.driver != null)
            this.driver.quit();
    }

}

package ee.testng;

import helpers.TestDataReader;
import listeners.TestReporter;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import selenium.DriverFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;


@Listeners({TestReporter.class})
public abstract class BaseTest {

    public WebDriver driver;



    @BeforeSuite
    public void setUp() {
        TestDataReader.getInstance().setUpTestData();
    }

    public void setUpDriver() {
        Map<String, String> testData = TestDataReader.getInstance().getTestData();
        String browser = testData.get("browser");
        driver = DriverFactory.getDriver(browser);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(testData.get("implicitWaitInSec") != null ?
                Integer.parseInt(testData.get("implicitWaitInSec")) : 10));
    }

    @AfterSuite
    public void teardown() {
        if(this.driver != null)
            this.driver.quit();
    }

}

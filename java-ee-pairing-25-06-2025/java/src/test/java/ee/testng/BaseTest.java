package ee.testng;

import helpers.Constants;
import helpers.TestDataReader;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import listeners.TestReporter;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import restAssured.apis.LoginApi;
import selenium.DriverFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


@Listeners({TestReporter.class})
public abstract class BaseTest extends AbstractTestNGCucumberTests {

    public WebDriver driver;

    private static String address = "localhost";
    private static String port = "3000";
    public String baseUrl;

    @BeforeSuite
    public void setUp() {
        TestDataReader.getInstance().setUpTestData();
        baseUrl = TestDataReader.getInstance().getTestData().get(Constants.BaseURL);
        if (baseUrl == null) {
            baseUrl = String.format("http://%s:%s", address, port);
        }
    }

    /**
     * Returns an empty string if we couldn't login
     * @param user
     * @param password
     * @return
     */
    public String loginAndCaptureCookie(String user, String password) {
        LoginApi loginApi = new LoginApi(baseUrl); // TODO: get baseUrl from TestData
        return loginApi.loginAndGetStatus(user, password);
    }

    public void setUpDriver() {
        Map<String, String> testData = TestDataReader.getInstance().getTestData();
        String browser = testData.get(Constants.BROWSER);
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

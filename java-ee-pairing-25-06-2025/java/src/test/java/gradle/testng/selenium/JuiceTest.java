package gradle.testng.selenium;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import selenium.HomePage;
import selenium.LoginPage;
import selenium.SeleniumCustomException;

import java.io.File;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class JuiceTest extends BaseTest {
    private static String address = "localhost";
    private static String port = "3000";
    private static String baseUrl = String.format("http://%s:%s", address, port);
    Logger logger = LoggerFactory.getLogger(JuiceTest.class);

    public Customer customer;
    public LoginPage loginPage;
    public HomePage homePage;


    @BeforeClass
    void setup() {
        //TODO Task1: Add your credentials to customer i.e. email, password and security answer.
        customer = new Customer(testData.get("validUserId"), testData.get("validPassword"), testData.get("validUserSecurityAnswer"));

    }




    //TODO Task2: Login and post a product review using Selenium
    @Test
    void loginAndPostProductReviewViaUiTest(ITestContext testContext) throws InterruptedException, SeleniumCustomException {
//        driver.get(baseUrl + "/#/login");
        loginPage = new LoginPage();
        loginPage.loadLoginPage(driver, baseUrl);
        Assert.assertTrue(loginPage.getLoginPageHeader(driver).getText().contains("Login"),
                "We are not in the login page");
        Thread.sleep(2000);
        // TODO Dismiss popup (click close)
        loginPage.closeWelcomePopup(driver);
        Thread.sleep(2000);
        loginPage.login(driver, customer.getEmail(), customer.getPassword());

        Thread.sleep(2000);
        homePage = new HomePage();
//        emailField.sendKeys(testData.get("validEmail"));
//        passwordField.sendKeys(testData.get("validPassword"));
//        loginButton.click();
        Assert.assertTrue(homePage.getTheBasket(driver).isDisplayed(),
                "We might not have logged in successfully");

        // TODO Navigate to product and post review

        // TODO Assert that the review has been created successfully

    }



    // TODO Task3: Login and post a product review using the Juice Shop API
    @Test
    void loginAndPostProductReviewViaApi() {
        // Example HTTP request with assertions using Rest Assured. Can be removed.
        String status = given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "/rest/products/search")
                .then()
                .statusCode(200)
                .body("status", equalTo("success") )
                .body("data", hasItem(
                        allOf(
                                hasEntry("image", "apple_pressings.jpg"),
                                hasEntry("name", "Apple Pomace")
                        )
                ))
                .extract()
                .path("status");

        System.out.println(String.format("Status value is: %s", status));

        // TODO Retrieve token via login API

        // TODO Use token to post review to product

        // TODO Assert that the product review has persisted
    }


    @AfterMethod
    public void takeScreenshotsWhenFailure(ITestResult testResult) throws IOException {
        // Take screenshot if test failed
        if(ITestResult.FAILURE == testResult.getStatus()) {
            File screenshot = ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.FILE);
            java.nio.file.Files.copy(screenshot.toPath(), java.nio.file.Paths.get("screenshots",
                    String.format("failed-test-screenshot-%d.png", System.currentTimeMillis())));
        }
    }
}

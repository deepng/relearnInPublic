package gradle.testng.selenium;

import helpers.Utils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import restAssured.apis.*;
import restAssured.models.Customer;
import restAssured.models.Products;
import selenium.SeleniumCustomException;
import selenium.pageObjects.HomePage;
import selenium.pageObjects.LoginPage;
import selenium.pageObjects.ProductDialog;

import java.io.File;
import java.io.IOException;

public class JuiceTest extends BaseTest {
    private static final String REVIEW_PRODUCT =  "OWASP Juice Shop T-Shirt"; // "OWASP Juice Shop \"King of the Hill\" Facemask";
    private static final String REVIEW_TEXT = "This is a Test Review with 1-10 and #$!%^@";

    private static String address = "localhost";
    private static String port = "3000";
    private static String baseUrl = String.format("http://%s:%s", address, port);

    Logger logger = LoggerFactory.getLogger(JuiceTest.class);

    public Customer customer;
    public LoginPage loginPage;
    public HomePage homePage;


    @BeforeClass
    void setup() throws IOException {
        //TODO Task1: Add your credentials to customer i.e. email, password and security answer.
        customer = new Customer(testData.get("validUserId"), testData.get("validPassword"), testData.get("validUserSecurityAnswer"));
        if (loginAndCaptureCookie(customer.getEmail(), customer.getPassword()).isEmpty())
            Assert.assertTrue(createTestUser(), "We couldn't create a test user");
    }

    public boolean createTestUser() throws IOException {
        CreateUserApi createUserApi = new CreateUserApi(baseUrl);
        String status = createUserApi.createNewUser(customer);
        return status.equalsIgnoreCase("success");
    }

    /**
     * Returns an empty string if we couldn't login
     * @param user
     * @param password
     * @return
     */
    public String loginAndCaptureCookie(String user, String password) {
        LoginApi loginApi = new LoginApi(baseUrl);
        return loginApi.loginAndGetStatus(user, password);
    }

    //TODO Task2: Login and post a product review using Selenium
    @Test
    void loginAndPostProductReviewViaUiTest(ITestContext testContext) throws InterruptedException, SeleniumCustomException {
        setUpDriver();
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
        logger.debug("We have logged in with user: {}", customer.getEmail());
        Thread.sleep(2000);
        homePage = new HomePage();
//        emailField.sendKeys(testData.get("validEmail"));
//        passwordField.sendKeys(testData.get("validPassword"));
//        loginButton.click();
        Assert.assertTrue(homePage.getTheBasket(driver).isDisplayed(),
                "We might not have logged in successfully");

        // TODO Navigate to product and post review
        String productToReview = getTheProductToReview();
        WebElement productPage = homePage.openProduct(driver, productToReview);
        productPage.click();
        String reviewComments = getReviewComments();
        ProductDialog productDialog = new ProductDialog();
        Assert.assertTrue(productDialog.isVisible(driver), "We didn't open the product dialog for review");
        productDialog.addReviewComments(driver, reviewComments);

        // TODO Assert that the review has been created successfully
        productDialog.checkReviewsFor(driver, reviewComments, customer.getEmail());
    }



    // TODO Task3: Login and post a product review using the Juice Shop API
    @Test
    void loginAndPostProductReviewViaApiTest() {
        // Example HTTP request with assertions using Rest Assured. Can be removed.
//        String status = given()
//                .header("Content-Type", "application/json")
//                .when()
//                .get(baseUrl + "/rest/products/search")
//                .then()
//                .statusCode(200)
//                .body("status", equalTo("success") )
//                .body("data", hasItem(
//                        allOf(
//                                hasEntry("image", "apple_pressings.jpg"),
//                                hasEntry("name", "Apple Pomace")
//                        )
//                ))
//                .extract()
//                .path("status");
//
//        logger.debug(String.format("Status value is: %s", status));

        // TODO Retrieve token via login API
        String cookie = loginAndCaptureCookie(customer.getEmail(), customer.getPassword());
        ProductListApi productListApi = new ProductListApi(baseUrl);
        Products productList = productListApi.getProductList(cookie);
        int productId = 38;
        String productToReview = getTheProductToReview();
        for (Products.Data datum : productList.getData()) {
            if(datum.name.equalsIgnoreCase(productToReview)) {
                productId = datum.id;
                break;
            }
        }
        // TODO Use token to post review to product
        String reviewComments = getReviewComments();
        ReviewProductApi reviewProductApi = new ReviewProductApi(baseUrl);
        reviewProductApi.putReviewFor(cookie, productId, reviewComments, customer.getEmail());
        // TODO Assert that the product review has persisted
        CheckProductReviewApi checkProductReviewApi = new CheckProductReviewApi(baseUrl);
        Assert.assertTrue(checkProductReviewApi.validateThisProductReview(cookie, productId, reviewComments, customer.getEmail()),
                String.format("We reviewed product %s, but didn't find the review comments %s", productToReview, reviewComments));
    }

    private String getReviewComments() {
        return String.format("%s %s", Utils.getCurrentDateInFormat(Utils.DATE_FORMAT),
                testData.getOrDefault("reviewComments", REVIEW_TEXT));
    }

    private String getTheProductToReview() {
        return testData.getOrDefault("productToReview", REVIEW_PRODUCT);
    }


    @AfterMethod
    public void takeScreenshotsWhenFailure(ITestResult testResult) throws IOException {
        // Take screenshot if test failed
        if(driver != null && ITestResult.FAILURE == testResult.getStatus()) {
            File screenshot = ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.FILE);
            java.nio.file.Files.copy(screenshot.toPath(), java.nio.file.Paths.get("screenshots",
                    String.format("failed-test-screenshot-%s.png", Utils.getCurrentDateInFormat(Utils.SCREENSHOT_FORMAT))));
        }
    }
}

package ee.testng;

import framework.IScenario;
import framework.LoginScenario;
import helpers.TestDataReader;
import helpers.Utils;
import listeners.RetryAnalyzer;
import models.Customer;
import models.Products;
import models.UserData;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
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
import selenium.SeleniumCustomException;
import selenium.pageObjects.HomePage;
import selenium.pageObjects.LoginPage;
import selenium.pageObjects.ProductDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class JuiceTest extends BaseTest {
    private static final String REVIEW_PRODUCT =  "Apple Juice (1000ml)";
    // OWASP Juice Shop Iron-Ons (16pcs)"; // ""OWASP Juice Shop T-Shirt"; // "OWASP Juice Shop \"King of the Hill\" Facemask";
    private static final String REVIEW_TEXT = "This is a Test Review with 1-10 and #$!^@";

    private static final String defaultUserId = "deepak@ee.com";
    private static final String defaultUserPassword = "eedeepak";
    private static final String defaultUserAnswer = "simple";

    Logger logger = LoggerFactory.getLogger(JuiceTest.class);

    public Customer customer;
    public LoginPage loginPage;
    public HomePage homePage;

    public String cookie;
    
    // ThreadLocal variables for parallel execution
    private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();
    private static final ThreadLocal<String> threadLocalCookie = new ThreadLocal<>();

    @BeforeClass
    void setup() throws IOException {
        //TODO Task1: Add your credentials to customer i.e. email, password and security answer.
        Optional<UserData> firstUserData = TestDataReader.getInstance().getFirstUser();
        UserData firstUser = firstUserData.orElseGet(() ->
                new UserData(defaultUserId, defaultUserPassword, defaultUserAnswer));
        customer = new Customer(firstUser.userId, firstUser.password, firstUser.userSecurityAnswer);
        cookie = loginAndCaptureCookie(customer.getEmail(), customer.getPassword());
        if (cookie.isEmpty()) {
            Assert.assertTrue(createTestUser(), "We couldn't create a test user");
            cookie = loginAndCaptureCookie(customer.getEmail(), customer.getPassword());
        }
        threadLocalCookie.set(cookie);
    }

    public boolean createTestUser() throws IOException {
        CreateUserApi createUserApi = new CreateUserApi(baseUrl);
        String status = createUserApi.createNewUser(customer);
        return status.equalsIgnoreCase("success");
    }

    //TODO Task2: Login and post a product review using Selenium
    @Test(retryAnalyzer = RetryAnalyzer.class)
    void loginAndPostProductReviewViaUiTest(ITestContext testContext) throws InterruptedException, SeleniumCustomException {
        setUpDriver();
        threadLocalDriver.set(driver);
//        driver.get(baseUrl + "/#/login");
        loginPage = new LoginPage();
        loginPage.loadLoginPage(driver, baseUrl);
        Assert.assertTrue(loginPage.getLoginPageHeader(driver).getText().contains("Login"),
                "We are not in the login page");
        // TODO Dismiss popup (click close)
        loginPage.closeWelcomePopup(driver);
        loginPage.login(driver, customer.getEmail(), customer.getPassword());
        logger.debug("We have logged in with user: {}", customer.getEmail());
        homePage = new HomePage();
//        emailField.sendKeys(testData.get("validEmail"));
//        passwordField.sendKeys(testData.get("validPassword"));
//        loginButton.click();
        Assert.assertTrue(homePage.getTheBasket(driver).isDisplayed(),
                "We might not have logged in successfully");


//        IScenario scenario = new LoginScenario();

        // TODO Navigate to product and post review
        String productToReview = getTheProductToReview();
        logger.info("Let's review {}", productToReview);
        System.out.println("reviewing " + productToReview);
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
    @Test(invocationCount = 5, threadPoolSize = 2)
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
        String localCookie = loginAndCaptureCookie(customer.getEmail(), customer.getPassword());
        threadLocalCookie.set(localCookie);

        Products productList = getProductsList(localCookie);
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
        reviewProductApi.putReviewFor(localCookie, productId, reviewComments, customer.getEmail());
        // TODO Assert that the product review has persisted
        CheckProductReviewApi checkProductReviewApi = new CheckProductReviewApi(baseUrl);
        Assert.assertTrue(checkProductReviewApi.validateThisProductReview(localCookie, productId, reviewComments, customer.getEmail()),
                String.format("We reviewed product %s, but didn't find the review comments %s", productToReview, reviewComments));
    }

    private String getReviewComments() {
        return String.format("%s %s", Utils.getCurrentDateInFormat(Utils.DATE_FORMAT),
                TestDataReader.getInstance().getTestData().getOrDefault("reviewComments", REVIEW_TEXT));
    }

    private String getTheProductToReview() {
        String localCookie = threadLocalCookie.get();
//        if(localCookie != null && !localCookie.isEmpty()) {
//            Products productsList = getProductsList(localCookie);
//            ArrayList<Products.Data> productListData = productsList.getData();
//            Random random = new Random();
//            int randomIndex = random.nextInt(productListData.size());
//            Products.Data randomProduct = productListData.get(randomIndex);
//            return randomProduct.getName();
//        }
        return TestDataReader.getInstance().getTestData().getOrDefault("productToReview", REVIEW_PRODUCT);
    }

    private Products getProductsList(String cookie) {
        ProductListApi productListApi = new ProductListApi(baseUrl);
        return productListApi.getProductList(cookie);
    }


    @AfterMethod
    public void takeScreenshotsWhenFailure(ITestResult testResult) throws IOException {
        // Take screenshot if test failed
        WebDriver localDriver = threadLocalDriver.get();
        if(localDriver != null && ITestResult.FAILURE == testResult.getStatus()) {
            File screenshot = ((TakesScreenshot) localDriver).getScreenshotAs(OutputType.FILE);
            java.nio.file.Files.copy(screenshot.toPath(), java.nio.file.Paths.get("screenshots",
                    String.format("failed-test-screenshot-%s.png", Utils.getCurrentDateInFormat(Utils.SCREENSHOT_FORMAT))));
        }
        // Clean up ThreadLocal resources
        if(localDriver != null) {
            localDriver.quit();
            threadLocalDriver.remove();
        }
        threadLocalCookie.remove();
    }
}

package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;

public class LoginPage extends BasePage {

    public void login(WebDriver driver, String username, String password) throws SeleniumCustomException {

        // Login with credentials
//        WebElement emailField = driver.findElement(By.name("email"));
//        WebElement passwordField = driver.findElement(By.name("password"));
//        WebElement loginButton = driver.findElement(By.id("loginButton"));
//        WebElement forgotPasswordLink = driver.findElement(By.partialLinkText("#/forgot-password"));



        Map<SeleniumTypes, String> findBy = new HashMap<>();
        findBy.put(SeleniumTypes.NAME, "email");
        WebElement usernameField = SeleniumUtils.findElementWithOptions(findBy, driver);
        usernameField.sendKeys(username);

        findBy.clear();
        findBy.put(SeleniumTypes.NAME, "password");
        WebElement passwordField = SeleniumUtils.findElementWithOptions(findBy, driver);
        passwordField.sendKeys(password);

        findBy.clear();
        findBy.put(SeleniumTypes.ID, "loginButton");
        WebElement loginButton = SeleniumUtils.findElementWithOptions(findBy, driver);
        loginButton.click();

        findBy.clear();
        findBy.put(SeleniumTypes.XPATH, "//a[@routerlink='/forgot-password']");
        WebElement forgotPasswordLink = SeleniumUtils.findElementWithOptions(findBy, driver);
        if (forgotPasswordLink.isDisplayed()) {
            throw new SeleniumCustomException("Login might have failed, We are still in the login page");
        }
    }

    public WebElement getLoginPageHeader(WebDriver driver) {
        return driver.findElement(By.xpath("//div[@id='login-form']//preceding::h1"));
    }

    public void loadLoginPage(WebDriver driver, String baseUrl) {
        driver.get(baseUrl + "/#/login");
    }

}

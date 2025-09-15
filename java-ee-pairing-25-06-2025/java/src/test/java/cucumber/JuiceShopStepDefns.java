package cucumber;

import helpers.TestDataReader;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import selenium.DriverFactory;
import selenium.SeleniumCustomException;
import selenium.pageObjects.HomePage;
import selenium.pageObjects.LoginPage;

import java.sql.Driver;

public class JuiceShopStepDefns {
    private WebDriver driver;
    HomePage homePage;
    LoginPage loginPage;
    String baseUrl;

    public JuiceShopStepDefns() {
        String browser = TestDataReader.getInstance().getBrowser();
        driver = DriverFactory.getDriver(browser);
        baseUrl = TestDataReader.getInstance().getBaseUrl();
        homePage = new HomePage();
        loginPage = new LoginPage();
    }

    @Then("can dismiss the welcome screen")
    public void canDismissTheWelcomeScreen() throws SeleniumCustomException {
        // Write code here that turns the phrase above into concrete actions
        homePage.closeWelcomePopup(driver);
    }

    @Given("open Juice shop application")
    public void openJuiceShopApplication() {
        // Write code here that turns the phrase above into concrete actions
        driver.get(baseUrl);
    }

    @Then("see the welcome screen")
    public void seeTheWelcomeScreen() throws SeleniumCustomException {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertTrue(homePage.checkWelcomePage(driver), "We don't have a welcome screen");
    }

    @And("welcome screen has a welcome message {string}")
    public void welcomeScreenHasAWelcomeMessage(String arg0) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertTrue(homePage.checkForWelcomeMessage(driver), "We don't have a welcome message");
    }

    @Then("can launch the tutorial from the welcome screen")
    public void canLaunchTheTutorialFromTheWelcomeScreen() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("can dismiss the welcome screen after launching the tutorial")
    public void canDismissTheWelcomeScreenAfterLaunchingTheTutorial() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("get hacking instructions to {string}")
    public void getHackingInstructionsTo(String arg0) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("click on the hacking instructions")
    public void clickOnTheHackingInstructions() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("get hacking instruction to {string}")
    public void getHackingInstructionTo(String arg0) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("can dismiss the welcome screen after going through the tutorial")
    public void canDismissTheWelcomeScreenAfterGoingThroughTheTutorial() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}

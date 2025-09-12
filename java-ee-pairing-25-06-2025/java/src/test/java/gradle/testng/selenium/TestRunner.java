package gradle.testng.selenium;

import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"cucumber"},
        plugin = {"pretty", "html:target/cucumber-reports.html"},
        monochrome = true,
        tags = "@test"
)
public class TestRunner {

    public void runTests() {
        System.out.println("Running tests...");
    }
}

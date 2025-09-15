package ee.testng;

import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.Test;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"cucumber"},
        plugin = {"pretty", "html:target/cucumber-reports.html"},
        monochrome = true,
        tags = "@test"
)
public class TestRunner extends BaseTest {

    @Test
    public void runTests() {
        System.out.println("Running tests...");
    }
}

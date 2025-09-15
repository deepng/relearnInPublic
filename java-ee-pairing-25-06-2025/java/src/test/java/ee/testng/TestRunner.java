package ee.testng;

import helpers.TestDataReader;
import io.cucumber.testng.CucumberOptions;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"cucumber"},
        plugin = {"pretty", "html:target/cucumber-reports.html"},
        monochrome = true,
        tags = "@test"
)
public class TestRunner extends BaseTest { // BaseTest extends AbstractTestNGCucumberTests

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}

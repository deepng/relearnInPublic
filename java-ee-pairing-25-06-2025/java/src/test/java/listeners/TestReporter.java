package listeners;

import org.testng.*;

public class TestReporter implements ITestListener {

    @Override
    public void onFinish(ITestContext context) {
        IResultMap passedTests = context.getPassedTests();
        IResultMap failedTests = context.getFailedTests();
        IResultMap skippedTests = context.getSkippedTests();
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("We have %d passed tests\n", passedTests.size()));
        if(failedTests.size() > 0)
            // We send an email or message, you will need webhooks or tokens to do this
            builder.append(String.format("We have %d failed tests\n", failedTests.size()));
        if(skippedTests.size() > 0)
            builder.append(String.format("We have %d skipped tests\n", skippedTests.size()));
        System.out.println(builder.toString());

    }
}

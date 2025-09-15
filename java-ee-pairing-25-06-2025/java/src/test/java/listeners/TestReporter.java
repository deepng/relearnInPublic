package listeners;

import helpers.Utils;
import org.testng.IResultMap;
import org.testng.ITestContext;
import org.testng.ITestListener;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

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
        try {
            builder.append("Reports can be found in " + bundleTheReports());
        } catch (IOException io) {
            builder.append("We couldn't create reports this time because - " + io.getMessage());
        }
        System.out.println(builder);

    }

    // Push this to a fileServer like S3 and share the link
    private String bundleTheReports() throws IOException {
        String currentDir = System.getProperty("user.dir");
        String currentDateInFormat = Utils.getCurrentDateInFormat(Utils.SCREENSHOT_FORMAT);
        Path reportPath = Path.of(String.format("%s/%s", currentDir, "reports"));
        Path newReportPath = Path.of(String.format("%s/%s/reports-%s", currentDir, "reports", currentDateInFormat));
        Path createdPath = Files.createFile(newReportPath);
        if(createdPath.toString().equalsIgnoreCase(newReportPath.toString()))
            Files.move(reportPath, newReportPath, StandardCopyOption.REPLACE_EXISTING);
        return newReportPath.toString();
    }
}

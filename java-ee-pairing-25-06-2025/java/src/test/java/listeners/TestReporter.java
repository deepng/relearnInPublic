package listeners;

import helpers.Constants;
import helpers.ISubscriber;
import helpers.PubSub;
import helpers.Utils;
import org.testng.IResultMap;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TestReporter implements ITestListener {
    List<Map<String, Object>> apiDataList = new ArrayList<>();

    @Override
    public void onStart(ITestContext result) {
        PubSub.getInstance().registerSubscriber(Constants.API_TOPIC, new ApiSubscriber());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        apiDataList.clear();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ITestContext testContext = result.getTestContext();
        ArrayList<Map<String, Object>> currentTestApis = new ArrayList<>(apiDataList);
        // TODO: Causes memory issues for load tests
//        if(currentTestApis.size() > 0)
//            testContext.setAttribute(Constants.API_TOPIC, currentTestApis);
    }

    @Override
    public void onFinish(ITestContext context) {
        IResultMap passedTests = context.getPassedTests();
        IResultMap failedTests = context.getFailedTests();
        IResultMap skippedTests = context.getSkippedTests();
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("We have %d passed tests\n", passedTests.size()));
        if(failedTests.size() > 0)
            // Append API data if its available for the given testMethod
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
        Path newReportPath = Path.of(String.format("%s/reports-%s", reportPath, currentDateInFormat));
        Path createdPath = Files.createFile(newReportPath);
        if(createdPath.toString().equalsIgnoreCase(newReportPath.toString()))
            Files.move(reportPath, createdPath, StandardCopyOption.REPLACE_EXISTING);
        return newReportPath.toString();
    }

    private class ApiSubscriber implements ISubscriber {
        @Override
        public void processEvent(Object event) {
            // TODO: Write logic
            Map<String, Object> apiData = (Map<String, Object>) event;
            apiDataList.add(apiData);
        }
    }
}

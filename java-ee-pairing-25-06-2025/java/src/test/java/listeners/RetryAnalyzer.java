package listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    int retryCount = 1;
    static final int DEFAULT_MAX_RETRIES = 3;

    @Override
    public boolean retry(ITestResult result) {
        if(retryCount < DEFAULT_MAX_RETRIES) {
            retryCount++;
            return true;
        } else {
            return false;
        }
    }
}

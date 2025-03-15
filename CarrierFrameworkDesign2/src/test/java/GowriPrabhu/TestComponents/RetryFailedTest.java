package GowriPrabhu.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryFailedTest implements IRetryAnalyzer{
	private int retryCount = 0; // This should be an instance variable
    private static final int maxRetryCount = 3; // Set max retries
	@Override
	public boolean retry(ITestResult result) {
		// TODO Auto-generated method stub
		if(retryCount < maxRetryCount) {
			retryCount++;
			return true;
		}
		return false;
	}

}

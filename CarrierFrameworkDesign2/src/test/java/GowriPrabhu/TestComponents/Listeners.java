package GowriPrabhu.TestComponents;

import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import GowriPrabhu.resorces.ExtentReporterNG;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;

public class Listeners extends BaseTest implements ITestListener {
	ExtentTest test;
 ExtentReports extent = ExtentReporterNG.getReportObject();
 ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
    @Override
    public void onTestStart(ITestResult result) {
       test =  extent.createTest(result.getMethod().getMethodName());
       extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
    	//extentTest.get().log(Status.PASS, "Test Passed");
    	extentTest.get().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
    	extentTest.get().fail(result.getThrowable());
    	try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	String filepath = null;
    	try {
			 filepath = getScreenShot(result.getMethod().getMethodName(),driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	extentTest.get().addScreenCaptureFromPath(filepath,result.getMethod().getMethodName());
    	
    	//Screen Short
    
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test Skipped: " + result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Test Execution Started");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Test Execution Finished");
        extent.flush();
    }
}

package Listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import TestBase.testBase;

public class CustomListener extends testBase implements ITestListener {
	ExtentTest test;
	String screenshotpath;

	/*
	 * GenerateReport() extent object return karte hai , us object ko hamne ext name
	 * ke object me store krdia and ext ko use krege is class me
	 */
	ExtentReports extent = GenerateReport(); // non static method of paretn ko child ke class me outside method
												// call
	// karana hai to it must be returning something and it needs to be
	// called inside another non static var

	/*
	 * On start of each test it will create toggle for creates a toggle for the
	 * given test, adds all info of events under it
	 */
	@Override
	public void onTestStart(ITestResult result) { // methods inside interface are by default public and abstract
													// (ie they are non static)
		test = extent.createTest(result.getMethod().getMethodName());
		test.info("testcase started");
	}

	/*
	 * It is necessary to call extent.flush() in this method to ensure that the
	 * extent report is properly generated and finalized after each test cases
	 * finishes
	 */
	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}

	/*
	 * On test failure call the getscreenshot which returns a string and we are
	 * storing it in another variable and then passing to
	 * test.addScreenCaptureFromPath to add ss in extent report on testcase failure
	 * 
	 */
	@Override
	public void onTestFailure(ITestResult result) {
		test.log(Status.FAIL, "failed");
		System.out.println("failed so exiting browser");
		screenshotpath = getScreenshot(result.getMethod().getMethodName());
		test.addScreenCaptureFromPath(screenshotpath);
		driver.close();
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		test.info("testcase ended");
	}

//	@Override simple method of taking screenshot on test failure without extent report
//	public void onTestFailure(ITestResult result) {
//		System.out.println("failed so exiting browser");
//		getScreenshot(result.getMethod().getMethodName());
//		driver.close();
//	}

}

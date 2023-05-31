package facebookLogin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class extenreportbasic {
	static String url_google = "https://www.google.com/";
	static String expectedTitleGoogle = "Google";

	public static void main(String[] args) {

		// start reporters
		ExtentSparkReporter spark = new ExtentSparkReporter("extent.html");

		// create ExtentReports and attach reporter(s)
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(spark);

		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\rudraksh\\eclipse-workspace\\seleniumPractise\\Chromedriver.exe");
		WebDriver driver = new ChromeDriver();

		// creates a toggle for the given test, adds all info of events under it
		ExtentTest test = extent.createTest("testname");

		test.info("testcase started");
		// navigate to google
		driver.navigate().to(url_google);

		// validate google title
		String obtainedTitleGoogle = driver.getTitle();
		Assert.assertEquals(obtainedTitleGoogle, expectedTitleGoogle);
		System.out.println("pass");

		driver.quit();
		test.info("testcase ended");

		// calling flush writes everything to the report file
		extent.flush();

	}

}

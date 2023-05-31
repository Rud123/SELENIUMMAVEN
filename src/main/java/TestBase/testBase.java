package TestBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class testBase {
	public static WebDriver driver = null; // driver is advisable to make it static if its being used in other classes
	public static Properties prop;
	public ExtentSparkReporter spark;
	public ExtentReports extent;
	String browser;
	String url;

	/*
	 * Method to generate extent report return the object of extent report as it
	 * will be used in listener class
	 */
	@BeforeSuite
	public ExtentReports GenerateReport() {
		spark = new ExtentSparkReporter("extent.html");
		extent = new ExtentReports();
		extent.attachReporter(spark);
		return extent;
	}

//	/*
//	/*
//	 * initialization using parameters from testng.xml
//	 */
//	@SuppressWarnings("deprecation")
//	@Parameters({ "browser", "url" })
//	@BeforeClass
//	public void initializationParametersTestng(String browser, String url) {
//		this.browser = browser;
//		this.url = url;
//
//		if (browser.equalsIgnoreCase("chrome")) {
//			WebDriverManager.chromedriver().setup();
//			driver = new ChromeDriver();
//		} else if (browser.equalsIgnoreCase("edge")) {
//			WebDriverManager.edgedriver().setup();
//			driver = new EdgeDriver();
//		} else {
//			System.out.println("invalid input");
//		}
//
//		driver.manage().window().maximize();
//		driver.manage().deleteAllCookies();
//		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//		driver.get(url);
//	}

	/*
	 * initialization using config properties
	 */
	@SuppressWarnings("deprecation")
	@BeforeClass
	public void initializationConfigProperties() {
		loadConfigProperties(); // ye vala method object prop ko load karega config file se
		browser = prop.getProperty("browser");
		url = prop.getProperty("url");

		if (browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		} else {
			System.out.println("invalid input");
		}

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(url);
	}

	/*
	 * The loadConfigProperties() method loads the properties file into the prop
	 * object
	 * 
	 * And then we can user prop.getProperty() to access the config file
	 */
	public void loadConfigProperties() {
		prop = new Properties();
		try {
			FileInputStream file = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\main\\java\\config\\configuration.properties");
			prop.load(file);
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Method to take screenshot changed to return type to String to return path of
	 * copied file as it will be used in listener class to capture screenshot on
	 * test failure and attach in extent report
	 */
	public String getScreenshot(String Filename) {
		String dest = null;
		try {
			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			dest = "C:\\Users\\rudraksh\\eclipse-workspace\\SeleniumMaven\\target\\ScreenshotFailed\\" + Filename
					+ ".png";
			FileUtils.copyFile(src, new File(dest));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dest;

	}

	/*
	 * Method to open a new blank tab using javascript executor
	 */
	public void openNewTab() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.open()");
		Thread.sleep(2000);
	}

	/*
	 * Method to get switch tabs
	 */
	public void switchTabs(int num) {
		ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tab.get(num));
	}

	/*
	 * Calling extent.flush() in this method ensures that the extent report is
	 * flushed and any remaining information is written to the report file before
	 * the test suite(all <test>) execution is completed.
	 * 
	 * extent.flush() is req in both: after class and in ontestfinish in listener
	 * as Both calls to extent.flush() serve different purposes and are necessary to
	 * ensure that the extent report is correctly generated and all the test
	 * information is captured in the report file.
	 */
	@AfterClass
	public void tearDown() {
		driver.quit();
		extent.flush();
	}

//	public void getScreenshot(String Filename) {
//		try {
//			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//			String dest = "C:\\Users\\rudraksh\\eclipse-workspace\\SeleniumMaven\\target\\ScreenshotFailed\\" + Filename
//					+ ".png";
//			FileUtils.copyFile(src, new File(dest));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}  simple method of taking screenshot on test failure without extent report

}

package loginPage;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utility.ExcelData;

public class facebookLogin {
	public WebDriver driver = null;

	public facebookLogin(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(name = "email")
	WebElement username;

	@FindBy(name = "pass")
	WebElement password;

	@FindBy(name = "login")
	WebElement loginButton;

	@FindBy(xpath = "//span[text()='RudTest Rud']")
	WebElement profileName;

	public boolean loginCorrect() throws InterruptedException {
//		username.sendKeys("RudTest");
//		password.sendKeys("Rud@123.456");

		// getting username and password through excel
		username.sendKeys(ExcelData.getCellData("loginInfo", "username", 1)); // row index starting from 0 (so data is
																				// in 1st row, 0th row contains
																				// headings)
		password.sendKeys(ExcelData.getCellData("loginInfo", "password", 1));
		loginButton.click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOf(profileName));

		if (profileName.isDisplayed()) {
			String pname = profileName.getText();
			ExcelData.write_CellData("loginInfo", 1, 2, pname);//row with index 1 and col or cell with index 2
			ExcelData.write_CellData("fetchedData", 1, 0, pname);//row with index 1 and col or cell with index 0
			System.out.println("login success");
			System.out.println("login success");
			System.out.println("login success");
			return true;
		} else {
			System.out.println("login failed");
			return false;
		}

	}

}

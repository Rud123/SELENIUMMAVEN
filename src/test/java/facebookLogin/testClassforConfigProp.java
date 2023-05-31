package facebookLogin;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Listeners.CustomListener;
import TestBase.testBase;
import loginPage.facebookLogin;

@Listeners(CustomListener.class)
public class testClassforConfigProp extends testBase {
	facebookLogin fbl;

	@BeforeMethod
	public void setup() {
		fbl = new facebookLogin(driver);
	}

	@Test(priority = 1)
	public void loginPass() throws InterruptedException {
		Assert.assertTrue(fbl.loginCorrect());

	}

	@Test(priority = 2)
	public void windowSwitch() throws InterruptedException {
		openNewTab();
		switchTabs(1);
		switchTabs(0);
	}

}

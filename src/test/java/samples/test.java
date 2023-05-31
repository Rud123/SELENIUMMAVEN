package samples;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Listeners.CustomListener;
import TestBase.testBase;
import loginPage.facebookLogin;

@Listeners(CustomListener.class)
public class test extends testBase {
	facebookLogin fbl;

	@BeforeMethod
	public void setup() {

		fbl = new facebookLogin(driver);
	}

	@Test
	public void tested() throws InterruptedException {
		prop.setProperty("browser", "edge");
		Assert.assertFalse(fbl.loginCorrect());
	}

}

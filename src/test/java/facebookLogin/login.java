package facebookLogin;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Listeners.CustomListener;
import TestBase.testBase;
import loginPage.facebookLogin;

@Listeners(CustomListener.class)
public class login extends testBase {
	facebookLogin fbl; // static non static vala sirf parent child me hoga agar normal dusri class ka
						// kisi or karna hai call to object banana hoga jis class ka call karna hai uski
						// jis class me call krna hai usme

	@BeforeMethod
	public void setup() {
		fbl = new facebookLogin(driver);
	}// if the loginIncorrect method is not called during the test execution, the
		// facebookLogin object would have been created unnecessarily if we do it at
		// class level So we create object for Page class in particular method or in
		// before method.

	@Test(priority = 1)
	public void loginPass() throws InterruptedException {
//		facebookLogin fbl = new facebookLogin(driver);
		Assert.assertTrue(fbl.loginCorrect());

	}

	@Test(priority = 2)
	public void windowSwitch() throws InterruptedException {
		openNewTab(); // open new blank tab
		switchTabs(1); // switch focus to that tab then only do some actions
		switchTabs(0); // switch focus back to parent tab
	}

}

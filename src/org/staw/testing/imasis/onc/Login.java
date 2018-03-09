package org.staw.testing.imasis.onc;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.staw.framework.CustomizedActions;
import org.staw.framework.SeleniumDriver;
import org.staw.framework.SoftAssertion;
import org.staw.framework.helpers.Utilities;

public class Login {
	private static SoftAssertion myAssert;
	private Utilities util;
	
	public Login(SoftAssertion myAssert) {
		this.myAssert = myAssert;
		util = new Utilities(myAssert);
	}
	
	public boolean login(String userName, String password ) {
		try {
			myAssert.Success("Logged in");
			ImasisLoginAction(userName,password);
			return true;
		}catch(Exception e) {
			myAssert.Failed("Unable to login");
			return false;
		}
	}
	
	
	private boolean ImasisLoginAction(String userName, String password) {
		WebDriver driver = SeleniumDriver.getInstance().getWebDriver();
				
		try {
			WebElement userId = CustomizedActions.getElement(By.id("login-username"),myAssert);
			if(userId!= null){
				userId.clear();
				userId.sendKeys(userName.trim());
			}
			
			WebElement loginButton = CustomizedActions.getElement(By.id("login-signin"),myAssert);
			if(loginButton!= null){
				loginButton.click();
				util.syncBrowser();
			}
			
			WebElement pwd = CustomizedActions.getElement(By.id("login-passwd"),myAssert);
			if(pwd!= null){
				pwd.clear();
				pwd.sendKeys(password.trim());
			}
			
			loginButton = CustomizedActions.getElement(By.id("login-signin"),myAssert);
			if(loginButton!= null){
				loginButton.click();
				util.syncBrowser();
			}
			
			
			String message = "Succesfully entered value: " + userId + " and Successfully clicked login ";
			myAssert.Success(message);
			return true;
		} catch (Exception e) {
			myAssert.fail("Unable to login");
			return false;
		}
	}
}

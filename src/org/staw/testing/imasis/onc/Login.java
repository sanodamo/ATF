package org.staw.testing.imasis.onc;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.staw.framework.SeleniumWrapper;
import org.staw.framework.SeleniumDriver;
import org.staw.framework.SoftAssertion;


public class Login {
	private static SoftAssertion myAssert;
	
	
	public Login(SoftAssertion myAssert) {
		this.myAssert = myAssert;	
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
			WebElement userId = SeleniumWrapper.getElement(By.id("login-username"),myAssert);
			if(userId!= null){
				userId.clear();
				userId.sendKeys(userName.trim());
			}
			
			WebElement loginButton = SeleniumWrapper.getElement(By.id("login-signin"),myAssert);
			if(loginButton!= null){
				loginButton.click();
				SeleniumWrapper.syncBrowser();
			}
			
			WebElement pwd = SeleniumWrapper.getElement(By.id("login-passwd"),myAssert);
			if(pwd!= null){
				pwd.clear();
				pwd.sendKeys(password.trim());
			}
			
			loginButton = SeleniumWrapper.getElement(By.id("login-signin"),myAssert);
			if(loginButton!= null){
				loginButton.click();
				SeleniumWrapper.syncBrowser();
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

package org.staw.testing.imasis.onc;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.staw.framework.SeleniumWrapper;
import org.staw.framework.SeleniumDriver;
import org.staw.framework.SoftAssertion;
import org.staw.testing.imasis.constants.DomConstants;


public class Login {
	private static SoftAssertion myAssert;
	
	
	public Login(SoftAssertion myAssert) {
		this.myAssert = myAssert;	
	}
	
	public boolean login(String userName, String password ) {
		try {			
			return ImasisLoginAction(userName,password);			
		}catch(Exception e) {
			myAssert.Failed("Unable to login");
			return false;
		}
	}
	
	
	private boolean ImasisLoginAction(String userName, String password) {
		WebDriver driver = SeleniumDriver.getInstance().getWebDriver();
				
		try {
			WebElement userId = SeleniumWrapper.getElement(By.id(DomConstants.Login.IMASIS_LOGIN_USERNAME),myAssert);
			if(userId!= null){
				userId.clear();
				userId.sendKeys(userName.trim());
			}
			
			WebElement pwd = SeleniumWrapper.getElement(By.id(DomConstants.Login.IMASIS_LOGIN_PASSWORD),myAssert);
			if(pwd!= null){
				pwd.clear();
				pwd.sendKeys(password.trim());
			}
			
			WebElement loginButton = SeleniumWrapper.getElement(By.cssSelector(DomConstants.Login.IMASIS_LOGIN_SUBMIT),myAssert);
			if(loginButton!= null){
				loginButton.click();						
			}
			SeleniumWrapper.syncBrowser();
			
			String message = "Succesfully entered value: " + userId + " and Successfully clicked login ";
			myAssert.Success(message);
			return true;
		} catch (Exception e) {
			myAssert.fail("Unable to login");
			return false;
		}
	}
}

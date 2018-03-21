package org.staw.testing.imasis.onc;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.staw.framework.SeleniumWrapper;
import org.staw.framework.SoftAssertion;
import org.staw.framework.helpers.StringExtensions;
import org.staw.testing.imasis.constants.DomConstants;


public class Call {
	
	private static SoftAssertion myAssert;
	
	
	public Call(SoftAssertion myAssert) {
		this.myAssert = myAssert;
		
	}
	
	public boolean CallAction(String action, String val) {				
		boolean isSuccess = true;		
		switch(StringExtensions.removeSpace(action)) {
			case "startcall":
				isSuccess = SeleniumWrapper.clickOnElement(By.cssSelector(DomConstants.Call.START_CALL), myAssert);					
		}	
		SeleniumWrapper.syncBrowser();
		return isSuccess;	
	}
	
	public boolean FillCallerDetails(String callType, String firstName, String lastName, String location, String phone,
			String extension, String unableToContract, String comments) {
			SeleniumWrapper.syncBrowser();			
			boolean isValid = true;
			isValid = isValid && SeleniumWrapper.fillTextFieldAndTabOut(By.id(DomConstants.Call.FIRST_NAME), firstName, "firstName", myAssert);
			isValid = isValid && SeleniumWrapper.fillTextFieldAndTabOut(By.id(DomConstants.Call.LAST_NAME), lastName, "lastName", myAssert);
			isValid = isValid && SeleniumWrapper.fillTextFieldAndTabOut(By.id(DomConstants.Call.PHONE), phone, "phone", myAssert);
			
			return isValid;		
	}

}

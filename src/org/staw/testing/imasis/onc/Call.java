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
	
	public boolean FillCallerDetails(String action, String value) {
		try {
			SeleniumWrapper.syncBrowser();			
			switch(StringExtensions.removeSpace(action)) {
				case "firstname":
					return SeleniumWrapper.fillTextFieldAndTabOut(By.id(DomConstants.Call.FIRST_NAME), value, "firstName", myAssert);
				case "lastname":
					return SeleniumWrapper.fillTextFieldAndTabOut(By.id(DomConstants.Call.LAST_NAME), value, "lastName", myAssert);
				case "phone":
					return SeleniumWrapper.fillTextFieldAndTabOut(By.id(DomConstants.Call.PHONE), value, "phone", myAssert);					
			}				
			
			return myAssert.Failed("Unable to add caller information");
		}
		catch(Exception e) {
			return myAssert.Failed("Unable to add caller information");
		}
				
	}

}

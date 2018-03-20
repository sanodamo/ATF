package org.staw.testing.imasis.onc;

import org.openqa.selenium.By;
import org.staw.framework.SeleniumWrapper;
import org.staw.framework.SoftAssertion;
import org.staw.framework.helpers.StringExtensions;
import org.staw.testing.imasis.constants.DomConstants;


public class Physician {
	
	private static SoftAssertion myAssert;
	
	
	public Physician(SoftAssertion myAssert) {
		this.myAssert = myAssert;
		
	}
	
	public boolean physicianAction(String action, String val) {
		return true;
	}
	
	public boolean PhysicianSearchByNPI(String state, String search) {		
		
		boolean isSuccess = SeleniumWrapper.selectByValue(By.cssSelector(DomConstants.Physician.SEARCH_BY), "NPI", "search by", myAssert);
		SeleniumWrapper.syncBrowser();
		isSuccess = isSuccess && SeleniumWrapper.fillTextFieldAndTabOut(By.id(DomConstants.Physician.SEARCH_NPI), search, "NPI", myAssert);
		isSuccess = isSuccess && SeleniumWrapper.selectByValue(By.id(DomConstants.Physician.SEARCH_NPI_STATE), state, "physician state", myAssert);
		isSuccess = isSuccess && SeleniumWrapper.clickOnElement(By.cssSelector(DomConstants.Physician.SUBMIT_NPI_SEARCH), myAssert);
		SeleniumWrapper.syncBrowser();
		return isSuccess;
	}

	public boolean PhysicianSelect(String action) {
		if(StringExtensions.removeSpace(action).equalsIgnoreCase("activephysician")) {
			return SeleniumWrapper.clickOnElementByIndex(0,By.xpath(DomConstants.Physician.PHYSICIAN_LINK) ,"first physician", myAssert);
		}
		return false;
	}
}

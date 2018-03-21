package org.staw.testing.imasis.onc;

import org.openqa.selenium.By;
import org.staw.framework.SeleniumWrapper;
import org.staw.framework.SoftAssertion;
import org.staw.framework.helpers.StringExtensions;
import org.staw.testing.imasis.constants.DomConstants;

public class Provider {
	private static SoftAssertion myAssert;
	
	
	public Provider(SoftAssertion myAssert) {
		this.myAssert = myAssert;
		
	}
	
	
	public boolean ProviderSearchByNPI(String state, String search) {	
		
		boolean isSuccess = SeleniumWrapper.selectByValue(By.cssSelector(DomConstants.Provider.SEARCH_BY), "NPI", "search by", myAssert);
		SeleniumWrapper.syncBrowser();
		isSuccess = isSuccess && SeleniumWrapper.fillTextFieldAndTabOut(By.id(DomConstants.Provider.SEARCH_NPI), search, "NPI", myAssert);
		
		isSuccess = isSuccess && SeleniumWrapper.selectByValue(By.xpath(DomConstants.Provider.SEARCH_NPI_STATE), state, "physician state", myAssert);
		isSuccess = isSuccess && SeleniumWrapper.clickOnElement(By.cssSelector(DomConstants.Provider.SUBMIT_NPI_SEARCH), myAssert);
		SeleniumWrapper.syncBrowser();
		return isSuccess;
	}

	public boolean ProviderSelect(String action) {
		if(StringExtensions.removeSpace(action).equalsIgnoreCase("contractedprovider")) {
			return SeleniumWrapper.clickOnElementByIndex(0,By.xpath(DomConstants.Provider.PROVIDER_LINK) ,"first provider", myAssert);
		}
		return false;
	}
}

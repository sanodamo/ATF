package org.staw.testing.imasis.onc;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.staw.framework.SeleniumWrapper;
import org.staw.framework.SoftAssertion;
import org.staw.framework.helpers.DateExtension;
import org.staw.framework.helpers.StringExtensions;
import org.staw.testing.imasis.constants.DomConstants;


public class Member {
	
	private static SoftAssertion myAssert;
	
	
	public Member(SoftAssertion myAssert) {
		this.myAssert = myAssert;
	
	}
	
	public boolean MemberSearch(String client, String memberid) {		
		boolean isSuccess = SeleniumWrapper.selectByValue(By.id(DomConstants.Member.CLIENT_ID), client, "client", myAssert);
		if(isSuccess) {
			SeleniumWrapper.syncBrowser();
			isSuccess = SeleniumWrapper.fillTextFieldAndTabOut(By.id(DomConstants.Member.SUBSCRIBER_SSN), memberid, "Member Id", myAssert);
			if(isSuccess) {
				isSuccess = SeleniumWrapper.clickOnElement(By.cssSelector(DomConstants.Member.SEARCH_SUBMIT), myAssert);
				SeleniumWrapper.syncBrowser();
			}			
		}
		
		return isSuccess;
	}

	public boolean MemberSelect(String action) {
		if(StringExtensions.removeSpace(action).equalsIgnoreCase("activemember")) {
			return SeleniumWrapper.clickOnElementByIndex(0,By.cssSelector(DomConstants.Member.MEMBER_LINK) ,"first member", myAssert);
		}
		return false;
	}
	
	public boolean ProductSelection(String product) {
		
		if(product.equalsIgnoreCase("rad")) {
			return SeleniumWrapper.clickOnElement(By.cssSelector(DomConstants.Product.RAD_PRODUCT_SELECTION), myAssert);
		}
		return false;
	}
	
	public boolean TreatmentDateSelection(String val) {
		
		String dateOfService = DateExtension.CurrentDate();
		String planningDate= DateExtension.CurrentDate();
		if(val.equalsIgnoreCase("prospective")) {			
		}			
		boolean isSuccess = SeleniumWrapper.fillTextFieldAndTabOut(By.id(DomConstants.TreatmentDates.DATE_OF_SERVICE), dateOfService, "Date of service", myAssert);
		SeleniumWrapper.syncBrowser();
		if(isSuccess)
			isSuccess = SeleniumWrapper.fillTextFieldAndTabOut(By.id(DomConstants.TreatmentDates.PLANNING_DATE), planningDate, "Planning Date", myAssert);
		
		return isSuccess;
	}
	
}

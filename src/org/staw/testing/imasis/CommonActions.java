package org.staw.testing.imasis;

import org.openqa.selenium.By;
import org.staw.framework.SeleniumWrapper;
import org.staw.framework.SoftAssertion;
import org.staw.testing.imasis.constants.DomConstants;

public class CommonActions {
private static SoftAssertion myAssert;
	
	
	public CommonActions(SoftAssertion myAssert) {
		this.myAssert = myAssert;	
	}
	
	public boolean SwitchTab(String tabName ) {
		boolean isSuccess = false;
		try {
			switch(tabName.toLowerCase()) {
				case "member":
					isSuccess = SeleniumWrapper.clickOnLink(By.id(DomConstants.Tabs.MEMBER), "member tab", myAssert);
					break;
				case "physician":
					isSuccess = SeleniumWrapper.clickOnLink(By.id(DomConstants.Tabs.PHYSICIAN), "physician tab", myAssert);
					break;
				case "provider":
					isSuccess = SeleniumWrapper.clickOnLink(By.id(DomConstants.Tabs.PROVIDER), "provider tab", myAssert);
					break;
				case "modality":
					isSuccess = SeleniumWrapper.clickOnLink(By.id(DomConstants.Tabs.MODALITY), "modality tab", myAssert);
					break;
				case "clinical":
					isSuccess = SeleniumWrapper.clickOnLink(By.id(DomConstants.Tabs.CLINICAL), "clinical tab", myAssert);
					break;
				case "drugsummary":
					SeleniumWrapper.syncBrowser();
					SeleniumWrapper.syncBrowser();
					SeleniumWrapper.syncBrowser();
					SeleniumWrapper.syncBrowser();
					isSuccess = SeleniumWrapper.clickOnLink(By.id(DomConstants.Tabs.DRUGSUMMARY), "DrugSummary tab", myAssert);
					break;
					
				default:
					return myAssert.Failed("Unable to find " + tabName + " element");
					
			}
		} catch(Exception e) {
			return myAssert.Failed("Unable to click " + tabName + " element");
		}
							
		SeleniumWrapper.syncBrowser();
		return isSuccess;
	}
	
	public boolean CaseActions(String option) {
		try {
			SeleniumWrapper.syncBrowser();
			SeleniumWrapper.syncBrowser();
			switch(option.toLowerCase()) {
				case "save":
					return SeleniumWrapper.clickOnLink(By.xpath(DomConstants.CaseActions.SAVE), "Save Case", myAssert);					
				case "close":
					return SeleniumWrapper.clickOnLink(By.xpath(DomConstants.CaseActions.CLOSE), "Close Case", myAssert);					
				default:
					return myAssert.Failed("Unable to click " + option + " element");
			}
		}catch(Exception e) {
			return myAssert.Failed("Unable to click " + option + " element");
		}			
		
	}
	
	public boolean SwitchFrame(String frame) {
		By frameLocator = null;
		SeleniumWrapper.syncBrowser();
		switch(frame.toLowerCase()) {
			case "eviti":
				frameLocator = By.cssSelector("iframe.evitiIframe");
				break;
		}
		return SeleniumWrapper.switchFrame(frame, frameLocator,myAssert);
	}
	
}

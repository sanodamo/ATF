package org.staw.testing.imasis.onc;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.staw.framework.SeleniumDriver;
import org.staw.framework.SeleniumWrapper;
import org.staw.framework.SoftAssertion;
import org.staw.framework.constants.GlobalConstants.UtilitiesConstants;
import org.staw.framework.helpers.StringExtensions;
import org.staw.testing.imasis.constants.DomConstants;

public class Clinical {
	private static SoftAssertion myAssert;
	
	
	public Clinical(SoftAssertion myAssert) {
		this.myAssert = myAssert;
		
	}
	
	
	public boolean APClinicalQuestion(String question, String answer,String controlType) {
		return ApConversation(question,answer,controlType);		
	}
	
	public boolean APTreatmentQuestion(String question, String answer,String controlType) {
		return ApConversation(question,answer,controlType);		
	}
	
	public boolean APClinicalComplete(String element) {
		try {
			WebDriver driver = SeleniumDriver.getInstance().getWebDriver();
			WebElement next =  SeleniumWrapper.getElement(By.xpath(DomConstants.RadClinical.CLINICAL_NEXT_BUTTON), myAssert);
			SeleniumWrapper.javaScriptExecutorClickElement(driver, myAssert,UtilitiesConstants.JAVASCRIPT_CLICK_ELEMENT, next);
			SeleniumWrapper.syncBrowser();
			return myAssert.Success("Successfully clicked " + element + " element");
		}catch(Exception e) {
			return myAssert.Failed("Unable to click " + element + " element");
		}
			
	}
	
	public boolean ApReviewConfirm(String elementName) {
		try {
			WebDriver driver = SeleniumDriver.getInstance().getWebDriver();
			WebElement elm =  SeleniumWrapper.getElement(By.xpath(DomConstants.RadClinical.CLINICAL_CONTINUE_BUTTON), myAssert);
			SeleniumWrapper.javaScriptExecutorClickElement(driver, myAssert,UtilitiesConstants.JAVASCRIPT_CLICK_ELEMENT, elm);

			SeleniumWrapper.syncBrowser();
			return myAssert.Success("Successfully clicked " + elementName + " element");
		}catch(Exception e) {
			return myAssert.Failed("Unable to click " + elementName + " element");
		}
	}
	
	
	private boolean ApConversation(String question, String answer, String controlType) {
		WebDriver driver = SeleniumDriver.getInstance().getWebDriver();
		boolean isSuccess = true;
		try {
			List<WebElement> questionContainers = SeleniumWrapper.getElements(By.xpath(DomConstants.RadClinical.AP_QUESTION_CONTAINER), myAssert);		
			for (WebElement questionContainer : questionContainers) {
				WebElement que =  SeleniumWrapper.getElement(questionContainer, By.cssSelector(DomConstants.RadClinical.AP_QUESTION_CAPTION), myAssert);				
				if (StringExtensions.startWith(que.getAttribute("caption"),question)) {
					if(controlType.equalsIgnoreCase("radio")) {
						List<WebElement> answers = SeleniumWrapper.getElements(questionContainer, By.cssSelector(DomConstants.RadClinical.AP_RADIO_BUTTON_ANSWER), myAssert);
						for (WebElement ans : answers) {
							if (StringExtensions.startWith(ans.getText(),answer)) {
								isSuccess = SeleniumWrapper.javaScriptExecutorClickElement(driver, myAssert,UtilitiesConstants.JAVASCRIPT_CLICK_ELEMENT, ans);
								SeleniumWrapper.syncBrowser();
								return myAssert.Success("Successfully answered " + question + " element");
							}
						}
					}
					else if (controlType.equalsIgnoreCase("text")) {												
						isSuccess = SeleniumWrapper.fillTextField(questionContainer, By.cssSelector(DomConstants.RadClinical.AP_TEXTBOX_ANSWER), answer, question, false, myAssert);
						SeleniumWrapper.syncBrowser();
						return isSuccess;
					}
				}
			}
			
			
			
		} catch (Exception e) {
			return myAssert.Failed("Unable to answer " + question + " element");
		}
		return myAssert.Failed("Unable to answer " + question);		
	}
	
	
}

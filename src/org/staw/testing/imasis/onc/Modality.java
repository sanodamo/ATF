package org.staw.testing.imasis.onc;

import org.openqa.selenium.By;
import org.staw.framework.SeleniumWrapper;
import org.staw.framework.SoftAssertion;
import org.staw.framework.helpers.StringExtensions;
import org.staw.testing.imasis.constants.DomConstants;

public class Modality {
	private static SoftAssertion myAssert;
	
	
	public Modality(SoftAssertion myAssert) {
		this.myAssert = myAssert;
		
	}
	
	
	public boolean TreatmentSelection(String cpt, String category) {
		boolean isSuccess = true;
		try {
			switch(StringExtensions.removeSpace(category)) {
				case "primary":
					isSuccess = SeleniumWrapper.selectByValue(By.id(DomConstants.Modality.PRIMARY_TREATMENT), cpt, "primary treatment", myAssert);
					break;
			}
		}catch(Exception e) {
			return myAssert.Failed("Unable to find " + cpt + " element");
		}
		SeleniumWrapper.syncBrowser();		
		return isSuccess;
	}
	
	public boolean PreExamQuestions(String preExamQuestion, String cancerType, String cancerSubType, String pathology, 
			String tnmStage, String stage, String treatment, String goal, String performance)
	{
		boolean isSuccess = true;
		
		try {
			switch(StringExtensions.removeSpace(preExamQuestion)) {
				case "primarytumor":
					isSuccess = SeleniumWrapper.clickOnElement(By.xpath(DomConstants.Modality.PRIMARY_TUMOR), myAssert);
					break;
			}
			
			SeleniumWrapper.syncBrowser();
			
			isSuccess = isSuccess && SeleniumWrapper.clickOnElement(By.id(DomConstants.Modality.CANCER_TYPE_SELECT), myAssert);		
			isSuccess = isSuccess && SeleniumWrapper.clickOnListElement(cancerType, By.xpath(DomConstants.Modality.CANCER_TYPE_OPTIONS), myAssert);
					
			SeleniumWrapper.syncBrowser();
			
			isSuccess = isSuccess && SeleniumWrapper.clickOnElement(By.id(DomConstants.Modality.CANCER_SUBTYPE_SELECT), myAssert);		
			isSuccess = isSuccess && SeleniumWrapper.clickOnListElement(cancerSubType, By.xpath(DomConstants.Modality.CANCER_SUBTYPE_OPTIONS), myAssert);
			
			SeleniumWrapper.syncBrowser();
			
			isSuccess = isSuccess && SeleniumWrapper.clickOnElement(By.id(DomConstants.Modality.PATHOLOGY_SELECT), myAssert);		
			isSuccess = isSuccess && SeleniumWrapper.clickOnListElement(pathology, By.xpath(DomConstants.Modality.PATHOLOGY_TYPE_OPTIONS), myAssert);
			
			SeleniumWrapper.syncBrowser();
			
			if(tnmStage.equalsIgnoreCase("stage")) {
				isSuccess = isSuccess && SeleniumWrapper.clickOnElement(By.xpath(DomConstants.Modality.STAGING_RADIO), myAssert);
			}
			
			SeleniumWrapper.syncBrowser();
			
			isSuccess = isSuccess && SeleniumWrapper.clickOnElement(By.id(DomConstants.Modality.STAGING_SELECT), myAssert);		
			isSuccess = isSuccess && SeleniumWrapper.clickOnListElement(stage, By.xpath(DomConstants.Modality.STAGING_OPTIONS), myAssert);
					
			SeleniumWrapper.syncBrowser();
			
			isSuccess = isSuccess && SeleniumWrapper.clickOnElement(By.id(DomConstants.Modality.TREATMENT_SELECT), myAssert);		
			isSuccess = isSuccess && SeleniumWrapper.clickOnListElement(treatment, By.xpath(DomConstants.Modality.TREATMENT_OPTIONS), myAssert);
					
			SeleniumWrapper.syncBrowser();
			
			isSuccess = isSuccess && SeleniumWrapper.clickOnElement(By.id(DomConstants.Modality.GOAL_SELECT), myAssert);		
			isSuccess = isSuccess && SeleniumWrapper.clickOnListElement(goal, By.xpath(DomConstants.Modality.GOAL_OPTIONS), myAssert);
					
			SeleniumWrapper.syncBrowser();
			
			isSuccess = isSuccess && SeleniumWrapper.clickOnElement(By.id(DomConstants.Modality.PERFROMACE_STATUS_SELECT), myAssert);		
			isSuccess = isSuccess && SeleniumWrapper.clickOnListElement(performance, By.xpath(DomConstants.Modality.PERFROMACE_STATUS_OPTIONS), myAssert);
					
			SeleniumWrapper.syncBrowser();
			
			isSuccess = isSuccess && SeleniumWrapper.clickOnElement(By.xpath(DomConstants.Modality.SAVE_ACTION), myAssert);
			SeleniumWrapper.syncBrowser();
		}
		catch(Exception e) {
			return myAssert.Failed("Unable to complete Pre Exam Questions");
		}
		return isSuccess;
	}
	
}

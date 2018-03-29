package org.staw.testing.imasis;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
			SeleniumWrapper.syncBrowser();
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
	
	
	public boolean evitiStart(String name) {		
		try {
			SeleniumWrapper.clickOnLink(By.xpath(DomConstants.MocClinical.CLINICAL_START_BUTTON), "Start Clinical", myAssert);
			SeleniumWrapper.syncBrowser();
			return myAssert.Success("Successfully started clinical");
		} catch (Exception e) {
			return myAssert.Failed("Unable to start clinical");
		}		
		
	}
	
	public boolean patientHeightWeight(String height, String weight) {		
		try {
			SeleniumWrapper.syncBrowser();
			SeleniumWrapper.fillTextFieldAndTabOut(By.id(DomConstants.MocClinical.PATIENT_HEIGHT), height, "patient height", myAssert);
			SeleniumWrapper.fillTextFieldAndTabOut(By.id(DomConstants.MocClinical.PATIENT_WEIGHT), weight, "patient weight", myAssert);
			SeleniumWrapper.clickOnLink(By.id(DomConstants.MocClinical.SAVE_AND_CONTINUE), "Save and continue", myAssert);
			SeleniumWrapper.syncBrowser();
			
			return myAssert.Success("Successfully added height " +height + " weight " +  weight );
		} catch (Exception e) {
			return myAssert.Failed("Unable to add height & weight");
		}		
		
	}
	
	public boolean regimenSearch(String action, String value) {
		boolean isSuccess = false;
		try {
			SeleniumWrapper.syncBrowser();
			
			switch(StringExtensions.removeSpace(action)) {
				case "cancertype":					
					return selectListItem(value, "cancer type", By.xpath(DomConstants.MocClinical.CANCER_TYPE_SELECT),By.xpath(DomConstants.MocClinical.CANCER_TYPE_OPTIONS) );
					
				case "icd":
					return selectListItem(value,"icd", By.xpath(DomConstants.MocClinical.ICD_SELECT),By.xpath(DomConstants.MocClinical.ICD_OPTIONS) );
			}						
								
		} catch (Exception e) {
			return myAssert.Failed("Unable to select regimen");
		}
		
		return isSuccess;
	}
	
	public boolean selectDrug(String name) {
		try {
			SeleniumWrapper.syncBrowser();
			return SeleniumWrapper.clickOnListElement(name,By.xpath(DomConstants.MocClinical.DRUG_NAME_CLICK) , myAssert);
		} catch (Exception e) {
			return myAssert.Failed("Unable to remove drug");
		}		
	} 
	
	public boolean buildTreatmentPlan(String dose, String route, String subroute, String cycle, String cycleLen, String daysOfCycle, String freqPerDay ) {
		boolean isSuccess = false;
		try {
			
			SeleniumWrapper.syncBrowser();
			isSuccess = incrementInputBox(dose, "dose", By.xpath(DomConstants.MocClinical.EDIT_DOSE), By.xpath(DomConstants.MocClinical.DOSE_INPUT));
			
			isSuccess = isSuccess && selectListItem(route,"route", By.xpath(DomConstants.MocClinical.ROUTE_CLICK),By.xpath(DomConstants.MocClinical.ROUTE_OPTION) );
			
			SeleniumWrapper.syncBrowser();
			
			isSuccess = isSuccess && selectListItem(subroute,"subroute", By.xpath(DomConstants.MocClinical.SUBROUTE_CLICK),By.xpath(DomConstants.MocClinical.SUBROUTE_OPTION) );
			
			isSuccess = isSuccess && SeleniumWrapper.fillTextFieldAndTabOut(By.id(DomConstants.MocClinical.CYCLE_TEXT), cycle, "cycle", myAssert);
			
			isSuccess = isSuccess && incrementInputBox(cycleLen, "cycle Len", By.xpath(DomConstants.MocClinical.CYCLE_LEN_CLICK), By.id(DomConstants.MocClinical.CYCLE_LEN_INPUT));
			
			isSuccess = isSuccess && SeleniumWrapper.fillTextFieldAndTabOut(By.id(DomConstants.MocClinical.DAYS_CYCLE_INPUT), daysOfCycle, "daysOfCycle", myAssert);
			
			isSuccess = isSuccess && selectListItem(freqPerDay,"freqPerDay", By.xpath(DomConstants.MocClinical.FREQ_PER_DAY_SELECT),By.xpath(DomConstants.MocClinical.FREQ_PER_DAY_OPTION) );
			
			isSuccess = isSuccess && SeleniumWrapper.clickOnLink(By.id(DomConstants.MocClinical.BACK_FROM_EDIT_DRUG), "back from edit drug", myAssert);
			
			SeleniumWrapper.syncBrowser();
			
			isSuccess = isSuccess && SeleniumWrapper.clickOnLink(By.xpath(DomConstants.MocClinical.SAVE_DRUG_CONTINUE), "save and continue", myAssert);
			
			SeleniumWrapper.syncBrowser();
			
			return isSuccess;
			
		} catch (Exception e) {
			return myAssert.Failed("Unable to build treatment plan");
		}
	}
	
	public boolean enterDiagnosis(String pathology, String stage, String icd, String lineOfTreat, String goalOfTreat, String performStatus) {
		boolean isSuccess = false;
		
		try {
			isSuccess = selectListItem(pathology,"pathology", By.xpath(DomConstants.MocClinical.PATHOLOGY_SELECT),By.xpath(DomConstants.MocClinical.PATHOLOGY_OPTIONS) );
			SeleniumWrapper.syncBrowser();
			
			isSuccess = isSuccess && selectListItem(stage,"stage", By.xpath(DomConstants.MocClinical.STAGE_SELECT),By.xpath(DomConstants.MocClinical.STAGE_OPTIONS) );
			SeleniumWrapper.syncBrowser();
			
			isSuccess = isSuccess && selectListItem(icd,"icd", By.xpath(DomConstants.MocClinical.ICDCODE_SELECT),By.xpath(DomConstants.MocClinical.ICDCODE_OPTIONS) );
			SeleniumWrapper.syncBrowser();
			
			isSuccess = isSuccess && selectListItem(lineOfTreat,"lineOfTreat", By.xpath(DomConstants.MocClinical.LINE_OF_TREATMENT_SELECT),By.xpath(DomConstants.MocClinical.LINE_OF_TREATMENT_OPTIONS) );
			SeleniumWrapper.syncBrowser();
			
			isSuccess = isSuccess && selectListItem(goalOfTreat,"goalOfTreat", By.xpath(DomConstants.MocClinical.GOAL_OF_TREATMENT_SELECT),By.xpath(DomConstants.MocClinical.GOAL_OF_TREATMENT_OPTIONS) );
			SeleniumWrapper.syncBrowser();
			
			isSuccess = isSuccess && selectListItem(performStatus,"performStatus", By.xpath(DomConstants.MocClinical.PERF_STATUS_SELECT),By.xpath(DomConstants.MocClinical.PERF_STATUS_OPTIONS) );
			SeleniumWrapper.syncBrowser();
			
			return isSuccess;
			
		} catch (Exception e) {
			return myAssert.Failed("Unable to enter diagnosis");
		}
	}
	
	public boolean enterBiomarker(String biomarker, String value) {
		boolean isSuccess = false;
		try {
			switch(StringExtensions.removeSpace(biomarker)) {
				case "estrogenreceptor":
					isSuccess = selectListItem(value,biomarker, By.xpath(DomConstants.MocClinical.ESTROGEN_RECEPTOR_SELECT),By.xpath(DomConstants.MocClinical.BIOMARKER_OPTIONS) );
					break;
				case "her2":
					isSuccess = selectListItem(value,biomarker, By.xpath(DomConstants.MocClinical.HER2_SELECT),By.xpath(DomConstants.MocClinical.BIOMARKER_OPTIONS) );
					break;	
				case "menopausal":
					isSuccess = selectListItem(value,biomarker, By.xpath(DomConstants.MocClinical.MENOPAUSAL_SELECT),By.xpath(DomConstants.MocClinical.BIOMARKER_OPTIONS) );
					break;
				case "progesterone":
					isSuccess = selectListItem(value,biomarker, By.xpath(DomConstants.MocClinical.PROGESTERONE_SELECT),By.xpath(DomConstants.MocClinical.BIOMARKER_OPTIONS) );
					break;	
					
			}
			
			return isSuccess;
			
		} catch (Exception e) {
			return myAssert.Failed("Unable to enter biomarker");
		}
	}
	
	public boolean saveAndContinueEviti(String value) {
		boolean isSuccess = false;
		SeleniumWrapper.syncBrowser();
		try {
			switch(StringExtensions.removeSpace(value)) {
			case "regimen":
				isSuccess = SeleniumWrapper.clickOnLink(By.id(DomConstants.MocClinical.REGIMEN_SEARCH_DONE), "Save and continue regimen", myAssert);
				break;
			case "diagnosis":
				isSuccess = SeleniumWrapper.clickOnLink(By.id(DomConstants.MocClinical.SAVE_DIAGNOSIS), "save diagnosis", myAssert);
				break;
			case "review":
				isSuccess = SeleniumWrapper.clickOnLink(By.id(DomConstants.MocClinical.SAVE_AND_CONTINUE), "save and continue", myAssert);
				break;
			case "complete":
				isSuccess = SeleniumWrapper.clickOnLink(By.id(DomConstants.MocClinical.COMPLETE_CLINICAL), "complete clinical", myAssert);
				break;
			}			
			
			return isSuccess;
			
		} catch (Exception e) {
			return myAssert.Failed("Unable to save and continue");
		}
	}
	
	public boolean removeDrug(String name) {
		try {
			SeleniumWrapper.syncBrowser();
			return SeleniumWrapper.clickOnListElement(name,By.xpath(DomConstants.MocClinical.REMOVE_DRUG) , myAssert);
		} catch (Exception e) {
			return myAssert.Failed("Unable to remove drug");
		}
		
	}
	
	public boolean addDrug(String name) {
		try {
			SeleniumWrapper.syncBrowser();
			return SeleniumWrapper.fillTextField(By.id(DomConstants.MocClinical.HCPCS), name, "HCPCS",Keys.ENTER, myAssert);
		} catch (Exception e) {
			return myAssert.Failed("Unable to remove drug");
		}
		
	}
	
	public boolean EditRegimen(String action) {
		try {
			return SeleniumWrapper.clickOnLink(By.xpath(DomConstants.MocClinical.EDIT_OUTCOME), "click edit outcome", myAssert);			
		} catch (Exception e) {
			return myAssert.Failed("Unable to edit regimen");
		}
		
	}
	public boolean EditOutcome(String action) {
		boolean isSuccess = false;
		SeleniumWrapper.syncBrowser();
		try {
			switch(StringExtensions.removeSpace(action)) {
				case "met":					
					return  SeleniumWrapper.clickOnLink(By.xpath(DomConstants.MocClinical.MET_BUTTON), "click met outcome", myAssert);	
				default:
					return myAssert.Failed("Unable to edit outcome");	
			}
		} catch (Exception e) {
			return myAssert.Failed("Unable to edit outcome");
		}
		
	}
	
	private boolean incrementInputBox(String text, String message, By label, By inputElement) {
		boolean isSuccess = false;
		try {
			isSuccess = SeleniumWrapper.clickOnElement(label, myAssert);
			SeleniumWrapper.syncBrowser();
			if(isSuccess) {
				return SeleniumWrapper.fillTextFieldAndTabOut(inputElement, text,message, myAssert);
			}
			
			return myAssert.Failed("Unable to select " + message);
		} catch (Exception e) {
			return myAssert.Failed("Unable to select " + message);
		}	
	}
	
	private boolean selectListItem(String text, String message, By label, By options) {
		boolean isSuccess = false;
		try {
			isSuccess = SeleniumWrapper.clickOnElement(label, myAssert);
			SeleniumWrapper.syncBrowser();
			if(isSuccess) {
				return SeleniumWrapper.clickOnListElement(text,options , myAssert);
			}			
			SeleniumWrapper.syncBrowser();
			return myAssert.Failed("Unable to select " + message);
		} catch (Exception e) {
			return myAssert.Failed("Unable to select " + message);
		}	
	}
	

}

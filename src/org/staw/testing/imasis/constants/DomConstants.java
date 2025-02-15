package org.staw.testing.imasis.constants;

public class DomConstants {
	public class Login{
		public static final String IMASIS_LOGIN_USERNAME = "UserName";
		public static final String IMASIS_LOGIN_PASSWORD = "Password";
		public static final String IMASIS_LOGIN_SUBMIT = "input.submit";
	}
	
	public class Call{
		public static final String START_CALL = "input.submit.start-call";	
		public static final String FIRST_NAME = "PartyFirstName";
		public static final String LAST_NAME = "PartyLastName";
		public  static final String PHONE = "CallBackNumber";
	}
	
	public class Member{
		
		public static final String CLIENT_ID = "ClientId";
		public static final String SUBSCRIBER_SSN = "SubscriberSSN";	
		public static final String SEARCH_SUBMIT = "form.trigger-auto-submit-then-form-submit input.submit";
		public static final String MEMBER_LINK = "a.trigger-onc-select-validation-then-link-get";
	}
	
	public class Physician{
		public static final String SEARCH_BY = "p.extraneous-paragraph-surrounding-switcher select";
		public static final String SEARCH_NPI = "NPI";
		public static final String SEARCH_NPI_STATE = "NPIState";
		public static final String SUBMIT_NPI_SEARCH ="div.npi input.submit";
		
		public static final String PHYSICIAN_LINK = "//div[@id='physician-div-content']/table/tbody/tr/td/a";
	}
		
	public class Provider{
		public static final String SEARCH_BY = "p.extraneous-paragraph-surrounding-switcher select";
		public static final String SEARCH_NPI = "NPI";
		public static final String SEARCH_NPI_STATE = "//div[@class='provider-npi']/form/fieldset/div/select[@id='State']";
		public static final String SUBMIT_NPI_SEARCH ="div.provider-npi input.submit";
		
		public static final String PROVIDER_LINK = "//div[@id='provider-search-results']/table/tbody/tr/td/a";
		
		public static final String PLACE_OF_SERVICE = "//select[@id='placeofservice']";
	}
	
	public class Modality{
		public static final String PRIMARY_TREATMENT = "primary-treatment";
		public static final String PRIMARY_TUMOR ="//input[@name='PreExamQuestions.DiagnosisCategory.Id' and @value='2']";
		
		public static final String CANCER_TYPE_SELECT = "cancer-type-selectized";
		public static final String CANCER_TYPE_OPTIONS = "//select[@id='cancer-type']/following-sibling::div//div[@class='selectize-dropdown-content']/div";
				
		public static final String CANCER_SUBTYPE_SELECT = "cancer-sub-type-selectized";
		public static final String CANCER_SUBTYPE_OPTIONS = "//select[@id='cancer-sub-type']/following-sibling::div//div[@class='selectize-dropdown-content']/div";
		
		public static final String PATHOLOGY_SELECT = "pathology-selectized";
		public static final String PATHOLOGY_TYPE_OPTIONS = "//select[@id='pathology']/following-sibling::div//div[@class='selectize-dropdown-content']/div";
		
		public static final String STAGING_RADIO = "//input[@name='PreExamQuestions.TNMOrStage' and @value='Stage' ]";
		
		public static final String STAGING_SELECT = "stage-selectized";
		public static final String STAGING_OPTIONS = "//select[@id='stage']/following-sibling::div//div[@class='selectize-dropdown-content']/div";
		
		public static final String TREATMENT_SELECT ="treatment-selectized";
		public static final String TREATMENT_OPTIONS = "//select[@id='treatment']/following-sibling::div//div[@class='selectize-dropdown-content']/div";
		
		public static final String GOAL_SELECT="goal-selectized";
		public static final String GOAL_OPTIONS = "//select[@id='goal']/following-sibling::div//div[@class='selectize-dropdown-content']/div";
		
			
		public static final String PERFROMACE_STATUS_SELECT = "perfStatus-selectized";
		public static final String PERFROMACE_STATUS_OPTIONS = "//select[@id='perfStatus']/following-sibling::div//div[@class='selectize-dropdown-content']/div";
		
		public static final String SAVE_ACTION = "//form[@id='procedure-request-form']//input[@name='SaveAction']";		
	}
	
	public class RadClinical{
		public static final String CLINICAL_NEXT_BUTTON = "//button[@caption='Next']";
		public static final String CLINICAL_CONTINUE_BUTTON = "//button[@caption='Continue']";
		public static final String AP_QUESTION_CONTAINER = "//div[@asset='question']";
		public static final String AP_QUESTION_CAPTION = "div.app-label";
		public static final String AP_RADIO_BUTTON_ANSWER = "label.app-radio span.control-caption";
		public static final String AP_TEXTBOX_ANSWER = "div.app-input input";
	}
	
	public class MocClinical{
		public static final String CLINICAL_START_BUTTON ="//input[@value='Start Clinical']";
		
		public static final String PATIENT_HEIGHT ="MainContent_txtHeight";
		public static final String PATIENT_WEIGHT ="MainContent_txtWeight";
		
		public static final String CANCER_TYPE_SELECT = "//span[contains(@class,'rsddCancerType')]";
		public static final String CANCER_TYPE_OPTIONS = "//ul[@id='ddCancerType_listbox']/li";
		
		public static final String ICD_SELECT = "//span[contains(@class,'rsddICD10')]";
		public static final String ICD_OPTIONS = "//ul[@id='ddICD10_listbox']/li";
		public static final String HCPCS = "SearchBox";
		public static final String OVERRIDE_TREATMENT_WARN="//div[contains(@class,'ui-dialog') and contains(@style,'display: block')]//div[@class='ui-dialog-buttonset']/button";
		public static final String REGIMEN_SEARCH_DONE="btnShowResult";
		public static final String DRUG_NAME_CLICK = "//tr[@itemtype='Drug']/td[@class='WSSColumnDrugName']";
		public static final String EDIT_DOSE="//input[contains(@class,'WSSEMDose') and @readonly='readonly']";
		public static final String DOSE_INPUT="//input[@id='EMDose']";
		public static final String ROUTE_CLICK="//span[@id='EMRoutesArea']/span";
		public static final String ROUTE_OPTION ="//ul[@id='EMRoutes_listbox']/li";
		public static final String SUBROUTE_CLICK="//span[@id='EMSubRoutesArea']/span";
		public static final String SUBROUTE_OPTION ="//ul[@id='EMSubRoutes_listbox']/li";
		
		public static final String CYCLE_TEXT="EMCycles";
		public static final String CYCLE_LEN_CLICK="//input[contains(@class,'WSSEMCycleLength') and @readonly='readonly']";
		public static final String CYCLE_LEN_INPUT="EMCycleLength";
		public static final String DAYS_CYCLE_INPUT="EMDaysOfCycle";
		public static final String FREQ_PER_DAY_SELECT= "//span[@class='k-widget k-dropdown k-header WSSEMPerDay required']";
		public static final String FREQ_PER_DAY_OPTION= "//ul[@id='EMPerDay_listbox']/li";
		public static final String BACK_FROM_EDIT_DRUG = "btnBackFromEditDrug";
		public static final String SAVE_DRUG_CONTINUE = "//a[contains(@class,'btnSaveContinue')]";
		
		public static final String PATHOLOGY_SELECT="//li[@id='liPathology']//span[@role='listbox']";
		public static final String PATHOLOGY_OPTIONS="//ul[@id='ddlPathology_listbox']/li";
		
		public static final String STAGE_SELECT="//li[@id='liStage']//span[@role='listbox']";
		public static final String STAGE_OPTIONS="//ul[@id='ddlStage_listbox']/li";
		
		public static final String ICDCODE_SELECT="//li[@id='liICDCode']//span[@role='listbox']";
		public static final String ICDCODE_OPTIONS="//ul[@id='ddlICDCode_listbox']/li";
		
		public static final String BIOMARKER_OPTIONS = "//div[@class='k-animation-container km-popup' and contains(@style,'display: block')]//ul/li";
		public static final String ESTROGEN_RECEPTOR_SELECT="(//li[@id='liBioMarkers']//span[@role='listbox'])[1]";				
		public static final String HER2_SELECT="(//li[@id='liBioMarkers']//span[@role='listbox'])[2]";			
		public static final String MENOPAUSAL_SELECT="(//li[@id='liBioMarkers']//span[@role='listbox'])[3]";			
		public static final String PROGESTERONE_SELECT="(//li[@id='liBioMarkers']//span[@role='listbox'])[4]";
		
		
		public static final String LINE_OF_TREATMENT_SELECT="//li[@id='liLineOfTreatment']//span[@role='listbox']";
		public static final String LINE_OF_TREATMENT_OPTIONS="//ul[@id='ddlLOT_listbox']/li";
		
		public static final String GOAL_OF_TREATMENT_SELECT="//li[@id='liGoalOfTreatment']//span[@role='listbox']";
		public static final String GOAL_OF_TREATMENT_OPTIONS="//ul[@id='ddlGOT_listbox']/li";
		
		public static final String PERF_STATUS_SELECT="//li[@id='liPerformanceStatus']//span[@role='listbox']";
		public static final String PERF_STATUS_OPTIONS="//ul[@id='ddlPerformanceStatus_listbox']/li";
		
		public static final String SAVE_DIAGNOSIS="MainContent_btnSave";
		public static final String SAVE_AND_CONTINUE="MainContent_lbNext";
		public static final String COMPLETE_CLINICAL = "MainContent_cmdContinue";
		
		public static final String EDIT_OUTCOME = "//div[@id='regimen-info-header']//a";
		public static final String MET_BUTTON = "//input[@id='met']";
		
		public static final String REMOVE_DRUG ="//div[@id='pnlChemoDrugs']//a[@class='scProductName']";
	}
	
	public class Product{
		public static final String RAD_PRODUCT_SELECTION = "input.form-submitter.rad-radio.radio";
		public static final String MOC_PRODUCT_SELECTION = "input.form-submitter.chemo-radio.radio";
	}
	
	public class TreatmentDates{
		public static final String DATE_OF_SERVICE = "DateOfService";
		public static final String PLANNING_DATE = "PlanningStartDate";
	}
	
	public class Tabs{
		public static final String MEMBER="Member";
		public static final String PHYSICIAN="Physician";
		public static final String PROVIDER ="Provider";
		public static final String MODALITY ="Modality";
		public static final String CLINICAL ="Clinical";
		public static final String DRUGSUMMARY ="DrugSummary";
		
	}
	
	
	public class CaseActions{
		public static final String SAVE = "//a[@title='Save Case']";
		public static final String CLOSE = "//a[contains(@class,'close-case')]";
				
	}
}

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
	
	public class Product{
		public static final String RAD_PRODUCT_SELECTION = "input.form-submitter.rad-radio.radio";
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
		
	}
	
	public class CaseActions{
		public static final String SAVE = "//a[@title='Save Case']";
		public static final String CLOSE = "//a[contains(@class,'close-case')]";
				
	}
}

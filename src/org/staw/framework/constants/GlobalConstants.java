package org.staw.framework.constants;

public class GlobalConstants {
	
	public class RunEnvironment{
		public static final String RUN_ENVIRONMENT_PROP = "runEnvironmentProp";
  		  		
  		public static final String PROD = "prod";	
  		public static final String QA = "qa";
  		public static final String LOCAL_DEV = "local_dev";
  		
	}
	
	public class MasterConstant {	
		public static final String STRING_EMPTY = "";
		public static final String CURRENT_BRANCH_NAME = "CURRENT_BRANCH_NAME";		
		public static final String FM_P_ID = "p_id";
		public static final String FM_TESTNAME = "testCaseName";
		public static final String FM_BROWSER = "testbrowser";
		public static final String FM_BROWSER_VERSION = "browserversion";
		public static final String FM_OPERATING_SYSTEM = "os";
		public static final String FM_USER_ID = "USERID";
		public static final String FM_HOST_NAME = "HOSTNAME";
  		public static final String FM_TEST_CRASHED = "testCrashed";
  		public static final String FM_SESSION_ID = "sessionId";
		public static final String FM_CURRENT_KEYWORD_STEP = "Step_Count";
		public static final String FM_CURRENT_KEYWORD = "Current_Keyword";
		public static final String FM_CURRENT_KEYWORD_PARAMETERS = "Current_Keyword_Parameters";
		public static final String FM_URL = "url";
		public static final String FM_START_DATE_AND_TIME = "startDateAndTime";
		public static final String FM_TEST_START_TIME = "testStartTime";
		public static final String FM_TEST_RESULT = "TestResult";
		public static final String FM_STEPS_PASSED = "stepsPassed";
		public static final String FM_STEPS_FAILED = "stepsFailed";
		public static final String FM_TOTAL_EXECUTION_TIME = "totalExecutionTime";
		public static final String FM_STEP_LEVEL_REPORT = "StepLevelReport";
		public static final String FM_RETRY_COUNT = "retryCount";
		public static final String FM_CURRENT_TEST_STEP_REPORT = "currentTestStepReport";
		public static final String FM_TOTAL_EXECUTION_STEPS = "totalSteps";
		public static final String FM_JSESSION_NOT_VALID="JSESSION_NOT_VALID";
		public static final String FM_ACTUAL_JSESSIONID="ACTUAL_JSESSIONID";
		public static final String FM_EXPECTED_JSESSIONID="EXPECTED_JSESSIONID";
  		
		public static final String GBL_PASS_STEP_COUNT = "gblPassStepsCount";
		public static final String GBL_FAIL_STEP_COUNT = "gblFailStepsCount";
		public static final String GBL_TEST_RESULT = "gblTestResult";
		public static final String GBL_PASS_MESSAGE = "gblPassMessage";
		public static final String GBL_ERROR_MESSAGE = "gblErrorMessage";
		public static final String GBL_CURRENT_USER_ID = "gblCurrentUserId";
  		public static final String GBL_LAST_PAGE = "gblLastPage";
  		public static final String GBL_CURRENT_PAGE = "gblCurrentPage";
	}

	public class AssertionAndTestStepConstant {
		public static final String KEYWORD = "keyWord";
		public static final String PARAMETER_VALUES = "parameterValues";
		public static final String STEP_RESULT = "stepResult";
		public static final String DESRIPTION = "description";
		public static final String STEP_COUNT = "Step_Count";
	}
		
	public class UtilitiesConstants{
		public static final String REGEX_REPLACE_NON_DIGIT = "[^\\d.]";
		public static final String REGEX_REPLACE_MULTIPLE_SPACE = "\\s+";
		public static final String REGEX_REPLACE_NON_APLHA_NUMERIC = "[^A-Za-z0-9\\s]*";
		public static final String REGEX_SPECIAL_CHARACTERS = "[^\\w\\d]|\\s";
		public static final String REGEX_CURRENCY = "[.0-9]+";
		public static final String REGEX_REPLACE_STRING = "[^0-9?!\\\\.]";
		
		public static final String JAVASCRIPT_CLICK_ELEMENT = "arguments[0].click();";
		public static final String JAVASCRIPT_BLUR_ELEMENT = "arguments[0].blur();";
		public static final String JAVASCRIPT_HOVER_ELEMENT = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover',true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
		public static final String JAVASCRIPT_GET_ELEMENT_VALUE = "return arguments[0].value;";
		public static final String JAVASCRIPT_GET_DIGITAL_DATA = "return window.digitalData";
		public static final String JAVASCRIPT_SCROLL_TO_END_OF_PAGE = "window.scrollTo(0, document.body.scrollHeight)";
		public static final String JAVASCRIPT_SCROLL_TO_ELEMENT = "arguments[0].scrollIntoView(true);";
		public static final String JAVASCRIPT_GO_BACK_PAGE = "history.go(-1)";
		
		
		public static final String ALT = "alt";
		public static final String CLASS = "class";
		public static final String ID = "id";
		public static final String TITLE = "title";
		public static final String VALUE = "value";
		public static final String STYLE = "style";
		public static final String DATA_VALUE = "data-value";
		public static final String DATA_TITLE = "data-title";
		public static final String DATA_INDEX = "data-index";
		public static final String DATA_TYPE ="data-type";
		public static final String PLACEHOLDER = "placeholder";
		public static final String NAME = "name";
		public static final String ONCLICK="onclick";
		public static final String DATE_CODE = "data-code";
		public static final String DATE_TITLE = "data-original-title";
		public static final String CHECKED="checked";
		public static final String HEAD = "head";
		public static final String CONTENT = "content";
		
		public static final String H1 = "h1";
		public static final String H2 = "h2";
		public static final String H3 = "h3";
		public static final String H4 = "h4";
		public static final String H5 = "h5";
		public static final String H6 = "h6";
		public static final String A = "a";
		public static final String DIV = "div";
		public static final String INPUT = "input";
		public static final String IMG = "img";
		public static final String LI = "li";
		public static final String P = "p";
		public static final String SPAN = "span";
		public static final String I = "i";
		public static final String BUTTON = "button";
		public static final String FIELDSET = "fieldset";
		public static final String SELECT = "select";
		public static final String LINK = "link";
		public static final String META = "meta";
		public static final String FOR = "for";
		
		public static final String OPTION = "option";
		public static final String UL = "ul";
		public static final String TD = "td";
		public static final String TR = "tr";
		public static final String TH = "th";
		public static final String TBODY = "tbody";
		public static final String SECTION = "section";
		public static final String HREF = "href";
		public static final String DL = "dl";
		public static final String DD="dd";
		public static final String SMALL="small";
	}
}
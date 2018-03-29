package org.staw.framework.constants;

public class GlobalConstants {
	
	public class KeywordName{
		public static final String INITIALIZE = "Initialize";
	}
	
	public class RunEnvironment{
		public static final String RUN_ENVIRONMENT_PROP = "runEnvironmentProp";
  		  		
  		public static final String PROD = "prod";	
  		public static final String QA = "qa";
  		public static final String LOCAL_DEV = "local_dev";
  		
	}
	
	public class ContextConstant {	
		public static final String STRING_EMPTY = "";
		public static final String CURRENT_BRANCH_NAME = "CURRENT_BRANCH_NAME";		
		public static final String EXECUTION_ID = "execution_id";
		public static final String TEST_NAME = "testCaseName";
		public static final String BROWSER = "testbrowser";
		public static final String BROWSER_VERSION = "browserversion";
		public static final String OPERATING_SYSTEM = "os";
		public static final String USER_ID = "USERID";
		public static final String HOST_NAME = "HOSTNAME";
  		public static final String TEST_CRASHED = "testCrashed";
  		public static final String SESSION_ID = "sessionId";
		public static final String CURRENT_KEYWORD_STEP = "Step_Count";
		public static final String CURRENT_KEYWORD = "Current_Keyword";
		public static final String CURRENT_KEYWORD_PARAMETERS = "Current_Keyword_Parameters";
		public static final String URL = "url";
		public static final String START_DATE_AND_TIME = "startDateAndTime";
		public static final String TEST_START_TIME = "testStartTime";	
		public static final String STEPS_PASSED = "stepsPassed";
		public static final String STEPS_FAILED = "stepsFailed";
		public static final String TOTAL_EXECUTION_TIME = "totalExecutionTime";
		public static final String STEP_LEVEL_REPORT = "StepLevelReport";
		public static final String RETRY_COUNT = "retryCount";
		public static final String CURRENT_TEST_STEP_REPORT = "currentTestStepReport";
		public static final String TOTAL_EXECUTION_STEPS = "totalSteps";
		public static final String JSESSION_NOT_VALID="JSESSION_NOT_VALID";
		public static final String ACTUAL_JSESSIONID="ACTUAL_JSESSIONID";
		public static final String EXPECTED_JSESSIONID="EXPECTED_JSESSIONID";
  		
		public static final String PASS_STEP_COUNT = "PassStepsCount";
		public static final String FAIL_STEP_COUNT = "FailStepsCount";
		public static final String TEST_RESULT = "TestResult";
		public static final String PASS_MESSAGE = "PassMessage";
		public static final String ERROR_MESSAGE = "ErrorMessage";
		public static final String CURRENT_USER_ID = "CurrentUserId";
  		public static final String LAST_PAGE = "LastPage";
  		public static final String CURRENT_PAGE = "CurrentPage";
	}

	public class StepConstant {
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
		public static final String JAVASCRIPT_SCROLL_TO_END_OF_PAGE = "window.scrollTo(0, document.body.scrollHeight)";
		public static final String JAVASCRIPT_SCROLL_TO_ELEMENT = "arguments[0].scrollIntoView(true);";
		public static final String JAVASCRIPT_GO_BACK_PAGE = "history.go(-1)";

		public static final String VALUE = "value";
		public static final String INPUT = "input";
	}
}
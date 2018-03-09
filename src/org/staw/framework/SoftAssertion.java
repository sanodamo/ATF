package org.staw.framework;

import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.staw.datarepository.DataLibrary.ReportType;
import org.staw.datarepository.DataLibrary;
import org.staw.framework.constants.AssertTypes;
import org.staw.framework.constants.GlobalConstants;
import org.staw.framework.helpers.Utilities;
import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;

public class SoftAssertion extends Assertion {

		public Utilities util = null;
		
	    public static Logger log = Logger.getLogger(SoftAssertion.class.getName());
	
	    public void configurePrefix(String browserName, String tcName){
	    	DataLibrary.setValue(ReportType.MASTER_TABLE, GlobalConstants.MasterConstant.GBL_TEST_RESULT, Boolean.toString(true));
	    	DataLibrary.setValue(ReportType.MASTER_TABLE, GlobalConstants.MasterConstant.GBL_PASS_STEP_COUNT, "0");
	    	DataLibrary.setValue(ReportType.MASTER_TABLE, GlobalConstants.MasterConstant.GBL_FAIL_STEP_COUNT, "0");
	    }
	    
		@Override
		public void executeAssert(IAssert assertCommand){
								
			int passStepsCount = 0;
			int failStepCount = 0;
			String stepResult = "";
			String keyword = DataLibrary.getValue(ReportType.MASTER_TABLE, GlobalConstants.MasterConstant.FM_CURRENT_KEYWORD);
			String keywordParam = DataLibrary.getValue(ReportType.MASTER_TABLE, GlobalConstants.MasterConstant.FM_CURRENT_KEYWORD_PARAMETERS);
			String keywordDescription = "";
			try {
				assertCommand.doAssert();
				stepResult = WordUtils.capitalize("PASSED");
				keywordDescription = DataLibrary.getValue(ReportType.MASTER_TABLE, GlobalConstants.MasterConstant.GBL_PASS_MESSAGE);
				passStepsCount = Integer.parseInt(DataLibrary.getValue(ReportType.MASTER_TABLE, GlobalConstants.MasterConstant.GBL_PASS_STEP_COUNT));
				passStepsCount++;
				DataLibrary.setValue(ReportType.MASTER_TABLE, GlobalConstants.MasterConstant.GBL_PASS_STEP_COUNT, Integer.toString(passStepsCount));
			}catch (AssertionError ex){
				
				log.error("\n---------------------------------Error--------------------------------------------"
						+ "\nFailure Detected at Step From TC Name "+ DataLibrary.getValue(ReportType.THREADS, GlobalConstants.MasterConstant.FM_TESTNAME) 
						+ "\nIn Browser: " + DataLibrary.getValue(ReportType.THREADS, GlobalConstants.MasterConstant.FM_BROWSER) + " version: " 
						+ DataLibrary.getValue(ReportType.THREADS, GlobalConstants.MasterConstant.FM_BROWSER_VERSION)
						+ " \nwith Error Message: "+ keywordDescription
						+ " \nwith keyword: " + keyword
						+ " \nwith Parameter(s): " + keywordParam
						+ "\n---------------------------------------------------------------------------------\n");
				stepResult = "Failed";
				keywordDescription = DataLibrary.getValue(ReportType.MASTER_TABLE, GlobalConstants.MasterConstant.GBL_ERROR_MESSAGE);
				failStepCount = Integer.parseInt(DataLibrary.getValue(ReportType.MASTER_TABLE, GlobalConstants.MasterConstant.GBL_FAIL_STEP_COUNT));
				failStepCount++;
				DataLibrary.setValue(ReportType.MASTER_TABLE, GlobalConstants.MasterConstant.GBL_FAIL_STEP_COUNT, Integer.toString(failStepCount));
				DataLibrary.setValue(ReportType.MASTER_TABLE, GlobalConstants.MasterConstant.GBL_TEST_RESULT, Boolean.toString(false));
			}
			DataLibrary.setValue(ReportType.STEP_LEVEL_REPORT, GlobalConstants.AssertionAndTestStepConstant.KEYWORD, keyword);
			DataLibrary.setValue(ReportType.STEP_LEVEL_REPORT, GlobalConstants.AssertionAndTestStepConstant.PARAMETER_VALUES, keywordParam);
			DataLibrary.setValue(ReportType.STEP_LEVEL_REPORT, GlobalConstants.AssertionAndTestStepConstant.DESRIPTION, keywordDescription);
			DataLibrary.setValue(ReportType.STEP_LEVEL_REPORT, GlobalConstants.AssertionAndTestStepConstant.STEP_RESULT, stepResult);
			
			
		}


	/****************************************************************************************************
	 * Method: setGblPassFailMessage
	 * Description: 
	 * @author 
	 * @return void
	 ***************************************************************************************************/ 
		public void setAssertedMessage(AssertTypes assertType, String message){
			switch(assertType){
			case SUCCESS:
				DataLibrary.setValue(ReportType.MASTER_TABLE, GlobalConstants.MasterConstant.GBL_PASS_MESSAGE, message);			
				break;				
			case FAILED:
			case WARNING:			
			case ERROR:
				DataLibrary.setValue(ReportType.MASTER_TABLE, GlobalConstants.MasterConstant.GBL_ERROR_MESSAGE, message);
				break;
			}
		}
		
	
		public boolean Success(String message) {
			setAssertedMessage(AssertTypes.SUCCESS, message);
			return true;
		}
		
	
		public boolean Failed(String message) {
			setAssertedMessage(AssertTypes.FAILED, message);
			return false;
		}
}

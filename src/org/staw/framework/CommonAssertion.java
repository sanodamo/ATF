package org.staw.framework;

import java.util.Date;

import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.staw.datarepository.dao.Steps.Step;
import org.staw.datarepository.dao.Steps.StepProvider;
import org.staw.datarepository.dao.TestContext.TestContextProvider;
import org.staw.datarepository.dao.TestRun.TestRun;
import org.staw.datarepository.dao.TestRun.TestRunProvider;
import org.staw.framework.constants.AssertTypes;
import org.staw.framework.constants.GlobalConstants;

import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;

public class CommonAssertion extends Assertion {

		
		
	    public static Logger log = Logger.getLogger(CommonAssertion.class.getName());
	    
		@Override
		public void executeAssert(IAssert assertCommand){
								
			int passStepsCount = 0;
			int failStepCount = 0;
			String stepResult = "";
			String currentStepCount = "0";
						
			String keyword = TestContextProvider.GetValue(GlobalConstants.ContextConstant.CURRENT_KEYWORD);
			String keywordParam = TestContextProvider.GetValue(GlobalConstants.ContextConstant.CURRENT_KEYWORD_PARAMETERS);
			currentStepCount = TestContextProvider.GetValue(GlobalConstants.ContextConstant.CURRENT_KEYWORD_STEP);
			
			String keywordDescription = "";
			try {
				assertCommand.doAssert();
				stepResult = WordUtils.capitalize("PASSED");
								
				keywordDescription = TestContextProvider.GetValue(GlobalConstants.ContextConstant.PASS_MESSAGE);
				passStepsCount = Integer.parseInt(TestContextProvider.GetValue(GlobalConstants.ContextConstant.PASS_STEP_COUNT));
						
				passStepsCount++;
								
				TestContextProvider.SetValue(GlobalConstants.ContextConstant.PASS_STEP_COUNT, Integer.toString(passStepsCount));
			}catch (AssertionError ex){
				
				TestRun test = TestRunProvider.GetCurrentTest();							
				
				log.error("\n *************** Filed ***********************" + 
						"\n Test Case --> " + test.getTestCaseName() +  
						"\n Browser --> " + test.getBrowserName() +
						"\n Browser Version --> " + test.getBrowserVersion() +
						"\n OS --> " + test.getOsName() +
						"\n User ID --> " + test.getUserId() +
						"\n Host --> " + test.getHostName() +
						"\n Keyword  --> " + keyword +
						"\n Arg --> " + keywordParam + "\n"
						);
				
				
				stepResult = "Failed";
				
				keywordDescription = TestContextProvider.GetValue(GlobalConstants.ContextConstant.ERROR_MESSAGE);
				failStepCount = Integer.parseInt(TestContextProvider.GetValue(GlobalConstants.ContextConstant.FAIL_STEP_COUNT));
				
				failStepCount++;
				
				TestContextProvider.SetValue(GlobalConstants.ContextConstant.FAIL_STEP_COUNT, Integer.toString(failStepCount));
				TestContextProvider.SetValue(GlobalConstants.ContextConstant.TEST_RESULT, "false");
				
			}
					
			Step step = new Step();
			step.setStepNumber(Integer.parseInt(currentStepCount));
			step.setKeyword(keyword);
			step.setDescription(keywordDescription != null ? keywordDescription : "N/A");
			step.setParameters(keywordParam != null ? keywordParam : "N/A");
			step.setResult(stepResult);
			step.setExecutionTime(new Date());
						
			StepProvider.SetValue(step);
			
		}

	
		public void setAssertedMessage(AssertTypes assertType, String message){
			switch(assertType){
			case SUCCESS:							
				TestContextProvider.SetValue(GlobalConstants.ContextConstant.PASS_MESSAGE, message);
				break;				
			case FAILED:
			case WARNING:			
			case ERROR:				
				TestContextProvider.SetValue(GlobalConstants.ContextConstant.ERROR_MESSAGE, message);
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
		
		public void InitializeTestContext() {
			TestContextProvider.SetValue(GlobalConstants.ContextConstant.TEST_RESULT, Boolean.toString(true));
			TestContextProvider.SetValue(GlobalConstants.ContextConstant.PASS_STEP_COUNT, "0");
			TestContextProvider.SetValue(GlobalConstants.ContextConstant.FAIL_STEP_COUNT, "0");
		}
}

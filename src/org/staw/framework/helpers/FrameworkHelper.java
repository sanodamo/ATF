package org.staw.framework.helpers;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.staw.datarepository.dao.TestContext.TestContextMapper;
import org.staw.datarepository.dao.TestContext.TestContextProvider;
import org.staw.datarepository.dao.TestRun.TestRun;
import org.staw.datarepository.dao.TestRun.TestRunProvider;
import org.staw.framework.KeywordDispatcher;
import org.staw.framework.DriverFactory;
import org.staw.framework.CommonAssertion;
import org.staw.framework.ExecutionIdentity;
import org.staw.framework.constants.GlobalConstants;
import org.staw.framework.models.GlobalVariables;
import org.w3c.dom.NodeList;

public class FrameworkHelper {

	public static Logger log = Logger.getLogger(FrameworkHelper.class.getName());
	private static CommonAssertion commonAssertion;
	private static String executionEnv = "";
 
	
	public static String getSessionPid() {
		return String.valueOf(UUID.randomUUID());
	}
	
	public static CommonAssertion initialize(String testCaseName, String browser, String browserVersion, String osVersion) {
		Thread.currentThread().setName("Thread-"+testCaseName+"_"+browser+"_"+browserVersion+"_"+osVersion);
		commonAssertion = new CommonAssertion();
				
		try {
			
			ExecutionIdentity.initialize();
			ExecutionIdentity.setValue(GlobalConstants.ContextConstant.EXECUTION_ID, getSessionPid());
									
						
			TestRun test = new TestRun();
			test.setTestCaseName(testCaseName);
			test.setBrowserName(browser);
			test.setBrowserVersion(browserVersion);
			test.setOsName(osVersion);
			test.setUserId(TestSetupHelper.getCurrentUser());
			test.setHostName(TestSetupHelper.getCurrentHostName());
			test.setStartDateTime(new Date());
						
			TestRunProvider.Save(test);
			
			executionEnv = TestSetupHelper.getRunEnvironemnt();
			DriverFactory.getInstance().getDriver(testCaseName, browser, browserVersion, osVersion, executionEnv, commonAssertion);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return commonAssertion;
	}
		
	public static boolean runTests(String testCaseName, String browser, String browserVersion, String osVersion){
		WebDriver driver = DriverFactory.getInstance().getWebDriver();
	
		if(driver != null) {
			try {
								
				commonAssertion.InitializeTestContext();
		    	
				KeywordDispatcher allactions = new KeywordDispatcher(commonAssertion);

				NodeList keywords = null;
			
				keywords = TestSetupHelper.readKeywords(testCaseName.trim() + ".xml");
				
				if (keywords == null) {
					
					commonAssertion.assertTrue(false);
															
					TestContextProvider.SetValue(GlobalConstants.ContextConstant.TEST_CRASHED, "true");
					
					DriverFactory.getInstance().removeDriver(testCaseName);
					log.error("No Actions node found");
					return false;
				}
				
				TestContextProvider.SetValue(GlobalConstants.ContextConstant.TOTAL_EXECUTION_STEPS,  Integer.toString(keywords.getLength()));
				
				allactions.InvokeKeywordMethods(keywords);
				
			} catch (Exception e) {
				
				finalize(testCaseName, browser, browserVersion, osVersion);
				log.error("Error coming from test: " + testCaseName + " with driver: " + DriverFactory.getInstance().getWebDriver().hashCode() + " on browser: " + browser);
				return false;
			} finally {
				
			}
		}
		return false;
	}
	
	private static String getTestCase(String currRunEnv, String tcName) {
		return tcName;
	}
	
	public static void finalize(String tcName, String browser, String browserVersion, String osVersion) {
		WebDriver driver = DriverFactory.getInstance().getWebDriver();
		if(driver != null) {
			tcName = tcName.trim();
			browser = browser.trim();
			browserVersion = browserVersion.trim();
			osVersion = osVersion.trim();
						
			finalizeTestInformation(tcName, browser, browserVersion, osVersion);
			
			try {
				
					DriverFactory.getInstance().removeDriver(driver, tcName, log);
				
			} catch (Exception e) {
				log.error("Unable to end test, Error: " + e.getMessage());
				return;
			}
		}
	}
	
	
	public static void finalizeTestInformation(String tcName, String browser, String browserVersion, String osVersion) {
		
		long totalExecutionTime;
		long testEndTime = System.currentTimeMillis();
		long startTime = 0L;
		
		try{
			startTime = Long.parseLong(TestContextProvider.GetValue(GlobalConstants.ContextConstant.TEST_START_TIME));
		}catch(NumberFormatException e){
			log.error("Unable to get Test start time when storing final report information. Error: " + e.getCause());
		}
				
		totalExecutionTime = testEndTime - startTime;
		String time = getExecutionTime(totalExecutionTime);
		
		int totalSteps =0;
		int stepsPassed = 0;
		try{
			totalSteps = Integer.parseInt(TestContextProvider.GetValue(GlobalConstants.ContextConstant.TOTAL_EXECUTION_STEPS));
			stepsPassed = Integer.parseInt(TestContextProvider.GetValue(GlobalConstants.ContextConstant.PASS_STEP_COUNT));
		}catch(NumberFormatException e){
			log.error("Unable to get Total Execution Steps. Error: " + e.getMessage()); 
			e.printStackTrace();
		}
		TestContextProvider.SetValue(GlobalConstants.ContextConstant.STEPS_FAILED, Integer.toString(totalSteps-stepsPassed));
		TestContextProvider.SetValue(GlobalConstants.ContextConstant.TOTAL_EXECUTION_TIME, time);			
	}

	public static String getExecutionTime(long totalExecutionTime){
		long secondsInMilli = 1000;
		long minutesInMilli = secondsInMilli * 60;
		long hoursInMilli = minutesInMilli * 60;
		long elapsedHours = totalExecutionTime / hoursInMilli;
		totalExecutionTime = totalExecutionTime % hoursInMilli;
		long elapsedMinutes = totalExecutionTime / minutesInMilli;
		totalExecutionTime = totalExecutionTime % minutesInMilli;
		long elapsedSeconds = totalExecutionTime / secondsInMilli;
		NumberFormat f = new DecimalFormat("00");
		if(elapsedHours == 0)
			return "00:" + f.format(elapsedMinutes) +":" + f.format(elapsedSeconds);
		else
			return f.format(elapsedHours) +":" + f.format(elapsedMinutes) +":" + f.format(elapsedSeconds);
	}	
	
}

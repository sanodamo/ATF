package org.staw.framework;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.staw.datarepository.dao.TestContext.TestContextProvider;
import org.staw.framework.constants.GlobalConstants;
import org.staw.framework.helpers.FrameworkHelper;
import org.staw.framework.helpers.TestSetupHelper;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ExecuteTests { 

	static CommonAssertion commonAssertion;
	
	 
	
	public static Logger log = Logger.getLogger(ExecuteTests.class.getName());
	
	@BeforeMethod
	@Parameters({ "testCaseName", "browserName", "browserVersion", "osVersion", "UserId" })
	public void initSoftAssert(String testCaseName, String browserName, String browserVersion, String osVersion, String UserId) {
		commonAssertion = FrameworkHelper.initialize(testCaseName, browserName, browserVersion, osVersion);		
				    
	    TestContextProvider.SetValue(GlobalConstants.ContextConstant.USER_ID, TestSetupHelper.getCurrentHostName());
	    TestContextProvider.SetValue(GlobalConstants.ContextConstant.TEST_START_TIME, Long.toString(System.currentTimeMillis()));
	    TestContextProvider.SetValue(GlobalConstants.ContextConstant.START_DATE_AND_TIME, new SimpleDateFormat("MM/dd/yy HH:mm:ss").format(new Date()));
	       
	}


	@Test
	@Parameters({"testCaseName", "browserName", "browserVersion", "osVersion", "UserId" })
	public void runTestCase(String testCaseName, String browserName, String browserVersion, String osVersion, String UserId) {
		testCaseName = testCaseName.trim();
		browserName = browserName.trim();
		browserVersion = browserVersion.trim();
		osVersion = osVersion.trim();
		UserId = UserId.trim();
		
		FrameworkHelper.runTests(testCaseName, browserName, browserVersion, osVersion);
	}

	@AfterMethod
	@Parameters({ "testCaseName", "browserName", "browserVersion", "osVersion", "UserId" })
	public void afterMethod(String testCaseName, String browserName, String browserVersion, String osVersion, String UserId) {
		FrameworkHelper.finalize(testCaseName, browserName, browserVersion, osVersion);
	}
}
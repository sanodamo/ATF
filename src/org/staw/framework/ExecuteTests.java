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

	static SoftAssertion myAssert;
	
	 
	
	public static Logger log = Logger.getLogger(ExecuteTests.class.getName());
	
	@BeforeMethod
	@Parameters({ "tcName", "browser", "browserVersion", "osVersion", "UserId" })
	public void initSoftAssert(String tcName, String browser, String browserVersion, String osVersion, String UserId) {
		myAssert = FrameworkHelper.initialize(tcName, browser, browserVersion, osVersion);		
				    
	    TestContextProvider.SetValue(GlobalConstants.ContextConstant.USER_ID, TestSetupHelper.getCurrentHostName());
	    TestContextProvider.SetValue(GlobalConstants.ContextConstant.TEST_START_TIME, Long.toString(System.currentTimeMillis()));
	    TestContextProvider.SetValue(GlobalConstants.ContextConstant.START_DATE_AND_TIME, new SimpleDateFormat("MM/dd/yy HH:mm:ss").format(new Date()));
	       
	}


	@Test
	@Parameters({"tcName", "browser", "browserVersion", "osVersion", "UserId" })
	public void runTestCase(String tcName, String browser, String browserVersion, String osVersion, String UserId) {
		tcName = tcName.trim();
		browser = browser.trim();
		browserVersion = browserVersion.trim();
		osVersion = osVersion.trim();
		UserId = UserId.trim();
		
		FrameworkHelper.runTests(tcName, browser, browserVersion, osVersion);
	}

	@AfterMethod
	@Parameters({ "tcName", "browser", "browserVersion", "osVersion", "UserId" })
	public void afterMethod(String tcName, String browser, String browserVersion, String osVersion, String UserId) {
		FrameworkHelper.finalize(tcName, browser, browserVersion, osVersion);
	}
}
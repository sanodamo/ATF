package org.staw.framework;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.staw.datarepository.DataLibrary.ReportType;
import org.staw.datarepository.DataLibrary;
import org.staw.framework.constants.GlobalConstants;
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
		HashMap<String, String> insertVals = new HashMap<>();
		insertVals.put(GlobalConstants.MasterConstant.FM_USER_ID, UserId);
	    DataLibrary.storeInitialInformation(ReportType.MASTER_TABLE, insertVals);
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
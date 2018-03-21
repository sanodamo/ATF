package org.staw.framework.helpers;

import java.util.HashMap;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.staw.datarepository.DataLibrary.ReportType;
import org.openqa.selenium.WebDriver;
import org.staw.datarepository.DataLibrary;
import org.staw.framework.models.GlobalVariables;
import org.staw.framework.KeywordRunner;
import org.staw.framework.SeleniumDriver;
import org.staw.framework.SoftAssertion;
import org.staw.framework.ThreadInformation;
import org.staw.framework.constants.GlobalConstants;
import org.w3c.dom.NodeList;
import org.staw.framework.helpers.EnviromentSetupHelper;

public class FrameworkHelper {

	public static Logger log = Logger.getLogger(FrameworkHelper.class.getName());
	private static SoftAssertion myAssert;
	private static String executionEnv = "";
	
	public static String getSessionPid() {
		return String.valueOf(UUID.randomUUID());
	}
	
	public static SoftAssertion initialize(String tcName, String browser, String browserVersion, String osVersion) {
		Thread.currentThread().setName("Thread-"+tcName+"_"+browser+"_"+browserVersion+"_"+osVersion);
		tcName = tcName.trim();
		browser = browser.trim();
		browserVersion = browserVersion.trim();
		osVersion = osVersion.trim();
		myAssert = new SoftAssertion();
		HashMap<String, String> inf = new HashMap<>();
		inf.put(GlobalConstants.MasterConstant.FM_TESTNAME, tcName);
		inf.put(GlobalConstants.MasterConstant.FM_BROWSER, browser);
		inf.put(GlobalConstants.MasterConstant.FM_BROWSER_VERSION, browserVersion);
		inf.put(GlobalConstants.MasterConstant.FM_OPERATING_SYSTEM, osVersion);
		try {
			String previousPid = setPreviousID(tcName, browser, browserVersion, osVersion);
			ThreadInformation.initialize();
			ThreadInformation.setValue(GlobalConstants.MasterConstant.FM_P_ID, getSessionPid());
			
			//DataLibrary.storeScriptInBuildScripts();
			DataLibrary.setValue(ReportType.RERUN_TABLE, ThreadInformation.getValue(GlobalConstants.MasterConstant.FM_P_ID), previousPid);
			DataLibrary.setValue(ReportType.THREADS, GlobalConstants.MasterConstant.FM_P_ID, ThreadInformation.getValue(GlobalConstants.MasterConstant.FM_P_ID));
			
			for(String key: inf.keySet()){
				ThreadInformation.setValue(key, inf.get(key));
				DataLibrary.setValue(ReportType.THREADS, key, inf.get(key));
			}

			
			executionEnv = TestSetupHelper.getRunEnvironemnt();
			SeleniumDriver.getInstance().getDriver(tcName, browser, browserVersion, osVersion, executionEnv, myAssert);
			DataLibrary.storeInitialInformation(ReportType.MASTER_TABLE, null);
			DataLibrary.storeInitialInformation(ReportType.REPORT_TABLE, null);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return myAssert;
	}
	
	private static String setPreviousID(String tcName, String browser, String browserVersion, String osVersion) {
		String previousPid = "";
		if(GlobalVariables.pId.containsKey(Long.toString(Thread.currentThread().getId()))){
			String currentKey = tcName+browser+browserVersion+osVersion;
			String previousKey = ThreadInformation.getValue(GlobalConstants.MasterConstant.FM_TESTNAME) + ThreadInformation.getValue(GlobalConstants.MasterConstant.FM_BROWSER) 
			+ ThreadInformation.getValue(GlobalConstants.MasterConstant.FM_BROWSER_VERSION) + ThreadInformation.getValue(GlobalConstants.MasterConstant.FM_OPERATING_SYSTEM);
			if(ThreadInformation.containsKey(GlobalConstants.MasterConstant.FM_P_ID) && currentKey.toLowerCase().equals(previousKey.toLowerCase())){
				previousPid = ThreadInformation.getValue(GlobalConstants.MasterConstant.FM_P_ID);
			}else{
				previousPid = Integer.toString(-1);
			}
		} else {
			previousPid = Integer.toString(-1);
		}
		return previousPid;
	}
	
	public static boolean runTests(String tcName, String browser, String browserVersion, String osVersion){
		WebDriver driver = SeleniumDriver.getInstance().getWebDriver();
		String testCaseName = "";
		String currRunEnv = EnviromentSetupHelper.getTestRunningEnvironmentVariable();
		if(driver != null) {
			try {
				myAssert.configurePrefix(browser,tcName);

				KeywordRunner allactions = new KeywordRunner(myAssert);

				NodeList keywords = null;
				testCaseName = getTestCase(currRunEnv, tcName);
				keywords = TestSetupHelper.readTestCase(testCaseName.trim() + ".xml");
				if (keywords == null) {
					
					myAssert.assertTrue(false);
					DataLibrary.setValue(ReportType.MASTER_TABLE, GlobalConstants.MasterConstant.FM_TEST_CRASHED, Boolean.toString(true));
					SeleniumDriver.getInstance().removeDriver(tcName);
					log.error("No Actions node found");
					return false;
				}
				allactions.executeKeywords(keywords);
			} catch (Exception e) {
				
				finalize(tcName, browser, browserVersion, osVersion);
				log.error("Error coming from test: " + tcName + " with driver: " + SeleniumDriver.getInstance().getWebDriver().hashCode() + " on browser: " + browser);
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
		WebDriver driver = SeleniumDriver.getInstance().getWebDriver();
		if(driver != null) {
			tcName = tcName.trim();
			browser = browser.trim();
			browserVersion = browserVersion.trim();
			osVersion = osVersion.trim();
			StringBuilder output = new StringBuilder();
			
			DataLibrary.storeFinalTestInformationForReport(tcName, browser, browserVersion, osVersion);
			
			try {
				
					SeleniumDriver.getInstance().removeDriver(driver, tcName, log);
				
			} catch (Exception e) {
				log.error("Unable to end test, Error: " + e.getMessage());
				return;
			}
		}
	}
	
}

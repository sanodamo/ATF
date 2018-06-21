package org.staw.framework;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.staw.framework.helpers.TestSetupHelper;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MainTestInvoker extends CoreTestCase{
	public static Logger logger = Logger.getLogger(MainTestInvoker.class.getName());
	
	@Override
	@BeforeSuite
	public void StartSuite() {
		super.StartSuite();
	}
	

	@Test(dataProvider = "getTestCaseInput", dataProviderClass = MainTestInvoker.class)
	public void CreateTest(String testName, String browser, String browserVersion, String os, String env){
		if(runRegularTest()){
			HashMap<String, String> parameters = new HashMap<>();
			String uniqueName = testName.trim() + "_" + browser.trim() + "_"+ browserVersion.trim() + "_"+ os.trim();
			parameters.put("testCaseName", testName);
			parameters.put("browserName", browser);
			parameters.put("browserVersion", browserVersion);
			parameters.put("osVersion", os);			
			parameters.put("UserId", TestSetupHelper.getCurrentUser());
			super.runTest(testName, uniqueName, env, "org.staw.framework.ExecuteTests", parameters);
		}
	}
	
	
	@AfterSuite
	@Override
	public void EndSuite() {
		if(runRegularTest()){
			super.EndSuite();
		}
	}

	private boolean runRegularTest(){
		return InitializeTestSuite.getInstance().isDatasourceInitialized();
	}
	
	
	
	@DataProvider()
	public static Object[][] getTestCaseInput() throws IOException {
		
		TestSetupHelper.buildEnvironment();		
		return TestSetupHelper.getParameters();
			
	}
		
	
	
	
	
	
	
	
	
}
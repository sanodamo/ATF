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

public class RunTests extends CoreTestCase{
	public static Logger logger = Logger.getLogger(RunTests.class.getName());
	
	@Override
	@BeforeSuite
	public void StartSuite() {
		super.StartSuite();
	}
	

	@Test(dataProvider = "getTestCaseNamesAndEnvOptions", dataProviderClass = RunTests.class)
	public void CreateTest(String tcName, String browser, String browserVersion, String osVersion, String env){
		if(runRegularTest()){
			HashMap<String, String> testParameters = new HashMap<>();
			String uniqueName = tcName.trim() + "_" + browser.trim() + "_"+ browserVersion.trim() + "_"+ osVersion.trim();
			testParameters.put("tcName", tcName);
			testParameters.put("browser", browser);
			testParameters.put("browserVersion", browserVersion);
			testParameters.put("osVersion", osVersion);
			testParameters.put("UserId", "");
			super.runTest(tcName, uniqueName, env, "org.staw.framework.ExecuteTests", testParameters);
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
		return Initialize.getInstance().isConnectionPoolCreated() && TestSetupHelper.getRunJob().equalsIgnoreCase("regularjob");
	}
	
	
	
	@DataProvider()
	public static Object[][] getTestCaseNamesAndEnvOptions() throws IOException {
		
		TestSetupHelper.priliminaryCheck();		
		return TestSetupHelper.getParameters(retrieveTestCasesToExecute(), TestSetupHelper.getEnvironmentOptions());
			
	}
		
	
	
	public static List<String> retrieveTestCasesToExecute() throws IOException {
		List<String> listOfTests = new ArrayList<>();
		
		String testCases = TestSetupHelper.getTestCases();
		if(testCases.contains(",")){
			String[] tCases = testCases.split(",");
			for(String tCase: tCases){
				listOfTests.add(tCase.trim());
			}
		}else{
			listOfTests.add(testCases);
		}
		
		return listOfTests;
	}
	
	
	
	
	
}
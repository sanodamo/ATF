package org.staw.framework.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.staw.framework.constants.BrowserTargetType;
import org.staw.framework.constants.EnviromentProperties;
import org.staw.framework.models.EnvironmentOptions;

public class TestSetupHelper extends ConfigPropertiesHelper{
	private static Logger log = Logger.getLogger(TestSetupHelper.class.getName());
	private static Properties setupHelperPropFile;
	
	
	public TestSetupHelper() {
		setupHelperPropFile = getConfigProperties();
	}

	
	
	@Override
	Properties getConfigProperties(String filename) {		
		return null;
	}

	@Override
	Properties getConfigProperties() {
		return openAndReadPropFile("config");
	}

	@Override
	String getProperty(String prop) {
		try{
			String propVal = setupHelperPropFile.getProperty(prop).toString();
			return propVal;
		}catch(Exception e){
			return "";
		}		
	}
	
	
	public static String getRunEnvironemnt() throws Exception{
		int executionEnvs=BrowserTargetType.values().length;
		String[] validEnv = new String[executionEnvs];
		for(int i=0; i<executionEnvs;i++)
			validEnv[i] = BrowserTargetType.values()[i].getTargetType().toUpperCase();
		if(Arrays.asList(validEnv).contains(setupHelperPropFile.getProperty("RunningEnvironment").toUpperCase().trim())){
			return setupHelperPropFile.getProperty("RunningEnvironment");
		}
		log.error("INVALID RUN ENVIRONMENT. PLEASE DOUBLE CHECK THE 'RunningEnvironment' IN THE CONFIG.PROPERTIES.");
		throw new Exception();
	}
	
	public static String getRunJob(){
		String runningJob = "RegularJob";
		try{
			switch(setupHelperPropFile.getProperty("RunJob").toLowerCase().trim().replace(" ", "")){
			default:
				runningJob = "RegularJob";
				break; 
			}

		}catch(Exception e) 
		{
			e.printStackTrace();
			log.error("Unable to get the Job type: " + e.getMessage());
		}

		return runningJob;

	}
	
	public static String getTestCases(){
		return setupHelperPropFile.getProperty("RunTest");
	}	
	
	public static String getLevel() throws Exception{
		try{
			String lvl = setupHelperPropFile.getProperty("Level");
			EnviromentProperties.RunningEnvironment.getVariable(lvl);
			return lvl;
		}catch(Exception e){
			throw new Exception("INVALID LELEL TYPE");
		}
	}
	
	
	public static List<EnvironmentOptions> getEnvironmentOptions() {
		List<EnvironmentOptions> listOfEnv = new ArrayList<EnvironmentOptions>();
		String runEnvNumber = getRunEnvNumber();
		String[] envOptions = setupHelperPropFile.getProperty("EnvironmentOptions"+runEnvNumber).split(",");
		if (ArrayUtils.isNotEmpty(envOptions)) {
			for (String option: envOptions) {
				String[] tempEnvOptions = getEnvOptions(option);
				if (tempEnvOptions.length == 3) {
					listOfEnv.add(new EnvironmentOptions(tempEnvOptions[0],
							tempEnvOptions[1], tempEnvOptions[2]));
				}
			}
		}
		return listOfEnv;
	}
	
	private static String getRunEnvNumber() {
		String runEnvNum = "";
		try {
			runEnvNum = getEnvironmentCombination();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return runEnvNum;
	}
	
	public static String getEnvironmentCombination(){
		try{
			String env = setupHelperPropFile.getProperty("RunEnvironment");
			
			return env;
		}catch(Exception e){
			throw new NumberFormatException("RUN COMBINATION IN CONFIG IN NOT A DIGIT");
		}
	}
	
	private static String[] getEnvOptions(String option){
		String[] envOptions = new String[3];
		envOptions[0] = "chrome";
		envOptions[1] = "59.0";
		envOptions[2] = "Windows 10";
				
		return envOptions;
	}
	
	
	public static Object[][] getParameters(List<String> tcName,
			List<EnvironmentOptions> EOValues) {
		String env = null;
		try {
			env = getRunEnvironemnt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		int noOfBrowsers = getNumberOfBrowsers(EOValues, env);
		
		Object[][] testCaseParameters = new Object[tcName.size() * noOfBrowsers][5];
		int count = 0;
		for (int j = 0; j < tcName.size(); j++) {
			for (int i = 0; i < noOfBrowsers; i++) {
				testCaseParameters[count][0] = tcName.get(j);
				testCaseParameters[count][1] = EOValues.get(i).getBrowser();
				testCaseParameters[count][2] = EOValues.get(i).getBrowserVersion();
				testCaseParameters[count][3] = EOValues.get(i).getOsVersion();
				testCaseParameters[count][4] = env;
				count += 1;
			}
		}
		
		return testCaseParameters;
	}
	
	
	private static int getNumberOfBrowsers(List<EnvironmentOptions> EOValues, String env){
		int noOfBrowsers = 1;
		//To-Do
		return noOfBrowsers;
	}
}

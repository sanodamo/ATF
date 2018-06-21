package org.staw.framework.helpers;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.staw.datarepository.dao.TestContext.TestContextProvider;
import org.staw.datarepository.dao.TestData.TestData;
import org.staw.datarepository.dao.TestData.TestDataProvider;
import org.staw.framework.constants.BrowserType;
import org.staw.framework.constants.DriverVariables;
import org.staw.framework.constants.EnviromentProperties;
import org.staw.framework.constants.ExecutionEnvironment;
import org.staw.framework.constants.GlobalConstants;
import org.staw.framework.models.EnvironmentOptions;
import org.staw.framework.models.GlobalVariables;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TestSetupHelper extends ConfigHelper{
	private static Logger logger = Logger.getLogger(TestSetupHelper.class.getName());
	private static Properties PropFile;
	private static boolean filefound = false;
	
	
	public TestSetupHelper() {
		PropFile = getConfigProperties();
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
			String propVal = PropFile.getProperty(prop).toString();
			return propVal;
		}catch(Exception e){
			return "";
		}		
	}
	
	public static String getRemoteHub() {
		return PropFile.getProperty("RemoteHub");
	}
	
	public static String getRunEnvironemnt() throws Exception{
		int executionEnvs=ExecutionEnvironment.values().length;
		String[] validEnv = new String[executionEnvs];
		for(int i=0; i<executionEnvs;i++)
			validEnv[i] = ExecutionEnvironment.values()[i].getLocation().toUpperCase();
		if(Arrays.asList(validEnv).contains(PropFile.getProperty("ExecutionEnvironment").toUpperCase().trim())){
			return PropFile.getProperty("ExecutionEnvironment");
		}
		logger.error("Invalid ExecutionEnvironment");
		throw new Exception();
	}
	
	
	
	public static String getTestCases(){
		return PropFile.getProperty("RunTest");
	}	
	
	public static String getTargetEnvironment() throws Exception{
		try{
			String env = PropFile.getProperty("TargetEnvironment");
			EnviromentSetupHelper.RunningEnvironment.getVariable(env);
			return env;
		}catch(Exception e){
			throw new Exception("Invalid Target Environment !!!");
		}
	}
	
	public static String getWorkSpace() {
		if (System.getenv("WORKSPACE") == null|| System.getenv("WORKSPACE").isEmpty())
			return System.getProperty("user.dir");
		else if (!System.getenv("WORKSPACE").contains("STAW"))
			return System.getenv("WORKSPACE")+ "//STAW";
		else
			return System.getenv("WORKSPACE");
	}
	
	private static File getTestCasePath(String tcFileName){
		File fileToFind = null;
		File folder = new File(getWorkSpace() + "//XML");
		File[] listOfFiles = folder.listFiles();
		
		fileToFind = getFile(FrameworkHelper.getSessionPid(), tcFileName, listOfFiles, false);
		
		return fileToFind;
	}
	
	public static List<EnvironmentOptions> getEnvironmentOptions() {
		List<EnvironmentOptions> listOfEnv = new ArrayList<EnvironmentOptions>();
		String runEnvNumber = getRunEnvNumber();
		String[] envOptions = PropFile.getProperty("BrowserOptions"+runEnvNumber).split(",");
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
			runEnvNum = getTargetedEnvironment();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return runEnvNum;
	}
	
	public static String getTargetedEnvironment(){
		try{
			String env = PropFile.getProperty("BrowserCategory");
			
			return env;
		}catch(Exception e){
			throw new NumberFormatException("error in getting targetted environment details from properties file");
		}
	}
	
	private static String[] getEnvOptions(String option){
		if(option.isEmpty()) {return null;}
		return option.split(":");		
	}
	
	public static Object[][] getParameters() throws IOException{
		return getParameters(getTestCasesToExecute(),getEnvironmentOptions());
	}
	
	public static Object[][] getParameters(List<String> tcName,
			List<EnvironmentOptions> EnvironmentValues) {
		String env = null;
		try {
			env = getRunEnvironemnt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		int noOfBrowsers = getNumberOfBrowsers(EnvironmentValues, env);
		
		Object[][] testCaseParameters = new Object[tcName.size() * noOfBrowsers][5];
		int count = 0;
		for (int j = 0; j < tcName.size(); j++) {
			for (int i = 0; i < noOfBrowsers; i++) {
				testCaseParameters[count][0] = tcName.get(j);
				testCaseParameters[count][1] = EnvironmentValues.get(i).getBrowser();
				testCaseParameters[count][2] = EnvironmentValues.get(i).getBrowserVersion();
				testCaseParameters[count][3] = EnvironmentValues.get(i).getOsVersion();
				testCaseParameters[count][4] = env;
				count += 1;
			}
		}
		
		return testCaseParameters;
	}
	
	public static String getCurrentUser() {
		try {
			return System.getProperty("user.name");
		} catch (Exception e) {
			return "N/A";
		}					
	}
	
	public static String getCurrentHostName() {
		try {
			java.net.InetAddress localMachine = java.net.InetAddress.getLocalHost();
			return localMachine.getHostName();
		} catch (Exception e) {
			return "N/A";
		}					
	}
	
	private static int getNumberOfBrowsers(List<EnvironmentOptions> EOValues, String env){
		int noOfBrowsers = 1;
		//To-Do
		return noOfBrowsers;
	}
	
	public static File getFile(String pid, String tcName, File[] files, boolean runUpdate) {
		filefound = false;
		File file = null;
		if (files != null) {
			for (File fileOrDirectory: files) {
				if (fileOrDirectory.isDirectory()) {
					file = getFile(pid, tcName, fileOrDirectory.listFiles(), runUpdate);
					if (filefound) break;
				} else {
					if (fileOrDirectory.getName().equalsIgnoreCase(tcName)) {
						file = fileOrDirectory;						
						filefound = true;
						break;
					}
				}
			}
		}
		return file;
	}
	
	public static ArrayList<String> getParametersValue(ArrayList<String> args) {
		ArrayList<String> argValue = new ArrayList<String>();
		
		Field constantsField = null;	
		String testData;
				
		for (String arg: args) {
			arg = arg.trim();
			try {
															
				try{
					constantsField = EnviromentProperties.class.getDeclaredField(arg);
					if(constantsField != null) {
						argValue.add((String) constantsField.get(null));
						continue;
					}
					
				}catch(Exception e){}
				
				try {
					if(!arg.isEmpty()) {
						
						TestData predefinedData = TestDataProvider.GetValue(arg);
						testData = predefinedData.getValue();
						
						if(!testData.isEmpty()) {
							argValue.add(testData);
							continue;
						}
					}
				}
				catch(Exception e) {}
				
												
				argValue.add(arg);
				
			} catch (Exception e) {				
				argValue.add(arg);
				logger.error("Please provide a valid parameter. Unable to parse parameter: " + arg);				
			}
		}
		
		return argValue;
	}
	

	public static boolean validateSchema(File xml) throws SAXException, IOException, ParserConfigurationException {
		try {
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(new File(DriverVariables.getFilePath(DriverVariables.XSD)));
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(xml));
		} catch (IOException e) {
			logger.error("Exception: " + e.getMessage());
			return false;
		} catch (SAXException e1) {
			logger.error("SAX Exception: " + e1.getMessage());
			return false;
		}
		return true;
	}
	
	public static NodeList readKeywords(String testCaseFileName) {
		NodeList nList = null;
		try {
			File KeywordFile = getTestCasePath(testCaseFileName);
			
			if (KeywordFile == null){
				logger.error("Unable to find test case with name: " + testCaseFileName);
				return nList;
			}
			try {
				if (!validateSchema(KeywordFile))
					return nList;
			} catch (SAXException | IOException | ParserConfigurationException e) {
				logger.error(e.getMessage());
				return nList;
			}
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(KeywordFile);
			doc.getDocumentElement().normalize();
			nList = doc.getElementsByTagName("KeyWord");
			
		} catch (Exception e) {
			logger.error("Unable to read xml document, Error: " + e.getMessage());
			return null;
		}
		return nList;
	}
	
	public static void buildEnvironment() {
			
		String var1 = "";
				
			String[] runEnv = null;
			try {
				runEnv = getTargetEnvironment().split(",");
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (runEnv.length == 1) {
				var1  = runEnv[0];
			} else if (runEnv.length == 2) {
				var1 = runEnv[0];				
			} else if (runEnv.length == 3) {
				var1 = runEnv[0];				
			}
		
		GlobalVariables.runEnvironment.put(GlobalConstants.RunEnvironment.RUN_ENVIRONMENT_PROP, var1);	
	}
	
	public static List<String> getTestCasesToExecute() throws IOException {
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

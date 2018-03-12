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
import org.staw.datarepository.DataLibrary;
import org.staw.datarepository.DataLibrary.ReportType;
import org.staw.framework.FrameworkHelper;
import org.staw.framework.constants.BrowserTargetType;
import org.staw.framework.constants.DriverVariables;
import org.staw.framework.constants.EnviromentProperties;
import org.staw.framework.constants.GlobalConstants;
import org.staw.framework.models.EnvironmentOptions;
import org.staw.framework.models.GlobalVariables;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TestSetupHelper extends ConfigHelper{
	private static Logger logger = Logger.getLogger(TestSetupHelper.class.getName());
	private static Properties setupHelperPropFile;
	private static boolean filefound = false;
	
	
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
		logger.error("INVALID RUN ENVIRONMENT. PLEASE DOUBLE CHECK THE 'RunningEnvironment' IN THE CONFIG.PROPERTIES.");
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
			logger.error("Unable to get the Job type: " + e.getMessage());
		}

		return runningJob;

	}
	
	public static String getTestCases(){
		return setupHelperPropFile.getProperty("RunTest");
	}	
	
	public static String getLevel() throws Exception{
		try{
			String lvl = setupHelperPropFile.getProperty("Level");
			EnviromentSetupHelper.RunningEnvironment.getVariable(lvl);
			return lvl;
		}catch(Exception e){
			throw new Exception("INVALID LELEL TYPE");
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
	
	private static ArrayList<String> splitArgument(String arg){
		ArrayList<String> argumentList = new ArrayList<>();
		String[] args = arg.split(",");
		for(String ar: args){
			argumentList.add(ar);
		}
		return argumentList;
	}
	
	public static ArrayList<String> getParametersValue(ArrayList<String> args) {
		ArrayList<String> argValue = new ArrayList<String>();
		Field masterHashMapField = null;
		Field constantsVarField = null;
		boolean foundInHashMap;
		boolean foundInConstVar;
		boolean specialArgs=false;
		if(args.size()==1&&args.get(0).contains(",")){
			args = splitArgument(args.get(0));
			specialArgs=true;
		}
		for (String arg: args) {
			arg = arg.trim();
			try {
				try{
					masterHashMapField = GlobalConstants.MasterConstant.class.getField(arg);
					foundInHashMap = true;
				}catch(Exception e){foundInHashMap = false;}
				try{
					constantsVarField = EnviromentProperties.class.getDeclaredField(arg);
					foundInConstVar = true;
				}catch(Exception e){foundInConstVar = false;}
				
				if(foundInHashMap){
					//argValue.add();
				}else if(foundInConstVar){
					argValue.add((String) constantsVarField.get(null));
				}
				
			} catch (Exception e) {				
				argValue.add(arg);
				logger.error("Please provide a valid argument. Unable to parse argument: " + arg);				
			}
		}
		if(specialArgs){
			StringBuffer sb = new StringBuffer();
			for(int i=0; i<argValue.size();i++){
				sb.append(argValue.get(i)+((i!=(argValue.size()-1)?",":"")));
			}
			argValue = new ArrayList<>();
			argValue.add(sb.toString());
		}
		return argValue;
	}
	

	public static boolean validateXml(File xml) throws SAXException, IOException, ParserConfigurationException{
		try {
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory
					.newSchema(new File(DriverVariables.getFilePath(DriverVariables.XSD)));
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
	
	public static NodeList readTestCase(String tcFileName) {
		NodeList nList = null;
		try {
			File fXmlFile = getTestCasePath(tcFileName);
			if (fXmlFile == null){
				logger.error("Unable to find test case with name: " + tcFileName);
				return nList;
			}
//			try {
//				if (!validateXml(fXmlFile))
//					return nList;
//			} catch (SAXException | IOException | ParserConfigurationException e) {
//				logger.error(e.getMessage());
//				return nList;
//			}
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			nList = doc.getElementsByTagName("KeyWord");
			int totalSteps = doc.getElementsByTagName("KeyWord").getLength();
			DataLibrary.setValue(ReportType.REPORT_TABLE, GlobalConstants.MasterConstant.FM_TOTAL_EXECUTION_STEPS, Integer.toString(totalSteps));
			
		} catch (Exception e) {
			logger.error("Unable to read xml document, Error: " + e.getMessage());
			return null;
		}
		return nList;
	}
	
	public static void priliminaryCheck() {
			
		String var1 = "";
		String var2 = "";
		String var3 = "";
		
			String[] runEnv = null;
			try {
				runEnv = getLevel().split(",");
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (runEnv.length == 1) {
				var1 = var2 = var3 = runEnv[0];
			} else if (runEnv.length == 2) {
				var1 = runEnv[0];
				var2 = var3 = runEnv[1];
			} else if (runEnv.length == 3) {
				var1 = runEnv[0];
				var2 = runEnv[1];
				var3 = runEnv[2];
			}
		
		GlobalVariables.runEnvironment.put(GlobalConstants.RunEnvironment.RUN_ENVIRONMENT_PROP_SHEET, var1);
		GlobalVariables.runEnvironment.put(GlobalConstants.RunEnvironment.TEST_CASES_TO_RUN, var2);
		GlobalVariables.runEnvironment.put(GlobalConstants.RunEnvironment.USER_NAME_AND_PASSWORD, var3);
	}
}

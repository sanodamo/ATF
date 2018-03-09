package org.staw.framework.helpers;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.staw.datarepository.DataLibrary.ReportType;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.staw.datarepository.DataLibrary;
import org.staw.framework.CustomizedActions;
import org.staw.framework.FrameworkHelper;
import org.staw.framework.SeleniumDriver;
import org.staw.framework.SoftAssertion;
import org.staw.framework.constants.EnviromentProperties;
import org.staw.framework.constants.DriverVariables;
import org.staw.framework.constants.GlobalVariables;
import org.staw.framework.constants.GlobalConstants;

public class Utilities {
	public static Logger logger = Logger.getLogger(Utilities.class.getName());
	private static SoftAssertion myAssert;
	private static boolean filefound = false;
	
	
	private enum BROWSER_READY_STATE{
		LOADING, INTERACTIVE, COMPLETE;
	}
	public Utilities(SoftAssertion sr) {
		myAssert = sr;
	}
	
	public static String getWorkSpace() {
		if (System.getenv("WORKSPACE") == null|| System.getenv("WORKSPACE").isEmpty())
			return System.getProperty("user.dir");
		else if (!System.getenv("WORKSPACE").contains("Gcom_Automation"))
			return System.getenv("WORKSPACE")+ "//ATF";
		else
			return System.getenv("WORKSPACE");
	}
	
	private void theadSleep(long time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {
			logger.error("Unable to sleep, " + e.toString());
		}
	}
	
	private boolean isBrowserReady(BROWSER_READY_STATE state) {
		WebDriver driver = SeleniumDriver.getInstance().getWebDriver();
		
		long timeSliceMs = Long.parseLong(EnviromentProperties.TIME_SLICE_MS);
		int timeOutSecond = Integer.parseInt(EnviromentProperties.TIME_OUT_SECONDS);
		String readyState = null;
		
		do {
			theadSleep(timeSliceMs);
			timeOutSecond -= (timeSliceMs / 1000);
			readyState = (String) new WebDriverWait(driver, 1000)
					.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState"));
		} while(timeOutSecond > 0 && !StringUtils.equalsIgnoreCase(readyState, state.toString()));
	
		return readyState.equals(state.name().toLowerCase());
	}
	
	public boolean syncBrowser() {
		try {
			WebDriver driver = SeleniumDriver.getInstance().getWebDriver();
			long previous = 0;
			long current = 0;

			long timeSliceMs = Long.parseLong(EnviromentProperties.TIME_SLICE_MS);
			int timeOutSecond = Integer.parseInt(EnviromentProperties.TIME_OUT_SECONDS);
			String script = "return document.getElementsByTagName('*').length";
			isBrowserReady(BROWSER_READY_STATE.COMPLETE);
			do {
				previous = current;
				theadSleep(timeSliceMs);
				timeOutSecond -= (timeSliceMs / 1000);
				current = (long) CustomizedActions.executeJavaScript(driver, script);
			} while (current > previous && timeOutSecond > 0);
			SeleniumDriver.getJavaScriptErrors();
			return true;
		} catch (Exception e) {
			logger.error(
					"Sync Browser Failed to sync the browser. Either the browseer has been closed or is unreachable. Session information: "
							);
		}
		return false;
	}
	
	public static String getTestRunningEnvironmentVariable(){
		return GlobalVariables.runEnvironment.get(GlobalConstants.RunEnvironment.RUN_ENVIRONMENT_PROP_SHEET);
	}
	
	public static void priliminaryCheck() {
		new TestSetupHelper();		
		String var1 = "";
		String var2 = "";
		String var3 = "";
		
			String[] runEnv = null;
			try {
				runEnv = TestSetupHelper.getLevel().split(",");
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
	
	public NodeList readTestCase(String tcFileName) {
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
			myAssert.Failed("Unable to read xml document, Error: " + e.getMessage());
			return null;
		}
		return nList;
	}
	
	private static File getTestCasePath(String tcFileName){
		File fileToFind = null;
		File folder = new File(Utilities.getWorkSpace() + "//XML");
		File[] listOfFiles = folder.listFiles();
		
		fileToFind = getFile(FrameworkHelper.getSessionPid(), tcFileName, listOfFiles, false);
		
		return fileToFind;
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
	
}

package org.staw.framework;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.staw.datarepository.DataLibrary.ReportType;
import org.staw.datarepository.DataLibrary;
import org.staw.framework.constants.GlobalConstants;
import org.staw.framework.models.Keywords;
import org.staw.framework.helpers.TestSetupHelper;

import org.staw.framework.constants.BrowserTargetType;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class KeywordRunner {

	private static SoftAssertion myAssert;
	private static SeleniumDriver sd = new SeleniumDriver();
	private static String sessionId;

	public KeywordRunner(SoftAssertion sr) {
		myAssert = sr;
	}

	public static Logger log = Logger.getLogger(KeywordRunner.class.getName());
	
	private static String getExecutionEnv(){
		try {
			return TestSetupHelper.getRunEnvironemnt();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static boolean keywordResult(ArrayList<String> argList, Class<?> className, String methodName) {
		int argListSize = 0;
		if (argList != null)
			argListSize = argList.size();
		boolean keywordResult = false;
		Class<?>[] paramType;
		Object[] methodArguments;
		int methodParameters = 0;
		boolean createConstWithNoArg = false;
		Constructor<?> constructor = null;
		Class<?>[] argTypes = { SoftAssertion.class };
		try {
			constructor = className.getDeclaredConstructor(argTypes);
		} catch (NoSuchMethodException | SecurityException e) {
			log.info("Unable to create Constructor with SoftAssertion. Trying Constructor with no Args for Method: " + methodName);
			try{
				constructor = className.getConstructor();
				createConstWithNoArg = true;
			}catch(NoSuchMethodException | SecurityException e2){
				log.error("Unable to create Constructor with no args.");
				e2.printStackTrace();
			}
		}
		Object[] arguments = { myAssert };
		Method[] methods = className.getDeclaredMethods();
		for (Method meth : methods) {
			if (meth.getName().equalsIgnoreCase(methodName.replace("_", ""))) {
				methodParameters = meth.getParameters().length;
				methodArguments = new Object[methodParameters + 1];
				paramType = new Class<?>[methodParameters];
				if (argListSize != methodParameters) {
					return myAssert.Failed("Expected " + methodParameters + " argument(s) found " + argListSize + " argument(s)");
				}
				try {
					for (int i = 0; i < meth.getParameterTypes().length; i++) {
						paramType[i] = meth.getParameterTypes()[i];
					}
					if(createConstWithNoArg){
						methodArguments[0] = constructor.newInstance();
					}else{
						methodArguments[0] = constructor.newInstance(arguments);
					}
					if (methodParameters != 0) {
						for (int i = 1; i <= argListSize; i++) {
							methodArguments[i] = argList.get(i - 1);
						}
					}
					MethodHandles.Lookup lookup = MethodHandles.publicLookup();
					MethodType methodType = MethodType.methodType(boolean.class, paramType);
					MethodHandle handle = lookup.findVirtual(className, meth.getName(), methodType);
					keywordResult = (boolean) handle.invokeWithArguments(methodArguments);
					
					break;
				} catch (Throwable e) {
					log.error("Error with Keyword: " + meth.getName().toUpperCase() + " Test Case  " );
					keywordResult = false;
					break;
				}
			}
		}

		if (keywordResult) {
			return true;
		} else {
			return false;
		}
	}
	

	private static ArrayList<String> getInitialInformationFromXml(int keywordCount, Node singleKeyword) {
		ArrayList<String> argumentList = new ArrayList<>();
		ArrayList<String> returnList = null;
		NodeList nodeArgs;
		String currKeyWord = singleKeyword.getAttributes().getNamedItem("label").getNodeValue();
		
		nodeArgs = singleKeyword.getChildNodes();
		if(nodeArgs.getLength() != 0){
			NodeList arguments = nodeArgs.item(1).getChildNodes();
			for(int i=0; i<arguments.getLength();i++){
				Node node = arguments.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					argumentList.add(node.getAttributes().getNamedItem("value").getNodeValue());
				}
			}
		}
		if (!argumentList.isEmpty())
			returnList = TestSetupHelper.getParametersValue(argumentList);
		else
			returnList = null;
		
		DataLibrary.setValue(ReportType.MASTER_TABLE, GlobalConstants.MasterConstant.FM_CURRENT_KEYWORD_STEP, Integer.toString(keywordCount));
		DataLibrary.setValue(ReportType.MASTER_TABLE, GlobalConstants.MasterConstant.FM_CURRENT_KEYWORD, currKeyWord);
		try {
			DataLibrary.setValue(ReportType.MASTER_TABLE, GlobalConstants.MasterConstant.FM_CURRENT_KEYWORD_PARAMETERS, returnList.toString().replace("[", "").replace("]", ""));
		} catch (Exception e) {
			DataLibrary.setValue(ReportType.MASTER_TABLE, GlobalConstants.MasterConstant.FM_CURRENT_KEYWORD_PARAMETERS, "");
		}
		DataLibrary.setValue(ReportType.STEP_LEVEL_REPORT, GlobalConstants.AssertionAndTestStepConstant.STEP_COUNT, Integer.toString(keywordCount));
		
		return returnList;
	}
	
	
	public boolean executeKeywords(NodeList keyWords) {
		Keywords[] loginFunctions = { Keywords.LOGIN };
	
		String strKeyword = "";
		boolean lastKeywordResult = false, currentKeywordResult = false;
		ArrayList<String> argList = null;
		Keywords kw;
		 
		int keywordCount =1;
		try {
			for (int i = 0; i < keyWords.getLength(); i++) {
				Node singleKeyword = keyWords.item(i);
				if (singleKeyword.getNodeType() == Node.ELEMENT_NODE) {
					if ((sessionId == null || sessionId.isEmpty()) && getExecutionEnv().contains(BrowserTargetType.REMOTE.getTargetType()))
						break;
					argList = getInitialInformationFromXml(keywordCount, singleKeyword);
					strKeyword = singleKeyword.getAttributes().getNamedItem("label").getNodeValue().toUpperCase();
					kw = Keywords.getKeyword(strKeyword);
					
					if (kw != null) {
						switch (kw) {
						
						case INITIALROUTINES:
						case INITIALIZEDATA:
							currentKeywordResult = true;
														
							break;

						case SETGLOBALVARIABLES:
						case SETGLOBALVARBYEXPRESSION:
							currentKeywordResult = true;
							break;

						default:
							try {
								currentKeywordResult = keywordResult(argList, kw.getClassName(), kw.name());
							} catch (Throwable e) {
								log.error("Keyword failed to execute: " + kw.getName() + ". Localized Message: " + e.getLocalizedMessage() + " Message: "
										+ e.getMessage());
								e.printStackTrace();
							}
							break;
						}
					}
					
					myAssert.assertTrue(currentKeywordResult);
					if((lastKeywordResult == false && currentKeywordResult == false) && i>0) break;
					if(currentKeywordResult == false && Arrays.asList(loginFunctions).contains(kw)) break;
					lastKeywordResult = currentKeywordResult;
					myAssert.Success("");
					myAssert.Failed("");
					keywordCount++;
				}
			}
		} catch (Exception e) {
			return myAssert.Failed("Error parsing keyword: " + e.getMessage());
		}
		return currentKeywordResult;
	}
}

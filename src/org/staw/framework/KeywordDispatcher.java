package org.staw.framework;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.staw.datarepository.dao.TestContext.TestContextProvider;
import org.staw.framework.constants.GlobalConstants;
import org.staw.framework.helpers.StringExtensions;
import org.staw.framework.helpers.TestSetupHelper;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class KeywordDispatcher {

	private static CommonAssertion commonAssertion;
	public KeywordDispatcher(CommonAssertion commonAssertion) {
		this.commonAssertion = commonAssertion;
	}

	public static Logger log = Logger.getLogger(KeywordDispatcher.class.getName());
	
	private static Constructor<?> getContructor(Class<?> className) {
		Constructor<?> constructor = null;
		Class<?>[] argTypes = { CommonAssertion.class };
				
		switch(className.toString()) {
			default:
				//argTypes[0] = CommonAssertion.class;
				break;
		}
		
		try {
			
			constructor = className.getDeclaredConstructor(argTypes);
			
		} catch (NoSuchMethodException | SecurityException e) {
			log.info("Failed to create Constructor with CommonAssertion. Trying Constructor with no Args for class: " + className.toString());
			try{
				constructor = className.getConstructor();		
			}catch(NoSuchMethodException | SecurityException ex){
				log.error("Unable to create Constructor with no args.");
				ex.printStackTrace();
			}
		}
		
		return constructor;
	}
	
	private static boolean isEmptyConstructor(Class<?> classes) {
	    for (Constructor<?> constructor : classes.getConstructors()) {	       
	        if (constructor.getParameterCount() == 0) { 
	            return true;
	        }
	    }
	    return false;
	}
	
	
	private static boolean InvokeMethod(ArrayList<String> argList, Class<?> className, String methodName) {
		
		int argListSize = argList != null ? argList.size() : 0;
		
		boolean iskeywordExecuted = false;
		Class<?>[] paramType;
		Object[] methodArguments;
		int methodParameters = 0;
					
		Constructor<?> constructor = getContructor(className);
		
		boolean isEmptyContructor = isEmptyConstructor(className);
		
		Object[] arguments = { commonAssertion };
		
		Method[] methods = className.getDeclaredMethods();
		
		for (Method method : methods) {
			
			if (StringExtensions.compareMethodName(method.getName(), methodName)) {
				
				methodParameters = method.getParameters().length;
				methodArguments = new Object[methodParameters + 1];
				paramType = new Class<?>[methodParameters];
				
				if (argListSize != methodParameters) {
					return commonAssertion.Failed("Expected " + methodParameters + " argument(s) found " + argListSize + " argument(s)");
				}
				
				try {
					for (int i = 0; i < method.getParameterTypes().length; i++) {
						paramType[i] = method.getParameterTypes()[i];
					}
					if(isEmptyContructor){
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
					MethodHandle handle = lookup.findVirtual(className, method.getName(), methodType);
					iskeywordExecuted = (boolean) handle.invokeWithArguments(methodArguments);
					
					break;
				} catch (Throwable e) {
					log.error("Error with Keyword: " + method.getName().toUpperCase() + " Test Case  " );
					iskeywordExecuted = false;
					break;
				}
			}
		}

		return iskeywordExecuted;
	}
	

	private static ArrayList<String> getKeywordDataFromXml(int keywordCount, Node singleKeyword) {
		ArrayList<String> argumentList = new ArrayList<>();
		ArrayList<String> returnList = null;
		NodeList nodeArgs;
		String currKeyWord = singleKeyword.getAttributes().getNamedItem("method").getNodeValue();
		
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
				
		TestContextProvider.SetValue(GlobalConstants.ContextConstant.CURRENT_KEYWORD_STEP, Integer.toString(keywordCount));
		TestContextProvider.SetValue(GlobalConstants.ContextConstant.CURRENT_KEYWORD, currKeyWord);
		TestContextProvider.SetValue(GlobalConstants.ContextConstant.CURRENT_KEYWORD_PARAMETERS,
				returnList != null && returnList.size() > 0 ? returnList.toString().replace("[", "").replace("]", ""): "");
				
		
		return returnList;
	}
	
	
	public boolean InvokeKeywordMethods(NodeList keyWords) {		
		String methodName = "";		
		Class<?> keywordClass = null;
		
		boolean lastKeywordResult = false, currentKeywordResult = false;
		ArrayList<String> argList = null;
				 
		int keywordCount =1;
		try {
			for (int i = 0; i < keyWords.getLength(); i++) {
				Node singleKeyword = keyWords.item(i);
				if (singleKeyword.getNodeType() == Node.ELEMENT_NODE) {					
					argList = getKeywordDataFromXml(keywordCount, singleKeyword);
					methodName = singleKeyword.getAttributes().getNamedItem("method").getNodeValue().toUpperCase();
					Node classAttr =  singleKeyword.getAttributes().getNamedItem("class");
					if(classAttr != null) {
						keywordClass = Class.forName(classAttr.getNodeValue());
					}
														
					if (methodName != null) {
						switch (methodName) {
												
						case GlobalConstants.KeywordName.INITIALIZE:
							currentKeywordResult = true;													
							break;
						
						default:
							try {							
								currentKeywordResult = InvokeMethod(argList, keywordClass, methodName);
							} catch (Throwable e) {
								log.error("Keyword failed to execute: " + methodName + ". Message: " + e.getLocalizedMessage() + " Message: "
										+ e.getMessage());
								e.printStackTrace();
							}
							break;
						}
					}
					
					commonAssertion.assertTrue(currentKeywordResult);
					if((lastKeywordResult == false && currentKeywordResult == false) && i>0) break;					
					lastKeywordResult = currentKeywordResult;					
					keywordCount++;
				}
			}
		} catch (Exception e) {
			return commonAssertion.Failed("Error parsing keyword: " + e.getMessage());
		}
		return currentKeywordResult;
	}
}

package org.staw.framework.helpers;

public class StringExtensions {
	public static String removeSpace(String value) {
		if(!value.isEmpty()) {
			return value.replace(" ", "").toLowerCase();
		}
		return "";
	}
	
	public static boolean startWith(String lValue, String rValue) {
		if(lValue != null && rValue != null) {
			return lValue.startsWith(rValue);
		}
		return false;
	}
	
	public static boolean compareMethodName(String lvalue, String rvalue) {
		if(!rvalue.isEmpty()) {
			rvalue = rvalue.replace(" ", "").replace("_", "");
			return lvalue.equalsIgnoreCase(rvalue);
		}
		return false;		
	}
}

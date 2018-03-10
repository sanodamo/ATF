package org.staw.framework.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class GlobalVariables {
	public static String gblStrParameter1;
	public static String gblStrParameter2;
	
	public static int totalSuiteScripts;
	public static Map<String, HashMap<String, String>> pId = new ConcurrentHashMap<String, HashMap<String, String>>(totalSuiteScripts);
	public static Map<String, String> runEnvironment = new HashMap<>();	
}
package org.staw.framework;

import java.util.HashMap;

import org.staw.framework.models.GlobalVariables;


public class ThreadInformation {

	public static void initialize() {
		GlobalVariables.pId.put(Long.toString(Thread.currentThread().getId()), new HashMap<>());
	}

	public static String getValue(String key) {
		return GlobalVariables.pId.get(Long.toString(Thread.currentThread().getId())).get(key);
	}

	public static void setValue(String key, String value) {
		GlobalVariables.pId.get(Long.toString(Thread.currentThread().getId())).put(key, value);
	}

	public static boolean containsKey(String key) {
		if (GlobalVariables.pId.get(Long.toString(Thread.currentThread().getId())).containsKey(key))
			return true;
		else
			return false;
	}
}
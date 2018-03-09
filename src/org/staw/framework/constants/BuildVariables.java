package org.staw.framework.constants;

import org.staw.framework.FrameworkHelper;

public class BuildVariables {

	private static final String CURRENT_USER = System.getProperty("user.name");
	private static final String LOCAL_BUILD = "localBuild_" + CURRENT_USER + System.currentTimeMillis() + "_" +
	FrameworkHelper.getSessionPid();
	
	public static String getBuildFullKey() {
		return LOCAL_BUILD;
	}
}

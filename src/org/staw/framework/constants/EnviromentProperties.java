package org.staw.framework.constants;

import org.apache.log4j.Logger;
import org.staw.framework.helpers.EnviromentSetupHelper;

public class EnviromentProperties {
	private static Logger logger = Logger.getLogger(EnviromentProperties.class.getName());
	
	private static EnviromentSetupHelper csHelper = new EnviromentSetupHelper();
	public static final String RAD_IMASIS=csHelper.getProperty("RAD_IMASIS");
	public static final String TIME_SLICE_MS = csHelper.getProperty("TIME_SLICE_MS");
	public static final String TIME_OUT_SECONDS = csHelper.getProperty("TIME_OUT_SECONDS");
	public static final String IMASIS_RN_USER_ID=csHelper.getProperty("IMASIS_RN_USER_ID");
	public static final String IMASIS_RN_PASSWORD=csHelper.getProperty("IMASIS_RN_PASSWORD");
	
		
	
	
	
	
	
}

package org.staw.framework.helpers;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.staw.framework.constants.EnviromentProperties;


public class EnviromentPropertiesSetupHelper extends ConfigPropertiesHelper {
	private static Logger log = Logger.getLogger(EnviromentPropertiesSetupHelper.class.getName());
	private Properties constPropFile;

	public EnviromentPropertiesSetupHelper() {
		constPropFile = getConfigProperties();
	}
	

	Properties getConfigProperties() {
		return openAndReadPropFile(EnviromentProperties.runningEnvironmentProperty(Utilities.getTestRunningEnvironmentVariable()));
	}

	@Override
	public String getProperty(String prop) {
		try{
			String propVal = constPropFile.getProperty(prop).toString();
			return propVal;
		}catch(Exception e){
			return "";
		}
	}

	@Override
	Properties getConfigProperties(String filename) {
		// TODO Auto-generated method stub
		return null;
	}
}
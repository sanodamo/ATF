package org.staw.framework.helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class ConfigPropertiesHelper {
	
	abstract Properties getConfigProperties(String filename);
	
	abstract Properties getConfigProperties();
	
	abstract String getProperty(String prop);

	public static Properties openAndReadPropFile(String fileName){
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(TestSetupHelper.getWorkSpace()
					+ "//resources//properties//"+fileName+".properties");
			prop.load(input);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}
}

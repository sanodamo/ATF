package org.staw.framework.helpers;

import java.util.Properties;

import org.apache.log4j.Logger;


public class DatabaseHelper extends ConfigHelper {
	private String propFile;
	private Properties dbPropFile;
	private static Logger log = Logger.getLogger(DatabaseHelper.class.getName());
	public DatabaseHelper(String propFile) {
		setPropFile(propFile);
		dbPropFile = getConfigProperties();
	}
	
	public void setPropFile(String propFile){
		this.propFile = propFile;
	}
	
	public String getPropFile(){
		return this.propFile;
	}
	
	@Override
	Properties getConfigProperties() {
		return openAndReadPropFile(getPropFile());
	}

	@Override
	public String getProperty(String prop) {
		try{
			String propVal = dbPropFile.getProperty(prop).toString();
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
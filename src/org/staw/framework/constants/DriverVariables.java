package org.staw.framework.constants;

import java.io.File;

public enum DriverVariables {
	
	RESOURCES_FOLDER("\\resources"),
	CHROME_DRIVER_WINDOWS("drivers\\chromedriver_win32\\chromedriver.exe"),
	INTERNET_EXPLORER_DRIVER("drivers\\iedriver\\IEDriverServer.exe"),
	GECKO_DRIVER_WIN("\\Drivers\\GeckoDriver\\Windows\\geckodriver.exe"),	
	LOG4J_PROP("resources\\properties"),
	XSD("resources\\xsd\\KeywordSchema.xsd");
	
	private String path;
	private DriverVariables(String path){
		this.path = path;
	}
	public String getPath(){
		return this.path;
	}
	public static String getFilePath(DriverVariables resource){
		File file = new File(resource.getPath());
		return file.getAbsolutePath();
	}
	public static DriverVariables getValueOfProperties(String propValue){
		try{
			return DriverVariables.valueOf(propValue);
		}catch(Exception e){
			return null;
		}
	}
}

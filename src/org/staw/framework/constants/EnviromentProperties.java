package org.staw.framework.constants;

import org.apache.log4j.Logger;
import org.staw.framework.helpers.EnviromentPropertiesSetupHelper;

public class EnviromentProperties {
	private static Logger logger = Logger.getLogger(EnviromentProperties.class.getName());
	
	private static EnviromentPropertiesSetupHelper csHelper = new EnviromentPropertiesSetupHelper();
	public static final String RAD_IMASIS=csHelper.getProperty("RAD_IMASIS");
	public static final String TIME_SLICE_MS = csHelper.getProperty("TIME_SLICE_MS");
	public static final String TIME_OUT_SECONDS = csHelper.getProperty("TIME_OUT_SECONDS");
	public static final String IMASIS_USER_ID=csHelper.getProperty("IMASIS_USER_ID");
	public static final String IMASIS_PASSWORD=csHelper.getProperty("IMASIS_PASSWORD");
	
			
	private static String PROD = "prod";	
	private static String QA = "qa";
	private static String LOCAL_DEV = "local_dev";
	
	public enum RunningEnvironment{
		DEFAULT("No Value",EnviromentProperties.LOCAL_DEV),
		PROD("prod",EnviromentProperties.PROD),
		QA("qa",EnviromentProperties.QA),
		LOCAL_DEV("local_dev", EnviromentProperties.LOCAL_DEV);
		
		private String value;
		private String runEnv;
		
		private RunningEnvironment(String value, String runEnv){
			this.value = value;
			this.runEnv = runEnv;
		
		
		}
		public String getRunEnv(){
			return this.runEnv;
		}
		public String getVal(){
			return this.value;
		}
		
		public static RunningEnvironment getVariable(String var){
			try{
				return RunningEnvironment.valueOf(var.toUpperCase().replace("-", "_"));
			}catch(IllegalArgumentException | NullPointerException e){
				return null;
			}
		}
	}
	
	public static String runningEnvironmentProperty(String environment){
		RunningEnvironment currVar = (environment == null || environment.isEmpty()) ?   RunningEnvironment.DEFAULT : RunningEnvironment.getVariable(environment);
		try{
			return currVar.getRunEnv();
		}catch(Exception e){
			logger.fatal("NOT A VALID RUN ENIVRONMENT");
			return null;
		}
	}
	
	
	
}

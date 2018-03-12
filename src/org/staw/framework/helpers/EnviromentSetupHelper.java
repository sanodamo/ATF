package org.staw.framework.helpers;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.staw.framework.constants.GlobalConstants;
import org.staw.framework.models.GlobalVariables;;


public class EnviromentSetupHelper extends ConfigHelper {
	private static Logger log = Logger.getLogger(EnviromentSetupHelper.class.getName());
	private Properties constPropFile;

	
	public enum RunningEnvironment{
		DEFAULT("No Value",GlobalConstants.RunEnvironment.LOCAL_DEV),
		PROD("prod",GlobalConstants.RunEnvironment.PROD),
		QA("qa",GlobalConstants.RunEnvironment.QA),
		LOCAL_DEV("local_dev", GlobalConstants.RunEnvironment.LOCAL_DEV);
		
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
			log.fatal("NOT A VALID RUN ENIVRONMENT");
			return null;
		}
	}
	
	public EnviromentSetupHelper() {
		constPropFile = getConfigProperties();
	}
	
	public static String getTestRunningEnvironmentVariable(){
		return GlobalVariables.runEnvironment.get(GlobalConstants.RunEnvironment.RUN_ENVIRONMENT_PROP_SHEET);
	}
	
	Properties getConfigProperties() {
		return openAndReadPropFile(runningEnvironmentProperty(getTestRunningEnvironmentVariable()));
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
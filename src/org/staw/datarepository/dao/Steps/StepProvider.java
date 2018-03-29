package org.staw.datarepository.dao.Steps;

import org.apache.log4j.Logger;
import org.staw.framework.ExecutionIdentity;
import org.staw.framework.constants.GlobalConstants;


public class StepProvider {

	private static Logger logger = Logger.getLogger(StepProvider.class.getName());
	
	private static IStepDao stepDao;
		
	static {
		stepDao = new StepDao();
	}		

	public static boolean SetValue(Step step) {
		try {				
			String processId = ExecutionIdentity.getValue(GlobalConstants.ContextConstant.EXECUTION_ID);
			step.setProcessId(processId);			
			return stepDao.Create(step);
		} catch (Exception e) {
			logger.error("Error in setting step level values -->" + e.getMessage());
			return false;
		}		
		
	}	
	
	/*public static boolean SetValue(Step step, String prop) {
		
		String processId = ThreadInformation.getValue(GlobalConstants.MasterConstant.P_ID);
		step.setProcessId(processId);
		
		switch(prop) {
			case StepConstant.STEP_COUNT:				
				step.getExecutionTime(new Date());				
				break;
			
		}
		
		
	}*/
}

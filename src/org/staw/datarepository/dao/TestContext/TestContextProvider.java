package org.staw.datarepository.dao.TestContext;

import java.util.List;

import org.apache.log4j.Logger;
import org.staw.framework.ExecutionIdentity;
import org.staw.framework.constants.GlobalConstants;


public class TestContextProvider {
	private static Logger logger = Logger.getLogger(TestContextProvider.class.getName());
	
	private static ITestContextDao testContextDao;
	static {
		testContextDao = new TestContextDao();
	}		

	public static boolean SetValue(String name, String value) {
		try {
			TestContext context = new TestContext();			
			String processId = ExecutionIdentity.getValue(GlobalConstants.ContextConstant.EXECUTION_ID);
			context.setProcessId(processId);
			context.setName(name);
			context.setValue(value);
			return testContextDao.Create(context);
		} catch (Exception e) {
			logger.error("Failed to set value in TestContext table ---> " + e.getMessage());
			return false;
		}		
		
	}
	
	public static String GetValue(String name) {
		String value = null;
		try {			
			String processId = ExecutionIdentity.getValue(GlobalConstants.ContextConstant.EXECUTION_ID);
			List<TestContext> contextList = testContextDao.FindAll(processId, name);
			
			if(contextList != null) {
				for (TestContext item : contextList) {
					value = item.getValue();
				}				
			}
		} catch (Exception e) {
			logger.error("Failed to get value from TestContext table ---> " + e.getMessage());
			return null;
		}				
		return value;
		
	}
	
}

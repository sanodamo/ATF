package org.staw.datarepository.dao.TestRun;

import org.staw.framework.ExecutionIdentity;
import org.staw.framework.constants.GlobalConstants;

public class TestRunProvider {
	
	private static ITestRunDao testRunDao;
		
	static {
		testRunDao = new TestRunDao();
	}		

	public static boolean Save(TestRun test) {
		try {						
			String processId = ExecutionIdentity.getValue(GlobalConstants.ContextConstant.EXECUTION_ID);
			TestRun currentTest = GetCurrentTest();
			if(currentTest==null) {
				test.setProcessId(processId);
				return testRunDao.Create(test);
			}
			else {
				return testRunDao.Update(test);
			}
		} catch (Exception e) {
			return false;
		}		
		
	}
	
	public static TestRun GetCurrentTest() {
		try {			
			String processId = ExecutionIdentity.getValue(GlobalConstants.ContextConstant.EXECUTION_ID);
			return testRunDao.FindOne(processId);
		} catch (Exception e) {
			return null;
		}		
		
	}
	
}

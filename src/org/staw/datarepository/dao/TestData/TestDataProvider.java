package org.staw.datarepository.dao.TestData;

import org.apache.log4j.Logger;


public class TestDataProvider {
	private static Logger logger = Logger.getLogger(TestDataProvider.class.getName());
	private static ITestDataDao testDataDao;
	static {
		testDataDao = new TestDataDao();
	}		

	
	public static TestData GetValue(String name) {
		try {						
			return testDataDao.FindOne(name);
		} catch (Exception e) {
			logger.error("Failed to get value from Test Data table ---> " + e.getMessage());
			return null;
		}		
		
	}
	
}

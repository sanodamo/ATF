package org.staw.datarepository.dao.TestRun;

import org.staw.datarepository.DataAccessProvider;
import org.staw.datarepository.Queries;

public class TestRunDao implements ITestRunDao {
	
	@Override
	public boolean Create(TestRun testRun) {
		try {
			DataAccessProvider.getJdbcTemplate().update(Queries.CREATE_TEST_RUN.getSql(), 
					testRun.getProcessId(),
					testRun.getTestCaseName(),
					testRun.getBrowserName(),
					testRun.getBrowserVersion(),
					testRun.getOsName(),
					testRun.getUserId(),
					testRun.getHostName(),
					testRun.getStartDateTime()
			);
			return true;
		} catch (Exception e) {
			throw e;
		}				
	}

	@Override
	public boolean Update(TestRun testRun) {
		try {
			DataAccessProvider.getJdbcTemplate().update(Queries.UPDATE_TEST_RUN.getSql(), 					
					testRun.getTestCaseName(),
					testRun.getBrowserName(),
					testRun.getBrowserVersion(),
					testRun.getOsName(),
					testRun.getUserId(),
					testRun.getHostName(),
					testRun.getStartDateTime(),
					testRun.getProcessId()
			);
			return true;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public TestRun FindOne(String processId) {		
		return DataAccessProvider.getJdbcTemplate().queryForObject(Queries.GET_TEST_RUN.getSql(),new Object[] { processId }, new TestRunMapper());					
	}

}

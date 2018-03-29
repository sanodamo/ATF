package org.staw.datarepository.dao.TestContext;

import java.util.List;

import org.staw.datarepository.DataAccessProvider;
import org.staw.datarepository.Queries;

public class TestContextDao implements ITestContextDao {

	
	@Override
	public boolean Create(TestContext context) {
		try {
			DataAccessProvider.getJdbcTemplate().update(Queries.ADD_TEST_CONTEXT.getSql(), 
					context.getProcessId(),
					context.getName(),
					context.getValue()
					
			);
			return true;
		} catch (Exception e) {
			throw e;
		}		
	}

	@Override
	public boolean Update(TestContext context) {
		try {
			DataAccessProvider.getJdbcTemplate().update(Queries.UPDATE_TEST_CONTEXT.getSql(), 					
					context.getName(),
					context.getValue(),
					context.getProcessId()
			);
			return true;
		} catch (Exception e) {
			throw e;
		}	
	}

	@Override
	public List<TestContext> FindAll(String processId, String name) {		
		return DataAccessProvider.getJdbcTemplate().query(Queries.GET_TEST_CONTEXT.getSql(),new Object[] { processId, name }, new TestContextMapper());		
	}

}

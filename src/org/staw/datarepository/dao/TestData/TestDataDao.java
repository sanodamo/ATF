package org.staw.datarepository.dao.TestData;

import org.staw.datarepository.DataAccessProvider;
import org.staw.datarepository.Queries;

public class TestDataDao implements ITestDataDao {
	
	@Override
	public TestData FindOne(String name) {		
		return DataAccessProvider.getJdbcTemplate().queryForObject(Queries.GET_TEST_DATA.getSql(),new Object[] { name }, new TestDataMapper());					
	}

}

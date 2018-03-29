package org.staw.datarepository.dao.TestData;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class TestDataMapper implements RowMapper<TestData> {
	@Override
	public TestData mapRow(ResultSet rs, int rowNum) throws SQLException {
		TestData testData = new TestData();	
		testData.setGroup(rs.getString("KEY_GROUP"));
		testData.setName(rs.getString("KEY_NAME"));
		testData.setValue(rs.getString("VALUE"));		
	    return testData;		
	}

}

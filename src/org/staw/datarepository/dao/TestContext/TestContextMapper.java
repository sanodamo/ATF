package org.staw.datarepository.dao.TestContext;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TestContextMapper implements RowMapper<TestContext> {
	@Override
	public TestContext mapRow(ResultSet rs, int rowNum) throws SQLException {
		TestContext context = new TestContext();
		context.setProcessId(rs.getString("P_ID"));
		context.setName(rs.getString("NAME"));
		context.setValue(rs.getString("VAL"));		
	    return context;		
	}

}

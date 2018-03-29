package org.staw.datarepository.dao.TestRun;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TestRunMapper implements RowMapper<TestRun> {

	@Override
	public TestRun mapRow(ResultSet rs, int rowNum) throws SQLException {
		TestRun testRun = new TestRun();
		testRun.setProcessId(rs.getString("P_ID"));
		testRun.setTestCaseName(rs.getString("TESTNAME"));
		testRun.setBrowserName(rs.getString("BROWSER"));
		testRun.setBrowserVersion(rs.getString("BROWSER_VERSION"));
		testRun.setUserId(rs.getString("USERID"));
		testRun.setHostName(rs.getString("HOSTNAME"));
		testRun.setOsName(rs.getString("OS"));
		testRun.setStartDateTime(rs.getTimestamp("START_TIMESTAMP"));
	    return testRun;		
	}

}

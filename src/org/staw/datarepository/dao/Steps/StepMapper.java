package org.staw.datarepository.dao.Steps;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class StepMapper implements RowMapper<Step> {
	@Override
	public Step mapRow(ResultSet rs, int rowNum) throws SQLException {
		Step step = new Step();
		step.setProcessId(rs.getString("P_ID"));
		step.setExecutionTime(rs.getTimestamp("EXECUTION_TIME"));
		step.setStepNumber(rs.getInt("STEP_NUMBER"));
		step.setKeyword(rs.getString("KEYWORD"));
		step.setParameters(rs.getString("PARAMETERS"));
		step.setDescription(rs.getString("DESCRIPTION"));
		step.setResult(rs.getString("RESULT"));		
	    return step;		
	}

}

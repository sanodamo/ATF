package org.staw.datarepository.dao.Steps;

import java.util.List;

import org.staw.datarepository.DataAccessProvider;
import org.staw.datarepository.Queries;

public class StepDao implements IStepDao {
	
	@Override
	public boolean Create(Step step) {
		try {
			DataAccessProvider.getJdbcTemplate().update(Queries.ADD_TEST_STEP.getSql(), 
					step.getProcessId(),
					step.getExecutionTime(),
					step.getStepNumber(),
					step.getKeyword(),
					step.getParameters(),
					step.getDescription(),
					step.getResult()
			);
			return true;
		} catch (Exception e) {
			return false;
		}		
	}

	@Override
	public boolean Update(Step step) {
		try {
			DataAccessProvider.getJdbcTemplate().update(Queries.UPDATE_TEST_STEP.getSql(), 					
					step.getExecutionTime(),
					step.getStepNumber(),
					step.getKeyword(),
					step.getParameters(),
					step.getDescription(),
					step.getResult(),
					step.getProcessId()
			);
			return true;
		} catch (Exception e) {
			return false;
		}	
	}

	@Override
	public List<Step> FindAll(String processId) {
		try {
			return DataAccessProvider.getJdbcTemplate().query(Queries.GET_TEST_STEP.getSql(),new Object[] { processId }, new StepMapper());			
		} catch (Exception e) {
			return null;
		}
	}

}

package org.staw.datarepository.dao.Steps;

import java.util.List;

public interface IStepDao {
	public boolean Create(Step step);
	public boolean Update(Step step);
	public List<Step> FindAll(String processId);	
}

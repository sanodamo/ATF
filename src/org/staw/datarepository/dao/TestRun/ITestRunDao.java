package org.staw.datarepository.dao.TestRun;

public interface ITestRunDao {
	public boolean Create(TestRun testRun);
	public boolean Update(TestRun testRun);
	public TestRun FindOne(String processId);	
}

package org.staw.datarepository.dao.TestContext;

import java.util.List;

public interface ITestContextDao {
	public boolean Create(TestContext context);
	public boolean Update(TestContext context);
	public List<TestContext> FindAll(String processId, String name);
}

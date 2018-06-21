package org.staw.framework;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.staw.datarepository.DataAccessProvider;
import org.staw.framework.constants.DriverVariables;
import org.staw.framework.helpers.TestSetupHelper;
import org.testng.annotations.Test;

public class InitializeTestSuite {
	private boolean datasourceInitialized;
	private static InitializeTestSuite instance = new InitializeTestSuite();
	private Logger log = Logger.getLogger(InitializeTestSuite.class.getName());

	public static InitializeTestSuite getInstance() {
		return instance;
	}
	public boolean isDatasourceInitialized() {
		return datasourceInitialized;
	}

	public void setDatasourceStatus(boolean isCreated) {
		this.datasourceInitialized = isCreated;
	}

	@Test
	public void initialize() {
		try {
			PropertyConfigurator.configure(DriverVariables.getFilePath(DriverVariables.LOG4J_PROP));
			new TestSetupHelper();			
			getInstance().setDatasourceStatus(DataAccessProvider.initializeDatasource());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error Initializing data");
		}
	}
}

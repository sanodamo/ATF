package org.staw.framework;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.staw.datarepository.SqlDatabase;
import org.staw.framework.constants.DriverVariables;
import org.staw.framework.helpers.TestSetupHelper;
import org.testng.annotations.Test;

public class Initialize {
	private boolean connectionPoolCreated;
	private static Initialize instance = new Initialize();
	private Logger log = Logger.getLogger(Initialize.class.getName());

	public static Initialize getInstance() {
		return instance;
	}
	public boolean isConnectionPoolCreated() {
		return connectionPoolCreated;
	}

	public void setConnectionPoolCreated(boolean connectionPoolCreated) {
		this.connectionPoolCreated = connectionPoolCreated;
	}

	@Test
	public void initialize() {
		try {
			PropertyConfigurator.configure(DriverVariables.getFilePath(DriverVariables.LOG4J_PROP));
			new TestSetupHelper();
			getInstance().setConnectionPoolCreated(SqlDatabase.startConnectionPool());			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error Initializing data");
		}
	}
}

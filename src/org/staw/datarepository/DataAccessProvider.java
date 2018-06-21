
package org.staw.datarepository;

import java.sql.Connection;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.staw.framework.helpers.DatabaseHelper;

import com.zaxxer.hikari.HikariDataSource;

public class DataAccessProvider {
	
	private static Logger log = Logger.getLogger(DataAccessProvider.class.getName());
	private static HikariDataSource dataSource;

	
	public static JdbcTemplate getJdbcTemplate() {
		try {
			return new JdbcTemplate(dataSource);
		} catch (Exception e) {
			log.error("Error getting connection from connection pool");
			e.printStackTrace();
		}
		return null;
	}

	public static boolean initializeDatasource() {
		String location = "AutomationDatabase";
		dataSource = new HikariDataSource();

		try {
			DatabaseHelper dbHelper = new DatabaseHelper("database");
			dataSource.setDriverClassName(dbHelper.getProperty(location + ".driver"));
			dataSource.setJdbcUrl(dbHelper.getProperty(location + ".url"));
			dataSource.setUsername(dbHelper.getProperty(location + ".userName"));
			dataSource.setPassword(dbHelper.getProperty(location + ".password"));
			dataSource.setMaximumPoolSize(10);
			dataSource.setMaxLifetime(60000);
			dataSource.setInitializationFailTimeout(60000);
			dataSource.setLeakDetectionThreshold(30000);
			dataSource.setLoginTimeout(30000);
			dataSource.setIdleTimeout(30000);
			dataSource.setPoolName("AutomationHikariPool");
			dataSource.addDataSourceProperty("useServerPrepStmts", true);
			dataSource.setConnectionTestQuery("SELECT 1");
			log.info("Starting Connection Pool. Pool Name: " + dataSource.getPoolName());
			Connection conn = dataSource.getConnection();
			conn.close();
			return true;
		} catch (Exception e1) {
			log.error("ERROR CREATING CONNECTION : " + e1.getStackTrace()[0].getClassName() + " "
					+ e1.getStackTrace()[0].getMethodName() + " " + e1.getStackTrace()[0].getLineNumber());
			return false;
		}
	}

	public static void closeConnetionPool() {
		dataSource.close();
	}
	
}

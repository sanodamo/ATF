package org.staw.datarepository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;

import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.apache.log4j.Logger;
import org.staw.framework.helpers.DatabasePropertiesHelper;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;

public class SqlDatabase {
	private enum DataType {
		INT, STRING, TIMESTAMP, BIGINT, LONG;
	}

	private static Logger log = Logger.getLogger(SqlDatabase.class.getName());
	private static HikariDataSource dataSource;

	public static Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			log.error("Error getting connection from connection pool");
			e.printStackTrace();
		}
		return null;
	}

	public static PreparedStatement getSQL(Connection conn, QueryVars sqlFor) {
		try {
			return conn.prepareStatement(sqlFor.getSql(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage() + " TRYING SQL: " + sqlFor.name());
		}
		return null;
	}

	public static boolean startConnectionPool() {
		String location = "";
		dataSource = new HikariDataSource();
		String currUser = "autoUser";
		try {
			DatabasePropertiesHelper dbHelper = new DatabasePropertiesHelper("database");
			
			location = "AutomationDatabase.qa.sqlserver";
			
			dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			dataSource.setJdbcUrl(dbHelper.getProperty(location + ".url"));
			
			dataSource.setUsername(dbHelper.getProperty(location + "." + currUser + ".userName"));
			dataSource
					.setPassword(dbHelper.getProperty(location + "." + currUser + ".password"));
			dataSource.setMaximumPoolSize(10);
			dataSource.setMaxLifetime(60000);
			dataSource.setInitializationFailTimeout(60000);
//			oracleDataSource.setConnectionTimeout(60000);
			dataSource.setLeakDetectionThreshold(30000);
			//dataSource.addDataSourceProperty("oracle.net.CONNECT_TIMEOUT", 60000);
			//dataSource.addDataSourceProperty("oracle.jdbc.ReadTimeout", 60000);
			dataSource.setLoginTimeout(30000);
			dataSource.setIdleTimeout(30000);
//			oracleDataSource.addDataSourceProperty("cachePrepStmts", "true");
//			oracleDataSource.addDataSourceProperty("prepStmtCacheSize", "250");
//			oracleDataSource.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
			dataSource.setPoolName("AutomationHikariPool");
			dataSource.addDataSourceProperty("useServerPrepStmts", true);
			log.info("Starting Connection Pool against Oracle Database. Pool Name: " + dataSource.getPoolName());
			Connection conn = dataSource.getConnection();
			conn.close();
			return true;
		} catch (Exception e1) {
			log.error("ERROR CREATING CONNECTION AGAINST ORACLE DB. Exception thrown at : "
					+ e1.getStackTrace()[0].getClassName() + " " + e1.getStackTrace()[0].getMethodName() + " "
					+ e1.getStackTrace()[0].getLineNumber());
			return false;
		}
	}

	public static void closeConnetionPool() {
		dataSource.close();
	}

	public static String getValFromReport(QueryVars esq, String[] arr)
			throws FileNotFoundException, SQLException, IOException {
		String columnValue = "";

		try (Connection conn = getConnection();
				PreparedStatement sqlStatement = getStatement(conn, esq, arr);
				ResultSet rs = sqlStatement.executeQuery();) {
			if (rs.next() == false) {
				if (rs.isClosed()) {
					log.error("Result set is closed. Please take a look at the query " + esq.getSql());
				}
			}
			rs.beforeFirst();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					columnValue = rs.getString(i);
				}
			}
		} catch (Exception e) {
			log.error("Error processing the Result Set from the query: " + esq.getSql() + ".");
			return columnValue;
		}
		return columnValue;
	}
//
	public static String[][] getResultSet(QueryVars esq, String[] val)
			throws FileNotFoundException, SQLException, IOException {
		String[][] resultSetVal = null;
		try (Connection conn = getConnection();
				PreparedStatement sqlStatement = getStatement(conn, esq, val);
				ResultSet rs = sqlStatement.executeQuery();) {
			if (rs.next() == false) {
				if (rs.isClosed()) {
					log.error("Result set is closed. Please take a look at the query " + esq.getSql());
				}
				return resultSetVal;
			}
			rs.beforeFirst();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			rs.last();
			int rowNumber = rs.getRow();
			rs.beforeFirst();
			resultSetVal = new String[rowNumber + 1][columnsNumber];
			for (int col = 1; col <= columnsNumber; col++) {
				resultSetVal[0][col - 1] = rsmd.getColumnName(col);
			}
			int row = 1;
			while (rs.next()) {
				for (int col = 1; col <= columnsNumber; col++) {
					resultSetVal[row][col - 1] = rs.getString(col);
				}
				row++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Unable to get Result set. Error: " + e.getCause());
		}
		return resultSetVal;
	}
//
	private static PreparedStatement getStatement(Connection conn, QueryVars sqlType, String[] val)
			throws SQLException, IOException, FileNotFoundException {
		PreparedStatement stm = null;
		stm = getSQL(conn, sqlType);
		if (stm == null)
			return null;
		switch (sqlType) {
		case GET_ITEMS_REGULAR_ITEM:
		case GET_ITEMS_CLEARANCE_ITEM:
		case GET_ITEMS_ONSALE_ITEM:
		case GET_ITEMS_LESSTHANDOLLAR_ITEM:
		case GET_ITEMS_HAZMAT_ITEM:
		case GET_ITEMS_DISCONTINUED_ITEM:
		case GET_ITEMS_ACID_ITEM:
		case GET_ITEMS_BALLVALVESWITHLEAD_ITEM:
		case GET_ITEMS_BALLVALVESWITHOUTLEAD_ITEM:
		case GET_ITEMS_LOWLEADWITHALTERNATE_ITEM:
		case GET_ITEMS_LOWLEADNOALTERNATE_ITEM:
		case GET_ITEMS_LOWLEADWITHA6_ITEM:
		case GET_ITEMS_LOWLEADWITHA9_ITEM:
		case GET_ITEMS_STATERESTRICTED_ITEM:
		case GET_ITEMS_REFRIGERANT_ITEM:
		case GET_ITEMS_STATUSTP_ITEM:
		case GET_ITEMS_STATUSST_ITEM:
		case GET_ITEMS_STATUSLS_ITEM:
		case GET_ITEMS_STATUSC3_ITEM:
		case GET_ITEMS_STATUSC4_ITEM:
		case GET_ITEMS_STATUSWH_ITEM:
		case GET_ITEMS_STATUSPW_ITEM:
		case GET_ITEMS_STATUSPS_ITEM:
		case GET_ITEMS_STATUSNC_ITEM:
		case GET_ITEMS_STATUSNH_ITEM:
		case GET_ITEMS_STATUSWGWV_ITEM:
		case GET_ITEMS_OHTYPEA_ITEM:
		case GET_ITEMS_OHTYPEB_ITEM:
		case GET_ITEMS_LIMITEDQTY_ITEM:
		case GET_ITEMS_TRIPLEGUARD_ITEM:
		case GET_ITEMS_PAPERPRODUCT_ITEM:
		case GET_ITEMS_INTELLIGENTOFFER_ITEM:
		case GET_ITEMS_NONINTELLIGENTOFFER_ITEM:
		case GET_ITEMS_RESPIRATORY_ITEM:
		case GET_ITEMS_EXPORTCOMPLIANCE_ITEM:
		case GET_ITEMS_CCSCENARIO1CATALOG_ITEM:
		case GET_ITEMS_CCSCENARIO1NONCATALOG_ITEM:
		case GET_ITEMS_CCSCENARIO2CATALOG_ITEM:
		case GET_ITEMS_CCSCENARIO2NONCATALOG_ITEM:
		case GET_ITEMS_CCSCENARIO3CATALOG_ITEM:
		case GET_ITEMS_CCSCENARIO3NONCATALOG_ITEM:
		case GET_ITEMS_CCSCENARIO4CATALOG_ITEM:
		case GET_ITEMS_CCSCENARIO4NONCATALOG_ITEM:
		case GET_ITEMS_VERIFYADDRESSDELETE_ITEM:
		case GET_ITEMS_LOWLEAD_ITEM:
		case GET_ITEMS_FABORYS_ITEM:
		case GET_ITEMS_INTERNATIONALSALERESTRICTEDHAZ_ITEM:
		case GET_ITEMS_SEVENCOMBO_ITEM:
		case GET_ITEMS_SWITHTECHNICALSPECS_ITEM:
		case GET_ITEMS_NOPICKUP_ITEM:
		case GET_ITEMS_PANDEMICORDER_ITEM:
		case GET_ITEMS_LOWLEADSTATERESTRICTED_ITEM:
		case GET_ITEMS_TAAGSA_ITEM:
		case GET_ITEMS_CSPACCT0800008369_ITEM:
		case GET_ITEMS_SPECIAL_ITEM:
		case GET_ITEMS_JWOODAB1_ITEM:
		case GET_ITEMS_GSA_ITEM:
		case GET_ITEMS_ESI_ITEM:
		case GET_ITEMS_SHIPPACK_ITEM:
		case GET_ITEMS_ORPHANCLEANER_ITEM:
		case GET_ITEMS_GUESTUSERRESTICTED_ITEM:
		case GET_ITEMS_PCARDPRICECHECK_ITEM:
		case GET_ITEMS_ILRESTRICTED_ITEM:
		case GET_ITEMS_L3CATEGORY_ITEM:
		case GET_ITEMS_SKUSEARCHSPECIFIC_ITEM:
		case GET_ITEMS_CODE399_ITEM:
		case GET_ITEMS_PRICECHECK_ITEM:
		case GET_EMPLOYEE_DETAILS_BY_ID:
		case GET_SCRIPT_PATH_BY_P_ID:
		case GET_SALESFORCE_PROD_USER_INFORMATION:
		case GET_ITEMS_LTLHAZMATITEM_ITEM:
		case GET_ITEMS_LTLONLYITEM_ITEM:
		case GET_ITEMS_LTLORITEM_ITEM:
		case GET_ITEMS_ORONLYITEM_ITEM:	
			setStatementValues(stm, DataType.STRING, 0, val.length, val);
			break;

		case GET_STEP_LEVEL_REPORT_VALUES_BY_P_ID:
		case GET_REPORT_ALL_INFORMATION_BY_PID:
		case GET_REPORT_TABLE_RESULT_BY_PID:
		case GET_THREADS_TESTNAME:
		case GET_THREADS_BROWSER:
		case GET_THREADS_BROWSER_VERSION:
		case GET_THREADS_OS:
		case INSERT_INTO_USERS:
		case UPDATE_SCRIPTS:
		case GET_SELENIUM_USER_USER_AND_PASSWORD:
		case GET_SELENIUM_USER_USER_ID:
		case GET_CREDIT_CARD_ROW:
		case GET_CREDIT_CARD_NAME_ON_CARD:
		case GET_CREDIT_CARD_EXPIRY_YEAR:
		case GET_SALES_FORCE_ROW:
		case GET_USER_REGISTRATION_ROW:
		case GET_REPORT_TABLE_ALL_INFORMATION_BY_TESTNAME_BROWSER_BROWSER_VERION_OS:
		case GET_MASTER_TABLE_APP_SERVER_URL:
		case INSERT_INTO_USER_REGRISTRATION:
		case INSERT_INTO_USER_SALES_FORCE:
		case GET_REPORT_APP_SERVER_INFORMATION_BY_URL:
		case GET_REPORT_TABLE_RESULT_BY_TESTNAME:
		case GET_EMPLOYEE_DETAILS_BY_MAC_ADDRESS:
		case GET_EMPLOYEE_DETAILS_BY_RACFID:
		case INSERT_INTO_MASTER_TABLE:
		case UPDATE_THREADS_TESTNAME:
		case UPDATE_THREADS_BROWSER:
		case UPDATE_THREADS_BROWSER_VERSION:
		case UPDATE_THREADS_OS:
		case UPDATE_MASTER_TABLE:
		case UPDATE_REPORT_TABLE_RESULT:
		case UPDATE_REPORT_TABLE_SAUCE_VIDEO:
		case UPDATE_REPORT_TABLE_SPLUNK_SESSION:
		case UPDATE_REPORT_TABLE_SILO:
		case UPDATE_REPORT_TABLE_BUILD:
		case UPDATE_REPORT_TABLE_CONTENT_VERSION:
		case UPDATE_REPORT_TABLE_O_COOKIE:
		case UPDATE_REPORT_TABLE_TOTAL_EXECUTION_TIME:
		case UPDATE_RERUN_TABLE_CURRENT_PID_WITH_PREVIOUS_PID:
		case GET_COOKIES_VALUE_BY_COOKIE_AND_PID:
		case GET_COOKIES_ALL_COOKIES_BY_PID:
		case GET_RERUN_TABLE_PREVIOUS_PID_FOR_CURRENT_PID:
		case GET_RERUN_TABLE_ORIGINAL_AND_RERUN_USING_ORIGINAL_PID:
		case GET_MASTER_TABLE_VALUE:
		case GET_REPORT_TABLE_START_TIME:
		case GET_REPORT_TABLE_TOTAL_STEPS:
		case GET_REPORT_TABLE_STEPS_PASSED:
		case GET_REPORT_TABLE_STEPS_FAILED:
		case GET_REPORT_TABLE_TOTAL_EXECUTION_TIME:
		case GET_REPORT_TABLE_RESULT:
		case GET_REPORT_TABLE_SAUCE_VIDEO:
		case GET_REPORT_TABLE_SPLUNK_SESSION:
		case GET_REPORT_TABLE_SILO:
		case GET_REPORT_TABLE_BUILD:
		case GET_REPORT_TABLE_CONTENT_VERSION:
		case GET_REPORT_TABLE_O_COOKIE:
		case GET_BAMBOO_PLAN_NAME_BY_PLAN_NAME:
		case GET_PLAN_ID_BY_PLAN_NAME:
		case GET_BAMBOO_PLAN_NAME_BY_ID:
		case INSERT_INTO_PLANS:
		case GET_MASTER_TABLE_COUNT_OF_KEY:
		case GET_MASTER_TABLE_INFO_OF_KEY:
		case GET_MASTER_TABLE_COUNT_OF_KEY_AND_VALUE:
		case GET_EMPLOYEE_RACFID_BY_FIRST_NAME:
		case GET_THREADS_TABLE_ALL_BROWSERS_RUN_IN_SUITE:
		case GET_REPORT_ALL_INFORMATION:
		case GET_REPORT_BUILD_NUMBER:
		case GET_REPORT_LIST_OF_ALL_TEST_PASSED:
		case GET_REPORT_LIST_OF_ALL_TEST_FAILED:
		case GET_REPORT_TOTAL_STEPS:
		case GET_THREADS_ALL_SCRIPTS_RUN_IN_SUITE_INCLUDING_RERUN:
		case GET_THREADS_ALL_SCRIPTS_RUN_IN_SUITE_NOT_INCLUDING_RERUN_COUNT:
		case GET_REPORT_TOTAL_STEPS_PASSED:
		case GET_THREADS_UNIQUE_SCRIPTS_RUN_IN_SUITE:
		case GET_THREADS_INFORMATION:
		case GET_JSERRORS_SIZE:
		case GET_JSERRORS_ALL_INFORMATION:
		case GET_COOKIES_ALL_INFORMATION:
		case GET_THREADS_UNIQUE_PID:
		case GET_BUILD_ID_FROM_BUILD_BY_BUILD_FULL_KEY:
		case GET_SCRIPTS_P_ID_BY_SCRIPT_NAME:
		case GET_THREADS_INFO_BY_PID:
		case GET_THREADS_SCRIPT_RESULT_BY_SCRIPT_INFORMATION:
		case GET_MASTER_TABLE_PROD_APP_SERVER_SILO_BY_PID:
		case GET_MASTER_TABLE_QA_APP_SERVER_SILO_BY_PID:
		case GET_REPORT_TABLE_TOTAL_BROWSERS_FROM_SUITE:
		case GET_THREADS_TABLE_BROWSER_RESULT_COUNT_BY_BROWSER_OS_INFO:
		case GET_TOTAL_SCRIPTS_RUN_FOR_PLAN:
		case GET_TOTAL_PASSED_SCRIPTS_RUN_FOR_PLAN:
		case GET_THREADS_P_ID_BY_TEST_NAME_AND_BUILD_KEY:
		case GET_MASTER_TABLE_PROD_APP_SERVER_SILO_AND_COMPONENT:
		case GET_MASTER_TABLE_QA_APP_SERVER_SILO_AND_COMPONENT:
		case GET_MASTER_TABLE_PROD_APP_SERVER_URL:
		case GET_BROWSERS_ENV_OPTIONS:
		case GET_EMULATORS_ENV_OPTIONS:
		case GET_SLACK_KEY_BY_NAME:
		case GET_WEB_KS_ORDER_ID:	
		case GET_BUILD_BROWSER_STATISTICS:
			setStatementValues(stm, DataType.STRING, 0, val.length, val);
			break;
			
		case GET_SALESFORCE_USER_ID:
		case GET_NETWORK_DATA_FIELDS_FROM_ND_ID:
			setStatementValues(stm, DataType.INT, 0, val.length, val);
			break;

		case INSERT_INTO_SCRIPTS:
			stm.setInt(1, Integer.parseInt(val[0]));
			stm.setString(2, val[1]);
			setStatementValues(stm, DataType.INT, 2, val.length, val);
			break;

		case INSERT_INTO_CREDIT_CARD:
			Integer[] intNums = { 0, 4, 5, 6 };
			for (int i = 0; i < val.length; i++) {
				if (Arrays.asList(intNums).contains(i) && sqlType.equals(QueryVars.INSERT_INTO_CREDIT_CARD))
					stm.setInt(i + 1, Integer.parseInt(val[i]));
				else
					stm.setString(i + 1, val[i]);
			}
			break;

		case INSERT_INTO_PROD_APP_SERVERS:
		case INSERT_INTO_QA_APP_SERVERS:
		case INSERT_INTO_ITEMS:
		case INSERT_INTO_THREADS:
		case INSERT_INTO_RERUN_TABLE:
		case UPDATE_REPORT_TABLE_TOTAL_STEPS:
		case UPDATE_REPORT_TABLE_STEPS_PASSED:
		case UPDATE_REPORT_TABLE_STEPS_FAILED:
		case INSERT_INTO_SCRIPT_PATH:
			stm.setInt(1, Integer.parseInt(val[0]));
			setStatementValues(stm, DataType.STRING, 1, val.length, val);
			break;

		case INSERT_INTO_STEP_LEVEL_REPORT:
			stm.setString(1, val[0]);
			stm.setTimestamp(2, Timestamp.valueOf(val[1]));
			stm.setInt(3, Integer.parseInt(val[2]));
			setStatementValues(stm, DataType.STRING, 3, val.length, val);
			break;

		case INSERT_INTO_REPORT_TABLE:
			stm.setString(1, val[0]);
			stm.setTimestamp(2, Timestamp.valueOf(val[1]));
			setStatementValues(stm, DataType.INT, 2, 5, val);
			setStatementValues(stm, DataType.STRING, 5, val.length, val);
			break;

		case UPDATE_REPORT_TABLE_START_TIME:
			stm.setTimestamp(1, Timestamp.valueOf(val[0]));
			setStatementValues(stm, DataType.STRING, 1, val.length, val);
			break;

		case INSERT_INTO_JSERROR_TABLE:
		case INSERT_INTO_COOKIES_TABLE:
			stm.setString(1, val[0]);
			stm.setTimestamp(2, Timestamp.valueOf(val[1]));
			setStatementValues(stm, DataType.STRING, 2, val.length, val);
			break;

		case UPDATE_STEP_LEVEL_KEYWORD:
		case UPDATE_STEP_LEVEL_PARAMETERS:
		case UPDATE_STEP_LEVEL_DESCRIPTION:
		case UPDATE_STEP_LEVEL_RESULT:
		case GET_STEP_LEVEL_KEYWORD:
		case GET_STEP_LEVEL_PARAMETERS:
		case GET_STEP_LEVEL_DESCRIPTION:
		case GET_STEP_LEVEL_RESULT:
		case GET_NETWORK_DATA_SCRIPT_VALUES_FROM_PAGE_AND_SCRIPT_NAME:
			setStatementValues(stm, DataType.STRING, 0, 2, val);
			setStatementValues(stm, DataType.INT, 2, val.length, val);
			break;

		case INSERT_INTO_BUILD:
			setStatementValues(stm, DataType.INT, 0, 1, val);
			setStatementValues(stm, DataType.STRING, 1, 3, val);
			setStatementValues(stm, DataType.INT, 3, 4, val);
			setStatementValues(stm, DataType.TIMESTAMP, 4, val.length, val);
			break;

		case INSERT_INTO_BUILD_INFO:
			setStatementValues(stm, DataType.BIGINT, 0, 1, val);
			setStatementValues(stm, DataType.STRING, 1, 7, val);
			setStatementValues(stm, DataType.INT, 7, 11, val);
			setStatementValues(stm, DataType.STRING, 11, val.length, val);
			break;

		case INSERT_INTO_BUILD_SCRIPTS:
			setStatementValues(stm, DataType.BIGINT, 0, 1, val);
			setStatementValues(stm, DataType.STRING, 1, val.length, val);
			break;

		case UPDATE_SCRIPT_PATH:
			setStatementValues(stm, DataType.STRING, 0, 1, val);
			setStatementValues(stm, DataType.INT, 1, val.length, val);
			break;
			
		case INSERT_INTO_BUILD_BROWSER_STASTICS:
			setStatementValues(stm, DataType.STRING, 0, 4, val);
			setStatementValues(stm, DataType.INT, 4, val.length, val);
			break;

		default:
			break;
		}
		return stm;
	}

	public static void getPoolInfo() throws MalformedObjectNameException {
		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		ObjectName poolName = new ObjectName("com.zaxxer.hikari:type=Pool(" + dataSource.getPoolName() + ")");
		log.info(poolName.getCanonicalName());
		HikariPoolMXBean poolProxy = JMX.newMXBeanProxy(mBeanServer, poolName, HikariPoolMXBean.class);

		log.info("Total Connections: " + poolProxy.getTotalConnections() + "\n Active Connections : "
				+ poolProxy.getActiveConnections() + "\n Idle Connections: " + poolProxy.getIdleConnections());
	}
//
	private static void setStatementValues(PreparedStatement stm, DataType type, int start, int end, String[] val) {
		try {
			for (int i = start; i < end; i++) {
				switch (type) {
				case INT:
					try {
						stm.setInt(i + 1, Integer.parseInt(val[i]));
					} catch (Exception e) {
						log.error("Error setting int for statement: " + stm.toString() + " with value: " + val[i]);
						stm.setInt(i + 1, 1000000);
					}
					break;

				case STRING:
					stm.setString(i + 1, val[i]);
					break;

				case TIMESTAMP:
					try {
						stm.setTimestamp(i + 1, Timestamp.valueOf(val[i]));
					} catch (Exception e) {
						log.error(
								"Error setting timestamp for statement: " + stm.toString() + " with value: " + val[i]);
						stm.setTimestamp(i + 1, Timestamp.valueOf(DataLibrary.getCurrentTimestampMySqlFormat()));
					}
					break;

				case BIGINT:
				case LONG:
					try {
						stm.setLong(i + 1, Long.parseLong(val[i]));
					} catch (Exception e) {
						log.error("Error setting big int/long  for statement: " + stm + " with value: " + val[i]);
						stm.setLong(i + 1, 1000000);
					}
					break;

				}
			}
		} catch (SQLException e) {
			log.error("Error trying to set value for prepared statement " + stm);
			e.printStackTrace();
		}
	}
//
	public static boolean runSqlForEmdDb(QueryVars sqlType, String[] val)
			throws SQLException, IOException, FileNotFoundException {
		try (Connection conn = getConnection(); PreparedStatement stm = getStatement(conn, sqlType, val);) {
			if (stm == null)
				return false;
			stm.execute();
			return true;
		} catch (Exception e) {
//			if(!sqlType.getSql().contains(ReportType.MASTER_TABLE.name()))
//				if(!sqlType.getSql().contains(ReportType.COOKIES_TABLE.name().replace("_TABLE", ""))) {
//					log.error("Error executing query: " + sqlType.getSql() + ". Query tried INSERT once and failed. Error " + e.getMessage());
//				}
			throw(e);
		}
		//return false;
	}
}

package org.staw.datarepository;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.staw.framework.ThreadInformation;
import org.staw.framework.constants.GlobalConstants;


public class DataLibrary {

	public enum ReportType {
		THREADS, MASTER_TABLE, REPORT_TABLE, STEP_LEVEL_REPORT, RERUN_TABLE, JSERROR_TABLE, COOKIES_TABLE, TESTDATA;
	}

	private static Logger log = Logger.getLogger(DataLibrary.class.getName());

//	public static void createAllTables(){
////		destroyAllTables();
//		for(ReportType type: ReportType.values()){
//			DataLibrary.initialize("create", type);
//		}
//	}
//
//	public static void destroyAllTables(){
//		for(ReportType type: ReportType.values()){
//			DataLibrary.initialize("drop", type);
//		}
//	}

//	public static void initialize(String action, ReportType rt) {
//		boolean create = action.equalsIgnoreCase("create") ? true : false;
//		QueryVars esq = null;
//		switch (rt) {
//		case MASTER_TABLE:
//			esq = create ? QueryVars.CREATE_MASTER_TABLE : QueryVars.DROP_MASTER_TABLE;
//			break;
//		case REPORT_TABLE:
//			esq = create ? QueryVars.CREATE_REPORT_TABLE : QueryVars.DROP_REPORT_TABLE;
//			break;
//		case STEP_LEVEL_REPORT:
//			esq = create ? QueryVars.CREATE_STEP_LEVEL_REPORT : QueryVars.DROP_STEP_LEVEL_REPORT;
//			break;
//		case THREADS:
//			esq = create ? QueryVars.CREATE_THREADS_TABLE : QueryVars.DROP_THREADS_TABLE;
//			break;
//		case RERUN_TABLE:
//			esq = create ? QueryVars.CREATE_RERUN_TABLE : QueryVars.DROP_RERUN_TABLE;
//			break;
//		case JSERROR_TABLE:
//			esq = create ? QueryVars.CREATE_JSERROR_TABLE : QueryVars.DROP_JSERROR_TABLE;
//			break;
//		case COOKIES_TABLE:
//			esq = create ? QueryVars.CREATE_COOKIE_TABLE : QueryVars.DROP_COOKIES_TABLE;
//			break;
//		}
//		try {
//			SqlDatabase.runSqlForEmdDb(esq, null);
//		} catch (SQLException | IOException e) {
//			log.error(e.getMessage());
//		}
//	}

	public static String getCurrentTimestampMySqlFormat(){
		Date date = new java.util.Date();
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Timestamp(date.getTime()));
	}
	
	public static void setValue(ReportType rt, String key, String val) {
		val = val == null || val.equals("") || val.isEmpty()? "N/A" : val;
		String time = getCurrentTimestampMySqlFormat();
		QueryVars esq = null;
		String[] dbVal = null;
		String p_id = ThreadInformation.getValue(GlobalConstants.MasterConstant.FM_P_ID);
		switch (rt) {
		case THREADS:
			switch(key){
			case GlobalConstants.MasterConstant.FM_P_ID:
				dbVal = new String[6];
				dbVal[0] = Long.toString(Thread.currentThread().getId());
				dbVal[1] = p_id;
				for(int i=2; i<6; i++)dbVal[i] = "N/A";
				esq = QueryVars.INSERT_INTO_THREADS;
				break;
			case GlobalConstants.MasterConstant.FM_TESTNAME:
				esq = QueryVars.UPDATE_THREADS_TESTNAME;
				break;
			case GlobalConstants.MasterConstant.FM_BROWSER:
				esq = QueryVars.UPDATE_THREADS_BROWSER;
				break;
			case GlobalConstants.MasterConstant.FM_BROWSER_VERSION:
				esq = QueryVars.UPDATE_THREADS_BROWSER_VERSION;
				break;
			case GlobalConstants.MasterConstant.FM_OPERATING_SYSTEM:
				esq = QueryVars.UPDATE_THREADS_OS;
				break;
			}
			if(!key.equals(GlobalConstants.MasterConstant.FM_P_ID)){
				dbVal = new String[2];
				dbVal[0] = val;
				dbVal[1] = p_id;
			}
			break;

		case MASTER_TABLE:
			dbVal = new String[3];
			dbVal[0] = p_id;
			dbVal[1] = key;
			dbVal[2] = val;
			esq = QueryVars.INSERT_INTO_MASTER_TABLE;
			break;

		case STEP_LEVEL_REPORT:
			switch(key){
			case GlobalConstants.AssertionAndTestStepConstant.STEP_COUNT:
				dbVal = new String[7];
				dbVal[0] = p_id;
				dbVal[1] = time;
				dbVal[2] = val;
				for(int i=3; i<7; i++)dbVal[i] = "N/A";
				esq = QueryVars.INSERT_INTO_STEP_LEVEL_REPORT;
				break;
			case GlobalConstants.AssertionAndTestStepConstant.KEYWORD:
				esq = QueryVars.UPDATE_STEP_LEVEL_KEYWORD;
				break;

			case GlobalConstants.AssertionAndTestStepConstant.PARAMETER_VALUES:
				esq = QueryVars.UPDATE_STEP_LEVEL_PARAMETERS;
				break;

			case GlobalConstants.AssertionAndTestStepConstant.DESRIPTION:
				esq = QueryVars.UPDATE_STEP_LEVEL_DESCRIPTION;
				break;

			case GlobalConstants.AssertionAndTestStepConstant.STEP_RESULT:
				esq = QueryVars.UPDATE_STEP_LEVEL_RESULT;
				break;
			}
			if(!key.equals(GlobalConstants.AssertionAndTestStepConstant.STEP_COUNT)){
				dbVal = new String[3];
				dbVal[0] = val;
				dbVal[1] = p_id;
				dbVal[2] = DataLibrary.getValue(ReportType.MASTER_TABLE, GlobalConstants.MasterConstant.FM_CURRENT_KEYWORD_STEP);
			}
			break;

		case REPORT_TABLE:
			switch(key){
			case GlobalConstants.MasterConstant.FM_P_ID:
				dbVal = new String[10];
				dbVal[0] = p_id;
				dbVal[1] = time;
				dbVal[2]="0";
				dbVal[3]="0";
				dbVal[4]="0";
				dbVal[5] = "00:00:00";
				dbVal[6]="N/A";
				dbVal[7]="N/A";
				dbVal[8]="N/A";
				dbVal[9]="N/A";
				esq = QueryVars.INSERT_INTO_REPORT_TABLE;
				break;
			case GlobalConstants.MasterConstant.FM_START_DATE_AND_TIME:
				esq = QueryVars.UPDATE_REPORT_TABLE_START_TIME;
				break;

			case GlobalConstants.MasterConstant.FM_TOTAL_EXECUTION_STEPS:
				esq = QueryVars.UPDATE_REPORT_TABLE_TOTAL_STEPS;
				break;

			case GlobalConstants.MasterConstant.FM_STEPS_PASSED:
				esq = QueryVars.UPDATE_REPORT_TABLE_STEPS_PASSED;
				break;

			case GlobalConstants.MasterConstant.FM_STEPS_FAILED:
				esq = QueryVars.UPDATE_REPORT_TABLE_STEPS_FAILED;
				break;

			case GlobalConstants.MasterConstant.FM_TOTAL_EXECUTION_TIME:
				esq = QueryVars.UPDATE_REPORT_TABLE_TOTAL_EXECUTION_TIME;
				break;

			case GlobalConstants.MasterConstant.FM_TEST_RESULT:
				esq = QueryVars.UPDATE_REPORT_TABLE_RESULT;
				break;			
			}
			if(!key.equals(GlobalConstants.MasterConstant.FM_P_ID)){
				dbVal = new String[2];
				dbVal[0] = val;
				dbVal[1] = p_id;
			}
			break;

		case RERUN_TABLE:
			esq = QueryVars.INSERT_INTO_RERUN_TABLE;
			dbVal = new String[3];
			dbVal[0] = Long.toString(Thread.currentThread().getId());
			dbVal[1] = key;
			dbVal[2] = val;
			break;

		case JSERROR_TABLE:
			esq = QueryVars.INSERT_INTO_JSERROR_TABLE;
			dbVal = new String[4];
			dbVal[0] = p_id;
			dbVal[1] = time;
			dbVal[2] = key;//URL
			dbVal[3] = val;//JS-ERROR
			break;

		default:
			log.error(rt.name() + " not yet implemented");
			break;
		}
		try {
			if(!SqlDatabase.runSql(esq, dbVal))
				updateValue(rt, key, val);
		} catch (SQLException | IOException e) {
			log.error("Unable to set nor update the key: " + key + " with value " + val);
		}
	}

	public static String getValue(ReportType rt, String key) {
		String returnVal = "";
		Object[] arr = getSqlAndArray(rt, key);
		QueryVars esq = (QueryVars)arr[0];
		String[] dbVal = (String[])arr[1];
		try {
			returnVal = SqlDatabase.getValFromReport(esq, dbVal);
		} catch (SQLException | IOException e) {
			log.error(e.getMessage());
		}
		return returnVal;
	}

	private static Object[] getSqlAndArray(ReportType rt, String key){
		Object[] arr = new Object[2];
		QueryVars esq = null;
		String[] dbVal = null;
		String p_id = ThreadInformation.getValue(GlobalConstants.MasterConstant.FM_P_ID);
		switch (rt) {
		case THREADS:
			switch(key){
			case GlobalConstants.MasterConstant.FM_TESTNAME:
				esq = QueryVars.GET_THREADS_TESTNAME;
				break;
			case GlobalConstants.MasterConstant.FM_BROWSER:
				esq = QueryVars.GET_THREADS_BROWSER;
				break;
			case GlobalConstants.MasterConstant.FM_BROWSER_VERSION:
				esq = QueryVars.GET_THREADS_BROWSER_VERSION;
				break;
			case GlobalConstants.MasterConstant.FM_OPERATING_SYSTEM:
				esq = QueryVars.GET_THREADS_OS;
				break;
			}
			dbVal = new String[1];
			dbVal[0] = p_id;
			break;

		case MASTER_TABLE:
			dbVal = new String[2];
			dbVal[0] = p_id;
			dbVal[1] = key;
			esq = QueryVars.GET_MASTER_TABLE_VALUE;
			break;

		case STEP_LEVEL_REPORT:
			switch (key) {
			case GlobalConstants.AssertionAndTestStepConstant.KEYWORD:
				esq = QueryVars.GET_STEP_LEVEL_KEYWORD;
				break;
			case GlobalConstants.AssertionAndTestStepConstant.PARAMETER_VALUES:
				esq = QueryVars.GET_STEP_LEVEL_PARAMETERS;
				break;
			case GlobalConstants.AssertionAndTestStepConstant.DESRIPTION:
				esq = QueryVars.GET_STEP_LEVEL_DESCRIPTION;
				break;
			case GlobalConstants.AssertionAndTestStepConstant.STEP_RESULT:
				esq = QueryVars.GET_STEP_LEVEL_RESULT;
				break;
			}
			dbVal = new String[2];
			dbVal[0] = p_id;
			dbVal[1] = DataLibrary.getValue(ReportType.MASTER_TABLE, GlobalConstants.MasterConstant.FM_CURRENT_KEYWORD_STEP);
			break;

		case REPORT_TABLE:
			switch (key) {
			case GlobalConstants.MasterConstant.FM_START_DATE_AND_TIME:
				esq = QueryVars.GET_REPORT_TABLE_START_TIME;
				break;
			case GlobalConstants.MasterConstant.FM_TOTAL_EXECUTION_STEPS:
				esq = QueryVars.GET_REPORT_TABLE_TOTAL_STEPS;
				break;
			case GlobalConstants.MasterConstant.FM_STEPS_PASSED:
				esq = QueryVars.GET_REPORT_TABLE_STEPS_PASSED;
				break;
			case GlobalConstants.MasterConstant.FM_STEPS_FAILED:
				esq = QueryVars.GET_REPORT_TABLE_STEPS_FAILED;
				break;
			case GlobalConstants.MasterConstant.FM_TOTAL_EXECUTION_TIME:
				esq = QueryVars.GET_REPORT_TABLE_TOTAL_EXECUTION_TIME;
				break;
			case GlobalConstants.MasterConstant.FM_TEST_RESULT:
				esq = QueryVars.GET_REPORT_TABLE_RESULT;
				break;			
			}
			dbVal = new String[1];
			dbVal[0] = p_id;
			break;

		case RERUN_TABLE:
			esq = QueryVars.GET_RERUN_TABLE_PREVIOUS_PID_FOR_CURRENT_PID;
			dbVal = new String[1];
			dbVal[0] = key;
			break;

		case JSERROR_TABLE:
		case COOKIES_TABLE:
			break;
		case TESTDATA:
			dbVal = new String[1];			
			dbVal[0] = key;
			esq = QueryVars.GET_TEST_DATA_BY_KEY;
			break;
		}
		arr[0]=esq;
		arr[1]=dbVal;
		return arr;
	}

	public static void updateValue(ReportType rt, String key, String value) throws SQLException , IOException{
		value = value.equals("") || value.isEmpty()? "N/A" : value;
		QueryVars esq = null;
		String[] dbVal = null;
		switch (rt) {
		case MASTER_TABLE:
			dbVal = new String[3];
			dbVal[0] = value;
			dbVal[1] = ThreadInformation.getValue(GlobalConstants.MasterConstant.FM_P_ID);
			dbVal[2] = key;
			esq = QueryVars.UPDATE_MASTER_TABLE;
			break;

		case RERUN_TABLE:
			dbVal = new String[2];
			dbVal[0] = value;
			dbVal[1] = key;
			esq = QueryVars.UPDATE_RERUN_TABLE_CURRENT_PID_WITH_PREVIOUS_PID;
			break;

		default:
			log.error("ERROR UPDATING VALUE FOR: " + rt.toString() + " with Key: " + key + " with value: " + value);
		}
		try{
			SqlDatabase.runSql(esq, dbVal);
		}catch (SQLException| IOException e){
			log.error("ERROR UPDATING VALUE FOR: " + rt.toString() + " with Key: " + key + " with value: " + value +  e.getCause());
		}
	}

	public static String[][] getSqlResult(QueryVars esq, String[] val){
		String[][] resultSetVal = null;
		try {
			resultSetVal = SqlDatabase.getResultSet(esq, val);
			if(resultSetVal == null){
				resultSetVal = new String[1][1];
			}
		} catch (SQLException | IOException e) {
			log.error("Error trying to fetch result Set for query " + esq.getSql());
			e.printStackTrace();
		}
		return resultSetVal;
	}

	public static boolean containsKey(ReportType rt, String key) {
		boolean returnVal = false;
		Object[] arr = getSqlAndArray(rt, key);
		QueryVars esq = (QueryVars)arr[0];
		String[] dbVal = (String[])arr[1];
		try {
			returnVal = SqlDatabase.getValFromReport(esq, dbVal).equals("") ? false : true;
		} catch (SQLException | IOException e) {
			log.error(e.getMessage());
		}
		return returnVal;
	}

	public static void runGenericSql(QueryVars esq, String[] val){
		try{
			SqlDatabase.runSql(esq, val);
		}catch (SQLException| IOException e){
			log.error(e.getCause());
		}
	}

	public static void storeInitialInformation(ReportType rt, HashMap<String, String> insertVals) {
		switch(rt){
		case THREADS:
			break;
		case MASTER_TABLE:
			if(insertVals != null) break;
			Date startExecution = new Date();
			DateFormat df = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
			String vidLink = "";
			/*
			 * Start Every test as Failed
			 */
			DataLibrary.setValue(ReportType.MASTER_TABLE, GlobalConstants.MasterConstant.FM_TEST_START_TIME, Long.toString(System.currentTimeMillis()));
			DataLibrary.setValue(ReportType.MASTER_TABLE, GlobalConstants.MasterConstant.FM_START_DATE_AND_TIME, df.format(startExecution));
			DataLibrary.setValue(ReportType.MASTER_TABLE, GlobalConstants.MasterConstant.FM_TEST_RESULT, WordUtils.capitalize("FAILED"));			
			break;
		case REPORT_TABLE:
			DataLibrary.setValue(ReportType.REPORT_TABLE, GlobalConstants.MasterConstant.FM_P_ID, ThreadInformation.getValue(GlobalConstants.MasterConstant.FM_P_ID));
			break;
		case STEP_LEVEL_REPORT:
		case RERUN_TABLE:
		case JSERROR_TABLE:
		case COOKIES_TABLE:
			break;
		}
		if(insertVals != null){
			for (String key : insertVals.keySet()) {
				DataLibrary.setValue(rt, key, insertVals.get(key));
			}
		}
	}


	public static void storeFinalTestInformationForReport(String tcName, String browser, String browserVersion, String osVersion) {
		long totalExecutionTime;
		long testEndTime = System.currentTimeMillis();
		long startTime = 0L;
		try{
			startTime = Long.parseLong(DataLibrary.getValue(ReportType.MASTER_TABLE, GlobalConstants.MasterConstant.FM_TEST_START_TIME));
		}catch(NumberFormatException e){
			log.error("Unable to get Test start time when storing final report information. Error: " + e.getCause());
		}
		
		if (!DataLibrary.containsKey(ReportType.MASTER_TABLE, (GlobalConstants.MasterConstant.GBL_PASS_STEP_COUNT)))
			DataLibrary.setValue(ReportType.MASTER_TABLE, GlobalConstants.MasterConstant.GBL_PASS_STEP_COUNT, Integer.toString(0));

		if (!DataLibrary.containsKey(ReportType.MASTER_TABLE, (GlobalConstants.MasterConstant.GBL_FAIL_STEP_COUNT)))
			DataLibrary.setValue(ReportType.MASTER_TABLE, GlobalConstants.MasterConstant.GBL_FAIL_STEP_COUNT, Integer.toString(0));

		if (Boolean.parseBoolean(
				DataLibrary.getValue(ReportType.MASTER_TABLE, GlobalConstants.MasterConstant.GBL_TEST_RESULT)))
			DataLibrary.setValue(ReportType.MASTER_TABLE, GlobalConstants.MasterConstant.FM_TEST_RESULT, WordUtils.capitalize("PASSED"));
		else
			DataLibrary.setValue(ReportType.MASTER_TABLE, GlobalConstants.MasterConstant.FM_TEST_RESULT, WordUtils.capitalize("FAILED"));
		
		totalExecutionTime = testEndTime - startTime;
		String time = getExecutionTime(totalExecutionTime);
		
		DataLibrary.setValue(ReportType.REPORT_TABLE, GlobalConstants.MasterConstant.FM_STEPS_PASSED, DataLibrary.getValueIfContains(ReportType.MASTER_TABLE, GlobalConstants.MasterConstant.GBL_PASS_STEP_COUNT));
		
		int totalSteps =0;
		int stepsPassed = 0;
		try{
			totalSteps = Integer.parseInt(DataLibrary.getValueIfContains(ReportType.REPORT_TABLE, GlobalConstants.MasterConstant.FM_TOTAL_EXECUTION_STEPS));
		}catch(NumberFormatException e){
			log.error("Unable to get Total Execution Steps. Error: " + e.getMessage() + " for test: " 
			+ ThreadInformation.getValue(GlobalConstants.MasterConstant.FM_TESTNAME) + " for thread: " + Thread.currentThread().getId());
			e.printStackTrace();
		}
		DataLibrary.setValue(ReportType.REPORT_TABLE, GlobalConstants.MasterConstant.FM_STEPS_FAILED, Integer.toString(totalSteps-stepsPassed));
		DataLibrary.setValue(ReportType.REPORT_TABLE, GlobalConstants.MasterConstant.FM_TOTAL_EXECUTION_TIME, time);
		DataLibrary.setValue(ReportType.REPORT_TABLE, GlobalConstants.MasterConstant.FM_TEST_RESULT, DataLibrary.getValueIfContains(ReportType.MASTER_TABLE, GlobalConstants.MasterConstant.FM_TEST_RESULT));		
	}

	public static String getExecutionTime(long totalExecutionTime){
		long secondsInMilli = 1000;
		long minutesInMilli = secondsInMilli * 60;
		long hoursInMilli = minutesInMilli * 60;
		long elapsedHours = totalExecutionTime / hoursInMilli;
		totalExecutionTime = totalExecutionTime % hoursInMilli;
		long elapsedMinutes = totalExecutionTime / minutesInMilli;
		totalExecutionTime = totalExecutionTime % minutesInMilli;
		long elapsedSeconds = totalExecutionTime / secondsInMilli;
		NumberFormat f = new DecimalFormat("00");
		if(elapsedHours == 0)
			return "00:" + f.format(elapsedMinutes) +":" + f.format(elapsedSeconds);
		else
			return f.format(elapsedHours) +":" + f.format(elapsedMinutes) +":" + f.format(elapsedSeconds);
	}

	private static String getValueIfContains(ReportType rt, String key){
		if(DataLibrary.containsKey(rt, key))
			return DataLibrary.getValue(rt, key);
		else
			return "N/A";
	}
	
//	public static void archivePlanAndBuildData(){
//		try{
//			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
//			DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String buildTimestamp = "";
//			Date date = df.parse(BuildVariables.getBuildTimestamp());
//			buildTimestamp = df2.format(date);
//			String planId = "";
//			String planName = JobProperties.getCurrentJob().getJobName();
//			String[][] doesPlanNameExist = DataLibrary.getSqlResult(QueryVars.GET_BAMBOO_PLAN_NAME_BY_PLAN_NAME, new String[]{planName});
//			if(doesPlanNameExist == null || doesPlanNameExist.length <= 1){
//				DataLibrary.runGenericSql(QueryVars.INSERT_INTO_PLANS, new String[]{planName});
//			}
//			String[][] planIdArr = DataLibrary.getSqlResult(QueryVars.GET_PLAN_ID_BY_PLAN_NAME, new String[]{planName});
//			if(planIdArr.length >1){
//				planId = planIdArr[1][0];
//			}else{
//				planId="10000000000";
//				log.error("Unable to get ID for plan: " + planName + " But found plan already exists in table");
//			}
//			runGenericSql(QueryVars.INSERT_INTO_BUILD, 
//					new String[]{
//							planId, 
//							BuildVariables.getBuildFullKey(), 
//							BuildVariables.getBuildKey(), 
//							BuildVariables.getBuildNumber(), 
//							buildTimestamp
//					});
//		}catch(Exception e){
//			e.printStackTrace();
//			log.error("Error Archving Initial Data");
//		}
//	}
	
//	public static void archiveBuildInfoData(Suite suite) {
//		try{
//			String trigger = BuildVariables.getBuildTrigger() == null? BuildVariables.getScheduleTrigger() : BuildVariables.getBuildTrigger();
//			String[][] buildId = DataLibrary.getSqlResult(QueryVars.GET_BUILD_ID_FROM_BUILD_BY_BUILD_FULL_KEY, new String[]{BuildVariables.getBuildFullKey()});
//			if(buildId == null || buildId.length <=1){
//				log.error("Unable to get build ID from build table!");
//			}else{
//				runGenericSql(QueryVars.INSERT_INTO_BUILD_INFO, 
//						new String[]{
//								buildId[1][0],
//								BuildVariables.getBuildResultKey(), 
//								BuildVariables.getBuildResultUrl(), 
//								trigger, 
//								BuildVariables.buildRunEnvForDatabase(), 
//								BuildVariables.buildBrowserPermutation(), 
//								suite.getDuration(), 
//								Integer.toString(suite.getTotalScriptsCount()), 
//								Integer.toString(suite.getTotalScriptsPassedCount()), 
//								Integer.toString(suite.getTotalUniqueScriptsCount()), 
//								Integer.toString(suite.getTotalUniqueScriptsPassedCount()),
//								suite.getResult()
//						});
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//			log.error("Error Archving Data");
//		}
//	}
	
//	public static void storeScriptInBuildScripts(){
//		String[][] buildId = DataLibrary.getSqlResult(QueryVars.GET_BUILD_ID_FROM_BUILD_BY_BUILD_FULL_KEY, new String[]{BuildVariables.getBuildFullKey()});
//		if(buildId == null || buildId.length <=1){
//			log.error("Unable to get build ID from build table for each test script!");
//		}else{
//			DataLibrary.runGenericSql(QueryVars.INSERT_INTO_BUILD_SCRIPTS, new String[]{buildId[1][0], 
//					ThreadInformation.getValue(HashMapConstants.MasterConstant.FM_P_ID)});
//		}
//	}
}

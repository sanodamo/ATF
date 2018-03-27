package org.staw.framework.helpers;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateExtension {
	public static String CurrentDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");  
		Date date = new Date();  
		return  formatter.format(date);
	} 
	
	public static String getCurrentTimestampFormat(){
		Date date = new java.util.Date();
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Timestamp(date.getTime()));
	}
}

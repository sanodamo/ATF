package org.staw.framework.helpers;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateExtension {
	static final int DAY=24;
	
	public static String CurrentDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");  
		Date date = new Date();  
		return  formatter.format(date);
	} 
	
	public static String AddDays(int days) {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");  
		Date date = new Date();  
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, days * DAY);
		
		return  formatter.format(calendar.getTime());
	} 
	
	public static String getCurrentTimestampFormat(){
		Date date = new java.util.Date();
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Timestamp(date.getTime()));
	}
}

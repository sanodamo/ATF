package org.staw.framework.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateExtension {
	public static String CurrentDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");  
		Date date = new Date();  
		return  formatter.format(date);
	} 
}

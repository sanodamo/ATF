package org.staw.framework.models;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.staw.testing.imasis.onc.*;

public enum Keywords {
	/*
	 * NO-Class
	 */
	SETGLOBALVARIABLES(null,"Description", "Name","RacfID", "MM/dd/yyyy"),
	INITIALIZEDATA(null,"Description", "Name","RacfID", "MM/dd/yyyy"),
	INITIALROUTINES(null,"Description", "Name","RacfID", "MM/dd/yyyy"),
	SETGLOBALVARBYEXPRESSION(null,"Description", "Name","RacfID", "MM/dd/yyyy"),
	LOGIN(Login.class,"Description", "Name","RacfID", "MM/dd/yyyy"),
	CLOSEBROWSER(null,"Description", "Name","RacfID", "MM/dd/yyyy"),
	NAVIGATETOURL(Route.class,"Description", "Name","RacfID", "MM/dd/yyyy"),

	
	MEMBERPAGEACTION(Member.class,"Description", "Name","RacfID", "MM/dd/yyyy"),
	PHYSICIANPAGEACTION(Physician.class,"Description", "Name","RacfID", "MM/dd/yyyy");
	
	private final Class<?> className;	
	private final String description;
	private final String name;
	private final String racfid;
	private final String date;
	
	public static Keywords getKeyword(String kw){
		try{
			return Keywords.valueOf(kw);
		}catch(IllegalArgumentException | NullPointerException e){
			return null;
		}
	}
	
	public Class<?> getClassName() {
		return this.className;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getRacfid() {
		return this.racfid;
	}
	
	public String getDate() {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		return df.format(this.date);
	}
	
	private Keywords(Class<?> className, String description, String name, String racfid, String date){
		this.className = className;
		this.description = description;
		this.name = name;
		this.racfid = racfid;
		this.date = date;
	}
	
	private Keywords(){
		this.className = null;
		this.description = "No Description";
		this.name = "No Name";
		this.racfid = "No Racfid";
		this.date = "No Date";
	}
}
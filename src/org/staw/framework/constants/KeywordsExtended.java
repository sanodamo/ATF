package org.staw.framework.constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.staw.testing.imasis.onc.*;

public enum KeywordsExtended {
	
	MEMBERPAGEACTION(Member.class,"Description", "Name","RacfID", "MM/dd/yyyy"),
	PHYSICIANPAGEACTION(Physician.class,"Description", "Name","RacfID", "MM/dd/yyyy");
	
	private final Class<?> className;
	private final String description;
	private final String name;
	private final String racfid;
	private final String date;
	
	public static KeywordsExtended getKeyword(String kw){
		try{
			return KeywordsExtended.valueOf(kw);
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
	private KeywordsExtended(Class<?> className, String description, String name, String racfid, String date){
		this.className = className;
		this.description = description;
		this.name = name;
		this.racfid = racfid;
		this.date = date;
	}
	
	private KeywordsExtended(){
		this.className = null;
		this.description = "No Description";
		this.name = "No Name";
		this.racfid = "No Racfid";
		this.date = "No Date";
	}
}


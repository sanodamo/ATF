package org.staw.framework.models;


import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.staw.testing.imasis.*;

public enum KeywordRegistry {
		
	INITIALIZE(null,"Description", "Name","UserID", "MM/dd/yyyy"),	
	
	LOGIN(Login.class,"Description", "Name","UserID", "MM/dd/yyyy"),
	CLOSEBROWSER(null,"Description", "Name","UserID", "MM/dd/yyyy"),
	NAVIGATETOURL(Navigation.class,"Description", "Name","UserID", "MM/dd/yyyy"),

	CALLACTION(Call.class,"Description", "Name","UserID", "MM/dd/yyyy"),
	FILLCALLERDETAILS(Call.class,"Description", "Name","UserID", "MM/dd/yyyy"),
	MEMBERSEARCH(Member.class,"Description", "Name","UserID", "MM/dd/yyyy"),
	MEMBERSELECT(Member.class,"Description", "Name","UserID", "MM/dd/yyyy"),
	
	PHYSICIANSEARCHBYNPI(Physician.class,"Description", "Name","UserID", "MM/dd/yyyy"),
	PHYSICIANSELECT(Physician.class,"Description", "Name","UserID", "MM/dd/yyyy"),
	
	PROVIDERSEARCHBYNPI(Provider.class,"Description", "Name","UserID", "MM/dd/yyyy"),
	PROVIDERSELECT(Provider.class,"Description", "Name","UserID", "MM/dd/yyyy"),
	PLACEOFSERVICE(Provider.class,"Description", "Name","UserID", "MM/dd/yyyy"),
	TREATMENTSELECTION(Modality.class,"Description", "Name","UserID", "MM/dd/yyyy"),
	PREEXAMQUESTIONS(Modality.class,"Description", "Name","UserID", "MM/dd/yyyy"),
	
	APCLINICALQUESTION(Clinical.class,"Description", "Name","UserID", "MM/dd/yyyy"),
	APCLINICALCOMPLETE(Clinical.class,"Description", "Name","UserID", "MM/dd/yyyy"),
	APTREATMENTQUESTION(Clinical.class,"Description", "Name","UserID", "MM/dd/yyyy"),
	
	APREVIEWCONFIRM(Clinical.class,"Description", "Name","UserID", "MM/dd/yyyy"),
	
	EVITISTART(Clinical.class,"Description", "Name","UserID", "MM/dd/yyyy"),
	PATIENTHEIGHTWEIGHT(Clinical.class,"Description", "Name","UserID", "MM/dd/yyyy"),
	REGIMENSEARCH(Clinical.class,"Description", "Name","UserID", "MM/dd/yyyy"),
	BUILDTREATMENTPLAN(Clinical.class,"Description", "Name","UserID", "MM/dd/yyyy"),
	ENTERDIAGNOSIS(Clinical.class,"Description", "Name","UserID", "MM/dd/yyyy"),
	ENTERBIOMARKER(Clinical.class,"Description", "Name","UserID", "MM/dd/yyyy"),
	SAVEANDCONTINUEEVITI(Clinical.class,"Description", "Name","UserID", "MM/dd/yyyy"),
	REMOVEDRUG(Clinical.class,"Description", "Name","UserID", "MM/dd/yyyy"),
	ADDDRUG(Clinical.class,"Description", "Name","UserID", "MM/dd/yyyy"),
	EDITOUTCOME(Clinical.class,"Description", "Name","UserID", "MM/dd/yyyy"),
	SELECTDRUG(Clinical.class,"Description", "Name","UserID", "MM/dd/yyyy"),
	EDITREGIMEN(Clinical.class,"Description", "Name","UserID", "MM/dd/yyyy"),
	
	PRODUCTSELECTION(Member.class,"Description", "Name","UserID", "MM/dd/yyyy"),
	TREATMENTDATESELECTION(Member.class,"Description", "Name","UserID", "MM/dd/yyyy"),
	SWITCHTAB(CommonActions.class,"Description", "Name","UserID", "MM/dd/yyyy"),
	CASEACTIONS(CommonActions.class,"Description", "Name","UserID", "MM/dd/yyyy"),
	SWITCHFRAME(CommonActions.class,"Description", "Name","UserID", "MM/dd/yyyy");
	
	
	
	private final Class<?> className;	
	private final String description;
	private final String name;
	private final String UserID;
	private final String date;
	
	public static KeywordRegistry getKeyword(String kw){
		try{
			return KeywordRegistry.valueOf(kw);
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
	
	public String getUserID() {
		return this.UserID;
	}
	
	public String getDate() {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		return df.format(this.date);
	}
	
	private KeywordRegistry(Class<?> className, String description, String name, String UserID, String date){
		this.className = className;
		this.description = description;
		this.name = name;
		this.UserID = UserID;
		this.date = date;
	}
	
	private KeywordRegistry(){
		this.className = null;
		this.description = "No Description";
		this.name = "No Name";
		this.UserID = "No UserID";
		this.date = "No Date";
	}
}
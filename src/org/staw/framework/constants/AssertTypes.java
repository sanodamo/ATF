package org.staw.framework.constants;

public enum AssertTypes {
	SUCCESS("success"),
	FAILED("failed"),
	WARNING("warning"),
	ERROR("error");
	
	public String assertType;
	private AssertTypes(String assertType){
		this.assertType = assertType;
	}
	public String getAssertType(){
		return this.assertType;
	}
	public static AssertTypes getAssertType(String val){
		for(AssertTypes et: AssertTypes.values()){
			if(et.getAssertType().equalsIgnoreCase(val.trim())) return et;
		}
		return null;
	}	
}

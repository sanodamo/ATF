package org.staw.framework.constants;

	public enum BrowserTargetType {		
		FIREFOX("Firefox"), 		
		INTERNET_EXPLORER("Internet Explorer"), 
		MICROSOFT_EDGE("Microsoft Edge"),		
		CHROME("Chrome"), 		 
		SAFARI("Safari"),
		REMOTE("Remote");
		
		public String targetType;
		private BrowserTargetType(String targetType){
			this.targetType = targetType;
		}
		public String getTargetType(){
			return this.targetType;
		}
		public static BrowserTargetType getTargetType(String val){
			for(BrowserTargetType et: BrowserTargetType.values()){
				if(et.getTargetType().equalsIgnoreCase(val.trim())) return et;
			}
			return null;
		}	
	}




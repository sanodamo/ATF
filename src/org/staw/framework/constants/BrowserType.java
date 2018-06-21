package org.staw.framework.constants;

	public enum BrowserType {		
		FIREFOX("Firefox"), 		
		INTERNET_EXPLORER("Internet Explorer"), 
		MICROSOFT_EDGE("Microsoft Edge"),		
		CHROME("Chrome"), 		 
		SAFARI("Safari"),
		REMOTE("Remote");
		
		public String targetType;
		private BrowserType(String targetType){
			this.targetType = targetType;
		}
		public String getTargetType(){
			return this.targetType;
		}
		public static BrowserType getTargetType(String val){
			for(BrowserType et: BrowserType.values()){
				if(et.getTargetType().equalsIgnoreCase(val.trim())) return et;
			}
			return null;
		}	
	}




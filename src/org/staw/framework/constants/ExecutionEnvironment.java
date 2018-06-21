package org.staw.framework.constants;

	public enum ExecutionEnvironment {		
		LOCAL("Local"), 				
		REMOTE("Remote");
		
		public String location;
		private ExecutionEnvironment(String targetType){
			this.location = targetType;
		}
		public String getLocation(){
			return this.location;
		}
		public static ExecutionEnvironment getLocation(String val){
			for(ExecutionEnvironment et: ExecutionEnvironment.values()){
				if(et.getLocation().equalsIgnoreCase(val.trim())) return et;
			}
			return null;
		}	
	}




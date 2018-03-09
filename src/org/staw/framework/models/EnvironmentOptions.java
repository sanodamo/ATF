package org.staw.framework.models;

public class EnvironmentOptions {
	private String platform;
	private String platformVersion;
	private String osVersion;
	
	public EnvironmentOptions(String platform, String platformVersion, String osVersion) {
		this.platform = platform;
		this.platformVersion = platformVersion;
		this.osVersion = osVersion;
	}
	public String getBrowser() {
		return platform;
	}
	public void setBrowser(String platform) {
		this.platform = platform;
	}
	public String getBrowserVersion() {
		return platformVersion;
	}
	public void setBrowserVersion(String platformVersion) {
		this.platformVersion = platformVersion;
	}
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	
	
}

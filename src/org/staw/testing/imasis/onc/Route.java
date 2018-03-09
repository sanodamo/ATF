package org.staw.testing.imasis.onc;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.staw.framework.SeleniumDriver;
import org.staw.framework.SoftAssertion;
import org.staw.framework.helpers.Utilities;

public class Route {
	public static Logger logger = Logger.getLogger(Utilities.class.getName());
	private static SoftAssertion myAssert;
	private Utilities util;
	public Route(SoftAssertion myAssert) {
		this.myAssert = myAssert;
		util = new Utilities(myAssert);
	}
	
	public boolean navigateToUrl(String url) {
		WebDriver driver = SeleniumDriver.getInstance().getWebDriver();
		String currRunEnv = Utilities.getTestRunningEnvironmentVariable();
		Utilities util = new Utilities(myAssert); 		
		try {
			if (!url.isEmpty()) {
							
				driver.get(url);
				util.syncBrowser();
										
				SeleniumDriver.getJavaScriptErrors();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return myAssert.Failed("Unable to open url: " + url + ", Error: " + e.getMessage());
		}
		return myAssert.Success("Successfully navigated to: " + url);
	}
		
}

package org.staw.testing.imasis.onc;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.staw.framework.SeleniumDriver;
import org.staw.framework.SeleniumWrapper;
import org.staw.framework.SoftAssertion;
import org.staw.framework.helpers.EnviromentSetupHelper;


public class Route {
	public static Logger logger = Logger.getLogger(Route.class.getName());
	private static SoftAssertion myAssert;
	
	public Route(SoftAssertion myAssert) {
		this.myAssert = myAssert;
		
	}
	
	public boolean navigateToUrl(String url) {
		WebDriver driver = SeleniumDriver.getInstance().getWebDriver();
		String currRunEnv = EnviromentSetupHelper.getTestRunningEnvironmentVariable();
		 		
		try {
			if (!url.isEmpty()) {
							
				driver.get(url);
				SeleniumWrapper.syncBrowser();
										
				SeleniumDriver.getJavaScriptErrors();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return myAssert.Failed("Unable to open url: " + url + ", Error: " + e.getMessage());
		}
		return myAssert.Success("Successfully navigated to: " + url);
	}
		
}

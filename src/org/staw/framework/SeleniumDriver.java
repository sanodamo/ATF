package org.staw.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.logging.Level;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.staw.framework.constants.BrowserTargetType;
import org.staw.framework.constants.DriverVariables;

public class SeleniumDriver {
	
	public static Logger log = Logger.getLogger(SeleniumDriver.class.getName());
	private ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();
	private static SeleniumDriver instance = new SeleniumDriver();	
	
    public static SeleniumDriver getInstance(){
    		return instance;
    }
	   
       
	public WebDriver getDriver(String tcName, String browser, String browserVersion, String osVersion, String executionEnv, SoftAssertion sa) {
		
		BrowserTargetType currType =  BrowserTargetType.getTargetType(executionEnv);		
		try {
			switch(currType){			
			case INTERNET_EXPLORER:			
				getIEDriver();
				break;
				
			case FIREFOX:			
				getFFDriver();
				break; 
				
			case CHROME:										
				getChromeDriver();
				break;
				
			case SAFARI:
			
				
			default:
				log.error(currType + " is invalid");
				return null;
				
			}
			if(webDriver.get() != null){
				webDriver.get().manage().window().maximize();	
				return webDriver.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
			sa.Failed("Unable to create driver" + e.getMessage());
			return null;
		}
		return null;
	}


	private void getChromeDriver() {		
		System.setProperty("webdriver.chrome.driver",DriverVariables.getFilePath(DriverVariables.CHROME_DRIVER_WINDOWS));				
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		LoggingPreferences loggingprefs = new LoggingPreferences();
		loggingprefs.enable(LogType.BROWSER, Level.SEVERE);
		capabilities.setCapability(CapabilityType.LOGGING_PREFS, loggingprefs);		       
		this.webDriver.set(new ChromeDriver(capabilities));
	}


	private void getFFDriver() {		 
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();				
		capabilities.setCapability(FirefoxDriver.PROFILE, true);
		System.setProperty("webdriver.gecko.driver", DriverVariables.getFilePath(DriverVariables.GECKO_DRIVER_WIN));
		capabilities.setCapability(FirefoxDriver.PROFILE, new FirefoxProfile());
		LoggingPreferences pref = new LoggingPreferences();
		pref.enable(LogType.BROWSER, Level.OFF);
		pref.enable(LogType.CLIENT, Level.OFF);
		pref.enable(LogType.DRIVER, Level.OFF);
		pref.enable(LogType.PERFORMANCE, Level.OFF);
		pref.enable(LogType.PROFILER, Level.OFF);
		pref.enable(LogType.SERVER, Level.OFF);
		capabilities.setCapability(CapabilityType.LOGGING_PREFS, pref);
		webDriver.set(new FirefoxDriver(capabilities));
	}


	private void getIEDriver() {		
		System.setProperty("webdriver.ie.driver",DriverVariables.getFilePath(DriverVariables.INTERNET_EXPLORER_DRIVER));
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
		capabilities.setCapability("ignoreZoomSetting", true);
		this.webDriver.set(new InternetExplorerDriver(capabilities));
	}
	

	
	public WebDriver getWebDriver() {
		if (webDriver.get() != null) {
			return webDriver.get();
		}
		return null;
	}
	
	
	
	public void removeDriver(WebDriver dr, String tcName, Logger log){
    	StringBuilder output = new StringBuilder();
		output.append(System.getProperty("line.separator"));
		output.append("--------------------------------------Test-----------------------------------");
		output.append(System.getProperty("line.separator"));
		output.append("Ending Test: " + tcName);
		output.append(System.getProperty("line.separator"));
		
		if(dr != null){
    		output.append("Driver: "+ dr.toString());
    		output.append(System.getProperty("line.separator"));
    		output.append("Hash code: " + dr.hashCode());
    		output.append(System.getProperty("line.separator"));
    		output.append("Thread: " + Thread.currentThread().getId());
    		output.append(System.getProperty("line.separator"));
    		
    		dr.quit();
    		output.append(System.getProperty("line.separator"));
    	}
	
		output.append("-----------------------------------------------------------------------------");
		output.append(System.getProperty("line.separator"));
		log.info(output.toString());
    } 
	
	public void removeDriver(String tcName) {
		if (webDriver.get() != null) {
			webDriver.get().quit();
		} 
	}
	
	  public static List<WebElement> fluentWaitFindElements(final WebDriver driver, final By locator, final int timeoutSeconds) {
	        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
	        		.withTimeout(timeoutSeconds, TimeUnit.SECONDS)
	                .pollingEvery(2, TimeUnit.SECONDS)
	                .ignoring(NoSuchElementException.class);

	        return wait.until(new Function<WebDriver, List<WebElement>>() {
	            @Override
				public List<WebElement> apply(WebDriver wbDriver) {
	                return driver.findElements(locator);
	            }
	        });
	    }
	  
	  public static void getJavaScriptErrors(){	    	
    		WebDriver driver = SeleniumDriver.getInstance().getWebDriver();
        	JavascriptExecutor js = (JavascriptExecutor) driver;
        	Object temp = js.executeScript("return window.jsErrors");
        	if(temp instanceof ArrayList<?>){
    			@SuppressWarnings("unchecked")
				ArrayList<Object> dataMap =  (ArrayList<Object>) temp;
        		if(dataMap.size() != 0){
        			for(Object val: dataMap){
        				log.info(val);
        			}
        		}
        	}	    	
	    }
	  
	  public static WebElement fluentWaitFindElement(final WebDriver driver, final By locator, final int timeoutSeconds) {
		    FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
		            .withTimeout(timeoutSeconds, TimeUnit.SECONDS)
		            .pollingEvery(2, TimeUnit.SECONDS)
		            .ignoring(NoSuchElementException.class);
		    
		    return wait.until(new Function<WebDriver, WebElement>(){
		    	@Override
				public WebElement apply(WebDriver wbDriver){
		    		return driver.findElement(locator);
		    	}
		    });
		}
	  
	  public static WebElement fluentWaitFindElement(final WebElement element, final By locator, final int timeoutSeconds) {
			WebDriver driver = SeleniumDriver.getInstance().getWebDriver();
		    FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
		            .withTimeout(timeoutSeconds, TimeUnit.SECONDS)
		            .pollingEvery(2, TimeUnit.SECONDS)
		            .ignoring(NoSuchElementException.class);
		    
		    return wait.until(new Function<WebDriver, WebElement>(){
		    	@Override
				public WebElement apply(WebDriver wbDriver){
		    		return element.findElement(locator);
		    	}
		    });
		}
	  
	  public static List<WebElement> fluentWaitFindElements(final WebElement element, final By locator, final int timeoutSeconds) {
	    	WebDriver driver = SeleniumDriver.getInstance().getWebDriver();
	        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timeoutSeconds, TimeUnit.SECONDS)
	                .pollingEvery(2, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

	        return wait.until(new Function<WebDriver, List<WebElement>>() {
	            @Override
				public List<WebElement> apply(WebDriver wbDriver) {
	                return element.findElements(locator);
	            }
	        });
	    }
}

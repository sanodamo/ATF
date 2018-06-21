package org.staw.framework;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.staw.framework.constants.EnviromentProperties;
import org.staw.framework.constants.GlobalConstants;
import org.staw.framework.constants.GlobalConstants.UtilitiesConstants;


public class SeleniumWrapper {
	public enum ELEMENT_ACTIONS {CLICK,FILL,COMPAREATTRIBUTE,COMPARETEXT};
	private static Logger log = Logger.getLogger(SeleniumWrapper.class.getName());
	private static boolean elemFound;
	private enum BROWSER_READY_STATE{
		LOADING, INTERACTIVE, COMPLETE;
	}

	public static boolean switchFrame(String frame, By locator, CommonAssertion commonAssertion) {

		try {
			WebDriver driver = DriverFactory.getInstance().getWebDriver();
			driver.switchTo().defaultContent();
			if(!frame.equalsIgnoreCase("default") && locator != null) {					
				driver.switchTo().frame( DriverFactory.fluentWaitFindElement(driver, locator, 30));
			}
			return commonAssertion.Success("switched to the frame " + frame);
		}
		catch(Exception e) {
			return commonAssertion.Failed("Unable to switch the frame " + frame);
		}
	}

	public static boolean clickOnElement(WebElement webElement, By type, String Message, CommonAssertion commonAssertion) {
		try {
			DriverFactory.fluentWaitFindElement(webElement, type, 20).click();
		} catch (Exception e) {
			return commonAssertion.Failed("Unable to click '" + Message + "' Element, " + e.toString());
		}
		return commonAssertion.Success(Message + " Element clicked");
	}

	public static boolean clickOnElement(By type, CommonAssertion commonAssertion) {
		try {
			WebDriver driver = DriverFactory.getInstance().getWebDriver();
			DriverFactory.fluentWaitFindElement(driver,type, 20).click();
		} catch (Exception e) {
			return commonAssertion.Failed("Unable to click Element, " + e.toString());
		}
		return commonAssertion.Success(" Element clicked");
	}

	public static boolean clickOnLink(By type, String Message, CommonAssertion commonAssertion) {		
		WebDriver driver = DriverFactory.getInstance().getWebDriver();
		return clickOnLink(driver,type,Message,commonAssertion);		
	}

	public static boolean clickOnLink(WebDriver driver, By type, String Message, CommonAssertion commonAssertion) {
		try {
			DriverFactory.fluentWaitFindElement(driver, type, 20).click();
		} catch (Exception e) {
			return commonAssertion.Failed("Unable to click '" + Message + "' link, " + e.toString());
		}
		return commonAssertion.Success(Message + " link clicked");
	}
		
	public static boolean fillTextField(By element, String whatValue, String fieldName,	boolean isSubmit, CommonAssertion commonAssertion) {
		
		WebElement oField = getElement(element, commonAssertion);
		if(oField != null) {
			return fillTextField(oField, whatValue, fieldName, isSubmit, commonAssertion);
		}
		return commonAssertion.Failed("Unable to find " + fieldName + " field.");
	}

	public static boolean fillTextFieldAndTabOut(By element, String whatValue, String fieldName, CommonAssertion commonAssertion) {		
		return fillTextField(element, whatValue, fieldName, Keys.TAB , commonAssertion);
	}

	public static boolean fillTextField(By element, String whatValue, String fieldName,Keys key, CommonAssertion commonAssertion) {
		WebElement oField = getElement(element, commonAssertion);
		if(oField != null) {
			fillTextField(oField, whatValue, fieldName, false, commonAssertion);
			syncBrowser();
			oField.sendKeys(key);
			return commonAssertion.Success("Successfully entered " + whatValue + " in field " + fieldName);
		}
		return commonAssertion.Failed("Unable to find " + fieldName + " field.");
	}


	public static boolean fillTextField(WebElement parentElement, By childElement, String whatValue, String fieldName,
			boolean isSubmit, CommonAssertion commonAssertion) {
		WebElement oField = getElement(parentElement, childElement, commonAssertion);
		if(oField != null) {
			return fillTextField(oField, whatValue, fieldName, isSubmit, commonAssertion);
		}
		return commonAssertion.Failed("Unable to find " + fieldName + " field.");
	}


	public static boolean fillTextField(WebElement webElement, String whatValue, String fieldName, boolean isSubmit,
			CommonAssertion commonAssertion) {
		try {
			webElement.clear();
			webElement.sendKeys(whatValue);
			if (isSubmit) {
				webElement.submit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return commonAssertion.Failed("Unable to find " + fieldName + " field.");
		}
		return commonAssertion.Success("Successfully entered " + whatValue + " in field " + fieldName);
	}


	public static boolean compareValuesIfEqual(String actual, String expected, CommonAssertion commonAssertion) {
		if (actual.toLowerCase().trim().equals(expected.toLowerCase().trim())) {
			return commonAssertion.Success("Successfully verified. Actual : " + actual + ", " + "Expected : " + expected);
		} else {
			return commonAssertion.Failed("Verification failed. Actual : " + actual + ", " + "Expected : " + expected);
		}
	}


	public static boolean compareValuesIfContains(String stringToContainValue, String containingValue,
			CommonAssertion commonAssertion) {
		if (stringToContainValue.toLowerCase().trim().contains(containingValue.toLowerCase().trim())) {
			return commonAssertion.Success("Successfully verified : " + stringToContainValue + ", CONTAINS :  " + containingValue);
		} else {
			return commonAssertion.Failed(
					"Verification failed. Actual : " + stringToContainValue + " " + "Expected : " + containingValue);
		}
	}


	public static String getTextByValue(By method) {
		WebDriver driver = DriverFactory.getInstance().getWebDriver();
		try {
			return DriverFactory.fluentWaitFindElement(driver, method, 20).getAttribute(UtilitiesConstants.VALUE);
		} catch (Exception e) {
			return GlobalConstants.ContextConstant.STRING_EMPTY;
		}
	}

	public static boolean clickOnElementByIndex(int index, By locator, String message, CommonAssertion commonAssertion) {
		WebDriver driver = DriverFactory.getInstance().getWebDriver();
		try {
			List<WebElement> buttons = SeleniumWrapper.getElements(locator, commonAssertion);			
			SeleniumWrapper.javaScriptExecutorClickElement(driver, commonAssertion,UtilitiesConstants.JAVASCRIPT_CLICK_ELEMENT, buttons.get(index));
			return commonAssertion.Success("Successfully clicked " + message + " element");			

		} catch (Exception e) {
			return commonAssertion.Failed("Unable to find " + message + " element");
		}		
	}	

	public static boolean clickOnListElement(String element, By locator, CommonAssertion commonAssertion) {
		WebDriver driver = DriverFactory.getInstance().getWebDriver();
		try {
			List<WebElement> buttons = SeleniumWrapper.getElements(locator, commonAssertion);
			for (WebElement button : buttons) {
				if (button.getText().equalsIgnoreCase(element.trim()) && button.isEnabled()) {
					SeleniumWrapper.javaScriptExecutorClickElement(driver, commonAssertion,UtilitiesConstants.JAVASCRIPT_CLICK_ELEMENT, button);
					return commonAssertion.Success("Successfully clicked " + element + " element");
				}
			}
		} catch (Exception e) {
			return commonAssertion.Failed("Unable to find " + element + " element");
		}
		return commonAssertion.Failed("Unable to find element " + element);
	}	


	public static boolean performActionOnElementInList(List<WebElement> listElement, String attributeName,
			String attributeValue, ELEMENT_ACTIONS action, CommonAssertion commonAssertion, String strValue1,
			String strValue2) {
		WebDriver driver = DriverFactory.getInstance().getWebDriver();
		WebElement elem = getElementFromAList(listElement, attributeName, attributeValue, commonAssertion);
		if (elem != null) {
			switch (action) {
			case CLICK:
				return javaScriptExecutorClickElement(driver, commonAssertion, UtilitiesConstants.JAVASCRIPT_CLICK_ELEMENT,
						elem);
			case FILL:
				return fillTextField(elem, strValue1, strValue1, false, commonAssertion);
			case COMPAREATTRIBUTE:
				return compareValuesIfEqual(elem.getAttribute(strValue2), strValue1, commonAssertion);
			case COMPARETEXT:
				return compareValuesIfEqual(elem.getText(), strValue1, commonAssertion);
			default:
				return commonAssertion.Failed("Action is not defined");
			}
		}
		return commonAssertion.Failed("Action is not defined");
	}


	public static WebElement getElementFromAList(List<WebElement> listElement, String attributeName,
			String attributeValue, CommonAssertion commonAssertion) {
		for (WebElement cElement : listElement) {
			if (cElement.getAttribute(attributeName).contains(attributeValue)) {
				return cElement;
			}
		}
		return null;
	}

	public static boolean selectByValue(By elementType, String whatValue, String fieldName, CommonAssertion commonAssertion) {
		WebDriver driver = DriverFactory.getInstance().getWebDriver();
		return selectByAnyMethod(driver,elementType,whatValue,"text", fieldName, commonAssertion);
	}

	public static boolean selectByIndex(By elementType, String whatValue, String fieldName, CommonAssertion commonAssertion) {
		WebDriver driver = DriverFactory.getInstance().getWebDriver();
		return selectByAnyMethod(driver,elementType,whatValue,"index", fieldName, commonAssertion);
	}

	public static boolean selectByAnyMethod(WebDriver driver, By elementType, String whatValue, String selectByType,
			String fieldName, CommonAssertion commonAssertion) {
		Select oSelect;

		try {
			oSelect = new Select(DriverFactory.fluentWaitFindElement(driver, elementType, 20));
			switch (selectByType) {
			case "value":
				oSelect.selectByValue(whatValue);
				break;
			case "text":
			case "visibletext":
				oSelect.selectByVisibleText(whatValue);
				break;
			case "index":
				oSelect.selectByIndex(Integer.parseInt(whatValue));
				break;
			default:
				return commonAssertion.Failed("Please provide a valid 'SelectByType'");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return commonAssertion.Failed("Unable to find " + fieldName + " select field.");
		}

		return commonAssertion.Success("Successfully selected " + whatValue + " from select field " + fieldName);
	}


	public static boolean clickOnInputSubmitByValue(WebDriver driver, String submitValue, String fieldName,
			CommonAssertion commonAssertion) {
		return clickOnLink(driver, By.cssSelector("input[type='submit'][value='" + submitValue + "']"), fieldName, commonAssertion);
	}


	public static boolean javaScriptExecutorClickElement(WebDriver driver, CommonAssertion commonAssertion, String script, Object... args){
		return executeJavaScript(driver, commonAssertion,script, args);
	}



	public static boolean executeJavaScript(WebDriver driver,CommonAssertion commonAssertion, String script, Object... args){
		try{
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript(script, args);
			return commonAssertion.Success("Web Element clicked successfully using JavaScript Executor Command");
		}catch(Exception e){
			return commonAssertion.Failed("Failed to click WebElement using JavaScript Executor Command");
		}
	}


	public static Object executeJavaScript(WebDriver driver, String script){
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		return executor.executeScript(script);
	}


	public static Object javaScriptExecutorGetElementValue(WebElement element){
		WebDriver driver = DriverFactory.getInstance().getWebDriver();
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		return executor.executeScript(UtilitiesConstants.JAVASCRIPT_GET_ELEMENT_VALUE, element);
	}


	public static boolean javaScriptExecutorScrollToEndOfPage(){
		WebDriver driver = DriverFactory.getInstance().getWebDriver();
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript(UtilitiesConstants.JAVASCRIPT_SCROLL_TO_END_OF_PAGE);
		return true;
	}


	public static boolean isElementExistOnPage(By elementType, CommonAssertion commonAssertion){
		WebDriver driver = DriverFactory.getInstance().getWebDriver();
		try {
			WebElement elementToFind = DriverFactory.fluentWaitFindElement(driver, elementType, 30);
			if(elementToFind != null){
				return commonAssertion.Success("Element " + elementType.toString() + " is present on page");
			}
		} catch (Exception e){
			return commonAssertion.Failed("Error finding element " + elementType.toString() + " on page");
		}
		return commonAssertion.Failed("Element " + elementType.toString() + " is not present on page");
	}


	public static boolean isElementExistInside(WebElement parentElement, By childElementType, CommonAssertion commonAssertion){
		try {
			WebElement elementToFind = DriverFactory.fluentWaitFindElement(parentElement, childElementType, 30);    		 
			if(elementToFind!=null){
				return commonAssertion.Success("Element " + childElementType.toString() + " is present on page");
			}
		} catch (Exception e){
			return commonAssertion.Failed("Error finding element " + childElementType.toString() + " on page");
		}
		return commonAssertion.Failed("Element " + childElementType.toString() + " is not present on page");
	}


	public static WebElement getElement(By element, CommonAssertion commonAssertion){
		WebDriver driver = DriverFactory.getInstance().getWebDriver();
		WebElement elementToFind = null;
		if(isElementExistOnPage(element, commonAssertion))
			elementToFind = DriverFactory.fluentWaitFindElement(driver, element, 30);
		return elementToFind;
	}



	public static List<WebElement> getElements(By element, CommonAssertion commonAssertion){
		WebDriver driver = DriverFactory.getInstance().getWebDriver();
		List<WebElement> elementsToFind = new ArrayList<>();
		if(isElementExistOnPage(element, commonAssertion))
			elementsToFind = DriverFactory.fluentWaitFindElements(driver, element, 20);
		return elementsToFind;
	}


	public static WebElement getElement(WebElement parentElement, By childElement, CommonAssertion commonAssertion){
		WebElement elementToFind = null;
		if(isElementExistInside(parentElement, childElement, commonAssertion))
			elementToFind = DriverFactory.fluentWaitFindElement(parentElement, childElement, 20);
		return elementToFind;
	}


	public static List<WebElement> getElements(WebElement parentElement, By childElement, CommonAssertion commonAssertion){
		List<WebElement> elementsToFind = null;
		if(isElementExistInside(parentElement, childElement, commonAssertion))
			elementsToFind = DriverFactory.fluentWaitFindElements(parentElement, childElement, 20);
		return elementsToFind;
	}
	
	public static WebElement getChildElement(WebElement parent, By grandchild, CommonAssertion commonAssertion) {
		elemFound = false;
		WebElement child = null;		
		List<WebElement> children = DriverFactory.fluentWaitFindElements(parent, By.xpath(".//*"), 30);
		if (children != null) {
			for (WebElement element : children) {
				child = SeleniumWrapper.getChildElement(element, grandchild, commonAssertion);
				if (child != null) {
					elemFound = true;
					break;
				} else {
					child = getChildElement(element, grandchild, commonAssertion);
					if (elemFound) {
						break;
					}
				}
			}
		}
		return child;
	}
	
	public static boolean isElementEnabled(By elementType, CommonAssertion commonAssertion) {
		if (isElementExistOnPage(elementType, commonAssertion)) {
			WebElement currElement = getElement(elementType, commonAssertion);
			if (currElement == null) {
				return commonAssertion.Failed("Unable to find element " + elementType + " on the page");
			} else {
				return currElement.isEnabled();
			}
		} else {
			return commonAssertion.Failed("Element " + elementType + " does not exist on page");
		}
	}


	public static boolean isElementEnabled(WebElement parentElement, By elementType, CommonAssertion commonAssertion) {
		if (isElementExistInside(parentElement, elementType, commonAssertion)) {
			WebElement currElement = getElement(parentElement, elementType, commonAssertion);
			if (currElement == null) {
				return commonAssertion.Failed("Unable to find element " + elementType + " on the page");
			} else {
				return currElement.isEnabled();
			}
		} else {
			return commonAssertion.Failed("Element " + elementType + " does not exist on page");
		}
	}


	public static boolean isElementDisplayed(By elementType, CommonAssertion commonAssertion) {
		if (isElementExistOnPage(elementType, commonAssertion)) {
			WebElement currElement = getElement(elementType, commonAssertion);
			if (currElement == null) {
				return commonAssertion.Failed("Unable to find element " + elementType + " displayed on the page");
			}
			if (currElement.isDisplayed()) {
				return commonAssertion.Success("Able to find element " + elementType + " displayed on the page");
			}
		}
		return commonAssertion.Failed("Element " + elementType + " does not exist on page");
	}



	public static boolean isElementDisplayed(WebElement parentElement, By elementType, CommonAssertion commonAssertion) {
		if (isElementExistInside(parentElement, elementType, commonAssertion)) {
			WebElement currElement = getElement(parentElement, elementType, commonAssertion);
			if (currElement == null) {
				return commonAssertion.Failed("Unable to find element " + elementType + " displayed on the page");
			}
			if (currElement.isDisplayed()) {
				return commonAssertion.Success("Able to find element " + elementType + " displayed on the page");
			}
		}
		return commonAssertion.Failed("Element " + elementType + " does not exist on page");
	}


	public static boolean isElementSelected(By elementType, CommonAssertion commonAssertion) {
		if (isElementExistOnPage(elementType, commonAssertion)) {
			WebElement currElement = getElement(elementType, commonAssertion);
			if (currElement == null) {
				return commonAssertion.Failed("Unable to find element " + elementType + " selected on the page");
			}
			if (currElement.isSelected()) {
				return commonAssertion.Success("Able to find element " + elementType + " selected on the page");
			}
		}
		return commonAssertion.Failed("Element " + elementType + " does not exist on page");
	}




	public static boolean isElementSelected(WebElement parentElement, By elementType, CommonAssertion commonAssertion) {
		if (isElementExistInside(parentElement, elementType, commonAssertion)) {
			WebElement currElement = getElement(parentElement, elementType, commonAssertion);
			if (currElement == null) {
				return commonAssertion.Failed("Unable to find element " + elementType + " selected on the page");
			}
			if (currElement.isSelected()) {
				return commonAssertion.Success("Able to find element " + elementType + " selected on the page");
			}
		}
		return commonAssertion.Failed("Element " + elementType + " does not exist on page");
	}


	public static String getAttributeValueByIndex(WebElement parentElement, By Element, String valueIndex,
			String attributeValue, CommonAssertion commonAssertion) {
		try {
			List<WebElement> oRows = SeleniumWrapper.getElements(parentElement, Element, commonAssertion);
			return oRows.get(Integer.parseInt(valueIndex)).getAttribute(attributeValue);
		} catch (Exception e) {
			return "";
		}
	}


	public static String getElementAttributeValue(By elementType, String attribute, CommonAssertion commonAssertion) {
		WebElement eleToFind = getElement(elementType, commonAssertion);
		if (eleToFind != null) {
			return eleToFind.getAttribute(attribute);
		}
		return GlobalConstants.ContextConstant.STRING_EMPTY;
	}


	public static String getElementAttributeValue(WebElement oParent, By elementType, String attribute,
			CommonAssertion commonAssertion) {
		WebElement eleToFind = getElement(oParent, elementType, commonAssertion);
		if (eleToFind != null) {
			return eleToFind.getAttribute(attribute);
		}
		return GlobalConstants.ContextConstant.STRING_EMPTY;
	}


	public static String getElementText(By elementType, CommonAssertion commonAssertion) {
		WebElement eleToFind = getElement(elementType, commonAssertion);
		if (eleToFind != null) {
			return eleToFind.getText();
		}
		return GlobalConstants.ContextConstant.STRING_EMPTY;
	}


	public static String getElementText(WebElement oParent, By elementType, CommonAssertion commonAssertion) {
		WebElement eleToFind = getElement(oParent, elementType, commonAssertion);
		if (eleToFind != null) {
			return eleToFind.getText();
		}
		return GlobalConstants.ContextConstant.STRING_EMPTY;
	}


	public static boolean clickOnCoordinates(WebElement htmlObject, int xOffset, int yOffset, CommonAssertion commonAssertion) {
		WebDriver driver = DriverFactory.getInstance().getWebDriver();
		try {
			Actions builder = new Actions(driver);
			Actions hoverOverRegistrar = builder.moveToElement(htmlObject);
			hoverOverRegistrar.perform();
			builder.moveToElement(htmlObject, xOffset, yOffset).click().build().perform();
			return commonAssertion.Success("Successfully clicked on element");
		} catch (Exception e) {
			return commonAssertion.Failed("Not able to click element");
		}
	}


	public static boolean verifyContent(WebElement parent, By elementType, String expected, CommonAssertion commonAssertion) {
		String actualContent = SeleniumWrapper.getElementText(parent, elementType, commonAssertion);

		return compareValuesIfEqual(actualContent.replaceAll(UtilitiesConstants.REGEX_SPECIAL_CHARACTERS, ""), expected.replaceAll(UtilitiesConstants.REGEX_SPECIAL_CHARACTERS, ""), commonAssertion)
				|| compareValuesIfContains(actualContent.replaceAll(UtilitiesConstants.REGEX_SPECIAL_CHARACTERS, ""), expected.replaceAll(UtilitiesConstants.REGEX_SPECIAL_CHARACTERS, ""), commonAssertion);
	}

	private static void theadSleep(long time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {
			log.error("Unable to sleep, " + e.toString());
		}
	}

	public static boolean BrowserLoadComplete() {
		return isBrowserReady(BROWSER_READY_STATE.COMPLETE);
	}
	
	private static boolean isBrowserReady(BROWSER_READY_STATE state) {
		WebDriver driver = DriverFactory.getInstance().getWebDriver();

		long timeSliceMs = Long.parseLong(EnviromentProperties.TIME_SLICE_MS);
		int timeOutSecond = Integer.parseInt(EnviromentProperties.TIME_OUT_SECONDS);
		String readyState = null;

		do {
			theadSleep(timeSliceMs);
			timeOutSecond -= (timeSliceMs / 1000);
			readyState = (String) new WebDriverWait(driver, 1000)
					.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState"));
		} while(timeOutSecond > 0 && !StringUtils.equalsIgnoreCase(readyState, state.toString()));

		return readyState.equals(state.name().toLowerCase());
	}

	public static boolean syncBrowser() {
		try {
			WebDriver driver = DriverFactory.getInstance().getWebDriver();
			long previous = 0;
			long current = 0;

			long timeSliceMs = Long.parseLong(EnviromentProperties.TIME_SLICE_MS);
			int timeOutSecond = Integer.parseInt(EnviromentProperties.TIME_OUT_SECONDS);
			String script = "return document.getElementsByTagName('*').length";
			isBrowserReady(BROWSER_READY_STATE.COMPLETE);
			do {
				previous = current;
				theadSleep(timeSliceMs);
				timeOutSecond -= (timeSliceMs / 1000);
				current = (long) SeleniumWrapper.executeJavaScript(driver, script);
			} while (current > previous && timeOutSecond > 0);
			DriverFactory.getJavaScriptErrors();
			return true;
		} catch (Exception e) {
			log.error(
					"Sync Browser Failed to sync the browser. Either the browseer has been closed or is unreachable. Session information: "
					);
		}
		return false;
	}	

}

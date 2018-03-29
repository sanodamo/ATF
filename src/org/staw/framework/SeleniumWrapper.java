package org.staw.framework;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.staw.framework.constants.EnviromentProperties;
import org.staw.framework.constants.GlobalConstants;
import org.staw.framework.constants.GlobalConstants.UtilitiesConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class SeleniumWrapper {
	public enum ELEMENT_ACTIONS {CLICK,FILL,COMPAREATTRIBUTE,COMPARETEXT};
	private static Logger log = Logger.getLogger(SeleniumWrapper.class.getName());
	private static boolean elemFound;
	private enum BROWSER_READY_STATE{
		LOADING, INTERACTIVE, COMPLETE;
	}

	public static boolean switchFrame(String frame, By locator, SoftAssertion myAssert) {

		try {
			WebDriver driver = SeleniumDriver.getInstance().getWebDriver();
			driver.switchTo().defaultContent();
			if(!frame.equalsIgnoreCase("default") && locator != null) {					
				driver.switchTo().frame(driver.findElement(locator));
			}
			return myAssert.Success("switched to the frame " + frame);
		}
		catch(Exception e) {
			return myAssert.Failed("Unable to switch the frame " + frame);
		}
	}

	public static boolean clickOnElement(WebElement webElement, By type, String Message, SoftAssertion myAssert) {
		try {
			SeleniumDriver.fluentWaitFindElement(webElement, type, 20).click();
		} catch (Exception e) {
			return myAssert.Failed("Unable to click '" + Message + "' Element, " + e.toString());
		}
		return myAssert.Success(Message + " Element clicked");
	}

	public static boolean clickOnElement(By type, SoftAssertion myAssert) {
		try {
			WebDriver driver = SeleniumDriver.getInstance().getWebDriver();
			SeleniumDriver.fluentWaitFindElement(driver,type, 20).click();
		} catch (Exception e) {
			return myAssert.Failed("Unable to click Element, " + e.toString());
		}
		return myAssert.Success(" Element clicked");
	}

	public static boolean clickOnLink(By type, String Message, SoftAssertion myAssert) {		
		WebDriver driver = SeleniumDriver.getInstance().getWebDriver();
		return clickOnLink(driver,type,Message,myAssert);		
	}

	public static boolean clickOnLink(WebDriver driver, By type, String Message, SoftAssertion myAssert) {
		try {
			SeleniumDriver.fluentWaitFindElement(driver, type, 20).click();
		} catch (Exception e) {
			return myAssert.Failed("Unable to click '" + Message + "' link, " + e.toString());
		}
		return myAssert.Success(Message + " link clicked");
	}

	public static boolean clickButtonInList(WebDriver driver, String classValue, String Message,
			SoftAssertion myAssert) {
		try {
			WebElement oList = driver.findElement(By.className(classValue));
			List<WebElement> tempList = oList.findElements(By.tagName("li"));
			WebElement input = tempList.get(2).findElement(By.tagName(UtilitiesConstants.INPUT));
			String buttonValue = input.getAttribute("value");
			if (buttonValue.equalsIgnoreCase("search")) {
				input.click();
			}
		} catch (Exception e) {
			return myAssert.Failed("Unable to click '" + Message + "' link, " + e.toString());
		}
		return myAssert.Success(Message + " link clicked");
	}

	public static boolean clickOnInputButton(WebDriver driver, String idValue, String Message, SoftAssertion myAssert) {
		return clickOnLink(driver, By.cssSelector("input[type='button'][id='" + idValue + "']"), Message, myAssert);
	}


	public static boolean clickOnInputButtonByValue(WebDriver driver, String buttonText, String message,
			SoftAssertion myAssert) {
		return clickOnLink(driver, By.cssSelector("input[type='button'][value='" + buttonText + "']"), message,
				myAssert);
	}

	public static boolean clickOnInputButtonByValue(WebElement webElement, String buttonText, String message,
			SoftAssertion myAssert) {
		return clickOnElement(webElement, By.cssSelector("input[type='button'][value='" + buttonText + "']"), message,
				myAssert);
	}


	public static boolean fillTextField(By element, String whatValue, String fieldName,	boolean isSubmit, SoftAssertion myAssert) {
		
		WebElement oField = getElement(element, myAssert);
		if(oField != null) {
			return fillTextField(oField, whatValue, fieldName, isSubmit, myAssert);
		}
		return myAssert.Failed("Unable to find " + fieldName + " field.");
	}

	public static boolean fillTextFieldAndTabOut(By element, String whatValue, String fieldName, SoftAssertion myAssert) {		
		return fillTextField(element, whatValue, fieldName, Keys.TAB , myAssert);
	}

	public static boolean fillTextField(By element, String whatValue, String fieldName,Keys key, SoftAssertion myAssert) {
		WebElement oField = getElement(element, myAssert);
		if(oField != null) {
			fillTextField(oField, whatValue, fieldName, false, myAssert);
			syncBrowser();
			oField.sendKeys(key);
			return myAssert.Success("Successfully entered " + whatValue + " in field " + fieldName);
		}
		return myAssert.Failed("Unable to find " + fieldName + " field.");
	}


	public static boolean fillTextField(WebElement parentElement, By childElement, String whatValue, String fieldName,
			boolean isSubmit, SoftAssertion myAssert) {
		WebElement oField = getElement(parentElement, childElement, myAssert);
		if(oField != null) {
			return fillTextField(oField, whatValue, fieldName, isSubmit, myAssert);
		}
		return myAssert.Failed("Unable to find " + fieldName + " field.");
	}


	public static boolean fillTextField(WebElement webElement, String whatValue, String fieldName, boolean isSubmit,
			SoftAssertion myAssert) {
		try {
			webElement.clear();
			webElement.sendKeys(whatValue);
			if (isSubmit) {
				webElement.submit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return myAssert.Failed("Unable to find " + fieldName + " field.");
		}
		return myAssert.Success("Successfully entered " + whatValue + " in field " + fieldName);
	}


	public static boolean compareValuesIfEqual(String actual, String expected, SoftAssertion myAssert) {
		if (actual.toLowerCase().trim().equals(expected.toLowerCase().trim())) {
			return myAssert.Success("Successfully verified. Actual : " + actual + ", " + "Expected : " + expected);
		} else {
			return myAssert.Failed("Verification failed. Actual : " + actual + ", " + "Expected : " + expected);
		}
	}


	public static boolean compareValuesIfContains(String stringToContainValue, String containingValue,
			SoftAssertion myAssert) {
		if (stringToContainValue.toLowerCase().trim().contains(containingValue.toLowerCase().trim())) {
			return myAssert.Success("Successfully verified : " + stringToContainValue + ", CONTAINS :  " + containingValue);
		} else {
			return myAssert.Failed(
					"Verification failed. Actual : " + stringToContainValue + " " + "Expected : " + containingValue);
		}
	}


	public static String getTextByValue(By method) {
		WebDriver driver = SeleniumDriver.getInstance().getWebDriver();
		try {
			return SeleniumDriver.fluentWaitFindElement(driver, method, 20).getAttribute(UtilitiesConstants.VALUE);
		} catch (Exception e) {
			return GlobalConstants.ContextConstant.STRING_EMPTY;
		}
	}

	public static boolean clickOnElementByIndex(int index, By locator, String message, SoftAssertion myAssert) {
		WebDriver driver = SeleniumDriver.getInstance().getWebDriver();
		try {
			List<WebElement> buttons = SeleniumWrapper.getElements(locator, myAssert);			
			SeleniumWrapper.javaScriptExecutorClickElement(driver, myAssert,UtilitiesConstants.JAVASCRIPT_CLICK_ELEMENT, buttons.get(index));
			return myAssert.Success("Successfully clicked " + message + " element");			

		} catch (Exception e) {
			return myAssert.Failed("Unable to find " + message + " element");
		}		
	}	

	public static boolean clickOnListElement(String element, By locator, SoftAssertion myAssert) {
		WebDriver driver = SeleniumDriver.getInstance().getWebDriver();
		try {
			List<WebElement> buttons = SeleniumWrapper.getElements(locator, myAssert);
			for (WebElement button : buttons) {
				if (button.getText().equalsIgnoreCase(element.trim()) && button.isEnabled()) {
					SeleniumWrapper.javaScriptExecutorClickElement(driver, myAssert,UtilitiesConstants.JAVASCRIPT_CLICK_ELEMENT, button);
					return myAssert.Success("Successfully clicked " + element + " element");
				}
			}
		} catch (Exception e) {
			return myAssert.Failed("Unable to find " + element + " element");
		}
		return myAssert.Failed("Unable to find element " + element);
	}	


	public static boolean performActionOnElementInList(List<WebElement> listElement, String attributeName,
			String attributeValue, ELEMENT_ACTIONS action, SoftAssertion myAssert, String strValue1, String strValue2) {
		WebDriver driver = SeleniumDriver.getInstance().getWebDriver();
		WebElement elem = getElementFromAList(listElement, attributeName, attributeValue, myAssert);
		if (elem != null) {
			switch (action) {
			case CLICK:
				return javaScriptExecutorClickElement(driver, myAssert, UtilitiesConstants.JAVASCRIPT_CLICK_ELEMENT,elem);
			case FILL:
				return fillTextField(elem, strValue1, strValue1, false, myAssert);
			case COMPAREATTRIBUTE:
				return compareValuesIfEqual(elem.getAttribute(strValue2), strValue1, myAssert);
			case COMPARETEXT:
				return compareValuesIfEqual(elem.getText(), strValue1, myAssert);
			default:
				return myAssert.Failed("Action is not defined");
			}
		}
		return myAssert.Failed("Action is not defined");
	}


	public static WebElement getElementFromAList(List<WebElement> listElement, String attributeName,
			String attributeValue, SoftAssertion myAssert) {
		for (WebElement cElement : listElement) {
			if (cElement.getAttribute(attributeName).contains(attributeValue)) {
				return cElement;
			}
		}
		return null;
	}

	public static boolean selectByValue(By elementType, String whatValue, String fieldName, SoftAssertion myAssert) {
		WebDriver driver = SeleniumDriver.getInstance().getWebDriver();
		return selectByAnyMethod(driver,elementType,whatValue,"text", fieldName, myAssert);
	}

	public static boolean selectByIndex(By elementType, String whatValue, String fieldName, SoftAssertion myAssert) {
		WebDriver driver = SeleniumDriver.getInstance().getWebDriver();
		return selectByAnyMethod(driver,elementType,whatValue,"index", fieldName, myAssert);
	}

	public static boolean selectByAnyMethod(WebDriver driver, By elementType, String whatValue, String selectByType,
			String fieldName, SoftAssertion myAssert) {
		Select oSelect;

		try {
			oSelect = new Select(SeleniumDriver.fluentWaitFindElement(driver, elementType, 20));
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
				return myAssert.Failed("Please provide a valid 'SelectByType'");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return myAssert.Failed("Unable to find " + fieldName + " select field.");
		}

		return myAssert.Success("Successfully selected " + whatValue + " from select field " + fieldName);
	}


	public static boolean clickOnInputSubmitByValue(WebDriver driver, String submitValue, String fieldName,
			SoftAssertion myAssert) {
		return clickOnLink(driver, By.cssSelector("input[type='submit'][value='" + submitValue + "']"), fieldName, myAssert);
	}


	public static boolean javaScriptExecutorClickElement(WebDriver driver, SoftAssertion myAssert, String script, Object... args){
		return executeJavaScript(driver, myAssert,script, args);
	}



	public static boolean executeJavaScript(WebDriver driver,SoftAssertion myAssert, String script, Object... args){
		try{
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript(script, args);
			return myAssert.Success("Web Element clicked successfully using JavaScript Executor Command");
		}catch(Exception e){
			return myAssert.Failed("Failed to click WebElement using JavaScript Executor Command");
		}
	}


	public static Object executeJavaScript(WebDriver driver, String script){
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		return executor.executeScript(script);
	}


	public static Object javaScriptExecutorGetElementValue(WebElement element){
		WebDriver driver = SeleniumDriver.getInstance().getWebDriver();
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		return executor.executeScript(UtilitiesConstants.JAVASCRIPT_GET_ELEMENT_VALUE, element);
	}


	public static boolean javaScriptExecutorScrollToEndOfPage(){
		WebDriver driver = SeleniumDriver.getInstance().getWebDriver();
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript(UtilitiesConstants.JAVASCRIPT_SCROLL_TO_END_OF_PAGE);
		return true;
	}


	public static boolean doesElementExistOnPage(By elementType, SoftAssertion myAssert){
		WebDriver driver = SeleniumDriver.getInstance().getWebDriver();
		try {
			WebElement elementToFind = SeleniumDriver.fluentWaitFindElement(driver, elementType, 30);
			if(elementToFind != null){
				return myAssert.Success("Element " + elementType.toString() + " is present on page");
			}
		} catch (Exception e){
			return myAssert.Failed("Error finding element " + elementType.toString() + " on page");
		}
		return myAssert.Failed("Element " + elementType.toString() + " is not present on page");
	}


	public static boolean doesElementExistOnPageUnderParentElement(WebElement parentElement, By childElementType, SoftAssertion myAssert){
		try {
			WebElement elementToFind = SeleniumDriver.fluentWaitFindElement(parentElement, childElementType, 30);    		 
			if(elementToFind!=null){
				return myAssert.Success("Element " + childElementType.toString() + " is present on page");
			}
		} catch (Exception e){
			return myAssert.Failed("Error finding element " + childElementType.toString() + " on page");
		}
		return myAssert.Failed("Element " + childElementType.toString() + " is not present on page");
	}


	public static WebElement getElement(By element, SoftAssertion myAssert){
		WebDriver driver = SeleniumDriver.getInstance().getWebDriver();
		WebElement elementToFind = null;
		if(doesElementExistOnPage(element, myAssert))
			elementToFind = SeleniumDriver.fluentWaitFindElement(driver, element, 30);
		return elementToFind;
	}



	public static List<WebElement> getElements(By element, SoftAssertion myAssert){
		WebDriver driver = SeleniumDriver.getInstance().getWebDriver();
		List<WebElement> elementsToFind = new ArrayList<>();
		if(doesElementExistOnPage(element, myAssert))
			elementsToFind = SeleniumDriver.fluentWaitFindElements(driver, element, 20);
		return elementsToFind;
	}


	public static WebElement getElement(WebElement parentElement, By childElement, SoftAssertion myAssert){
		WebElement elementToFind = null;
		if(doesElementExistOnPageUnderParentElement(parentElement, childElement, myAssert))
			elementToFind = SeleniumDriver.fluentWaitFindElement(parentElement, childElement, 20);
		return elementToFind;
	}


	public static List<WebElement> getElements(WebElement parentElement, By childElement, SoftAssertion myAssert){
		List<WebElement> elementsToFind = null;
		if(doesElementExistOnPageUnderParentElement(parentElement, childElement, myAssert))
			elementsToFind = SeleniumDriver.fluentWaitFindElements(parentElement, childElement, 20);
		return elementsToFind;
	}

	public static boolean compareTwoFloats(String str1, String str2, SoftAssertion myAssert) {
		BigDecimal d = new BigDecimal(str1);
		if (d.compareTo(new BigDecimal(str2)) == 0) {
			return myAssert.Success("Values Matched: Float 1 : " + str1 + " and Float 2 : " + str2);
		} else {
			return myAssert.Failed("Values did not match : Float 1 : " + str1 + " and Float 2 : " + str2);
		}
	}


	public static WebElement returnChildElement(WebElement parent, By grandchild, SoftAssertion myAssert) {
		elemFound = false;
		WebElement child = null;
		// Get All Child WebElements
		List<WebElement> children = SeleniumDriver.fluentWaitFindElements(parent, By.xpath(".//*"), 30);
		if (children != null) {
			for (WebElement element : children) {
				child = SeleniumWrapper.returnChildElement(element, grandchild, myAssert);
				if (child != null) {
					elemFound = true;
					break;
				} else {
					child = returnChildElement(element, grandchild, myAssert);
					if (elemFound) {
						break;
					}
				}
			}
		}
		return child;
	}


	public static boolean validateElementMessageContainsTextFromList(String elementText,
			List<String> messagesToValidateAgainst, SoftAssertion myAssert) {

		for (String strMessage : messagesToValidateAgainst) {
			if (StringUtils.containsIgnoreCase(elementText, strMessage)) {
				return myAssert
						.Success("String: " + elementText + " contains text from list of strings provided");
			}
		}
		return myAssert
				.Failed("String: " + elementText + " does NOT contain text from list of strings provided.");

	}


	public static boolean isElementEnabled(By elementType, SoftAssertion myAssert) {
		if (doesElementExistOnPage(elementType, myAssert)) {
			WebElement currElement = getElement(elementType, myAssert);
			if (currElement == null) {
				return myAssert.Failed("Unable to find element " + elementType + " on the page");
			} else {
				return currElement.isEnabled();
			}
		} else {
			return myAssert.Failed("Element " + elementType + " does not exist on page");
		}
	}


	public static boolean isElementEnabled(WebElement parentElement, By elementType, SoftAssertion myAssert) {
		if (doesElementExistOnPageUnderParentElement(parentElement, elementType, myAssert)) {
			WebElement currElement = getElement(parentElement, elementType, myAssert);
			if (currElement == null) {
				return myAssert.Failed("Unable to find element " + elementType + " on the page");
			} else {
				return currElement.isEnabled();
			}
		} else {
			return myAssert.Failed("Element " + elementType + " does not exist on page");
		}
	}


	public static boolean isElementDisplayed(By elementType, SoftAssertion myAssert) {
		if (doesElementExistOnPage(elementType, myAssert)) {
			WebElement currElement = getElement(elementType, myAssert);
			if (currElement == null) {
				return myAssert.Failed("Unable to find element " + elementType + " displayed on the page");
			}
			if (currElement.isDisplayed()) {
				return myAssert.Success("Able to find element " + elementType + " displayed on the page");
			}
		}
		return myAssert.Failed("Element " + elementType + " does not exist on page");
	}



	public static boolean isElementDisplayed(WebElement parentElement, By elementType, SoftAssertion myAssert) {
		if (doesElementExistOnPageUnderParentElement(parentElement, elementType, myAssert)) {
			WebElement currElement = getElement(parentElement, elementType, myAssert);
			if (currElement == null) {
				return myAssert.Failed("Unable to find element " + elementType + " displayed on the page");
			}
			if (currElement.isDisplayed()) {
				return myAssert.Success("Able to find element " + elementType + " displayed on the page");
			}
		}
		return myAssert.Failed("Element " + elementType + " does not exist on page");
	}


	public static boolean isElementSelected(By elementType, SoftAssertion myAssert) {
		if (doesElementExistOnPage(elementType, myAssert)) {
			WebElement currElement = getElement(elementType, myAssert);
			if (currElement == null) {
				return myAssert.Failed("Unable to find element " + elementType + " selected on the page");
			}
			if (currElement.isSelected()) {
				return myAssert.Success("Able to find element " + elementType + " selected on the page");
			}
		}
		return myAssert.Failed("Element " + elementType + " does not exist on page");
	}




	public static boolean isElementSelected(WebElement parentElement, By elementType, SoftAssertion myAssert) {
		if (doesElementExistOnPageUnderParentElement(parentElement, elementType, myAssert)) {
			WebElement currElement = getElement(parentElement, elementType, myAssert);
			if (currElement == null) {
				return myAssert.Failed("Unable to find element " + elementType + " selected on the page");
			}
			if (currElement.isSelected()) {
				return myAssert.Success("Able to find element " + elementType + " selected on the page");
			}
		}
		return myAssert.Failed("Element " + elementType + " does not exist on page");
	}


	public static boolean doesTextValueExisInXmlDocument(Node node, String valueToFind) {
		elemFound = false;
		if (node.hasChildNodes()) {
			NodeList childrens = node.getChildNodes();
			for (int i = 0; i < childrens.getLength(); i++) {
				elemFound = doesTextValueExisInXmlDocument(childrens.item(i), valueToFind);
				if (elemFound)
					break;
			}
		} else {
			if (node.getNodeType() == Node.TEXT_NODE) {
				String nodeValue = node.getNodeValue();
				if (nodeValue != null) {
					if (nodeValue.trim().equals(valueToFind)) {
						elemFound = true;
					}
				}
			}
		}
		return elemFound;
	}


	public static boolean isCollectionNullOrEmpty(final Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}



	public static String removeSpecialCharacters(String toBeCleaned) {
		String cleanedString = StringUtils.replacePattern(toBeCleaned, "[^A-Za-z0-9 ]", "");
		return cleanedString;
	}


	public static String getAttributeValueByIndex(WebElement parentElement, By Element, String valueIndex,
			String attributeValue, SoftAssertion myAssert) {
		try {
			List<WebElement> oRows = SeleniumWrapper.getElements(parentElement, Element, myAssert);
			return oRows.get(Integer.parseInt(valueIndex)).getAttribute(attributeValue);
		} catch (Exception e) {
			return "";
		}
	}


	public static String getElementAttributeValue(By elementType, String attribute, SoftAssertion myAssert) {
		WebElement eleToFind = getElement(elementType, myAssert);
		if (eleToFind != null) {
			return eleToFind.getAttribute(attribute);
		}
		return GlobalConstants.ContextConstant.STRING_EMPTY;
	}


	public static String getElementAttributeValue(WebElement oParent, By elementType, String attribute,
			SoftAssertion myAssert) {
		WebElement eleToFind = getElement(oParent, elementType, myAssert);
		if (eleToFind != null) {
			return eleToFind.getAttribute(attribute);
		}
		return GlobalConstants.ContextConstant.STRING_EMPTY;
	}


	public static String getElementText(By elementType, SoftAssertion myAssert) {
		WebElement eleToFind = getElement(elementType, myAssert);
		if (eleToFind != null) {
			return eleToFind.getText();
		}
		return GlobalConstants.ContextConstant.STRING_EMPTY;
	}


	public static String getElementText(WebElement oParent, By elementType, SoftAssertion myAssert) {
		WebElement eleToFind = getElement(oParent, elementType, myAssert);
		if (eleToFind != null) {
			return eleToFind.getText();
		}
		return GlobalConstants.ContextConstant.STRING_EMPTY;
	}


	public static boolean clickOnCoordinates(WebElement htmlObject, int xOffset, int yOffset, SoftAssertion myAssert) {
		WebDriver driver = SeleniumDriver.getInstance().getWebDriver();
		try {
			Actions builder = new Actions(driver);
			Actions hoverOverRegistrar = builder.moveToElement(htmlObject);
			hoverOverRegistrar.perform();
			builder.moveToElement(htmlObject, xOffset, yOffset).click().build().perform();
			return myAssert.Success("Successfully clicked on element");
		} catch (Exception e) {
			return myAssert.Failed("Not able to click element");
		}
	}


	public static boolean verifyContent(WebElement parent, By elementType, String expected, SoftAssertion myAssert) {
		String actualContent = SeleniumWrapper.getElementText(parent, elementType, myAssert);

		return compareValuesIfEqual(actualContent.replaceAll(UtilitiesConstants.REGEX_SPECIAL_CHARACTERS, ""), expected.replaceAll(UtilitiesConstants.REGEX_SPECIAL_CHARACTERS, ""), myAssert)
				|| compareValuesIfContains(actualContent.replaceAll(UtilitiesConstants.REGEX_SPECIAL_CHARACTERS, ""), expected.replaceAll(UtilitiesConstants.REGEX_SPECIAL_CHARACTERS, ""), myAssert);
	}





	private static void theadSleep(long time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {
			log.error("Unable to sleep, " + e.toString());
		}
	}

	private static boolean isBrowserReady(BROWSER_READY_STATE state) {
		WebDriver driver = SeleniumDriver.getInstance().getWebDriver();

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
			WebDriver driver = SeleniumDriver.getInstance().getWebDriver();
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
			SeleniumDriver.getJavaScriptErrors();
			return true;
		} catch (Exception e) {
			log.error(
					"Sync Browser Failed to sync the browser. Either the browseer has been closed or is unreachable. Session information: "
					);
		}
		return false;
	}	

}

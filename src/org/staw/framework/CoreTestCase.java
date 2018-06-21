package org.staw.framework;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class CoreTestCase {
	private int threadCount;
	private String envr;
	private TestNG allTest;
	private XmlSuite testSuite;
	private ArrayList<XmlSuite> suites;
	private String currTest;
	private String prevTest;
	private int count;
	private static Logger logger = Logger.getLogger(CoreTestCase.class.getName());
	
	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	public String getEnvr() {
		return envr;
	}

	public void setEnvr(String envr) {
		this.envr = envr;
	}

	public TestNG getAllTest() {
		return allTest;
	}

	public void setAllTest(TestNG allTest) {
		this.allTest = allTest;
	}

	public XmlSuite getTestSuite() {
		return testSuite;
	}

	public void setTestSuite(XmlSuite testSuite) {
		this.testSuite = testSuite;
	}

	public ArrayList<XmlSuite> getSuites() {
		return suites;
	}

	public void setSuites(ArrayList<XmlSuite> suites) {
		this.suites = suites;
	}

	public String getCurrTest() {
		return currTest;
	}

	public void setCurrTest(String currTest) {
		this.currTest = currTest;
	}

	public String getPrevTest() {
		return prevTest;
	}

	public void setPrevTest(String prevTest) {
		this.prevTest = prevTest;
	}


	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public void StartSuite(){
		setAllTest(new TestNG());
		setTestSuite(new XmlSuite());
		getAllTest().setJUnit(false);
		getAllTest().setUseDefaultListeners(false);
	}
	
	
	public void runTest(String tcName, String uniqueTestName, String env, String className, HashMap<String, String> testParameters){
		ArrayList<XmlClass> classes = new ArrayList<XmlClass>();
		XmlTest test = new XmlTest(getTestSuite());
		setCurrTest(tcName);
		test.setName(uniqueTestName);		
		test.setParameters(testParameters);	
		classes.add(new XmlClass(className));
		test.setXmlClasses(classes);		
	}
	
	
	public void EndSuite() {
		try {					
			getTestSuite().setName("Regtression Tests");			
			setSuites(new ArrayList<XmlSuite>());
			getSuites().add(getTestSuite());
			getAllTest().setXmlSuites(getSuites());			
			getAllTest().run();
		} catch (Exception e) {
			logger.error("Filed to run suite " + e.getMessage());
			e.printStackTrace();
		}
	}
	
		
}

package org.staw.testing.imasis.onc;

import org.staw.framework.SoftAssertion;


public class Physician {
	
	private static SoftAssertion myAssert;
	
	
	public Physician(SoftAssertion myAssert) {
		this.myAssert = myAssert;
		
	}
	
	public boolean physicianPageAction(String action, String val) {
		return true;
	}
}

package org.staw.testing.imasis.onc;

import org.staw.framework.SoftAssertion;
import org.staw.framework.helpers.Utilities;

public class Physician {
	
	private static SoftAssertion myAssert;
	private Utilities util;
	
	public Physician(SoftAssertion myAssert) {
		this.myAssert = myAssert;
		util = new Utilities(myAssert);
	}
	
	public boolean physicianPageAction(String action, String val) {
		return true;
	}
}

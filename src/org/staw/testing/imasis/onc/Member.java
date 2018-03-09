package org.staw.testing.imasis.onc;

import org.staw.framework.SoftAssertion;
import org.staw.framework.helpers.Utilities;

public class Member {
	
	private static SoftAssertion myAssert;
	private Utilities util;
	
	public Member(SoftAssertion myAssert) {
		this.myAssert = myAssert;
		util = new Utilities(myAssert);
	}
	
	public boolean memberPageAction(String action, String val) {
		return true;
	}
}

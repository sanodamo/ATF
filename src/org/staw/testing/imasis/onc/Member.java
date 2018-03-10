package org.staw.testing.imasis.onc;

import org.staw.framework.SoftAssertion;


public class Member {
	
	private static SoftAssertion myAssert;
	
	
	public Member(SoftAssertion myAssert) {
		this.myAssert = myAssert;
	
	}
	
	public boolean memberPageAction(String action, String val) {
		return true;
	}
}

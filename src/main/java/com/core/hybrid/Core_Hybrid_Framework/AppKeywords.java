package com.core.hybrid.Core_Hybrid_Framework;

import java.util.Hashtable;

import com.core.hybrid.Core_Hybrid_Framework.util.Constants;
import com.relevantcodes.extentreports.ExtentTest;

public class AppKeywords extends GenericKeywords {

	AppKeywords(ExtentTest test) {
		super(test);
		
	}

	public String verifyLoginDetails(Hashtable<String,String> testData) {
		
		String expectedName = testData.get("Name");
		
		// actual
		
		return Constants.PASS;
		
	}

}

package com.core.hybrid.Core_Hybrid_Framework;

import static org.testng.Assert.fail;

import java.util.Hashtable;

import com.core.hybrid.Core_Hybrid_Framework.util.Constants;
import com.core.hybrid.Core_Hybrid_Framework.util.Xls_Reader;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import junit.framework.Assert;

public class Keywords {
	
	ExtentTest test;
	AppKeywords app;
	
	public Keywords(ExtentTest test) {
		this.test = test;
	}

	public void executeKeywords(String testUnderExecution ,Xls_Reader xls,Hashtable<String,String> testData) {
		
	
		app = new AppKeywords(test);
		
		int rows = xls.getRowCount("Keywords");
		//app.reportFailure("xxx");
		
		for(int rNum=2;rNum<=rows;rNum++) {
			String tcid = xls.getCellData(Constants.KEYWORDS_SHEET,Constants.TCID_COL, rNum);
			if(tcid.equals(testUnderExecution)) {
			String keyword = xls.getCellData(Constants.KEYWORDS_SHEET,Constants.KEYWORD_COL, rNum);
			String object = xls.getCellData(Constants.KEYWORDS_SHEET,Constants.OBJECT_COL, rNum);
			String key = xls.getCellData(Constants.KEYWORDS_SHEET,Constants.DATA_COL, rNum);
			String data = testData.get(key) ;
			test.log(LogStatus.INFO, tcid+"-------"+keyword+"---------"+object+"--------"+data);
			String result="";
			
			if(keyword.equals("openBrowser"))
				result = app.openBrowser(data);
			else if(keyword.equals("navigate"))
				result = app.navigate(object);
			else if(keyword.equals("input"))
				result = app.input(object,data);
			else if(keyword.equals("click"))
				result = app.click(object);
			else if(keyword.equals("closeBrowser"))
				result = app.closeBrowser();
			else if(keyword.equals("verifyLoginDetails"))
				result = app.verifyLoginDetails(testData);
			else if(keyword.equals("verifyElementPresent"))
				result = app.verifyElementPresent(object);
			
			
			if(!result.equals(Constants.PASS)) {
				app.reportFailure(result);
				Assert.fail(result);
			}
				
			
			
			
			}
		}
		
		
	}
	
	
	public AppKeywords getGenericKeywords() {
		return app;
	}


}

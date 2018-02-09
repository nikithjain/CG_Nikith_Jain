package com.core.hybrid.Core_Hybrid_Framework.testcases;

import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.core.hybrid.Core_Hybrid_Framework.Keywords;
import com.core.hybrid.Core_Hybrid_Framework.util.Constants;
import com.core.hybrid.Core_Hybrid_Framework.util.DataUtil;
import com.core.hybrid.Core_Hybrid_Framework.util.ExtentManager;
import com.core.hybrid.Core_Hybrid_Framework.util.Xls_Reader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class FacebookLogin {
	
	ExtentReports rep = ExtentManager.getInstance();
	ExtentTest test;
	String testName = "FaceBookTest";
	Xls_Reader xls = new Xls_Reader(Constants.SUITEA_XLS);
	
	@Test(dataProvider="getData")
	public void doLogin(Hashtable<String,String> data) {
		
			
		test = rep.startTest(testName);
		test.log(LogStatus.INFO, data.toString());
		
		if(DataUtil.isSkip(xls, testName) || data.get("Runmode").equals("N")) {
			test.log(LogStatus.SKIP, "Skipping the test as Runmode is N");
			throw new SkipException("Skipping the test as Runmode is N");
		}
		test.log(LogStatus.INFO,"Starting Gmail login Test");
		
		
		Keywords app = new Keywords(test);
		test.log(LogStatus.INFO,"Executing Keywords");
		app.executeKeywords(testName,xls,data);
		//take screenshot
		app.getGenericKeywords().reportFailure("xxx"); 
		
		
	}
	
	@DataProvider
	public Object[][] getData() {
		
		return DataUtil.getData(xls, testName);
	}
	
	
	@AfterTest
	public void quit() {
		if(rep!=null) {
			rep.endTest(test);
			rep.flush();
		}
	}
 }

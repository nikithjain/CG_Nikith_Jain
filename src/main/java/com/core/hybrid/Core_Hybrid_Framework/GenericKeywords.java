package com.core.hybrid.Core_Hybrid_Framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;

import com.core.hybrid.Core_Hybrid_Framework.util.Constants;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class GenericKeywords {
	public WebDriver driver;
	Properties prop;
	ExtentTest test;
	
	
	GenericKeywords(ExtentTest test) {
		
		this.test=test;
		prop= new Properties();
		try {
			FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\project.properties");
			prop.load(fs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String openBrowser(String browserType) {
		test.log(LogStatus.INFO, "Opening browser");
		if(browserType.equals("Mozilla"))
			driver = new FirefoxDriver();
		else if(browserType.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver","C:\\Selenium_Automation\\LibraryFiles\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if(browserType.equals("ie")){
			System.setProperty("webdriver.ie.driver", "C:\\Selenium_Automation\\LibraryFiles\\IEDriverServer.exe");
			driver =  new InternetExplorerDriver();
		}
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		return Constants.PASS;
	}
		
	
	public String navigate(String urlKey) {
		test.log(LogStatus.INFO, "Navigating to "+prop.getProperty(urlKey));
		//System.out.println("Navigating to "+prop.getProperty(urlKey));
		driver.get(prop.getProperty(urlKey));
		return Constants.PASS;
		
	}
	
	public String click(String locatorKey) {
		test.log(LogStatus.INFO, "Clicking on "+prop.getProperty(locatorKey));
		//System.out.println("Clicking on "+prop.getProperty(locatorKey));
		WebElement e = getElement(locatorKey);
		e.click();
		return Constants.PASS;
		
	}
	
	public String input(String locatorKey,String data) {
		test.log(LogStatus.INFO,"Typing in "+prop.getProperty(locatorKey));
		WebElement e = getElement(locatorKey);
		e.sendKeys(data);
		return Constants.PASS;
	}
	
	
	
	public String closeBrowser() {
		test.log(LogStatus.INFO,"Closing browser");
//		driver.quit();
		return Constants.PASS;
	}
	
	/****************************Validation Functions***************************/
	
	public String verifyText(String locatorKey, String expectedText) {
		WebElement e = getElement(locatorKey);
		String actualtext = e.getText();
		if(actualtext.equals(expectedText))
			return Constants.PASS;
		else
			return Constants.FAIL;
		
	}
	
	
	
	public String verifyElementPresent(String locatorKey) {
		boolean result = isElementPresent(locatorKey);
		if(result)
			return Constants.PASS;
		else
			return Constants.FAIL+" Could not find element "+locatorKey;
		
	}
	
	public String verifyElementNotPresent(String locatorKey) {
		boolean result = isElementPresent(locatorKey);
		if(!result)
			return Constants.PASS;
		else
			return Constants.FAIL+" Could find element "+locatorKey;
		
	}
	
	/*********************Utility Functions****************************/
	
	
	public WebElement getElement(String locatorKey) {
		WebElement e = null;
		try {
			if(locatorKey.endsWith("_id"))
				e = driver.findElement(By.id(prop.getProperty(locatorKey)));
			else if(locatorKey.endsWith("_xpath"))
				e = driver.findElement(By.xpath(prop.getProperty(locatorKey)));
			else if(locatorKey.endsWith("_name"))
				e = driver.findElement(By.name(prop.getProperty(locatorKey)));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
			reportFailure("Failure is Element Extraction - "+locatorKey);
			Assert.fail("Failure is Element Extraction - "+locatorKey);
		}
		
		return e;
		
	}
	
	
	
	public boolean isElementPresent(String locatorKey) {
		List<WebElement> e= null;
		
		if(locatorKey.endsWith("_id"))
			e = driver.findElements(By.id(prop.getProperty(locatorKey)));
		else if(locatorKey.endsWith("_xpath"))
			e = driver.findElements(By.xpath(prop.getProperty(locatorKey)));
		else if(locatorKey.endsWith("_name"))
			e = driver.findElements(By.name(prop.getProperty(locatorKey)));
		if(e.size()==0)
			return false;
		else
			return true;
		
	}
	
	/****************************************************************************/
	
	public void reportFailure(String failureMessage) {
		takeScreenshot();
		test.log(LogStatus.FAIL,failureMessage);
	}
	public void takeScreenshot() {
		
		Date d = new Date();
		String screenshotFile=d.toString().replace(":", "_").replace(" ","_")+".png";
		String path=Constants.SCREENSHOT_PATH+screenshotFile;
		// take screenshot
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(srcFile, new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// embed
		test.log(LogStatus.INFO, test.addScreenCapture(path));
		
	}

}

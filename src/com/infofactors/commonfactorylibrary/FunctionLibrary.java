package com.infofactors.commonfactorylibrary;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.sun.jna.platform.unix.X11.XClientMessageEvent.Data;
import com.utilities.PropertyFileUtil;

public class FunctionLibrary 
{
	public static WebDriver startBrowser(WebDriver driver) throws FileNotFoundException, IOException 
	{
		if (PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("firefox")) 
		{
			driver=new FirefoxDriver();	
		}
		else
		if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("Chrome"))
		{
			driver=new ChromeDriver();
			
		}else 
		if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("IE"))
		{
			driver=new InternetExplorerDriver();
		}
		 return driver;	
	}
	
	   public static void openApplication(WebDriver driver) throws FileNotFoundException, IOException 
	   {
		   driver.manage().window().maximize();
		   driver.get(PropertyFileUtil.getValueForKey("URL"));
	   }
	   
	   public static void clickAction(WebDriver driver, String locatorType, String locatorValue) 
	   {
		   if (locatorType.equalsIgnoreCase("id"))
		   {
			   driver.findElement(By.id(locatorValue)).click();	
		}
		   else
			   if (locatorType.equalsIgnoreCase("name")) 
			   {
				   driver.findElement(By.name(locatorValue)).click();	
			}else
				if (locatorType.equalsIgnoreCase("xpath"))
				{
					driver.findElement(By.xpath(locatorValue)).click();
				}
	   }
	   
	   public static void typeAction(WebDriver driver, String locatorType, String locatorValue, String data) 
	   {
		   
		   if (locatorType.equalsIgnoreCase("id"))
		   {
			   driver.findElement(By.id(locatorValue)).clear();
			   driver.findElement(By.id(locatorValue)).sendKeys(data);
		}
		   else 
		   if(locatorType.equalsIgnoreCase("name"))
		   {
			   driver.findElement(By.name(locatorValue)).clear();
			   driver.findElement(By.name(locatorValue)).sendKeys(data);  
		   }else 
			   if(locatorType.equalsIgnoreCase("xpath"))
			   {
				   driver.findElement(By.xpath(locatorValue)).clear();
				   driver.findElement(By.xpath(locatorValue)).sendKeys(data);   
			   }
	   }
	   
	    public static void closeBrowser(WebDriver driver) 
	    {
	    	driver.close();
	    }
	    
	    public static void waitForElement(WebDriver driver, String locatorType, String locatorValue, String waitTime) 
	    {
	    	WebDriverWait wait=new WebDriverWait(driver, Integer.parseInt(waitTime));
	    	if(locatorType.equalsIgnoreCase("id")) 
	    	{
	    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
	    	}
	    	else 
	    		if(locatorType.equalsIgnoreCase("name")) 
	    		{
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorValue)));	
	    		}else
	    			if(locatorType.equalsIgnoreCase("xpath"))
	    			{
	    				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
	    			}
	    }
	    
	    public static void titleValidation(WebDriver driver, String validData) throws InterruptedException 
	    {
	    	Thread.sleep(4000);
	    	String act_title=driver.getTitle();
	    	Thread.sleep(4000);
	    	String exp_title=validData;
	    	
	    	Assert.assertEquals(act_title, exp_title);
	    }
	
	     public static String getDate() 
	     {
	    	 Date date=new Date();
	    	 SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-DD-SS");
	    	 return sdf.format(date);
	     }
	    

}

package com.infofactors.driverfactory;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.infofactors.commonfactorylibrary.FunctionLibrary;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.utilities.ExcelFileUtil;

public class DriverScript
{
	WebDriver driver;
	ExtentReports report;
	ExtentTest logger;
	
	public void startTest() throws Exception, InvalidFormatException, IOException
	{
		ExcelFileUtil excel=new ExcelFileUtil();
		for(int i=1; i<=excel.rowCount("MasterTestCases"); i++) 
		{
			String modulestatus="";
			if (excel.getData("MasterTestCases", i, 2).equalsIgnoreCase("Y"))
			{
				String TCModule=excel.getData("MasterTestcases", i, 1);
				report=new ExtentReports("./Reports//"+TCModule+".html "+ "_"+ FunctionLibrary.getDate());
				logger=report.startTest(TCModule);
				int rowcount=excel.rowCount(TCModule);
				
				for(int j=1; j<=rowcount;j++) 
				{
					
					String description=excel.getData(TCModule, j, 0);
					String object_Type=excel.getData(TCModule, j,1);
					String locator_type=excel.getData(TCModule, j, 2);
					String locator_value=excel.getData(TCModule, j, 3);
					String test_data =excel.getData(TCModule, j, 4);
					
					try 
					{
						if(object_Type.equalsIgnoreCase("startBrowser")) 
						{
							driver=FunctionLibrary.startBrowser(driver);
							logger.log(LogStatus.INFO,description);
						}
						if(object_Type.equalsIgnoreCase("openApplication")) 
						{
							FunctionLibrary.openApplication(driver);
							logger.log(LogStatus.INFO, description);
						}
						if(object_Type.equalsIgnoreCase("clickAction")) 
						{
							FunctionLibrary.clickAction(driver, locator_type, locator_value);
							logger.log(LogStatus.INFO, description);
							
						}
						
						if(object_Type.equalsIgnoreCase("typeAction")) 
						{
							FunctionLibrary.typeAction(driver, locator_type, locator_value, test_data);
							logger.log(LogStatus.INFO, description);
							
						}
						
						if(object_Type.equalsIgnoreCase("waitForElement")) 
						{
							FunctionLibrary.waitForElement(driver, locator_type, locator_value, test_data);
							logger.log(LogStatus.INFO, description);
							
							
						}
						
						if(object_Type.equalsIgnoreCase("closeBrowser")) 
						{
							FunctionLibrary.closeBrowser(driver);
							logger.log(LogStatus.INFO, description);	
						}
						if(object_Type.equalsIgnoreCase("titleValidation")) 
						{
							FunctionLibrary.titleValidation(driver, test_data);
							logger.log(LogStatus.INFO, description);	
						}	
						
						excel.setData(TCModule, j, 5,"Pass");
						modulestatus="true";
						logger.log(LogStatus.PASS, description+" Pass");
			}
					catch(Exception e) 
					{
						excel.setData(TCModule, j, 5, "Fail");
						modulestatus="false";
						logger.log(LogStatus.FAIL, description+ " Fail");
						File srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
						FileUtils.copyFile(srcFile,new File(".//ScreenShots//"+description+"_"+FunctionLibrary.getDate()+".jpg"));
						
						String image=logger.addScreenCapture(".//ScreenShots//"+description+"_"+FunctionLibrary.getDate()+".jpg");
						logger.log(LogStatus.FAIL, "TCModule", image);
						break;					
					}
					catch(AssertionError e) 
					{
						excel.setData(TCModule, j, 5, "Fail");
						modulestatus="False";
						logger.log(LogStatus.FAIL, description+ "Fail");
						
						File srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
						FileUtils.copyFile(srcFile,new File(".//ScreenShots//"+description+"_"+FunctionLibrary.getDate()+".jpg"));

						String image=logger.addScreenCapture(".//ScreenShots//"+description+"_"+FunctionLibrary.getDate()+".jpg");
						logger.log(LogStatus.FAIL, "TCModule", image);
						break;	
					}
				   }
				if(modulestatus.equalsIgnoreCase("true")) 
				{
					excel.setData("MasterTestCases",i, 3, "Pass");
					
				}else 
				{
					excel.setData("MasterTestCases", i, 3, "Fail");
				}
					}
			else 
			{
				excel.setData("MasterTestCases", i, 3, "NotExecuted");
			}
			
			report.endTest(logger);
			report.flush();
		}
	}
	
}

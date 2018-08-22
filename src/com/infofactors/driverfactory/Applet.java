package com.infofactors.driverfactory;

import org.testng.annotations.Test;

public class Applet
{
	@Test
	public void kickStart() 
	{
		DriverScript ds=new DriverScript();
		
		try 
		{
			ds.startTest();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
	}

}

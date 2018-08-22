package com.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileUtil 
{
	 public static String getValueForKey(String key) throws FileNotFoundException, IOException 
	 {
		 Properties configproperties=new Properties();
		 configproperties.load(new FileInputStream(""));
		 return configproperties.getProperty(key);
		 
		 
	 }

}

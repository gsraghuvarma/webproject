/**
 * 
 */
package com.lnt.demo;

import java.io.*;
import java.util.*;

/**
 * @author 20071387
 *
 */
public class PropertyReaderUtil {

	public static final String PROP_FILENAME = "properties//myapp.properties";
	public static Properties myAppProps;
	public static FileInputStream input;
	static {

		try {
			input = new FileInputStream(PROP_FILENAME);
			myAppProps = new Properties();
			myAppProps.load(input);
		} catch (IOException e) {
			System.out.println("Check if the property file is present"+e.getMessage());
		}
		finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
					 
				}
			}
	}
	}

	public static String getProperty(String propKey) {

		return myAppProps==null || myAppProps.getProperty(propKey) == null?propKey:myAppProps.getProperty(propKey);
	}

	/*public static void main(String arg[])
	{
		PropertyReaderUtil obj = new PropertyReaderUtil();
		System.out.println(obj.getProperty("String"));

	}*/
	
	public static Set<Object> getAllPropKey() {

		return myAppProps.keySet();
	}
	
}

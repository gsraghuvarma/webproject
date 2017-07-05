package com.lnt.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class DosCmd {

public static void main(String[] args) throws IOException, InterruptedException {
		File str = new File("D:\\NewWorkspace\\Final-ForHTML\\");
		String funcName = "actionPerformed";
		System.out.println("main Called");
        searchFileName(funcName, str);
          
	}

	public static String searchFileName(String funcName, File path){
		String fileName = null;
		System.out.println("searchFileName called");
		try 
        { 
            Process p=Runtime.getRuntime().exec("cmd /c findstr /s /C:" + funcName+"(" + " *.java", null, path); 
           // p.waitFor(); 
            BufferedReader reader=new BufferedReader(
                new InputStreamReader(p.getInputStream())
            ); 
            String line; 
            String[] arr;
            while((line = reader.readLine()) != null) 
            { 
            	System.out.println(line);
            	if(line.contains(":")) 
            	{
            		arr = line.split(":");
            		if(null != arr)
            		{
            			if(arr[1].contains("private") || arr[1].contains("public") || 
            					arr[1].contains("void") || arr[1].contains("protected") || arr[1].contains("static"))
            			{
            				fileName = arr[0].trim();
            			}
            		}
            	}
            } 
                       
			System.out.println("FileName - :" + fileName);
        }
        catch(IOException e1) {e1.printStackTrace();} 
       // catch(InterruptedException e2) {e2.printStackTrace();} 
		
		return fileName;
	}

}

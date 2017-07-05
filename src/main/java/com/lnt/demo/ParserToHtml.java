package com.lnt.demo;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.webfirmframework.wffweb.tag.html.Body;
import com.webfirmframework.wffweb.tag.html.Br;
import com.webfirmframework.wffweb.tag.html.H3;
import com.webfirmframework.wffweb.tag.html.Html;
import com.webfirmframework.wffweb.tag.html.P;
import com.webfirmframework.wffweb.tag.html.attribute.Accept;
import com.webfirmframework.wffweb.tag.html.attribute.Action;
import com.webfirmframework.wffweb.tag.html.attribute.Cols;
import com.webfirmframework.wffweb.tag.html.attribute.Href;
import com.webfirmframework.wffweb.tag.html.attribute.Name;
import com.webfirmframework.wffweb.tag.html.attribute.Rows;
import com.webfirmframework.wffweb.tag.html.attribute.Type;
import com.webfirmframework.wffweb.tag.html.attribute.Value;
import com.webfirmframework.wffweb.tag.html.attribute.event.mouse.OnClick;
import com.webfirmframework.wffweb.tag.html.attribute.global.ClassAttribute;
import com.webfirmframework.wffweb.tag.html.attribute.global.Id;
import com.webfirmframework.wffweb.tag.html.attribute.global.Style;
import com.webfirmframework.wffweb.tag.html.attributewff.CustomAttribute;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Button;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Form;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Input;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Option;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Select;
import com.webfirmframework.wffweb.tag.html.formsandinputs.TextArea;
import com.webfirmframework.wffweb.tag.html.html5.attribute.Multiple;
import com.webfirmframework.wffweb.tag.html.html5.stylesandsemantics.Dialog;
import com.webfirmframework.wffweb.tag.html.links.A;
import com.webfirmframework.wffweb.tag.html.metainfo.Head;
import com.webfirmframework.wffweb.tag.html.programming.Script;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Div;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.StyleTag;
import com.webfirmframework.wffweb.tag.html.tables.Table;
import com.webfirmframework.wffweb.tag.html.tables.Td;
import com.webfirmframework.wffweb.tag.html.tables.Tr;
import com.webfirmframework.wffweb.tag.htmlwff.NoTag;

public class ParserToHtml {


	static File fileForParse;
	static int count=0;
	static Map<String,Integer> compSize = new HashMap<>();
	static Map<String, Map<String,String>> CsvMap = new HashMap<String,Map<String,String>>();

	public static int parseFile(String path, String outputPath) throws IOException {

		Map<String,String> parserMap = new HashMap<String,String>(); 
		parserMap = MapObject.getCompMap(path);

		//		System.out.println("==== map from mapobject :"+ parserMap);

		int parsedCounter=0;
		Map<String,String> comp = new HashMap<String,String>();

		/* Action Listener*/
		ConcurrentHashMap<String, String> actionListenerMap = MapObject.getActionListener(path);
		for (Entry<String, String> entry : actionListenerMap.entrySet()) {
					System.out.println("ActionListener Key===="+entry.getKey()+"====================="+"ActionListenerValue"+entry.getValue());

		}

		/* New Html Code with Java */
		Map<String, String> map = MapObject.getCompMap(path);

		if (!map.isEmpty()) {

			List<String> listval = new LinkedList<String>();

			String[] labelfield1 = null;
			String[] labelFields = null;

			String[] buttonfield1 = null;
			String[] buttonFields = null;
			String fileName = null;
			Writer writer=null;
			int countComp=0;


			String[] componentName = null;

			System.out.println("======NM======" + map);

			for (Entry<String, String> entry : map.entrySet()) {
				if (entry.getKey().toString().equalsIgnoreCase("JButton") || entry.getKey().toString().equalsIgnoreCase("JTextField") || entry.getKey().toString().equalsIgnoreCase("JTextArea") ||
						entry.getKey().toString().equalsIgnoreCase("JPanel") || entry.getKey().toString().equalsIgnoreCase("JFrame") || entry.getKey().toString().equalsIgnoreCase("JCheckBox")
						|| entry.getKey().toString().equalsIgnoreCase("JComboBox") || entry.getKey().toString().equalsIgnoreCase("JDialog") || entry.getKey().toString().equalsIgnoreCase("JFileChooser")
						|| entry.getKey().toString().equalsIgnoreCase("JLabel")  || entry.getKey().toString().equalsIgnoreCase("JTable") || entry.getKey().toString().equalsIgnoreCase("JMenu") 
						|| entry.getKey().toString().equalsIgnoreCase("JMenuBar") || entry.getKey().toString().equalsIgnoreCase("JMenuItem") || entry.getKey().toString().equalsIgnoreCase("JOptionPane") 
						|| entry.getKey().toString().equalsIgnoreCase("JRadioButton") || entry.getKey().toString().equalsIgnoreCase("JPasswordField") || entry.getKey().toString().equalsIgnoreCase("JDialog") 
						|| entry.getKey().toString().equalsIgnoreCase("JSplitPane") || entry.getKey().toString().equalsIgnoreCase("JScrollBar") || entry.getKey().toString().equalsIgnoreCase("JProgressBar") ) {
					componentName=entry.getValue().toString().split("Variablename");

					Matcher m =Pattern.compile("\\(([^)]+)\\)").matcher(componentName[1]);
					if(m.find()){
						String value = m.group(1);
						String line1 = value.replace("\"", "");
						labelFields = line1.split(",");
						System.out.println("labelFields*************"+labelFields.length);
						countComp=countComp+labelFields.length;
						compSize.put(new File(path).getName(),countComp);
						for (int i = 0; i < labelFields.length; i++) {
							System.out.println("Inside Loop labelFields###"+labelFields[i]+"labelFields.length######"+labelFields.length);
							comp.put(labelFields[i],entry.getKey().toString());
						}
					}					
				}
				CsvMap.put(new File(path).getName(), comp);
			}			
		}

		for(Entry<String, Integer> entryMap : compSize.entrySet()){
			System.out.println("compSize key ::: "+entryMap.getKey()+" compSize Value ::: "+entryMap.getValue());  
		}
		
		
		Map<String,String> htmlMap = new LinkedHashMap<String,String>();

				for(Entry<String, String> entryMap : parserMap.entrySet()){
					//			System.out.println("key ::: "+entryMap.getKey());  
					String dynamicKey = entryMap.getKey();
					String parserKey="";
					if(dynamicKey.contains("==")){
						String[] splitDynamicKey = dynamicKey.split("\\==");
						parserKey=splitDynamicKey[1];
					}else{
						parserKey=dynamicKey;
					} 
					 
					if(parserKey.equalsIgnoreCase("JLabel") || parserKey.equalsIgnoreCase("JTextArea") 
							|| parserKey.equalsIgnoreCase("JCheckBox") || parserKey.equalsIgnoreCase("JRadioButton") 
							|| parserKey.contains("JComboBox")|| parserKey.equalsIgnoreCase("JPasswordField") 
							|| parserKey.equalsIgnoreCase("JButton") || parserKey.equalsIgnoreCase("JTextField")
							|| parserKey.equalsIgnoreCase("JTable") || parserKey.equalsIgnoreCase("JDialog")
	
							|| parserKey.equalsIgnoreCase("JOptionPane") //need javascript to run it so not taken in consideration
							|| parserKey.equalsIgnoreCase("JScrollBar") || parserKey.equalsIgnoreCase("JSplitPane") 
							|| parserKey.equalsIgnoreCase("JFileChooser")
							|| parserKey.equalsIgnoreCase("JList") // Have to check more on this..???
							|| parserKey.equalsIgnoreCase("JProgressBar") || parserKey.equalsIgnoreCase("JMenu")//need javascript to run it so not taken in consideration
							|| parserKey.equalsIgnoreCase("JTabbedPane") || parserKey.equalsIgnoreCase("JToggleButton")//need javascript to run it so not taken in consideration
							|| parserKey.equalsIgnoreCase("JPanel")//Not checking for it  
							){
	
						String totalLabal = entryMap.getValue(); 
						String[] eachLabel = totalLabal.split("\\$");
						for(int i=0;i<eachLabel.length;i++){
							Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(eachLabel[i]); 
							if(eachLabel[i].contains("Variablevalue")){
								if(m.find()) {
									String labelVal = m.group(1);
									String line = labelVal.replace("\"", "");
									if(line.contains(",")){ 
										String[] labelFieldsnew = line.split("\\,");  
	
										if(labelFieldsnew.length==0){
											htmlMap.put("NA-"+count,parserKey); 
										}
										for(int j=0;j<labelFieldsnew.length;j++){
											System.out.println("Each Label :::"+labelFieldsnew[j]); 
											htmlMap.put(labelFieldsnew[j]+"-"+count,parserKey);
										}
									}else{
										htmlMap.put(line+"-"+count,parserKey);
									}
								}else{ 
									htmlMap.put("NA-"+count,parserKey);
								}
							}
						}
					} 
					count++;
				}
				System.out.println("Html map ::: "+htmlMap); 
	
				/************************************** HTML Code ***************************************************/
	 
				HashMap<String, String> reactmapper = new HashMap<String,String>();
	
				reactmapper.put("JLabel", "<label for =\"@VariableValue\" "+">@VariableValue</label>");
				reactmapper.put("JTextArea", "<textarea rows =\"4\" cols =\"50\" "+">@VariableValue</textarea>");
				reactmapper.put("JCheckBox", "<input type =\"checkbox\" name=\"@VariableValue\" "+">@VariableValue");
				reactmapper.put("JRadioButton", "<input type =\"radio\" name=\"@VariableValue\" "+">@VariableValue");
				reactmapper.put("JComboBox", " <select name =\"@VariableValue\"><option value=\"@VariableValue\">@VariableValue</option></select>");
				reactmapper.put("JPasswordField", "<input type =\"password\" name=\"@VariableValue\" "+">");
				reactmapper.put("JButton", "<button type =\"@VariableValue\" onclick= this.@VariableValue()>@VariableValue</button>");
				reactmapper.put("JTextField", "<input type =\"text\" name=\"@VariableValue\">");
				reactmapper.put("JTable", "<table border =\"1\"><tr></tr></table>");
				reactmapper.put("JDialog", "<dialog id =\"@VariableValue\"></dialog >");//totally depends upon button
				reactmapper.put("JPanel", "<fieldset>"+"<legend>\"@VariableValue\"</legend>"+"Dynamic Data.!"+"</fieldset>");
				reactmapper.put("JFileChooser", "<input type =\"file\" name=\"@VariableValue\"  accept=\"image/*\">");
				reactmapper.put("JList", "<select name =\"@VariableValue\" multiple><option value=\"@VariableValue\">@VariableValue</option></select>");
				//Toggle have to check
				//Splipane have to check
				
		if(htmlMap.size()>0){	
			
				String filename = "";
				File dir = null;
				dir = new File(outputPath);
	
				if(!dir.exists()){
					dir.mkdir();
				}
	
				File fileToCreate = new File(path);
				filename = fileToCreate.getName();
	
				filename=filename.substring(0, filename.indexOf(".java"));
				File tagFile = new File(dir+"\\"+filename+".js");
	
				FileWriter fw =null;
				File fileReact  = new File("D:\\Neha-June\\webproject\\webproject\\src\\main\\java\\PlaceHolder.txt");
				String fileContent = "";
	
				if(fileReact.exists()){
					Scanner sc = new Scanner(fileReact).useDelimiter("\\Z");
					fileContent=sc.next();
				}
	
				StringBuffer bufferContent = new StringBuffer(); 
				
				fileContent = fileContent.replace("@FileNameMarker", filename);
				
				StringBuffer bufferContent2 = new StringBuffer();
	
				for(Entry<String,String> htmlMapEntry : htmlMap.entrySet()){
	
	
					if(reactmapper.containsKey(htmlMapEntry.getValue())){
						String reactLine = reactmapper.get(htmlMapEntry.getValue());
						String varReplacer = htmlMapEntry.getKey();
						String[] temlStr = varReplacer.split("-");
						varReplacer = temlStr.length>0?temlStr[0]:varReplacer;
						reactLine=reactLine.replace("@VariableValue", varReplacer); 
	
						if(reactLine.contains("@ArrayValue")){
							//array code
						}
	
						if(!(bufferContent2.toString().contains("@ArrayValue")))
							bufferContent2.append(reactLine+"\n");
					}
				}
	
				String fullStringBuffer = bufferContent2.toString();
	
				fileContent = fileContent.replace("@ReactDynamicCode", fullStringBuffer);
				System.out.println("File Content is :::: "+fileContent);
	
				fw = new FileWriter(tagFile);
				fw.flush();
				fw.write(fileContent);
				fw.close();



			/*File dir = new File(outputPath);
			if (!dir.exists()) {
				dir.mkdir();
				//				System.out.println("Folder Created...");
			}

			File fileToCreate = new File(path);
			String fileName = fileToCreate.getName();

			//			System.out.println("fileName  fileName"+fileName);
			String[] fileNameNew = fileName.split("\\."); 
			//			System.out.println("!!!!!!!!!!!!!!!!!!!!!!" +fileName);



			File tagFile = new File(dir, fileNameNew[0]+".html");
			//			System.out.println("fileNameNew[1] fileNameNew[1]"+fileNameNew[0]);

			BufferedWriter writer = null;


			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tagFile), "utf-8"));
			writer.write(html.toHtmlString());

			 *//***** ActionListener To JavaScript File Creation*****//*
			for (Entry<String, String> entry : actionListenerMap.entrySet()) {
				File tagFileJs = new File(dir, fileName + ".JS");
				Writer writerJs = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tagFileJs), "utf-8"));
				writerJs.write(html.toHtmlString());
			}
			writer.close();
			  *//***********/
				
		   getJString(path,outputPath,actionListenerMap);

			if(!map.isEmpty())
			{
				parsedCounter++;
			}
			System.out.println("\n ***********  DONE ***************");
		}

		
		return parsedCounter;
	}	



	/*for (Entry<String, HashMap<String, String>> entry : CsvMap.entrySet()) {
			System.out.println("Keys ::@@@@@@:::::"+entry.getKey()+"Value:::$$$$$$$$::::::"+entry.getValue());			
		}*/




	public static void getJString(String path, String outputPath, ConcurrentHashMap<String,String>actionListnerMap){
		File dir=new File(outputPath);
		if(!dir.exists()){
			dir.mkdir();
		}
		File fileToCreate=new File(path);
		String fileName=fileToCreate.getName();
		fileName=fileName.substring(0,fileName.indexOf(".java"));
		String jsFileContent="";
		/***** ActionListener To JavaScript File Creation*****/
		for (Entry<String, String> entry : actionListnerMap.entrySet()) {
			System.out.println(entry.getKey()+"====================="+entry.getValue());
			System.out.println("The Content from  Map ---- "+entry.getValue());
			String jsString=FindNReplaceJavaToJs.replacerUtil((String)entry.getValue());
			System.out.println("The Content from  Map replace---- "+jsString);
			jsString=FindNReplaceJavaToJs.replaceAnyConstOrMessageBox(jsString);
			System.out.println("The Content from  Map MessageBox---- "+jsString);
			jsString=FindNReplaceJavaToJs.formatterUtil(jsString);
			System.out.println("The Content from  Map formatter---- "+jsString);
			jsFileContent=jsFileContent+"\n\n"+entry.getKey()+" = () => {"+jsString+"};}";
		}
		System.out.println("The JsConent to be added is - "+jsFileContent);
		FileWriterUtil.writeJs(dir+"\\"+fileName+".js",jsFileContent);
		
	}

	public Map<String, Map<String, String>> getCompMap()
	{
		return CsvMap;
	}
	
	public Map<String,Integer> getComponntSize(){
		return compSize;
		
	}

}


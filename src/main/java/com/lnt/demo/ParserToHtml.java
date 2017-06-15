package com.lnt.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.webfirmframework.wffweb.tag.html.Body;
import com.webfirmframework.wffweb.tag.html.Html;
import com.webfirmframework.wffweb.tag.html.attribute.Type;
import com.webfirmframework.wffweb.tag.html.attribute.global.Style;
import com.webfirmframework.wffweb.tag.html.attributewff.CustomAttribute;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Button;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Input;
import com.webfirmframework.wffweb.tag.html.metainfo.Head;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Div;
import com.webfirmframework.wffweb.tag.html.tables.Table;
import com.webfirmframework.wffweb.tag.html.tables.Td;
import com.webfirmframework.wffweb.tag.html.tables.Tr;
import com.webfirmframework.wffweb.tag.htmlwff.NoTag;

public class ParserToHtml {

	public static void main(String[] args) throws FileNotFoundException, IOException {

	}
	static int counter=0;
	
	public static int parserFile(String path, String outputPath) throws IOException {
		boolean flag=false;
		
			try (BufferedReader br = new BufferedReader(new FileReader(path))) {
						System.out.println("inputFile Name::"+path);
						String sCurrentLine;
						
						
						while ((sCurrentLine = br.readLine()) != null) {
							sCurrentLine.trim();
							if (sCurrentLine.contains("javax.swing")) {
								counter++;
								flag=true;
								break;
							}
						}
						if(flag)
							{
						//		System.out.println("This file is a Swing File"+path+counter);
							parseFile(path,outputPath);
							}
					}
			return counter; 
	}

	public static void parseFile(String path, String outputPath) throws IOException {

		/* Action Listener*/
		HashMap<String, String> actionListenerMap=MapObject.getActionListener(path);
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

			System.out.println("============" + map);

			Map<String, String> htmlMap = new LinkedHashMap<String, String>();

			List<String> buttonListvalues666666 = new LinkedList<String>();

			for (Entry<String, String> entry : map.entrySet()) {

				if (entry.getKey().toString().equalsIgnoreCase("JLabel")) {
					labelfield1 = entry.getValue().toString().split("\\$");
					Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(labelfield1[1]);
					if (m.find()) {
						String labelVal12 = m.group(1);
						String line1 = labelVal12.replace("\"", "");
						labelFields = line1.split(",");
						listval.add(labelFields[0]);
						for (int i = 0; i < labelFields.length; i++) {
							htmlMap.put(labelFields[i], "JLabel");
						}
					}
				}

				if (entry.getKey().toString().equalsIgnoreCase("JButton")) {
					buttonfield1 = entry.getValue().toString().split("\\$");
					Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(buttonfield1[1]);
					if (m.find()) {
						String buttonVal12 = m.group(1);
						String line1 = buttonVal12.replace("\"", "");
						buttonFields = line1.split(",");
						for (int i = 0; i < buttonFields.length; i++) {
							htmlMap.put(buttonFields[i], "JButton");
							buttonListvalues666666.add(buttonFields[i]);
						}
					}
				}

			}

			/********************************
			 * HTML CODE
			 ****************************************/

			Html html = new Html(null) {
				{
					new Head(this) {
						{
							Style style = new Style();
						}
					};

					String ss = "background-color: GhostWhite;";

					new Body(this, new Style(ss.toString())) {
						{
							String normalStr = "normal";
							String divStyle = "background-color: yellow;";

							String divStyle2 = "background-color: purple;";

							String tdStyleStr = "width: 42%;";
							String buttonStyleStr = "margin-top: 20px;margin-right: 48px;height: 30px;width: 132px;";
							String tablealign = "margin: 0px auto;";

							// For Getting Style for all label fields
							/*
							 * for (Entry<String, Component> en :
							 * mapSwingComponents.entrySet()) {
							 * 
							 * if(en.getKey().toString().equalsIgnoreCase(
							 * "JLabel")){
							 * 
							 * styleStr="font-style:"+normalStr+";font-size:"+en
							 * .getValue().getFontSize()+"px;color:"+en.getValue
							 * ().getForeground()+";";
							 * System.out.println("Final Style ::: "+styleStr);
							 * System.out.println("Div Str :: "+divStyle); } }
							 */

							new Div(this, new Style("padding-left: 200px; padding-top: 110px;margin-left: 500px;")) {
								{

									Table table = new Table(this, new CustomAttribute("border", "0px")) {
										{
											for (Entry<String, String> entry : htmlMap.entrySet()) {

												// for(int
												// i=0;i<labelFields.length;i++){


												if (entry.getValue().equalsIgnoreCase("JLabel".trim())) {

													System.out
													.println("FINAL Value----------->>>>> " + entry.getValue());
													System.out
													.println("Final Key -------------->>>>>>" + entry.getKey());

													if (entry.getValue().equalsIgnoreCase("JLabel") && entry.getKey().toString().equalsIgnoreCase(listval.get(0))) {

														Tr tr = new Tr(this) {
															Td td1 = new Td(this, new CustomAttribute("colspan", "2"),
																	new Style("padding-left: 107px;font-size:30px;")) {

																{
																	new NoTag(this, entry.getKey().trim());
																}
															};
														};
													} else {

														Tr tr = new Tr(this) {
															Td td1 = new Td(this, new Style(tdStyleStr.toString())) {
																{
																	new NoTag(this, entry.getKey().trim());
																}
															};
															Td td2 = new Td(this) {
																Input cellContent = new Input(this, new Type("text"));
															};
														};
													}

												}

												if (entry.getValue().equalsIgnoreCase("JButton".trim())) {
												}
											} 

											if (buttonListvalues666666.size() > 0) {
												Tr tr = new Tr(this, new Style(buttonStyleStr.toString())) {
													{
														Td td2 = new Td(this, new CustomAttribute("colspan", "2")) {
															{
																for (String buttonName : buttonListvalues666666) {
																	new Button(this, new Type("button"),
																			new Style(buttonStyleStr.toString())) {
																		{
																			new NoTag(this, buttonName.trim());
																		}
																	};

																}
															}
														};
													}
												};
											}

										}// table
									};// table

								}// div
							};// div
						}
					};
				}
			};

			html.setPrependDocType(true);
			System.out.println(html.toHtmlString());

			File dir = new File(outputPath);
			if (!dir.exists()) {
				dir.mkdir();
				System.out.println("Folder Created ;;;");
			}
			DateFormat format = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
			String timeStamp = format.format(new Date());
			String fileName = listval.get(0) + "_" + timeStamp;
			File tagFile = new File(dir, fileName + ".html");
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tagFile), "utf-8"));
			writer.write(html.toHtmlString());
			
			/***** ActionListener To JavaScript File Creation*****/
			for (Entry<String, String> entry : actionListenerMap.entrySet()) {
				System.out.println(entry.getKey()+"====================="+entry.getValue());
				File tagFileJs = new File(dir, fileName + ".JS");
				Writer writerJs = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tagFileJs), "utf-8"));
				writerJs.write(html.toHtmlString());
				
			} 
			/***********/
			
			writer.close();

			System.out.println("\n ***********  DONE ***************");

		}

	}
}


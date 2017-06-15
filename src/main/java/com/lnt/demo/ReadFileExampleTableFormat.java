package com.lnt.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.webfirmframework.wffweb.tag.html.Body;
import com.webfirmframework.wffweb.tag.html.Br;
import com.webfirmframework.wffweb.tag.html.Html;
import com.webfirmframework.wffweb.tag.html.attribute.ColSpan;
import com.webfirmframework.wffweb.tag.html.attribute.Type;
import com.webfirmframework.wffweb.tag.html.attribute.Value;
import com.webfirmframework.wffweb.tag.html.attribute.global.Id;
import com.webfirmframework.wffweb.tag.html.attribute.global.Style;
import com.webfirmframework.wffweb.tag.html.attributewff.CustomAttribute;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Button;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Input;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Label;
import com.webfirmframework.wffweb.tag.html.formsandinputs.TextArea;
import com.webfirmframework.wffweb.tag.html.metainfo.Head;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Div;
import com.webfirmframework.wffweb.tag.html.tables.Table;
import com.webfirmframework.wffweb.tag.html.tables.Td;
import com.webfirmframework.wffweb.tag.html.tables.Tr;
import com.webfirmframework.wffweb.tag.htmlwff.Blank;
import com.webfirmframework.wffweb.tag.htmlwff.NoTag;

public class ReadFileExampleTableFormat {

	// private static final String FILENAME =
	// "D:\\sarmistha\\Final\\Final\\src\\com\\test\\AddAccountant.java";
	static ArrayList<String> compList = new ArrayList<String>();
	static LinkedHashMap<String, String> compMap = new LinkedHashMap<String, String>();
	static LinkedHashMap<String, HashMap<String, String>> compObjMap = new LinkedHashMap<String, HashMap<String, String>>();
	static HashMap<String, String> strMap = new LinkedHashMap<String, String>();
	static HashMap<HashMap<String, String>, Component> compStrMap = new LinkedHashMap<HashMap<String, String>, Component>();
	static String labelVal1 = "";

	static HashMap<String, Component> mapSwingComponents = new LinkedHashMap<String, Component>();

	static String styleStr = "";

	static String labelFieldValue[];
	static boolean flag = false;
	static String eachFileLabelField = "";
	static String fileName = null;

	public static void main(String[] args) throws FileNotFoundException, IOException {

		parseFile("inputFile", "outputPath");
	}

	public static void parseFile(String inputFile, String outputPath) throws IOException {

		int count = 0;
		int filedCount = 0;
		String labelVal = "";

		// String labelVal2="10";
		// String s1 =".";

		String sCurrentLine = null;
		String subCompArray[] = null;
		String newCompArray1[] = null;
		String newCompArray[] = null;
		String newCompArray11[] = null;
		String newObj[] = null;
		Component comp = new Component();
		List<String> listval = new LinkedList<String>();

		List<String> buttonValueList = new LinkedList<String>();

		try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {

			while ((sCurrentLine = br.readLine()) != null) {
				sCurrentLine.trim();
				if (sCurrentLine.contains("javax.swing")) {
					count++;
					break;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		if (count != 0) {

			try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
				while ((sCurrentLine = br.readLine()) != null) {
					if (!sCurrentLine.isEmpty()) {
						sCurrentLine = sCurrentLine.replaceAll("^\\s+", "");
						sCurrentLine = sCurrentLine.replaceAll("\\s+$", "");

						subCompArray = sCurrentLine.split("\\(");

						if (subCompArray[0].trim().endsWith("JLabel") || subCompArray[0].trim().endsWith("JTextField")
								|| subCompArray[0].trim().endsWith("JPasswordField")
								|| subCompArray[0].trim().endsWith("JButton"))// ||
																				// subCompArray[0].trim().contains(labelVal1))
						{
							Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(sCurrentLine);
							if (m.find()) {
								labelVal = m.group(1);
								System.out.println("labelVal " + labelVal);
								eachFileLabelField = labelVal.trim().substring(labelVal.indexOf("\"") + 1,
										labelVal.length() - 1);
								listval.add(eachFileLabelField);
								labelVal = labelVal.replace("\"", "");
								labelVal = labelVal + "=" + filedCount;
								newCompArray = subCompArray[0].split("=");
								newCompArray11 = newCompArray[0].split(" ");
								labelVal1 = newCompArray11[1].trim();
								newCompArray1 = newCompArray[1].split("new");
								System.out.println("newCompArray1 :::: " + newCompArray1[1].trim());
								compMap.put(labelVal, newCompArray1[1].trim());
								strMap.put(labelVal1, newCompArray1[1].trim());
								filedCount++;
							} else {
								newCompArray = subCompArray[0].split("=");
								labelVal1 = newCompArray[0].trim();
								newCompArray1 = newCompArray[1].split("new");
								labelVal = "" + filedCount;
								compMap.put(labelVal, newCompArray1[1].trim());
								strMap.put(labelVal1, newCompArray1[1].trim());
								filedCount++;

							}

						}
					}
				}

				try (BufferedReader buffer2 = new BufferedReader(new FileReader(inputFile))) {

					while ((sCurrentLine = buffer2.readLine()) != null) {
						for (Entry<String, String> entry : strMap.entrySet()) {
							if (sCurrentLine.trim().contains(entry.getKey()) && sCurrentLine.trim().contains(".set")
									&& entry.getValue().equals("JLabel")) {
								Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(sCurrentLine);
								if (m.find()) {
									String labelVal1 = m.group(0);
									System.out.println("Each Label Value :: " + labelVal1);
									comp.setComponentType("JLabel");

									if (labelVal1.contains("new")) {
										String fontString = labelVal1.substring(labelVal1.indexOf(".") + 1,
												labelVal1.indexOf(")"));
										String[] fontSplit = fontString.split(",");
										String fontStyle = fontSplit[0];
										String fontSize = fontSplit[1];
										System.out.println("FontStyle->" + fontSplit[0] + "|fontSize->" + fontSplit[1]);
										comp.setFontStyle(fontStyle);
										comp.setFontSize(fontSize);
									} else if (labelVal1.contains("Color")) {
										String cssColor = labelVal1.substring(labelVal1.indexOf("_") + 1,
												labelVal1.indexOf(")"));
										System.out.println("CSS Color is :: " + cssColor.toLowerCase());
										comp.setForeground(cssColor.toLowerCase());
									}
									// compStrMap.put(strMap, comp);
									mapSwingComponents.put("JLabel", comp);
								}

							} else if (sCurrentLine.trim().contains(entry.getKey())
									&& sCurrentLine.trim().contains(".set") && entry.getValue().equals("JTextField")) {

								Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(sCurrentLine);
								if (m.find()) {
									String labelVal12 = m.group(0);
									labelVal12 = labelVal12.replace("\"", "");
									labelVal12 = labelVal12.replace("(", "");
									labelVal12 = labelVal12.replace(")", "");

									System.out.println("Each TextField Column Value :::" + labelVal12);
									comp.setComponentType("JTextField");

									if (!labelVal12.isEmpty()) {
										System.out.println("Text Column Size :: " + labelVal12);
										comp.setTextFieldColumn(labelVal12);
									}
									// compStrMap.put(strMap, comp); v
									mapSwingComponents.put("JTextField", comp);
								}
							} else if (sCurrentLine.trim().contains(entry.getKey())
									&& sCurrentLine.trim().contains(".set")
									&& entry.getValue().equals("JPasswordField")) {

								Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(sCurrentLine);
								if (m.find()) {
									String labelVal12 = m.group(0);
									labelVal12 = labelVal12.replace("\"", "");
									labelVal12 = labelVal12.replace("(", "");
									labelVal12 = labelVal12.replace(")", "");
									System.out.println("Each passwordField Column Value :::" + labelVal12);
									comp.setComponentType("JPasswordField");
									if (!labelVal12.isEmpty()) {
										System.out.println("Password Column Size :: " + labelVal12);
										comp.setPasswordFieldColumn(labelVal12);
									}
									// compStrMap.put(strMap, comp);
									mapSwingComponents.put("JPasswordField", comp);
								}
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		/*
		 * for (Entry<String, Component> entry : mapSwingComponents.entrySet())
		 * { if(entry.getKey().toString().equalsIgnoreCase("JLabel")){
		 * System.out.println("For JLabel -->>");
		 * System.out.println("FontStryle ::"+entry.getValue().getFontStyle()+
		 * " FontSize :: "+entry.getValue().getFontSize()+" Foreground ::"+entry
		 * .getValue().getForeground()); }else
		 * if(entry.getKey().toString().equalsIgnoreCase("JTextField")){
		 * System.out.println("For JTextField -->>");
		 * System.out.println("TextColumn size ::"+entry.getValue().
		 * getTextFieldColumn()); }else
		 * if(entry.getKey().toString().equalsIgnoreCase("JPasswordField")){
		 * System.out.println("For JPasswordField -->>");
		 * System.out.println("JPasswordFieldColumn size ::"+entry.getValue().
		 * getPasswordFieldColumn()); }
		 * 
		 * }
		 */

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
						for (Entry<String, Component> en : mapSwingComponents.entrySet()) {

							if (en.getKey().toString().equalsIgnoreCase("JLabel")) {

								styleStr = "font-style:" + normalStr + ";font-size:" + en.getValue().getFontSize()
										+ "px;color:" + en.getValue().getForeground() + ";";
								System.out.println("Final Style ::: " + styleStr);
								System.out.println("Div Str :: " + divStyle);
							}
						}
						new Div(this, new Style("padding-left: 200px; padding-top: 110px;margin-left: 500px;")) {
							{

								Table table = new Table(this, new CustomAttribute("border", "0px")) {
									{
										for (Entry<String, String> entry : compMap.entrySet()) {

											if (entry.getValue().equalsIgnoreCase("JLabel".trim())) {

												System.out.println("CompMap Value ::: " + entry.getValue());
												System.out.println("CompMap Key ::: " + entry.getKey());
												labelFieldValue = entry.getKey().split("=");
												if (entry.getValue().equalsIgnoreCase("JLabel") && labelFieldValue[0]
														.toString().equalsIgnoreCase(listval.get(0))) {

													System.out.println("listval.get(0)  :::--->>>> " + listval.get(0)
															+ " labelFieldValue[0] --" + labelFieldValue[0]);
													Tr tr = new Tr(this) {
														Td td1 = new Td(this, new CustomAttribute("colspan", "2"),
																new Style("padding-left: 107px;font-size:30px;")) {

															{
																new NoTag(this, labelFieldValue[0].trim());
															}
														};
													};
												} else {

													Tr tr = new Tr(this) {
														Td td1 = new Td(this, new Style(tdStyleStr.toString())) {
															{
																new NoTag(this, labelFieldValue[0].trim());
															}
														};
														Td td2 = new Td(this) {
															Input cellContent = new Input(this, new Type("text"));
														};
													};
												}

											}

											if (entry.getValue().equalsIgnoreCase("JButton".trim())) {
												String arr[] = entry.getKey().split("=");
												buttonValueList.add(arr[0]);
												System.out.println("===============" + buttonValueList);
											}
										} // for

										// System.out.println("Button Size :::::
										// "+buttonValueList.size());
										if (buttonValueList.size() > 0) {
											Tr tr = new Tr(this, new Style(buttonStyleStr.toString())) {
												{
													Td td2 = new Td(this, new CustomAttribute("colspan", "2")) {
														{
															for (String buttonName : buttonValueList) {
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
		if (!listval.isEmpty()) {
			fileName = listval.get(0) + "_" + timeStamp;
		}

		File tagFile = new File(dir, fileName + ".html");
		Writer writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tagFile), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.write(html.toHtmlString());
		writer.close();

		System.out.println("\n ***********  DONE ***************");

	}
}

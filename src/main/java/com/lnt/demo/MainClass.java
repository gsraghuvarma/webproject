package com.lnt.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import com.test.Component;
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

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.webfirmframework.wffweb.tag.html.Html;

public class MainClass {

	static String sourcePath;
	static String destinationPath;
	static String labelFieldValue[];
	static List<String> buttonListvalues = new LinkedList<String>();
	static List<String> buttonListvalues666666 = new LinkedList<String>();
	static Map<String, String> htmlMap = new LinkedHashMap<String, String>();
	static List<String> listval = new LinkedList<String>();

	public static void main(String[] args) {

		// Map<String, String> finalmap = getCompMap();
		/*
		 * for (Entry<String, String> str : finalmap.entrySet()) {
		 * System.out.println("Map Keys::" + str.getKey() + "==>>" +
		 * "Map Values::" + str.getValue()); }
		 */

		Map<String, String> fileMap = parseFile(sourcePath, destinationPath);

		for (Entry<String, String> str : fileMap.entrySet()) {
			System.out.println("Map Keys::" + str.getKey() + "==>>" + "Map Values::" + str.getValue());
		}
	}

	public static Map<String, String> getCompMap() {

		String path = "C:\\Users\\20126957\\Music\\Final\\src\\com\\test\\AddAccountant.java";
		File file = new File(path);

		char[] contents = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			StringBuffer sb = new StringBuffer();
			String line = "";
			while ((line = br.readLine()) != null) {
				// append the content and the lost new line.
				sb.append(line + "\n");
			}
			contents = new char[sb.length()];
			sb.getChars(0, sb.length() - 1, contents, 0);

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setResolveBindings(true);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);

		parser.setBindingsRecovery(true);

		Map options = JavaCore.getOptions();
		parser.setCompilerOptions(options);

		String unitName = "Sample.java";
		parser.setUnitName(unitName);

		String[] sources = { "C:\\Users\\20126957\\Music\\Final\\src\\com\\test" };
		String[] classpath = { "C:\\Program Files\\Java\\jdk1.8.0_121\\jre\\lib\\rt.jar" };

		parser.setEnvironment(classpath, sources, new String[] { "UTF-8" }, true);

		parser.setSource(contents);

		CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		if (cu.getAST().hasBindingsRecovery()) {
		}

		Multimap<String, String> variablemultimap = ArrayListMultimap.create();

		Multimap<String, String> valuemultimap = ArrayListMultimap.create();

		Multimap<String, String> propertymultimap = ArrayListMultimap.create();

		Map<String, String> finalMap = new HashMap<>();

		cu.accept(new ASTVisitor() {

			public boolean visit(VariableDeclarationFragment node) {

				IVariableBinding binding = node.resolveBinding();

				if (binding.getVariableDeclaration().getType().getName().startsWith("J")) {
					variablemultimap.put(binding.getVariableDeclaration().getType().getName(),
							binding.getVariableDeclaration().getName());
				}

				return true;
			}

			@Override
			public boolean visit(ClassInstanceCreation node) {

				String str = node.toString();

				String[] splitString = str.split("new\\s");

				if (splitString[1].startsWith("J")) {
					String str2 = splitString[1];

					String[] splitString2 = str2.split("\\(");

					int length = splitString2[1].length();

					valuemultimap.put(splitString2[0], splitString2[1].substring(0, length - 1));

				}

				return super.visit(node);
			}

			@Override
			public boolean visit(MethodInvocation node) {
				String str = node.toString();

				// System.out.println(str);

				if (str.contains("Color")) {
					// System.out.println(str);
					String[] splitString1 = str.split("\\(");

					String[] splitString2 = splitString1[0].split("\\.");

					String finalValue1 = splitString2[0];
					String finalValue2 = splitString2[1];
					String finalValue3 = splitString1[1];

					propertymultimap.put(finalValue1, "Color#" + finalValue3.substring(0, finalValue3.length() - 1));
				} else if (str.contains("Font")) {

					String[] fontSplit1 = str.split("\\(");

					String[] fontSplit2 = fontSplit1[0].split("\\.");

					String finalValue1 = fontSplit2[0];
					String finalValue2 = fontSplit2[1];
					String trimString = fontSplit1[2].replace("\"", "");
					trimString = trimString.replace("))", "");

					propertymultimap.put(finalValue1, "Font#" + trimString);
				}
				return super.visit(node);
			}
		});

		/*
		 * System.out.println(variablemultimap.toString());
		 * System.out.println(valuemultimap.toString());
		 * System.out.println(propertymultimap.toString());
		 */

		Set<String> variableKeyset = variablemultimap.keySet();
		Iterator<String> variableIterator = variableKeyset.iterator();

		Set<String> propertyKeyset = propertymultimap.keySet();
		Iterator<String> propertyIterator = propertyKeyset.iterator();

		/*
		 * while (variableIterator.hasNext()) { String variablekey =
		 * variableIterator.next();
		 * 
		 * List<String> variableValueList = (List<String>)
		 * variablemultimap.get(variablekey);
		 * 
		 * List<String> valueList =
		 * (List<String>)valuemultimap.get(variablekey);
		 * 
		 * //System.out.println(variablekey + " "+ variableValueList+ " "+
		 * valueList); List<String> propertyList = null; for(String
		 * variableValue : variableValueList) {
		 * 
		 * if(!propertymultimap.get(variableValue).isEmpty()) { propertyList =
		 * (List<String>)propertymultimap.get(variableValue); }
		 * 
		 * }
		 * 
		 * System.out.println(variablekey + " "+ variableValueList+ " "+
		 * valueList+ " "+ propertyList);
		 * 
		 * //System.out.println("\n");
		 * 
		 * }
		 */

		while (propertyIterator.hasNext()) {
			String propertyKey = propertyIterator.next();

			while (variableIterator.hasNext()) {

				StringBuilder sb1 = new StringBuilder("Variablename(");
				StringBuilder sb2 = new StringBuilder("Variablevalue(");
				StringBuilder sb3 = new StringBuilder("Properties(");

				String variablekey = variableIterator.next();

				List<String> variableList = (List<String>) variablemultimap.get(variablekey);

				for (String variable : variableList) {
					sb1.append(variable + ",");
				}
				sb1.deleteCharAt(sb1.length() - 1);
				sb1.append(")");
				List<String> valueList = (List<String>) valuemultimap.get(variablekey);

				for (String value : valueList) {
					sb2.append(value + ",");
				}
				sb2.deleteCharAt(sb2.length() - 1);
				sb2.append(")");
				List<String> propertyList = null;

				for (String variableValue : variableList) {
					if (variableValue.equalsIgnoreCase(propertyKey)) {

						propertyList = (List<String>) propertymultimap.get(variableValue);
						sb3.append("(" + variableValue + ")");
						for (String propery : propertyList) {
							sb3.append(propery + ",");
						}
						sb3.deleteCharAt(sb3.length() - 1);
					}

				}
				sb3.append(")");

				finalMap.put(variablekey, sb1.toString() + "$" + sb2.toString() + "$" + sb3.toString());

				// System.out.println(variablekey+" "+ sb1.toString()+ " "+
				// sb2.toString()+ " "+ sb3.toString());

			}
		}

		return finalMap;
	}

	public static Map<String, String> parseFile(String sourcePath, String destinationPath) {

		File file = new File(sourcePath);
		System.out.println("================" + file);
		char[] contents = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			StringBuffer sb = new StringBuffer();
			String line = "";
			while ((line = br.readLine()) != null) {
				// append the content and the lost new line.
				sb.append(line + "\n");
			}
			contents = new char[sb.length()];
			sb.getChars(0, sb.length() - 1, contents, 0);

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setResolveBindings(true);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);

		parser.setBindingsRecovery(true);

		Map options = JavaCore.getOptions();
		parser.setCompilerOptions(options);

		String unitName = "Sample.java";
		parser.setUnitName(unitName);

		String[] sources = { "C:\\Users\\20126957\\Music\\Final\\src\\com\\test" };
		String[] classpath = { "C:\\Program Files\\Java\\jdk1.8.0_121\\jre\\lib\\rt.jar" };

		parser.setEnvironment(classpath, sources, new String[] { "UTF-8" }, true);

		parser.setSource(contents);

		CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		if (cu.getAST().hasBindingsRecovery()) {
		}

		Multimap<String, String> variablemultimap = ArrayListMultimap.create();

		Multimap<String, String> valuemultimap = ArrayListMultimap.create();

		Multimap<String, String> propertymultimap = ArrayListMultimap.create();

		Map<String, String> finalMap = new HashMap<>();

		cu.accept(new ASTVisitor() {

			public boolean visit(VariableDeclarationFragment node) {

				IVariableBinding binding = node.resolveBinding();

				if (binding.getVariableDeclaration().getType().getName().startsWith("J")) {
					variablemultimap.put(binding.getVariableDeclaration().getType().getName(),
							binding.getVariableDeclaration().getName());
				}

				return true;
			}

			@Override
			public boolean visit(ClassInstanceCreation node) {

				String str = node.toString();

				String[] splitString = str.split("new\\s");

				if (splitString[1].startsWith("J")) {
					String str2 = splitString[1];

					String[] splitString2 = str2.split("\\(");

					int length = splitString2[1].length();

					valuemultimap.put(splitString2[0], splitString2[1].substring(0, length - 1));

				}

				return super.visit(node);
			}

			@Override
			public boolean visit(MethodInvocation node) {
				String str = node.toString();

				// System.out.println(str);

				if (str.contains("Color")) {
					// System.out.println(str);
					String[] splitString1 = str.split("\\(");

					String[] splitString2 = splitString1[0].split("\\.");

					String finalValue1 = splitString2[0];
					String finalValue2 = splitString2[1];
					String finalValue3 = splitString1[1];

					propertymultimap.put(finalValue1, "Color#" + finalValue3.substring(0, finalValue3.length() - 1));
				} else if (str.contains("Font")) {

					String[] fontSplit1 = str.split("\\(");

					String[] fontSplit2 = fontSplit1[0].split("\\.");

					String finalValue1 = fontSplit2[0];
					String finalValue2 = fontSplit2[1];
					String trimString = fontSplit1[2].replace("\"", "");
					trimString = trimString.replace("))", "");

					propertymultimap.put(finalValue1, "Font#" + trimString);
				}
				return super.visit(node);
			}
		});

		/*
		 * System.out.println(variablemultimap.toString());
		 * System.out.println(valuemultimap.toString());
		 * System.out.println(propertymultimap.toString());
		 */

		Set<String> variableKeyset = variablemultimap.keySet();
		Iterator<String> variableIterator = variableKeyset.iterator();

		Set<String> propertyKeyset = propertymultimap.keySet();
		Iterator<String> propertyIterator = propertyKeyset.iterator();

		/*
		 * while (variableIterator.hasNext()) { String variablekey =
		 * variableIterator.next();
		 * 
		 * List<String> variableValueList = (List<String>)
		 * variablemultimap.get(variablekey);
		 * 
		 * List<String> valueList =
		 * (List<String>)valuemultimap.get(variablekey);
		 * 
		 * //System.out.println(variablekey + " "+ variableValueList+ " "+
		 * valueList); List<String> propertyList = null; for(String
		 * variableValue : variableValueList) {
		 * 
		 * if(!propertymultimap.get(variableValue).isEmpty()) { propertyList =
		 * (List<String>)propertymultimap.get(variableValue); }
		 * 
		 * }
		 * 
		 * System.out.println(variablekey + " "+ variableValueList+ " "+
		 * valueList+ " "+ propertyList);
		 * 
		 * //System.out.println("\n");
		 * 
		 * }
		 */

		while (propertyIterator.hasNext()) {
			String propertyKey = propertyIterator.next();

			while (variableIterator.hasNext()) {

				StringBuilder sb1 = new StringBuilder("Variablename(");
				StringBuilder sb2 = new StringBuilder("Variablevalue(");
				StringBuilder sb3 = new StringBuilder("Properties(");

				String variablekey = variableIterator.next();

				List<String> variableList = (List<String>) variablemultimap.get(variablekey);

				for (String variable : variableList) {
					sb1.append(variable + ",");
				}
				sb1.deleteCharAt(sb1.length() - 1);
				sb1.append(")");
				List<String> valueList = (List<String>) valuemultimap.get(variablekey);

				for (String value : valueList) {
					sb2.append(value + ",");
				}
				sb2.deleteCharAt(sb2.length() - 1);
				sb2.append(")");
				List<String> propertyList = null;

				for (String variableValue : variableList) {
					if (variableValue.equalsIgnoreCase(propertyKey)) {

						propertyList = (List<String>) propertymultimap.get(variableValue);
						sb3.append("(" + variableValue + ")");
						for (String propery : propertyList) {
							sb3.append(propery + ",");
						}
						sb3.deleteCharAt(sb3.length() - 1);
					}

				}
				sb3.append(")");

				finalMap.put(variablekey, sb1.toString() + "$" + sb2.toString() + "$" + sb3.toString());

				// System.out.println(variablekey+" "+ sb1.toString()+ " "+
				// sb2.toString()+ " "+ sb3.toString());

			}
		}

		return finalMap;
	}

	// HTML CODE

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
					 * if(en.getKey().toString().equalsIgnoreCase("JLabel")){
					 * 
					 * styleStr="font-style:"+normalStr+";font-size:"+en.
					 * getValue().getFontSize()+"px;color:"+en.getValue().
					 * getForeground()+";";
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

											System.out.println("FINAL Value----------->>>>> " + entry.getValue());
											System.out.println("Final Key -------------->>>>>>" + entry.getKey());
											labelFieldValue = entry.getKey().split("=");
											if (entry.getValue().equalsIgnoreCase("JLabel")
													&& entry.getKey().toString().equalsIgnoreCase("Add Accountant")) {

												// System.out.println("listval.get(0)
												// :::--->>>> "+listval.get(0)+"
												// labelFieldValue[0]
												// --"+labelFieldValue[0]);
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
											// String
											// arr[]=entry.getKey().split("=");

											// buttonListvalues.add("Button Name
											// 222 -------->>>
											// "+entry.getKey());

											// System.out.println("==============="+buttonListvalues);
										}
									} // for

									System.out.println("Button Size ::::: " + buttonListvalues666666.size());
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
	{
	html.setPrependDocType(true);
	System.out.println(html.toHtmlString());

	File dir=new File("D:\\Scenario2 with Html Generated");
	if(!dir.exists())
	{
		dir.mkdir();
		System.out.println("Folder Created ;;;");
	}
	DateFormat format = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
	String timeStamp = format.format(new Date());
	String fileName = listval.get(01) + "_" + timeStamp;
	File tagFile = new File(dir, fileName + ".html");
	Writer writer = null;try
	{
		writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tagFile), "utf-8"));
	}catch(
	UnsupportedEncodingException ex)
	{
		// TODO Auto-generated catch block
		ex.printStackTrace();
	}catch(
	FileNotFoundException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}try {
		writer.write(html.toHtmlString());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	finally{
		try {
	
		writer.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	System.out.println("\n ***********  DONE ***************");
}
}

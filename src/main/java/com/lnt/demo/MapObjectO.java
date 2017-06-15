package com.lnt.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class MapObjectO {
	
	static int counter=0;
	
	public static int parseFile(String path, String outputPath) throws IOException {
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
								getCompMap(path);
							}
					}
			return counter; 
	}

	public static Map<String, String> getCompMap(String path) {
		

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
		String[] sources =  {""}; //{ "C:\\Users\\20110305\\Desktop\\POc project\\integration code\\Demo\\src" };
		String[] classpath = { "" };
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

				if (str.contains("Color")) {
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

		Set<String> variableKeyset = variablemultimap.keySet();
		Iterator<String> variableIterator = variableKeyset.iterator();
		Set<String> propertyKeyset = propertymultimap.keySet();
		Iterator<String> propertyIterator = propertyKeyset.iterator();

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
			}
		}
		return finalMap;
	}

	public static HashMap<String, String> getString(String path) {

		
		String fileName = path;
		String val = "";
		String key = "";
		String value = "";
		boolean nextLine = false;
		HashMap<String,String> results = new HashMap<>();
		 
		try {
		File f = new File(fileName);
		BufferedReader b = new BufferedReader(new FileReader(f));
		String readLine = "";
		while ((readLine = b.readLine()) != null) {
		readLine= readLine.trim();
		readLine=" "+readLine;
		 
		if(readLine.contains("(new ActionListener()".trim())){
		key = readLine.substring(0, readLine.indexOf("."));
		nextLine=true;
		}
		else if(nextLine==true)
		{
		value = value+readLine;
		 
		}
		 
	
		if(readLine.contains(" });"))
		{
		if(!key.isEmpty())
		results.put(key.trim(), value=value.replaceAll("\\s+", " ").trim());
		nextLine=false;
		key="";
		value = "";	 
		} 
		val = val + readLine+" ";
		}
		} catch (IOException e) {
		e.printStackTrace();
		}
		return results;
		
	}
}

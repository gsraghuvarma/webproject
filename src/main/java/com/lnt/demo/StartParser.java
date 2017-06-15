package com.lnt.demo;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;
public class StartParser {

	static String displayLogs;
	private final static Logger logger = Logger.getLogger(StartParser.class);
	static Map<File, List> map = new LinkedHashMap<File, List>();

	final static int interval = 1000;
	static int i=0;
	static String styleStr = "";

	static String labelFieldValue[];
	static boolean flag = false;
	static String eachFileLabelField = "";
	static int count = 0;
	static int filedCount = 0;
	static String labelVal = "";
	static int num=0;

	static String labelVal2 = "10";
	static String s1 = ".";

	static String sCurrentLine = null;
	static String subCompArray[] = null;
	static String newCompArray1[] = null;
	static String newCompArray[] = null;
	static String newCompArray11[] = null;
	static String newObj[] = null;
	static Timer timer;
	static JProgressBar pb = new JProgressBar();
	static String outputDir;
	static int counter=0;

	static JPanel panel = new JPanel();
	static JPanel frontPanel = new JPanel();

	static String fileNamePath;
	static String fileNameExec= null;

	static ParserToHtml rf = new ParserToHtml();
	static JButton convert;
	static StringBuilder buffer = new StringBuilder();
	static JTextArea display = new JTextArea(10,20);

	static JFrame frame2 = new JFrame("Progress...");
	static JPanel contentPane2 = new JPanel();

	static JMenu FileMenu = new JMenu("File");
	static JMenu helpMenu = new JMenu("Help");

	static JMenuBar menuBar = new JMenuBar();

	static JButton button = new JButton("Let's Begin !!");


	static JTextField userText = new JTextField("C:\\Users\\20126957\\Music\\Final\\src\\com\\test", 20);

	static JTextField htmlText = new JTextField("D:\\rajeev_Sachdeva", 20);

	static JLabel htmlDestLabel = new JLabel("Html Destination Location");

	static JFrame frame = new JFrame();
	
	//For Help Menu
	static JScrollPane helpScrollpane;
	static JScrollPane aboutScrollpane;
	static JScrollPane scrollpane;

	//	static ProgressBarDemo pbrDemo = new ProgressBarDemo(count);

	static String fileOpFolder;
	static int height;
	static int width; 
	
	static File[] fileName;

	//	static JPanel middlePanel = new JPanel();

	static{

		frontPanel.setVisible(true);
		frontPanel.add(button);



		panel.setVisible(false);

		getAllLoggerToPrint("info", "Converter Logger begin");
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultLookAndFeelDecorated(true);
		//	frame.setBackground(Color.BLUE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		height = screenSize.height;
		width = screenSize.width;

		// Layout . . .
		JLabel lblAddAccountant = new JLabel("Swing to HTML converter");
		lblAddAccountant.setForeground(Color.BLACK);

		lblAddAccountant.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblAddAccountant.setBounds(300, 30, 500, 50);

		panel.setBorder(new TitledBorder(new EtchedBorder(), "Converter !!"));
		panel.setBounds(100, 10, 500, 50);

//		JTextField userText = new JTextField("D:\\NewWorkspace\\Final-ForHTML\\src\\com\\test", 20);
		panel.setLayout(null);

		panel.add(lblAddAccountant);

		JLabel userFileLabel = new JLabel("Folder to convert");
		userFileLabel.setBounds(70, 10, 200, 250);
		panel.add(userFileLabel);

		// JTextField userText = new JTextField(20);
		userText.setBounds(220, 120, 250, 35);
		panel.add(userText);


		JButton btnBrowse = new JButton("Browse");
		btnBrowse.setBounds(550, 120, 100, 35);
		panel.add(btnBrowse);
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

				fileChooser.setAcceptAllFileFilterUsed(false);

				int rVal = fileChooser.showOpenDialog(null);
				if (rVal == JFileChooser.APPROVE_OPTION) {
					userText.setText(fileChooser.getSelectedFile().toString());
				}
			}
		});

		
		menuBar.add(FileMenu);
		menuBar.add(helpMenu);

		JMenuItem exit = new JMenuItem("Exit");
		JMenuItem help = new JMenuItem("Help");
		JMenuItem about = new JMenuItem("About");
		exit.addActionListener(new exitApp());

		FileMenu.add(exit);
		helpMenu.add(about);
		helpMenu.add(help);
		
		pb.setValue(0);
		pb.setStringPainted(true);
		pb.setBounds(10, 10, 400, 35);
		pb.setVisible(false);
		pb.setMaximum(100);

		//frame2.add(pb);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
		frame2.setLocationByPlatform(true);


		//            contentPane2.setBackground(Color.DARK_GRAY);

		//              contentPane2.add(hideButton);
		frame2.getContentPane().add(contentPane2);
		frame2.setSize(500, 100);
		frame2.add(pb);
		frame2.setVisible(true);

		JLabel htmlDestLabel = new JLabel("Html Destination Location");
		htmlDestLabel.setBounds(70, 80, 200, 250);
		panel.add(htmlDestLabel);

//		JTextField htmlText = new JTextField("D:\\Neha\\Demo", 20);
		htmlText.setBounds(220, 190, 250, 35);
		panel.add(htmlText);

		JButton browseHtml = new JButton("Browse");
		browseHtml.setBounds(550, 190, 100, 35);
		panel.add(browseHtml);
		browseHtml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.setAcceptAllFileFilterUsed(false);
				int rVal = fileChooser.showOpenDialog(null);
				if (rVal == JFileChooser.APPROVE_OPTION) {
					htmlText.setText(fileChooser.getSelectedFile().toString());
				}
			}
		});

/*
		menuBar.add(FileMenu);
		menuBar.add(helpMenu);
		JMenuItem exit = new JMenuItem("Exit");
		JMenuItem help = new JMenuItem("About help");
		exit.addActionListener(new exitApp());
		FileMenu.add(exit);
		helpMenu.add(help);*/
		/*
			Container c = frame.getContentPane();
		c.setLayout(new BorderLayout());
		c.add(panel, BorderLayout.NORTH);*/


		frame.setJMenuBar(menuBar);

		frame.setVisible(true); 
		frame.pack();
		frame.setSize(width/2 , height );
		frame.add(panel);

		panel.setBackground(Color.WHITE);
		

		getAllLoggerToPrint("infoEnd", "Test");
	
		
		//begin help and help about in menu bar 
		
		String helpCategories[] = {"Swing to Html Application","Version 1.0"};
		String aboutCategories[] = {"Swing to Html Application","Swing to Html Converter is for source code location.",
				"By using a browser button we can select the our source code.Html Destination location is for Destination location",
				"we can select the directory to place the converted code.","Converter: Click on converter button it will be converted java swing code to Html code.",
				"Reset: click on reset button we can erase all the text data.",
				"logging:It will be display all the logs of conversion code with respective time and date"};
		

		JList helpList = new JList(helpCategories);
		JList aboutList = new JList(aboutCategories);
		
		
		
		helpScrollpane = new JScrollPane(helpList);
		aboutScrollpane = new JScrollPane(aboutList);

		helpScrollpane.getViewport().add(helpList);
		aboutScrollpane.getViewport().add(aboutList);
		
		
		about.addActionListener(new ActionListener() { // we add an action
			// listener to the
			// JMenuItem
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, aboutScrollpane, "View About", JOptionPane.PLAIN_MESSAGE);
			}
		});

		help.addActionListener(new ActionListener() { // we add an action
			// listener to the
			// JMenuItem
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, helpScrollpane, "View Help", JOptionPane.PLAIN_MESSAGE);
			}
		});
		//end help and help about in menu bar 

	}
	public static void main(String[] args) throws IOException {




		JLabel totalFiles = new JLabel("Total no. of Files parsed: 0");
		totalFiles.setBounds(70,300,200,30);

		JLabel filesFailed = new JLabel("Files not parse: 0 ");
		filesFailed.setBounds(70,340,200,30);

		JLabel filesSuccess = new JLabel("Files successfully parsed: 0");
		filesSuccess.setBounds(70,320,200,30);

		JTextArea logsOutput = new JTextArea("Text area");
		logsOutput.setBounds(10, 300, 100, 250);


		/*	convert = new JButton("Convert");
		convert.setBounds(220, 280, 100, 35);
		 */
		convert = new JButton("Convert");
		convert.setBounds(220, 250, 100, 35);
		convert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {


				/*	frame.setVisible(true); 
				frame.pack();
				frame.setSize(width , height);
				frame.add(panel);

				panel.setBackground(Color.WHITE);*/

				pb.setVisible(true);
				
				String inputDir = userText.getText();
				System.out.println("inputDir==>>" + inputDir);
				getAllLoggerToPrint("debugIn",inputDir);
				String outputDir = htmlText.getText();
				System.out.println("outputDir==>>" + outputDir);

				File fileFolder = new File(outputDir);

				System.out.println("================^^^^^^^^^+++++++++"+fileFolder.getName());
				//Creating a project structure 
				System.out.println("+++++++++++++++++-------------*******"+htmlText.getClass());

				createDirectoriesWithCommonParent(fileFolder,"public","src\\components\\Article","src\\components\\Home","src\\constants","src\\reducers");
				System.out.println("outputDir==>>" + outputDir);

				String outputDirHtml = outputDir+"\\"+"public";

				System.out.println("Outpur Directory for HTML==>>"+outputDirHtml);


				/*fileOpFolder =   fileFolder +"\\"+"SummaryReport.csv";
				try {
					generateCsvFile(fileOpFolder);
				} catch (FileNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}*/

				File directory = new File(inputDir);
				fileName = directory.listFiles();
				StringBuffer sb = new StringBuffer("");
				List<String> listFile = new ArrayList<String>();
				String line = "";

				num=fileName.length;

				totalFiles.setText("Total no. of Files parsed:"+String.valueOf(num));


				for (File file : fileName) {

					fileNamePath = file.getPath();

					fileNameExec=fileNamePath.toString();

					logsOutput.setText(logsOutput.getText() + fileNameExec + "\r\n");

					System.out.println("logsOutput.getText():::"+logsOutput.getText().toString()+"::"+fileNameExec);

					BufferedReader br = null;
					try {
						br = new BufferedReader(new FileReader(file));
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (file.isFile() && file.length() > 0) {

						boolean flag = true;
						try {
							while ((line = br.readLine()) != null) {
								listFile.add(line);
								// System.out.println(line);
							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
					map.put(file, listFile);
					count=count+10;
					try {
						//					timer.start();
						System.out.println("FileName With Full Qualified:::" + fileNamePath);
						counter = rf.parserFile(fileNamePath, outputDirHtml);
						updateBar(count);
						System.out.println("num-counter"+(num-counter));
						filesFailed.setText("Files not parsed:"+(num-counter));
						filesSuccess.setText("Files successfully parsed:"+counter);
						System.out.println("counter::"+counter);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				fileOpFolder =   fileFolder +"\\"+"SummaryReport.csv";
				try {
					generateCsvFile(fileOpFolder);
				} catch (FileNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				getAllLoggerToPrint("debugFile",fileNamePath);

			}
		});


		panel.add(filesSuccess);
		panel.add(filesFailed);
		panel.add(totalFiles);
		//		panel.add(logsOutput);

		panel.add(convert);


		JButton registerButton = new JButton("Reset");
		registerButton.setBounds(350, 250, 100, 35);
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userText.setText("");
				htmlText.setText("");
			}

		});

		panel.add(registerButton);

		JLabel logData = new JLabel("Logs generated...");
		logData.setBounds(100, 260, 200, 250);
		panel.add(logData);



		display.setEditable(true); // set textArea non-editable

		JScrollPane scroll = new JScrollPane(display);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(100, 400, 400, 400);

		panel.add(scroll);

		panel.setVisible(true);
		panel.setPreferredSize(new Dimension(640, 480));
		panel.setLayout(new BorderLayout());

		/*frame.setJMenuBar(menuBar);

		frame.setVisible(true); 
		frame.pack();
		frame.setSize(width/2 , height );
		frame.add(panel);*/


		getAllLoggerToPrint("infoEnd","Test");

		System.out.println("******************* END ***********************");

		ReadLogFile();
	}
	//creating a java project structure 
	public static boolean createDirectoriesWithCommonParent(File parent, String... subs) {

		try
		{
			System.out.println("=====     cvbcvbv        =============");
			parent.mkdirs();
			File change = parent;
			if (!parent.exists() || !parent.isDirectory()) {
				return false;
			}
			for (String sub : subs) {

				File subFile = new File(parent, sub);
				subFile.mkdirs();
				if (!subFile.exists() || !subFile.isDirectory()) {
					return false;
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return true;
	}


	public static void getAllLoggerToPrint(String param1,String param2){

		if(param1.equals("info")){
			logger.info("Converter Logger begin in log");
		}

		if(param1.equals("debugIn")){
			logger.debug("Input Directory :"+param2);
		} 

		if(param1.equals("debugOut")){
			logger.debug("Output Directory :"+param2);
		}
		if(param1.equals("debugFile")){
			logger.debug("FileNamePath :"+param2);
		}

		if(param1.equals("debugFile")){
			logger.debug("FileNamePath :"+param2);
		}

		if(param1.equals("infoEnd")){
			logger.info("Converter Logger end in log"); 
		}
	}

	public static void ReadLogFile() throws IOException{

		FileInputStream fstream = new FileInputStream("C:\\log4j\\log4j");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream)); 
		String strLine;

		while ((strLine = br.readLine()) != null)   {
			// System.out.println (strLine);
			display.setText(display.getText() + strLine + "\r\n"); 
		}
		fstream.close(); 
		br.close();
	}

	// Exit functionality of menu bar
	static class exitApp implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	// CSV file generated code
	public static void generateCsvFile(String fileOpFolder) throws FileNotFoundException {
		PrintWriter pw = null;
		StringBuffer writer = new StringBuffer();
		// FileWriter writer = null;
		try {

			pw = new PrintWriter(fileOpFolder);

			/*
			 * sb.append("id"); sb.append(','); sb.append("Name");
			 * sb.append(','); sb.append("Gender"); sb.append('\n');
			 */
			/*
			 * writer = new FileWriter(fileOpFolder);
			 */
			writer.append("Path");
			writer.append(',');
			writer.append("FileName");
			writer.append(',');
			writer.append("Migration Status");
			writer.append(',');
			writer.append("Error's (ifany)");
			writer.append(',');
			writer.append("Total Number of UI components");
			writer.append(',');
			writer.append("Total Swing");
			writer.append(',');
			writer.append("Total Swing migrated");
			writer.append(',');
			writer.append("Total Awt");
			writer.append(',');
			writer.append("Total Awt migrated");
			writer.append(',');
			writer.append("Total Iview");
			writer.append(',');
			writer.append("Total Iview migrated");
			writer.append(',');
			writer.append("Total Huawei");
			writer.append(',');
			writer.append("Total Huawei migrated");
			writer.append(',');
			writer.append("Total number of listeners");
			writer.append(',');
			writer.append("Total number of listeners migrated");
			writer.append('\n');
			for (File csvFileName : fileName) {
				writer.append(csvFileName);
				writer.append(',');
				writer.append(csvFileName.getName());
				writer.append(',');
				writer.append('\n');
				
			}		
			/*writer.append(',');
			writer.append(',');
			writer.append(num);*/
			pw.write(writer.toString());
			System.out.println("CSV file is created..."+num);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				
				  pw.flush(); 
				  pw.close();
				 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	

	public static void updateBar(int newValue) {

		pb.setValue(newValue);

	}


}


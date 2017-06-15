package com.lnt.demo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.Timer;

public class DeskApp extends JPanel implements Runnable {
	

	static Map<File, List> map = new LinkedHashMap<File, List>();
 
	final static int interval = 1000;
	static int i;
	static String styleStr = "";

	static String labelFieldValue[];
	static boolean flag = false;
	static String eachFileLabelField = "";
	static int count = 0;
	static int filedCount = 0;
	static String labelVal = "";

	static String labelVal2 = "10";
	static String s1 = ".";

	static String sCurrentLine = null;
	static String subCompArray[] = null;
	static String newCompArray1[] = null;
	static String newCompArray[] = null;
	static String newCompArray11[] = null;
	static String newObj[] = null;
	static Timer timer;
	static JProgressBar pb;


	static ReadFileExampleTableFormat rf = new ReadFileExampleTableFormat();


	static JPanel panel = new JPanel();
	static JFrame frame;
	Thread thread = null;



	public static void main(String args[])
	{
		DeskApp main = new DeskApp();
		frame = new JFrame("Demo application");
		// frame.setSize(500, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultLookAndFeelDecorated(true);


		// make the frame half the height and width
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = screenSize.height;
		int width = screenSize.width;
		frame.setSize(width / 2, height / 2);

		// center the jframe on screen
		frame.setLocationRelativeTo(null);

		frame.setVisible(true);

		frame.add(panel);
		frame.setVisible(true);
		
		
		if(flag)
		{
			main.start();
		}
	}

	public DeskApp(){

		/*Component comp = new Component();


		List<String> listval = new LinkedList<String>();

		List<String> buttonValueList = new LinkedList<String>();
*/
		JLabel lblAddAccountant = new JLabel("Swing to HTML converter");
		lblAddAccountant.setForeground(Color.BLACK);

		lblAddAccountant.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblAddAccountant.setBounds(300, 30, 500, 50);


		JTextField userText = new JTextField("D:\\NewWorkspace\\Final-ForHTML\\src\\com\\test\\AddAccountant.java", 20);
		panel.setLayout(null);

		panel.add(lblAddAccountant);

		JLabel userFileLabel = new JLabel("Folder to convert");
		userFileLabel.setBounds(70, 10, 200, 250);
		panel.add(userFileLabel);

		// JTextField userText = new JTextField(20);
		userText.setBounds(220, 120, 250, 35);
		panel.add(userText);



		//Create a timer.
		timer = new Timer(interval, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (i == 20){
					Toolkit.getDefaultToolkit().beep();
					timer.stop();
					pb.setValue(0);
				}
				i = i + 1;
				pb.setValue(i);
			}
		});



		JButton btnBrowse = new JButton("Browse");
		btnBrowse.setBounds(550, 120, 100, 35);
		panel.add(btnBrowse);
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();

				// For Directory
				//fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				// For File
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

				fileChooser.setAcceptAllFileFilterUsed(false);

				int rVal = fileChooser.showOpenDialog(null);
				if (rVal == JFileChooser.APPROVE_OPTION) {
					userText.setText(fileChooser.getSelectedFile().toString());
				}
			}
		});

		JLabel htmlDestLabel = new JLabel("Html Source Location");
		htmlDestLabel.setBounds(70, 80, 200, 250);
		panel.add(htmlDestLabel);

		JTextField htmlText = new JTextField("D:\\Neha\\Demo",20);
		htmlText.setBounds(220, 190, 250, 35);
		panel.add(htmlText);

		JButton browseHtml = new JButton("Browse");
		browseHtml.setBounds(550, 190, 100, 35);
		panel.add(browseHtml);
		browseHtml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();

				// For Directory
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				// For File
				// fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

				fileChooser.setAcceptAllFileFilterUsed(false);

				int rVal = fileChooser.showOpenDialog(null);
				if (rVal == JFileChooser.APPROVE_OPTION) {
					htmlText.setText(fileChooser.getSelectedFile().toString());
				}
			}
		});




		JButton loginButton = new JButton("Convert");
		loginButton.setBounds(220, 280, 100, 35);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {


				pb = new JProgressBar(0, 20);
				pb.setValue(0);
				pb.setStringPainted(true);
				pb.setBounds(230, 400, 400, 35);
				pb.setVisible(true);
				panel.add(pb);



				String inputDir = userText.getText();
				System.out.println("inputDir==>>" + inputDir);

				String outputDir = htmlText.getText();
				System.out.println("outputDir==>>" + outputDir);

				//			File directory = new File(inputDir);
				//			File[] fileName = directory.listFiles();
				StringBuffer sb = new StringBuffer("");
				List<String> listFile = new ArrayList<String>();
				String line = "";

				File file = new File(inputDir);

				//		for (File file : fileName) {

				String fileNamePath = file.getPath();

				System.out.println("FileName With Full Qualified:::" + fileNamePath);
				BufferedReader br = null;
				try {
					br = new BufferedReader(new FileReader(file));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (file.isFile() && file.length() > 0) {

					flag=true;

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
				//				map.put(file, listFile);
				try {
					timer.start();
					rf.parseFile(fileNamePath, outputDir);

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//			}

			}
		});


		panel.add(loginButton);

		JButton registerButton = new JButton("Reset");
		registerButton.setBounds(350, 280, 100, 35);
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userText.setText("");
				htmlText.setText("");
			}
		});

		panel.add(registerButton);






	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		i=0;
	}

	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}


}
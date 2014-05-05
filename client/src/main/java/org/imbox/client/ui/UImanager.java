package org.imbox.client.ui;

import java.util.function.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.*;

public class UImanager{
    
    private JFrame         mainFrame;
    private JPanel         mainPanel;
    private int            lineCtr;
    private RegisterUI     reger;

    private JPanel         displayPanel;
    private JScrollPane    scroll;
    private JTextArea      display;

    private JPanel         loginPanel;
    private JButton        button;
    private JPasswordField pwdField;
    private JLabel         pwdFieldLabel;
    private JTextField     nameField;
    private JLabel         nameFieldLabel;
    private JLabel         stateLabel;

    private JButton        syncButton;
    private JButton        urlButton;


    private BiConsumer<String,String> newUserFun;
    private BiConsumer<String,String> loginFun;
    private Consumer<String> shareURLFun;
    private Consumer<String> fSyncFun;

    public void updateNewUserFun   
	(BiConsumer<String,String> newf){newUserFun = newf;};
    public void updateLoginFun   
	(BiConsumer<String,String> newf){loginFun = newf;};
    public void updateShareURLFun
	(Consumer<String> newf){shareURLFun = newf;};
    public void updateFSyncFun   
	(Consumer<String> newf){fSyncFun = newf;};

    public UImanager(){

	/** initializing all action handler **/	

	setDefaultFuns();

	/** Main Frame Init **/
	
	mainFrame = new JFrame("IMBOX");
	mainPanel = new JPanel();
	lineCtr   = 0;
	mainFrame.setResizable(false);
	mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	reger       = new RegisterUI(mainFrame);

	/** Console Area Init **/

	displayPanel = new JPanel();
	display      = new JTextArea("*** System initialized ***");
	scroll       = new JScrollPane (display);

	/** Console Area Setup **/

	display.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        display.setRows(20);
	display.setColumns(40);
	display.setLineWrap(true);  
        display.setWrapStyleWord(false);  
	display.setBackground(Color.BLACK);
	display.setForeground(Color.GREEN);
	display.setEditable(false);
	scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.
					  VERTICAL_SCROLLBAR_ALWAYS);
	displayPanel.setBorder(new TitledBorder
			       (new EtchedBorder(),"System Info"));

	/** Login Area Init **/
	
	loginPanel     = new JPanel();
        button         = new JButton("Connect");
	pwdField       = new JPasswordField(12);
        nameField      = new JTextField(12);
	stateLabel     = new JLabel();
	syncButton     = new JButton();
	urlButton      = new JButton();
	
	/** Login Area Setup**/

	button.setSize(pwdField.getSize());
	button.addActionListener(loginAction);
	stateLabel.setSize(pwdField.getSize());
	loginPanel.setLayout(new GridLayout(3,2));
 
	stateLabel.setHorizontalAlignment(JLabel.CENTER);
	stateLabel.setHorizontalTextPosition(JLabel.CENTER);

	nameField.setBorder(new TitledBorder
			     (new EtchedBorder(),"User:"));
	pwdField.setBorder(new TitledBorder
			     (new EtchedBorder(),"Password:"));
	loginPanel.setBorder(new TitledBorder
			     (new EtchedBorder(),"Login"));

	/** Components attachment and layout setting **/

	loginPanel.add(nameField);
	loginPanel.add(stateLabel);
	loginPanel.add(pwdField);
	loginPanel.add(button);
	loginPanel.add(urlButton);
	loginPanel.add(syncButton);
	displayPanel.add(scroll);	
	mainPanel.setLayout(new BorderLayout());
	mainPanel.add(displayPanel,BorderLayout.CENTER);
	mainPanel.add(loginPanel,BorderLayout.SOUTH);
	mainFrame.getContentPane().add(mainPanel);
	mainFrame.setLocationRelativeTo(null);
	mainFrame.pack();

	/** setup system as init state **/

	setDISCONNECTED();
    };

    public void show(){mainFrame.setVisible(true);};	
    public void hide(){mainFrame.setVisible(false);};
    
    public void appendMsg(String msg){
	if(lineCtr >= 1500){
	    display.setText("*** Refresh Log ***");
	    lineCtr = 0; 
	}
	lineCtr += 1;
	display.append("\n ["+lineCtr+"]> "+msg);
	refresh();
    };
    private void refresh(){
	display.setCaretPosition(display.getDocument().getLength());
    }; 


    /**
       DEFINITION OF STATES OF BUTTON SET
     */

    private void setButtonsOnline(){
	syncButton.setText("Manually Sync");
	urlButton.setText("Share File");
	urlButton.removeActionListener(newUserAction);
	syncButton.removeActionListener(systemExitAction);
	urlButton.addActionListener(shareURLAction);
	syncButton.addActionListener(fSyncAction);
	urlButton.setEnabled(true);
	syncButton.setEnabled(true);
    }
    private void setButtonsOffline(){
	syncButton.setText("Manually Sync");
	urlButton.setText("Share File");
	urlButton.setEnabled(false);
	syncButton.setEnabled(false);
    }
    private void setButtonsSpecialized(){
	urlButton.setText("New User");
	syncButton.setText("Exit");
	urlButton.removeActionListener(shareURLAction);
	syncButton.removeActionListener(fSyncAction);
	urlButton.addActionListener(newUserAction);
	syncButton.addActionListener(systemExitAction);
	urlButton.setEnabled(true);
	syncButton.setEnabled(true);
    }

    /**
       DEFINITION OF SYSTEM STATES 
     */

    public void setSYNCHRONIZED(){
	stateLabel.setForeground(Color.BLUE);
	stateLabel.setText("SYNCHRONIZED");
	nameField.setEnabled(false);
	pwdField.setEnabled(false);
	button.setEnabled(false);
	setButtonsOnline();
    };
    public void setSYNCHRONIZING(){
	stateLabel.setForeground(Color.MAGENTA);
	stateLabel.setText("SYNCHRONIZING");
	nameField.setEnabled(false);
	pwdField.setEnabled(false);
	button.setEnabled(false);
	setButtonsOffline();
    };
    public void setDISCONNECTED(){
	stateLabel.setForeground(Color.GRAY);
	stateLabel.setText("DISCONNECTED"); 
	button.setEnabled(true);
	nameField.setEnabled(true);
	pwdField.setEnabled(true);
	setButtonsSpecialized();
    };
    public void setCONFLICT(){
	stateLabel.setForeground(Color.ORANGE);
	stateLabel.setText("SYNCHRONIZED with CONFLICT!");
	nameField.setEnabled(false);
	pwdField.setEnabled(false);
	button.setEnabled(false);
	setButtonsOnline();
    };
    public void setSHUTDOWN(){
	stateLabel.setForeground(Color.GRAY);
	stateLabel.setText("SYSTEM ERROR");
	nameField.setEnabled(false);
	pwdField.setEnabled(false);
	button.setEnabled(false);
	setButtonsOffline();
    };

    /**
     *  DEFAULT ACTION
     */
    private ActionListener newUserAction = (ActionEvent e) -> {
	if(reger.regist()){
	    newUserFun.accept(reger.getAcc(),reger.getPwd());
	}
    };
    private ActionListener loginAction = (ActionEvent e) -> {
	String name = nameField.getText();
	String pwd = pwdField.getText();
	if(name.equals("") || 
	   pwd.equals("")){
	    appendMsg("Please check you account name and password!");
	}else{	    
	    loginFun.accept(name,pwd);
	}
    };
    private ActionListener shareURLAction = (ActionEvent e) -> {
	try{
	    ChooserUI fChooser = new ChooserUI();
	    if(fChooser.ChooseFile()){
		shareURLFun.accept(fChooser.getFileName());
	    }
	}catch(HeadlessException he){
	    appendMsg("System failure because of a HeadlessException!");
	    appendMsg("Please restart your Imbox client.");
	    setSHUTDOWN();
	}
    };
    private ActionListener fSyncAction = (ActionEvent e) -> {
	fSyncFun.accept("");
    };
    private ActionListener systemExitAction = (ActionEvent e) -> {
	System.exit(0);
    };

    private void setDefaultFuns(){
	newUserFun  = (String _acc, String _pwd) -> {
	    appendMsg("[DEFAULT] new user action");
	};
	loginFun    = (String _acc, String _pwd) -> {
	    appendMsg("[DEFAULT] login action");
	};
	shareURLFun = (String _fname) -> {
	    appendMsg("[DEFAULT] generate URL action");
	};
	fSyncFun    = (String _str) -> {
	    appendMsg("[DEFAULT] forced sync action");
	};
    };
}

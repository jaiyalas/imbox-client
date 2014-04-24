package org.imbox.client;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.*;

import org.imbox.infrastructure.network.loginmaster.Loginmaster;

public class UImanager{
    
    private JFrame         mainFrame;
    private JPanel         mainPanel;
    private int            lineCtr;
    private Loginmaster    loginmaster;

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

    public UImanager(){

	this.loginmaster = new Loginmaster();

	/** Main Frame Init **/
	
	mainFrame = new JFrame("IMBOX");
	mainPanel = new JPanel();
	lineCtr   = 0;
	mainFrame.setResizable(false);
	mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	/** Console Area Init **/

	displayPanel = new JPanel();
	display      = new JTextArea("*** System initialized ***");
	scroll       = new JScrollPane (display);

	/** Console Area Setup **/

        display.setRows(20);
	display.setColumns(25);
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
	
	/** Login Area Setup**/

	button.setSize(pwdField.getSize());
	stateLabel.setSize(pwdField.getSize());
	loginPanel.setLayout(new GridLayout(2,2));
 
	stateLabel.setHorizontalAlignment(JLabel.CENTER);
	stateLabel.setHorizontalTextPosition(JLabel.CENTER);
	setDISCONNECTED();

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
	displayPanel.add(scroll);	
	mainPanel.setLayout(new BorderLayout());
	mainPanel.add(displayPanel,BorderLayout.CENTER);
	mainPanel.add(loginPanel,BorderLayout.SOUTH);
	mainFrame.getContentPane().add(mainPanel);
	mainFrame.setLocationRelativeTo(null);
	mainFrame.pack();
	
	appendMsg(loginmaster.getMAC());

	btnBehavior(ae->connecting());
	
    };

    public void show(){mainFrame.setVisible(true);};	
    public void hide(){mainFrame.setVisible(false);};
    
    public void btnBehavior(ActionListener al){
	button.addActionListener(al);
    };
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

    public void setSYNCHRONIZED(){
	stateLabel.setForeground(Color.BLUE);
	stateLabel.setText("SYNCHRONIZED");
	nameField.setEnabled(false);
	pwdField.setEnabled(false);
	button.setEnabled(false);
    };
    public void setSYNCHRONIZING(){
	stateLabel.setForeground(Color.MAGENTA);
	stateLabel.setText("SYNCHRONIZING");
	nameField.setEnabled(false);
	pwdField.setEnabled(false);
	button.setEnabled(false);
    };
    public void setDISCONNECTED(){
	stateLabel.setForeground(Color.GRAY);
	stateLabel.setText("DISCONNECTED"); 
	button.setEnabled(true);
	nameField.setEnabled(true);
	pwdField.setEnabled(true);
    };
    public void setCONFLICT(){
	stateLabel.setForeground(Color.ORANGE);
	stateLabel.setText("SYNCHRONIZED with CONFLICT!");
	nameField.setEnabled(false);
	pwdField.setEnabled(false);
	button.setEnabled(false);
    };
    public void setSHUTDOWN(){
	stateLabel.setForeground(Color.GRAY);
	stateLabel.setText("SYSTEM ERROR");
	nameField.setEnabled(false);
	pwdField.setEnabled(false);
	button.setEnabled(false);
    };

    public void connecting(){	
	String name = nameField.getText();
	String pwd = pwdField.getText();
	if(name.equals("") || 
	   pwd.equals("")){
	    appendMsg("Please check you account name and password!");
	}else{
	    /** NOTICE NOTICE NOTICE NOTICE NOTICE NOTICE **/
	    loginmaster.setInfo(name,pwd);
	    //loginmaster.authenticate();
	    if(loginmaster.getstatus()){
		appendMsg("Connecting to Server. "+
			  "You're logging in server with \n"+
			  "    <"+name+"/"+pwd+">");
		setSYNCHRONIZING();
		/** auto-sync should be involked here **/
		setSYNCHRONIZED();
	    }else{ //loginFail
		appendMsg("Login failed, please verify your network "+
			  "and/or your login information.");
	    }
	}	    
	
    };

}

package org.imbox.client;

import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.*;

public class UImanager{
    
    private JFrame         mainFrame;
    private JPanel         mainPanel;

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
	mainFrame = new JFrame("IMBOX");
	mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	mainPanel = new JPanel();


	/** 
	    Setup Log Area for Showing Logs/Events.  
	**/
	displayPanel = new JPanel();
	display      = new JTextArea("System initialized");
	scroll       = new JScrollPane (display);

	/** 
	    Setup Login Area for enabling connection.  
	**/
	loginPanel     = new JPanel();
        button         = new JButton("Connect");
	pwdField       = new JPasswordField(12);
        //pwdFieldLabel  = new JLabel("Password: ");
        nameField      = new JTextField(12);
        //nameFieldLabel = new JLabel("Account: ");
	stateLabel     = new JLabel();
	
	button.setSize(pwdField.getSize());
	stateLabel.setSize(pwdField.getSize());
	    
	//loginPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
	loginPanel.setLayout(new GridLayout(2,2));

	stateLabel.setForeground(Color.RED);
	stateLabel.setHorizontalAlignment(JLabel.CENTER);
	stateLabel.setHorizontalTextPosition(JLabel.CENTER);
	stateLabel.setText("DISCONNECTED"); 
	//stateLabel.setFont(stateLabel.getFont())

	//nameFieldLabel.setLabelFor(nameField);
	//pwdFieldLabel.setLabelFor(pwdField);

	loginPanel.add(nameField);
	loginPanel.add(stateLabel);
	loginPanel.add(pwdField);
	loginPanel.add(button);

	nameField.setBorder(new TitledBorder
			     (new EtchedBorder(),"User:"));
	pwdField.setBorder(new TitledBorder
			     (new EtchedBorder(),"Password:"));
	loginPanel.setBorder(new TitledBorder
			     (new EtchedBorder(),"Login"));
	



	display.setEditable (false);
	display.setColumns(25);
        display.setRows(20);
	display.setLineWrap(true);  
        display.setWrapStyleWord(true);  
        display.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT); 
	display.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT); 

	scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.
					  VERTICAL_SCROLLBAR_ALWAYS);
	displayPanel.add(scroll);
	displayPanel.setBorder(new TitledBorder
			       (new EtchedBorder(),"System Info"));
	
	mainPanel.setLayout(new BorderLayout());
	mainPanel.add(displayPanel,BorderLayout.CENTER);
	mainPanel.add(loginPanel,BorderLayout.SOUTH);
	mainFrame.getContentPane().add(mainPanel);
	mainFrame.setLocationRelativeTo(null);
	mainFrame.pack();	
    };

    public void show(){mainFrame.setVisible(true);};	
    public void hide(){mainFrame.setVisible(false);};
    public void refresh(){
	display.setCaretPosition(display.getDocument().getLength());
    }; 
    public void settext(String content){
	//text.setText(content);
    };
}

package org.imbox;

//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.*;

public class UImanager{
    
    private JFrame mainframe;
    private JPanel mainpanel;
    private JTextArea display;
    private JScrollPane scroll;

    public UImanager(){
	mainframe = new JFrame("IMBOX");
	mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	mainpanel = new JPanel();
	mainpanel.setBorder (new TitledBorder(new EtchedBorder(), 
					      "Events Log"));
	
	display = new JTextArea();
	display.setColumns(20);
        display.setLineWrap(true);
        display.setRows(5);
        display.setWrapStyleWord(true);
	display.setEditable (false);

	scroll = new JScrollPane (display);
	scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.
					  VERTICAL_SCROLLBAR_ALWAYS);

	mainpanel.add(scroll);
	mainframe.getContentPane().add(mainpanel);
	mainframe.setLocationRelativeTo ( null );
	mainframe.pack();
	//mainframe.setVisible(true);	
    }	

    public void show(){mainframe.setVisible(true);};	
    public void hide(){mainframe.setVisible(false);};
	
    public void settext(String content){
	//text.setText(content);
    };
}

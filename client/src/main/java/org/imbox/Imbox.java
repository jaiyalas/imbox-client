package org.imbox;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils; 

import org.imbox.client.*;
import org.imbox.infrastructure.*;
import org.imbox.infrastructure.file.*;

public class Imbox {
    public static void main(String[] args) throws IOException {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.

        //String string = "This is\na test";
	//File file = new File("/Users/jaiyalas/test.txt");
        //FileUtils.writeStringToFile(file, string);

        //new Thread(
        //  () -> System.out.println("hello world")
        //).start();

	//JTextArea textArea = new JTextArea();
	//textArea.setColumns(20);
        //textArea.setLineWrap(true);
        //textArea.setRows(5);
        //textArea.setWrapStyleWord(true);
         
        //JScrollPane jScrollPane1 = new JScrollPane(textArea);

	//JPanel mainpanel = new JPanel();
	//mainpanel.setBorder (new TitledBorder(new EtchedBorder(), 
	//				      "Events Log"));

	//JFrame jf = new JFrame("TEST");
	//jf.setSize(500,500);
	//jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//mainpanel.add(jScrollPane1);
	//jf.getContentPane().add(mainpanel);
	//jf.pack();

	UImanager ui = new UImanager();

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run(){
		//ui.btnBehavior(ae->ui.syncState());
		ui.show(); 
		//for(int i=0;i<=100;i++) ui.appendMsg("Event"+i);
	    }
        });

	//UImanager ui = new UImanager();
	//ui.show();
    }
}

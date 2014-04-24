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
import org.imbox.infrastructure.exceptions.*;

public class Imbox {
    public static void main(String[] args) throws IOException {
	UImanager ui = new UImanager();

	boolean b = false;
	try{
	    b = Workspace.prepareWorkspace();
	}catch(IMBOX_DirConfException e){
	    ui.appendMsg(e.toString());
	}catch(IMBOX_MkdirFailException e){
	    ui.appendMsg(e.toString());
	    ui.setSHUTDOWN();
	}
	ui.show();
	
	//javax.swing.SwingUtilities.invokeLater(new Runnable() {
        //    public void run(){
		//ui.btnBehavior(ae->ui.syncState());
	//	ui.show(); 
		//for(int i=0;i<=100;i++) ui.appendMsg("Event"+i);
	//    }
        //});

	//UImanager ui = new UImanager();
	//ui.show();
    }
}

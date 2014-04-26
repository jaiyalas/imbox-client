package org.imbox;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils; 

import org.imbox.client.*;
import org.imbox.client.fileMonitor.*;
import org.imbox.client.synchronize.*;

import org.imbox.infrastructure.*;
import org.imbox.infrastructure.log.*;
import org.imbox.infrastructure.file.*;
import org.imbox.infrastructure.exceptions.*;

public class Imbox {
    public static void main(String[] args) throws IOException {
	UImanager ui = new UImanager();

	File workspace = new File(Workspace.HOME);
	try{
	    workspace = Workspace.prepareWorkspace();
	}catch(IMBOX_DirConfException e){
	    ui.appendMsg(e.toString());
	}catch(IMBOX_MkdirFailException e){
	    ui.appendMsg(e.toString());
	    ui.setSHUTDOWN();
	}
	ui.show();

	LogWriter logwriter = new LogWriter();
	Synker    synker    = new Synker();
	
	MonitorShell shell = new MonitorShell(workspace,ui,logwriter,synker);
	try{
	    shell.start(); //folk
	}catch(Exception e){}
    }
}

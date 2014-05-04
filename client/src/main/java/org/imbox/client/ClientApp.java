package org.imbox.client;

import java.io.*;
import java.util.*;

import org.imbox.client.*;
import org.imbox.client.ui.*;
import org.imbox.client.log.*;
import org.imbox.client.fileMonitor.*;
import org.imbox.client.synchronize.*;
import org.imbox.client.networkrelated.*;

import org.imbox.infrastructure.*;
import org.imbox.infrastructure.file.*;
import org.imbox.infrastructure.exceptions.*;

public class ClientApp{

    private UImanager ui;
    private File workspace; 
    private Loginmaster loginmaster;
    private LogWriter logwriter;
    private Synker    synker;
    private MonitorShell shell;

    public ClientApp(){	
	workspace= new File(Workspace.HOME);
	ui = new UImanager();
	shell = new MonitorShell(workspace);

	loginmaster = new Loginmaster();

	logwriter = new LogWriter();
	synker    = new Synker();	
    };
    public void exec(){
	try{
	    workspace = Workspace.prepareWorkspaceC();
	}catch(IMBOX_DirConfException e){
	    ui.appendMsg(e.toString());
	}catch(IMBOX_MkdirFailException e){
	    ui.appendMsg(e.toString());
	    ui.setSHUTDOWN();
	}
	
	updateUIFunctions();
	ui.show();

	updateShellHandler();
	try{
	    shell.start(); //folk
	}catch(Exception e){}

    };

    private void updateUIFunctions(){
	ui.updateNewUserFun((_acc,_pwd)->{
		ui.appendMsg("[REPLACED] new user: "+_acc+"/"+_pwd);
	    });
	ui.updateLoginFun((_acc,_pwd) ->{
		loginmaster.setInfo(_acc,_pwd);
		loginmaster.authenticate();
		if(loginmaster.getstatus()){
		    ui.setSYNCHRONIZING();
		    ui.appendMsg("Connecting to Server...");
		    ui.appendMsg("You're logging in server with "+_acc);
		    ui.setSYNCHRONIZED();
		}else{
		ui.appendMsg("Login failed, please verify your network "+
			     "and/or your login information.");
		}
	    }); 
	ui.updateShareURLFun((_fname) -> {
		//
	    });
	ui.updateFSyncFun((_str) -> {
		//
	    });
    };
	
    private void updateShellHandler(){
	shell.updateHandler(AltType.FileCreate, (File file)->{
		try{
		    List<Block> bs = FileHandler.genBlocksFromChannel
			(new RandomAccessFile(file,"r").getChannel());
		    bs.forEach(b -> {
			    Blockposter bp = new Blockposter(file.getName(),b.getContent(),b.getPos());
			    ui.appendMsg("sent block "+b.getName()+" = "+bp.getstatus());
			});
		}catch(IOException e){} 
		ui.appendMsg(file.getAbsoluteFile() + " was created.");
	    });
	shell.updateHandler(AltType.FileDelete, (File file)->{
		ui.appendMsg(file.getAbsoluteFile() + " was removed.");
	    });
	shell.updateHandler(AltType.FileChange, (File file)->{
		ui.appendMsg(file.getAbsoluteFile() + " was modified.");
	    });
    };
}

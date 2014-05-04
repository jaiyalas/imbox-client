package org.imbox.client;

import java.io.*;
import java.util.*;

import org.imbox.client.*;
import org.imbox.client.ui.*;
import org.imbox.client.log.*;
import org.imbox.client.fileMonitor.*;
import org.imbox.client.synchronize.*;

import org.imbox.client.network.login.*;
import org.imbox.client.network.block.*;

import org.imbox.infrastructure.*;
import org.imbox.infrastructure.file.*;
import org.imbox.infrastructure.exceptions.*;

public class ClientApp{

    private UImanager ui;
    private File workspace; 

    private LogWriter logwriter;
    private Synker    synker;
    private MonitorShell shell;
    
    public ClientApp(){	
	workspace= new File(Workspace.HOME);
	ui = new UImanager();
	shell = new MonitorShell(workspace);

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
		Accountcreator ac = new Accountcreator(_acc,_pwd);
		try{
		    ac.register();
		    if(ac.getstatus()){
			ui.appendMsg("Your account ("+_acc+
				     ") has been created successfully");
		    }else{
			ui.appendMsg("Account creating have failed");
		    }
		}catch(IMBOXNW_jsonException e){
		    ui.appendMsg("Create new user failure.");
		}catch(IMBOXNW_httpstatusException e){
		    ui.appendMsg("Create new user failure.");
		}catch(IOException e){
		    ui.appendMsg("Create new user failure.");
		}		
	    });
	ui.updateLoginFun((_acc,_pwd) ->{
		Loginmaster loginmaster = new Loginmaster(_acc,_pwd);
		try{
		    loginmaster.authenticate();
		    if(loginmaster.getstatus()){
			ui.setSYNCHRONIZING();
			ui.appendMsg("Connecting to Server...");
			ui.appendMsg("You're logging in server with "+_acc);
			ui.setSYNCHRONIZED();
		    }else{
			ui.appendMsg("Login failed,"+
				     " please verify your network "+
				     "and/or your login information.");
		    }
		}catch(IMBOXNW_jsonException e){
		    ui.appendMsg("Login failure.");
		}catch(IMBOXNW_httpstatusException e){
		    ui.appendMsg("Login failure.");
		}catch(IOException e){
		    ui.appendMsg("Login failure.");
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
		ui.appendMsg(file.getAbsoluteFile() + " was created.");
		try{
		    ui.setSYNCHRONIZING();
		    List<Block> bs = FileHandler.genBlocksFromChannel
			(new RandomAccessFile(file,"r").getChannel());
		    bs.forEach
			(b -> {
			    try{
				Blockposter bp = new Blockposter(file.getName(),
								 b.getContent(),b.getPos());
				 bp.sendrequest();
				 if(bp.getstatus()){
				     ui.appendMsg("Block "+b.getName()+"has been sent");
				 }else{
				     ui.appendMsg("Sending has failed");
				 }
			    }catch(Exception e){
				ui.appendMsg("GOT a Exception");
			    }
			});
		    ui.setSYNCHRONIZED();
		}catch(IOException e){
		    ui.appendMsg("Loading file ("+file.getName()+") has failed");
		    ui.setSHUTDOWN();
		} 
	    });
	shell.updateHandler(AltType.FileDelete, (File file)->{
		ui.appendMsg(file.getAbsoluteFile() + " was removed.");
	    });
	shell.updateHandler(AltType.FileChange, (File file)->{
		ui.appendMsg(file.getAbsoluteFile() + " was modified.");
	    });
    };
}

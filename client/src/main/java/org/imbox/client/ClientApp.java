package org.imbox.client;

import java.io.*;
import java.util.*;

import org.apache.commons.io.*;

import org.imbox.client.*;
import org.imbox.client.ui.*;
import org.imbox.client.log.*;
import org.imbox.client.fileMonitor.*;
import org.imbox.client.synchronize.*;

import org.imbox.client.network.*;
import org.imbox.client.network.file.*;
import org.imbox.client.network.login.*;
import org.imbox.client.network.block.*;
import org.imbox.client.network.sharelink.*;

import org.imbox.infrastructure.*;
import org.imbox.infrastructure.file.*;
import org.imbox.infrastructure.exceptions.*;

public class ClientApp{

    private UImanager ui;
    private File workspace; 

    
    private Synker    synker;
    private MonitorShell shell;
    
    public ClientApp(){	
	workspace= new File(Workspace.SYSDIRc);
	ui = new UImanager();
	shell = new MonitorShell(workspace);

	synker    = new Synker(ui);	
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

	shell.resetShellHandlers();
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
			ui.appendMsg("Account creating have failed,"+
				     " please verify your network "+
				     "and try again.");
		    }
		}catch(Exception e){
		    ui.appendMsg("Create new user failure.");
		    ui.appendMsg("[EXCEPTION]:"+e.toString());
		}		
	    });
	ui.updateLoginFun((_acc,_pwd) ->{
		Loginmaster loginmaster = new Loginmaster(_acc,_pwd);
		try{
		    loginmaster.authenticate();
		    if(loginmaster.getstatus()){
			updateShellHandler();
			ui.setSYNCHRONIZING();
			ui.appendMsg("You're logging in server with "+_acc);
			ui.setSYNCHRONIZED();
		    }else{
			ui.appendMsg("Login failed,"+
				     " please verify your network "+
				     "and try again.");
		    }
		}catch(Exception e){
		    ui.appendMsg("Login failure.");
		    ui.appendMsg("[EXCEPTION]:"+e.toString());
		}
	    }); 
	ui.updateShareURLFun((_fname) -> {
		try{
		    URLgenerator urlg = new URLgenerator(_fname);
		    urlg.sendrequest();
		    if(urlg.getstatus()){
			ui.appendMsg("URL: "+urlg.geturl());
		    }else{
			ui.appendMsg("Generating URL has failed");
		    }
		}catch(Exception e){
		    ui.appendMsg("Generating URL failure.");
		    ui.appendMsg("[EXCEPTION]:"+e.toString());
		}
		
	    });
	ui.updateFSyncFun((_str) -> {
		synker.sync();
	    });
    };
	
    private void updateShellHandler(){
	shell.updateHandler(AltType.FileCreate, (File file)->{
		ui.appendMsg(file.getAbsoluteFile() + " was created.");
		try{
		    ui.setSYNCHRONIZING();
		    InputStream is    = new FileInputStream(file);
		    byte[]      bytes = IOUtils.toByteArray(is);
		    is.close();
		    Newfilerequester nfq = new Newfilerequester(file.getName(),Hash.getMD5String(bytes)); 
		    nfq.sendrequest();
		    ui.appendMsg("[new file] succ = " + nfq.getstatus()); 
		    ui.appendMsg("[new file] errorcode = " + nfq.geterrorcode()); 
		    ui.appendMsg("[new file] needtopostblock = " + nfq.needtopostblock());

		    if(nfq.getstatus() && nfq.needtopostblock()){
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
					//
				    }else{
					ui.appendMsg("Sending has failed");
				    }
				}catch(Exception e){
				    ui.appendMsg("GOT a Exception");
				}
			    });
		    }//end-if
		    ui.setSYNCHRONIZED();
		}catch(IOException e){
		    ui.appendMsg("Loading file ("+file.getName()+") has failed");
		    ui.setSHUTDOWN();
		}catch(Exception e){
		    ui.appendMsg("Loading file ("+file.getName()+") has failed with UNKNOWN reason:"+
				 e.toString());
		    ui.setSHUTDOWN();
		}
		
	    });
	shell.updateHandler(AltType.FileDelete, (File file)->{
		try{
		    ui.setSYNCHRONIZING();
		    Deletefilerequester dfq = new Deletefilerequester(file.getName()); 
		    dfq.sendrequest(); 
		    if(dfq.getstatus()){
			ui.appendMsg("File "+file.getName()+" has been removed");
		    }else{
			ui.appendMsg("Removing has failed");
		    }
		    // System.out.println("[new file] succ = " + dfq.getstatus()); 
		    // System.out.println("[new file] errorcode = " + dfq.geterrorcode());
		    ui.setSYNCHRONIZED();
		}catch(Exception e){
		    ui.appendMsg("Delete file ("+file.getName()+") has failed with UNKNOWN reason:"+
				 e.toString());
		    ui.setSHUTDOWN();
		}

	    });
	shell.updateHandler(AltType.FileChange, (File file)->{
		ui.appendMsg(file.getAbsoluteFile() + " was modified.");
	    });
    };

    private boolean tokenCheck(){
	try{
	    Tokenverifier tv = new Tokenverifier();
	    tv.verifytoken();
	    return tv.gettokenverifyresult() && tv.gettokenverifyresult();
	}catch(Exception e){}
	return false;
    }
}

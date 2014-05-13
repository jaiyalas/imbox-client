package org.imbox.client.synchronize;

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

public class Synker{

    private UImanager ui;
    private LogWriter logwriter;

    public Synker(UImanager _ui,LogWriter _log ){
	this.ui = _ui;
	logwriter = _log;
    }

    public void sync(){

	ui.setSYNCHRONIZING();

	List<FileRec> ofrs = logwriter.readLog();
	List<FileRec> cfrs = LogUtils.getCurrentShapshot();
	List<FileRecH> frhs = LogUtils.zip(ofrs,cfrs);	

	try{
	    Syncrequester syncer = new Syncrequester();
	    syncer.sendrequest();
	    if(syncer.getstatus()){
		List<FileRecH> remote_frhs = syncer.getfilelist();
		// for (FileRecH frh : remote_frhs)
		// ui.appendMsg("[REMOTE]"+frh.getName()+
		// 		 ":["+frh.getPastMD55()+
		// 		 "->"+frh.getCurrentMD5()+"]");

		for (FileRecH frh : remote_frhs){
		    if(frh.getCurrentMD5().equalsIgnoreCase("ffffffffffffffffffffffffffffffff")){
			int i = remote_frhs.indexOf(frh);
			remote_frhs.remove(i);

			File f = new File(Workspace.SYSDIRc+frh.getName());
			if(f.exists() && !f.isDirectory()){
			    FileUtils.deleteQuietly(f);
			    ui.appendMsg("Remove file: "+frh.getName());
			}
		    }
		}

		List<FileRecH> push = LogUtils.getPushNewFileList
		    (new Vector<FileRecH>(frhs),
		     new Vector<FileRecH>(remote_frhs));
		

		// for (FileRecH frh : push)
		// ui.appendMsg("[PUSH]"+frh.getName()+
		// 		 ":["+frh.getPastMD55()+
		// 		 "->"+frh.getCurrentMD5()+"]");
		
		List<FileRecH> pull = LogUtils.getPullNewFileList
		    (new Vector<FileRecH>(frhs),
		     new Vector<FileRecH>(remote_frhs));
		for (FileRecH frh : pull){
		    Blockrecordgetter brg = new Blockrecordgetter(frh.getName()); 
		    brg.sendrequest();
		    if(brg.getstatus()){
			List<BlockRec> blocklist = brg.getblocklist(); 
			List<Block> bs = new Vector<Block>();
			boolean b = true;
			int i = 0;
			for (BlockRec a: blocklist){
			    Blockgetter bg = new Blockgetter(a.getName(), i); 
			    i += 1;
			    bg.sendrequest();
			    if(bg.getstatus()){
				bs.add(bg.getresultblock());
			    };
			}
			FileHandler.writeFileFromBlocks(bs,Workspace.SYSDIRc,frh.getName());
			ui.appendMsg("[PULL]"+frh.getName()+
				     ":["+frh.getPastMD55()+
				     "->"+frh.getCurrentMD5()+"]");    		
		    }
		    
		}
	    }
	}catch(Exception e){}
	
	logwriter.refreshLog();
	
	ui.setSYNCHRONIZED();

    }

 };

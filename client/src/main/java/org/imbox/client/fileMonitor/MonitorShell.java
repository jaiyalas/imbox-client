package org.imbox.client.fileMonitor;

import java.io.File;
import java.util.function.*;
import org.apache.commons.io.monitor.*;


import org.imbox.client.Workspace; //{FSEP, HOME}
import org.imbox.client.UImanager;

import org.imbox.client.fileMonitor.*;

// this is a client only module
public class MonitorShell{

    private File workspace;
    private FileAlterationObserver fao;
    private FileAlterationMonitor monitor;
    private FileEventHandler handler;
    
    private UImanager ui;
    //private LogWriter logwriter;
    //private Synker synker;

    //public MonitorShell(UIManager _ui, LogWriter _logwriter, Synker _synker){
    public MonitorShell(File _workspace, UImanager _ui){					     
	this.ui        = _ui;
	//this.logwriter = _logwriter;
	//this.synker    = _synker;
	
	workspace = _workspace;
	ui.appendMsg(workspace.toString());
	fao = new FileAlterationObserver(workspace);
	handler = new FileEventHandler();
	fao.addListener(handler);
	monitor = new FileAlterationMonitor(1 * 1000);
	monitor.addObserver(fao);

	Consumer<File> f0 = (File f) -> {ui.appendMsg(f.getAbsoluteFile() + " was created.");};
	Consumer<File> f1 = (File f) -> {ui.appendMsg(f.getAbsoluteFile() + " was deleted.");};
	Consumer<File> f2 = (File f) -> {ui.appendMsg(f.getAbsoluteFile() + " was changed.");};
	
	handler.updateHandler(AltType.FileCreate, f0);
	handler.updateHandler(AltType.DireCreate, f0);
	handler.updateHandler(AltType.FileDelete, f1);
	handler.updateHandler(AltType.DireDelete, f1);
	handler.updateHandler(AltType.FileChange, f2);
	handler.updateHandler(AltType.DireChange, f2);

    }
    public void start() throws Exception{
	monitor.start();
    }

};




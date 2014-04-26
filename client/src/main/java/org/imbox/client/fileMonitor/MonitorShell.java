package org.imbox.client.fileMonitor;

import java.io.File;
import java.util.function.*;
import org.apache.commons.io.monitor.*;

import org.imbox.client.*;
import org.imbox.client.fileMonitor.*;
import org.imbox.client.synchronize.*;

import org.imbox.infrastructure.*;
import org.imbox.infrastructure.log.*;

// this is a client only module
public class MonitorShell{

    private File      workspace;
    private UImanager ui;
    private LogWriter logwriter;
    private Synker    synker;
    
    private FileAlterationObserver fao;
    private FileAlterationMonitor  monitor;
    private FileEventHandler       handler;

    public MonitorShell(File _workspace, 
			UImanager _ui, 
			LogWriter _logwriter, 
			Synker _synker){					     
	this.workspace = _workspace;
	this.ui        = _ui;
	this.logwriter = _logwriter;
	this.synker    = _synker;
	
	handler = new FileEventHandler();
	fao = new FileAlterationObserver(workspace);
	monitor = new FileAlterationMonitor(Const.monitorPeriod * 1000);
	
	handlersInitial();

	fao.addListener(handler);
	monitor.addObserver(fao);
    };

    private void handlersInitial(){
	Consumer<File> fun_newF = (File file) -> {
	    //logwriter.dosomething();
	    //synker.dosomething();
	    ui.appendMsg(file.getAbsoluteFile() + " was created.");
	};
	Consumer<File> fun_newD = (File file) -> {
	    //logwriter.dosomething();
	    //synker.dosomething();
	    ui.appendMsg(file.getAbsoluteFile() + " was deleted.");
	};
	Consumer<File> fun_delF = (File file) -> {
	    //logwriter.dosomething();
	    //synker.dosomething();
	    ui.appendMsg(file.getAbsoluteFile() + " was changed.");
	};
	Consumer<File> fun_delD = (File file) -> {
	    //logwriter.dosomething();
	    //synker.dosomething();
	    ui.appendMsg(file.getAbsoluteFile() + " was changed.");
	};
	Consumer<File> fun_chgF = (File file) -> {
	    //logwriter.dosomething();
	    //synker.dosomething();
	    ui.appendMsg(file.getAbsoluteFile() + " was changed.");
	};
	Consumer<File> fun_chgD = (File file) -> {
	    //logwriter.dosomething();
	    //synker.dosomething();
	    ui.appendMsg(file.getAbsoluteFile() + " was changed.");
	};
	
	handler.updateHandler(AltType.FileCreate, fun_newF);
	handler.updateHandler(AltType.DireCreate, fun_newD);
	handler.updateHandler(AltType.FileDelete, fun_delF);
	handler.updateHandler(AltType.DireDelete, fun_delD);
	handler.updateHandler(AltType.FileChange, fun_chgF);
	handler.updateHandler(AltType.DireChange, fun_chgD);
    };

    public void updateHandler(AltType _at, Consumer<File> _fun){
	handler.updateHandler(_at, _fun);
    };

    public void start() throws Exception{monitor.start();};

};




package org.imbox.client.fileMonitor;

import java.io.File;
import java.util.function.Consumer;

import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.commons.io.monitor.FileAlterationMonitor;

import org.imbox.infrastructure.Const;
import org.imbox.client.fileMonitor.AltType;
import org.imbox.client.fileMonitor.FileEventHandler;

public class MonitorShell{
    private File      workspace;
    private FileAlterationObserver fao;
    private FileAlterationMonitor  monitor;
    private FileEventHandler       eventHandler;

    public MonitorShell(File _workspace){ 
	this.workspace = _workspace;
	
	eventHandler = new FileEventHandler();
	fao = new FileAlterationObserver(workspace);
	monitor = new FileAlterationMonitor(Const.monitorPeriod * 1000);
	
	handlersInitial();

	fao.addListener(eventHandler);
	monitor.addObserver(fao);
    };

    private void handlersInitial(){
	Consumer<File> emptyFun = (File f) -> {};
	
	eventHandler.updateHandler(AltType.FileCreate, emptyFun);
	eventHandler.updateHandler(AltType.DireCreate, emptyFun);
	eventHandler.updateHandler(AltType.FileDelete, emptyFun);
	eventHandler.updateHandler(AltType.DireDelete, emptyFun);
	eventHandler.updateHandler(AltType.FileChange, emptyFun);
	eventHandler.updateHandler(AltType.DireChange, emptyFun);
    };

    public void updateHandler(AltType _at, Consumer<File> _fun){
	eventHandler.updateHandler(_at, _fun);
    };

    public void start()throws Exception{monitor.start();};

};




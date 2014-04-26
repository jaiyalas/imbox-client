package org.imbox.infrastructure.monitor;

import java.io.File;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import org.imbox.client.Workspace; //{FSEP, HOME}

public class MonitorShell{

    private File workspace;
    private FileAlterationObserver fao;
    private FileAlterationMonitor monitor;

    private FileAlterationListener listener;
    
    private UIManager ui;
    private LogWriter logwriter;
    private Synker synker;

    public MonitorShell(){
	workspace = new File(Workspace.HOME);
	fao = new FileAlterationObserver(workspace);
	
	fao.addListener(new FileEventHandler());
	monitor = new FileAlterationMonitor(10 * 1000);
	monitor.addObserver(fao);
	monitor.start(); //folk

	

    }


};




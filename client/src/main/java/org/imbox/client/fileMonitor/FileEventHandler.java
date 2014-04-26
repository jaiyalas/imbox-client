package org.imbox.client.fileMonitor;

import java.io.File;
import java.util.function.Consumer;

import org.apache.commons.io.monitor.*;

import org.imbox.client.*;
import org.imbox.client.fileMonitor.*;

public class FileEventHandler implements FileAlterationListener{

    private Consumer<File> newFileHandler;
    private Consumer<File> delFileHandler;
    private Consumer<File> chgFileHandler;
    private Consumer<File> newDireHandler;
    private Consumer<File> delDireHandler;
    private Consumer<File> chgDireHandler;

    public FileEventHandler(){ 
	newFileHandler = (file) -> {file.toString();};
	delFileHandler = (file) -> {file.toString();};
	chgFileHandler = (file) -> {file.toString();};
	newDireHandler = (file) -> {file.toString();};
	delDireHandler = (file) -> {file.toString();};
	chgDireHandler = (file) -> {file.toString();};
    };
 
    public void onDirectoryCreate(final File dir)
    {newDireHandler.accept(dir);};
    public void onDirectoryChange(final File dir)
    {chgDireHandler.accept(dir);};
    public void onDirectoryDelete(final File dir)
    {delDireHandler.accept(dir);};
    public void onFileCreate(final File file)
    {newFileHandler.accept(file);};
    public void onFileChange(final File file)
    {chgDireHandler.accept(file);};
    public void onFileDelete(final File file)
    {delDireHandler.accept(file);};

    public void updateHandler(AltType at, Consumer<File> fun){
	switch(at){
	case DireCreate: newDireHandler = fun;break;
	case DireChange: chgDireHandler = fun;break;
	case DireDelete: delDireHandler = fun;break;
	case FileCreate: newFileHandler = fun;break;
	case FileChange: chgFileHandler = fun;break;
	case FileDelete: delFileHandler = fun;break;
	}; 
    };

    public void onStart(final FileAlterationObserver observer) {};
    public void onStop(final FileAlterationObserver observer) {};

};


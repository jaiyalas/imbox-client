package org.imbox.client.log;

import java.io.*;
import java.util.*;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import org.imbox.client.log.*;

import org.imbox.infrastructure.*;
import org.imbox.infrastructure.file.*;

public class LogWriter{
    private File logfile;
    private ObjectOutputStream objectOut;
    private ObjectInputStream objectIn;

    private List<FileRec> fs;

    public LogWriter(){
	logfile = new File(Workspace.HOME+"_imbox");	
	fs = new Vector<FileRec>();
    }

    public void refreshLog(){
	try{
	    objectOut = new ObjectOutputStream
		(new FileOutputStream(logfile));
	    objectOut.writeObject(LogUtils.getCurrentShapshot()); 
	    objectOut.flush();
	    objectOut.close(); // Close the output stream	
	    fs.clear();
	}catch(Exception e){}
    } 

    public List<FileRec> readLog(){
	List<FileRec> fsin = new Vector<FileRec>();
	try{
	    objectIn = new ObjectInputStream
		(new FileInputStream(logfile));
	    
	    fsin = (List<FileRec>) objectIn.readObject();
	    
	    objectIn.close();
	    
	    return fsin;
	}catch(Exception e){
	    System.out.println(e.toString());
	}
	return fsin;
    } 
};

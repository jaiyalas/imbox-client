package org.imbox.client;

import java.io.*;

import org.imbox.infrastructure.exceptions.*;

public class Workspace{
    private static String FSEP; 
    private static String HOME; 

    static{
	FSEP = System.getProperty("file.separator").toLowerCase();
	HOME    = System.getProperty("user.home").toLowerCase();
    };

    public static boolean prepareWorkspace()throws IMBOX_DirConfException, 
						   IMBOX_MkdirFailException{
	File workspace = new File(HOME+FSEP+"imbox");
	if(workspace.exists()){
	    if(!workspace.isDirectory()){
		throw new IMBOX_DirConfException("The path \""+HOME+FSEP+
						 "imbox\" exists but is not "+
						 "a directory");}
	}else{ // workspace doesn't exists
	    if(!workspace.mkdir()){
		throw new IMBOX_MkdirFailException("mkdir \""+HOME+FSEP+
						   "imbox\" failed");}
	}
	return true; 
    };

}; 

package org.imbox.infrastructure;

import java.io.*;

import org.imbox.infrastructure.exceptions.*;

public class Workspace{
    public static String FSEP; 
    public static String HOME; 
    public static String SYSDIRc;
    public static String SYSDIRs;
    static{
	FSEP    = System.getProperty("file.separator").toLowerCase();
	HOME    = System.getProperty("user.home").toLowerCase()+FSEP;
	SYSDIRc = HOME+"imbox"+FSEP;
	SYSDIRs = HOME+"blockyard"+FSEP;
    };

    public static File prepareWorkspace(String _sysdir)throws IMBOX_DirConfException, 
							      IMBOX_MkdirFailException{
	File workspace = new File(_sysdir);
	if(workspace.exists()){
	    if(!workspace.isDirectory()){
		throw new IMBOX_DirConfException("The path \""+_sysdir+
						 "\" exists but is not "+
						 "a directory");}
	}else{ // workspace doesn't exists
	    if(!workspace.mkdir()){
		throw new IMBOX_MkdirFailException("mkdir \""+_sysdir+
						   "\" failed");}
	}
	return workspace; 
    };

    public static File prepareWorkspaceC()throws IMBOX_DirConfException, 
						 IMBOX_MkdirFailException{
	return prepareWorkspace(SYSDIRc); 
    };
    public static File prepareWorkspaceS()throws IMBOX_DirConfException, 
						 IMBOX_MkdirFailException{
	return prepareWorkspace(SYSDIRs); 
    };

}; 

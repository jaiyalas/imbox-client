package org.imbox.client.log;

import java.io.*;
import java.util.*;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import org.imbox.infrastructure.*;
import org.imbox.infrastructure.file.*;

public class LogUtils{
    public LogUtils(){}

    public static List<FileRec> getCurrentShapshot(){
	List<FileRec> temp_fs = new Vector<FileRec>();
	try{
	    File dir = new File(Workspace.SYSDIRc);
	    List<File> files = (List<File>) FileUtils.listFiles
		(dir, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
	    for (File file : files) {
		InputStream is    = new FileInputStream(file);
		byte[]      bytes = IOUtils.toByteArray(is);
		is.close();
		temp_fs.add(new FileRec
			    (file.getName(),Hash.getMD5String(bytes)));
	    }
	    Collections.sort(temp_fs,(FileRec  b0, FileRec b1) -> {
		    return b0.getName().compareToIgnoreCase(b1.getName());});
	    return temp_fs;
	}catch(Exception e){}
	return temp_fs;
    }

    public static List<FileRecH> zip(List<FileRec> ofrs,List<FileRec> cfrs){
	List<FileRecH> frhs = new Vector<FileRecH>();
	
	ofrs.forEach(ofr -> {
		int i = -1;
		String cmd5 = "";
		for(FileRec cfr : cfrs){
		    if(cfr.getName().equals(ofr.getName())){
			i = cfrs.indexOf(cfr);
			cmd5 = cfr.getMd5();
			cfrs.remove(i);
			break;
		    }
		}
		if(i != -1){
		    frhs.add(new FileRecH(ofr.getName(),cmd5,ofr.getMd5()));
		}else{
		    frhs.add(new FileRecH(ofr.getName(),ofr.getMd5(),ofr.getMd5()));
		}
	    });
	cfrs.forEach(cfr -> {
		frhs.add(new FileRecH(cfr.getName(),cfr.getMd5(),cfr.getMd5()));
	    });
	return frhs;
    }

    public static List<FileRecH> getPushNewFileList
	(List<FileRecH> loacl,List<FileRecH> remote){
	List<FileRecH> re = new Vector<FileRecH>();
	for(FileRecH frh : loacl){
	    boolean b = true;
	    for(FileRecH rfrh : remote){
		if(frh.getName().equals(rfrh.getName())){b = false;}
	    }
	    if(b){re.add(frh);}
	}
	return re;
    };
    public static List<FileRecH> getPullNewFileList
	(List<FileRecH> loacl,List<FileRecH> remote){
	List<FileRecH> re = new Vector<FileRecH>();
	for(FileRecH rfrh : remote){
	    boolean b = true;
	    for(FileRecH frh : loacl){
		if(rfrh.getName().equals(frh.getName())){b = false;}
	    }
	    if(b){re.add(rfrh);}
	}
	return re;
    };
};

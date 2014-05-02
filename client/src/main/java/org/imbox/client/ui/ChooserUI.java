package org.imbox.client.ui;

import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.File;
import java.awt.HeadlessException;

import org.imbox.infrastructure.*;

public class ChooserUI {
    private String path;
    private String fname;
    private String URL;
    public ChooserUI(){path = Workspace.SYSDIRc;}
    public String getFileName(){return fname;}
    public String getURL(){return URL;}
    public void ChooseFile() throws HeadlessException{
	try{
	    JFileChooser chooser = new JFileChooser(path);
	    chooser.setMultiSelectionEnabled(false);
	    chooser.setApproveButtonText("Select");
	    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	    chooser.setFileView(new FileView() {
		    @Override
		    public Boolean isTraversable(File f) {
			return (f.isDirectory() && f.getName().equals(path));
		    }
		});
	    int returnVal = chooser.showOpenDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
		
		/** connecting to server here! **/
		this.fname = chooser.getSelectedFile().getName();
	    }
	}catch(IllegalArgumentException ignored){}
    }
    public void Message(){}
}

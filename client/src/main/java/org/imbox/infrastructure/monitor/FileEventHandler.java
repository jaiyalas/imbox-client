package org.imbox.infrastructure.monitor;

import java.io.File;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

public class FileEventHandler implements FileAlterationListener{
    public void onStart(final FileAlterationObserver observer) {
        //
    };
 
    public void onDirectoryCreate(final File directory) {
	//
    };
 
    public void onDirectoryChange(final File directory) {
        //
    };
 
    public void onDirectoryDelete(final File directory) {
        //
    };
 
    public void onFileCreate(final File file) {
        //
    };

    public void onFileChange(final File file) {
        //
    };
 
    public void onFileDelete(final File file) {
        //
    };
 
    public void onStop(final FileAlterationObserver observer) {
        //
    };
};




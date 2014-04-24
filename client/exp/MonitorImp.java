import java.io.File;
import java.util.Date;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;

public class MonitorImp implements FileAlterationListener{
 
    public void onStart(final FileAlterationObserver observer) {
        System.out.println("The WindowsFileListener has STARTed on " + observer.getDirectory().getAbsolutePath());
    }
 
    public void onDirectoryCreate(final File directory) {
        System.out.println(directory.getAbsolutePath() + " was created.");
    }
 
    public void onDirectoryChange(final File directory) {
        System.out.println(directory.getAbsolutePath() + " was modified");
    }
 
    public void onDirectoryDelete(final File directory) {
        System.out.println(directory.getAbsolutePath() + " was deleted.");
    }
 
    public void onFileCreate(final File file) {
        System.out.println(file.getAbsoluteFile() + " was created.");
        System.out.println("----------> length: " + file.length());
        System.out.println("----------> last modified: " + new Date(file.lastModified()));
        System.out.println("----------> readable: " + file.canRead());
        System.out.println("----------> writable: " + file.canWrite());
        System.out.println("----------> executable: " + file.canExecute());
    }

    public void onFileChange(final File file) {
        System.out.println(file.getAbsoluteFile() + " was modified.");
        System.out.println("----------> length: " + file.length());
        System.out.println("----------> last modified: " + new Date(file.lastModified()));
        System.out.println("----------> readable: " + file.canRead());
        System.out.println("----------> writable: " + file.canWrite());
        System.out.println("----------> executable: " + file.canExecute());
    }
 
    public void onFileDelete(final File file) {
        System.out.println(file.getAbsoluteFile() + " was deleted.");
    }
 
    public void onStop(final FileAlterationObserver observer) {
        System.out.println("The WindowsFileListener has STOPped on " + observer.getDirectory().getAbsolutePath());
    }
}

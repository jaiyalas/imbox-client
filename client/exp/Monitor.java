import java.io.File;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;


import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.InetAddress;


public class Monitor {
 
    public static void main(String[] args) throws Exception {
 
	try {
	    InetAddress ip = InetAddress.getLocalHost();
	    System.out.println("Current IP address : " + ip.getHostAddress());
		
	    NetworkInterface network = NetworkInterface.getByInetAddress(ip);	
	    byte[] mac = network.getHardwareAddress();
	    System.out.print("Current MAC address : ");
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < mac.length; i++) {
		sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? ":" : ""));		
	    }
	    System.out.println(sb.toString());
	} catch (SocketException e){e.printStackTrace();}





        // Change this to match the environment you want to watch.
        final File directory = 
	    new File("/Users/jaiyalas/project/ntu/imbox/client/exp/files");
        
	FileAlterationObserver fao = new FileAlterationObserver(directory);
        
	fao.addListener(new MonitorImp());
        
	final FileAlterationMonitor monitor = new FileAlterationMonitor(10 * 1000);
        
	monitor.addObserver(fao);
        
	System.out.println("Starting monitor. CTRL+C to stop.");
        monitor.start();
 
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() { try {
                System.out.println("Stopping monitor.");
                monitor.stop();
		} catch(Exception ignored){}};
        }));
    }
}

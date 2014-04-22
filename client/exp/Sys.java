
import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.monitor.FileEntry;

import java.io.*;

import javax.swing.*;

public class Sys{

    private static String FSEP; 
    private static String HOME; 

    static{
	FSEP = System.getProperty("file.separator").toLowerCase();
	HOME    = System.getProperty("user.home").toLowerCase();
    };

    public static void main(String[] args) throws IOException{
	System.out.println(FSEP);
	System.out.println(HOME);

	File dir = new File(HOME+FSEP+"imbox");
	System.out.println(".....");
	System.out.println(HOME+FSEP+"imbox");
	System.out.println(dir.exists());
	System.out.println(dir.isDirectory());






	JTextArea textArea = new JTextArea();
	textArea.setColumns(20);
        textArea.setLineWrap(true);
        textArea.setRows(5);
        textArea.setWrapStyleWord(true);
         
        JScrollPane jScrollPane1 = new JScrollPane(textArea);


	JFrame jf = new JFrame("TEST");
	jf.setSize(500,500);
	jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	jf.getContentPane().add(jScrollPane1);
	jf.pack();

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run(){ jf.setVisible(true); }
        });

    };
}

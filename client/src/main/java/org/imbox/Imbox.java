package org.imbox;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils; 

import org.imbox.ui.*;
import org.imbox.infrastructure.*;
import org.imbox.infrastructure.file.*;

public class Imbox {
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("FrameDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel emptyLabel = new JLabel("");
        emptyLabel.setPreferredSize(new Dimension(175, 100));
        frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
    public static void main(String[] args) throws IOException {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.

        String string = "This is\na test";
	File file = new File("/Users/jaiyalas/test.txt");
        FileUtils.writeStringToFile(file, string);

        new Thread(
          () -> System.out.println("hello world")
        ).start();

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run(){createAndShowGUI();}
        });
    }
}

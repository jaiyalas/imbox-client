package org.imbox.uimgt;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;


public class UImanager
{
	private JFrame mainframe;
	private JLabel text;
	public UImanager()
	{
		mainframe = new JFrame();
		mainframe.setSize(300, 400);
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		text = new JLabel("Welcome to IMBOX!");
		mainframe.getContentPane().add(BorderLayout.CENTER,text);
	}
	
	public UImanager(int width,int height)
	{
		mainframe = new JFrame();
		mainframe.setSize(width, height);
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		text = new JLabel("Welcome to IMBOX!");
		mainframe.getContentPane().add(BorderLayout.CENTER,text);
	}
	
	public UImanager(String content)
	{
		mainframe = new JFrame();
		mainframe.setSize(300, 400);
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		text = new JLabel(content);
		mainframe.getContentPane().add(BorderLayout.CENTER,text);
	}
	
	public UImanager(int width,int height,String content)
	{
		mainframe = new JFrame();
		mainframe.setSize(width, height);
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		text = new JLabel(content);
		mainframe.getContentPane().add(BorderLayout.CENTER,text);
	}
	
	public void show()
	{
		mainframe.setVisible(true);
	}
	
	public void hide()
	{
		if (mainframe.isVisible())
		{
			mainframe.setVisible(false);
		}
	}
	
	public void settext(String content)
	{
		text.setText(content);
	}
	
	public void settimertext(final String content, int timelength)
	{
		Timer timer = new Timer(timelength, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent ae) {
				text.setText(content);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}

}

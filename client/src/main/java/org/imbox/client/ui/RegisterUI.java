package org.imbox.client.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegisterUI extends JFrame {  
    private JFrame frame; 
    private JTextField acc_input,pwd_input,repwd_input;
    private JLabel message;
    private JPanel mainPanel,accPanel,pwdPanel,repwdPanel,b_pane;
    private JButton jbkOK,jbkReset;
    private String account,password;
    private static final long serialVersionUID = 1L; 
    public RegisterUI() {
        frame = new JFrame();               
	accPanel = new JPanel();
        pwdPanel = new JPanel();            
	repwdPanel = new JPanel();
        acc_input = new JTextField(15);     
	pwd_input = new JTextField(15);
        repwd_input = new JTextField(15);   
	b_pane = new JPanel();
        message = new JLabel("");           
	jbkOK = new JButton("OK");
        jbkReset = new JButton("Reset");
    }
    public String getAcc(){
    	return account;
    }
    public String getPwd(){
    	return password;
    }
    public void show_UI(){  
    	frame.setSize(250, 450);
    	frame.setLocationRelativeTo(null); 
        mainPanel = new JPanel();  
        mainPanel.setBorder(BorderFactory.createTitledBorder("imbox register")); 
        GridBagLayout gl = new GridBagLayout();
        mainPanel.setLayout(gl);
        //image
        ImageIcon reIcon = new ImageIcon("group.png");
        JLabel lblShowImg = new JLabel(reIcon);
        GridBagConstraints pic = new GridBagConstraints();
        pic.gridx = 0;          pic.gridy = 0;
        pic.gridwidth = 4;      pic.gridheight = 2;
        pic.weightx = 0;        pic.weighty = 0;
        mainPanel.add(lblShowImg,pic);
        //message
        message.setForeground(Color.RED);
        GridBagConstraints m = new GridBagConstraints();
        m.gridx = 0;           m.gridy = 2;
        m.gridwidth = 4;       m.gridheight = 1;
        m.weightx = 0;         m.weighty = 0;
        m.insets = new Insets(5,0,10,0);
        mainPanel.add(message,m);
        //account input
        accPanel.setBorder(BorderFactory.createTitledBorder("input account"));
        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridx = 0;        c1.gridy = 3;
        c1.gridwidth = 4;    c1.gridheight = 1;
        c1.weightx = 0;      c1.weighty = 0;
        c1.fill = GridBagConstraints.BOTH;
        acc_input.setOpaque(false);
        acc_input.setBorder(BorderFactory.createEmptyBorder());
        accPanel.add(acc_input);
        mainPanel.add(accPanel,c1);
        //password input
        pwdPanel.setBorder(BorderFactory.createTitledBorder("input password"));
        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridx = 0;            c2.gridy = 4;
        c2.gridwidth = 4;        c2.gridheight = 1;
        c2.weightx = 0;          c2.weighty = 0;
        c2.fill = GridBagConstraints.BOTH;
        pwd_input.setOpaque(false);
        pwd_input.setBorder(BorderFactory.createEmptyBorder());
        pwdPanel.add(pwd_input);
        mainPanel.add(pwdPanel,c2);
        //confirm password
        repwdPanel.setBorder(BorderFactory.createTitledBorder("confirm password"));
        GridBagConstraints c3 = new GridBagConstraints();
        c3.gridx = 0;         c3.gridy = 5;
        c3.gridwidth = 4;     c3.gridheight = 1;
        c3.weightx = 0;       c3.weighty = 0;
        c3.fill = GridBagConstraints.BOTH;
        c3.insets = new Insets(0,0,20,0);
        repwd_input.setBorder(BorderFactory.createEmptyBorder());
        repwd_input.setOpaque(false);
        repwdPanel.add(repwd_input);
        mainPanel.add(repwdPanel,c3);

        buttonEvent();  //call button Event
        b_pane.setLayout(new GridLayout(1,2,20,20));
        GridBagConstraints b = new GridBagConstraints();
        b.gridx = 0;             b.gridy = 6;
        b.gridwidth = 4;         b.gridheight = 1;
        b.weightx = 0;           b.weighty = 0;
        b.fill = GridBagConstraints.BOTH;
        b_pane.add(jbkOK);
        b_pane.add(jbkReset);
        mainPanel.add(b_pane,b);
        //main panel
        frame.add(mainPanel);
        frame.setVisible(true);
    }
    public void buttonEvent(){
        jbkOK.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(pwd_input.getText().equals(repwd_input.getText())){
		    message.setText("successful!");
		    account=String.valueOf(acc_input.getText());
		    password=String.valueOf(pwd_input.getText());
		    frame.setVisible(false);
                }else{
		    message.setText("password not the same");
		    pwd_input.setText("");
                    repwd_input.setText("");
                }
            }
        });
        //button Reset event
        jbkReset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                acc_input.setText("");
                pwd_input.setText("");
                repwd_input.setText("");
            }
        });
    }
}  

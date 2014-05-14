import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Reg {
    private JDialog        jdialog;

    private JTextField     acc_input;
    private JPasswordField pwd_input;
    private JPasswordField repwd_input;

    private JLabel message;
    private JPanel mainPanel;
    private JPanel accPanel;
    private JPanel pwdPanel;
    private JPanel repwdPanel;
    private JPanel b_pane;

    private JButton jbkOK;
    private JButton jbkReset;

    private String account;
    private String password;
    public String getAcc(){return account;}
    public String getPwd(){return password;}

    public Reg(Frame owner){
    	jdialog     = new JDialog(owner,"New Account Register",true);
    	accPanel    = new JPanel();
        pwdPanel    = new JPanel();            
        repwdPanel  = new JPanel();
        acc_input   = new JTextField(15);     
        pwd_input   = new JPasswordField(15);
        repwd_input = new JPasswordField(15);   
        b_pane      = new JPanel();
        message     = new JLabel("");           
        jbkOK       = new JButton("OK");
        jbkReset    = new JButton("Reset");

     	jdialog.setSize(250,450);
    	jdialog.setLocationRelativeTo(null); 
 
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

	//setup action listener 
	buttonEvent();  
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
        jdialog.add(mainPanel);
        jdialog.setVisible(false);
    }
    public void show(){
	jdialog.setVisible(true);
    }
    public void buttonEvent(){
        jbkOK.addActionListener
	    ((ae)->{
		String passString = new String(pwd_input.getPassword()); 
            	String repassString = new String(repwd_input.getPassword()); 
            	if(acc_input.getText().trim().length() == 0){
            		message.setText("you should input account");
                }
            	else if(passString.trim().length() == 0  || repassString.trim().length() == 0){
            		message.setText("you should input password");
                }
            	else if(passString.equals(repassString)){
                	message.setText("successful!");
                	account=String.valueOf(acc_input.getText());
                	password=String.valueOf(pwd_input.getPassword());
                	jdialog.dispose();
                }
                else{
                	message.setText("password not the same");
                	pwd_input.setText("");
                    repwd_input.setText("");
                }
	    });
        jbkReset.addActionListener
	    ((ae)->{
		acc_input.setText("");
                pwd_input.setText("");
                repwd_input.setText("");
	    });
    }
}

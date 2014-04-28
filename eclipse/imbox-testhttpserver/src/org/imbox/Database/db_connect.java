package org.imbox.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class db_connect {
	protected static Connection connect = null;
	String driver = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/group2_db";  //�s���Ӹ�Ʈw
    String user = "root";              //mysql�b��
    String password = "cindyboy";      //mysql�K�X
    
	public db_connect(){ 
	    try { 
	      Class.forName(driver); 
	      //���Udriver 
	      connect = DriverManager.getConnection(url,user,password); 
	      //���oconnection
	      if(!connect.isClosed())
	          System.out.println("successfuly connected t0 DB"); 
	      //connect.close();
	      //System.out.println("��Ʈw�������\"); 
	    } 
	    catch(ClassNotFoundException e) 
	    { 
	      System.out.println("cannot find driver type:"+e.toString()); 
	    }//���i��|����sqlexception 
	    catch(SQLException e) { 
	      System.out.println("exception:"+e.toString()); 
	    } 
	} 
	public static void main(String[] args) {
        new db_connect();
	}

}

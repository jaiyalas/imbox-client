package org.imbox.database;
import java.sql.*;
public class db_connect {
    private static String driver   = "com.mysql.jdbc.Driver";
    private static String url      = "jdbc:mysql://localhost:3306/";
    protected static String dbname   = "imbox";
    private static String user     = "root";
    private static String password = "ted910905";
    protected static Connection connect;
    static{
	try{
	Class.forName(driver);
	connect = DriverManager.getConnection(url+dbname,user,password);
	}catch(ClassNotFoundException | SQLException e){
	    throw new ExceptionInInitializerError(e);
	}
    };
    public db_connect(){ };
    
}

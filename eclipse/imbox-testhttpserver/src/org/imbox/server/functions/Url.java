package org.imbox.server.functions;
import java.sql.ResultSet;
import java.sql.Statement;

import org.imbox.Database.db_connect;


public class Url extends db_connect{
	static String urlname ;
	static String domain = "127.0.0.1";
	public Url(String account,String filename){
		try{
			
		Statement stmt = connect.createStatement();	
		String md5 = "SELECT f_MD5 FROM " +account+ " WHERE fileName = '"+filename+"'";
		ResultSet md5_result = stmt.executeQuery(md5);
		md5_result.next();
		String md5name = md5_result.getString(1);
		urlname = "http://"+domain+"/generatefile" + "."+md5name+ "."+filename; 
		System.out.println(urlname);
		}catch(Exception e){
		System.out.println("exception:"+e.toString()); 
	    }
	}
	
	public String geturlname()
	{
		return urlname;
	}

	
//public static void main(String[] args) {
//	Scanner input = new Scanner(System.in);
//	String account = input.next();
//	String filename = input.next();
//	//String account = args[0];
//    //String filename = args[1];	
//	new Url(account,filename);
//	}
     
	}

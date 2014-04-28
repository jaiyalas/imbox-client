package db_query;
import java.sql.ResultSet;
import java.sql.Statement;
//import java.util.Scanner;

public class Modify_File extends db_connect{
	String acc,old_FID,new_FID,newName,new_MD5;
	//給定參數(帳號，舊的檔案ID，新的檔案ID，新的檔案名，新的檔案MD5)
    public Modify_File(String acc,String old_FID,String new_FID,String newName,String new_MD5){
    	this.acc = acc;            this.old_FID = old_FID;    	
    	this.new_FID = new_FID;    this.newName=newName;     this.new_MD5 = new_MD5;
    	try{  
    		//取出舊的MD5
    		String oldMD5 = "SELECT f_MD5 FROM "+acc+" WHERE FID = '"+old_FID+"'";
    		Statement stmt = connect.createStatement();
			ResultSet oldMD5_result = stmt.executeQuery(oldMD5);
			if(oldMD5_result.next()){ 
				String old_MD5 = oldMD5_result.getString("f_MD5");
	    		//刪掉舊的檔案
	    		new Delete_File_user(acc,old_FID);
				//換成新的檔案
	    		new Insert_File(acc,newName,new_FID,new_MD5);
	    		//原本的MD5放到antedent_MD5
	    		String newMD5 = "UPDATE "+acc+" SET antedent_f_MD5='"+old_MD5+"' WHERE FID = '"+new_FID+"'";
	    		stmt.executeUpdate(newMD5);
			}
			else{
				System.out.println("伺服端無此檔案");
			}
			connect.close();  //關閉資料庫
    	}catch(Exception e){
			System.out.println("例外:"+e.toString()); 
		}
    }
	public static void main(String[] args) {
		/*System.out.println("給定五參數：使用者帳號    舊的檔案ID  新的檔案ID  新的檔案名   新的檔案MD5");
		Scanner input = new Scanner(System.in);
		String acc = input.next();
		String old_FID = input.next();      String new_FID = input.next();
		String newName = input.next();  	String new_MD5 = input.next();
		new Modify_File(acc,old_FID,new_FID,newName,new_MD5);
		input.close();*/
	}

}

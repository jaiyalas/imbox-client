package org.imbox.database;
import java.sql.ResultSet;
import java.sql.Statement;
//import java.util.Scanner;

public class Delete_File_user extends db_connect{
	private String acc,MD5;
	private boolean userFileDelete;
	//input:account, file_MD5
    public Delete_File_user(String acc,String MD5){
        this.MD5 = MD5; this.acc = acc;
    }
    public void setFileDelete(boolean userFileDelete){    //set if user has file for delete
    	this.userFileDelete = userFileDelete;
    }
    public boolean getFileDelete(){                  //get if user has file for delete
    	return userFileDelete;
    }
    public void DeleteFileUser(){
    	try{  
    		String searchMD5 = "SELECT counter FROM server_file WHERE f_MD5 = '"+MD5+"'";
    		Statement stmt = connect.createStatement();
			ResultSet MD5result = stmt.executeQuery(searchMD5);
			if(MD5result.next()){
				setFileDelete(true);
	    		String delete = "DELETE FROM "+ acc +" WHERE f_MD5 = '"+MD5+"'";
	    		stmt.executeUpdate(delete);
				Delete_File delete1 = new Delete_File(MD5);
				delete1.DeleteFile();
			}
			else
				setFileDelete(false);
    	}catch(Exception e){
			System.out.println(e.toString()); 
		}
    }
	public static void main(String[] args) {
		/*System.out.println("input: account, file_MD5");
		Scanner input = new Scanner(System.in);
		String acc = input.next();      String MD5 = input.next();
		Delete_File_user delete = new Delete_File_user(acc,MD5);
		delete.DeleteFileUser();
		System.out.println(delete.getFileDelete());
		input.close();*/
	}

}

package org.imbox.Database;
import java.sql.Statement;
//import java.util.Scanner;

public class Delete_File_user extends db_connect{
	String acc,FID;
	//���w�Ѽ�(�ϥΪ̱b���A�ɮ�ID)
    public Delete_File_user(String acc,String FID){
        this.FID = FID;
    	try{  
	    		String delete = "DELETE FROM "+ acc +" WHERE FID = '"+FID+"'";
	    		Statement stmt = connect.createStatement();
	    		stmt.executeUpdate(delete);
				new Delete_File(FID);
				
    	}catch(Exception e){
			System.out.println("�ҥ~:"+e.toString()); 
		}
    }
	public static void main(String[] args) {
		/*System.out.println("���w�ѼơG�ϥΪ̱b��     �ɮ�ID");
		Scanner input = new Scanner(System.in);
		String acc = input.next();      String FID = input.next();
		new Delete_File_user(acc,FID);
		input.close();*/
	}

}

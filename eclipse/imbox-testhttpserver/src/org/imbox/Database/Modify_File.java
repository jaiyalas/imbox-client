package org.imbox.Database;
import java.sql.ResultSet;
import java.sql.Statement;
//import java.util.Scanner;

public class Modify_File extends db_connect{
	String acc,old_FID,new_FID,newName,new_MD5;
	//���w�Ѽ�(�b���A�ª��ɮ�ID�A�s���ɮ�ID�A�s���ɮצW�A�s���ɮ�MD5)
    public Modify_File(String acc,String old_FID,String new_FID,String newName,String new_MD5){
    	this.acc = acc;            this.old_FID = old_FID;    	
    	this.new_FID = new_FID;    this.newName=newName;     this.new_MD5 = new_MD5;
    	try{  
    		//���X�ª�MD5
    		String oldMD5 = "SELECT f_MD5 FROM "+acc+" WHERE FID = '"+old_FID+"'";
    		Statement stmt = connect.createStatement();
			ResultSet oldMD5_result = stmt.executeQuery(oldMD5);
			if(oldMD5_result.next()){ 
				String old_MD5 = oldMD5_result.getString("f_MD5");
	    		//�R���ª��ɮ�
	    		new Delete_File_user(acc,old_FID);
				//�����s���ɮ�
	    		new Insert_File(acc,newName,new_FID,new_MD5);
	    		//�쥻��MD5���antedent_MD5
	    		String newMD5 = "UPDATE "+acc+" SET antedent_f_MD5='"+old_MD5+"' WHERE FID = '"+new_FID+"'";
	    		stmt.executeUpdate(newMD5);
			}
			else{
				System.out.println("���A�ݵL���ɮ�");
			}
			connect.close();  //������Ʈw
    	}catch(Exception e){
			System.out.println("�ҥ~:"+e.toString()); 
		}
    }
	public static void main(String[] args) {
		/*System.out.println("���w���ѼơG�ϥΪ̱b��    �ª��ɮ�ID  �s���ɮ�ID  �s���ɮצW   �s���ɮ�MD5");
		Scanner input = new Scanner(System.in);
		String acc = input.next();
		String old_FID = input.next();      String new_FID = input.next();
		String newName = input.next();  	String new_MD5 = input.next();
		new Modify_File(acc,old_FID,new_FID,newName,new_MD5);
		input.close();*/
	}

}

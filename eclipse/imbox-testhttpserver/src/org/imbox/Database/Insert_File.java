package org.imbox.Database;
import java.sql.*;
//import java.util.Scanner;
public class Insert_File extends db_connect{
    String acc,FileName,FID,MD5;
	//���w�|�Ѽ�(�ϥΪ̱b���A�ɮצW�A�ɮ�ID�A�ɮ�MD5)
	public Insert_File(String acc,String FileName,String FID,String MD5){
		this.acc = acc;   this.FileName = FileName;
		this.FID = FID;   this.MD5 = MD5;
		try{          
			//�d����A�����ɮצ��L�ۦPFID(�D��)�A�Τ���n��MD5���i
			String searchFID = "SELECT FID FROM server_file WHERE FID = '"+FID+"'";
			Statement stmt = connect.createStatement();
			ResultSet FIDresult = stmt.executeQuery(searchFID);
			if(FIDresult.next()){    
				//���ۦP�A�W�[��FID server_file counter
				//System.out.println("���@���ɮ�");
				String counter_plus = "UPDATE server_file SET counter=counter+1 WHERE FID = '"+FID+"'";
				stmt.executeUpdate(counter_plus);
			}
			else{    //���s�ɮ�
				//�s�W�iserver_file table�Acounter�]��1
				String insert = "INSERT INTO server_file VALUES ('"+ FID +"', 1,'"+ MD5 +"')";
				stmt.executeUpdate(insert);
				//�s�W�iblock?
				
			}
			//�s�W�ܨϥΪ̦ۤv���ɮ׸�ƪ�A���ۦP�ɮצW�ٵ���ĵ�i
			String searchFN = "SELECT fileName FROM "+acc+" WHERE fileName = '"+FileName+"'";
			ResultSet FNresult = stmt.executeQuery(searchFN);
			if(FNresult.next()){
				System.out.println("��user�w���ϥιL�o���ɮצW��");
			}
			else{
				String owner = "INSERT INTO "+acc+" VALUES ('"+FileName+"','"+FID+"','"+MD5 +"','"+MD5+"')";
				stmt.executeUpdate(owner);
			}
			connect.close();        //������Ʈw
		}catch(Exception e){
			System.out.println("�ҥ~:"+e.toString()); 
		}
	}
	public static void main(String[] args) {
		/*System.out.println("���w�|�ѼơG�ϥΪ̱b��    �ɮצW    �ɮ�ID  �ɮ�MD5");
		Scanner input = new Scanner(System.in);
		String acc = input.next();  	String FileName = input.next();
		String FID = input.next();		String MD5 = input.next();
		new Insert_File(acc,FileName,FID,MD5);
		input.close();*/
	}

}

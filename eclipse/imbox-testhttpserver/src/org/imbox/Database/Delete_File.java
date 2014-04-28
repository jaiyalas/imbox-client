package org.imbox.Database;
import java.sql.ResultSet;
import java.sql.Statement;
//import java.util.Scanner;

public class Delete_File extends db_connect{
	String acc,FID;
	//���w�Ѽ�(�ɮ�ID)
    public Delete_File(String FID){
        this.FID = FID;
    	try{  
    		String searchFID = "SELECT counter FROM server_file WHERE FID = '"+FID+"'";
    		Statement stmt = connect.createStatement();
			ResultSet FIDresult = stmt.executeQuery(searchFID);
			if(FIDresult.next()){ //���A�����ɮ�
				int counter = FIDresult.getInt("counter");
				//counter�Y��1�h�����R�����A���ɮסA������~<1���|�@�_�R��
				if(counter<=1){
					String delete = "DELETE FROM server_file WHERE FID = '"+FID+"'";
					stmt.executeUpdate(delete);   //����user�ݩMblock���|�Q�۰ʧR��
				}
				else{  //�j��1�hcounter-1
					String counter_minus = "UPDATE server_file SET counter=counter-1 WHERE FID = '"+FID+"'";
					stmt.executeUpdate(counter_minus);
				}
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
		/*System.out.println("���w�ѼơG �ɮ�ID");
		Scanner input = new Scanner(System.in);
		String FID = input.next();
		new Delete_File(FID);
		input.close();*/
	}

}

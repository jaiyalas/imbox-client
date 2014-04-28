package db_query;
import java.sql.ResultSet;
import java.sql.Statement;
//import java.util.Scanner;

public class Delete_File extends db_connect{
	String acc,FID;
	//給定參數(檔案ID)
    public Delete_File(String FID){
        this.FID = FID;
    	try{  
    		String searchFID = "SELECT counter FROM server_file WHERE FID = '"+FID+"'";
    		Statement stmt = connect.createStatement();
			ResultSet FIDresult = stmt.executeQuery(searchFID);
			if(FIDresult.next()){ //伺服器有檔案
				int counter = FIDresult.getInt("counter");
				//counter若為1則直接刪除伺服器檔案，防止錯誤<1都會一起刪除
				if(counter<=1){
					String delete = "DELETE FROM server_file WHERE FID = '"+FID+"'";
					stmt.executeUpdate(delete);   //此時user端和block都會被自動刪除
				}
				else{  //大於1則counter-1
					String counter_minus = "UPDATE server_file SET counter=counter-1 WHERE FID = '"+FID+"'";
					stmt.executeUpdate(counter_minus);
				}
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
		/*System.out.println("給定參數： 檔案ID");
		Scanner input = new Scanner(System.in);
		String FID = input.next();
		new Delete_File(FID);
		input.close();*/
	}

}

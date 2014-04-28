package db_query;
import java.sql.*;
//import java.util.Scanner;
public class Insert_File extends db_connect{
    String acc,FileName,FID,MD5;
	//給定四參數(使用者帳號，檔案名，檔案ID，檔案MD5)
	public Insert_File(String acc,String FileName,String FID,String MD5){
		this.acc = acc;   this.FileName = FileName;
		this.FID = FID;   this.MD5 = MD5;
		try{          
			//查找伺服器端檔案有無相同FID(主鍵)，或之後要用MD5都可
			String searchFID = "SELECT FID FROM server_file WHERE FID = '"+FID+"'";
			Statement stmt = connect.createStatement();
			ResultSet FIDresult = stmt.executeQuery(searchFID);
			if(FIDresult.next()){    
				//有相同，增加該FID server_file counter
				//System.out.println("有一樣檔案");
				String counter_plus = "UPDATE server_file SET counter=counter+1 WHERE FID = '"+FID+"'";
				stmt.executeUpdate(counter_plus);
			}
			else{    //為新檔案
				//新增進server_file table，counter設為1
				String insert = "INSERT INTO server_file VALUES ('"+ FID +"', 1,'"+ MD5 +"')";
				stmt.executeUpdate(insert);
				//新增進block?
				
			}
			//新增至使用者自己的檔案資料表，有相同檔案名稱給予警告
			String searchFN = "SELECT fileName FROM "+acc+" WHERE fileName = '"+FileName+"'";
			ResultSet FNresult = stmt.executeQuery(searchFN);
			if(FNresult.next()){
				System.out.println("該user已有使用過這個檔案名稱");
			}
			else{
				String owner = "INSERT INTO "+acc+" VALUES ('"+FileName+"','"+FID+"','"+MD5 +"','"+MD5+"')";
				stmt.executeUpdate(owner);
			}
			connect.close();        //關閉資料庫
		}catch(Exception e){
			System.out.println("例外:"+e.toString()); 
		}
	}
	public static void main(String[] args) {
		/*System.out.println("給定四參數：使用者帳號    檔案名    檔案ID  檔案MD5");
		Scanner input = new Scanner(System.in);
		String acc = input.next();  	String FileName = input.next();
		String FID = input.next();		String MD5 = input.next();
		new Insert_File(acc,FileName,FID,MD5);
		input.close();*/
	}

}

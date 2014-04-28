package db_query;
import java.sql.ResultSet;
import java.sql.Statement;
//import java.util.Scanner;
public class search_block extends db_connect{
    String FID;
	public search_block(String FID){
    	this.FID = FID;
    	try{  
    		String searchFID = "SELECT blockName,sequence FROM block_map WHERE FID = '"+FID+"'";
    		Statement stmt = connect.createStatement();
    		ResultSet FIDresult = stmt.executeQuery(searchFID);
            //有檔案block一筆一筆印出
			while(FIDresult.next()){
				String result_b = FIDresult.getString("blockName");
				String result_s = FIDresult.getString("sequence");
				System.out.println("b_name："+result_b+", seq："+result_s);
			}
        }catch(Exception e){
		System.out.println("例外:"+e.toString()); 
	    }
    }
	public static void main(String[] args) {
		/*System.out.println("給檔案ID");
		Scanner input = new Scanner(System.in);
		String FID = input.next();
		new search_block(FID);
        input.close();*/
	}

}

package db_query;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.*;
import java.io.*;
public class Insert_block extends db_connect{
	String filePath;
    public Insert_block(String filePath){
    	try{
    		//讀入block檔案放進資料庫中(如果FID相同則直接取代舊的block並增加新的或刪除多的
    		this.filePath = filePath;                               //讀取檔案
    		File file = new File(filePath);
    		FileReader fr = new FileReader(file);
    	    BufferedReader br = new BufferedReader(fr);
    		String[] tempArray = new String[3];                 //暫存資料的一維陣列
    		ArrayList<String> myList = new ArrayList<String>(); //動態一維陣列
    		while (br.ready()) {
    	        String line = br.readLine();         //讀取一行資料放入line
    	        tempArray = line.split("\\s");       //用空白分開
    	        for(int i=0;i<tempArray.length;i++)
    	        	myList.add(tempArray[i]);        //放入動態一維陣列
     	    }
    		fr.close();
    		int size = myList.size()/3;                   //有幾個block要新增
    		int index = 0;                                //myList中讀資料指標
    		String[][] inputArray = new String[size][3];  //放資料的二維陣列
    		for(int j=0; j < size ; j++){
                for(int k=0 ; k<3 ; k++){
                	inputArray[j][k]= myList.get(index);
                	index++;              //一個index來決定myList讀取值的位置
                }
            }
    		//是否為檔案修改的新增block
    		Statement stmt = connect.createStatement();
    		String searchFID = "SELECT count(*) FROM block_map WHERE FID = '"+inputArray[0][2]+"'"; //計算舊的有幾筆
    		ResultSet FIDresult = stmt.executeQuery(searchFID);
     		if(FIDresult.next()){
    			//server端已有檔案block，非新檔案
    			int count = FIDresult.getInt(1);  //原本block有幾筆
    			//新檔案block數比原本多
    			if(count <= size){   
    				//舊的blockName換成新的
    				for(int i=0; i <count ; i++){  
    					String update_b = "UPDATE block_map SET blockName='"+inputArray[i][0]+"' WHERE FID = '"+inputArray[i][2]+"' AND sequence ="+(i+1);
    					stmt.executeUpdate(update_b);
    				}
    				//新的block直接插入
    				for(int j=count+1 ; j <= size ; j++){  
    					String insert_b = "INSERT INTO block_map VALUES (NULL,'"+inputArray[j-1][0]+"', "+j+",'"+inputArray[j-1][2]+"')";
    	        		stmt.executeUpdate(insert_b);
    				}
    			}
    			//新檔案block數比原本少
    			else{
    				//舊的blockName換成新的
    				for(int i=0; i < size ; i++){  
    					String update_b = "UPDATE block_map SET blockName='"+inputArray[i][0]+"' WHERE FID = '"+inputArray[i][2]+"' AND sequence ="+(i+1);
    					stmt.executeUpdate(update_b);
    				}
    				//多出來的block刪除
    				for(int j=size+1 ; j <= count ; j++){  
    					String delete_b = "DELETE FROM block_map WHERE FID = '" + inputArray[0][2] + "' AND sequence= "+ j;
    	        		stmt.executeUpdate(delete_b);
    				}
    			}
    		}
     		else{	//新檔案直接將block資料放進資料庫中
     			int k = 1;
     			for(int j=0; j < size ; j++){
     				String insert = "INSERT INTO block_map VALUES (NULL,'"+inputArray[j][0]+"', "+k+",'"+inputArray[j][2]+"')";
     				stmt.executeUpdate(insert);
     				k++;
     			}
     		}
     		connect.close();  //關閉資料庫
       	}catch(Exception e){
    		System.out.println(e.toString());
    	}

    }
	public static void main(String[] args) {
		/*System.out.println("給檔案名稱");
		Scanner input = new Scanner(System.in);
		String filePath = input.next();
        new Insert_block(filePath);
        input.close();*/
	}

}

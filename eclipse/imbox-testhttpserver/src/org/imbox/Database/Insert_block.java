package org.imbox.Database;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.*;
import java.io.*;
public class Insert_block extends db_connect{
	String filePath;
    public Insert_block(String filePath){
    	try{
    		//Ū�Jblock�ɮש�i��Ʈw��(�p�GFID�ۦP�h�������N�ª�block�üW�[�s���ΧR���h��
    		this.filePath = filePath;                               //Ū���ɮ�
    		File file = new File(filePath);
    		FileReader fr = new FileReader(file);
    	    BufferedReader br = new BufferedReader(fr);
    		String[] tempArray = new String[3];                 //�Ȧs��ƪ��@���}�C
    		ArrayList<String> myList = new ArrayList<String>(); //�ʺA�@���}�C
    		while (br.ready()) {
    	        String line = br.readLine();         //Ū���@���Ʃ�Jline
    	        tempArray = line.split("\\s");       //�Ϊťդ��}
    	        for(int i=0;i<tempArray.length;i++)
    	        	myList.add(tempArray[i]);        //��J�ʺA�@���}�C
     	    }
    		fr.close();
    		int size = myList.size()/3;                   //���X��block�n�s�W
    		int index = 0;                                //myList��Ū��ƫ���
    		String[][] inputArray = new String[size][3];  //���ƪ��G���}�C
    		for(int j=0; j < size ; j++){
                for(int k=0 ; k<3 ; k++){
                	inputArray[j][k]= myList.get(index);
                	index++;              //�@��index�ӨM�wmyListŪ���Ȫ���m
                }
            }
    		//�O�_���ɮ׭ק諸�s�Wblock
    		Statement stmt = connect.createStatement();
    		String searchFID = "SELECT count(*) FROM block_map WHERE FID = '"+inputArray[0][2]+"'"; //�p���ª����X��
    		ResultSet FIDresult = stmt.executeQuery(searchFID);
     		if(FIDresult.next()){
    			//server�ݤw���ɮ�block�A�D�s�ɮ�
    			int count = FIDresult.getInt(1);  //�쥻block���X��
    			//�s�ɮ�block�Ƥ�쥻�h
    			if(count <= size){   
    				//�ª�blockName�����s��
    				for(int i=0; i <count ; i++){  
    					String update_b = "UPDATE block_map SET blockName='"+inputArray[i][0]+"' WHERE FID = '"+inputArray[i][2]+"' AND sequence ="+(i+1);
    					stmt.executeUpdate(update_b);
    				}
    				//�s��block�������J
    				for(int j=count+1 ; j <= size ; j++){  
    					String insert_b = "INSERT INTO block_map VALUES (NULL,'"+inputArray[j-1][0]+"', "+j+",'"+inputArray[j-1][2]+"')";
    	        		stmt.executeUpdate(insert_b);
    				}
    			}
    			//�s�ɮ�block�Ƥ�쥻��
    			else{
    				//�ª�blockName�����s��
    				for(int i=0; i < size ; i++){  
    					String update_b = "UPDATE block_map SET blockName='"+inputArray[i][0]+"' WHERE FID = '"+inputArray[i][2]+"' AND sequence ="+(i+1);
    					stmt.executeUpdate(update_b);
    				}
    				//�h�X�Ӫ�block�R��
    				for(int j=size+1 ; j <= count ; j++){  
    					String delete_b = "DELETE FROM block_map WHERE FID = '" + inputArray[0][2] + "' AND sequence= "+ j;
    	        		stmt.executeUpdate(delete_b);
    				}
    			}
    		}
     		else{	//�s�ɮת����Nblock��Ʃ�i��Ʈw��
     			int k = 1;
     			for(int j=0; j < size ; j++){
     				String insert = "INSERT INTO block_map VALUES (NULL,'"+inputArray[j][0]+"', "+k+",'"+inputArray[j][2]+"')";
     				stmt.executeUpdate(insert);
     				k++;
     			}
     		}
     		connect.close();  //������Ʈw
       	}catch(Exception e){
    		System.out.println(e.toString());
    	}

    }
	public static void main(String[] args) {
		/*System.out.println("���ɮצW��");
		Scanner input = new Scanner(System.in);
		String filePath = input.next();
        new Insert_block(filePath);
        input.close();*/
	}

}

package	simpleconnection;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;

public class Responsereader
{
	private String responsebody;
	public Responsereader(HttpResponse res )
	{
		try{
		BufferedReader br = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
		String readLine;
		responsebody = "";
		while (((readLine = br.readLine()) != null)) 
		{
		   responsebody = responsebody + readLine;
		}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public String getresponse()
	{
		return responsebody;
	}
}
package jsonreader;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;

public class Getfilereader
{
	private String token;
	private String filename;
	private String blockname;
	private int sequence;
	private HttpExchange request;
	private String jsonstring;
	
	public Getfilereader(HttpExchange httpconnection)
	{
		request = httpconnection;
		getjson();
	}
	
	private void getjson()
	{
		try {
			InputStreamReader requestreader =  new InputStreamReader(request.getRequestBody(),"utf-8");
			BufferedReader br = new BufferedReader(requestreader);
			jsonstring = br.readLine();
			br.close();
			requestreader.close();
			parsejson();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void parsejson()
	{
		try {
			JSONObject obj = new JSONObject(jsonstring);
			token = obj.getString("token");
			filename = obj.getString("filename");
			blockname = obj.getString("blockname");
			sequence = obj.getInt("seq");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String gettoken()
	{
		return token;
	}
	
	public String getfilename()
	{
		return filename;
	}
	
	public String getblockname()
	{
		return blockname;
	}
	
	public int getsequence()
	{
		return sequence;
	}
}
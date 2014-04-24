package jsonreader;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;

public class Postfilereader
{
	private String token;
	private String blockdata;
	private int sequence;
	private String jsonstring;
	private HttpExchange request;
	public Postfilereader(HttpExchange httpconnection)
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
			blockdata = obj.getString("blockdata");
			sequence = obj.getInt("seq");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public String gettoken()
	{
		return token;
	}
	
	public String getdatastring()
	{
		return blockdata;
	}
	
	public int getsequence()
	{
		return sequence;
	}
}
package org.imbox.server.jsonreaders;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;

public class Accountfilenamereader
{
	private String MAC;
	private String token;
	private String filename;
	private String accountname;
	private String jsonstring;
	private HttpExchange request;
	
	public Accountfilenamereader(HttpExchange httpconnection)
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
			MAC = obj.getString("MAC");
			token = obj.getString("token");
			accountname = obj.getString("account");
			filename = obj.getString("filename");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public String getfilename()
	{
		return filename;
	}
	
	public String getaccount()
	{
		return accountname;
	}
	
	public String getMAC()
	{
		return MAC;
	}
	
	public String gettoken()
	{
		return token;
	}
}
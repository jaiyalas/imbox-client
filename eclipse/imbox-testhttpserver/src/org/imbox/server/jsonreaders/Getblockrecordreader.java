package org.imbox.server.jsonreaders;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;


public class Getblockrecordreader
{
	private String token;
	private String MAC;
	private String filename;
	private HttpExchange request;
	private String jsonstring;
	
	public Getblockrecordreader(HttpExchange httpconnection)
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
			MAC = obj.getString("MAC");
			filename = obj.getString("filename");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String gettoken()
	{
		return token;
	}
	
	public String getmac()
	{
		return MAC;
		
	}
	
	public String getfilename()
	{
		return filename;
	}
}
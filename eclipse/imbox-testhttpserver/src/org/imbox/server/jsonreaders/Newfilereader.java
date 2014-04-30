package org.imbox.server.jsonreaders;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;

public class Newfilereader
{
	private String token;
	private String MAC;
	private String filename;
	private String md5;
	private HttpExchange request;
	private String jsonstring;
	
	public Newfilereader(HttpExchange httpconnection)
	{
		request = httpconnection;
	}
	
	public void readjson()
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
		try
		{
			JSONObject obj = new JSONObject(jsonstring);
			token = obj.getString("token");
			MAC = obj.getString("MAC");
			filename = obj.getString("filename");
			md5 = obj.getString("md5"); 
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public String getfilename()
	{
		return filename;
	}
	public String getmd5()
	{
		return md5;
	}
	public String gettoken()
	{
		return token;
	}
	public String getmac()
	{
		return MAC;
	}
}
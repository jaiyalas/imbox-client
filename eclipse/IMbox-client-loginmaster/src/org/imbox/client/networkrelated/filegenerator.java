package org.imbox.client.networkrelated;

import org.apache.http.HttpResponse;
import org.imbox.client.networkrelated.ultility.Responsereader;
import org.imbox.client.networkrelated.ultility.Simpleconnection;
import org.json.JSONObject;


public class filegenerator
{
	private String account;
	private String filename;
	private String data;
	private boolean status;
	private int errorcode;
	public filegenerator(String account,String filename)
	{
		this.account = account;
		this.filename = filename;
		errorcode = -1;
		data = new String();
		status = false;
		sendrequest();
	}
	
	private void sendrequest()
	{
		try
		{
			JSONObject obj = new JSONObject();
			obj.put("account", this.account);
			obj.put("filename", this.filename);
			Simpleconnection conn = new Simpleconnection();
			readresponse(conn.httppost("generatefile", obj));
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	private void readresponse(HttpResponse res)
	{
		try
		{
			if (res.getStatusLine().getStatusCode() == 200)
			{
				Responsereader reader = new Responsereader(res);
				JSONObject obj = new JSONObject(reader.getresponse());
				data = obj.getString("data");
				status = obj.getBoolean("succ");
				errorcode = obj.getInt("errorcode");
			}else
			{
				System.out.println("http error: " + Integer.toString(res.getStatusLine().getStatusCode()));
				data = new String();
				status = false;
				errorcode = -2;
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
			data = new String();
			status = false;
			errorcode = 20;
		}
	}
	public String getdata()
	{
		return data;
	}
	public boolean getstatus()
	{
		return status;
	}
	public int geterrorcode()
	{
		return errorcode;
	}
}
package org.imbox.client.networkrelated;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.imbox.client.networkrelated.ultility.Internetrecord;
import org.imbox.client.networkrelated.ultility.Responsereader;
import org.imbox.client.networkrelated.ultility.Simpleconnection;
import org.imbox.infrastructure.file.FileRec;
import org.json.JSONArray;
import org.json.JSONObject;


public class Syncrequester
{
	private boolean succ;
	private int errorcode;
	private JSONArray jsonfilelistarray;
	public Syncrequester()
	{
		
	}
	
	public void sendrequest()
	{
		try
		{
			JSONObject obj=new JSONObject();
			obj.put("token", Internetrecord.gettoken());
			obj.put("MAC", Internetrecord.getMAC());
			Simpleconnection conn = new Simpleconnection();
			HttpResponse res = conn.httppost("syncrequest", obj);
			if (res.getStatusLine().getStatusCode() == 200)
			{
				Responsereader responsereader = new Responsereader(res);
				JSONObject result = new JSONObject(responsereader.getresponse());
				jsonfilelistarray = result.getJSONArray("filelist");
				succ = result.getBoolean("succ");
				errorcode = result.getInt("errorcode");
			}else
			{
				System.out.println("http error: " + Integer.toString(res.getStatusLine().getStatusCode()));
				jsonfilelistarray = new JSONArray();
				succ = false;
				errorcode = -2;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public List<FileRec> getfilelist()
	{
		List<FileRec> returnlist = new ArrayList<FileRec>();
		try
		{
			for (int i = 0;i<jsonfilelistarray.length();i+=2)
			{
				returnlist.add(new FileRec(jsonfilelistarray.get(i).toString(),jsonfilelistarray.get(i+1).toString()));
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return returnlist;
	}
	
	public boolean getstatus()
	{
		return succ;
	}
	
	public int geterrorcode()
	{
		return errorcode;
	}
}
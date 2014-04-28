package org.imbox.client.networkrelated;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.imbox.client.networkrelated.ultility.Internetrecord;
import org.imbox.client.networkrelated.ultility.Responsereader;
import org.imbox.client.networkrelated.ultility.Simpleconnection;
import org.imbox.infrastructure.file.BlockRec;
import org.json.JSONArray;
import org.json.JSONObject;

public class Blockrecordgetter
{
	private String filename;
	private boolean succ;
	private int errorcode;
	private JSONArray jsonblocklistarray;
	public Blockrecordgetter(String filename)
	{
		this.filename = filename;
		sendrequest();
	}
	
	private void sendrequest()
	{
		try
		{
			JSONObject obj=new JSONObject();
			obj.put("token", Internetrecord.gettoken());
			obj.put("MAC", Internetrecord.getMAC());
			obj.put("filename", filename);
			Simpleconnection conn = new Simpleconnection();
			HttpResponse res = conn.httppost("createaccount", obj);
			if (res.getStatusLine().getStatusCode() == 200)
			{
				Responsereader responsereader = new Responsereader(res);
				JSONObject result = new JSONObject(responsereader.getresponse());
				jsonblocklistarray = result.getJSONArray("blocklist");
				succ = result.getBoolean("succ");
				errorcode = result.getInt("errorcode");
			}else
			{
				System.out.println("http error: " + Integer.toString(res.getStatusLine().getStatusCode()));
				jsonblocklistarray = new JSONArray();
				succ = false;
				errorcode = -2;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public List<BlockRec> getblocklist()
	{
		List<BlockRec> returnlist = new ArrayList<BlockRec>();
		try
		{
			for (int i = 0;i<jsonblocklistarray.length();i+=2)
			{
				returnlist.add(new BlockRec(jsonblocklistarray.get(i).toString(), (int) jsonblocklistarray.get(i+1)));
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
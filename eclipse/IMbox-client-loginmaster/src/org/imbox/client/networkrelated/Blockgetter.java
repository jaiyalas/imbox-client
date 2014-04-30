package org.imbox.client.networkrelated;

import org.apache.http.HttpResponse;
import org.imbox.client.networkrelated.ultility.Internetrecord;
import org.imbox.client.networkrelated.ultility.Responsereader;
import org.imbox.client.networkrelated.ultility.Simpleconnection;
import org.imbox.infrastructure.Casting;
import org.imbox.infrastructure.file.Block;
import org.json.JSONObject;


public class Blockgetter
{
	private String blockname;
	private int seq;
	private boolean status;
	private String datastring;
	private int errorcode;
	private Block resultblock;
	
	public Blockgetter(String blockname,int seq)
	{
		this.blockname = blockname;
		this.seq = seq;
		status=false;
		datastring = new String();
		errorcode = -1;
	}
	
	public void sendrequest()
	{
		try
		{
			JSONObject obj = new JSONObject();
			obj.put("token", Internetrecord.gettoken());
			obj.put("MAC", Internetrecord.getMAC());
			obj.put("blockname", blockname);
			obj.put("seq", seq);
			Simpleconnection conn = new Simpleconnection();
			readresponse(conn.httppost("getblock", obj));
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void readresponse(HttpResponse res)
	{
		
		try {
			if (res.getStatusLine().getStatusCode() == 200)
			{
				Responsereader reader = new Responsereader(res);
				JSONObject obj = new JSONObject(reader.getresponse());
				status = obj.getBoolean("succ");
				errorcode = obj.getInt("errorcode");
				datastring = obj.getString("data");
				byte[] content = Casting.stringToBytes(datastring);
				resultblock = Block.genBlock(content, seq);
			}else
			{
				System.out.println("http error: " + Integer.toString(res.getStatusLine().getStatusCode()));
				status = false;
				errorcode = -2;
				datastring = new String();
			}
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
			errorcode =20;
			datastring = new String();
		}
		
	}
	
	public boolean getstatus()
	{
		return status;
	}
	
	public int geterrorcode()
	{
		return errorcode;
	}
	
	public String getdata()
	{
		return datastring;
	}
	
	public Block getresultblock()
	{
		return resultblock;
	}

}
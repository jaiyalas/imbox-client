package org.imbox.client.networkrelated;

import java.net.InetAddress;
import java.net.NetworkInterface;

import org.apache.http.HttpResponse;
import org.imbox.client.networkrelated.ultility.Responsereader;
import org.imbox.client.networkrelated.ultility.Simpleconnection;
import org.json.JSONObject;


public class Fileposter
{
	private String token;
	private String blockdatastring;
	private String blockname;
	private int seq;
	private boolean status;
	private String datastring;
	private int errorcode;
	
	public Fileposter(String token,String blockname, String blockdata,int seq)
	{
		this.token = token;
		blockdatastring = blockdata;
		this.blockname = blockname;
		this.seq = seq;
		status = false;
		errorcode = -1;
		datastring = new String();
		sendfilestring();
	}
	
	private void sendfilestring()
	{
		try
		{
			JSONObject obj = new JSONObject();
			obj.put("token", token);
			obj.put("MAC" , getmacaddress());
			obj.put("blockname", blockname);
			obj.put("blockdata", blockdatastring);
			obj.put("seq", seq);
			Simpleconnection conn = new Simpleconnection();
			readresponse(conn.httppost("postfile", obj));
			
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
			}else
			{
				System.out.println("http error: " + Integer.toString(res.getStatusLine().getStatusCode()));
				status = false;
				errorcode = -2;
			}
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
			errorcode = 20;
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
	
	private String getmacaddress()
	{
		InetAddress ip;
		try
		{
			ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			byte[] mac = network.getHardwareAddress();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? ":" : ""));		
			}
			return sb.toString();
		}catch(Exception e)
		{
			e.printStackTrace();
			return "";
		}
		
	}

}
package org.imbox.networkrelated;

import java.net.InetAddress;
import java.net.NetworkInterface;

import org.apache.http.HttpResponse;
import org.imbox.networkrelated.ultility.Responsereader;
import org.imbox.networkrelated.ultility.Simpleconnection;
import org.json.JSONObject;


public class Serverlockgetter
{
	private String token;
	private String MAC;
	private boolean status;
	private int errorcode;
	public Serverlockgetter(String token)
	{
		this.token = token;
		this.MAC = getmacaddress();
		status = false;
		errorcode = -1;
		getlock();
	}
	
	private void getlock()
	{
		try
		{
			JSONObject obj = new JSONObject();
			obj.put("token", this.token);
			obj.put("MAC", this.MAC);
			Simpleconnection conn = new Simpleconnection();
			HttpResponse res = conn.httppost("getserverlock", obj);
			checkstatus(res);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void checkstatus(HttpResponse res)
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
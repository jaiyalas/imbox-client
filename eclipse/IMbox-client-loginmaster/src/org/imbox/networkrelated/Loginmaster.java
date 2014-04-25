package org.imbox.networkrelated;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpResponse;
import org.imbox.networkrelated.ultility.Responsereader;
import org.imbox.networkrelated.ultility.Simpleconnection;
import org.json.JSONObject;



public class Loginmaster
{
	private String accountname;
	private String password;
	private String token;
	private String MAC;
	private boolean status;
	private int errorcode;
	public Loginmaster(String accountname,String password) 
	{ 
		this.accountname = accountname; 
		this.password = password ; 
		this.MAC = getmacaddress(); 
		this.token = ""; 
		this.status = false; 
		this.errorcode = -1; 
	} 
	public Loginmaster() 
	{ 
		this("",""); 
	}
	
	public void setInfo(String name,String pwd) 
	{ 
		this.accountname = name; 
		this.password = pwd;
		this.MAC = getmacaddress();
	}
	
	public void authenticate()
	{
		try {
			JSONObject obj=new JSONObject();
			obj.put("account", accountname);
			obj.put("password", getencrypt());
			obj.put("MAC", MAC);
			Simpleconnection conn = new Simpleconnection();
			HttpResponse res = conn.httppost("login", obj);
			readresponse(res);
		} catch (Exception e){
			e.printStackTrace();
			token = new String();
			status = false;
			errorcode = 0;
		}	 
	}
	
	private void readresponse(HttpResponse res)
	{
		try{
			if (res.getStatusLine().getStatusCode() == 200)
			{
				Responsereader responsereader = new Responsereader(res);
				System.out.println(responsereader.getresponse());
				JSONObject result = new JSONObject(responsereader.getresponse());
				token = result.getString("token");
				status = result.getBoolean("succ");
				errorcode = result.getInt("errorcode");
			}else
			{
				System.out.println("http error: " + Integer.toString(res.getStatusLine().getStatusCode()));
				token = new String();
				status = false;
				errorcode = -2;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			token = new String();
			status = false;
			errorcode = 20;
		}
	}
	
	public String gettoken()
	{
		return token;
	}
	
	public boolean getstatus()
	{
		return status;
	}
	
	public int geterrorcode()
	{
		return errorcode;
	}
	
	public String getMAC() 
	{ 
		return getmacaddress(); 
	}
	
	
	private String getencrypt()
	{
		try {
			MessageDigest encrypter = MessageDigest.getInstance("SHA-256");
			encrypter.update(password.getBytes());
			byte[] digest = encrypter.digest();
			return digest.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
		
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
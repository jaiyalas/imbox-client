package org.imbox.client.networkrelated;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpResponse;
import org.imbox.client.networkrelated.ultility.Internetrecord;
import org.imbox.client.networkrelated.ultility.Responsereader;
import org.imbox.client.networkrelated.ultility.Simpleconnection;
import org.json.JSONObject;



public class Accountcreator
{
	private String account;
	private String password;
	private String token;
	private boolean responsestatus;
	private int responseerrorcode;
	
	public Accountcreator(String account, String password)
	{
		this.account = account;
		this.password = password;
		responsestatus = false;
		responseerrorcode = -1;
		token = "";
		register();
	}
	
	private void register()
	{
		try {
			JSONObject obj=new JSONObject();
			obj.put("account", account);
			obj.put("password", getencrypt());
			obj.put("MAC", Internetrecord.getMAC());
			Simpleconnection conn = new Simpleconnection();
			HttpResponse res = conn.httppost("createaccount", obj);
			if (res.getStatusLine().getStatusCode() == 200)
			{
				Responsereader responsereader = new Responsereader(res);
				JSONObject result = new JSONObject(responsereader.getresponse());
				token = result.getString("token");
				Internetrecord.settoken(token);
				responsestatus = result.getBoolean("succ");
				responseerrorcode = result.getInt("errorcode");
			}else
			{
				System.out.println("http error: " + Integer.toString(res.getStatusLine().getStatusCode()));
				responsestatus = false;
				token = "";
				responseerrorcode = -2;
			}
			
		} catch (Exception e){
			e.printStackTrace();
			responsestatus = false;
			token = "";
			responseerrorcode = 20;
		}
	}
	
//	public String gettoken()
//	{
//		return token;
//	}
	
	public boolean getstatus()
	{
		if (responsestatus)
		{
			return true;
		}else
		{
			return false;
		}
	}
	
	public int geterrorcode()
	{
		return responseerrorcode;
	}
	
	
	private String getencrypt()
	{
		try {
			MessageDigest encrypter = MessageDigest.getInstance("SHA-256");
			encrypter.update(password.getBytes());
			byte[] digest = encrypter.digest();
			return new String(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
}
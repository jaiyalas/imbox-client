package createaccount;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpResponse;
import org.json.JSONObject;

import simpleconnection.Responsereader;
import simpleconnection.Simpleconnection;


public class Accountcreator
{
	private String account;
	private String password;
	private String MAC;
	private String token;
	private boolean responsestatus;
	private int responseerrorcode;
	
	public Accountcreator(String account, String password)
	{
		this.account = account;
		this.password = password;
		MAC = getmacaddress();
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
			obj.put("MAC", MAC);
			Simpleconnection conn = new Simpleconnection();
			HttpResponse res = conn.httppost("createaccount", obj);
			if (res.getStatusLine().getStatusCode() == 200)
			{
				Responsereader responsereader = new Responsereader(res);
				JSONObject result = new JSONObject(responsereader.getresponse());
				token = result.getString("token");
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
	
	public String gettoken()
	{
		return token;
	}
	
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
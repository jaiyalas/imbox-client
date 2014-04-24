package jsonreader;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;

public class Loginrequestreader
{
	private HttpExchange request;
	private String account;
	private String password;
	private String jsonstring;
	private String MAC;
	public Loginrequestreader(HttpExchange httpconnection)
	{
		request = httpconnection;
		getjson();
	}
	
	private void getjson()
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
		 try {
			JSONObject obj = new JSONObject(jsonstring);
			account = obj.getString("account");
			password = obj.getString("password");
			MAC = obj.getString("MAC");
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
	}
	
	public String getaccount()
	{	
		return account;
	}
	
	public String getpassword()
	{
		return password;
	}
	
	public String getMAC()
	{
		return MAC;
	}

}
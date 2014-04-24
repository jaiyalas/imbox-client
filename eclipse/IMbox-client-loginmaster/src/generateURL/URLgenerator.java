package generateURL;

import org.apache.http.HttpResponse;
import org.json.JSONObject;

import simpleconnection.Responsereader;
import simpleconnection.Simpleconnection;

public class URLgenerator
{
	private String account;
	private String filename;
	private String URL;
	private boolean status;
	private int errorcode;
	public URLgenerator(String account,String filename)
	{
		this.account = account;
		this.filename = filename;
		URL=new String();
		status = false;
		errorcode = -1;
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
			readresponse(conn.httppost("generateURL", obj));
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
				URL = obj.getString("URL");
				status = obj.getBoolean("succ");
				errorcode = obj.getInt("errorcode");
			}else
			{
				System.out.println("http error: " + Integer.toString(res.getStatusLine().getStatusCode()));
				URL=new String();
				status = false;
				errorcode = -2;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			URL = new String();
			status = false;
			errorcode = 20;
		}
	}
	public String geturl()
	{
		return URL;
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
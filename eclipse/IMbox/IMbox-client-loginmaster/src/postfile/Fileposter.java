package postfile;

import org.apache.http.HttpResponse;
import org.json.JSONObject;

import simpleconnection.Responsereader;
import simpleconnection.Simpleconnection;

public class Fileposter
{
	private String token;
	private String blockdatastring;
	private int seq;
	private boolean status;
	private String datastring;
	private int errorcode;
	
	public Fileposter(String token,String blockdata,int seq)
	{
		this.token = token;
		blockdatastring = blockdata;
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

}
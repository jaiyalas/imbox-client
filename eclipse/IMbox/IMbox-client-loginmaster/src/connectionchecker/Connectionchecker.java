package connectionchecker;

import org.apache.http.HttpResponse;

import simpleconnection.Simpleconnection;

public class Connectionchecker
{
	public Connectionchecker()
	{
		
	}
	
	public boolean checknetworkstatus()
	{
		try
		{
			Simpleconnection conn = new Simpleconnection();
			HttpResponse res = conn.httpget("networkcheck");
			if (res.getStatusLine().getStatusCode() == 200)
			{
				return true;
			}else
			{
				return false;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
}
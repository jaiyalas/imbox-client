/*package org.imbox.client.network.block;

import org.apache.http.HttpResponse;
import org.imbox.client.network.ultility.Responsereader;
import org.imbox.client.network.ultility.Simpleconnection;
import org.json.JSONArray;
import org.json.JSONObject;

//not using any more
public class Serversnapshotgetter
{
	private JSONArray snapshot;
	
	public Serversnapshotgetter()
	{
		snapshot = new JSONArray();
		try
		{
			Simpleconnection conn = new Simpleconnection();
			HttpResponse res = conn.httpget("getserversnapshot");
			readresponse(res);
		}catch(Exception e)
		{
			e.printStackTrace();
			snapshot = new JSONArray();
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
				snapshot = obj.getJSONArray("snapshot");
			}else
			{
				snapshot = new JSONArray();
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			snapshot = new JSONArray();
		}
	}
	
	public JSONArray getsystemsnapshot()
	{
		return snapshot;
	}
}*/
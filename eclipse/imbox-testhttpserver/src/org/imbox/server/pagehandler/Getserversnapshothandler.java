package org.imbox.server.pagehandler;
import java.io.IOException;

import org.imbox.server.functions.Httpresponser;
import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


public class Getserversnapshothandler implements HttpHandler
{
	private HttpExchange httpconnection;
	@Override
	public void handle(HttpExchange httpconnection) throws IOException 
	{
		this.httpconnection = httpconnection;
		Handlerthread multithread = new Handlerthread();
		multithread.setName("clientconnection");
		multithread.start();
		
		
	}
	private class Handlerthread extends Thread
	{
		@Override
		public void run() 
		{
			try
			{
				if (httpconnection.getRequestMethod().equals("GET"))
				{
					System.out.println("this is a http get method @ getsystemsnapshot");
					JSONObject obj = new JSONObject();
					obj.put("snapshot", new JSONArray("[\"this is a jsonArray value\",\"another jsonArray value\"]"));    //TODO: attach system snap shot to ""
					String response = obj.toString();
					Httpresponser res = new Httpresponser(httpconnection, response);
					res.execute();
				}else
				{
					if (httpconnection.getRequestMethod().equals("POST"))
					{
						System.out.println("this is a post method @ getsystemsnapshot");
						String response = "this is a http post method @ getsystemsnapshot";
						Httpresponser res = new Httpresponser(httpconnection, response);
						res.execute();
					}else
					{
						System.out.println("unknown method:" + httpconnection.getRequestMethod());
						String response = "this is a unkown method"+ httpconnection.getRequestMethod();
						Httpresponser res = new Httpresponser(httpconnection, response);
						res.execute();
					}
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}
	
}
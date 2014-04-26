package org.imbox.server.pagehandler;

import java.io.IOException;


import org.imbox.server.functions.Authenticator;
import org.imbox.server.functions.Httpresponser;
import org.imbox.server.jsonreaders.TokenMACreader;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


public class Getserverlockhandler implements HttpHandler
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
					System.out.println("this is a http get method @ getserverlock");
					String response = "this is a http get method @ getserverlock";
					Httpresponser res = new Httpresponser(httpconnection, response);
					res.execute();
				}else
				{
					if (httpconnection.getRequestMethod().equals("POST"))
					{
						System.out.println("this is a post method @ getserverlock");
						TokenMACreader requestreader = new TokenMACreader(httpconnection);
						System.out.println("token = " + requestreader.gettoken());
						System.out.println("MAC = " + requestreader.getMAC());
						//TODO: grant server lock here
						Authenticator auth = new Authenticator();
						boolean result = auth.Authenticatebytoken(requestreader.gettoken(), requestreader.getMAC());
						//TODO: getresult();
						if (result)
						{
							JSONObject obj=new JSONObject();
							obj.put("succ", true);
							obj.put("errorcode", 0);        //TODO: error code here if any
							String response = obj.toString();
							Httpresponser res = new Httpresponser(httpconnection, response);
							res.execute();
						}else
						{
							JSONObject obj=new JSONObject();
							obj.put("succ", false);
							obj.put("errorcode", 1);		 //TODO: error code here if any
							String response = obj.toString();
							Httpresponser res = new Httpresponser(httpconnection, response);
							res.execute();
						}
					}else
					{
						System.out.println("@getserverlcok - unknown method:" + httpconnection.getRequestMethod());
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
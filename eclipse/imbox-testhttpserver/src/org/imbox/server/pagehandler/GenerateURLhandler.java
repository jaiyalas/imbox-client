package org.imbox.server.pagehandler;
import java.io.IOException;


import org.imbox.server.functions.Httpresponser;
import org.imbox.server.jsonreaders.Accountfilenamereader;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


public class GenerateURLhandler implements HttpHandler
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
					System.out.println("this is a http get method @ generateurl, post should be used");
					String response = "this is a http get method @ generateurl";
					Httpresponser res = new Httpresponser(httpconnection, response);
					res.execute();
				}else
				{
					if (httpconnection.getRequestMethod().equals("POST"))
					{
						System.out.println("this is a post method @ generateurl");
						Accountfilenamereader reader = new Accountfilenamereader(httpconnection);
						System.out.println("account = " + reader.getaccount());
						System.out.println("filename = " + reader.getfilename());
						JSONObject obj=new JSONObject();
						obj.put("URL", "http://localhost/");// TODO: URL should be here
						obj.put("succ", false);
						obj.put("errorcode", 2);  // TODO: change errorcode if needed
						String response = obj.toString();
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
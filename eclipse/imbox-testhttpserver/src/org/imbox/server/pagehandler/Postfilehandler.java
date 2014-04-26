package org.imbox.server.pagehandler;
import java.io.IOException;


import org.imbox.server.functions.Authenticator;
import org.imbox.server.functions.Httpresponser;
import org.imbox.server.functions.Serverblockwriter;
import org.imbox.server.jsonreaders.Postfilereader;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


public class Postfilehandler implements HttpHandler
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
					System.out.println("this is a http get method @ postfile, post should be used");
					String response = "this is a http get method @ postfile";
					Httpresponser res = new Httpresponser(httpconnection, response);
					res.execute();
				}else
				{
					if (httpconnection.getRequestMethod().equals("POST"))
					{
						System.out.println("this is a post method @ postfile");
						Postfilereader reader = new Postfilereader(httpconnection);
						System.out.println("token = " + reader.gettoken());
						System.out.println("MAC = " + reader.getmac());
						System.out.println("blockname = " + reader.getblockname());
						System.out.println("blockdata = " + reader.getdatastring());
						System.out.println("sequence = " + Integer.toString(reader.getsequence()));
						Authenticator auth = new Authenticator();
						if (auth.Authenticatebytoken(reader.gettoken(), reader.getmac()))
						{
							Serverblockwriter bw = new Serverblockwriter();
							if (bw.writeblock(reader.getblockname(), reader.getdatastring()))
							{
								JSONObject obj=new JSONObject();
								obj.put("succ", true);
								obj.put("errorcode", 0);  // TODO: change errorcode if needed
								String response = obj.toString();
								Httpresponser res = new Httpresponser(httpconnection, response);
								res.execute();
							}else
							{
								JSONObject obj=new JSONObject();
								obj.put("succ", false);
								obj.put("errorcode", 4);  // TODO: change errorcode if needed
								String response = obj.toString();
								Httpresponser res = new Httpresponser(httpconnection, response);
								res.execute();
							}
						}else
						{
							JSONObject obj=new JSONObject();
							obj.put("succ", false);
							obj.put("errorcode", 1);  // TODO: change errorcode if needed
							String response = obj.toString();
							Httpresponser res = new Httpresponser(httpconnection, response);
							res.execute();
						}
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
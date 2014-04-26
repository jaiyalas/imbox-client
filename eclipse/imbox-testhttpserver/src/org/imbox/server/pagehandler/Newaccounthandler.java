package org.imbox.server.pagehandler;
import java.io.IOException;
import java.io.OutputStream;


import org.imbox.server.functions.Authenticator;
import org.imbox.server.functions.Httpresponser;
import org.imbox.server.jsonreaders.Loginrequestreader;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


public class Newaccounthandler implements HttpHandler
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
					//TODO: modify if get method is needed
					System.out.println("this is a http get method @ newaccount");
					String response = "this is a http get method";
					Httpresponser res = new Httpresponser(httpconnection, response);
					res.execute();
				}else
				{
					if (httpconnection.getRequestMethod().equals("POST"))
					{
						System.out.println("this is a post method @ newaccount");
						Loginrequestreader requestreader = new Loginrequestreader(httpconnection);
						System.out.println("account = " + requestreader.getaccount());
						System.out.println("password = " + requestreader.getpassword());
						System.out.println("MAC = " + requestreader.getMAC());
						//TODO:  createaccount(requestreader.getaccount(),requestreader.getpassword(),requestreader.getMAC());
						//TODO: getresult();
						Authenticator auth = new Authenticator();
						String token = auth.Createaccount(requestreader.getaccount(), requestreader.getpassword(), requestreader.getMAC());
						if (token.length() >0)
						{
							JSONObject obj=new JSONObject();
							obj.put("token", token);  //TODO: token here
							obj.put("succ", true);
							obj.put("errorcode", 0);
							String response = obj.toString();
							Httpresponser res = new Httpresponser(httpconnection, response);
							res.execute();
						}else
						{
							JSONObject obj=new JSONObject();
							obj.put("token", token);  //TODO: no token here
							obj.put("succ", false);
							obj.put("errorcode", 2);		 //TODO: error code here if any
							String response = obj.toString();
							Httpresponser res = new Httpresponser(httpconnection, response);
							res.execute();
						}
					}else
					{
						System.out.println("@newaccount - unknown method:" + httpconnection.getRequestMethod());
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
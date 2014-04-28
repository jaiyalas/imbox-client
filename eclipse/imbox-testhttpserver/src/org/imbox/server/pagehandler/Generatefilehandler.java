package org.imbox.server.pagehandler;
import java.io.IOException;
import java.net.URI;

import org.imbox.server.functions.Authenticator;
import org.imbox.server.functions.Httpresponser;
import org.imbox.server.functions.Uriparser;
import org.imbox.server.jsonreaders.Accountfilenamereader;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


public class Generatefilehandler implements HttpHandler
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
		public void run() {
			try
			{
				if (httpconnection.getRequestMethod().equals("GET"))
				{
					System.out.println("this is a http get method @ generatefile, post should be used");
					URI responsemap;
					responsemap = httpconnection.getRequestURI();
					System.out.println(responsemap.toString());
					Uriparser up = new Uriparser(responsemap.toString());
					String md5 = up.getmd5();
					String filename = up.getfilename();
				    //TODO: get file by md5 + filename and return as body?
				    
					String response = "this is a http get method @ generatefile";
					Httpresponser res = new Httpresponser(httpconnection, response);
					res.execute();
				}else
				{
					if (httpconnection.getRequestMethod().equals("POST"))
					{
						System.out.println("this is a post method @ generatefile");
						Accountfilenamereader reader = new Accountfilenamereader(httpconnection);
						System.out.println("account = " + reader.getaccount());
						System.out.println("filename = " + reader.getfilename());
						System.out.println("MAC = " + reader.getMAC());
						System.out.println("token = " + reader.gettoken());
						Authenticator auth = new Authenticator();
						if (auth.Authenticatebytoken(reader.gettoken(), reader.getMAC()))
						{
							//authenticate success
							//TODO: getfile here
							String data = "datastring";
							JSONObject obj=new JSONObject();
							obj.put("data", data);
							obj.put("succ", true);
							obj.put("errorcode", 0);
							String response = obj.toString();
							Httpresponser res = new Httpresponser(httpconnection, response);
							res.execute();
						}else
						{
							String data = new String();
							JSONObject obj=new JSONObject();
							obj.put("data", data);
							obj.put("succ", false);
							obj.put("errorcode", 1);
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
package org.imbox.server.pagehandler;

import java.io.IOException;

import org.imbox.database.Userfilelistgetter;
import org.imbox.server.functions.Authenticator;
import org.imbox.server.functions.Filelistgetter;
import org.imbox.server.functions.Httpresponser;
import org.imbox.server.jsonreaders.TokenMACreader;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class Syncrequesthandler implements HttpHandler
{
	private HttpExchange httpconnection;
	private String connectionIP;
	@Override
	public void handle(HttpExchange httpconnection) throws IOException {
		this.httpconnection = httpconnection;
		connectionIP = httpconnection.getRemoteAddress().getAddress().toString();
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
					System.out.println("this is a http get method @ syncrequest, post should be used");
					String response = "this is a http get method @ syncrequest";
					Httpresponser res = new Httpresponser(httpconnection, response);
					res.execute();
				}else
				{
					if (httpconnection.getRequestMethod().equals("POST"))
					{
						System.out.println("this is a post method @ syncrequest");
						TokenMACreader reader = new TokenMACreader(httpconnection);
						System.out.println("MAC = " + reader.getMAC());
						System.out.println("token = " + reader.gettoken());
						Authenticator auth = new Authenticator();
						if (auth.Authenticatebytoken(reader.gettoken(), reader.getMAC(),connectionIP))
						{
							//authenticate success
							//TODO: return array of file owned by this account
							Userfilelistgetter ufg = new Userfilelistgetter(auth.getaccountname());
							ufg.preparefilelist();
							Filelistgetter fg = new Filelistgetter(ufg.getlist());
							fg.preparejsonarray();
							JSONObject obj=new JSONObject();
							obj.put("filelist", fg.getjsonarray());
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
				try
				{
					String data = new String();
					JSONObject obj=new JSONObject();
					obj.put("data", data);
					obj.put("succ", false);
					obj.put("errorcode", 4);
					String response = obj.toString();
					Httpresponser res = new Httpresponser(httpconnection, response);
					res.execute();
					System.out.println("the below error has happen in 'Syncrequesthandler'");
					e.printStackTrace();
				}catch (Exception layer2e)
				{
					System.out.println("the below error has happen in 'Syncrequesthandler',layer2exception");
					layer2e.printStackTrace();
				}
			}
		}
	}
}
package org.imbox.server.pagehandler.file;

import java.io.IOException;

import org.imbox.database.Delete_File_user;
import org.imbox.database.Filefidgetter;
import org.imbox.server.functions.Authenticator;
import org.imbox.server.functions.Httpresponser;
import org.imbox.server.jsonreaders.Deletefilereader;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class Deletefilehandler implements HttpHandler
{
	private HttpExchange httpconnection;
	private String IP;
	@Override
	public void handle(HttpExchange httpconnection) throws IOException {
		this.httpconnection = httpconnection;
		IP = httpconnection.getRemoteAddress().getAddress().toString();
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
					System.out.println("this is a http get method @ deletefilehandler");
					String response = "this is a http get method";
					Httpresponser res = new Httpresponser(httpconnection, response);
					res.execute();
				}else
				{
					if (httpconnection.getRequestMethod().equals("POST"))
					{
						Authenticator auth = new Authenticator();
						Deletefilereader reader = new Deletefilereader(httpconnection);
						reader.readjson();
						if (auth.Authenticatebytoken(reader.gettoken(), reader.getmac(), IP))
						{
							Filefidgetter ffg = new Filefidgetter(auth.getaccountname(), reader.getfilename());
							ffg.preparefileid();
							String fid = ffg.getfileid();
							//TODO: becuase file md5 currently = fid
							Delete_File_user dfu = new Delete_File_user(auth.getaccountname(), fid);
							dfu.DeleteFileUser();
							if (dfu.getFileDelete())
							{
								JSONObject obj = new JSONObject();
								obj.put("succ", true);
								obj.put("errorcode", 0);
								String response = obj.toString();
								Httpresponser res = new Httpresponser(httpconnection, response);
								res.execute();
							}else
							{
								JSONObject obj = new JSONObject();
								obj.put("succ", false);
								obj.put("errorcode", 6);
								String response = obj.toString();
								Httpresponser res = new Httpresponser(httpconnection, response);
								res.execute();
							}
						}else
						{
							JSONObject obj = new JSONObject();
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
					System.out.println("the below error has happen in 'Deletefilehandler'");
					JSONObject obj=new JSONObject();
					obj.put("succ", false);
					obj.put("errorcode", 4);
					String response = obj.toString();
					Httpresponser res = new Httpresponser(httpconnection, response);
					res.execute();
					e.printStackTrace();
				}catch(Exception layer2e)
				{
					System.out.println("the below error has happen in 'Deletefilehandler',layer2exception");
					layer2e.printStackTrace();
				}
			}
		}
	}
	
}
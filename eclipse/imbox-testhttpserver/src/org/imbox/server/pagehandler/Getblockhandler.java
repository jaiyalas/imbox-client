package org.imbox.server.pagehandler;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.imbox.infrastructure.Workspace;
import org.imbox.infrastructure.file.Block;
import org.imbox.server.functions.Authenticator;
import org.imbox.server.functions.Httpresponser;
import org.imbox.server.jsonreaders.Getfilereader;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


public class Getblockhandler implements HttpHandler
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
					System.out.println("this is a http get method @ getfile, post should be used");
					String response = "this is a http get method @ getfile";
					Httpresponser res = new Httpresponser(httpconnection, response);
					res.execute();
				}else
				{
					if (httpconnection.getRequestMethod().equals("POST"))
					{
						System.out.println("this is a post method @ getfile");
						Getfilereader reader = new Getfilereader(httpconnection);
						System.out.println("token = " + reader.gettoken());
						System.out.println("MAC = "+ reader.getmac());
						System.out.println("blockname = " + reader.getblockname());
						System.out.println("filename = " + reader.getfilename());
						System.out.println("sequence = " + Integer.toString(reader.getsequence()));
						//TODO: get file here, attach to data, change type if needed
						Authenticator auth = new Authenticator();
						if (auth.Authenticatebytoken(reader.gettoken(), reader.getmac()))
						{
							//token is correct
							//get path from DB
							byte[] bytedata = Block.readBlockFromHD(Workspace.SYSDIRs, reader.getblockname());
							Base64 base64 = new Base64();
							String data = base64.encodeAsString(bytedata);
							//return result
							if (data.length() >0)
							{
								JSONObject obj=new JSONObject();
								obj.put("succ", true);
								obj.put("data", data);  // TODO: change type if needed
								obj.put("errorcode", 0); // TODO: add errorcode if needed
								String response = obj.toString();
								Httpresponser res = new Httpresponser(httpconnection, response);
								res.execute();
							}else
							{
								JSONObject obj=new JSONObject();
								obj.put("succ", false);
								obj.put("data", data);  // TODO: change type if needed
								obj.put("errorcode", 3); // TODO: add errorcode if needed
								String response = obj.toString();
								Httpresponser res = new Httpresponser(httpconnection, response);
								res.execute();
							}
						}else
						{
							JSONObject obj=new JSONObject();
							obj.put("succ", false);
							obj.put("data", new String());  // TODO: change type if needed
							obj.put("errorcode", 1); // TODO: add errorcode if needed
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
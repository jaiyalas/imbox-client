package org.imbox.server.pagehandler.block;
import java.io.IOException;
import java.security.MessageDigest;

import org.imbox.database.Filefidgetter;
import org.imbox.database.Insert_block_V2;
import org.imbox.infrastructure.Casting;
import org.imbox.infrastructure.Workspace;
import org.imbox.infrastructure.file.Block;
import org.imbox.server.functions.Authenticator;
import org.imbox.server.functions.Httpresponser;
import org.imbox.server.jsonreaders.Postblockreader;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


public class Postblockhandler implements HttpHandler
{
	private HttpExchange httpconnection;
	private String connectionIP;
	@Override
	public void handle(HttpExchange httpconnection) throws IOException 
	{
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
					System.out.println("this is a http get method @ postfile, post should be used");
					String response = "this is a http get method @ postfile";
					Httpresponser res = new Httpresponser(httpconnection, response);
					res.execute();
				}else
				{
					if (httpconnection.getRequestMethod().equals("POST"))
					{
						System.out.println("this is a post method @ postfile");
						Postblockreader reader = new Postblockreader(httpconnection);
						reader.getjson();
						System.out.println("token = " + reader.gettoken());
						System.out.println("MAC = " + reader.getmac());
						System.out.println("filename = " + reader.getfilename());
						System.out.println("blockdata = " + reader.getdatastring());
						System.out.println("sequence = " + Integer.toString(reader.getsequence()));
						Authenticator auth = new Authenticator();
						if (auth.Authenticatebytoken(reader.gettoken(), reader.getmac(),connectionIP))
						{
							byte[] blockdata = Casting.stringToBytes(reader.getdatastring());
							Filefidgetter ffg = new Filefidgetter(auth.getaccountname(), reader.getfilename());
							ffg.preparefileid();
							byte[] md5array = blockdata.clone();
							MessageDigest md5calculator = MessageDigest.getInstance("md5");
							md5calculator.update(md5array);
							Insert_block_V2 ib2 = new Insert_block_V2(Casting.bytesToString(md5array), reader.getsequence(), ffg.getfileid());
							ib2.InsertBlock();
							Block.writeBlock(Workspace.SYSDIRs, blockdata);
							JSONObject obj=new JSONObject();
							obj.put("succ", true);
							obj.put("errorcode", 0);
							String response = obj.toString();
							Httpresponser res = new Httpresponser(httpconnection, response);
							res.execute();
						}else
						{
							JSONObject obj=new JSONObject();
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
					System.out.println("the below error has happen in 'Postblockhandler'");
					JSONObject obj=new JSONObject();
					obj.put("succ", false);
					obj.put("errorcode", 1);
					String response = obj.toString();
					Httpresponser res = new Httpresponser(httpconnection, response);
					res.execute();
					e.printStackTrace();
					}catch(Exception layer2e)
				{
					System.out.println("the below error has happen in 'Postblockhandler',layer2exception");
					layer2e.printStackTrace();
				}
			}
		}
		
	}
}
package pagehandler;
import java.io.IOException;
import java.io.OutputStream;

import jsonreader.Getfilereader;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class Getfilehandler implements HttpHandler
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
					httpconnection.sendResponseHeaders(200, response.length());
		            OutputStream os = httpconnection.getResponseBody();
		            os.write(response.getBytes());
		            os.close();
				}else
				{
					if (httpconnection.getRequestMethod().equals("POST"))
					{
						System.out.println("this is a post method @ getfile");
						Getfilereader reader = new Getfilereader(httpconnection);
						System.out.println("token = " + reader.gettoken());
						System.out.println("blockname = " + reader.getblockname());
						System.out.println("filename = " + reader.getfilename());
						System.out.println("sequence = " + Integer.toString(reader.getsequence()));
						//TODO: get file here, attach to data, change type if needed
						String data = "astring";
						JSONObject obj=new JSONObject();
						obj.put("succ", false);
						obj.put("data", data);  // TODO: change type if needed
						obj.put("errorcode", 2); // TODO: add errorcode if needed
						String response = obj.toString();
						httpconnection.sendResponseHeaders(200, response.length());
						OutputStream os = httpconnection.getResponseBody();
				        os.write(response.getBytes());
				        os.close();
					}else
					{
						System.out.println("unknown method:" + httpconnection.getRequestMethod());
						String response = "this is a unkown method"+ httpconnection.getRequestMethod();
						httpconnection.sendResponseHeaders(200, response.length());
			            OutputStream os = httpconnection.getResponseBody();
			            os.write(response.getBytes());
			            os.close();
					}
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}
	
}
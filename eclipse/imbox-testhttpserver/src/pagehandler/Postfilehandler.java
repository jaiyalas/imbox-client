package pagehandler;
import java.io.IOException;
import java.io.OutputStream;

import jsonreader.Postfilereader;

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
					httpconnection.sendResponseHeaders(200, response.length());
		            OutputStream os = httpconnection.getResponseBody();
		            os.write(response.getBytes());
		            os.close();
				}else
				{
					if (httpconnection.getRequestMethod().equals("POST"))
					{
						System.out.println("this is a post method @ postfile");
						Postfilereader reader = new Postfilereader(httpconnection);
						System.out.println("token = " + reader.gettoken());
						System.out.println("blockdata = " + reader.getdatastring());
						System.out.println("sequence = " + Integer.toString(reader.getsequence()));
						// TODO: currently always response success
						JSONObject obj=new JSONObject();
						obj.put("succ", true);
						obj.put("errorcode", 0);  // TODO: change errorcode if needed
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
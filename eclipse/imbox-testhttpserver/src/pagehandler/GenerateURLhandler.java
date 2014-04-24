package pagehandler;
import java.io.IOException;
import java.io.OutputStream;

import jsonreader.Accountfilenamereader;

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
					httpconnection.sendResponseHeaders(200, response.length());
		            OutputStream os = httpconnection.getResponseBody();
		            os.write(response.getBytes());
		            os.close();
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
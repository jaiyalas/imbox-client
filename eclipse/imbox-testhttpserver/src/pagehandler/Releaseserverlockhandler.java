package pagehandler;
import java.io.IOException;
import java.io.OutputStream;

import jsonreader.TokenMACreader;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class Releaseserverlockhandler implements HttpHandler
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
					System.out.println("this is a http get method @ releaseserverlock");
					String response = "this is a http get method @ releaseserverlock";
					httpconnection.sendResponseHeaders(200, response.length());
		            OutputStream os = httpconnection.getResponseBody();
		            os.write(response.getBytes());
		            os.close();
				}else
				{
					if (httpconnection.getRequestMethod().equals("POST"))
					{
						System.out.println("this is a post method @ releaseserverlock");
						TokenMACreader requestreader = new TokenMACreader(httpconnection);
						System.out.println("token = " + requestreader.gettoken());
						System.out.println("MAC = " + requestreader.getMAC());
						//TODO: release server lock here
						//TODO: getresult();
						boolean result = false; 
						if (result)
						{
							JSONObject obj=new JSONObject();
							obj.put("succ", true);
							obj.put("errorcode", 0);        //TODO: error code here if any
							String response = obj.toString();
							httpconnection.sendResponseHeaders(200, response.length());
							OutputStream os = httpconnection.getResponseBody();
						    os.write(response.getBytes());
						    os.close();
						}else
						{
							JSONObject obj=new JSONObject();
							obj.put("succ", false);
							obj.put("errorcode", 2);		 //TODO: error code here if any
							String response = obj.toString();
							httpconnection.sendResponseHeaders(200, response.length());
							OutputStream os = httpconnection.getResponseBody();
						    os.write(response.getBytes());
						    os.close();
						}
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
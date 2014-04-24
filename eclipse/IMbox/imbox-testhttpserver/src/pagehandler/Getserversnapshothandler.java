package pagehandler;
import java.io.IOException;
import java.io.OutputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class Getserversnapshothandler implements HttpHandler
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
					System.out.println("this is a http get method @ getsystemsnapshot");
					JSONObject obj = new JSONObject();
					obj.put("snapshot", new JSONArray("[\"this is a jsonArray value\",\"another jsonArray value\"]"));    //TODO: attach system snap shot to ""
					String response = obj.toString();
					httpconnection.sendResponseHeaders(200, response.length());
					OutputStream os = httpconnection.getResponseBody();
			        os.write(response.getBytes());
			        os.close();
				}else
				{
					if (httpconnection.getRequestMethod().equals("POST"))
					{
						System.out.println("this is a post method @ getsystemsnapshot");
						String response = "this is a http post method @ getsystemsnapshot";
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
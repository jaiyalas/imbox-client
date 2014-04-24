package pagehandler;

import java.io.OutputStream;

import jsonreader.Loginrequestreader;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class Loginhandler implements HttpHandler
{
	private HttpExchange httpconnection;
	@Override
	public void handle(HttpExchange httpconnection)
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
					//TODO: modify if get method is needed
					System.out.println("this is a http get method @ login");
					String response = "this is a http get method";
					httpconnection.sendResponseHeaders(200, response.length());
		            OutputStream os = httpconnection.getResponseBody();
		            os.write(response.getBytes());
		            os.close();
				}else
				{
					if (httpconnection.getRequestMethod().equals("POST"))
					{
						System.out.println("this is a post method @ login");
						Loginrequestreader requestreader = new Loginrequestreader(httpconnection);
						System.out.println("account = " + requestreader.getaccount());
						System.out.println("password = " + requestreader.getpassword());
						//TODO:  createaccount(requestreader.getaccount(),requestreader.getpassword(),requestreader.getMAC());
						//TODO: getresult();
						boolean result = false; 
						if (result)
						{
							JSONObject obj=new JSONObject();
							obj.put("token", new String());  //TODO: token here
							obj.put("succ", true);
							obj.put("errorcode", 0);
							String response = obj.toString();
							httpconnection.sendResponseHeaders(200, response.length());
							OutputStream os = httpconnection.getResponseBody();
				            os.write(response.getBytes());
				            os.close();
						}else
						{
							JSONObject obj=new JSONObject();
							obj.put("token", new String());  //TODO: login fail token here
							obj.put("succ", false);
							obj.put("errorcode", 1);		 //TODO: error code here if any
							String response = obj.toString();
							httpconnection.sendResponseHeaders(200, response.length());
							OutputStream os = httpconnection.getResponseBody();
				            os.write(response.getBytes());
				            os.close();
						}
					}else
					{
						//TODO:modify if other method is needed
						System.out.println("@login - unknown method:" + httpconnection.getRequestMethod());
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
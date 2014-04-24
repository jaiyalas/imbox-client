package pagehandler;
import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class Networkcheckhandler implements HttpHandler
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
				System.out.println("client performing network checking");
				String response = "network is fine.";
				httpconnection.sendResponseHeaders(200, response.length());
			    OutputStream os = httpconnection.getResponseBody();
			    os.write(response.getBytes());
			    os.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}
}
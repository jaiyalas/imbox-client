package main;

import java.net.InetSocketAddress;

import pagehandler.GenerateURLhandler;
import pagehandler.Generatefilehandler;
import pagehandler.Getfilehandler;
import pagehandler.Getserverlockhandler;
import pagehandler.Getserversnapshothandler;
import pagehandler.Loginhandler;
import pagehandler.Networkcheckhandler;
import pagehandler.Newaccounthandler;
import pagehandler.Postfilehandler;
import pagehandler.Releaseserverlockhandler;

import com.sun.net.httpserver.HttpServer;


public class IMboxserver
{
	private HttpServer server;
	public IMboxserver()
	{
		try{
		server = HttpServer.create(new InetSocketAddress(80), 0);
		server.createContext("/createaccount",new Newaccounthandler());
		server.createContext("/login", new Loginhandler());
		server.createContext("/getserversnapshot",new Getserversnapshothandler());
		server.createContext("/getserverlock",new Getserverlockhandler());
		server.createContext("/releaseserverlock",new Releaseserverlockhandler());
		server.createContext("/getfile",new Getfilehandler());
		server.createContext("/postfile",new Postfilehandler());
		server.createContext("/generateURL",new GenerateURLhandler());
		server.createContext("/generatefile",new Generatefilehandler());
		server.createContext("/networkcheck",new Networkcheckhandler());
        server.setExecutor(null); // creates a default executor
        System.out.println("the server is ready, please use startserver command to start");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void startserver()
	{
		server.start();
	}
	
	public void closeserver()
	{
		server.stop(0);
	}
}
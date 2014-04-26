package org.imbox.server.main;

import java.net.InetSocketAddress;

import org.imbox.server.pagehandler.GenerateURLhandler;
import org.imbox.server.pagehandler.Generatefilehandler;
import org.imbox.server.pagehandler.Getfilehandler;
import org.imbox.server.pagehandler.Getserverlockhandler;
import org.imbox.server.pagehandler.Getserversnapshothandler;
import org.imbox.server.pagehandler.Loginhandler;
import org.imbox.server.pagehandler.Networkcheckhandler;
import org.imbox.server.pagehandler.Newaccounthandler;
import org.imbox.server.pagehandler.Postfilehandler;
import org.imbox.server.pagehandler.Releaseserverlockhandler;


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
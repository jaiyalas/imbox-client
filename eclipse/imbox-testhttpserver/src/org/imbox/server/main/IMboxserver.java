package org.imbox.server.main;

import java.net.InetSocketAddress;

import org.imbox.infrastructure.Workspace;
import org.imbox.server.pagehandler.GenerateURLhandler;
import org.imbox.server.pagehandler.Generatefilehandler;
import org.imbox.server.pagehandler.Getblockrecordhandler;
import org.imbox.server.pagehandler.Getblockhandler;
import org.imbox.server.pagehandler.Getserverlockhandler;
import org.imbox.server.pagehandler.Loginhandler;
import org.imbox.server.pagehandler.Networkcheckhandler;
import org.imbox.server.pagehandler.Postblockhandler;
import org.imbox.server.pagehandler.Releaseserverlockhandler;
import org.imbox.server.pagehandler.Syncrequesthandler;

import com.sun.net.httpserver.HttpServer;


public class IMboxserver
{
	private HttpServer server;
	public IMboxserver()
	{
		try{
		server = HttpServer.create(new InetSocketAddress(8080), 0);
		//server.createContext("/createaccount",new Newaccounthandler());
		server.createContext("/login", new Loginhandler());
		//server.createContext("/getserversnapshot",new Getserversnapshothandler());
		server.createContext("/getserverlock",new Getserverlockhandler());
		server.createContext("/releaseserverlock",new Releaseserverlockhandler());
		server.createContext("/getblock",new Getblockhandler());
		server.createContext("/postblock",new Postblockhandler());
		server.createContext("/generateURL",new GenerateURLhandler());
		server.createContext("/generatefile",new Generatefilehandler());
		server.createContext("/networkcheck",new Networkcheckhandler());
		server.createContext("/syncrequest",new Syncrequesthandler());
		server.createContext("/getblockrecord",new Getblockrecordhandler());
        server.setExecutor(null); // creates a default executor
        System.out.println("the server is ready, please use startserver command to start");
        Workspace.prepareWorkspaceS();
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
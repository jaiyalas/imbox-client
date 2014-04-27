package org.imbox.client.networkrelated.ultility;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;


public class Simpleconnection 
{
    //private String URL = "http://54.254.1.241";//base domain
private String URL = "http://localhost";//base domain
	private HttpClient connectionmanager;
	
	public Simpleconnection()
	{
		connectionmanager = HttpClientBuilder.create().build();
	}
	
	public HttpResponse httppost(String targetpage,JSONObject obj)
	{
		
		try {
			String targethost = URL+"/"+targetpage;
			HttpPost httppost = new HttpPost(targethost);
			httppost.setEntity(new StringEntity(obj.toString()));
			HttpResponse res = connectionmanager.execute(httppost);
			return res;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public HttpResponse httpget(String targetpage)
	{
		try
		{
			HttpGet httpget = new HttpGet(URL+"/"+targetpage);
			return connectionmanager.execute(httpget);
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}

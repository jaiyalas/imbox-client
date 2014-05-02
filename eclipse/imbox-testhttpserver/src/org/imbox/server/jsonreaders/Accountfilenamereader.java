//package org.imbox.server.jsonreaders;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
//import org.imbox.infrastructure.exceptions.IMBOXNW_jsonException;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import com.sun.net.httpserver.HttpExchange;
//
//public class Accountfilenamereader
//{
//	private String MAC;
//	private String token;
//	private String filename;
//	private String accountname;
//	private String jsonstring;
//	private HttpExchange request;
//	
//	public Accountfilenamereader(HttpExchange httpconnection) throws IOException, IMBOXNW_jsonException
//	{
//		request = httpconnection;
//		token = new String();
//		filename = new String();
//		accountname = new String();
//		getjson();
//	}
//	
//	private void getjson() throws IOException, IMBOXNW_jsonException
//	{
//		try {
//			InputStreamReader requestreader =  new InputStreamReader(request.getRequestBody(),"utf-8");
//			BufferedReader br = new BufferedReader(requestreader);
//			jsonstring = br.readLine();
//			br.close();
//			requestreader.close();
//			parsejson();
//		} catch (IOException e) {
//			e.printStackTrace();
//			throw new IOException("Accountfilenamereader.getjson");
//		}
//	}
//	
//	private void parsejson() throws IMBOXNW_jsonException
//	{
//		try {
//			JSONObject obj = new JSONObject(jsonstring);
//			MAC = obj.getString("MAC");
//			token = obj.getString("token");
//			accountname = obj.getString("account");
//			filename = obj.getString("filename");
//		} catch (JSONException e) {
//			e.printStackTrace();
//			throw new IMBOXNW_jsonException("Accountfilenamereader.parsejson");
//		}
//		
//	}
//	
//	public String getfilename()
//	{
//		return filename;
//	}
//	
//	public String getaccount()
//	{
//		return accountname;
//	}
//	
//	public String getMAC()
//	{
//		return MAC;
//	}
//	
//	public String gettoken()
//	{
//		return token;
//	}
//}
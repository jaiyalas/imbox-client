package org.imbox.server.functions;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Serverblockreader
{
	public Serverblockreader()
	{
		
	}
	
	public String getbyteofblock(byte[] data)
	{
		String datastring;
		datastring = arraytostring(data);
		return datastring;
	}
	
	public String getbyteofblockbypath(String path)
	{
		try
		{
			File targetblock = new File(path);
			byte[] data = Files.readAllBytes(targetblock.toPath());
			return arraytostring(data);
		}catch(Exception e)
		{
			System.out.println("get byte of block by path failed.");
			e.printStackTrace();
			return new String();
		}
	}
	
	private String arraytostring (byte[] bytes)
	{
		 StringBuffer buff = new StringBuffer();
		 for(int i=0;i<bytes.length;i++)
		 {
			 buff.append(bytes[i] + ":");
		 }
		 return buff.toString();
	}
}
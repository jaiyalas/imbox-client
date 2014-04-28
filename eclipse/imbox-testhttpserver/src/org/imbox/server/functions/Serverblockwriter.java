package	org.imbox.server.functions;

import java.io.File;
import java.io.FileOutputStream;

public class Serverblockwriter
{
	private FileOutputStream writer;
	private String blockstoragebase;
	public Serverblockwriter()
	{
		checkfordirectory();
		blockstoragebase = "storage/blocks";
	}
	
	public boolean writeblock(String blockname, String data )
	{
		File blockfile = new File(blockstoragebase + "/" + blockname);
		if (blockfile.exists())
		{
			System.out.println("the file already exist, is it possible?");
			return false;
		}else
		{
			System.out.println("start writing file...");
			try
			{
				writer = new FileOutputStream(blockfile);
				byte[] datainbytes = stringtoarray(data);
				writer.write(datainbytes);
				writer.close();
				return true;
			}catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("something wrong in the write block file process!");
				return false;
			}
		}
	}
	
	
	private void checkfordirectory()
	{
		File storagedirectory = new File("storage");
		if (!storagedirectory.exists())
		{
			System.out.println("storage directory does not exist, making one");
			if (storagedirectory.mkdir())
			{
				System.out.println("storage directory made");
			}else
			{
				System.out.println("something wrong with making directory");
			}
		}else
		{
			System.out.println("storage exist.");
		}
		
		File blockstorgaedirectory = new File("storage/blocks");
		if (!blockstorgaedirectory.exists())
		{
			System.out.println("block storage directory does not exist, making one");
			if (blockstorgaedirectory.mkdir())
			{
				System.out.println(" block storage directory made");
			}else
			{
				System.out.println("something wrong with making block directory");
			}
		}else
		{
			System.out.println("storage exist.");
		}
	}
	
	private byte[] stringtoarray(String target)
	{
		String[] token;
		token = target.split(":");
		byte[] answer = new byte[token.length];
		for(int i =0;i<token.length;i++)
		{
			answer[i] = Byte.parseByte(token[i]);
		}
		return answer;
	}
}
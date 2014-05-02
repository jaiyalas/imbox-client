package org.imbox.server.functions;

import java.io.File;
import java.util.List;

import org.imbox.database.search_block;


public class blocktofile
{
	private String md5;
	private byte[] fileinbyte;
	private File file;
	public blocktofile(String md5)
	{
		this.md5 = md5;
	}
	public void reconstruct()
	{
		search_block sb = new search_block(md5);
		sb.searchBlock();
		List<String> blocklist = sb.getList();
		//TODO: join as block
	}
	public byte[] getfileinbyte()
	{
		return fileinbyte;
	}
	public File getfile()
	{
		return file;
	}
}
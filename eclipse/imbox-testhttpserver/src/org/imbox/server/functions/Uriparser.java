package org.imbox.server.functions;



public class Uriparser
{
	private String uristring;
	private String md5;
	private String filename;
	public Uriparser(String uri)
	{
		this.uristring = uri;
		parse();
	}
	
	private void parse()
	{
		String[] lv1token = uristring.split("\\.");
		preparemap(lv1token);
	}
	
	private void preparemap(String[] token)
	{
		md5 = token[1];
		for (int i = 2;i<token.length;i++)
		{
			filename = filename + "." + token[i];
		}
	}
	
	public String getmd5()
	{
		return md5;
	}
	
	public String getfilename()
	{
		return filename;
	}
}

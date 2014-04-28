package org.imbox.server.functions;

import org.json.JSONArray;


public class Blocklistgetter
{
	private String account;
	private String filename;
	public Blocklistgetter(String account,String filename)
	{
		this.account = account;
		this.filename = filename;
	}
	
	public JSONArray getblocklist()
	{
		JSONArray returnarray = new JSONArray();
		try
		{
			//go to DB get List<BlockRec>
			//and put to json array  ['Blockname','sequence','Blockname','sequence']
			//NOTICE: BlockRec(String,int);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return returnarray;
	}
}
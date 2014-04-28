package	org.imbox.server.functions;

import org.json.JSONArray;


public class Filelistgetter
{
	private String account;
	public Filelistgetter(String account)
	{
		this.account = account;
	}
	
	public JSONArray getfilelist()
	{
		JSONArray returnarray = new JSONArray();
		try
		{
			//go to DB get List<FileRec>
			//and put to json array  ['filename','filemd5','filename','filemd5']
			//NOTICE: BlockRec(String,int);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return returnarray;
	}
	
}
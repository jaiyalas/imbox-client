package org.imbox.client.showapi;

import org.imbox.client.networkrelated.Accountcreator;
import org.imbox.client.networkrelated.Blockgetter;
import org.imbox.client.networkrelated.Blockposter;
import org.imbox.client.networkrelated.Connectionchecker;
import org.imbox.client.networkrelated.Loginmaster;
import org.imbox.client.networkrelated.Serverlockgetter;
import org.imbox.client.networkrelated.Serverlockreleaser;
import org.imbox.client.networkrelated.Serversnapshotgetter;
import org.imbox.client.networkrelated.URLgenerator;
import org.imbox.client.networkrelated.filegenerator;
import org.imbox.client.networkrelated.ultility.Internetrecord;
import org.imbox.infrastructure.Workspace;
import org.imbox.infrastructure.file.Block;



public class Supertester
{
	public static void main (String[]args)
	{
		//TODO: currently using token of ac.gettoken, pls fix it
		System.out.println("this is a super tester, will run through all the function needed");
		System.out.println("================================================================");
		System.out.println("testing target: create account ,  showing all function");
		Accountcreator ac = new Accountcreator("testaccount", "password");
		System.out.println("[create account]token = " + Internetrecord.gettoken());
		System.out.println("[create account]succ = " + ac.getstatus());
		System.out.println("[create account]errorcode = " + Integer.toString(ac.geterrorcode()));
		System.out.println("================================================================");
		System.out.println("testing target: login,  showing all function");
		Loginmaster lgm = new Loginmaster("testaccount", "password");
		lgm.authenticate();
		System.out.println("[login]token = " + Internetrecord.gettoken());
		System.out.println("[login]succ = " + lgm.getstatus());
		System.out.println("[login]errorcode = " + Integer.toString(lgm.geterrorcode()));
		System.out.println("================================================================");
		System.out.println("testing target: getserversnapshot,  showing all function");
		Serversnapshotgetter ssg = new Serversnapshotgetter();
		System.out.println("[Serversnapshot]snapshot = " + ssg.getsystemsnapshot());
		System.out.println("================================================================");
		System.out.println("testing target: getserverlock,  showing all function");
		Serverlockgetter slg = new Serverlockgetter();
		System.out.println("[getserverlock]succ = " + slg.getstatus());
		System.out.println("[getserverlock]errorcode = " + Integer.toString(slg.geterrorcode()));
		System.out.println("================================================================");
		System.out.println("testing target: releaseserverlock,  showing all function");
		Serverlockreleaser slr = new Serverlockreleaser();
		System.out.println("[releaseserverlock]succ = " + slr.getstatus());
		System.out.println("[releaseserverlock]errorcode = " + Integer.toString(slr.geterrorcode()));
		System.out.println("================================================================");
		System.out.println("testing target: generateURL,  showing all function");
		URLgenerator ug = new URLgenerator("testaccount", "testfilename");
		System.out.println("[generateURL]URL = " + ug.geturl());
		System.out.println("[generateURL]succ = " + ug.getstatus());
		System.out.println("[generateURL]errorcode = " + ug.geterrorcode());
		System.out.println("================================================================");
		System.out.println("testing target: generateFile,  showing all function");
		filegenerator fgg = new filegenerator("testaccount", "testfilename");
		System.out.println("[generateFile]data = " + fgg.getdata());
		System.out.println("[generateFile]succ = " + fgg.getstatus());
		System.out.println("[generateFile]errorcode = " + fgg.geterrorcode());
		System.out.println("=================================================================");
		System.out.println("testing target: connectionchecker,  showing all function");
		Connectionchecker cc = new Connectionchecker();
		System.out.println("[connectionchecker]network available: " + cc.checknetworkstatus());
		System.out.println("=================================================================");
		System.out.println("testing target: postfile,  showing all function");
		try
		{
			byte[] datablock = Block.readBlockFromHD(Workspace.SYSDIRs, "Cloud_Didtribution_Group2.pptx");
			Blockposter fp = new Blockposter( "theclientfile.txt",datablock, 0);
			System.out.println("[postfile]succ = " + fp.getstatus());
			System.out.println("[postfile]errorcode = " + fp.geterrorcode());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("================================================================");
		System.out.println("testing target: getfile,  showing all function");
		Blockgetter fg = new Blockgetter("theseverfile.txt", "blocks", 0);
		//Clientblockwriter bw = new Clientblockwriter();
		//bw.writeblock("therserverfile.txt", fg.getdata());
		System.out.println("[getfile]succ = " + fg.getstatus());
		System.out.println("[getfile]data = " + fg.getdata());
		System.out.println("[getfile]errorcode = " + fg.geterrorcode());
		System.out.println("================================================================");
		
		System.out.println("END OF TESTING............");
		
	}
}
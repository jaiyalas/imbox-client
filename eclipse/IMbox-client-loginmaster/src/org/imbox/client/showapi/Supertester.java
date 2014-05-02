package org.imbox.client.showapi;

import java.util.List;

import org.imbox.client.network.Connectionchecker;
import org.imbox.client.network.block.Blockgetter;
import org.imbox.client.network.block.Blockposter;
import org.imbox.client.network.block.Blockrecordgetter;
import org.imbox.client.network.file.Syncrequester;
import org.imbox.client.network.login.Loginmaster;
import org.imbox.client.network.sharelink.Filegenerator;
import org.imbox.client.network.sharelink.URLgenerator;
import org.imbox.client.network.ultility.Internetrecord;
import org.imbox.infrastructure.Workspace;
import org.imbox.infrastructure.file.Block;
import org.imbox.infrastructure.file.BlockRec;
import org.imbox.infrastructure.file.FileRec;



public class Supertester
{
	public static void main (String[]args)
	{
		try{
			System.out.println("this is a super tester, will run through all the function needed");
			System.out.println("================================================================");
	//		System.out.println("testing target: create account ,  showing all function");
	//		Accountcreator ac = new Accountcreator("testaccount", "password");
	//		ac.register();
	//		System.out.println("[create account]token = " + Internetrecord.gettoken());
	//		System.out.println("[create account]succ = " + ac.getstatus());
	//		System.out.println("[create account]errorcode = " + Integer.toString(ac.geterrorcode()));
			System.out.println("================================================================");
			System.out.println("testing target: login,  showing all function");
			Loginmaster lgm = new Loginmaster("testaccount", "password");
			lgm.authenticate();
			System.out.println("[login]token = " + Internetrecord.gettoken());
			System.out.println("[login]succ = " + lgm.getstatus());
			System.out.println("[login]errorcode = " + Integer.toString(lgm.geterrorcode()));
			System.out.println("================================================================");
			System.out.println("testing target: syncrequester,  showing all function");
			Syncrequester sq = new Syncrequester();
			sq.sendrequest();
			List<FileRec> resultofsyncrequest = sq.getfilelist();  // just for debug
			System.out.println("[syncrequester]filelist = " + sq.getfilelist().toString());
			System.out.println("[syncrequester]succ = " + sq.getstatus());
			System.out.println("[syncrequester]errorcode = " + Integer.toString(sq.geterrorcode()));
			System.out.println("================================================================");
			System.out.println("testing target: getblockrecord,  showing all function");
			Blockrecordgetter brg = new Blockrecordgetter("1000.txt");
			brg.sendrequest();
			List<BlockRec> resultofgetblockrecord = brg.getblocklist();  // just for debug
			System.out.println("[getblockrecord]blocklist = " +  brg.getblocklist().toString());
			System.out.println("[getblockrecord]succ = " + brg.getstatus());
			System.out.println("[getblockrecord]errorcode = " + Integer.toString(brg.geterrorcode()));
			System.out.println("================================================================");
			System.out.println("testing target: getblock,  showing all function");
			Blockgetter bg = new Blockgetter("block4", 0); //for test, name does not mean anything
			bg.sendrequest();
			Block resultblock = bg.getresultblock();
			System.out.println("[getblock]block = " + bg.getresultblock());
			System.out.println("[getblock]succ = " + bg.getstatus());
			System.out.println("[getblockrecord]errorcode = " + Integer.toString(bg.geterrorcode()));
			System.out.println("================================================================");
			System.out.println("testing target: getblock,  showing all function");
			try
			{
				Blockposter bp = new Blockposter("Screenshot000.jpg", Block.readBlockFromHD(Workspace.SYSDIRs, "Screenshot000.jpg"), 0);
				bp.sendrequest();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			System.out.println("[postblock]succ = " + bg.getstatus());
			System.out.println("[postblock]errorcode = " + Integer.toString(bg.geterrorcode()));
			System.out.println("================================================================");
			System.out.println("testing target: generateURL,  showing all function");
			URLgenerator ug = new URLgenerator("testaccount", "1000.txt");
			ug.sendrequest();
			System.out.println("[generateURL]URL = " + ug.geturl());
			System.out.println("[generateURL]succ = " + ug.getstatus());
			System.out.println("[generateURL]errorcode = " + ug.geterrorcode());
			System.out.println("testing target: generateFile,  showing all function");
			System.out.println("================================================================");
			Filegenerator fgg = new Filegenerator("testaccount", "testfilename");
			fgg.sendrequest();
			System.out.println("[generateFile]data = " + fgg.getdata());
			System.out.println("[generateFile]succ = " + fgg.getstatus());
			System.out.println("[generateFile]errorcode = " + fgg.geterrorcode());
			System.out.println("=================================================================");
			System.out.println("testing target: connectionchecker,  showing all function");
			Connectionchecker cc = new Connectionchecker();
			System.out.println("[connectionchecker]network available: " + cc.checknetworkstatus());
			System.out.println("=================================================================");
			
			
	//		System.out.println("testing target: getserverlock,  showing all function");
	//		Serverlockgetter slg = new Serverlockgetter();
	//		System.out.println("[getserverlock]succ = " + slg.getstatus());
	//		System.out.println("[getserverlock]errorcode = " + Integer.toString(slg.geterrorcode()));
	//		System.out.println("================================================================");
	//		System.out.println("testing target: releaseserverlock,  showing all function");
	//		Serverlockreleaser slr = new Serverlockreleaser();
	//		System.out.println("[releaseserverlock]succ = " + slr.getstatus());
	//		System.out.println("[releaseserverlock]errorcode = " + Integer.toString(slr.geterrorcode()));
	//		System.out.println("================================================================");
	//		
	
	//		System.out.println("testing target: postfile,  showing all function");
	//		try
	//		{
	//			byte[] datablock = Block.readBlockFromHD(Workspace.SYSDIRs, "Cloud_Didtribution_Group2.pptx");
	//			Blockposter fp = new Blockposter( "theclientfile.txt",datablock, 0);
	//			System.out.println("[postfile]succ = " + fp.getstatus());
	//			System.out.println("[postfile]errorcode = " + fp.geterrorcode());
	//		}catch(Exception e)
	//		{
	//			e.printStackTrace();
	//		}
	//		System.out.println("================================================================");
	//		System.out.println("testing target: getfile,  showing all function");
	//		Blockgetter fg = new Blockgetter("theseverfile.txt", "blocks", 0);
	//		//Clientblockwriter bw = new Clientblockwriter();
	//		//bw.writeblock("therserverfile.txt", fg.getdata());
	//		System.out.println("[getfile]succ = " + fg.getstatus());
	//		System.out.println("[getfile]data = " + fg.getdata());
	//		System.out.println("[getfile]errorcode = " + fg.geterrorcode());
			System.out.println("================================================================");
			
			System.out.println("END OF TESTING............");
		}catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("FOR TEST, CATCH ALL EXCEPTION");
		}
		
	}
}
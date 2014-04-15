package org.imbox.File.Block;

import java.io.*;
import org.apache.commons.io.FileUtils; 
import org.apache.commons.io.IOUtils;

public class Block{

    private byte[] bytes;
    private String blockName;
    private boolean prefixed;

    public byte[] getBytes() {return this.bytes;}
    public String getName()  {return this.blockName;}
    public boolean hasPrefix(){return this.prefixed;}

    private Block(){};
    private Block(byte[] inBytes){this.bytes = inBytes;};

    // should be sync. (?)
    public static Block genBlock(byte[] inBytes){
	Block b    = new Block(inBytes);
	b.prefixed = false;
	b.blockName   = "untitled";
	return b;
    };
    public static Block genBlock(byte[] inBytes,String rawName){
	Block b    = new Block(inBytes);
	b.prefixed = false;
	b.blockName   = rawName;
	return b;
    };   
    public static Block genBlock(byte[] inBytes,int bNum, int bIdx,String rawName){
	Block b    = new Block(inBytes);
	b.prefixed = true;
	b.blockName   = prefixComplete(bNum,bIdx,rawName);
	return b;
    };
    
    public static Block readBlock(int bNum, int bIdx,String rawBlockName) throws IOException{
	String bName = prefixComplete(bNum,bIdx,rawBlockName);
	InputStream is    = new FileInputStream(new File(bName));
	byte[]      bytes = IOUtils.toByteArray(is);
	is.close();
	Block b = genBlock(bytes,bName);
	b.prefixed = true;
	return b;
    };
    public static int writeBlock(Block b, String rawName) throws IOException{
	OutputStream os = new FileOutputStream(new File(rawName));
	IOUtils.write(b.getBytes(), os);
	os.flush();os.close();
	return b.getBytes().length;
    };
    
    private static String prefixComplete(int totalBlockNum, 
					 int blockIndex, 
					 String fullName){
	StringBuffer sb = new StringBuffer();
	sb.append(totalBlockNum);sb.append("_");
	sb.append(blockIndex);sb.append("_");
	sb.append(fullName);
	return sb.toString();
    };

}

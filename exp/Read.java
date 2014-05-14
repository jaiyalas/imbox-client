import java.io.*;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import java.util.*;

//for md5
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.FileUtils;

public class Read{

    public static void main(String[] args) throws IOException{

	/* === FILE SLIPT === */

	final String fullName = "SCAR「ジョーカー_許されざる捜査官」ドラマ主題歌) - YouTube.mp4";
	int blocksize = 700;

        RandomAccessFile file    = new RandomAccessFile(fullName,"r");
        FileChannel      channel = file.getChannel();
        ByteBuffer       buffer  = ByteBuffer.allocate(blocksize);
	
	int bNum = (int) Math.ceil(channel.size() / (double) blocksize);
	int bIdx = 0;
        byte[] bytes;

        while(channel.read(buffer) > 0){
	    //reparing for loading next block
            buffer.flip(); // reset index of ByteBuffer
            bIdx+=1;       // succ block index
	    bytes = new byte[buffer.limit()];
	    //loading block (as a byte[])
	    for (int i = 0; i < buffer.limit(); i++){bytes[i] = buffer.get();}
	    //save one block from byte[]
	    bytes2Block(bytes, prefixComplete(bNum,bIdx,fullName));
	    buffer.clear(); 
            
	    System.out.println(Read.hashMd5(bytes));
        }
        channel.close();
        file.close();	

	/* === FILE REUNION === */
	filesReunification(bNum,fullName);
    }

    private static int filesReunification(int bNum, String fullName){
	try{
	    OutputStream os = new FileOutputStream(new File("new___"+fullName));
	    for(int bIdx = 1; bIdx <= bNum; bIdx++){
		byte[] bytes = block2Bytes(prefixComplete(bNum,bIdx,fullName));
		IOUtils.write(bytes, os);
	    }
	    os.flush();
	    os.close();
	    return(0);
	}catch(IOException e){
	    System.out.println(e.toString());
	    return(-1);
	}
    }

    
/* ==================================================== */
    private static String prefixComplete(int totalBlockNum, 
					 int blockIndex, 
					 String fullName){
	StringBuffer sb = new StringBuffer();
	sb.append(totalBlockNum);
	sb.append("_");
	sb.append(blockIndex);
	sb.append("_");
	sb.append(fullName);
	return sb.toString();
    }

    private static int bytes2Block(byte[] bytes, String fname) throws IOException{
	OutputStream os = new FileOutputStream(new File(fname));
	IOUtils.write(bytes, os);
	os.flush();os.close();
	return bytes.length;
    }
    private static byte[] block2Bytes(String blockName) throws IOException{
	InputStream is    = new FileInputStream(new File(blockName));
	byte[]      bytes = IOUtils.toByteArray(is);
	is.close();
	return bytes;
    }

    /* ==================================================== */
    protected static byte[]        digest;
    protected static StringBuffer  digest_strbuf;
    protected static MessageDigest md5Instance;
    static {
	try {Read.md5Instance = MessageDigest.getInstance("MD5");}
	catch(NoSuchAlgorithmException e){throw new ExceptionInInitializerError(e);}}

    protected static String hashMd5(byte[] byteArr){
	Read.md5Instance.reset();
	Read.digest = md5Instance.digest(byteArr);
        Read.digest_strbuf = new StringBuffer();
        for(byte b : Read.digest)
	    Read.digest_strbuf.append(String.format("%02x", b & 0xff));
	return Read.digest_strbuf.toString();
    }

}




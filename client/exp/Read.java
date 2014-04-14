import java.io.IOException;
import java.io.*;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Read{

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


    public static void main(String[] args) throws IOException{
 
        RandomAccessFile file    = new RandomAccessFile("Flip4Mac 3.2.0.16.dmg","r");
        FileChannel      channel = file.getChannel();
        ByteBuffer       buffer  = ByteBuffer.allocate(Const.mb);

	int i = 0, j = 0;
        byte[] list;
        while(channel.read(buffer) > 0){
            buffer.flip();
            j+=1;
	    list = new byte[Const.mb];
	    for (i = 0; i < buffer.limit(); i++){
		list[i] = buffer.get();
		//list.add(new Byte(buffer.get()));
            }
	    //File newFile = new File(j+"_files.s");
	    //FileChannel outChannel = new FileOutputStream(newFile).getChannel();
	    
	    System.out.print(j);
	    System.out.print("\t");
            System.out.println(Read.hashMd5(list));

	    buffer.clear(); 
        }
        channel.close();
        file.close();	
	
    }


}




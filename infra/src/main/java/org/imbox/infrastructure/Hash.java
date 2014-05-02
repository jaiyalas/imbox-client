package org.imbox.infrastructure;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Hash{
    /* old version */
    //protected static byte[]        digest;
    //protected static StringBuffer  digest_strbuf;
    //protected static MessageDigest md5Instance;
    //static {
    //	try {Hash.md5Instance = MessageDigest.getInstance("MD5");}
    //	catch(NoSuchAlgorithmException e)
    //	  {throw new ExceptionInInitializerError(e);}}

    public final static byte[] zeroMD5 = new byte[16];
    static{ for(int i=0;i<=15;i++) zeroMD5[i]=(byte)0; }

    public static byte[] hashMD5(byte[] rawData){
	try{
	    MessageDigest md5 = MessageDigest.getInstance("MD5");
	    md5.reset();
	    return md5.digest(rawData);
	}catch(NoSuchAlgorithmException e){
	    for(int i=0 ; i < 50; i++)
		System.err.println("MD5 BIG BANG!!!!");
	    System.err.println(e.toString());
	    System.exit(1);
	}
	return Hash.zeroMD5;
    }

    public static String md52String(byte[] digest){
	StringBuffer sb = new StringBuffer();        
	for(byte i : digest)
	    sb.append(String.format("%02x", i & 0xff));
	return sb.toString();
    };

    public static String getMD5String(byte[] _rawData){
	return md52String(hashMD5(_rawData));
    };

}

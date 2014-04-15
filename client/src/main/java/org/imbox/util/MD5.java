package org.imbox.util.MD5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5{
    protected static byte[]        digest;
    protected static StringBuffer  digest_strbuf;
    protected static MessageDigest md5Instance;
    static {
	try {MD5.md5Instance = MessageDigest.getInstance("MD5");}
	catch(NoSuchAlgorithmException e)
	  {throw new ExceptionInInitializerError(e);}}

    // hashMD5 :: byte[] -> String
    public static String hashMd5(byte[] rawData){
	MD5.md5Instance.reset();
	MD5.digest = md5Instance.digest(rawData);
	MD5.digest_strbuf.delete(0, MD5.digest_strbuf.length());        
	for(byte b : MD5.digest)
	    MD5.digest_strbuf.append(String.format("%02x", b & 0xff));
	return MD5.digest_strbuf.toString();
    }
}

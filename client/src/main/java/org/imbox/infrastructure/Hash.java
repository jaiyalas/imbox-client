package org.imbox.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.imbox.infrastructure.Error;

public class Hash{
    /* old version */
    //protected static byte[]        digest;
    //protected static StringBuffer  digest_strbuf;
    //protected static MessageDigest md5Instance;
    //static {
    //	try {Hash.md5Instance = MessageDigest.getInstance("MD5");}
    //	catch(NoSuchAlgorithmException e)
    //	  {throw new ExceptionInInitializerError(e);}}
    public static String hashMD5(byte[] rawData){
	try{
	MessageDigest md5    = MessageDigest.getInstance("MD5");md5.reset();
	}catch(NoSuchAlgorithmException e){
	    Error.popup("System failure within "+
			"org.imbox.util.Hash.hashMD5::byte[] -> String \n"
			+e.toString());}
	byte[]        digest = md5.digest(rawData);
	StringBuffer  sb     = new StringBuffer();        
	for(byte i : digest)
	    sb.append(String.format("%02x", i & 0xff));
	return sb.toString();
    }
}

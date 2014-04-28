package org.imbox.infrastructure;

public class Casting{

    // via ASCII 

    public static byte[] stringToBytes(String str) {
	char[] buffer = str.toCharArray();
	byte[] b = new byte[buffer.length];
	for (int i = 0; i < b.length; i++) {
	    b[i] = (byte) buffer[i];
	}
	return b;
    }
    
    public static String bytesToString(byte[] bs) {
	return (new String(bs));
    }

}

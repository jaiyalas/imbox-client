package org.imbox.infrastructure;

import org.apache.commons.codec.binary.Base64;

public class Casting{

    private static Base64 base64 = new Base64();

    public static byte[] stringToBytes(String str) {

	/** via ASCII **/
	//char[] buffer = str.toCharArray();
	//byte[] b = new byte[buffer.length];
	//for (int i = 0; i < b.length; i++) {
	//    b[i] = (byte) buffer[i];
	//}
	//return b;

	/** via BASE64 **/
	//Base64 base64 = new Base64();
	return (base64.decode(str));

}
    
    public static String bytesToString(byte[] bs) {

	/** via ASCII **/
	//return (new String(bs));

	/** via BASE64 **/
	//Base64 base64 = new Base64();
	return (new String(base64.encode(bs)));
    }

}

public class BS {
  public static void main(String args[]) {
      
      byte[] bytes = {15,16,-1};
      StringBuilder sb = new StringBuilder();
      for (byte b : bytes) {
	  System.out.println(b);
	  sb.append(String.format("%02X", b));
	  System.out.println((char) (b & 0xFF));
      }
      System.out.println(sb);
      
      System.out.println("=======");

      String str = new String(bytes);

      System.out.println("\""+str+"\"");
      
      byte[] btyes2 = stringToBytesASCII(str);

      for (byte b : bytes) System.out.println(b);

      //char[] chs = sb.toString().toCharArray();
      //byte[] btyes2 = byte[3];
      //for (in i=0;i<chs.lengtht;i+=2) 

  }


    public static byte read(char c){
	switch(c){
	case '0': return 0;
	case '1': return 1;
	case '2': return 2;
	case '3': return 3;
	case '4': return 4;
	case '5': return 5;
	case '6': return 6;
	case '7': return 7;
	case '8': return 8;
	case '9': return 9;
	case 'A': return 10;
	case 'B': return 11;
	case 'C': return 12;
	case 'D': return 13;
	case 'E': return 14;
	case 'F': return 15;
	}
	return -1;
    }

    public static byte[] stringToBytesASCII(String str) {
	char[] buffer = str.toCharArray();
	byte[] b = new byte[buffer.length];
	for (int i = 0; i < b.length; i++) {
	    b[i] = (byte) buffer[i];
	}
	return b;
    }


}


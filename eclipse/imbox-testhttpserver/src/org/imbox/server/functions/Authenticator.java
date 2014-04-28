package	org.imbox.server.functions;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Authenticator
{
	public Authenticator()
	{
		//init DB here?
	}
	
	public String Authenticatebypassword(String accountname, String pwd, String MAC)
	{
		//TODO:getpassword
		String correctpassword = pwd; //for test, always true
		if (correctpassword.equals(pwd))
		{
			return Tokenmaker(accountname,MAC);
		}else
		{
			return new String();
		}
	}
	
	public boolean Authenticatebytoken(String token, String MAC)
	{
		Tokenpair decryptedtoken = decrypttoken(token,MAC);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String timestring = sdf.format(date.getTime());
		if (decryptedtoken.timestamp.equals(timestring))   // TODO: and check account exist
		{
			return true;
		}else
		{
			return false;
		}
	}
	
	public String Createaccount(String account,String pwd,String MAC)
	{
		//TODO: create account at DB
		return Tokenmaker(account,MAC);
	}
	
	
	public String getaccountname(String token, String MAC)
	{
		Tokenpair tokenpair = decrypttoken(token, MAC);
		return tokenpair.account;
	}
	private String Tokenmaker(String account,String MAC)
	{
		String tokenbeforeencrypt = new String();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String timestring = sdf.format(date.getTime());
		tokenbeforeencrypt = timestring + account;
		return tokenencrypter(tokenbeforeencrypt,MAC);
	}
	
	private String tokenencrypter(String targetstring, String MAC)
	{
		try{
			byte[] uncutkey = MAC.getBytes();
			byte[] key = Arrays.copyOfRange(uncutkey, 0,16);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] encrytedbyte = cipher.doFinal(targetstring.getBytes());
			String encryptedstring = arraytostring(encrytedbyte);
			return encryptedstring;
		}catch(Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}
	
	private Tokenpair decrypttoken(String token,String MAC)
	{
		try
		{
			byte[] uncutkey = MAC.getBytes();
			byte[] key = Arrays.copyOfRange(uncutkey, 0,16);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] originalarray = stringtoarray(token);
			byte[] decryptedarray = cipher.doFinal(originalarray);
			String decryptedstring = new String(decryptedarray);
			String account = decryptedstring.substring(8);
			String timestamp = decryptedstring.substring(0, 8);
			Tokenpair tokenpair = new Tokenpair(account, timestamp);
			return tokenpair;
		}catch (Exception e)
		{
			e.printStackTrace();
			return new Tokenpair("", "");
		}
	}
	
	private class Tokenpair
	{
		public String account;
		public String timestamp;
		public Tokenpair(String account , String timestamp)
		{
			this.account = account;
			this.timestamp = timestamp;
		}
	}
	
	private String arraytostring (byte[] bytes)
	{
		 StringBuffer buff = new StringBuffer();
		 for(int i=0;i<bytes.length;i++)
		 {
			 buff.append(bytes[i] + ":");
		 }
		 return buff.toString();
	}
	
	private byte[] stringtoarray(String target)
	{
		String[] token;
		token = target.split(":");
		byte[] answer = new byte[token.length];
		for(int i =0;i<token.length;i++)
		{
			answer[i] = Byte.parseByte(token[i]);
		}
		return answer;
	}
}
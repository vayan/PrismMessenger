package pm.PrismMessenger;

import java.security.*;
import java.util.ArrayList;

import javax.crypto.Cipher;


public class Encryption {
	
	private static String ENCRYPTION_ALGORITHM = "RSA";
	
	public static ArrayList<Key> Generate() {
		KeyPairGenerator generator;
		try {
			generator = KeyPairGenerator.getInstance(ENCRYPTION_ALGORITHM);
			ArrayList<Key> 		keys = new ArrayList<Key>();
			KeyPair				kPair;
			
			generator.initialize(1024);
			kPair = generator.genKeyPair();
			keys.add(0, kPair.getPublic());
			keys.add(1, kPair.getPrivate());
			
			return (keys);
		} 
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return (null);
		}
	}
	
	public static byte[] Encrypt(String message, Key publicKey) {
		try {
			byte[] 		encryptedMessage = null;
			Cipher 		cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
		      
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		    encryptedMessage = cipher.doFinal(message.getBytes());
		    return encryptedMessage;
		} 
		catch (Exception e) {
		    e.printStackTrace();
		    return null;
		}
	}
		
	public static String Decrypt(byte[] cypher, Key privateKey) {
		try {
			String 		message = null;
			Cipher 		cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
			
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			message = new String(cipher.doFinal(cypher));
			return message;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	 }
}

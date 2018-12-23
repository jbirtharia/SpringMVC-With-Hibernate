package com.demo.springmvc.utility;


import org.jasypt.contrib.org.apache.commons.codec_1_3.binary.Base64;





public class SecurityUtility {



	public static String Encrypt(String Data) throws Exception {
		 byte[] encryptArray = Base64.encodeBase64(Data.getBytes());        
	     String encstr = new String(encryptArray,"UTF-8");   
		return encstr;
	     

	}

	public static String Decrypt(String encryptedData) throws Exception {
		 byte[] dectryptArray = encryptedData.getBytes();
	     byte[] decarray = Base64.decodeBase64(dectryptArray);
	     String decstr = new String(decarray,"UTF-8");
		return decstr; 
	}
	
	public static void main(String[] args) throws Exception {

		String test ="jbirtharia@gmail.com";
		String encrypt=Encrypt(test);
		String decrypt=Decrypt(encrypt);
		System.out.println("Encrypt : "+encrypt);
		System.out.println("Decrypt : "+decrypt);
		
	}
}

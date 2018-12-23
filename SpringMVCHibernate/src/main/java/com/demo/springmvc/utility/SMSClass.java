package com.demo.springmvc.utility;


import java.util.Random;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


public class SMSClass {

	public static final String ACCOUNT_SID = "ACe4087a701b34e777f0a930be0b34344a";
	public static final String AUTH_TOKEN = "a31fdf82df9cfa450c768eda5886f732";




	public static void SMS(String otp,String number) {

		UserContextFactory.getUserContext().setOtp(otp);
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		 number=number.trim();
		System.out.println("PHONE NUMBER : "+number);
		Message message = Message
				.creator(new PhoneNumber("+91"+number), // to
						new PhoneNumber("+18673222296"), // from
						"Your otp is "+otp)
				.create();

		System.out.println("Message Has been Sent to "+number+" and its id is : "+message.getSid());
	}

	
	 public static String OTP(int len) {
		    System.out.println("Generating OTP using random ()");
		  
		    // Using numeric values
		    String numbers = "0123456789";

		    // Using random method 
		    Random rndm_method = new Random();
		    char[] otp = new char[len];
		    for(int i=0; i<len;i++) {
		     
		      otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
		    }
		    

		    String newotp=String.valueOf(otp);
		    System.out.println("Your OTP is : "+newotp);
		    return newotp;
		  }
	 

	public static void main(String[] args) {
		//SMS();
		 int length = 4;
		 System.out.println(OTP(length));

	}

}
	


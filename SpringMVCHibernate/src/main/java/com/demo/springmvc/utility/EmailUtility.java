package com.demo.springmvc.utility;


import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class EmailUtility {

    static final String FROM = "jbirtharia@gmail.com";
    static final String FROMNAME = "Employee Portal";

    static final String SMTP_USERNAME = "jbirtharia@gmail.com";
    static final String SMTP_PASSWORD = "afd33874-799c-4af1-8c95-652588951f22";
    
    static final String CONFIGSET = "ConfigSet";
    
    static final String HOST =  "smtp.elasticemail.com";
    
    static final int PORT =	2525;
    
    static final String SUBJECT = "Welcome to Employee Portal";
    
     

    public static void MailSend(String name,String pass) throws Exception {
    	
    System.out.println("Encrypt : "+SecurityUtility.Encrypt(name));
    	
    	String BODY = String.join(
        	    System.getProperty("line.separator"),
        	    "<h1>Welcome To Employee Portal</h1>",
        	    "<p>This email was sent to confirm  ", 
        	    "your Registration in Portal",
        	    " for <a href='localhost:8080/SpringMVCHibernateWithSpringSecurityExample/verification-"+SecurityUtility.Encrypt(name)+"'>Click here to Login</a>.<br><br>",
        	    "Your Username : "+name+"<br>",
        	    "Your Password : "+pass
        	);
    	
    	 // Create a Properties object to contain connection configuration information.
    	Properties props = System.getProperties();
    	props.put("mail.transport.protocol", "smtp");
    	props.put("mail.smtp.port", PORT); 
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.auth", "true");

        // Create a Session object to represent a mail session with the specified properties. 
    	Session session = Session.getDefaultInstance(props);

        // Create a message with the specified information. 
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM,FROMNAME));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(name));
        msg.setSubject(SUBJECT);
        msg.setContent(BODY,"text/html");
    
        msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);
            
        Transport transport = session.getTransport();
                    
        // Send the message.
        try
        {
            System.out.println("Sending...");
            
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
        	
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email sent!");
        }
        catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        }
        finally
        {
            // Close and terminate the connection.
            transport.close();
        }
    	
		
	}
    
    
    
    public static void ForgotPassword(String name) throws Exception {
    	
    	  System.out.println("Encrypt : "+SecurityUtility.Encrypt(name));
    
    	String BODY = String.join(
        	    System.getProperty("line.separator"),
        	    "<h1>Welcome To Employee Portal</h1>",
        	    "<p>Please click on following link to change your password</p><br><br>", 
        	    "<a href='localhost:8080/SpringMVCHibernateWithSpringSecurityExample/managepass-"+SecurityUtility.Encrypt(name)+"'>Click here</a>.<br><br>"
        	    
        	);
    	
    	 // Create a Properties object to contain connection configuration information.
    	Properties props = System.getProperties();
    	props.put("mail.transport.protocol", "smtp");
    	props.put("mail.smtp.port", PORT); 
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.auth", "true");

        // Create a Session object to represent a mail session with the specified properties. 
    	Session session = Session.getDefaultInstance(props);

        // Create a message with the specified information. 
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM,FROMNAME));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(name));
        msg.setSubject(SUBJECT);
        msg.setContent(BODY,"text/html");
    
        msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);
            
        Transport transport = session.getTransport();
                    
        // Send the message.
        try
        {
            System.out.println("Sending...");
            
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
        	
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email sent!");
        }
        catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        }
        finally
        {
            // Close and terminate the connection.
            transport.close();
        }
    	
    	
    	
    }
    
}

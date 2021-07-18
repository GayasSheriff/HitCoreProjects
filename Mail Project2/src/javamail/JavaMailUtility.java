package javamail;

import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailUtility {
	public static void sendFromGmail(String fromMail, String password, String subject, String content) {
		
		Set<String> toMails=new TreeSet<String>();
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter number of Mails to send:");
		int max=sc.nextInt();
		sc.nextLine();
		
		System.out.println("Enter the mail to send :");
		for(int i=0;i<max;i++) {
			toMails.add(sc.nextLine());
		}
		sc.close();
		
		//Mail configuration Properties from jar javax
		
		Properties properties=new Properties();
		properties.put("mail.smtp.auth", "true"); //set authentication
		properties.put("mail.smtp.starttls.enable", "true"); 
		properties.put("mail.smtp.host", "smtp.gmail.com"); //set host -> gmail.com
		properties.put("mail.smtp.port", "587"); // default port 
		
		//Get session Object 
		Session session=Session.getInstance(properties,new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromMail, password);
			}
			
		});//created the session of from mail
		
		try {
			//create mime message object
			Message message=new MimeMessage(session);
			//set from address in from Mail
			message.setFrom(new InternetAddress(fromMail));
			
			for(String toMail:toMails) {
				if(isValidEmailAddress(toMail)) {
					String userName=getUserName(toMail);
					
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(toMail));
					message.setSubject(subject);
					
					content="Dear Mr. "+userName+",\n"+content;
					System.out.println(content);
					
					//actual message to Mail
					
					message.setText(content);
					System.out.println("Sending the Mail to: "+userName);
					Transport.send(message);
					System.out.println("Mail sent Sussessfully to "+userName+"\n");
					
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isValidEmailAddress(String email) 
	{ 
		boolean result = true; 
		try 
		{ 
			InternetAddress emailAddr = new InternetAddress(email); //get the address of email.
			emailAddr.validate(); } //validate your address
		catch (AddressException ex) 
			{ result = false; } 
		return result; 
		}
	
	public static String getUserName(String email) {
		int lastIndex=email.indexOf("@");
		email=email.substring(0, lastIndex);
		return email;
	}
}

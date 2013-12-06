import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.html.simpleparser.StyleSheet;
import com.lowagie.text.pdf.PdfWriter;


public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*
		 * String dateOfBirth="06/12/1970";
		 	
		Calendar dob = Calendar.getInstance();  
		dob.setTime(new Date(dateOfBirth));
		Calendar today = Calendar.getInstance();  
		int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);  
		if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
		  age--;  
		} else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
		    && today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
		  age--;  
		}
		System.out.println("age="+age);
		
		Intake intake = new Intake();
		try {
		BeanInfo info = Introspector.getBeanInfo(intake.getClass());
		PropertyDescriptor[] props = info.getPropertyDescriptors();  
	    for (int i=0;i<props.length;i++) {  
	        /*String name = pd.getName();  
	        Method getter = pd.getReadMethod();  
	        Class<?> type = pd.getPropertyType();  
	        Object value = getter.invoke(info);  
	        System.out.println(name + " = " + value + "; type = " + type);  
	        
	        System.out.println (">"+props[i].getDisplayName()+"="+props[i].getValue(props[i].getDisplayName()));
			}
	    
		}
		catch (Exception e) {
			
		}*/
		
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
		Session mailSession = Session.getDefaultInstance(properties,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("faithfarm.intake@gmail.com","It0525Ff");
					}
				});
		try{
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(mailSession);

	         // Set From: header field of the header.
	         //message.setFrom(new InternetAddress("donnotreply@faithfarm.org"));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO,
	        		 new InternetAddress("itdepartment@faithfarm.org"));
	         /*message.addRecipient(Message.RecipientType.TO,
                     new InternetAddress("intake.boyntonbeach@faithfarm.org"));
	         message.addRecipient(Message.RecipientType.TO,
                     new InternetAddress("intake.fortlauderdale@faithfarm.org"));
	         message.addRecipient(Message.RecipientType.TO,
                     new InternetAddress("intake.okeechobee@faithfarm.org"));
	         message.addRecipient(Message.RecipientType.TO,
                     new InternetAddress("intake.womensministry@faithfarm.org"));
	         message.addRecipient(Message.RecipientType.TO,
                     new InternetAddress("PZielinski@faithfarm.org"));
	         message.addRecipient(Message.RecipientType.TO,
                     new InternetAddress("RJurisDick@faithfarm.org"));
	         message.addRecipient(Message.RecipientType.TO,
                     new InternetAddress("MMurphy@faithfarm.org"));

	         message.addRecipient(Message.RecipientType.TO,
                     new InternetAddress("sjohnson@faithfarm.org"));
	         message.addRecipient(Message.RecipientType.TO,
                     new InternetAddress("iftl@faithfarm.org"));
	         
	         message.addRecipient(Message.RecipientType.TO,
                     new InternetAddress("AGorrin@faithfarm.org"));
	         message.addRecipient(Message.RecipientType.TO,
                     new InternetAddress("VAndres@faithfarm.org"));
			*/

	         
	         // Set Subject: header field
	         message.setSubject("Intake Application Received: ");

	         // Now set the actual message
	         message.setText("This is actual message");

	         // Send message
	         Transport.send(message);
	         System.out.println("Sent message successfully....");
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      }

	}
	
	

}

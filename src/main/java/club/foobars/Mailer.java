/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package club.foobars;

import java.io.IOException;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;
/**
 *
 * @author alessio.finamore
 */
public class Mailer {
    String mailFrom = "foobarswheel@gmail.com";
    public String mailServer = "t2p.enter.it"; // "192.168.10.97"; // "TIESSESRV18.tsgroup.local";
    
   public void sendMailHtml(String recipient, String subject, String htmlBody, String attach) throws MessagingException, IOException{
            
            //Get the session object
            Properties properties = System.getProperties();
            properties.setProperty("mail.smtp.host", mailServer);
            Session session = Session.getDefaultInstance(properties);
            
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailFrom));

            for(String to : recipient.split(";")){
                message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            }
            
            message.addRecipient(Message.RecipientType.BCC,new InternetAddress("its01@tiessespa.com"));
            message.setSubject(subject);
            
            
            Multipart mp = new MimeMultipart();
            
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlBody, "text/html");
            mp.addBodyPart(htmlPart);
           
            if (attach != null){
                MimeBodyPart htmlAttach = new MimeBodyPart();
                htmlAttach.attachFile(attach);
                mp.addBodyPart(htmlAttach);
            }
            message.setContent(mp);
            
            // Send message
            Transport.send(message);
    }
    
}

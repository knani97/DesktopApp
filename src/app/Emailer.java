
package app;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Emailer {
     public static void sendMail(String recepient, String content) throws Exception {
        System.out.println("Préparation de l'email.");
        Properties properties = new Properties();

        //Enable authentication
        properties.put("mail.smtp.auth", "true");
        //Set TLS encryption enabled
        properties.put("mail.smtp.starttls.enable", "true");
        //Set SMTP host
        properties.put("mail.smtp.host", "smtp.gmail.com");
        //Set smtp port
        properties.put("mail.smtp.port", "587");

        //Your gmail address
        String myAccountEmail = "docpidev@gmail.com";
        //Your gmail password
        String password = "pidev123456";

        //Create a session with account credentials
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        //Prepare email message
        Message message = prepareMessage(session, myAccountEmail, recepient, content);

        //Send mail
        Transport.send(message);
        System.out.println("Message envoyé avec succés.");
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recepient, String content) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Reset Password DOCTOURNA");
            String htmlCode = content;
            message.setContent(htmlCode, "text/html");
            return message;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}

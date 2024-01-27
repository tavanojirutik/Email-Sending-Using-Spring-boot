//This Is A Static Email File Which Is A Based On String 

package intelli.uno.service.entityengineermaster;

import java.util.Properties;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import intelli.uno.repository.RepositoryEntityEmailSetting;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@service
public class ServiceImplSendMail {

    static String smtpport = "465"; // Change to the appropriate SSL port
    static String smtphost = "smtp.quickpost.app";
    static String mailuser = "username";
    static String mailpassword = "password";
    static String frommail = "From Email Address"; //most Of the Case frommail and Sender mail is Seam mail ID
    static String protocolFlag = "N";
    static String senderEsm = "Sender Mail Address";
    static String receiverEsm = "Recever Mail Address";

    @Autowired
    public RepositoryEntityEmailSetting repositoryEntityEmailSetting;

    public static void main(String args[]) {
        System.out.println("Sending email...");

        Properties props = new Properties();
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.host", smtphost);
        props.setProperty("mail.smtp.port", smtpport);
        props.put("mail.smtp.ssl.enable", "true"); // Enable SSL
        props.put("mail.smtp.ssl.protocols", "TLSv1.2"); // Use TLS v1.2
        props.put("mail.user", frommail);
        props.put("mail.password", mailpassword);

        Session mailSession = Session.getInstance(props, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailuser, mailpassword);
            }
        });

        try {
            Message message = new MimeMessage(mailSession);

            message.setFrom(new InternetAddress(frommail));

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEsm));

            message.setSubject("Task Assing From UNO");
            long taskId=123;
            message.setText("Task ID :"+taskId+" Assign to you");

            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

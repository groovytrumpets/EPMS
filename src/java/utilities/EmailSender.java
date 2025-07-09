/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

/**
 *
 * @author nguye
 */
public class EmailSender {

    static final String senderEmail = "groovytrumpets@gmail.com";
    static final String senderPassword = "wieq zojo idky olyd"; // Google's  App password


    public static boolean sendEmail(String recipientEmail, String title, String otpCode) throws AddressException, MessagingException {
    Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
            new jakarta.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, senderPassword);
                }
            });
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderEmail));

        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        message.setSubject("Your OTP Code");

        message.setText("Your OTP code is: " + otpCode);

        Transport.send(message);
        return true;
    }
    public static void main(String[] args) throws MessagingException {
        if (sendEmail("nguyennamkhanhnnk@gmail.com", "alo", "911")) {
      
        }
    }
}

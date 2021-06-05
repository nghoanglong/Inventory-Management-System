package Controllers;
import javafx.fxml.FXML;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class forgot_pwd {
   /* @FXML
   // private TextField username;
    @FXML
    private TextField txtemailField;
    @FXML
    private TextField txtpasswordField;
    @FXML
    private Button btnSend;
    @FXML
    private Label sentboolemail;*/

    public static void sendEmail() throws Exception {
        System.out.println("Preparing to Email");

        String to = "xxxxxx@gmail.com";
        String from = "xxxxx@gmail.com";

        //setup email server

        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); //TLS


        Authenticator authenticator = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("xxxxxx@gmail.com", "*********"); // ghi username va matkhau email muon giui
            }
        };

        Session session = Session.getDefaultInstance(props, authenticator);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Hello Java");
            message.setText("Test");

            Transport.send(message);
            System.out.println("Message sent Successfully");

        } catch (MessagingException ex) {
            ex.printStackTrace();
        }

    }


}

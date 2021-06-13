package Controllers;
import Models.ACCOUNT;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javafx.fxml.FXMLLoader;
import java.awt.*;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.EventObject;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;



public class ForgotPassword_Controller {
    @FXML
    private TextField emailTF;
    @FXML
    private Button sendpasswordButton;
    @FXML
    private Button backButton;
    @FXML
    private Label noticeLabel;
    @FXML
    public void initialize(){noticeLabel.setVisible(false);};


    public void sendEmail(String Emailuser) throws Exception {
        System.out.println("Preparing to Email");

        String Emailto = Emailuser;
        final String from = "19521788@gm.uit.edu.vn";

        //setup email server

        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); //TLS


        Authenticator authenticator = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("19521788@gm.uit.edu.vn", "xxxxxxx"); // ghi username va matkhau email muon giui
            }
        };

        Session session = Session.getDefaultInstance(props, authenticator);

        try {
            ACCOUNT newAccount_Password = new ACCOUNT();
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(Emailto));
            message.setSubject("AUTHENTICATE TO INVENTORY MANGAGMENT SYSTEM");
            message.setText("password: " + newAccount_Password.getPassword(Emailto));

            Transport.send(message);
            noticeLabel.setText("Send email success");
            noticeLabel.setVisible(true);

        } catch (MessagingException ex) {
            ex.printStackTrace();
        }

    }


    public void sendpasswordButtonAction(ActionEvent actionEvent) throws Exception {
        String emailuser = emailTF.getText();
        if(emailuser.isEmpty()){
            noticeLabel.setText("Email should not be empty!");
        }else {
            sendEmail(emailuser);
            noticeLabel.setText("Message sent Successfully!!");
        }
    }

    public void backButtonAction(ActionEvent actionEvent) throws IOException {
        Parent LoginScreen_Parent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/LoginScreen/Login_Screen.fxml"));
        Stage LoginScreen_Stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene LoginScreen_Scene = new Scene(LoginScreen_Parent);
        LoginScreen_Stage.setScene(LoginScreen_Scene);
        LoginScreen_Stage.setResizable(false);
        LoginScreen_Stage.show();
    }
}

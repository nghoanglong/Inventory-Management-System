package Controllers;
import Controllers.Login_Controller;
import Controllers.StaffManagement.AddNewStaff_Controller;
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
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;



public class ForgotPassword_Controller {
    @FXML
    private TextField emailTF;
    @FXML
    private Button sendpwdBtn;
    @FXML
    private TextField codeTF;
    @FXML
    private PasswordField newPwdPF;
    public void initialize(){
        this.codeTF.setEditable(false);
        this.newPwdPF.setEditable(false);
        this.codeTF.setDisable(true);
        this.newPwdPF.setDisable(true);
    };

    // variables
    String input_email = null;
    int code_sent = 0;

    public void send_email(String to_email) throws Exception {
        System.out.println("Preparing to Email");

        this.input_email = to_email;
        String from = "19521788@gm.uit.edu.vn";

        //setup email server

        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); //TLS


        Authenticator authenticator = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("19521788@gm.uit.edu.vn", "0919610909aA"); // ghi username va matkhau email muon giui
            }
        };

        Session session = Session.getDefaultInstance(props, authenticator);

        try {
            ACCOUNT account_con = new ACCOUNT();
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to_email));
            message.setSubject("AUTHENTICATE TO INVENTORY MANGAGMENT SYSTEM");
            if (account_con.check_exist_email(to_email) == true){
                sendpwdBtn.setText("Change password");
                Random ran_num = new Random(1712);
                this.code_sent = ran_num.nextInt();
                message.setText("password: " + this.code_sent);
                Transport.send(message);
                Alert message_send = new Alert(Alert.AlertType.INFORMATION);
                message_send.setContentText("Code sent!, please fill it in the blank");
                message_send.show();
                emailTF.setEditable(false);
                emailTF.setDisable(true);
                codeTF.setDisable(false);
                codeTF.setEditable(true);
                newPwdPF.setEditable(true);
                newPwdPF.setDisable(false);

            }else{
                Alert message_send = new Alert(Alert.AlertType.INFORMATION);
                message_send.setContentText("No result matches this email in the system");
                message_send.show();
                emailTF.clear();
            }

        } catch (MessagingException ex) {
            ex.printStackTrace();
        }

    }


    public void sendpwdBtnAction(ActionEvent event) throws Exception {
        if(emailTF.getText().isEmpty()){
            Alert message_send = new Alert(Alert.AlertType.INFORMATION);
            message_send.setContentText("Email should not be empty");
            message_send.show();
        }else {
            if(sendpwdBtn.getText() == "Change password"){
                if(this.code_sent == Integer.parseInt(codeTF.getText())) {
                    ACCOUNT account_con = new ACCOUNT();
                    String id_user = account_con.get_Iduser_by_Email(this.input_email);

                    HashMap<String,String> data = new HashMap<String,String>();
                    data.put("pwd", AddNewStaff_Controller.HashingtoPassword(newPwdPF.getText()));
                    account_con.update_account(id_user, data);

                    codeTF.clear();
                    newPwdPF.clear();
                    Alert message_send = new Alert(Alert.AlertType.INFORMATION);
                    message_send.setContentText("Update new password success");
                    message_send.show();
                }else{
                    Alert message_send = new Alert(Alert.AlertType.INFORMATION);
                    message_send.setContentText("Wrong code!");
                    message_send.show();
                    emailTF.clear();
                    this.input_email = null;
                    this.code_sent = 0;
                }
            }else {
                String emailuser = emailTF.getText();
                send_email(emailuser);
            }
        }
    }

    public void backBtnAction(ActionEvent actionEvent) throws IOException {
        Parent LoginScreen_Parent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/LoginScreen/Login_Screen.fxml"));
        Stage LoginScreen_Stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene LoginScreen_Scene = new Scene(LoginScreen_Parent);
        LoginScreen_Stage.setScene(LoginScreen_Scene);
        LoginScreen_Stage.setResizable(false);
        LoginScreen_Stage.show();
    }
}

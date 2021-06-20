// Package
package Controllers;

// Import JavaFX libs
import Models.ACCOUNT;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.regex.*;
import Controllers.StaffManagement.AddNewStaff_Controller;


public class Login_Controller
{
    // Define virables to store and get input data
    @FXML
    private TextField usernameTF;
    @FXML
    private PasswordField pwdTF;
    @FXML
    private Label noticeLabel;

    @FXML
    private Button loginBtn;

    @FXML
    public void initialize()
    {
        noticeLabel.setVisible(false);
        usernameTF.textProperty().addListener((observable, oldvalue, newvalue) ->{
            Pattern pattern = Pattern.compile("^.{0,50}$");
            Matcher matcher = pattern.matcher(newvalue);
            if(!matcher.matches()){
                noticeLabel.setText("Vượt quá độ dài được phép nhập");
                noticeLabel.setVisible(true);
                loginBtn.setDisable(true);
            }else{
                noticeLabel.setVisible(false);
                loginBtn.setDisable(false);
            }
        });
        pwdTF.textProperty().addListener((observable, oldvalue, newvalue) ->{
            Pattern pattern = Pattern.compile("^.{0,50}$");
            Matcher matcher = pattern.matcher(newvalue);
            if(!matcher.matches()){
                noticeLabel.setText("Vượt quá độ dài được phép nhập");
                noticeLabel.setVisible(true);
                loginBtn.setDisable(true);
            }else{
                noticeLabel.setVisible(false);
                loginBtn.setDisable(false);
            }
        });
    }

    // Class Variabel
    public static int type_cur_user = -1;
    public static String id_cur_user = "";

    // Set action for Login Button
    public void loginBtnAction(ActionEvent event) throws IOException {
        String username = usernameTF.getText();
        String pwd = pwdTF.getText();

        if (username.isEmpty()) {
            noticeLabel.setText("username should not be empty");
            noticeLabel.setVisible(true);
        } else if (pwd.isEmpty()) {
            noticeLabel.setText("password should not be empty");
            noticeLabel.setVisible(true);
        } else {
            // result = 1 -> login thành công
            //          0 -> login thất bại
            ACCOUNT account_con = new ACCOUNT();
            int check_result = account_con.validate_login(username, AddNewStaff_Controller.HashingtoPassword(pwd));

            switch (check_result) {
                case 1:
                    noticeLabel.setText("Login success");
                    noticeLabel.setVisible(true);

                    Login_Controller.type_cur_user = account_con.get_account_role(username);
                    Login_Controller.id_cur_user = account_con.get_Iduser(username);
                
                    Parent IntroductionScreen_Parent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/IntroductionScreen/Introduction_Screen.fxml"));
                    Stage IntroductionScreen_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    IntroductionScreen_Stage.setScene(new Scene(IntroductionScreen_Parent, 1280, 720));
                    IntroductionScreen_Stage.setResizable(false);
                    IntroductionScreen_Stage.show();
                    break;
                case 0:
                    noticeLabel.setText("Login failed");
                    noticeLabel.setVisible(true);
                    break;
            }
        }
    }
    public void forgotpwdLabelAction(ActionEvent event) throws IOException{
        Parent ForgotPwdScreen_Parent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/ForgotPasswordScreen/ForgotPassword_Screen.fxml"));
        Stage ForgotPwdScreen_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ForgotPwdScreen_Stage.setScene(new Scene(ForgotPwdScreen_Parent, 1280, 720));
        ForgotPwdScreen_Stage.setResizable(false);
        ForgotPwdScreen_Stage.show();
    }
    public void closeAppAction(MouseEvent event){
        System.exit(0);
    }
}

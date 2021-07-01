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
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.regex.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

                    Login_Controller.type_cur_user = account_con.get_account_role(username);
                    Login_Controller.id_cur_user = account_con.get_Iduser(username);

                    String home_screen = "";
                    if(Login_Controller.type_cur_user == 1){
                        home_screen = "Views/HomeScreen/AdminHome/AdminHome_Screen.fxml";
                    }else if(Login_Controller.type_cur_user == 2){
                        home_screen = "Views/HomeScreen/WarehouseHome/WarehouseHome_Screen.fxml";
                    }else{
                        home_screen = "Views/HomeScreen/SellerHome/SellerHome_Screen.fxml";
                    }

                    Parent HomeScreen_parent = FXMLLoader.load(getClass().getClassLoader().getResource(home_screen));
                    Stage HomeScreen_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(HomeScreen_parent);
                    HomeScreen_stage.setScene(scene);
                    HomeScreen_stage.setResizable(false);
                    HomeScreen_stage.show();
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

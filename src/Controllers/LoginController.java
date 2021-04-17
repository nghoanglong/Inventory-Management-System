// Package
package Controllers;

// Import JavaFX libs
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

// Import java libs
import java.io.IOException;

// Import java modules
import Middlewares.LOGIN_MDW;

public class LoginController
{
    // Define virables to store and get input data
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField enterPasswordField;
    @FXML
    private Button signUpButton;
    @FXML
    private Button loginButton;
    @FXML
    private Label noticeLabel;

    @FXML
    public void initialize()
    {
        noticeLabel.setVisible(false);
    }

    // Tìm hiểu REGEX để kiếm tra input data - nhập quá 100 ký tự


    // Set action for Login Button
    public void loginButtonAction(ActionEvent event)
    {
        // Define userName, passWord to get data from user input
        String userName = usernameTextField.getText();
        String passWord = enterPasswordField.getText();

        // Define object để check validate login
        // result = 1 -> login thành công
        //        = 2 -> username ko tồn tại
        //        = 3 -> username đúng, password sai
        LOGIN_MDW new_login = new LOGIN_MDW();
        int check_result = new_login.validate_login(userName, passWord);

        switch (check_result)
        {
            case 1:
                noticeLabel.setText("Login success");
                noticeLabel.setVisible(true);
                break;
            case 2:
                noticeLabel.setText("User not exist");
                noticeLabel.setVisible(true);
                break;
            case 3:
                noticeLabel.setText("Wrong password");
                noticeLabel.setVisible(true);
                break;
        }

        System.out.println(noticeLabel);
    }

    public void signUpButtonAction(ActionEvent event) throws IOException
    {
        Parent signUpParent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/SignUp/signup.fxml"));
        Stage signUpStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene signUpScene = new Scene(signUpParent);

        signUpStage.setScene(signUpScene);
        signUpStage.show();
    }
}

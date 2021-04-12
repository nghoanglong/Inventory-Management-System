// Package
package Controllers;

// Import JavaFX libs
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

        // Define Alert virable to make pop up alert
        Alert alert_check_result = new Alert(Alert.AlertType.INFORMATION);
        alert_check_result.setContentText("Check result = " + check_result);

        // Show data input from user
        alert_check_result.show();
    }

    public void signUpButtonAction(ActionEvent event) throws IOException
    {
        Parent signUpParent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/Sign_Up_Scene.fxml"));
        Stage signUpStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene signUpScene = new Scene(signUpParent);

        signUpStage.setScene(signUpScene);
        signUpStage.show();
    }
}

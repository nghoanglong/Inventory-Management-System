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
import javafx.stage.Window;

import java.io.IOException;

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

        // Define Alert virable to make pop up alert
        Alert alertUsername = new Alert(Alert.AlertType.INFORMATION);
        Alert alertPassword = new Alert(Alert.AlertType.INFORMATION);
        alertUsername.setContentText("Username: " + userName);
        alertPassword.setContentText("Password: " + passWord);

        // Show data input from user
        alertPassword.show();
        alertUsername.show();
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

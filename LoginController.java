package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


public class LoginController
{
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField enterPasswordField;
    @FXML
    private Button signUpButton;
    @FXML
    private Button loginButton;

    public void loginButtonAction(ActionEvent event)
    {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        String userName = usernameTextField.getText();
        String passWord = enterPasswordField.getText();
        Alert alertUsername = new Alert(Alert.AlertType.INFORMATION);
        Alert alertPassword = new Alert(Alert.AlertType.INFORMATION);
        alertUsername.setContentText("Username: " + userName);
        alertPassword.setContentText("Password: " + passWord);
        alertPassword.show();
        alertUsername.show();
    }

    public void signUpButtonAction(ActionEvent event)
    {
        Stage stage = (Stage) signUpButton.getScene().getWindow();
        Alert alertCloseProgram = new Alert(Alert.AlertType.INFORMATION);
        alertCloseProgram.setContentText("Have not coded for Sign Up Button ,going to close the program!");
        alertCloseProgram.show();
        stage.close();
    }
}

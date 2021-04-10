// Package
package Controllers;

// Import JavaFX libs
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

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
        Stage stage = (Stage) loginButton.getScene().getWindow();
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

    public void signUpButtonAction(ActionEvent event)
    {
        // Define Alert virable
        Stage stage = (Stage) signUpButton.getScene().getWindow();

        // Have not coded part to switch to the Sign-Up scene
        // Will be coded later when receive Sign-Up scene from FE
        Alert alertCloseProgram = new Alert(Alert.AlertType.INFORMATION);
        alertCloseProgram.setContentText("Have not coded for Sign Up Button ,going to close the program!");
        alertCloseProgram.show();

        // Close program
        stage.close();
    }
}

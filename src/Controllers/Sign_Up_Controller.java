package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.time.format.DateTimeFormatter;

public class Sign_Up_Controller
{
    @FXML
    private TextField usernameTF;
    @FXML
    private TextField passwordTF;
    @FXML
    private TextField confirmedPasswordTF;
    @FXML
    private DatePicker dayOfBirthDP;
    @FXML
    private TextField emailTF;
    @FXML
    private MenuButton roleSelectionButton;
    @FXML
    private MenuItem adminMI;
    @FXML
    private MenuItem warehouseManagerMI;
    @FXML
    private MenuItem sellerMI;
    @FXML
    private Button signUpButton;
    @FXML
    private Button backButton;

    private int role = 0;

    // 1. Admin role
    // 2. Warehouse Manager role
    // 3. Seller role

    // Lấy MenuItem mà user chọn
    public void adminMIAction(ActionEvent event)
    {
        System.out.println("You chose ADMIN ROLE");
        role = 1;
    }
    public void warehouseMangerMIAction(ActionEvent event)
    {
        System.out.println("You chose WAREHOUSE MANAGER ROLE");
        role = 2;
    }
    public void sellerMIAction(ActionEvent event)
    {
        System.out.println("You chose SELLER ROLE");
        role = 3;
    }

    public void signUpButtonAction(ActionEvent event)
    {
        String username_input = usernameTF.getText();
        String password_input = passwordTF.getText();
        String confirmedPassword_input = confirmedPasswordTF.getText();
        String dayOfBirth_input = dayOfBirthDP.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String email_input = emailTF.getText();
        int role_input = role;

        System.out.println(username_input);
        System.out.println(password_input);
        System.out.println(confirmedPassword_input);
        System.out.println(dayOfBirth_input);
        System.out.println(email_input);
        System.out.println(role_input);
    }

    public void backButtonAction(ActionEvent event) throws Exception
    {
        Parent loginParent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/sample_view.fxml"));
        Stage loginSceneStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene loginScene = new Scene(loginParent);

        loginSceneStage.setScene(loginScene);
        loginSceneStage.show();
    }
}

package Controllers;

import Models.USERS;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.time.format.DateTimeFormatter;

public class SignUpController
{
    @FXML
    private TextField usernameTF;
    @FXML
    private PasswordField passwordTF;
    @FXML
    private PasswordField confirmedPasswordTF;
    @FXML
    private DatePicker dayOfBirthDP;
    @FXML
    private TextField emailTF;
    @FXML
    private Button signUpButton;
    @FXML
    private Button signInButton;

    @FXML
    private ComboBox roleSelectionCB;

    @FXML
    public void initialize()
    {
        ObservableList<String> options = FXCollections.observableArrayList(
                "Admin",
                "Seller",
                "Warehouse Manager"
        );
        roleSelectionCB.setItems(options);
    }


    private int role_num = 0;
    private String role_selected = null;

    // 1. Admin role
    // 2. Warehouse Manager role
    // 3. Seller role

    // Lấy Item mà user chọn
    public void roleSelectionCBAction(ActionEvent event)
    {
        roleSelectionCB.valueProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
            }
        });
        role_selected = roleSelectionCB.getValue().toString();
        System.out.println(role_selected);
        // Xử lý chuỗi lấy role_num
        switch (role_selected)
        {
                case "Admin":
                    role_num = 1;
                    break;
                case "Warehouse Manager":
                    role_num = 2;
                    break;
                case "Seller":
                    role_num = 3;
                    break;
                default:
                    break;
        }
        System.out.println(role_num);
    }

    public void signUpButtonAction(ActionEvent event)
    {
        String username_input = usernameTF.getText();
        String password_input = passwordTF.getText();
        String confirmedPassword_input = confirmedPasswordTF.getText();
        String dayOfBirth_input = dayOfBirthDP.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String email_input = emailTF.getText();

        if(!password_input.equals(confirmedPassword_input))
        {
            Alert checkConfPass = new Alert(Alert.AlertType.ERROR);
            checkConfPass.setContentText("Confirm Password sai, mời nhập lại Password");
            checkConfPass.show();
        }
        else
        {
            // chỗ này còn xử lý giá trị trả về khi insert thành công
            USERS user_con = new USERS();
            user_con.insert_user(username_input,password_input,dayOfBirth_input,role_num,email_input);
        }
    }

    public void signInButtonAction(MouseEvent event) throws Exception
    {
        Parent loginParent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/LoginPage/loginpage.fxml"));
        Stage loginSceneStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene loginScene = new Scene(loginParent);

        loginSceneStage.setScene(loginScene);
        loginSceneStage.show();
    }
}

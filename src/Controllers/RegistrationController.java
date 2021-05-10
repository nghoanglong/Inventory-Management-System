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
import javafx.stage.Stage;


import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RegistrationController
{
    @FXML
    private TextField fullnameTF;
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
    private Button saveInsertButton;
    @FXML
    private Button homeBackButton;
    @FXML
    private ComboBox roleSelectionCB;
    @FXML
    private Label noticeLabel;

    @FXML
    public void initialize()
    {
        ObservableList<String> options = FXCollections.observableArrayList(
                "Admin",
                "Seller",
                "Warehouse Manager"
        );
        roleSelectionCB.setItems(options);
        noticeLabel.setVisible(false);
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
    }

    public void saveInsertButtonAction(ActionEvent event)
    {
        String fullname_input = fullnameTF.getText();
        String username_input = usernameTF.getText();
        String password_input = passwordTF.getText();
        String confirmedPassword_input = confirmedPasswordTF.getText();
        LocalDate dayOfBirth_input = dayOfBirthDP.getValue();
        String email_input = emailTF.getText();
        if(fullname_input.isEmpty()){
            noticeLabel.setText("fullname should not be empty");
            noticeLabel.setVisible(true);
        }else if(username_input.isEmpty()){
            noticeLabel.setText("username should not be empty");
            noticeLabel.setVisible(true);
        }else if(password_input.isEmpty()){
            noticeLabel.setText("password should not be empty");
            noticeLabel.setVisible(true);
        }else if(confirmedPassword_input.isEmpty()){
            noticeLabel.setText("confirm password should not be empty");
            noticeLabel.setVisible(true);
        }else if(dayOfBirth_input == null){
            noticeLabel.setText("Your birthday should not be empty");
            noticeLabel.setVisible(true);
        }else if(email_input.isEmpty()){
            noticeLabel.setText("email should not be empty");
            noticeLabel.setVisible(true);
        }else {
            if (!password_input.equals(confirmedPassword_input)) {
                Alert checkConfPass = new Alert(Alert.AlertType.ERROR);
                checkConfPass.setContentText("Confirm Password sai, mời nhập lại Password");
                checkConfPass.show();
            } else {
                USERS user_con = new USERS();
                String id_user = user_con.generate_IDuser();
                int res = user_con.insert_user(id_user,
                                               fullname_input,
                                               username_input,
                                               password_input,
                                               dayOfBirth_input.toString(),
                                               role_num,
                                               email_input);
                if(res == 1){
                    noticeLabel.setText("Thêm user thành công");
                    noticeLabel.setVisible(true);
                }else{
                    noticeLabel.setText("username đã tồn tại");
                    noticeLabel.setVisible(true);
                }
            }
        }
    }

    public void homeBackButtonAction(ActionEvent event) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/HomeScreen/AdminLauncher/admin_launcher.fxml"));
        Stage loginSceneStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene loginScene = new Scene(loginParent);

        loginSceneStage.setScene(loginScene);
        loginSceneStage.show();
    }
}

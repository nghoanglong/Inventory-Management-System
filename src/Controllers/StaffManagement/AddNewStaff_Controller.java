package Controllers.StaffManagement;

import Models.ACCOUNT;
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

public class AddNewStaff_Controller
{
    @FXML
    private TextField fullnameTf;
    @FXML
    private TextField usernameTf;
    @FXML
    private TextField passwordTf;
    @FXML
    private TextField confirmTf;
    @FXML
    private DatePicker birthDp;
    @FXML
    private TextField emailTf;
    @FXML
    private Button saveBtn;
    @FXML
    private Button backBtn;
    @FXML
    private ComboBox roleCb;
    @FXML
    private Label noticeLb;

    @FXML
    public void initialize()
    {
        ObservableList<String> options = FXCollections.observableArrayList(
                "Admin",
                "Seller",
                "Warehouse Manager"
        );
        roleCb.setItems(options);
        noticeLb.setVisible(false);
    }


    private int role_num = 0;
    private String role_selected = null;

    // 1. Admin role
    // 2. Warehouse Manager role
    // 3. Seller role

    // Lấy Item mà user chọn
    public void roleCbAction(ActionEvent event)
    {
        roleCb.valueProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
            }
        });
        role_selected = roleCb.getValue().toString();
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

    public void addBtnAction(ActionEvent event)
    {
        String fullname_input = fullnameTf.getText();
        String username_input = usernameTf.getText();
        String password_input = passwordTf.getText();
        String confirmedPassword_input = confirmTf.getText();
        LocalDate dayOfBirth_input = birthDp.getValue();
        String email_input = emailTf.getText();
        if(fullname_input.isEmpty()){
            noticeLb.setText("fullname should not be empty");
            noticeLb.setVisible(true);
        }else if(username_input.isEmpty()){
            noticeLb.setText("username should not be empty");
            noticeLb.setVisible(true);
        }else if(password_input.isEmpty()){
            noticeLb.setText("password should not be empty");
            noticeLb.setVisible(true);
        }else if(confirmedPassword_input.isEmpty()){
            noticeLb.setText("confirm password should not be empty");
            noticeLb.setVisible(true);
        }else if(dayOfBirth_input == null){
            noticeLb.setText("Your birthday should not be empty");
            noticeLb.setVisible(true);
        }else if(email_input.isEmpty()){
            noticeLb.setText("email should not be empty");
            noticeLb.setVisible(true);
        }else {
            if (!password_input.equals(confirmedPassword_input)) {
                Alert checkConfPass = new Alert(Alert.AlertType.ERROR);
                checkConfPass.setContentText("Confirm Password sai, mời nhập lại Password");
                checkConfPass.show();
            } else {
                // Chỉnh sửa lại các fiedl cho phù hợp với database

                USERS user_con = new USERS();
                ACCOUNT account_con = new ACCOUNT();
                String id_user = user_con.generate_IDuser();
                int res_user = user_con.insert_user(id_user,
                                                    fullname_input,
                                                    dayOfBirth_input.toString(),
                                                    email_input);
                String id_account = account_con.generate_IDaccount();
                int res_account = account_con.insert_account(id_account, id_user, username_input, password_input, role_num);
                if(res_account == 1 && res_user == 1){
                    noticeLb.setText("Thêm user thành công");
                    noticeLb.setVisible(true);
                }else{
                    noticeLb.setText("username đã tồn tại");
                    noticeLb.setVisible(true);
                }
            }
        }
    }

    public void backBtnAction(ActionEvent event) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/StaffManagementScreen/StaffManagementScreen.fxml"));
        Stage loginSceneStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene loginScene = new Scene(loginParent);

        loginSceneStage.setScene(loginScene);
        loginSceneStage.show();
    }

    private void closeStage(ActionEvent event) {
        Node  source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}

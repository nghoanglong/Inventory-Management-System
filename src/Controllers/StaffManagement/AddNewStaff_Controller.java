package Controllers.StaffManagement;

import Models.ACCOUNT;
import Models.USERS;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;


import java.lang.constant.Constable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

public class AddNewStaff_Controller
{
    @FXML
    private TextField fullnameTF;
    @FXML
    private TextField usernameTF;
    @FXML
    private PasswordField pwdTF;
    @FXML
    private PasswordField confirm_pwdTF;
    @FXML
    private DatePicker birthDp;
    @FXML
    private TextField emailTF;
    @FXML
    private ComboBox roleCb;
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
        roleCb.setItems(options);
        noticeLabel.setVisible(false);
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

    public static String HashingtoPassword(String password){
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(password.getBytes());

            byte[] resultByteArray = messageDigest.digest();

            StringBuilder stringBuilder = new StringBuilder();

            for(byte b: resultByteArray){
                stringBuilder.append(String.format("%02x", b));
            }

            return stringBuilder.toString();
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return "";
    }

    public void addBtnAction(ActionEvent event)
    {
        String fullname_input = fullnameTF.getText();
        String username_input = usernameTF.getText();
        String password_input = pwdTF.getText();
        String confirmedPassword_input = confirm_pwdTF.getText();
        LocalDate dayOfBirth_input = birthDp.getValue();
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
                // Chỉnh sửa lại các fiedl cho phù hợp với database

                USERS user_con = new USERS();
                ACCOUNT account_con = new ACCOUNT();
                String id_user = user_con.generate_IDuser();
                int res_user = user_con.insert_user(id_user,
                                                    fullname_input,
                                                    dayOfBirth_input.toString(),
                                                    email_input);
                String id_account = account_con.generate_IDaccount();
                int res_account = account_con.insert_account(id_account, id_user, username_input, HashingtoPassword(password_input), role_num);
                if(res_account == 1 && res_user == 1){
                    noticeLabel.setText("Thêm user thành công");
                    noticeLabel.setVisible(true);
                    StaffManagement_Controller.data.clear();
                    StaffManagement_Controller.data.addAll(user_con.getTableUSER());
                }else{
                    noticeLabel.setText("username đã tồn tại");
                    noticeLabel.setVisible(true);
                }
            }
        }
    }

}

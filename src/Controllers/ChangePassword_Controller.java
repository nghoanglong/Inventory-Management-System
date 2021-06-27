package Controllers;

import Controllers.StaffManagement.AddNewStaff_Controller;
import Models.ACCOUNT;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;

import java.util.HashMap;

public class ChangePassword_Controller {
    @FXML
    private TextField old_pwdTF;
    @FXML
    private TextField new_pwdTF;
    @FXML
    private TextField confirm_pwdTF;
    @FXML
    private Label noticeLabel;
    @FXML
    public void initialize() {
        noticeLabel.setVisible(false);
    }


    public void saveBtnAction(ActionEvent event){
        ACCOUNT account_con = new ACCOUNT();
        HashMap<String, String> data = new HashMap<String, String>();
        String oldpwd = old_pwdTF.getText();
        String newpwd = new_pwdTF.getText();
        String confirmpwd = confirm_pwdTF.getText();
        if (!account_con.check_password(Login_Controller.id_cur_user, AddNewStaff_Controller.HashingtoPassword(oldpwd))) {
            noticeLabel.setText("Mật khẩu không đúng");
            noticeLabel.setVisible(true);
        }
        else if(!newpwd.equals(confirmpwd)){
            noticeLabel.setText("Confirm password sai, mời nhập lại");
            noticeLabel.setVisible(true);
        }
        else{
            data.put("pwd", AddNewStaff_Controller.HashingtoPassword(newpwd));
            int result = account_con.update_account(Login_Controller.id_cur_user, data);
            if(result == 1) {
                noticeLabel.setText("Update thành công");
                noticeLabel.setVisible(true);
                this.empty_field();
            }else {
                noticeLabel.setText("Update không thành công");
                noticeLabel.setVisible(true);
                this.empty_field();
            }
        }
    }

    public void empty_field(){
        this.old_pwdTF.setText("");
        this.new_pwdTF.setText("");
        this.confirm_pwdTF.setText("");
    }
}

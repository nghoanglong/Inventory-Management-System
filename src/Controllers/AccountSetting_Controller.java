package Controllers;

import Models.ACCOUNT;
import Models.USERS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;

public class AccountSetting_Controller {
    @FXML
    private TextField oldpwdTf;
    @FXML
    private TextField newpwdTf;
    @FXML
    private TextField confirmpwdTf;
    @FXML
    private Button saveBtn;
    @FXML
    private Label noticeLb;
    @FXML
    public void initialize() {
        noticeLb.setVisible(false);
    }


    public void saveBtnAction(ActionEvent event){
        ACCOUNT account_con = new ACCOUNT();
        HashMap<String, String> data = new HashMap<String, String>();
        String oldpwd = oldpwdTf.getText();
        String newpwd = newpwdTf.getText();
        String confirmpwd = confirmpwdTf.getText();
        if (!oldpwd.equals(account_con.get_pwd(Login_Controller.id_cur_user))) {
            noticeLb.setText("Mật khẩu không đúng");
            noticeLb.setVisible(true);
        }
        else if(!newpwd.equals(confirmpwd)){
            noticeLb.setText("Mật khẩu confirm không khớp");
            noticeLb.setVisible(true);
        }
        else{
            data.put("pwd",newpwd);
            int result = account_con.update_account(Login_Controller.id_cur_user, data);
            if(result == 1) {
                noticeLb.setText("Update thành công");
                noticeLb.setVisible(true);
                this.empty_field();
            }else {
                noticeLb.setText("Update không thành công");
                noticeLb.setVisible(true);
                this.empty_field();
            }
        }
    }

    public void empty_field(){
        this.oldpwdTf.setText("");
        this.newpwdTf.setText("");
        this.confirmpwdTf.setText("");
    }
}

package Controllers;

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
    private TextField fullnameTF;
    @FXML
    private TextField pwdTF;
    @FXML
    private TextField confirmpwdTF;
    @FXML
    private DatePicker ageDP;
    @FXML
    private TextField emailTF;
    @FXML
    private Button saveChangeBtn;
    @FXML
    private ImageView homebackBtn;
    @FXML
    private Label noticeLabel;
    @FXML
    public void initialize() {
        noticeLabel.setVisible(false);
    }


    public void saveChangeBtnAction(ActionEvent event){
        HashMap<String, String> data = new HashMap<String, String>();
        String pwd = pwdTF.getText();
        String confirmpwd = confirmpwdTF.getText();
        LocalDate age = ageDP.getValue();
        String email = emailTF.getText();
        if (!pwd.equals(confirmpwd)) {
            noticeLabel.setText("Mật khẩu confirm không khớp");
            noticeLabel.setVisible(true);
        }else {
            if (!pwd.isEmpty()){
                data.put("pwd", pwd);
            }
            if(age != null){
                data.put("age", age.toString());
            }
            if(!email.isEmpty()){
                data.put("email", email);
            }
            USERS user_con = new USERS();
            int result = user_con.update_user(Login_Controller.id_cur_user, data);
            if(result == 1) {
                noticeLabel.setText("Update thành công");
                noticeLabel.setVisible(true);
                this.deleteallTF();
            }else {
                noticeLabel.setText("Update không thành công");
                noticeLabel.setVisible(true);
                this.deleteallTF();
            }
        }
    }

    public void homeBackBtnAction(MouseEvent event){
        String home_screen = "";
        if(Login_Controller.type_cur_user == 1){
            home_screen = "Views/HomeScreen/AdminHome/AdminHome_Screen.fxml";
        }else if(Login_Controller.type_cur_user == 2){
            home_screen = "Views/HomeScreen/WarehouseHome/WarehouseHome_Screen.fxml";
        }else{
            home_screen = "Views/HomeScreen/SellerHome/SellerHome_Screen.fxml";
        }
        Parent HomeScreen = null;
        try {
            HomeScreen = FXMLLoader.load(getClass().getClassLoader().getResource(home_screen));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage HomeScreen_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene HomeScreen_Scene = new Scene(HomeScreen);
        HomeScreen_Stage.setScene(HomeScreen_Scene);
        HomeScreen_Stage.show();
    }

    public void deleteallTF(){
        this.fullnameTF.clear();
        this.pwdTF.clear();
        this.confirmpwdTF.clear();
        this.ageDP.getEditor().clear();
        this.emailTF.clear();
    }

}

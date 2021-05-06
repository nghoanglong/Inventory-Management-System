package Controllers;

import Models.QLSP;
import Models.USERS;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.imageio.IIOException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class SettingAccountController {
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
    private Button homeBackBtn;
    @FXML
    private Label noticeLabel;
    @FXML
    public void initialize() {
        noticeLabel.setVisible(false);
    }


    public void setSaveChangeBtnAction(ActionEvent event){
        HashMap<String, String> data = new HashMap<String, String>();
        String fullname = fullnameTF.getText();
        String pwd = pwdTF.getText();
        String confirmpwd = confirmpwdTF.getText();
        String age = ageDP.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String email = emailTF.getText();
        if (!pwd.equals(confirmpwd)) {
            noticeLabel.setText("Mật khẩu confirm không khớp");
            noticeLabel.setVisible(true);
        }else {
            if (!fullname.isEmpty()) {
                data.put("fullname", fullname);
            }
            if (!pwd.isEmpty()){
                data.put("pwd", pwd);
            }
            if(!age.isEmpty()){
                data.put("age", age);
            }
            if(!email.isEmpty()){
                data.put("email", email);
            }
            USERS user_con = new USERS();
            user_con.update_user(LoginController.id_cur_user, data);
            noticeLabel.setText("Update thành công");
            noticeLabel.setVisible(true);
        }
    }
    public void homeBackBtnAction(ActionEvent event) throws IOException {
        String home_screen = "";
        if(LoginController.type_cur_user == 1){
            home_screen = "Views/HomeScreen/AdminLauncher/admin_launcher.fxml";
        }else if(LoginController.type_cur_user == 2){
            home_screen = "Views/HomeScreen/WarehouseLauncher/warehouse_launcher.fxml";
        }else{
            home_screen = "Views/HomeScreen/UserLauncher/user_launcher.fxml";
        }

        Parent HomeScreen = FXMLLoader.load(getClass().getClassLoader().getResource(home_screen));
        Stage HomeScreen_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene HomeScreen_Scene = new Scene(HomeScreen);
        HomeScreen_Stage.setScene(HomeScreen_Scene);
        HomeScreen_Stage.show();
    }

}

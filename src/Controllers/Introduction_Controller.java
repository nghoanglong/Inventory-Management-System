package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Introduction_Controller {
    @FXML
    private Button homeBtn;
    @FXML
    private Button logOutBtn;

    public void homeBtnAction(ActionEvent event) throws IOException {
        String home_screen = "";
        if(Login_Controller.type_cur_user == 1){
            home_screen = "Views/HomeScreen/AdminHome/AdminHome_Screen.fxml";
        }else if(Login_Controller.type_cur_user == 2){
            home_screen = "Views/HomeScreen/WarehouseHome/WarehouseHome_Screen.fxml";
        }else{
            home_screen = "Views/HomeScreen/SellerHome/SellerHome_Screen.fxml";
        }

        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource(home_screen));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void logOutBtnAction(ActionEvent event) throws IOException {
        Login_Controller.id_cur_user = null;
        Login_Controller.type_cur_user = -1;
        Parent LoginPage_Parent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/LoginScreen/Login_Screen.fxml"));
        Stage LoginPage_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene LoginPage_Scene = new Scene(LoginPage_Parent);
        LoginPage_Stage.setScene(LoginPage_Scene);
        LoginPage_Stage.setResizable(false);
        LoginPage_Stage.show();
    }
}

package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Introduction_Controller {

    public void homeBtnAction(ActionEvent event) throws IOException {
        String home_screen = "";
        if(Login_Controller.type_cur_user == 1){
            home_screen = "Views/HomeScreen/AdminHome/AdminHome_Screen.fxml";
        }else if(Login_Controller.type_cur_user == 2){
            home_screen = "Views/HomeScreen/WarehouseHome/WarehouseHome_Screen.fxml";
        }else{
            home_screen = "Views/HomeScreen/SellerHome/SellerHome_Screen.fxml";
        }

        Parent HomeScreen_parent = FXMLLoader.load(getClass().getClassLoader().getResource(home_screen));
        Stage HomeScreen_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(HomeScreen_parent);
        HomeScreen_stage.setScene(scene);
        HomeScreen_stage.setResizable(false);
        HomeScreen_stage.show();
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

package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloScreenController {
    @FXML
    private Button HomeBT;
    @FXML
    private Button LogOutBT;
    @FXML
    private Button AboutBT;

    public void HomeBTAction(ActionEvent event) throws IOException {
        Parent AdminLauncher_Parent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/AdminLauncher/admin_launcher.fxml"));
        Stage AdminLauncher_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene AdminLauncher_Scene = new Scene(AdminLauncher_Parent);
        AdminLauncher_Stage.setScene(AdminLauncher_Scene);
        AdminLauncher_Stage.show();
    }

    public void LogOutBTAction(ActionEvent event) throws IOException {
        Parent LoginPage_Parent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/LoginPage/loginpage.fxml"));
        Stage LoginPage_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene LoginPage_Scene = new Scene(LoginPage_Parent);

        LoginPage_Stage.setScene(LoginPage_Scene);
        LoginPage_Stage.show();
    }
}

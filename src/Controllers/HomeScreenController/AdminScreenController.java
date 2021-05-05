package Controllers.HomeScreenController;

import Models.USERS;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminScreenController {
    @FXML
    private Button employeeBtn;

    public void employeeButtonAction(ActionEvent event) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/Registration/registration.fxml"));
        Stage loginSceneStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene loginScene = new Scene(loginParent);

        loginSceneStage.setScene(loginScene);
        loginSceneStage.show();
    }

}

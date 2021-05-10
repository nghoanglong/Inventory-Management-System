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

public class UserScreenController {
    @FXML
    private Button orderBTN;
    @FXML
    private Button requestBTN;
    @FXML
    private Button reportBTN;
    @FXML
    private Button personnelBTN;
    @FXML
    private Button productBTN;
    @FXML
    private Button accountBTN;

    //ACTION

    public void orderBTNAction(ActionEvent event) throws IOException
    {
        Parent orderParent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/Registration/registration.fxml"));
        Stage orderStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene orderScene = new Scene(orderParent);

        orderStage.setScene(orderScene);
        orderStage.show();
    }
    public void requestBTNAction(ActionEvent event) throws IOException
    {
        Parent requestParent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/Registration/registration.fxml"));
        Stage requestStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene requestScene = new Scene(requestParent);

        requestStage.setScene(requestScene);
        requestStage.show();
    }
    public void reportBTNAction(ActionEvent event) throws IOException
    {
        Parent reportParent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/Registration/registration.fxml"));
        Stage reportStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene reportScene = new Scene(reportParent);

        reportStage.setScene(reportScene);
        reportStage.show();
    }
    public void personnelBTNAction(ActionEvent event) throws IOException
    {
        Parent personnelParent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/Registration/registration.fxml"));
        Stage personnelStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene personnelScene = new Scene(personnelParent);

        personnelStage.setScene(personnelScene);
        personnelStage.show();
    }
    public void productBTNAction(ActionEvent event) throws IOException
    {
        Parent productParent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/Registration/registration.fxml"));
        Stage productStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene productScene = new Scene(productParent);

        productStage.setScene(productScene);
        productStage.show();
    }
    public void accountBTNAction(ActionEvent event) throws IOException {
        Parent accountParent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/Registration/registration.fxml"));
        Stage accountStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene accountScene = new Scene(accountParent);

        accountStage.setScene(accountScene);
        accountStage.show();
    }
}

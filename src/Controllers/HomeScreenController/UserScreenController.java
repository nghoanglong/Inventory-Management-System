package Controllers.HomeScreenController;

import Controllers.LoginController;
import Models.USERS;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class UserScreenController {
    @FXML
    private Button donhangBtn;
    @FXML
    private Button thongkeBtn;
    @FXML
    private Button sanphamBtn;
    @FXML
    private Button accountsettingBtn;
    @FXML
    private ImageView signoutBtn;
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
        Parent productParent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/ProductManagement/product_management.fxml"));
        Stage productStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene productScene = new Scene(productParent);

        productStage.setScene(productScene);
        productStage.show();
    }
    public void AccountSettingBTNAction(ActionEvent event) throws IOException {
        Parent accountParent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/AccountSetting/accountsetting.fxml"));
        Stage accountStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene accountScene = new Scene(accountParent);

        accountStage.setScene(accountScene);
        accountStage.show();
    }
    public void signoutBtnAction(MouseEvent event) throws IOException{
        LoginController.id_cur_user = null;
        LoginController.type_cur_user = -1;
        Parent accountParent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/LoginPage/loginpage.fxml"));
        Stage accountStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene accountScene = new Scene(accountParent);

        accountStage.setScene(accountScene);
        accountStage.show();
    }
}

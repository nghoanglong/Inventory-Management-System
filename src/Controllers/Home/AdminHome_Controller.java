package Controllers.Home;

import Controllers.Login_Controller;
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

public class AdminHome_Controller {
    @FXML
    private Button mng_orderBtn;
    @FXML
    private Button mng_requestBtn;
    @FXML
    private Button reportBtn;
    @FXML
    private Button personnelBtn;
    @FXML
    private Button productBtn;
    @FXML
    private Button accountsettingBtn;
    @FXML
    private ImageView signoutBtn;


    public void mng_orderBtnAction(ActionEvent event) throws IOException
    {
        Parent orderParent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/OrderScreen/Order_Screen.fxml"));
        Stage orderStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene orderScene = new Scene(orderParent);

        orderStage.setScene(orderScene);
        orderStage.show();
    }
    public void mng_requestBtnAction(ActionEvent event) throws IOException
    {
        Parent requestParent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/ConfirmRequestScreen/ConfirmRequest_Screen.fxml"));
        Stage requestStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene requestScene = new Scene(requestParent);

        requestStage.setScene(requestScene);
        requestStage.show();
    }
    public void reportBtnAction(ActionEvent event) throws IOException
    {
        Parent reportParent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/Registration/registration.fxml"));
        Stage reportStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene reportScene = new Scene(reportParent);

        reportStage.setScene(reportScene);
        reportStage.show();
    }
    public void mng_staffBtnAction(ActionEvent event) throws IOException
    {
        Parent personnelParent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/StaffManagementScreen/StaffManagementScreen.fxml"));
        Stage personnelStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene personnelScene = new Scene(personnelParent);

        personnelStage.setScene(personnelScene);
        personnelStage.show();
    }
    public void productBtnAction(ActionEvent event) throws IOException
    {
        Parent productParent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/ProductManagementScreen/ProductManagement_Screen.fxml"));
        Stage productStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene productScene = new Scene(productParent);

        productStage.setScene(productScene);
        productStage.show();
    }
    public void accountsettingBtnAction(ActionEvent event) throws IOException {
        Parent accountParent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/AccountSettingScreen/AccountSetting_Screen.fxml"));
        Stage accountStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene accountScene = new Scene(accountParent);

        accountStage.setScene(accountScene);
        accountStage.show();
    }
    public void signoutBtnAction(MouseEvent event) throws IOException{
        Login_Controller.id_cur_user = null;
        Login_Controller.type_cur_user = -1;
        Parent accountParent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/LoginScreen/Login_Screen.fxml"));
        Stage accountStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene accountScene = new Scene(accountParent);

        accountStage.setScene(accountScene);
        accountStage.show();
    }
}

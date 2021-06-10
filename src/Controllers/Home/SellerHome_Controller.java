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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SellerHome_Controller {
    @FXML
    private Button mng_orderBtn;
    @FXML
    private Button statisticalBtn;
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

    public void statisticalBtnAction(ActionEvent event) throws IOException
    {
        Parent reportParent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/StatisticalScreen/Statistical_Screen.fxml"));
        Stage reportStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene reportScene = new Scene(reportParent);

        reportStage.setScene(reportScene);
        reportStage.show();
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
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Views/AccountSettingScreen/AccountSetting_Screen.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Them Nhan Vien");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) event.getSource()).getScene().getWindow() );
        stage.show();
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

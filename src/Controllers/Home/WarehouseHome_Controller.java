package Controllers.Home;

import Controllers.Login_Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class WarehouseHome_Controller {


    public void mng_orderBtnAction(ActionEvent event) throws IOException
    {
        Parent orderParent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/OrderScreen/Order_Screen.fxml"));
        Stage orderStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene orderScene = new Scene(orderParent);
        orderStage.setScene(orderScene);
        orderStage.setResizable(false);
        orderStage.show();
    }
    public void mng_requestBtnAction(ActionEvent event) throws IOException
    {
        Parent requestParent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/RequestManagementScreen/RequestManagement_Screen.fxml"));
        Stage requestStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene requestScene = new Scene(requestParent);
        requestStage.setScene(requestScene);
        requestStage.setResizable(false);
        requestStage.show();
    }

    public void productBtnAction(ActionEvent event) throws IOException
    {
        Parent productParent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/ProductManagementScreen/ProductManagement_Screen.fxml"));
        Stage productStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene productScene = new Scene(productParent);
        productStage.setScene(productScene);
        productStage.setResizable(false);
        productStage.show();
    }
    public void accountsettingBtnAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Views/ChangePasswordScreen/ChangePassword_Screen.fxml"));
        stage.setScene(new Scene(root));
        stage.setResizable(false);
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
        accountStage.setResizable(false);
        accountStage.show();
    }
}

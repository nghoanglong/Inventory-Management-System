package Controllers.StaffManagement;

import Controllers.ProductManagement.SANPHAM;
import Models.USERS;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class StaffManagementController {
    @FXML
    private TableView<NHANVIEN> table_nv;
    @FXML
    private TableColumn<NHANVIEN, String> id_userCol;
    @FXML
    private TableColumn<NHANVIEN, String> id_accCol;
    @FXML
    private TableColumn<NHANVIEN, String> fullnameCol;
    @FXML
    private TableColumn<NHANVIEN, String> ageCol;
    @FXML
    private TableColumn<NHANVIEN, String> emailCol;

    private ObservableList<NHANVIEN> data;

    @FXML
    public void initialize(){
        USERS users_con = new USERS();
        data = FXCollections.observableArrayList();
        initTable();
        // data.addAll(users_con.getTableUSER()); -> phần getTable đang sai các field
        this.table_nv.setItems(data);
    }

    public void initTable(){
        id_userCol.setCellValueFactory(new PropertyValueFactory<NHANVIEN, String>("id_user"));
        id_accCol.setCellValueFactory(new PropertyValueFactory<NHANVIEN, String>("id_account"));
        fullnameCol.setCellValueFactory(new PropertyValueFactory<NHANVIEN, String>("fullname"));
        ageCol.setCellValueFactory(new PropertyValueFactory<NHANVIEN, String>("age"));
        emailCol.setCellValueFactory(new PropertyValueFactory<NHANVIEN, String>("email"));
    }
    public void backBtnAction(ActionEvent e) throws IOException {
        Parent homeParent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/HomeScreen/AdminHome/AdminHome_Screen.fxml"));
        Stage homeStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene homeScene = new Scene(homeParent);

        homeStage.setScene(homeScene);
        homeStage.show();
    }
    public void themNVBtnAction(ActionEvent e) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/Registration/registration.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);

        stage.setScene(scene);
        stage.show();
    }
}

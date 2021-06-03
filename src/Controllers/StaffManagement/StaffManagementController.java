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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class StaffManagementController {
    @FXML
    private TableView<NHANVIEN> table_nv;
    @FXML
    private TableColumn<NHANVIEN, String> id_userCol;
    @FXML
    private TableColumn<NHANVIEN, String> fullnameCol;
    @FXML
    private TableColumn<NHANVIEN, String> roleCol;
    @FXML
    private TableColumn<NHANVIEN, String> ageCol;
    @FXML
    private TableColumn<NHANVIEN, String> emailCol;
    @FXML
    private Label noticeDelLabel;
    @FXML
    private Label tennvLb;
    @FXML
    private Label roleLb;
    @FXML
    private Label birthLb;
    @FXML
    private Label emailLb;
    @FXML
    private Label usernameLb;

    private ObservableList<NHANVIEN> data;

    @FXML
    public void initialize(){
        this.noticeDelLabel.setVisible(false);
        this.tennvLb.setVisible(false);
        this.roleLb.setVisible(false);
        this.birthLb.setVisible(false);
        this.emailLb.setVisible(false);
        this.usernameLb.setVisible(false);
        USERS users_con = new USERS();
        data = FXCollections.observableArrayList();
        initTable();
        // data.addAll(users_con.getTableUSER()); -> phần getTable đang sai các field
        this.table_nv.setItems(data);
    }

    public void initTable(){
        id_userCol.setCellValueFactory(new PropertyValueFactory<NHANVIEN, String>("id_user"));
        fullnameCol.setCellValueFactory(new PropertyValueFactory<NHANVIEN, String>("fullname"));
        roleCol.setCellValueFactory(new PropertyValueFactory<NHANVIEN, String>("role"));
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

    @FXML
    public void themBtnAction(ActionEvent e) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Views/StaffManagement/AddStaffDialog/addStaffDialog.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Them Nhan Vien");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) e.getSource()).getScene().getWindow() );
        stage.show();
    }
}

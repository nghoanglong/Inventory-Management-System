package Controllers.StaffManagement;

import Controllers.ProductManagement.SANPHAM;
import Models.USERS;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Date;

public class StaffManagement_Controller {

    private String id_user_selected = null;
    private String fullname_selected = null;
    private String account_role_selected = null;
    private String dateOfBirth_selected = null;
    private String email_selected = null;


    @FXML
    private TableView<NHANVIEN> table_nv;
    @FXML
    private TableColumn<NHANVIEN, String> id_userCol;
    @FXML
    private TableColumn<NHANVIEN, String> fullnameCol;
    @FXML
    private TableColumn<NHANVIEN, String> account_roleCol;
    @FXML
    private TableColumn<NHANVIEN, String> dateOfBirthCol;
    @FXML
    private TableColumn<NHANVIEN, String> emailCol;


    @FXML
    private Label noticeDelLabel;
    @FXML
    private Label id_userLb;
    @FXML
    private Label fullnameLb;
    @FXML
    private Label account_roleLb;
    @FXML
    private Label dateOfBirthLb;
    @FXML
    private Label emailLb;

    public static ObservableList<NHANVIEN> data;

    @FXML
    public void initialize(){
        USERS users_con = new USERS();
        data = FXCollections.observableArrayList();
        initTable();
        data.addAll(users_con.getTableUSER());
        setLabel();
        this.table_nv.setItems(data);
    }

    public void initTable(){
        id_userCol.setCellValueFactory(new PropertyValueFactory<NHANVIEN, String>("id_user"));
        fullnameCol.setCellValueFactory(new PropertyValueFactory<NHANVIEN, String>("fullname"));
        account_roleCol.setCellValueFactory(new PropertyValueFactory<NHANVIEN, String>("account_role"));
        dateOfBirthCol.setCellValueFactory(new PropertyValueFactory<NHANVIEN, String>("dateOfBirth"));
        emailCol.setCellValueFactory(new PropertyValueFactory<NHANVIEN, String>("email"));
    }

    public void setLabel(){
        this.noticeDelLabel.setVisible(false);
        this.id_userLb.setVisible(false);
        this.fullnameLb.setVisible(false);
        this.account_roleLb.setVisible(false);
        this.dateOfBirthLb.setVisible(false);
        this.emailLb.setVisible(false);
    }

    public void table_nvAction(MouseEvent event){
        NHANVIEN selected = table_nv.getSelectionModel().getSelectedItem();
        this.id_user_selected = selected.getId_user();
        this.fullname_selected = selected.getFullname();
        this.account_role_selected = selected.getAccount_role();
        this.dateOfBirth_selected = selected.getDateOfBirth();
        this.email_selected = selected.getEmail();

        id_userLb.setText(this.id_user_selected);
        id_userLb.setVisible(true);
        fullnameLb.setText(this.fullname_selected);
        fullnameLb.setVisible(true);
        account_roleLb.setText(this.account_role_selected);
        account_roleLb.setVisible(true);
        dateOfBirthLb.setText(this.dateOfBirth_selected.toString());
        dateOfBirthLb.setVisible(true);
        emailLb.setText(this.email_selected);
        emailLb.setVisible(true);
    }
    public void backBtnAction(ActionEvent e) throws IOException {
        Parent homeParent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/HomeScreen/AdminHome/AdminHome_Screen.fxml"));
        Stage homeStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene homeScene = new Scene(homeParent);

        homeStage.setScene(homeScene);
        homeStage.show();
    }

    public void add_staffBtnAction(ActionEvent e) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Views/StaffManagementScreen/AddNewStaffScreen/AddNewStaff_Screen.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Them Nhan Vien");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) e.getSource()).getScene().getWindow() );
        stage.show();
    }
    public void delete_staffBtnAction(ActionEvent event){
        USERS user_con = new USERS();
        int res_del_user = user_con.delete_user(id_user_selected);
        if(res_del_user == 0){
            noticeDelLabel.setText("Xóa không thành công");
            noticeDelLabel.setVisible(true);
        }else{
            int idx = table_nv.getSelectionModel().getSelectedIndex();
            data.remove(idx);
            id_userLb.setText(null);
            fullnameLb.setText(null);
            account_roleLb.setText(null);
            dateOfBirthLb.setText(null);
            emailLb.setText(null);
            noticeDelLabel.setText("Yêu cầu xóa nhân viên thành công");
            noticeDelLabel.setVisible(true);
        }
    }
}

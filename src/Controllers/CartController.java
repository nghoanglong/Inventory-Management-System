package Controllers;

import Controllers.ProductManagement.ProductManagementController;
import Controllers.ProductManagement.SANPHAM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.event.ActionEvent;

public class CartController {
    @FXML
    private TextField tenkh_tf;
    @FXML
    private TextField sdtkh_tf;
    @FXML
    private TextField diachikh_tf;

    @FXML
    private TableView<SANPHAM> chitietyc_tv;
    @FXML
    private TableColumn<SANPHAM,String> tenspCol;
    @FXML
    private TableColumn<SANPHAM,String> loaispCol;
    @FXML
    private TableColumn<SANPHAM,Integer> giaspCol;
    @FXML
    private TableColumn<SANPHAM,Integer> numspCol;


    @FXML
    private Button ycnhap_btn;
    @FXML
    private Button ycxuat_btn;
    @FXML
    private Button clear_btn;

    //Class Variable
    private ObservableList<SANPHAM> data;

    @FXML
    public void initialize(){
        data = FXCollections.observableArrayList(ProductManagementController.lisp_yc);
        this.initTable();
        this.chitietyc_tv.setItems(data);
    }

    public void initTable(){
        tenspCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, String>("ten_sp"));
        loaispCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, String>("loai_sp"));
        giaspCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, Integer>("gia_sp"));
        numspCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, Integer>("num_sp"));
    }

    public void NhapBtnAction(ActionEvent e){
        // Gom cục data thành 1 đơn hàng và insert vô table YEUCAU để đợi Admin và WHManager phê duyệt
    }

    public void XuatBtnAction(ActionEvent e){
        // Gom cục data thành 1 đơn hàng và insert vô table YEUCAU để đợi Admin và WHManager phê duyệt
    }
}


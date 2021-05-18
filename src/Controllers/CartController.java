package Controllers;

import Controllers.ProductManagement.ProductManagementController;
import Controllers.ProductManagement.SANPHAM;
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
import javafx.stage.Stage;

<<<<<<< HEAD
=======
import java.awt.event.ActionEvent;
>>>>>>> a11a7fc328669b842b4f621fca535ad642b6c0f9
import java.io.IOException;

public class CartController {
    @FXML
    private TextField tenkhTF;
    @FXML
    private TextField sdtkhTF;
    @FXML
    private TextField diachikhTF;

    @FXML
    private TableView<SANPHAM> chitietycTV;
<<<<<<< HEAD
    @FXML
    private TableColumn<SANPHAM, String> idspCol;
=======
>>>>>>> a11a7fc328669b842b4f621fca535ad642b6c0f9
    @FXML
    private TableColumn<SANPHAM,String> tenspCol;
    @FXML
    private TableColumn<SANPHAM,String> loaispCol;
    @FXML
    private TableColumn<SANPHAM,Integer> giaspCol;
    @FXML
    private TableColumn<SANPHAM,Integer> numspCol;


    @FXML
    private Button ycnhapBtn;
<<<<<<< HEAD
    @FXML
    private Button ycxuatBtn;
    @FXML
    private Button xoaspBtn;
    @FXML
=======
    @FXML
    private Button ycxuatBtn;
    @FXML
    private Button xoaspBtn;
    @FXML
>>>>>>> a11a7fc328669b842b4f621fca535ad642b6c0f9
    private Button backBtn;

    //Class Variable
    private ObservableList<SANPHAM> data;

    @FXML
    public void initialize(){
        data = FXCollections.observableArrayList(ProductManagementController.lisp_yc);
        this.initTable();
        this.chitietycTV.setItems(data);
    }

    public void initTable(){
        idspCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, String>("id_sp"));
        tenspCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, String>("ten_sp"));
        loaispCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, String>("loai_sp"));
        giaspCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, Integer>("gia_sp"));
        numspCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, Integer>("num_sp"));
    }
    public void ycnhapBtnAction(ActionEvent e){
        // Gom cục data thành 1 đơn hàng và insert vô table YEUCAU để đợi Admin và WHManager phê duyệt

        // Sau khi thực hiện tạo đơn hàng thành công thì xóa dữ liệu đang lưu trong ArrayList tạm
        ProductManagementController.lisp_yc.clear();
    }
    public void ycxuatBtnAction(ActionEvent e){
        // Gom cục data thành 1 đơn hàng và insert vô table YEUCAU để đợi Admin và WHManager phê duyệt

        // Sau khi thực hiện tạo đơn hàng thành công thì xóa dữ liệu đang lưu trong ArrayList tạm
        ProductManagementController.lisp_yc.clear();
    }
    public void backBtnAction(ActionEvent e) throws IOException {
        Parent homeParent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/ProductManagement/product_management.fxml"));
        Stage homeStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene homeScene = new Scene(homeParent);

        homeStage.setScene(homeScene);
        homeStage.show();
        ProductManagementController.lisp_yc.removeAll(data);
    }
    public void ycxuatBtnAaction(ActionEvent e){
        // Gom cục data thành 1 đơn hàng và insert vô table YEUCAU để đợi Admin và WHManager phê duyệt

        // Sau khi thực hiện tạo đơn hàng thành công thì xóa dữ liệu đang lưu trong ArrayList tạm
        ProductManagementController.lisp_yc.removeAll(data);
    }

    public void xoaBtnAction(ActionEvent e){
        SANPHAM selected = chitietycTV.getSelectionModel().getSelectedItem();
        ProductManagementController.lisp_yc.remove(selected);
        int idx = chitietycTV.getSelectionModel().getSelectedIndex();
        data.remove(idx);
    }
}


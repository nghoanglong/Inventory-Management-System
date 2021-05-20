package Controllers.ProductManagement;

import Controllers.LoginController;
import Controllers.ProductManagement.ProductManagementController;
import Controllers.ProductManagement.SANPHAM;
import Models.*;
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

import java.io.IOException;

public class CartController {
    @FXML
    private TextField tenkhTF;
    @FXML
    private TextField sdtkhTF;
    @FXML
    private TextField diachikhTF;
    @FXML
    private Label messageLabel;

    @FXML
    private TableView<SANPHAM> chitietycTV;
    @FXML
    private TableColumn<SANPHAM, String> idspCol;
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
    @FXML
    private Button ycxuatBtn;
    @FXML
    private Button xoaspBtn;
    @FXML
    private Button backBtn;

    //Class Variable
    private ObservableList<SANPHAM> data;

    @FXML
    public void initialize(){
        data = FXCollections.observableArrayList(ProductManagementController.lisp_yc);
        this.initTable();
        this.chitietycTV.setItems(data);
        messageLabel.setVisible(false);
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
        TTKH new_kh = new TTKH();
        String id_kh = new_kh.generate_IDkh();
        int result_in_ttkh = new_kh.insert_TTKH(id_kh, tenkhTF.getText(), sdtkhTF.getText(), diachikhTF.getText());

        QLDH new_qldh = new QLDH();
        String id_dh = new_qldh.generate_IDdh();
        int result_in_qldh = new_qldh.insert_qldh(id_dh, LoginController.id_cur_user, id_kh);

        QLYC new_qlyc = new QLYC();
        String id_qlyc = new_qlyc.generate_IDyc();
        int result_in_qlyc = new_qlyc.insert_qlyc(id_qlyc, id_dh, "Import", java.time.LocalDate.now().toString());

        TRANGTHAI_YC new_ttyc = new TRANGTHAI_YC();
        String id_ttyc = new_ttyc.generate_IDttyc();
        int result_int_ttyc;
        if(LoginController.type_cur_user == 1) {
            // trường hợp current login là admin -> admin state = accept
            result_int_ttyc = new_ttyc.insert_ttyc(id_ttyc, id_qlyc, 1, 2, null);
        }else{
            // trường hợp current login là user
            result_int_ttyc = new_ttyc.insert_ttyc(id_ttyc, id_qlyc, 2, 2, null);
        }

        CTYC new_ctyc = new CTYC();
        for(SANPHAM row: chitietycTV.getItems()){
            new_ctyc.insert_ctyc(id_qlyc, row.getId_sp(), row.getNum_sp());
        }
        if(result_in_qldh == 0 || result_in_qlyc == 0 || result_in_ttkh == 0 || result_int_ttyc == 0){
            messageLabel.setText("Yêu cầu nhập không thành công");
            messageLabel.setVisible(true);
        }else{
            messageLabel.setText("Yêu cầu nhập thành công");
            messageLabel.setVisible(true);
        }
        ProductManagementController.lisp_yc.clear();
        data.clear();
    }
    public void ycxuatBtnAction(ActionEvent e){
        // Xử lý logic
        boolean check_err = false;
        QLSP new_qlsp = new QLSP();
        for(SANPHAM row: chitietycTV.getItems()){
            int num_sp_exist = new_qlsp.getNumsp(row.getId_sp());
            int num_sp_yc = row.getNum_sp();
            if(num_sp_exist - num_sp_yc < 0){
                messageLabel.setText("Order san pham " + row.getTen_sp() + " vuot qua so luong hien co");
                messageLabel.setVisible(true);
                check_err = true;
                break;
            }
        }
        if(!check_err) {
            // nếu số lượng order ổn -> thực hiện insert database

            TTKH new_kh = new TTKH();
            String id_kh = new_kh.generate_IDkh();
            int result_in_ttkh = new_kh.insert_TTKH(id_kh, tenkhTF.getText(), sdtkhTF.getText(), diachikhTF.getText());

            QLDH new_qldh = new QLDH();
            String id_dh = new_qldh.generate_IDdh();
            int result_in_qldh = new_qldh.insert_qldh(id_dh, LoginController.id_cur_user, id_kh);

            QLYC new_qlyc = new QLYC();
            String id_qlyc = new_qlyc.generate_IDyc();
            int result_in_qlyc = new_qlyc.insert_qlyc(id_qlyc, id_dh, "Export", java.time.LocalDate.now().toString());

            TRANGTHAI_YC new_ttyc = new TRANGTHAI_YC();
            String id_ttyc = new_ttyc.generate_IDttyc();
            int result_int_ttyc = new_ttyc.insert_ttyc(id_ttyc, id_qlyc, 1, 2, null);

            CTYC new_ctyc = new CTYC();
            for (SANPHAM row : chitietycTV.getItems()) {
                new_ctyc.insert_ctyc(id_qlyc, row.getId_sp(), row.getNum_sp());
                new_qlsp.update_qlsp(row.getId_sp(), 2, row.getNum_sp());
            }
            if (result_in_qldh == 0 || result_in_qlyc == 0 || result_in_ttkh == 0 || result_int_ttyc == 0) {
                messageLabel.setText("Yêu cầu xuất không thành công");
                messageLabel.setVisible(true);
            } else {
                messageLabel.setText("Yêu cầu xuất thành công");
                messageLabel.setVisible(true);
            }
            ProductManagementController.lisp_yc.clear();
            data.clear();
        }
    }
    public void backBtnAction(ActionEvent e) throws IOException {
        Parent homeParent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/ProductManagement/product_management.fxml"));
        Stage homeStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene homeScene = new Scene(homeParent);

        homeStage.setScene(homeScene);
        homeStage.show();
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


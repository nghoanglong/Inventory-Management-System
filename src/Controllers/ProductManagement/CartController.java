package Controllers.ProductManagement;

import Controllers.LoginController;
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
    private Label request_label;
    @FXML
    private Label notice_infocus_label;

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
    private ObservableList<SANPHAM> data_cart;

    @FXML
    public void initialize(){
        data_cart = FXCollections.observableArrayList(ProductManagementController.lisp_yc);
        this.initTable();
        this.chitietycTV.setItems(data_cart);
        request_label.setVisible(false);
        notice_infocus_label.setVisible(false);
    }

    public void initTable(){
        idspCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, String>("id_prod"));
        tenspCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, String>("name_prod"));
        loaispCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, String>("type_prod"));
        giaspCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, Integer>("price"));
        numspCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, Integer>("num_exist"));
    }
    public void ycnhapBtnAction(ActionEvent e){
        // Gom cục data thành 1 đơn hàng và insert vô table YEUCAU để đợi Admin và WHManager phê duyệt
        // Sau khi thực hiện tạo đơn hàng thành công thì xóa dữ liệu đang lưu trong ArrayList tạm
        int admin_state;
        if(LoginController.type_cur_user == 1){
            admin_state = 1;
        }else{
            admin_state = 2;
        }

        String name_cus = tenkhTF.getText();
        String phone_cus = sdtkhTF.getText();
        String address_cus = diachikhTF.getText();

        if(name_cus.isEmpty() || phone_cus.isEmpty() || address_cus.isEmpty()){
            notice_infocus_label.setText("Hãy điền đầy đủ thông tin khách hàng");
            notice_infocus_label.setVisible(true);
        }else {
            CUSTOMER_INFO customer_con = new CUSTOMER_INFO();
            String id_cus = customer_con.generate_IDcus();
            int res_in_customer = customer_con.insert_customer_info(id_cus, name_cus, phone_cus, address_cus);

            MNG_ORDERS mngord_con = new MNG_ORDERS();
            String id_ord = mngord_con.generate_IDmngord();
            int res_in_mngord = mngord_con.insert_mng_orders(id_ord, LoginController.id_cur_user, id_cus, "IMPORT", java.time.LocalDate.now().toString(), 2);


            DETAIL_ORD detailord_con = new DETAIL_ORD();
            for (SANPHAM row : chitietycTV.getItems()) {
               detailord_con.insert_detail_ord(id_ord, row.getId_prod(), row.getNum_exist());
            }

            IMPORT_ORD import_ord_con = new IMPORT_ORD();
            String id_import_ord = import_ord_con.generate_IDimportord();
            int res_int_importord = import_ord_con.insert_import_ord(id_import_ord, id_ord, admin_state, 2, null);

            if (res_in_customer == 0 || res_in_mngord == 0 || res_int_importord == 0) {
                request_label.setText("Yêu cầu nhập không thành công");
                request_label.setVisible(true);
            } else {
                request_label.setText("Yêu cầu nhập thành công");
                request_label.setVisible(true);
            }
            ProductManagementController.lisp_yc.clear();
            data_cart.clear();
            notice_infocus_label.setVisible(false);
            request_label.setVisible(false);
            tenkhTF.clear();
            sdtkhTF.clear();
            diachikhTF.clear();
        }
    }
    public void ycxuatBtnAction(ActionEvent e){
        // Xử lý logic
        boolean check_err = false;
        PRODUCTION production_con = new PRODUCTION();
        for(SANPHAM row: chitietycTV.getItems()){
            int num_sp_exist = production_con.getNumProductionExist(row.getId_prod());
            int num_sp_yc = row.getNum_exist();
            if(num_sp_exist - num_sp_yc < 0){
                request_label.setText("Order san pham " + row.getName_prod() + " vuot qua so luong hien co");
                request_label.setVisible(true);
                check_err = true;
                break;
            }
        }
        if(!check_err) {
            // nếu số lượng order ổn -> thực hiện insert database
            String name_cus = tenkhTF.getText();
            String phone_cus = sdtkhTF.getText();
            String address_cus = diachikhTF.getText();

            if(name_cus.isEmpty() || phone_cus.isEmpty() || address_cus.isEmpty()){
                notice_infocus_label.setText("Hãy điền đầy đủ thông tin khách hàng");
                notice_infocus_label.setVisible(true);
            }else {
                CUSTOMER_INFO customer_con = new CUSTOMER_INFO();
                String id_cus = customer_con.generate_IDcus();
                int res_in_customer = customer_con.insert_customer_info(id_cus, tenkhTF.getText(), sdtkhTF.getText(), diachikhTF.getText());

                MNG_ORDERS mngord_con = new MNG_ORDERS();
                String id_ord = mngord_con.generate_IDmngord();
                int res_in_mngord = mngord_con.insert_mng_orders(id_ord, LoginController.id_cur_user, id_cus, "EXPORT", java.time.LocalDate.now().toString(), 2);


                DETAIL_ORD detail_ord_con = new DETAIL_ORD();
                for (SANPHAM row : chitietycTV.getItems()) {
                    detail_ord_con.insert_detail_ord(id_ord, row.getId_prod(), row.getNum_exist());
                }

                EXPORT_ORD export_ord_con = new EXPORT_ORD();
                String id_export_ord = export_ord_con.generate_IDexportord();
                int res_in_export_ord = export_ord_con.insert_export_ord(id_export_ord, id_ord, 1, 2, null);

                if (res_in_customer == 0 || res_in_mngord == 0 || res_in_export_ord == 0) {
                    request_label.setText("Yêu cầu xuất không thành công");
                    request_label.setVisible(true);
                } else {
                    request_label.setText("Yêu cầu xuất thành công");
                    request_label.setVisible(true);
                }
                ProductManagementController.lisp_yc.clear();
                data_cart.clear();
                notice_infocus_label.setVisible(false);
                request_label.setVisible(false);
                tenkhTF.clear();
                sdtkhTF.clear();
                diachikhTF.clear();
            }
        }
    }
    public void backBtnAction(ActionEvent e) throws IOException {
        Parent homeParent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/ProductManagementScreen/product_management.fxml"));
        Stage homeStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene homeScene = new Scene(homeParent);

        homeStage.setScene(homeScene);
        homeStage.show();
    }
    public void xoaBtnAction(ActionEvent e){
        SANPHAM selected = chitietycTV.getSelectionModel().getSelectedItem();
        ProductManagementController.lisp_yc.remove(selected);
        int idx = chitietycTV.getSelectionModel().getSelectedIndex();
        data_cart.remove(idx);
        request_label.setVisible(false);
    }
}


package Controllers.ProductManagement;

import Controllers.Login_Controller;
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
import javafx.stage.StageStyle;

import java.io.IOException;

public class Cart_Controller {
    @FXML
    private TextField name_cusTF;
    @FXML
    private TextField phone_cusTF;
    @FXML
    private TextField address_cusTF;
    @FXML
    private Label notice_infocus_label;

    @FXML
    private TableView<SANPHAM> cartTable;
    @FXML
    private TableColumn<SANPHAM, String> id_prodCol;
    @FXML
    private TableColumn<SANPHAM,String> name_prodCol;
    @FXML
    private TableColumn<SANPHAM,String> type_prodCol;
    @FXML
    private TableColumn<SANPHAM,Integer> priceCol;
    @FXML
    private TableColumn<SANPHAM,Integer> num_cartCol;


    //Class Variable
    private ObservableList<SANPHAM> data_cart;

    @FXML
    public void initialize(){
        data_cart = FXCollections.observableArrayList(ProductManagement_Controller.li_prod_request);
        this.initTable();
        this.cartTable.setItems(data_cart);
        notice_infocus_label.setVisible(false);
    }

    public void initTable(){
        id_prodCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, String>("id_prod"));
        name_prodCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, String>("name_prod"));
        type_prodCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, String>("type_prod"));
        priceCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, Integer>("price"));
        num_cartCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, Integer>("num_exist"));
    }
    public void import_reqBtnAction(ActionEvent e){
        // Gom cục data thành 1 đơn hàng và insert vô table YEUCAU để đợi Admin và WHManager phê duyệt
        // Sau khi thực hiện tạo đơn hàng thành công thì xóa dữ liệu đang lưu trong ArrayList tạm
        int admin_state;
        if(Login_Controller.type_cur_user == 1){
            admin_state = 1;
        }else{
            admin_state = 2;
        }

        String name_cus = name_cusTF.getText();
        String phone_cus = phone_cusTF.getText();
        String address_cus = address_cusTF.getText();

        if(name_cus.isEmpty() || phone_cus.isEmpty() || address_cus.isEmpty()){
            notice_infocus_label.setText("Hãy điền đầy đủ thông tin khách hàng");
            notice_infocus_label.setVisible(true);
        }else {
            CUSTOMER_INFO customer_con = new CUSTOMER_INFO();
            String id_cus = customer_con.generate_IDcus();
            int res_in_customer = customer_con.insert_customer_info(id_cus, name_cus, phone_cus, address_cus);

            MNG_ORDERS mngord_con = new MNG_ORDERS();
            String id_ord = mngord_con.generate_IDmngord();
            int res_in_mngord = mngord_con.insert_mng_orders(id_ord, Login_Controller.id_cur_user, id_cus, "IMPORT", java.time.LocalDate.now().toString(), 2);


            DETAIL_ORD detailord_con = new DETAIL_ORD();
            for (SANPHAM row : cartTable.getItems()) {
               detailord_con.insert_detail_ord(id_ord, row.getId_prod(), row.getNum_exist());
            }

            IMPORT_ORD import_ord_con = new IMPORT_ORD();
            String id_import_ord = import_ord_con.generate_IDimportord();
            int res_int_importord = import_ord_con.insert_import_ord(id_import_ord, id_ord, admin_state, 2, null);

            if (res_in_customer == 0 || res_in_mngord == 0 || res_int_importord == 0) {
                Alert message_send = new Alert(Alert.AlertType.ERROR);
                message_send.setContentText("Yêu cầu nhập không thành công - Lỗi hệ thống");
                message_send.show();
            } else {
                Alert message_send = new Alert(Alert.AlertType.INFORMATION);
                message_send.setContentText("Yêu cầu nhập thành công");
                message_send.show();
            }
            ProductManagement_Controller.li_prod_request.clear();
            data_cart.clear();
            notice_infocus_label.setVisible(false);
            name_cusTF.clear();
            phone_cusTF.clear();
            address_cusTF.clear();
        }
    }
    public void exportRequestBtnAction(ActionEvent e){
        // Xử lý logic
        boolean check_err = false;
        PRODUCTION production_con = new PRODUCTION();
        for(SANPHAM row: cartTable.getItems()){
            int num_sp_exist = production_con.get_num_production_exist(row.getId_prod());
            int num_sp_yc = row.getNum_exist();
            if(num_sp_exist - num_sp_yc < 0){
                Alert message_send = new Alert(Alert.AlertType.ERROR);
                message_send.setContentText("Order san pham " + row.getName_prod() + " vuot qua so luong hien co");
                message_send.show();
                check_err = true;
                break;
            }
        }
        if(!check_err) {
            // nếu số lượng order ổn -> thực hiện insert database
            String name_cus = name_cusTF.getText();
            String phone_cus = phone_cusTF.getText();
            String address_cus = address_cusTF.getText();

            if(name_cus.isEmpty() || phone_cus.isEmpty() || address_cus.isEmpty()){
                notice_infocus_label.setText("Hãy điền đầy đủ thông tin khách hàng");
                notice_infocus_label.setVisible(true);
            }else {
                CUSTOMER_INFO customer_con = new CUSTOMER_INFO();
                String id_cus = customer_con.generate_IDcus();
                int res_in_customer = customer_con.insert_customer_info(id_cus, name_cusTF.getText(), phone_cusTF.getText(), address_cusTF.getText());

                MNG_ORDERS mngord_con = new MNG_ORDERS();
                String id_ord = mngord_con.generate_IDmngord();
                int res_in_mngord = mngord_con.insert_mng_orders(id_ord, Login_Controller.id_cur_user, id_cus, "EXPORT", java.time.LocalDate.now().toString(), 2);


                DETAIL_ORD detail_ord_con = new DETAIL_ORD();
                for (SANPHAM row : cartTable.getItems()) {
                    detail_ord_con.insert_detail_ord(id_ord, row.getId_prod(), row.getNum_exist());
                }

                EXPORT_ORD export_ord_con = new EXPORT_ORD();
                String id_export_ord = export_ord_con.generate_IDexportord();
                int res_in_export_ord = export_ord_con.insert_export_ord(id_export_ord, id_ord, 1, 2, null);

                if (res_in_customer == 0 || res_in_mngord == 0 || res_in_export_ord == 0) {
                    Alert message_send = new Alert(Alert.AlertType.ERROR);
                    message_send.setContentText("Yêu cầu xuất không thành công - Lỗi hệ thống");
                    message_send.show();
                } else {
                    Alert message_send = new Alert(Alert.AlertType.INFORMATION);
                    message_send.setContentText("Yêu cầu xuất thành công");
                    message_send.show();
                }
                ProductManagement_Controller.li_prod_request.clear();
                data_cart.clear();
                notice_infocus_label.setVisible(false);
                name_cusTF.clear();
                phone_cusTF.clear();
                address_cusTF.clear();
            }
        }
    }
    public void backBtnAction(ActionEvent e) throws IOException {
        Parent homeParent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/ProductManagementScreen/ProductManagement_Screen.fxml"));
        Stage homeStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene homeScene = new Scene(homeParent);
        homeStage.setResizable(false);
        homeStage.setScene(homeScene);
        homeStage.show();
    }
    public void delProdBtnAction(ActionEvent e){
        SANPHAM selected = cartTable.getSelectionModel().getSelectedItem();
        ProductManagement_Controller.li_prod_request.remove(selected);
        int idx = cartTable.getSelectionModel().getSelectedIndex();
        data_cart.remove(idx);
        Alert message_send = new Alert(Alert.AlertType.INFORMATION);
        message_send.setContentText("Xóa sản phẩm khỏi giỏ hàng thành công");
        message_send.show();
    }
}


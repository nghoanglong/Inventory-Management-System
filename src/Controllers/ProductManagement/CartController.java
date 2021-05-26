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
        CUSTOMER_INFO customer_con = new CUSTOMER_INFO();
        String id_cus = customer_con.generate_IDcus();
        int res_in_customer = customer_con.insert_customer_info(id_cus, tenkhTF.getText(), sdtkhTF.getText(), diachikhTF.getText());

        MNG_ORDERS mngord_con = new MNG_ORDERS();
        String id_ord = mngord_con.generate_IDmngord();
        int res_in_order = mngord_con.insert_mng_orders(id_ord, LoginController.id_cur_user, id_cus, "IMPORT", java.time.LocalDate.now().toString(), 2);


        MNG_REQUESTS mngreq_con = new MNG_REQUESTS();
        int admin_state;
        if(LoginController.type_cur_user == 1){
            admin_state = 1;
        }else{
            admin_state = 2;
        }
        for(SANPHAM row: chitietycTV.getItems()){
            mngreq_con.insert_mng_requests(id_ord, row.getId_sp(), row.getNum_sp(), admin_state, 2, null);
        }
        if(res_in_customer == 0 || res_in_order == 0){
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
        PRODUCTION production_con = new PRODUCTION();
        for(SANPHAM row: chitietycTV.getItems()){
            int num_sp_exist = production_con.getNumProductionExist(row.getId_sp());
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

            CUSTOMER_INFO customer_con = new CUSTOMER_INFO();
            String id_cus = customer_con.generate_IDcus();
            int res_in_customer = customer_con.insert_customer_info(id_cus, tenkhTF.getText(), sdtkhTF.getText(), diachikhTF.getText());

            MNG_ORDERS mngord_con = new MNG_ORDERS();
            String id_ord = mngord_con.generate_IDmngord();
            int res_in_mngord = mngord_con.insert_mng_orders(id_ord, LoginController.id_cur_user, id_cus, "EXPORT", java.time.LocalDate.now().toString(), 2);


            MNG_REQUESTS mngreq_con = new MNG_REQUESTS();
            for (SANPHAM row : chitietycTV.getItems()) {
                mngreq_con.insert_mng_requests(id_ord, row.getId_sp(), row.getNum_sp(), 1, 2, null);
                production_con.update_production(row.getId_sp(), 2, row.getNum_sp());
            }
            if (res_in_customer == 0 || res_in_mngord == 0 ) {
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
        Parent homeParent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/ProductManagement/request_management.fxml"));
        Stage homeStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene homeScene = new Scene(homeParent);

        homeStage.setScene(homeScene);
        homeStage.show();
    }
    public void xoaBtnAction(ActionEvent e){
        SANPHAM selected = chitietycTV.getSelectionModel().getSelectedItem();
        ProductManagementController.lisp_yc.remove(selected);
        int idx = chitietycTV.getSelectionModel().getSelectedIndex();
        data.remove(idx);
    }
}


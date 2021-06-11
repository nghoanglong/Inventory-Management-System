package Controllers;

import Controllers.Order.ORDER;
import Controllers.ProductManagement.ProductManagement_Controller;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;


public class ConfirmRequest_Controller {
    @FXML
    private Button backhomeBtn;
    @FXML
    private Button acceptBtn;
    @FXML
    private Button denyBtn;

    @FXML
    private Label noticelabel;

    // table bên trái
    @FXML
    private TableView<ORDER> tablesorder;
    @FXML
    private TableColumn<ORDER, String> id_orderCol;
    @FXML
    private TableColumn<ORDER, String> name_cusCol;
    @FXML
    private TableColumn<ORDER, String> fullname_userCol;
    @FXML
    private TableColumn<ORDER, String> type_ordCol;
    @FXML
    private TableColumn<ORDER, String> date_ordCol;

    // table bên phải
    @FXML
    private TableView<SANPHAM> tablereq;
    @FXML
    private TableColumn<SANPHAM, String> id_prodCol;
    @FXML
    private TableColumn<SANPHAM, String> name_prodCol;
    @FXML
    private TableColumn<SANPHAM, String> type_prodCol;
    @FXML
    private TableColumn<SANPHAM, Integer> num_reqCol;

    // class variable
    private ObservableList<ORDER> data_table_order;
    private ObservableList<SANPHAM> data_table_req;

    private String id_ord_selected = "";

    @FXML
    public void initialize() {
        MNG_ORDERS mngord_con = new MNG_ORDERS();
        data_table_order = FXCollections.observableArrayList();
        initOrderTable();
        if(Login_Controller.type_cur_user == 1) {
            data_table_order.addAll(mngord_con.getTableORDER_admin());
        }else{
            data_table_order.addAll(mngord_con.getTableORDER_warehouse());
        }
        tablesorder.setItems(data_table_order);

        data_table_req = FXCollections.observableArrayList();
        initReqTable();
        noticelabel.setVisible(false);
    }
    public void initOrderTable(){
        id_orderCol.setCellValueFactory(new PropertyValueFactory<ORDER, String>("id_ord"));
        name_cusCol.setCellValueFactory(new PropertyValueFactory<ORDER, String>("name_cus"));
        fullname_userCol.setCellValueFactory(new PropertyValueFactory<ORDER, String>("fullname_user"));
        type_ordCol.setCellValueFactory(new PropertyValueFactory<ORDER, String>("type_ord"));
        date_ordCol.setCellValueFactory(new PropertyValueFactory<ORDER, String>("date_ord"));
    }

    public void initReqTable(){
        id_prodCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, String>("id_prod"));
        name_prodCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, String>("name_prod"));
        type_prodCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, String>("type_prod"));
        num_reqCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, Integer>("num_exist"));
    }
    public void tableorderAction(MouseEvent event){
        DETAIL_ORD detail_ord_con = new DETAIL_ORD();
        ORDER selected = tablesorder.getSelectionModel().getSelectedItem();
        this.id_ord_selected = selected.getId_ord();
        data_table_req.clear();
        data_table_req.addAll(detail_ord_con.getTableDETAILORD(this.id_ord_selected));
        tablereq.setItems(data_table_req);
        noticelabel.setVisible(false);
    }

    public void backhomeBtnAction(ActionEvent event){
        ProductManagement_Controller.lisp_yc.clear();
        String home_screen = "";
        if(Login_Controller.type_cur_user == 1){
            home_screen = "Views/HomeScreen/AdminHome/AdminHome_Screen.fxml";
        }else if(Login_Controller.type_cur_user == 2){
            home_screen = "Views/HomeScreen/WarehouseHome/WarehouseHome_Screen.fxml";
        }else{
            home_screen = "Views/HomeScreen/SellerHome/SellerHome_Screen.fxml";
        }
        Parent HomeScreen = null;
        try {
            HomeScreen = FXMLLoader.load(getClass().getClassLoader().getResource(home_screen));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage HomeScreen_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene HomeScreen_Scene = new Scene(HomeScreen);
        HomeScreen_Stage.setScene(HomeScreen_Scene);
        HomeScreen_Stage.show();
    }
    public void denyBtnAction(ActionEvent event){
        MNG_ORDERS mngord_con = new MNG_ORDERS();
        DELETE_ORD delete_ord_con = new DELETE_ORD();
        EXPORT_ORD export_ord_con = new EXPORT_ORD();
        ADD_ORD add_ord_con = new ADD_ORD();
        IMPORT_ORD import_ord_con = new IMPORT_ORD();
        String type_ord = mngord_con.getTypeORDER(this.id_ord_selected);
        int res = 1;
        if(Login_Controller.type_cur_user == 1){
            // Deny của admin chỉ quan tâm 2 trường hợp là thêm mới (ADD) và nhập (IMPORT)
            if(type_ord.equals("ADD")){
                // admin deny thì warehouse deny luôn
                int res_admin = add_ord_con.update_addord_admin_state(this.id_ord_selected, 0);
                int res_wh = add_ord_con.update_addord_warehouse_state(this.id_ord_selected, 0);
                add_ord_con.update_addord_date_2state_return(this.id_ord_selected, java.time.LocalDate.now().toString());
                if(res_admin == 0 || res_wh == 0)
                    res = 0;
            }else{
                int res_admin = import_ord_con.update_importord_admin_state(this.id_ord_selected, 0);
                int res_wh = import_ord_con.update_importord_warehouse_state(this.id_ord_selected, 0);
                import_ord_con.update_importord_date_2state_return(this.id_ord_selected, java.time.LocalDate.now().toString());
                if(res_admin == 0 || res_wh == 0)
                    res = 0;
            }
        }else{
            // deny của warehouse cần quan tâm đến 4 trường hợp
            if(type_ord.equals("DELETE")){
                // trường hợp là đơn delete
                res = delete_ord_con.update_deleteord_warehouse_state(this.id_ord_selected, 0);
                delete_ord_con.update_deleteord_date_2state_return(this.id_ord_selected, java.time.LocalDate.now().toString());
            }else if(type_ord.equals("EXPORT")){
                // trường hợp là đơn export
                res = export_ord_con.update_exportord_warehouse_state(this.id_ord_selected, 0);
                export_ord_con.update_exportord_date_2state_return(this.id_ord_selected, java.time.LocalDate.now().toString());
            }else if(type_ord.equals("ADD")){
                res = add_ord_con.update_addord_warehouse_state(this.id_ord_selected, 0);
                add_ord_con.update_addord_date_2state_return(this.id_ord_selected, java.time.LocalDate.now().toString());
            }else{
                res = import_ord_con.update_importord_warehouse_state(this.id_ord_selected, 0);
                import_ord_con.update_importord_date_2state_return(this.id_ord_selected, java.time.LocalDate.now().toString());
            }
        }
        if(res == 1) {
            int idx = tablesorder.getSelectionModel().getSelectedIndex();
            data_table_order.remove(idx);
            data_table_req.clear();
            noticelabel.setText("Từ chối thành công");
            noticelabel.setVisible(true);
        }else{
            noticelabel.setText("Từ chối thành công");
            noticelabel.setVisible(true);
        }
    }
    public void acceptBtnAction(ActionEvent event){
        // xử lý button accept
        MNG_ORDERS mngord_con = new MNG_ORDERS();
        DELETE_ORD delete_ord_con = new DELETE_ORD();
        EXPORT_ORD export_ord_con = new EXPORT_ORD();
        ADD_ORD add_ord_con = new ADD_ORD();
        IMPORT_ORD import_ord_con = new IMPORT_ORD();
        String type_ord = mngord_con.getTypeORDER(this.id_ord_selected);
        int res = 1;
        if(Login_Controller.type_cur_user == 1){
            // accept của admin chỉ cần quan tâm 2 trường hợp là thêm mới (ADD) và nhập (IMPORT)
            if(type_ord.equals("ADD")) {
                res = add_ord_con.update_addord_admin_state(this.id_ord_selected, 1);
            }else{
                res = import_ord_con.update_importord_admin_state(this.id_ord_selected, 1);
            }

        }else{
            // accept của warehouse cần quan tâm đến 4 trường hợp
            if(type_ord.equals("DELETE")){
                res = delete_ord_con.update_deleteord_warehouse_state(this.id_ord_selected, 1);
                delete_ord_con.update_deleteord_date_2state_return(this.id_ord_selected, java.time.LocalDate.now().toString());
            }else if(type_ord.equals("EXPORT")){
                res = export_ord_con.update_exportord_warehouse_state(this.id_ord_selected, 1);
                export_ord_con.update_exportord_date_2state_return(this.id_ord_selected, java.time.LocalDate.now().toString());
            }else if(type_ord.equals("ADD")){
                res = add_ord_con.update_addord_warehouse_state(this.id_ord_selected, 1);
                add_ord_con.update_addord_date_2state_return(this.id_ord_selected, java.time.LocalDate.now().toString());
            }else{
                res = import_ord_con.update_importord_warehouse_state(this.id_ord_selected, 1);
                import_ord_con.update_importord_date_2state_return(this.id_ord_selected, java.time.LocalDate.now().toString());
            }
        }
        if(res == 1) {
            int idx = tablesorder.getSelectionModel().getSelectedIndex();
            data_table_order.remove(idx);
            data_table_req.clear();
            noticelabel.setText("Chấp thuận thành công");
            noticelabel.setVisible(true);
        }else{
            noticelabel.setText("Chấp thuận không thành công");
            noticelabel.setVisible(true);
        }
    }

}

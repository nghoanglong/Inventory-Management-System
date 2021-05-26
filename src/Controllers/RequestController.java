package Controllers;

import Controllers.LoginController;
import Controllers.OrderManagement.ORDER;
import Controllers.ProductManagement.ProductManagementController;
import Controllers.ProductManagement.SANPHAM;
import Models.MNG_ORDERS;
import Models.MNG_REQUESTS;
import Models.PRODUCTION;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import java.util.HashMap;


public class RequestController {
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
        if(LoginController.type_cur_user == 1) {
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
        MNG_REQUESTS mngreq_con = new MNG_REQUESTS();
        ORDER selected = tablesorder.getSelectionModel().getSelectedItem();
        this.id_ord_selected = selected.getId_ord();
        data_table_req.clear();
        data_table_req.addAll(mngreq_con.getTableREQUEST(this.id_ord_selected));
        tablereq.setItems(data_table_req);
        noticelabel.setVisible(false);
    }

    public void backhomeBtnAction(ActionEvent event){
        ProductManagementController.lisp_yc.clear();
        String home_screen = "";
        if(LoginController.type_cur_user == 1){
            home_screen = "Views/HomeScreen/AdminLauncher/admin_launcher.fxml";
        }else if(LoginController.type_cur_user == 2){
            home_screen = "Views/HomeScreen/WarehouseLauncher/warehouse_launcher.fxml";
        }else{
            home_screen = "Views/HomeScreen/UserLauncher/user_launcher.fxml";
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
        // xử lý button deny
        // Chỉ cần quan tâm khi deny 2 trường hợp xóa và xuất
        // 2 trường hợp kia ko cần update gì cả
        HashMap<String, String> data = new HashMap<String, String>();
        MNG_ORDERS mngord_con = new MNG_ORDERS();
        if(LoginController.type_cur_user == 1){
            data.put("admin_state", "0");
            data.put("warehouse_mng_state", "0");
            data.put("state_ord", "0");
            data.put("date_2state_return", java.time.LocalDate.now().toString());
            mngord_con.update_mng_order(this.id_ord_selected, data);
        }else{
            // xử lý khi warehouse deny
            PRODUCTION prod_con = new PRODUCTION();
            data.put("warehouse_mng_state", "0");
            data.put("state_ord", "0");
            data.put("date_2state_return", java.time.LocalDate.now().toString());
            String type_ord = mngord_con.getTypeORDER(this.id_ord_selected);
            if(type_ord.equals("DELETE")){
                prod_con.un_delete_production(this.id_ord_selected);
            }else{
                // trường hợp là đơn xuất
                prod_con.un_export_production(this.id_ord_selected);
            }
            mngord_con.update_mng_order(this.id_ord_selected, data);
        }
        int idx = tablesorder.getSelectionModel().getSelectedIndex();
        data_table_order.remove(idx);
        data_table_req.clear();
        noticelabel.setText("Từ chối thành công");
        noticelabel.setVisible(true);
    }
    public void acceptBtnAction(ActionEvent event){
        // xử lý button accept
        HashMap<String, String> data = new HashMap<String, String>();
        MNG_ORDERS mngord_con = new MNG_ORDERS();
        if(LoginController.type_cur_user == 1){
            data.put("admin_state", "1");
            mngord_con.update_mng_order(this.id_ord_selected, data);
        }else{
            PRODUCTION prod_con = new PRODUCTION();
            data.put("warehouse_mng_state", "1");
            data.put("state_ord", "1");
            data.put("date_2state_return", java.time.LocalDate.now().toString());
            String type_ord = mngord_con.getTypeORDER(this.id_ord_selected);
            if(type_ord == "ADD"){
                // trường hợp là đơn thêm sản phẩm mới
                prod_con.add_production(this.id_ord_selected);
            }else{
                // trường hợp là đơn nhập thêm
                prod_con.import_production(this.id_ord_selected);
            }
            mngord_con.update_mng_order(this.id_ord_selected, data);
        }
        int idx = tablesorder.getSelectionModel().getSelectedIndex();
        data_table_order.remove(idx);
        data_table_req.clear();
        noticelabel.setText("Chấp thuận thành công");
        noticelabel.setVisible(true);
    }

}

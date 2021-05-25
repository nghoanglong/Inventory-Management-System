package Controllers;

import Controllers.ProductManagement.ProductManagementController;
import Controllers.ProductManagement.SANPHAM;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class OrderController {
    @FXML
    private Button backhomeBtn;


    @FXML
    private TextField searchTF;

    // table bên trái
    @FXML
    private TableView<SANPHAM> tablesorder;
    @FXML
    private TableColumn<SANPHAM, String> id_orderCol;
    @FXML
    private TableColumn<SANPHAM, String> name_cusCol;
    @FXML
    private TableColumn<SANPHAM, String> fullname_userCol;
    @FXML
    private TableColumn<SANPHAM, Integer> type_ordCol;
    @FXML
    private TableColumn<SANPHAM, Integer> date_ordCol;
    @FXML
    private TableColumn<SANPHAM, Integer> state_ordCol;

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
    private ObservableList<SANPHAM> data_table_order;
    private ObservableList<SANPHAM> data_table_req;

    @FXML
    public void initialize() {
        PRODUCTION production_con = new PRODUCTION();
        data_table_order = FXCollections.observableArrayList();
        initOrderTable();
        data_table_order.addAll();

        data_table_req = FXCollections.observableArrayList();
        initReqTable();
        data_table_req.addAll();

        // tạo chức năng search table
//        FilteredList<SANPHAM> filteredData = new FilteredList<>(data_table_order, b -> true);
//        searchTF.textProperty().addListener((observable, oldvalue, newvalue) -> {
//            filteredData.setPredicate(sanpham -> {
//                if(newvalue == null || newvalue.isEmpty()){
//                    return true;
//                }
//                String lowercase_search = newvalue.toLowerCase();
//                if(sanpham.getTen_sp().toLowerCase().contains(lowercase_search)){
//                    return true;
//                }else{
//                    return false;
//                }
//            });
//        });
//        tablesorder.setItems(filteredData);

    }
    public void initOrderTable(){
        id_orderCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, String>("id_order"));
        name_cusCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, String>("name_cus"));
        fullname_userCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, String>("fullname_user"));
        type_ordCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, Integer>("type_ord"));
        date_ordCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, Integer>("date_ord"));
        state_ordCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, Integer>("state_ord"));
    }

    public void initReqTable(){
        id_prodCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, String>("id_prod"));
        name_prodCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, String>("name_prod"));
        type_prodCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, String>("type_prod"));
        num_reqCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, Integer>("num_req"));
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
}

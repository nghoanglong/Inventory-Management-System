package Controllers.Order;

import Controllers.Login_Controller;
import Controllers.ProductManagement.ProductManagement_Controller;
import Controllers.ProductManagement.SANPHAM;
import Models.MNG_ORDERS;
import Models.DETAIL_ORD;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Order_Controller {
    @FXML
    private Button backhomeBtn;


    @FXML
    private TextField searchTF;

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
    @FXML
    private TableColumn<ORDER, Integer> state_ordCol;

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

    @FXML
    public void initialize() {
        MNG_ORDERS mngord_con = new MNG_ORDERS();
        data_table_order = FXCollections.observableArrayList();
        initOrderTable();
        data_table_order.addAll(mngord_con.getTableORDER());
        tablesorder.setItems(data_table_order);

        data_table_req = FXCollections.observableArrayList();
        initReqTable();

        // tạo chức năng search table order
        FilteredList<ORDER> filteredData = new FilteredList<>(data_table_order, b -> true);
        searchTF.textProperty().addListener((observable, oldvalue, newvalue) -> {
            filteredData.setPredicate(order -> {
                if(newvalue == null || newvalue.isEmpty()){
                    return true;
                }
                String lowercase_search = newvalue.toLowerCase();
                if(order.getFullname_user().toLowerCase().contains(lowercase_search)){
                    return true;
                }else{
                    return false;
                }
            });
        });
        tablesorder.setItems(filteredData);

    }
    public void initOrderTable(){
        id_orderCol.setCellValueFactory(new PropertyValueFactory<ORDER, String>("id_ord"));
        name_cusCol.setCellValueFactory(new PropertyValueFactory<ORDER, String>("name_cus"));
        fullname_userCol.setCellValueFactory(new PropertyValueFactory<ORDER, String>("fullname_user"));
        type_ordCol.setCellValueFactory(new PropertyValueFactory<ORDER, String>("type_ord"));
        date_ordCol.setCellValueFactory(new PropertyValueFactory<ORDER, String>("date_ord"));
        state_ordCol.setCellValueFactory(new PropertyValueFactory<ORDER, Integer>("state_ord"));
    }

    public void initReqTable(){
        id_prodCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, String>("id_prod"));
        name_prodCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, String>("name_prod"));
        type_prodCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, String>("type_prod"));
        num_reqCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, Integer>("num_exist"));
    }

    public void tableorderAction(MouseEvent event){
        DETAIL_ORD detailord_con = new DETAIL_ORD();
        ORDER selected = tablesorder.getSelectionModel().getSelectedItem();
        String id_selected = selected.getId_ord();
        data_table_req.clear();
        data_table_req.addAll(detailord_con.getTableDETAILORD(id_selected));
        tablereq.setItems(data_table_req);
    }
    public void backhomeBtnAction(ActionEvent event){
        ProductManagement_Controller.li_prod_request.clear();
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
}

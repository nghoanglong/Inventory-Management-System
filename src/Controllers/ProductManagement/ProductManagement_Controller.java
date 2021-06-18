package Controllers.ProductManagement;

import Controllers.Login_Controller;
import Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ProductManagement_Controller {
    @FXML
    private Label id_prod_label;
    @FXML
    private Label name_prod_label;
    @FXML
    private Label type_prod_label;
    @FXML
    private Label price_label;
    @FXML
    private Label num_exist_label;
    @FXML
    private Label addToCart_messageLabel;
    @FXML
    private Label noticeDelLabel;

    @FXML
    private Button add_to_cartBtn;
    @FXML
    private Button addNewProductBtn;
    @FXML
    private Button delete_prodBtn;
    @FXML
    private Button reportBtn;

    @FXML
    private TextField num_prod_to_cartTF;
    @FXML
    private TextField searchTF;

    @FXML
    private ImageView cartBtn;


    @FXML
    private TableView<SANPHAM> productionTable;
    @FXML
    private TableColumn<SANPHAM, String> id_prodCol;
    @FXML
    private TableColumn<SANPHAM, String> name_prodCol;
    @FXML
    private TableColumn<SANPHAM, String> type_prodCol;
    @FXML
    private TableColumn<SANPHAM, Integer> priceCol;
    @FXML
    private TableColumn<SANPHAM, Integer> numspCol;

    // class variable
    private ObservableList<SANPHAM> data_production_table;
    public static ArrayList<SANPHAM> li_prod_request = new ArrayList<SANPHAM>();

    private String id_prodSelected = "";
    private String name_prodSelected = "";
    private String type_prodSelected = "";
    private int priceSelected = -1;
    private int num_existSelected = -1;

    @FXML
    public void initialize(){
        if(Login_Controller.type_cur_user == 3){
            // set button role user
            delete_prodBtn.setVisible(false);
            reportBtn.setVisible(false);
        }
        else if(Login_Controller.type_cur_user == 2){
            // set button role qlkho
            // sau này role qlkho sẽ có nút report sau
            delete_prodBtn.setVisible(false);
            addNewProductBtn.setVisible(false);
            cartBtn.setVisible(false);
            num_prod_to_cartTF.setVisible(false);
            add_to_cartBtn.setVisible(false);
            reportBtn.setVisible(true);
        }else{
            reportBtn.setVisible(false);
        }

        // configure data
        PRODUCTION production_con = new PRODUCTION();
        data_production_table = FXCollections.observableArrayList();
        initTable();
        data_production_table.addAll(production_con.getTablePRODUCTION());
        setLabel();

        // tạo chức năng search table
        FilteredList<SANPHAM> filteredData = new FilteredList<>(data_production_table, b -> true);
        searchTF.textProperty().addListener((observable, oldvalue, newvalue) -> {
            filteredData.setPredicate(sanpham -> {
                if(newvalue == null || newvalue.isEmpty()){
                    return true;
                }
                String lowercase_search = newvalue.toLowerCase();
                if(sanpham.getName_prod().toLowerCase().contains(lowercase_search)){
                    return true;
                }else{
                    return false;
                }
            });
        });
        productionTable.setItems(filteredData);

    }

    public void initTable(){
        id_prodCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, String>("id_prod"));
        name_prodCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, String>("name_prod"));
        type_prodCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, String>("type_prod"));
        priceCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, Integer>("price"));
        numspCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, Integer>("num_exist"));
    }

    public void setLabel(){
        id_prod_label.setVisible(false);
        name_prod_label.setVisible(false);
        type_prod_label.setVisible(false);
        price_label.setVisible(false);
        num_exist_label.setVisible(false);
        addToCart_messageLabel.setVisible(false);
        noticeDelLabel.setVisible(false);
    }

    public void tablesanphamAction(MouseEvent event){
        SANPHAM selected =  productionTable.getSelectionModel().getSelectedItem();
        this.id_prodSelected = selected.getId_prod();
        this.name_prodSelected = selected.getName_prod();
        this.type_prodSelected = selected.getType_prod();
        this.priceSelected = selected.getPrice();
        this.num_existSelected = selected.getNum_exist();

        id_prod_label.setText(this.id_prodSelected);
        id_prod_label.setVisible(true);
        name_prod_label.setText(this.name_prodSelected);
        name_prod_label.setVisible(true);
        type_prod_label.setText(this.type_prodSelected);
        type_prod_label.setVisible(true);
        price_label.setText(Integer.toString(this.priceSelected));
        price_label.setVisible(true);
        num_exist_label.setText(Integer.toString(this.num_existSelected));
        num_exist_label.setVisible(true);
    }
    public void addToCartBtnAction(ActionEvent event){
        String num_prod_to_cart = num_prod_to_cartTF.getText();
        if(this.id_prodSelected.isEmpty() ||
           this.name_prodSelected.isEmpty() ||
           this.type_prodSelected.isEmpty() ||
           this.priceSelected == -1 ||
           this.num_existSelected == -1){
            addToCart_messageLabel.setText("Vui lòng chọn sản phẩm");
            addToCart_messageLabel.setVisible(true);
            num_prod_to_cartTF.clear();
        }else if(num_prod_to_cart.isEmpty()){
            addToCart_messageLabel.setText("Vui lòng chọn số lượng");
            addToCart_messageLabel.setVisible(true);
        } else{
            ProductManagement_Controller.li_prod_request.add(new SANPHAM(this.id_prodSelected, this.name_prodSelected, this.type_prodSelected, this.priceSelected, Integer.parseInt(num_prod_to_cart)));
            addToCart_messageLabel.setText("Thêm vào giỏ hàng thành công");
            addToCart_messageLabel.setVisible(true);
            num_prod_to_cartTF.clear();
        }
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
        HomeScreen_Stage.setResizable(false);
        HomeScreen_Stage.show();
    }

    public void cartBtnAction(MouseEvent event) throws IOException {
        Parent CartScreen = FXMLLoader.load(getClass().getClassLoader().getResource("Views/CartScreen/Cart_Screen.fxml"));
        Stage CartScreen_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene CartScreen_Scene = new Scene(CartScreen);
        CartScreen_Stage.setScene(CartScreen_Scene);
        CartScreen_Stage.show();
    }
    public void addNewProductBtnAction(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Views/ProductManagementScreen/AddProductDialog/addProductDialog.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Them San Pham");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) event.getSource()).getScene().getWindow() );
        stage.show();
    }
    public void deleteBtnAction(ActionEvent event){
        MNG_ORDERS mngord_con = new MNG_ORDERS();
        String id_ord = mngord_con.generate_IDmngord();
        int res_in_mngord = mngord_con.insert_mng_orders(id_ord, Login_Controller.id_cur_user, null, "DELETE", java.time.LocalDate.now().toString(), 2);

        DELETE_ORD delete_ord_con = new DELETE_ORD();
        String id_del_ord = delete_ord_con.generate_IDdeleteord();
        int res_in_deleteord = delete_ord_con.insert_delete_ord(id_del_ord, id_ord, 1, 2, null);

        DETAIL_ORD detail_ord_con = new DETAIL_ORD();
        SANPHAM selected =  productionTable.getSelectionModel().getSelectedItem();
        int res_in_detailord = detail_ord_con.insert_detail_ord(id_ord, selected.getId_prod(), selected.getNum_exist());

        // trigger xử lý delete production

        if(res_in_mngord == 0 || res_in_detailord == 0 || res_in_deleteord == 0){
            noticeDelLabel.setText("Yêu cầu xóa sản phẩm không thành công");
            noticeDelLabel.setVisible(true);
        }else{
            int idx =  productionTable.getSelectionModel().getSelectedIndex();
            data_production_table.remove(idx);
            id_prod_label.setText(null);
            name_prod_label.setText(null);
            type_prod_label.setText(null);
            num_exist_label.setText(null);
            price_label.setText(null);
            noticeDelLabel.setText("Yêu cầu xóa sản phẩm thành công");
            noticeDelLabel.setVisible(true);

        }
    }
    public void reportBtnAction(ActionEvent event){
        // xử lý button report của warehouse
    }

}

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
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ProductManagement_Controller {
    @FXML
    private Label IDlabel;
    @FXML
    private Label namesp_label;
    @FXML
    private Label loaisp_label;
    @FXML
    private Label giasp_label;
    @FXML
    private Label numsp_label;
    @FXML
    private Label message_label;
    @FXML
    private Label noticeDelLabel;
    @FXML
    private TextField addnumspTF;
    @FXML
    private TextField searchTF;

    @FXML
    private Button addBtn;
    @FXML
    private Button backhomeBtn;
    @FXML
    private ImageView cartBtn;
    @FXML
    private Button searchBtn;
    @FXML
    private Button addNewProductBtn;
    @FXML
    private Button xoaBtn;
    @FXML
    private Button reportBtn;

    @FXML
    private TableView<SANPHAM> tablesanpham;
    @FXML
    private TableColumn<SANPHAM, String> idspCol;
    @FXML
    private TableColumn<SANPHAM, String> tenspCol;
    @FXML
    private TableColumn<SANPHAM, String> loaispCol;
    @FXML
    private TableColumn<SANPHAM, Integer> giaspCol;
    @FXML
    private TableColumn<SANPHAM, Integer> numspCol;

    // class variable
    private ObservableList<SANPHAM> data_qlsp;
    public static ArrayList<SANPHAM> lisp_yc = new ArrayList<SANPHAM>();

    private String ID_selected = "";
    private String namesp_selected = "";
    private String loaisp_selected = "";
    private int giasp_selected = -1;
    private int numsp_selected = -1;

    @FXML
    public void initialize(){
        if(Login_Controller.type_cur_user == 3){
            // set button role user
            xoaBtn.setVisible(false);
            reportBtn.setVisible(false);
        }
        else if(Login_Controller.type_cur_user == 2){
            // set button role qlkho
            // sau này role qlkho sẽ có nút report sau
            xoaBtn.setVisible(false);
            addNewProductBtn.setVisible(false);
            cartBtn.setVisible(false);
            addnumspTF.setVisible(false);
            addBtn.setVisible(false);
            reportBtn.setVisible(true);
        }else{
            reportBtn.setVisible(false);
        }

        // configure data
        PRODUCTION production_con = new PRODUCTION();
        data_qlsp = FXCollections.observableArrayList();
        initTable();
        data_qlsp.addAll(production_con.getTablePRODUCTION());
        setLabel();

        // tạo chức năng search table
        FilteredList<SANPHAM> filteredData = new FilteredList<>(data_qlsp, b -> true);
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
        tablesanpham.setItems(filteredData);

    }

    public void initTable(){
        idspCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, String>("id_prod"));
        tenspCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, String>("name_prod"));
        loaispCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, String>("type_prod"));
        giaspCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, Integer>("price"));
        numspCol.setCellValueFactory(new PropertyValueFactory<SANPHAM, Integer>("num_exist"));
    }

    public void setLabel(){
        IDlabel.setVisible(false);
        namesp_label.setVisible(false);
        loaisp_label.setVisible(false);
        giasp_label.setVisible(false);
        numsp_label.setVisible(false);
        message_label.setVisible(false);
        noticeDelLabel.setVisible(false);
    }

    public void tablesanphamAction(MouseEvent event){
        SANPHAM selected = tablesanpham.getSelectionModel().getSelectedItem();
        this.ID_selected = selected.getId_prod();
        this.namesp_selected = selected.getName_prod();
        this.loaisp_selected = selected.getType_prod();
        this.giasp_selected = selected.getPrice();
        this.numsp_selected = selected.getNum_exist();

        IDlabel.setText(this.ID_selected);
        IDlabel.setVisible(true);
        namesp_label.setText(this.namesp_selected);
        namesp_label.setVisible(true);
        loaisp_label.setText(this.loaisp_selected);
        loaisp_label.setVisible(true);
        giasp_label.setText(Integer.toString(this.giasp_selected));
        giasp_label.setVisible(true);
        numsp_label.setText(Integer.toString(this.numsp_selected));
        numsp_label.setVisible(true);
    }
    public void addBtnAction(ActionEvent event){
        String num_addTF = addnumspTF.getText();
        if(this.ID_selected.isEmpty() ||
           this.namesp_selected.isEmpty() ||
           this.loaisp_selected.isEmpty() ||
           this.giasp_selected == -1 ||
           this.numsp_selected == -1){
            message_label.setText("Vui lòng chọn sản phẩm");
            message_label.setVisible(true);
            addnumspTF.clear();
        }else if(num_addTF.isEmpty()){
            message_label.setText("Vui lòng chọn số lượng");
            message_label.setVisible(true);
        } else{
            ProductManagement_Controller.lisp_yc.add(new SANPHAM(this.ID_selected, this.namesp_selected, this.loaisp_selected, this.giasp_selected, Integer.parseInt(num_addTF)));
            message_label.setText("Thêm vào giỏ hàng thành công");
            message_label.setVisible(true);
            addnumspTF.clear();
        }
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

    public void cartBtnAction(MouseEvent event) throws IOException {
        Parent CartScreen = FXMLLoader.load(getClass().getClassLoader().getResource("Views/OrderINEProductScreen/OrderINEProduct_Screen.fxml"));
        Stage CartScreen_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene CartScreen_Scene = new Scene(CartScreen);
        CartScreen_Stage.setScene(CartScreen_Scene);
        CartScreen_Stage.show();
    }
    public void addNewProductBtnAction(ActionEvent event) throws IOException{
        Parent NewProductScreen = FXMLLoader.load(getClass().getClassLoader().getResource("Views/OrderAddNewProductScreen/OrderAddNewProduct_Screen.fxml"));
        Stage NewProductScreen_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene CartScreen_Scene = new Scene(NewProductScreen);
        NewProductScreen_Stage.setScene(CartScreen_Scene);
        NewProductScreen_Stage.show();
    }
    public void xoaBtnAction(ActionEvent event){
        MNG_ORDERS mngord_con = new MNG_ORDERS();
        String id_ord = mngord_con.generate_IDmngord();
        int res_in_mngord = mngord_con.insert_mng_orders(id_ord, Login_Controller.id_cur_user, null, "DELETE", java.time.LocalDate.now().toString(), 2);


        DETAIL_ORD detail_ord_con = new DETAIL_ORD();
        SANPHAM selected = tablesanpham.getSelectionModel().getSelectedItem();
        int res_in_detailord = detail_ord_con.insert_detail_ord(id_ord, selected.getId_prod(), selected.getNum_exist());

        DELETE_ORD delete_ord_con = new DELETE_ORD();
        String id_del_ord = delete_ord_con.generate_IDdeleteord();
        int res_in_deleteord = delete_ord_con.insert_delete_ord(id_del_ord, id_ord, 1, 2, null);

        // trigger xử lý delete production

        if(res_in_mngord == 0 || res_in_detailord == 0 || res_in_deleteord == 0){
            noticeDelLabel.setText("Yêu cầu xóa sản phẩm không thành công");
            noticeDelLabel.setVisible(true);
        }else{
            int idx = tablesanpham.getSelectionModel().getSelectedIndex();
            data_qlsp.remove(idx);
            noticeDelLabel.setText("Yêu cầu xóa sản phẩm thành công");
            noticeDelLabel.setVisible(true);
        }
    }
    public void reportBtnAction(ActionEvent event){
        // xử lý button report của warehouse
    }

}
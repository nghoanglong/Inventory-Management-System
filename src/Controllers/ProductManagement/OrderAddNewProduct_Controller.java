package Controllers.ProductManagement;

import Controllers.Login_Controller;
import Models.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class OrderAddNewProduct_Controller {
    @FXML
    private TextField tensp_tf;
    @FXML
    private TextField loaisp_tf;
    @FXML
    private TextField giasp_tf;
    @FXML
    private TextField numsp_tf;
    @FXML
    private TextField tenkh_tf;
    @FXML
    private TextField sdt_tf;
    @FXML
    private TextField diachi_tf;
    @FXML
    private ImageView backHomebtn;
    @FXML
    private Button them_btn;
    @FXML
    private Label noticeLb;

    public void initialize(){
        noticeLb.setVisible(false);
    }

    public void themBtnAction(ActionEvent event){
        String tensp = tensp_tf.getText();
        String loaisp = loaisp_tf.getText();
        String giasp = giasp_tf.getText(); // -> xử lý khi insert vào db thì parse int
        String numsp = numsp_tf.getText(); // -> xử lý khi insert vào db thì parse int
        String tenkh = tenkh_tf.getText();
        String phonekh = sdt_tf.getText();
        String diachikh = diachi_tf.getText();

        if(tensp.isEmpty() ||
           loaisp.isEmpty() ||
           giasp.isEmpty() ||
           numsp.isEmpty() ||
           tenkh.isEmpty() ||
           phonekh.isEmpty() ||
           diachikh.isEmpty()){
                // xử lý notice label ở đây
                noticeLb.setText("Hãy điền đầy đủ các thông tin ở trên");
                noticeLb.setVisible(true);
        }else{
            int admin_state;
            if(Login_Controller.type_cur_user == 1){
                admin_state = 1;

            }else{
                admin_state = 2;

            }
            CUSTOMER_INFO customer_con = new CUSTOMER_INFO();
            String id_cus = customer_con.generate_IDcus();
            int res_in_customer = customer_con.insert_customer_info(id_cus, tenkh, phonekh, diachikh);

            PRODUCTION production_con = new PRODUCTION();
            String id_prod = production_con.generate_IDproduction();
            int res_in_production = production_con.insert_production(id_prod, tensp, loaisp, Integer.parseInt(giasp), Integer.parseInt(numsp), 0);

            MNG_ORDERS mngord_con = new MNG_ORDERS();
            String id_ord = mngord_con.generate_IDmngord();
            int res_in_mngord = mngord_con.insert_mng_orders(id_ord, Login_Controller.id_cur_user, id_cus, "ADD", java.time.LocalDate.now().toString(), 2);

            ADD_ORD add_ord_con = new ADD_ORD();
            String id_add_ord = add_ord_con.generate_IDaddord();
            int res_in_addord = add_ord_con.insert_add_ord(id_add_ord, id_ord, admin_state, 2, null);

            DETAIL_ORD detail_ord_con = new DETAIL_ORD();
            int res_in_detailord = detail_ord_con.insert_detail_ord(id_ord, id_prod, Integer.parseInt(numsp));

            if(res_in_production == 0){
                noticeLb.setText("Không thể yêu cầu vì sản phẩm đã tồn tại trong hệ thống");
                noticeLb.setVisible(true);
            }
            else if(res_in_detailord == 0 || res_in_mngord == 0 || res_in_addord == 0 || res_in_customer == 0){
                noticeLb.setText("Yêu cầu thêm sản phẩm mới không thành công");
                noticeLb.setVisible(true);
            }
            else{
                noticeLb.setText("Yêu cầu thành công");
                noticeLb.setVisible(true);
            }
        }

    }
    public void backHomebtnAction(MouseEvent event){
        Parent HomeScreen = null;
        try {
            HomeScreen = FXMLLoader.load(getClass().getClassLoader().getResource("Views/ProductManagementScreen/ProductManagement_Screen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage HomeScreen_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene HomeScreen_Scene = new Scene(HomeScreen);
        HomeScreen_Stage.setScene(HomeScreen_Scene);
        HomeScreen_Stage.setResizable(false);
        HomeScreen_Stage.show();
    }
}

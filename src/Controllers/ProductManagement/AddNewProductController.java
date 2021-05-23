package Controllers.ProductManagement;

import Controllers.LoginController;
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

import java.io.IOException;

public class AddNewProductController {
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
    private Label noticelabel;

    public void initialize(){
        noticelabel.setVisible(false);
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
        }else{

            CUSTOMER_INFO new_kh = new CUSTOMER_INFO();
            String id_kh = new_kh.generate_IDkh();
            int result_in_ttkh = new_kh.insert_TTKH(id_kh, tenkh, phonekh, diachikh);

            PRODUCTION new_sp = new PRODUCTION();
            String id_sp = new_sp.generate_IDsp();
            int result_in_qlsp = new_sp.insert_qlsp(id_sp, tensp, loaisp, Integer.parseInt(giasp), Integer.parseInt(numsp), 0);

            ORDERS new_ORDERS = new ORDERS();
            String id_dh = new_ORDERS.generate_IDdh();
            int result_in_qldh = new_ORDERS.insert_qldh(id_dh, LoginController.id_cur_user, id_kh);

            QLYC new_qlyc = new QLYC();
            String id_qlyc = new_qlyc.generate_IDyc();
            int result_in_qlyc = new_qlyc.insert_qlyc(id_qlyc, id_dh, "Add", java.time.LocalDate.now().toString());

            ORD_DETAIL new_ORDDETAIL = new ORD_DETAIL();
            new_ORDDETAIL.insert_ctyc(id_qlyc, id_sp, Integer.parseInt(numsp));

            /* Insert bảng trạng thái yêu cầu
               admin_state: 0 -> deny
                            1 -> accept
                            2 -> pending
               qlkho_state: 0 -> deny
                            1 -> accept
                            2 -> pending

             */
            ORD_STATE new_ORDSTATE = new ORD_STATE();
            String id_trangthai_yc = new_ORDSTATE.generate_IDttyc();
            int result_in_trangthai_yc = new_ORDSTATE.insert_ttyc(id_trangthai_yc, id_qlyc, 2, 2, null);

            if(result_in_qlsp == 0){
                noticelabel.setText("Không thể yêu cầu vì sản phẩm đã tồn tại trong hệ thống");
                noticelabel.setVisible(true);
            }
            else if(result_in_ttkh == 0 || result_in_qlsp == 0 || result_in_qldh == 0 || result_in_qlyc == 0 || result_in_trangthai_yc == 0){
                noticelabel.setText("Yêu cầu thêm sản phẩm mới không thành công");
                noticelabel.setVisible(true);
            }
            else{
                noticelabel.setText("Yêu cầu thành công");
                noticelabel.setVisible(true);
            }
        }

    }
    public void backHomebtnAction(MouseEvent event){
        Parent HomeScreen = null;
        try {
            HomeScreen = FXMLLoader.load(getClass().getClassLoader().getResource("Views/ProductManagement/product_management.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage HomeScreen_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene HomeScreen_Scene = new Scene(HomeScreen);
        HomeScreen_Stage.setScene(HomeScreen_Scene);
        HomeScreen_Stage.show();
    }
}

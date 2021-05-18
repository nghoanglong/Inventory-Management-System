package Controllers.ProductManagement;

import Controllers.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    // Bien luu tru data nhap vao
    String tensp;
    String loaisp;
    int giasp;
    int numsp;
    String tenkh;
    String sdt;
    String diachi;

    public void initialize(){
    }

    public void get_input_data(){
        this.tensp = this.tensp_tf.getText();
        this.loaisp = this.loaisp_tf.getText();
        this.giasp = Integer.parseInt(this.giasp_tf.getText());
        this.numsp = Integer.parseInt(this.numsp_tf.getText());
        this.tenkh = this.tenkh_tf.getText();
        this.sdt = this.sdt_tf.getText();
        this.diachi = this.diachi_tf.getText();
    }

    public void themBtnAction(ActionEvent event){
        this.get_input_data();
        // xử lý yêu cầu thêm sản phẩm mới
        // hiển thị thông báo với noticelabel
    }
    public void backHomebtnAction(MouseEvent event){
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

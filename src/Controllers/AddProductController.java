package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;

public class AddProductController {
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

    public void themBtnAction(ActionEvent e){
        this.get_input_data();
    }
}

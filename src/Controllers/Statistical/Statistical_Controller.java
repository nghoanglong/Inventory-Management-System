package Controllers.Statistical;

import Models.EXPORT_ORD;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import java.io.IOException;

public class Statistical_Controller implements Initializable {
    @FXML
    private ComboBox monthCb;
    @FXML
    private ComboBox yearCb;
    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;
    @FXML
    private LineChart<String,Number> chartView;

    private String month_selected = null;
    private String year_selected = null;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        // initComboBox
        ObservableList<String> month = FXCollections.observableArrayList("1","2","3","4","5","6","7","8","9","10","11","12");
        ObservableList<String> year = FXCollections.observableArrayList("2020","2021");
        monthCb.setItems(month);
        yearCb.setItems(year);

    }

    public void monthCbAction(ActionEvent e){
        monthCb.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
            }
        });
        month_selected = monthCb.getValue().toString();
    }

    public void yearCbAction(ActionEvent e){
        monthCb.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
            }
        });
        year_selected = yearCb.getValue().toString();
    }

    public void backBtnAction(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/HomeScreen/AdminHome/AdminHome_Screen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public void pdfBtnAction(ActionEvent event){

    }

    public void statisticBtnAction(ActionEvent event){
        if(month_selected == null || year_selected == null){
            Alert check_empty = new Alert(Alert.AlertType.ERROR);
            check_empty.setContentText("Vui lòng chọn đầy đủ mốc thời gian!");
            check_empty.show();
        }
        else{
            EXPORT_ORD export_ord_con = new EXPORT_ORD();
            ArrayList<REVENUE_DAY> li_revenue = export_ord_con.getREVENUE_DAY(month_selected,year_selected);
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Doanh thu từng ngày");
            for(int i = 0; i < li_revenue.size(); i++){
                String date = export_ord_con.date_to_string(li_revenue.get(i).getDate_ord());
                int revenue = li_revenue.get(i).getSum_ord();
                series.getData().add(new XYChart.Data<String, Number>(date,revenue));
            }
            chartView.getData().add(series);
        }
    }
}

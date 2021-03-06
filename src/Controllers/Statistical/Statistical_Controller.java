package Controllers.Statistical;

import Models.EXPORT_ORD;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import com.itextpdf.text.*;
import org.jfree.data.*;
import org.jfree.data.xy.DefaultXYDataset;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;

import java.io.IOException;
import java.util.stream.Collectors;

public class Statistical_Controller implements Initializable {
    @FXML
    private Button pdfBtn;
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
    private ArrayList<REVENUE_DAY> li_revenue_day;
    private ArrayList<REVENUE_MONTH> li_revenue_month;
    public static int sum_money;
    private DefaultXYDataset dataset = new DefaultXYDataset();
    ObservableList<String> month = FXCollections.observableArrayList("<no optional>", "1","2","3","4","5","6","7","8","9","10","11","12");
    ObservableList<String> year = FXCollections.observableArrayList("<no optional>", "2021");

    @Override
    public void initialize(URL location, ResourceBundle resources){
        // initComboBox
        monthCb.setItems(month);
        yearCb.setItems(year);
        if(li_revenue_day == null || li_revenue_month == null){
            pdfBtn.setDisable(true);
        }

    }

    public void monthCbAction(ActionEvent e){
        monthCb.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
            }
        });
        if(monthCb.getValue() != null) {
            if (monthCb.getValue().toString().equals("<no optional>")) {
                month_selected = null;
            } else {
                month_selected = monthCb.getValue().toString();
            }
        }
    }

    public void yearCbAction(ActionEvent e){
        monthCb.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
            }
        });
        if(yearCb.getValue() != null) {
            if (yearCb.getValue().toString().equals("<no optional>")) {
                year_selected = null;
            } else {
                year_selected = yearCb.getValue().toString();
            }
        }
    }

    public void backBtnAction(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("Views/HomeScreen/AdminHome/AdminHome_Screen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    // S??n: /Users/sonchu/Desktop/Inventory-Management-System/src/Controllers/Statistical/
    // D:/Projects/Inventory-Management-System/src/Controllers/Statistical/

    public void pdfBtnAction(ActionEvent event){
        Document document = new Document(PageSize.A4);
        String url = "/Users/sonchu/Desktop/Inventory-Management-System/src/Controllers/Statistical/";
        String file_name = null;
        if(year_selected != null && month_selected == null){
            try{
                file_name = "BaoCaoDoanhThuNam";
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(url + "reports/" + file_name + year_selected + ".pdf"));
                document.open();

                File file_font_tieude = new File(url+"fonts/vuArialBold.ttf");
                BaseFont bf_tieude = BaseFont.createFont(file_font_tieude.getAbsolutePath(),BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
                Font font_tieude_1 = new Font(bf_tieude,16);
                font_tieude_1.setColor(BaseColor.BLUE);
                Font font_tieude_2 = new Font(bf_tieude,13);
                font_tieude_2.setColor(BaseColor.BLUE);
                Font font_tieude_3 = new Font(bf_tieude,13);
                Font font_tieude_4 = new Font(bf_tieude,12);

                File file_font_noidung = new File(url+"fonts/vuArial.ttf");
                BaseFont bf_noidung = BaseFont.createFont(file_font_noidung.getAbsolutePath(),BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
                Font font_noidung_1 = new Font(bf_noidung,13);
                Font font_noidung_2 = new Font(bf_noidung,12);
                Font font_noidung_3 = new Font(bf_noidung,11);

                Image logo = Image.getInstance(url+"images/laptop.png");
                logo.setAbsolutePosition(80, 750);
                logo.scaleAbsolute(50,50);
                document.add(logo);

                Paragraph prg_tench = new Paragraph("C???A H??NG M??Y T??NH IMS", font_tieude_2);
                prg_tench.setIndentationLeft(100);
                document.add(prg_tench);

                Paragraph prg_diachipk = new Paragraph("Khu ???? th??? ??HQG TPHCM, ph?????ng Linh Trung, Tp.Th??? ?????c, Tp.H??? Ch?? Minh", font_noidung_2);
                prg_diachipk.setIndentationLeft(100);
                document.add(prg_diachipk);

                Paragraph prg_sdtpk = new Paragraph("S??? ??i???n tho???i: 023 4567 8990", font_noidung_2);
                prg_sdtpk.setIndentationLeft(100);
                document.add(prg_sdtpk);

                Paragraph prg_tieude = new Paragraph("B??O C??O DOANH THU N??M " + year_selected,font_tieude_1);
                prg_tieude.setAlignment(Element.ALIGN_CENTER);
                prg_tieude.setSpacingBefore(10);
                prg_tieude.setSpacingAfter(10);
                document.add(prg_tieude);

                try{
                    File file = new File("reports/" + file_name + year_selected + ".pdf");
                    if(!Desktop.isDesktopSupported()){
                        System.out.println("not supported");
                        return;
                    }
                    Desktop desktop = Desktop.getDesktop();
                    if(file.exists()){
                        desktop.open(file);
                    }
                }catch(Exception e){
                    System.out.println("L???i");
                }

                Paragraph prg1 = new Paragraph("I. Doanh thu c???a h??ng theo th??ng: ",font_tieude_2);
                prg1.setAlignment(Element.ALIGN_LEFT);
                prg1.setSpacingBefore(10);
                prg1.setSpacingAfter(10);
                document.add(prg1);

                PdfPTable tableDoanhThu = new PdfPTable(4);
                tableDoanhThu.setWidthPercentage(80);
                tableDoanhThu.setSpacingBefore(10);
                tableDoanhThu.setSpacingAfter(10);

                float[] tableDV_columnWidths = { 50, 120, 120, 80 };
                tableDoanhThu.setWidths(tableDV_columnWidths);

                PdfPCell cellSTT = new PdfPCell(new Paragraph("STT",font_tieude_4));
                cellSTT.setBorderColor(BaseColor.BLACK);
                cellSTT.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellSTT.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellSTT.setMinimumHeight(30);
                tableDoanhThu.addCell(cellSTT);

                PdfPCell cellNgay = new PdfPCell(new Paragraph("Th??ng",font_tieude_4));
                cellNgay.setBorderColor(BaseColor.BLACK);
                cellNgay.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellNgay.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableDoanhThu.addCell(cellNgay);

                PdfPCell cellDoanhThu = new PdfPCell(new Paragraph("Doanh thu",font_tieude_4));
                cellDoanhThu.setBorderColor(BaseColor.BLACK);
                cellDoanhThu.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellDoanhThu.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableDoanhThu.addCell(cellDoanhThu);

                PdfPCell cellGhiChu = new PdfPCell(new Paragraph("Ghi ch??",font_tieude_4));
                cellGhiChu.setBorderColor(BaseColor.BLACK);
                cellGhiChu.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellGhiChu.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableDoanhThu.addCell(cellGhiChu);

                for(int i = 0; i < li_revenue_month.size(); i++){
                    PdfPCell cellTT = new PdfPCell(new Paragraph(String.valueOf(i+1), font_noidung_2));
                    cellTT.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellTT.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cellTT.setMinimumHeight(20);
                    tableDoanhThu.addCell(cellTT);

                    PdfPCell cellDate = new PdfPCell(new Paragraph(li_revenue_month.get(i).getMonth(), font_noidung_2));
                    cellDate.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellDate.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tableDoanhThu.addCell(cellDate);

                    PdfPCell cellRevenue = new PdfPCell(new Paragraph(String.valueOf(li_revenue_month.get(i).getSum()), font_noidung_2));
                    cellRevenue.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellRevenue.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tableDoanhThu.addCell(cellRevenue);

                    PdfPCell cellNote = new PdfPCell(new Paragraph("", font_noidung_2));
                    cellNote.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellNote.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tableDoanhThu.addCell(cellNote);
                }

                PdfPCell cellTongCong = new PdfPCell(new Paragraph("T???NG C???NG:", font_tieude_4));
                cellTongCong.setColspan(3);
                cellTongCong.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellTongCong.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellTongCong.setMinimumHeight(20);
                tableDoanhThu.addCell(cellTongCong);

                PdfPCell cellTongTien = new PdfPCell(new Paragraph(String.valueOf(sum_money), font_tieude_4));
                cellTongTien.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellTongTien.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellTongTien.setMinimumHeight(20);
                tableDoanhThu.addCell(cellTongTien);

                PdfPCell cellNote = new PdfPCell(new Paragraph("", font_tieude_4));
                cellNote.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cellNote.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableDoanhThu.addCell(cellGhiChu);

                document.add(tableDoanhThu);

                Paragraph prg2 = new Paragraph("II. B???ng th???ng k?? doanh thu: ",font_tieude_2);
                prg2.setAlignment(Element.ALIGN_LEFT);
                prg2.setSpacingBefore(10);
                prg2.setSpacingAfter(10);
                document.add(prg2);

                WritableImage image = chartView.snapshot(new SnapshotParameters(), null);
                BufferedImage awtImage = SwingFXUtils.fromFXImage(image, null);
                Image img = Image.getInstance(writer, awtImage, 1.0f);
                img.setAlignment(Element.ALIGN_CENTER);
                img.scaleAbsolute(450, 400);

                document.add(img);

                document.close();
                writer.close();
                System.out.println("Xu???t pdf th??nh c??ng");
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("Loi xuat pdf");
            }
        }
        else if(year_selected != null && month_selected != null){
            try{
                file_name = "BaoCaoDoanhThuThang";
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(url+"reports/"+file_name+month_selected+"-"+year_selected+".pdf"));
                document.open();

                File file_font_tieude = new File(url+"fonts/vuArialBold.ttf");
                BaseFont bf_tieude = BaseFont.createFont(file_font_tieude.getAbsolutePath(),BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
                Font font_tieude_1 = new Font(bf_tieude,16);
                font_tieude_1.setColor(BaseColor.BLUE);
                Font font_tieude_2 = new Font(bf_tieude,13);
                font_tieude_2.setColor(BaseColor.BLUE);
                Font font_tieude_3 = new Font(bf_tieude,13);
                Font font_tieude_4 = new Font(bf_tieude,12);

                File file_font_noidung = new File(url+"fonts/vuArial.ttf");
                BaseFont bf_noidung = BaseFont.createFont(file_font_noidung.getAbsolutePath(),BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
                Font font_noidung_1 = new Font(bf_noidung,13);
                Font font_noidung_2 = new Font(bf_noidung,12);
                Font font_noidung_3 = new Font(bf_noidung,11);

                Image logo = Image.getInstance(url+"images/laptop.png");
                logo.setAbsolutePosition(80, 750);
                logo.scaleAbsolute(50,50);
                document.add(logo);

                Paragraph prg_tench = new Paragraph("C???A H??NG M??Y T??NH IMS", font_tieude_2);
                prg_tench.setIndentationLeft(100);
                document.add(prg_tench);

                Paragraph prg_diachipk = new Paragraph("Khu ???? th??? ??HQG TPHCM, ph?????ng Linh Trung, Tp.Th??? ?????c, Tp.H??? Ch?? Minh", font_noidung_2);
                prg_diachipk.setIndentationLeft(100);
                document.add(prg_diachipk);

                Paragraph prg_sdtpk = new Paragraph("S??? ??i???n tho???i: 023 4567 8990", font_noidung_2);
                prg_sdtpk.setIndentationLeft(100);
                document.add(prg_sdtpk);

                Paragraph prg_tieude = new Paragraph("B??O C??O DOANH THU",font_tieude_1);
                prg_tieude.setAlignment(Element.ALIGN_CENTER);
                prg_tieude.setSpacingBefore(10);
                prg_tieude.setSpacingAfter(10);
                document.add(prg_tieude);

                try{
                    File file = new File("reports/"+file_name+month_selected+"-"+year_selected+".pdf");
                    if(!Desktop.isDesktopSupported()){
                        System.out.println("not supported");
                        return;
                    }
                    Desktop desktop = Desktop.getDesktop();
                    if(file.exists()){
                        desktop.open(file);
                    }
                }catch(Exception e){
                    System.out.println("L???i");
                }

                Paragraph prg1 = new Paragraph("I. Doanh thu c???a h??ng theo ng??y: ",font_tieude_2);
                prg1.setAlignment(Element.ALIGN_LEFT);
                prg1.setSpacingBefore(10);
                prg1.setSpacingAfter(10);
                document.add(prg1);

                PdfPTable tableDoanhThu = new PdfPTable(4);
                tableDoanhThu.setWidthPercentage(80);
                tableDoanhThu.setSpacingBefore(10);
                tableDoanhThu.setSpacingAfter(10);

                float[] tableDV_columnWidths = { 50, 120, 120, 80 };
                tableDoanhThu.setWidths(tableDV_columnWidths);

                PdfPCell cellSTT = new PdfPCell(new Paragraph("STT",font_tieude_4));
                cellSTT.setBorderColor(BaseColor.BLACK);
                cellSTT.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellSTT.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellSTT.setMinimumHeight(30);
                tableDoanhThu.addCell(cellSTT);

                PdfPCell cellNgay = new PdfPCell(new Paragraph("Ng??y",font_tieude_4));
                cellNgay.setBorderColor(BaseColor.BLACK);
                cellNgay.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellNgay.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableDoanhThu.addCell(cellNgay);

                PdfPCell cellDoanhThu = new PdfPCell(new Paragraph("Doanh thu",font_tieude_4));
                cellDoanhThu.setBorderColor(BaseColor.BLACK);
                cellDoanhThu.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellDoanhThu.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableDoanhThu.addCell(cellDoanhThu);

                PdfPCell cellGhiChu = new PdfPCell(new Paragraph("Ghi ch??",font_tieude_4));
                cellGhiChu.setBorderColor(BaseColor.BLACK);
                cellGhiChu.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellGhiChu.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableDoanhThu.addCell(cellGhiChu);

                for(int i = 0; i < li_revenue_day.size(); i++){
                    PdfPCell cellTT = new PdfPCell(new Paragraph(String.valueOf(i+1), font_noidung_2));
                    cellTT.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellTT.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cellTT.setMinimumHeight(20);
                    tableDoanhThu.addCell(cellTT);

                    PdfPCell cellDate = new PdfPCell(new Paragraph(li_revenue_day.get(i).getDate_ord(), font_noidung_2));
                    cellDate.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellDate.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tableDoanhThu.addCell(cellDate);

                    PdfPCell cellRevenue = new PdfPCell(new Paragraph(String.valueOf(li_revenue_day.get(i).getSum_ord()), font_noidung_2));
                    cellRevenue.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellRevenue.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tableDoanhThu.addCell(cellRevenue);

                    PdfPCell cellNote = new PdfPCell(new Paragraph("", font_noidung_2));
                    cellNote.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellNote.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tableDoanhThu.addCell(cellNote);
                }

                PdfPCell cellTongCong = new PdfPCell(new Paragraph("T???NG C???NG:", font_tieude_4));
                cellTongCong.setColspan(3);
                cellTongCong.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellTongCong.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellTongCong.setMinimumHeight(20);
                tableDoanhThu.addCell(cellTongCong);

                PdfPCell cellTongTien = new PdfPCell(new Paragraph(String.valueOf(sum_money), font_tieude_4));
                cellTongTien.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellTongTien.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellTongTien.setMinimumHeight(20);
                tableDoanhThu.addCell(cellTongTien);

                PdfPCell cellNote = new PdfPCell(new Paragraph("", font_tieude_4));
                cellNote.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cellNote.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableDoanhThu.addCell(cellGhiChu);

                document.add(tableDoanhThu);

                Paragraph prg2 = new Paragraph("II. B???ng th???ng k?? doanh thu: ",font_tieude_2);
                prg2.setAlignment(Element.ALIGN_LEFT);
                prg2.setSpacingBefore(10);
                prg2.setSpacingAfter(10);
                document.add(prg2);

                WritableImage image = chartView.snapshot(new SnapshotParameters(), null);
                BufferedImage awtImage = SwingFXUtils.fromFXImage(image, null);
                Image img = Image.getInstance(writer, awtImage, 1.0f);
                img.setAlignment(Element.ALIGN_CENTER);
                img.scaleAbsolute(450, 400);

                document.add(img);

                document.close();
                writer.close();
                System.out.println("Xu???t pdf th??nh c??ng");
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("Loi xuat pdf");
            }
        }
    }

    public void statisticBtnAction(ActionEvent event){
        if(month_selected == null && year_selected == null){
            Alert check_empty = new Alert(Alert.AlertType.ERROR);
            check_empty.setContentText("Vui l??ng ch???n m???c th???i gian!");
            check_empty.show();
        }
        else{
            if(year_selected != null && month_selected == null){
                chartView.getData().clear();
                if(x != null){
                    x.getCategories().clear();
                }
                x.setCategories(month);

                EXPORT_ORD export_ord_con = new EXPORT_ORD();
                li_revenue_month = export_ord_con.getREVENUE_MONTH(year_selected);
                XYChart.Series series = new XYChart.Series<>();
                series.setName("Doanh thu t???ng th??ng trong n??m " + year_selected);
                for(int i = 0; i < li_revenue_month.size(); i++){
                    String date = li_revenue_month.get(i).getMonth();
                    int revenue = li_revenue_month.get(i).getSum();
                    series.getData().add(new XYChart.Data<String, Number>(date,revenue));
                }
                chartView.getData().add(series);
                pdfBtn.setDisable(false);
                monthCb.setValue(null);
                yearCb.setValue(null);
            }
            else{
                chartView.getData().clear();
                if(x != null){
                    x.getCategories().clear();
                }
                YearMonth ym = YearMonth.of(Integer.parseInt(year_selected), Integer.parseInt(month_selected));
                ArrayList<String> days = new ArrayList<String>();
                for(int i = 1;i < ym.lengthOfMonth() + 1;i++){days.add(String.valueOf(i));}
                ObservableList<String> cate_x = FXCollections.observableArrayList(days);
                x.setCategories(cate_x);

                EXPORT_ORD export_ord_con = new EXPORT_ORD();
                li_revenue_day = export_ord_con.getREVENUE_DAY(month_selected,year_selected);
                XYChart.Series series = new XYChart.Series<>();
                series.setName("Doanh thu t???ng ng??y trong th??ng " + month_selected + ", n??m " + year_selected);
                for(int i = 0; i < li_revenue_day.size(); i++){
                    String date = li_revenue_day.get(i).getDate_ord();
                    int revenue = li_revenue_day.get(i).getSum_ord();
                    series.getData().add(new XYChart.Data<String, Number>(date,revenue));
                }
                chartView.getData().add(series);
                pdfBtn.setDisable(false);
                monthCb.setValue(null);
                yearCb.setValue(null);
            }
        }
    }

}

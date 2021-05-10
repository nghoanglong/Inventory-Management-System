package Controllers.ProductManagement;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SANPHAM {
    private String id_sp;
    private String ten_sp;
    private String loai_sp;
    private int gia_sp;
    private int num_sp;

    public SANPHAM(String id_sp, String ten_sp, String loai_sp, int gia_sp, int num_sp) {
        this.id_sp = id_sp;
        this.ten_sp = ten_sp;
        this.loai_sp = loai_sp;
        this.gia_sp = gia_sp;
        this.num_sp = num_sp;
    }

    public String getId_sp() {
        return this.id_sp;
    }
    public String getTen_sp(){return this.ten_sp;}
    public String getLoai_sp(){return this.loai_sp;}
    public int getGia_sp(){return this.gia_sp;}
    public int getNum_sp(){return this.num_sp;}

    public void setId_sp(String id_sp){this.id_sp = id_sp;}
    public void setTen_sp(String ten_sp){this.ten_sp = ten_sp;}
    public void setLoai_sp(String loai_sp){this.loai_sp = loai_sp;}
    public void setGia_sp(int gia_sp){this.gia_sp = gia_sp;}
    public void setNum_sp(int num_sp){this.num_sp = num_sp;}
}

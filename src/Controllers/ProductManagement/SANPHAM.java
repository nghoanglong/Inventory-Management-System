package Controllers.ProductManagement;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SANPHAM {
    private String id_prod;
    private String name_prod;
    private String type_prod;
    private int price;
    private int num_exist;

    public SANPHAM(String id_prod, String name_prod, String type_prod, int price, int num_exist) {
        this.id_prod = id_prod;
        this.name_prod = name_prod;
        this.type_prod = type_prod;
        this.price = price;
        this.num_exist = num_exist;
    }
    public SANPHAM(String id_prod, String name_prod, String type_prod, int num_exist){
        this.id_prod = id_prod;
        this.name_prod = name_prod;
        this.type_prod = type_prod;
        this.num_exist = num_exist;
    }

    public String getId_prod() { return this.id_prod; }
    public String getName_prod(){return this.name_prod;}
    public String getType_prod(){return this.type_prod;}
    public int getPrice(){return this.price;}
    public int getNum_exist(){return this.num_exist;}

    public void setId_prod(String id_prod){this.id_prod = id_prod;}
    public void setName_prod(String name_prod){this.name_prod = name_prod;}
    public void setType_prod(String type_prod){this.type_prod = type_prod;}
    public void setPrice(int price){this.price = price;}
    public void setNum_exist(int num_exist){this.num_exist = num_exist;}
}

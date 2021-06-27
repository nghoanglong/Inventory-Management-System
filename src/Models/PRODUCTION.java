package Models;

import Controllers.ProductManagement.SANPHAM;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class PRODUCTION extends CONNECT_DB{

    public PRODUCTION(){
        super();
    }

    public PRODUCTION(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }
    // variables
    String id_prod;
    String name_prod;
    String type_prod;
    int price;
    int num_exist;
    int state_prod;

    public ArrayList getTablePRODUCTION(){
        ArrayList<SANPHAM> li_sp = new ArrayList<SANPHAM>();
        try{
            Connection conn = this.getConnection();
            Statement stmt = conn.createStatement();
            String sql_query = "SELECT * FROM PRODUCTION WHERE state_prod = 1";
            ResultSet rs = stmt.executeQuery(sql_query);
            while (rs.next()){
                li_sp.add(new SANPHAM(rs.getString("id_prod"),
                                      rs.getString("name_prod"),
                                      rs.getString("type_prod"),
                                      rs.getInt("price"),
                                      rs.getInt("num_exist")));
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - getTablePRODUCTION - PRODUCTION");
        }
        return li_sp;
    }
    public int get_num_production_exist(String id_prod){
        int num_sp = 0;
        try {
            Connection conn = this.getConnection();
            String sql_query = "SELECT * FROM PRODUCTION WHERE id_prod = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql_query);
            pstmt.setString(1, id_prod);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                num_sp = Integer.parseInt(rs.getString("num_exist"));
            }
        }catch (Exception err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - getNumProductionExist - PRODUCTION");
        }
        return num_sp;
    }

    public boolean check_production_exist(String name_prod){
        /* Hàm kiểm tra xem đã tồn tại sản phẩm trong database hay chưa

           name_prod: tên sản phẩm

           return true ? false
         */
        boolean check = true;
        Connection con = this.getConnection();
        try{
            String sql_query = "SELECT * FROM PRODUCTION WHERE name_prod = ? AND state_prod = 1";
            PreparedStatement pstmt = con.prepareStatement(sql_query);
            pstmt.setString(1, name_prod);
            ResultSet res = pstmt.executeQuery();
            if(!res.next()){
                check = false;
            }

        } catch (SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - check_production_exist - PRODUCTION");
        }
        return check;
    }

    public boolean check_IDproduction(Connection con, String id_prod){
        boolean check = true;
        try {
            String sql_query = "SELECT * FROM PRODUCTION WHERE id_prod = ?;";
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, id_prod);
            ResultSet res = pstmt.executeQuery();
            if(!res.next()){
                check = false;
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - check_IDproduction - PRODUCTION");
        }
        return check;
    }

    public String generate_IDproduction(){
        Connection con = this.getConnection();
        Random ran_num = new Random(10005);
        String id_prod = "";
        while(true){
            String temp = "PRODUCTION" + ran_num.nextInt();
            if(!this.check_IDproduction(con, temp)){
                id_prod = temp;
                break;
            }
        }
        return id_prod;
    }

    public int insert_production(String id_prod,
                                 String name_prod,
                                 String type_prod,
                                 int price,
                                 int num_exist,
                                 int state_prod){
        /* insert data vào database

            id_sp: được generate
            loai_sp: loại sản phẩm
            ten_sp: tên sản phẩm
            gia: giá sản phẩm
            num_sp: số lượng sản phẩm
            sp_state = 0 -> sản phẩm đang pending được thêm mới
                     = 1 -> sản phẩm hiện đang trong kho

            return res = 0: insert ko thành công vì sản phẩm đã exist
                       = 1: insert thành công
         */
        int result = 1;
        try{
            Connection con = this.getConnection();
            if(this.check_production_exist(name_prod)){
                result = 0;
            }
            else{
                // check đã tồn tại sản phẩm này trong kho hay chưa
                // nếu chưa -> thực hiện insert
                String SQL_query = "INSERT INTO PRODUCTION(id_prod, name_prod, type_prod, price, num_exist, state_prod) VALUES(?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt = con.prepareStatement(SQL_query, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, id_prod);
                pstmt.setString(2, name_prod);
                pstmt.setString(3, type_prod);
                pstmt.setInt(4, price);
                pstmt.setInt(5, num_exist);
                pstmt.setInt(6, state_prod);
                pstmt.executeUpdate();
                System.out.println("Insert PRODUCTION succeed");
            }
        }catch(SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - insert_production - PRODUCTION");
        }
        return result;
    }

}

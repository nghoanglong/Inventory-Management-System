package Models;

import Controllers.ProductManagement.SANPHAM;

import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class QLSP extends CONNECT_DB{

    public QLSP(){
        super();
    }

    public QLSP(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }
    public ArrayList getTableQLSP(){
        ArrayList<SANPHAM> li_sp = new ArrayList<SANPHAM>();
        try{
            Connection conn = this.getConnection();
            Statement stmt = conn.createStatement();
            String sql_query = "SELECT * FROM QLSP WHERE sp_state = 1";
            ResultSet rs = stmt.executeQuery(sql_query);
            while (rs.next()){
                li_sp.add(new SANPHAM(rs.getString("id_sp"),
                                      rs.getString("ten_sp"),
                                      rs.getString("loai_sp"),
                                      rs.getInt("gia"),
                                      rs.getInt("num_sp")));
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - get table QLSP của QLSP");
        }
        return li_sp;
    }
    public int getNumsp(String id_sp){
        int num_sp = 0;
        try {
            Connection conn = this.getConnection();
            String sql_query = "SELECT * FROM QLSP WHERE id_sp = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql_query);
            pstmt.setString(1, id_sp);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                num_sp = Integer.parseInt(rs.getString("num_sp"));
            }
        }catch (Exception err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - get num sản phẩm của QLSP");
        }
        return num_sp;
    }

    public boolean check_exist_sp(Connection con, String ten_sp){
        /* Hàm kiểm tra xem đã tồn tại sản phẩm trong database hay chưa

           ten_sp: tên sản phẩm

           return true ? false
         */
        boolean check = true;
        try{
            String sql_query = "SELECT * FROM QLSP WHERE ten_sp = ?";
            PreparedStatement pstmt = con.prepareStatement(sql_query);
            pstmt.setString(1, ten_sp);
            ResultSet res = pstmt.executeQuery();
            if(!res.next()){
                check = false;
            }

        } catch (SQLException err){
            err.printStackTrace();
            System.out.print("Lỗi check tồn tại sản phẩm của QLSP");
        }
        return check;
    }

    public static boolean check_IDsanpham(Connection con, String id_sp){
        boolean check = true;
        try {
            String sql_query = "SELECT * FROM QLSP WHERE id_sp = ?;";
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, id_sp);
            ResultSet res = pstmt.executeQuery();
            if(!res.next()){
                check = false;
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.print("Lỗi check id sản phẩm của QLSP");
        }
        return check;
    }

    public String generate_IDsp(){
        Connection con = this.getConnection();
        Random ran_num = new Random(100000000);
        String id_sp = "";
        while(true){
            String temp = "QLSP" + ran_num.nextInt();
            if(!QLSP.check_IDsanpham(con, temp)){
                id_sp = temp;
                break;
            }
        }
        return id_sp;
    }

    public int insert_qlsp(String id_sp,
                           String ten_sp,
                           String loai_sp,
                           int gia,
                           int num_sp,
                           int sp_state){
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
            if(check_exist_sp(con, ten_sp)){
                result = 0;
            }
            else{
                // check đã tồn tại sản phẩm này trong kho hay chưa
                // nếu chưa -> thực hiện insert
                String SQL_query = "INSERT INTO QLSP(id_sp, ten_sp, loai_sp, gia, num_sp, sp_state) VALUES(?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt = con.prepareStatement(SQL_query, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, id_sp);
                pstmt.setString(2, ten_sp);
                pstmt.setString(3, loai_sp);
                pstmt.setInt(4, gia);
                pstmt.setInt(5, num_sp);
                pstmt.setInt(6, sp_state);
                pstmt.executeUpdate();
                System.out.println("Insert QLSP succeed");
            }
        }catch(SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi insert sản phẩm của QLSP");
        }
        return result;
    }

    public int update_qlsp(String id_sp, int type, int sl_sp){
        /* Hàm update số lượng sản phẩm

           id_sp: id sản phẩm
           type: 1 -> thêm
                 2  -> giảm
           sl_sp: số lượng sản phẩm muốn thêm hoặc giảm

           return 1 -> thành công
                  0 -> ko thành công do giảm số lượng sản phẩm < 0

         */
        int res = 1;
        try{
            Connection con = this.getConnection();
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String SQL_query = "SELECT * FROM QLSP WHERE id_sp='"+ id_sp +"';";
            ResultSet rs = stmt.executeQuery(SQL_query);
            rs.first();
            int cur_sl = rs.getInt("num_sp");
            switch (type){
                case 1:
                    rs.updateInt("num_sp", cur_sl + sl_sp);
                    break;
                case 2:
                    rs.updateInt("num_sp", cur_sl - sl_sp);

            }
            rs.updateRow();

        }catch (SQLException err){
            err.printStackTrace();
            System.out.print("Lỗi hệ thống - update sản phẩm của QLSP");
            res = 0;
        }
        return res;
    }

    public int delete_qlsp(String id_sp){
        /* Method delete sản phẩm

           return 1 -> delete thành công
                  0 -> delete thất bại do sản phẩm ko tồn tại

         */
        int result = 1;
        try{
            Connection con = this.getConnection();
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String SQL_query = "SELECT * FROM QLSP WHERE id_sp='"+ id_sp +"';";
            ResultSet rs = stmt.executeQuery(SQL_query);
            if(rs.first()) {
                rs.deleteRow();
            }else{
                result = 0;
            }
        }catch(SQLException err){
            err.printStackTrace();
            System.out.print("Lỗi delete sản phẩm của QLSP");
        }
        return result;
    }

}

package Models;

import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.Random;

public class QLSP extends CONNECT_DB{

    public QLSP(){
        super();
    }

    public QLSP(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }

    public static boolean check_IDsanpham(Connection con, String id_sp){
        boolean check = true;
        try {
            String query_login = "SELECT * FROM QLSP WHERE id_sp = ?;";
            PreparedStatement pstmt = con.prepareStatement(query_login, Statement.RETURN_GENERATED_KEYS);
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

    public boolean check_exist_sp(Connection con, String ten_sp){
        /* Hàm kiểm tra xem đã tồn tại sản phẩm trong database hay chưa

           ten_sp: tên sản phẩm

           return true ? false
         */
        boolean check = true;
        try{
            String SQL_query = "SELECT * FROM QLSP WHERE ten_sp = ?";
            PreparedStatement pstmt = con.prepareStatement(SQL_query);
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

    public int insert_qlsp(String id_sp, String ten_sp, String loai_sp, int gia, int num_sp){
        /* insert data vào database

            loai_sp: loại sản phẩm
            ten_sp: tên sản phẩm
            gia: giá sản phẩm
            num_sp: số lượng sản phẩm

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
                String SQL_query = "INSERT INTO QLSP(id_sp, ten_sp, loai_sp, gia, num_sp) VALUES(?, ?, ?, ?, ?)";
                PreparedStatement pstmt = con.prepareStatement(SQL_query, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, id_sp);
                pstmt.setString(2, ten_sp);
                pstmt.setString(3, loai_sp);
                pstmt.setInt(4, gia);
                pstmt.setInt(5, num_sp);
                pstmt.executeUpdate();
            }
        }catch(SQLException err){
            err.printStackTrace();
            System.out.print("Lỗi insert sản phẩm của QLSP");
        }
        return result;
    }

    public int update_qlsp(String id_sp, int type, int sl_sp){
        /* Hàm update số lượng sản phẩm

           id_sp: id sản phẩm
           type: 1 -> thêm
                   -> giảm
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
                    if(cur_sl - sl_sp < 0){
                        res = 0;
                    }else{
                        rs.updateInt("num_sp", cur_sl - sl_sp);
                    }
            }
            if(res != 0) {
                rs.updateRow();
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.print("Lỗi update sản phẩm của QLSP");
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

    public static void main(String[] args){
        // demo các method
        QLSP new_qlsp = new QLSP();
    }

}

package Models;

import java.io.File;
import java.io.FileInputStream;
import java.sql.*;

public class QLSP extends CONNECT_DB{

    public QLSP(){
        super();
    }

    public QLSP(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }

    public boolean check_exist_sp(String ten_sp){
        /* Hàm kiểm tra xem đã tồn tại sản phẩm trong database hay chưa

           ten_sp: tên sản phẩm

           return true ? false
         */
        boolean check = true;
        try{
            Connection con = this.getConnection();
            String SQL_query = "SELECT * FROM QLSP WHERE ten_sp = ?";
            PreparedStatement pstmt = con.prepareStatement(SQL_query);
            pstmt.setString(1, ten_sp);
            ResultSet res = pstmt.executeQuery();
            if(!res.next()){
                check = false;
            }

        } catch (SQLException err){
            err.printStackTrace();
        }
        return check;
    }
    public int insert_qlsp(String loai_sp, String ten_sp, int num_sp, FileInputStream image, long image_size){
        /* insert data vào database

            loai_sp: loại sản phẩm
            ten_sp: tên sản phẩm
            num_sp: số lượng sản phẩm
            image: controller chọn hình ảnh trong folder
            image_size: size của image

            return res = 0: insert ko thành công vì sản phẩm đã exist
                       = 1: insert thành công
         */
        int res = 0;
        try{
            if(!check_exist_sp(ten_sp)) {
                // check đã tồn tại sản phẩm này trong kho hay chưa
                // nếu chưa -> thực hiện insert

                String SQL_query = "INSERT INTO QLSP(loai_sp, ten_sp, num_sp, img) VALUES(?, ?, ?, ?)";
                Connection con = this.getConnection();
                PreparedStatement pstmt = con.prepareStatement(SQL_query, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, loai_sp);
                pstmt.setString(2, ten_sp);
                pstmt.setInt(3, num_sp);
                pstmt.setBinaryStream(4, image, image_size);
                pstmt.executeUpdate();
                res = 1;
            }
        }catch(SQLException err){
            err.printStackTrace();
        }
        return  res;
    }

    public int update_qlsp(int id_sp, int type, int sl_sp){
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
        }
        return res;
    }

    public int delete_qlsp(int id_sp){
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
        }
        return result;
    }

    public static void main(String[] args){
        // demo các method
        QLSP new_qlsp = new QLSP();
        int res = new_qlsp.update_qlsp(1, 1, 10);
        System.out.print(res);
    }

}

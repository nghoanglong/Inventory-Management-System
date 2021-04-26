package Models;

import java.io.File;
import java.io.FileInputStream;
import java.sql.*;

public class QLSP extends CONNECT_DB{

    public QLSP(){
        super("DESKTOP-BHNESJS\\SQLEXPRESS",1400,"sa","1712","Inventory_Management_System");
    }

    public QLSP(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }

    public boolean check_exist_sp(String ten_sp){
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

    // update sản phẩm

    // delete sản phẩm

}

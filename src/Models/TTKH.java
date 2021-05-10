package Models;

import java.sql.*;
import java.util.Random;

public class TTKH extends CONNECT_DB {
    public TTKH() { super();}

    public TTKH(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }
    public static boolean check_IDkh(Connection con, String id_kh){
        boolean check = true;
        try {
            String sql_query = "SELECT * FROM TT_KH WHERE id_kh = ?;";
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, id_kh);
            ResultSet res = pstmt.executeQuery();
            if(!res.next()){
                check = false;
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.print("Lỗi check id khách hàng của TTKH");
        }
        return check;
    }
    public String generate_IDkh(){
        Connection con = this.getConnection();
        Random ran_num = new Random(100000000);
        String id_kh = "";
        while(true){
            String temp = "TTKH" + ran_num.nextInt();
            if(!TTKH.check_IDkh(con, temp)){
                id_kh = temp;
                break;
            }
        }
        return id_kh;
    }
    public void insert_TTKH(String id_kh, String name_kh, String phone_kh, String address_kh){
         /* insert data vào table thông tin khách hàng
         */

        try{
            Connection con = this.getConnection();
            String sql_query = "INSERT INTO TT_KH(id_kh, name_kh, phone_kh, address_kh) VALUES(?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, id_kh);
            pstmt.setString(2, name_kh);
            pstmt.setString(3, phone_kh);
            pstmt.setString(4, address_kh);
            pstmt.executeUpdate();

        }catch(SQLException err){
            System.out.print("Lỗi insert thông tin khách hàng của TTKH");
        }
    }
}

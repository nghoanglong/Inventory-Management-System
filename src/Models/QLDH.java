package Models;

import java.sql.*;
import java.util.Random;

public class QLDH extends CONNECT_DB{
    public QLDH() { super();}

    public QLDH(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }
    public static boolean check_IDdh(Connection con, String id_dh){
        boolean check = true;
        try {
            String query_login = "SELECT * FROM QLDH WHERE id_dh = ?;";
            PreparedStatement pstmt = con.prepareStatement(query_login, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, id_dh);
            ResultSet res = pstmt.executeQuery();
            if(!res.next()){
                check = false;
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.print("Lỗi check id đơn hàng của QLDH");
        }
        return check;
    }
    public String generate_IDdh(){
        Connection con = this.getConnection();
        Random ran_num = new Random(100000000);
        String id_dh = "";
        while(true){
            String temp = "QLDH" + ran_num.nextInt();
            if(!QLDH.check_IDdh(con, temp)){
                id_dh = temp;
                break;
            }
        }
        return id_dh;
    }
    public void insert_qldh(String id_dh, String id_kh, String id_user, String loai_dh, String date_dh){
        /* insert data vào table quản lý đơn hàng
         */

        try{
            Connection con = this.getConnection();
            String sql_query = "INSERT INTO TTKH(id_dh, id_kh, id_user, loai_dh, date_dh) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, id_dh);
            pstmt.setString(2, "(SELECT id_kh FROM TT_KH WHERE id_kh = '" + id_kh + "')");
            pstmt.setString(3, "(SELECT id_user FROM USERS WHERE id_user = '" + id_user + "')");
            pstmt.setString(4, loai_dh);
            pstmt.setString(5, date_dh);
            pstmt.executeUpdate();

        }catch(SQLException err){
            System.out.print("Lỗi insert đơn hàng của QLDH");
        }
    }

}

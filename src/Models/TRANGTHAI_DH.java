package Models;

import java.sql.*;
import java.util.Random;

public class TRANGTHAI_DH extends CONNECT_DB {
    public TRANGTHAI_DH() { super();}

    public TRANGTHAI_DH(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }
    public static boolean check_IDttdh(Connection con, String id_ttdh){
        boolean check = true;
        try {
            String sql_query = "SELECT * FROM TRANGTHAI_DH WHERE id_ttdh = ?;";
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, id_ttdh);
            ResultSet res = pstmt.executeQuery();
            if(!res.next()){
                check = false;
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.print("Lỗi check id trạng thái đơn hàng của TRANGTHAI_DH");
        }
        return check;
    }
    public String generate_IDttdh(){
        Connection con = this.getConnection();
        Random ran_num = new Random(100000000);
        String id_ttdh = "";
        while(true){
            String temp = "TTDH" + ran_num.nextInt();
            if(!TRANGTHAI_DH.check_IDttdh(con, temp)){
                id_ttdh = temp;
                break;
            }
        }
        return id_ttdh;
    }
    public void insert_ttdh(String id_ttdh, String id_dh, int admin_state, int qlkho_state, String date_return_2state){
        /* insert data vào table chi tiết đơn hàng
         */

        try{
            Connection con = this.getConnection();
            String sql_query = "INSERT INTO TRANGTHAI_DH(id_ttdh, id_dh, admin_state, qlkho_state, date_return_2state) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, id_ttdh);
            pstmt.setString(2, "(SELECT id_dh FROM QLDH WHERE id_dh = '" + id_dh + "')");
            pstmt.setInt(3, admin_state);
            pstmt.setInt(4, qlkho_state);
            pstmt.setString(5, date_return_2state);
            pstmt.executeUpdate();

        }catch(SQLException err){
            System.out.print("Lỗi insert trạng thái đơn hàng của TRANGTHAI_DH");
        }
    }
}

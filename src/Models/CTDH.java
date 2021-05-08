package Models;

import java.sql.*;
import java.util.Random;

public class CTDH extends CONNECT_DB {
    public CTDH() { super();}

    public CTDH(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }
    public static boolean check_IDctdh(Connection con, String id_ctdh){
        boolean check = true;
        try {
            String query_login = "SELECT * FROM CTDH WHERE id_ctdh = ?;";
            PreparedStatement pstmt = con.prepareStatement(query_login, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, id_ctdh);
            ResultSet res = pstmt.executeQuery();
            if(!res.next()){
                check = false;
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.print("Lỗi check id chi tiết đơn hàng của CTDH");
        }
        return check;
    }
    public String generate_IDctdh(){
        Connection con = this.getConnection();
        Random ran_num = new Random(100000000);
        String id_ctdh = "";
        while(true){
            String temp = "CTDH" + ran_num.nextInt();
            if(!CTDH.check_IDctdh(con, temp)){
                id_ctdh = temp;
                break;
            }
        }
        return id_ctdh;
    }
    public void insert_ctdh(String id_ctdh, String id_sp, String id_dh, int sl_yc){
        /* insert data vào table chi tiết đơn hàng
         */

        try{
            Connection con = this.getConnection();
            String sql_query = "INSERT INTO CTDH(id_ctdh, id_sp, id_dh, sl_yc) VALUES(?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, id_ctdh);
            pstmt.setString(2, "(SELECT id_sp FROM QLSP WHERE id_sp = '" + id_sp + "')");
            pstmt.setString(3, "(SELECT id_dh FROM QLDH WHERE id_dh = '" + id_dh + "')");
            pstmt.setInt(4, sl_yc);
            pstmt.executeUpdate();

        }catch(SQLException err){
            System.out.print("Lỗi insert chi tiết đơn hàng của CTDH");
        }
    }

}

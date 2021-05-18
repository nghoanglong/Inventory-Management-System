package Models;

import java.sql.*;
import java.util.Random;

public class CTDH extends CONNECT_DB {
    public CTDH() { super();}

    public CTDH(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }

    public void insert_ctdh(String id_ctdh, String id_sp, int sl_yc){
        /* insert data vào table chi tiết đơn hàng
         */

        try{
            Connection con = this.getConnection();
            String sql_query = "INSERT INTO CTDH(id_ctdh, id_sp, sl_yc) VALUES(?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, "(SELECT id_dh FROM QLDH WHERE id_dh = '" + id_ctdh + "')");
            pstmt.setString(2, "(SELECT id_sp FROM QLSP WHERE id_sp = '" + id_sp + "')");
            pstmt.setInt(3, sl_yc);
            pstmt.executeUpdate();

        }catch(SQLException err){
            System.out.print("Lỗi insert chi tiết đơn hàng của CTDH");
        }
    }

}

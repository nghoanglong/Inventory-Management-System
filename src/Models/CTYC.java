package Models;

import java.sql.*;
import java.util.Random;

public class CTYC extends CONNECT_DB {
    public CTYC() { super();}

    public CTYC(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }

    public void insert_ctyc(String id_ctyc, String id_sp, int sl_yc){
        /* insert data vào table chi tiết đơn hàng
         */
        try{
            Connection con = this.getConnection();
            String sql_query = "INSERT INTO CTYC VALUES((SELECT id_yc FROM QLYC WHERE id_yc = '" + id_ctyc + "'), " +
                                                        "(SELECT id_sp FROM QLSP WHERE id_sp = '" + id_sp + "'), " +
                                                        sl_yc + ")";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql_query);
            System.out.println("Insert CTYC succeed");
        }catch(SQLException err){
            System.out.println("Lỗi hệ thống - insert chi tiết yêu cầu của CTYC");
        }
    }

}

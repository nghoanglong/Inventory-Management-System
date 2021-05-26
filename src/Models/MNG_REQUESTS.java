package Models;

import java.sql.*;
import java.util.Random;

public class MNG_REQUESTS extends CONNECT_DB {
    public MNG_REQUESTS() { super();}

    public MNG_REQUESTS(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }

    public int insert_mng_requests(String id_ord,
                                   String id_prod,
                                   int num_ord){
        /* insert data vào table chi tiết đơn hàng
         */
        int result = 1;
        try{
            Connection con = this.getConnection();
            String sql_query = "INSERT INTO MNG_REQUESTS VALUES((SELECT id_ord FROM MNG_ORDERS WHERE id_ord = '" + id_ord + "'), " +
                                                                "(SELECT id_prod FROM PRODUCTION WHERE id_prod = '" + id_prod + "'), " +
                                                                num_ord + ");";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql_query);
            System.out.println("Insert MNG_REQUESTS succeed");
        }catch(SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - insert_mng_requests - MNG_REQUESTS");
            result = 0;
        }
        return result;
    }

}

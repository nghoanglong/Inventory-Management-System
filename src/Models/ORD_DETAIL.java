package Models;

import java.sql.*;
import java.util.Random;

public class ORD_DETAIL extends CONNECT_DB {
    public ORD_DETAIL() { super();}

    public ORD_DETAIL(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }

    public void insert_order_detail(String id_ord, String id_prod, int num_ord){
        /* insert data vào table chi tiết đơn hàng
         */
        try{
            Connection con = this.getConnection();
            String sql_query = "INSERT INTO ORDER_DETAIL VALUES((SELECT id_ord FROM ORDERS WHERE id_ord = '" + id_ord + "'), " +
                                                                "(SELECT id_prod FROM PRODUCTION WHERE id_prod = '" + id_prod + "'), " +
                                                                num_ord + ")";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql_query);
            System.out.println("Insert ORDER_DETAIL succeed");
        }catch(SQLException err){
            System.out.println("Lỗi hệ thống - insert_order_detail - ORD_DETAIL");
        }
    }

}

package Models;

import Controllers.OrderManagement.ORDER;
import Controllers.ProductManagement.SANPHAM;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class DETAIL_ORD extends CONNECT_DB {
    public DETAIL_ORD() { super();}

    public DETAIL_ORD(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }

    public ArrayList getTableDETAILORD(String id_ord){
        ArrayList<SANPHAM> li_req = new ArrayList<SANPHAM>();
        try{
            Connection conn = this.getConnection();
            Statement stmt = conn.createStatement();
            String sql_query = "SELECT DETAIL_ORD.id_prod, name_prod, type_prod, num_ord\n" +
                               "FROM DETAIL_ORD\n" +
                               "INNER JOIN PRODUCTION ON DETAIL_ORD.id_prod = PRODUCTION.id_prod\n" +
                               "WHERE DETAIL_ORD.id_ord = '" + id_ord + "'";
            ResultSet rs = stmt.executeQuery(sql_query);
            while (rs.next()) {
                li_req.add(new SANPHAM(rs.getString("id_prod"),
                                       rs.getString("name_prod"),
                                       rs.getString("type_prod"),
                                       rs.getInt("num_ord")));
            }

        }catch (SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - getTableDETAILORD - DETAIL_ORD");
        }
        return li_req;
    }
    public int insert_detail_ord(String id_ord,
                                 String id_prod,
                                 int num_ord){
        /* insert data vào table chi tiết đơn hàng
         */
        int result = 1;
        try{
            Connection con = this.getConnection();
            String sql_query = "INSERT INTO DETAIL_ORD VALUES((SELECT id_ord FROM MNG_ORDERS WHERE id_ord = '" + id_ord + "'), " +
                                                             "(SELECT id_prod FROM PRODUCTION WHERE id_prod = '" + id_prod + "'), " +
                                                              num_ord + ");";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql_query);
            System.out.println("Insert DETAIL_ORD succeed");
        }catch(SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - insert_detail_ord - DETAIL_ORD");
            result = 0;
        }
        return result;
    }

}

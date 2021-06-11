package Models;

import Controllers.ProductManagement.SANPHAM;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class DELETE_ORD extends CONNECT_DB {
    public DELETE_ORD() { super();}

    public DELETE_ORD(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }

    public boolean check_IDdeleteord(Connection con, String id_del_ord){
        boolean check = true;
        try {
            String sql_query = "SELECT * FROM DELETE_ORD WHERE id_del_ord = ?;";
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, id_del_ord);
            ResultSet res = pstmt.executeQuery();
            if(!res.next()){
                check = false;
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - check_IDMdeleteord - DELETE_ORD");
        }
        return check;
    }
    public String generate_IDdeleteord(){
        Connection con = this.getConnection();
        Random ran_num = new Random(10004);
        String id_del_ord = "";
        while(true){
            String temp = "DELETE_ORD" + ran_num.nextInt();
            if(!this.check_IDdeleteord(con, temp)){
                id_del_ord = temp;
                break;
            }
        }
        return id_del_ord;
    }

    public int insert_delete_ord(String id_del_ord,
                                 String id_ord,
                                 int admin_state,
                                 int warehouse_state,
                                 String date_2state_return){
        /* insert data vào table chi tiết đơn hàng
         */
        int result = 1;
        try{
            Connection con = this.getConnection();
            String sql_query = "INSERT INTO DELETE_ORD VALUES('" + id_del_ord +
                                                              "', (SELECT id_ord FROM MNG_ORDERS WHERE id_ord = '" + id_ord + "'), " +
                                                              admin_state + ", " +
                                                              warehouse_state + ", " +
                                                              date_2state_return + ")";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql_query);
            System.out.println("Insert DELETE_ORD succeed");
        }catch(SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - insert_delete_ord - DELETE_ORD");
            result = 0;
        }
        return result;
    }
    public int update_deleteord_warehouse_state(String id_ord, int warehouse_state){
        int res = 1;
        try{
            Connection con = this.getConnection();
            String sql_query = "UPDATE DELETE_ORD\n" +
                               "SET warehouse_state = " + warehouse_state + "\n" +
                               "WHERE id_ord = '" + id_ord + "'";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql_query);
            System.out.println("Update DELETE_ORD warehouse_state succeed");
        }catch(SQLException err){
            res = 0;
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - update_deleteord_warehouse_state - DELETE_ORD");
        }
        return res;
    }
    public void update_deleteord_date_2state_return(String id_ord, String date_2state_return){
        try{
            Connection con = this.getConnection();
            String sql_query = "UPDATE DELETE_ORD\n" +
                               "SET date_2state_return = '" + date_2state_return + "'\n" +
                               "WHERE id_ord = '" + id_ord + "'";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql_query);
            System.out.println("Update DELETE_ORD date_2state_return succeed");
        }catch(SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - update_deleteord_date_2state_return - DELETE_ORD");
        }
    }
}

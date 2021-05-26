package Models;

import Controllers.OrderManagement.ORDER;
import Controllers.ProductManagement.SANPHAM;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MNG_ORDERS extends CONNECT_DB{
    public MNG_ORDERS() { super();}

    public MNG_ORDERS(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }

    public ArrayList getTableORDER(){
        ArrayList<ORDER> li_order = new ArrayList<ORDER>();
        try{
            Connection conn = this.getConnection();
            Statement stmt = conn.createStatement();
            String sql_query = "SELECT id_ord, name_cus, fullname, type_ord, date_ord, state_ord\n" +
                               "FROM MNG_ORDERS\n" +
                               "INNER JOIN USERS ON MNG_ORDERS.id_user = USERS.id_user\n" +
                               "LEFT JOIN CUSTOMER_INFO ON MNG_ORDERS.id_cus = CUSTOMER_INFO.id_cus";
            ResultSet rs = stmt.executeQuery(sql_query);
            while (rs.next()) {
                li_order.add(new ORDER(rs.getString("id_ord"),
                        rs.getString("name_cus"),
                        rs.getString("fullname"),
                        rs.getString("type_ord"),
                        rs.getString("date_ord"),
                        rs.getInt("state_ord")));
            }

        }catch (SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - getTableORDER - ORDER");
        }
        return li_order;
    }
    public ArrayList getTableORDER_admin(){
        ArrayList<ORDER> li_order = new ArrayList<ORDER>();
        try{
            Connection conn = this.getConnection();
            Statement stmt = conn.createStatement();
            String sql_query = "SELECT id_ord, name_cus, fullname, type_ord, date_ord\n" +
                               "FROM MNG_ORDERS\n" +
                               "INNER JOIN USERS ON MNG_ORDERS.id_user = USERS.id_user\n" +
                               "INNER JOIN CUSTOMER_INFO ON MNG_ORDERS.id_cus = CUSTOMER_INFO.id_cus\n" +
                               "WHERE MNG_ORDERS.admin_state = 2;";
            ResultSet rs = stmt.executeQuery(sql_query);
            while (rs.next()) {
                li_order.add(new ORDER(rs.getString("id_ord"),
                        rs.getString("name_cus"),
                        rs.getString("fullname"),
                        rs.getString("type_ord"),
                        rs.getString("date_ord")));
            }

        }catch (SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - getTableORDER_admin - ORDER");
        }
        return li_order;
    }
    public ArrayList getTableORDER_warehouse(){
        ArrayList<ORDER> li_order = new ArrayList<ORDER>();
        try{
            Connection conn = this.getConnection();
            Statement stmt = conn.createStatement();
            String sql_query = "SELECT id_ord, name_cus, fullname, type_ord, date_ord\n" +
                               "FROM MNG_ORDERS\n" +
                               "INNER JOIN USERS ON MNG_ORDERS.id_user = USERS.id_user\n" +
                               "LEFT JOIN CUSTOMER_INFO ON MNG_ORDERS.id_cus = CUSTOMER_INFO.id_cus\n" +
                               "WHERE MNG_ORDERS.warehouse_mng_state = 2 AND MNG_ORDERS.admin_state = 1;";
            ResultSet rs = stmt.executeQuery(sql_query);
            while (rs.next()) {
                li_order.add(new ORDER(rs.getString("id_ord"),
                        rs.getString("name_cus"),
                        rs.getString("fullname"),
                        rs.getString("type_ord"),
                        rs.getString("date_ord")));
            }

        }catch (SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - getTableORDER_admin - ORDER");
        }
        return li_order;
    }

    public String getTypeORDER(String id_ord){
        String result = "";
        try {
            String sql_query = "SELECT * FROM MNG_ORDERS WHERE id_ord = ?;";
            Connection con = this.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, id_ord);
            ResultSet res = pstmt.executeQuery();
            if(res.next()){
                result = res.getString("type_ord");
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - getTypeORDER - MNG_ORDERS");
        }
        return result;
    }

    public boolean check_IDmngord(Connection con, String id_ord){
        boolean check = true;
        try {
            String sql_query = "SELECT * FROM MNG_ORDERS WHERE id_ord = ?;";
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, id_ord);
            ResultSet res = pstmt.executeQuery();
            if(!res.next()){
                check = false;
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - check_IDMngord - MNG_ORDERS");
        }
        return check;
    }
    public String generate_IDmngord(){
        Connection con = this.getConnection();
        Random ran_num = new Random(10004);
        String id_ord = "";
        while(true){
            String temp = "MNG_ORDERS" + ran_num.nextInt();
            if(!this.check_IDmngord(con, temp)){
                id_ord = temp;
                break;
            }
        }
        return id_ord;
    }

    public int insert_mng_orders(String id_ord,
                             String id_user,
                             String id_cus,
                             String type_ord,
                             String date_ord,
                             int state_ord,
                             int admin_state,
                             int warehouse_mng_state,
                             String date_2state_return){
        /* insert data vào table quản lý đơn hàng
         */
        int result = 1;
        try{
            Connection con = this.getConnection();
            String sql_query = "INSERT INTO MNG_ORDERS VALUES('" + id_ord + "', " +
                                                              "(SELECT id_user FROM USERS WHERE id_user = '" + id_user + "'), " +
                                                              "(SELECT id_cus FROM CUSTOMER_INFO WHERE id_cus = '" + id_cus + "'), '" +
                                                              type_ord + "', '" +
                                                              date_ord + "', " +
                                                              state_ord + ", " +
                                                              admin_state + ", " +
                                                              warehouse_mng_state + ", " +
                                                              date_2state_return + ");";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql_query);
            System.out.println("Insert MNG_ORDERS succeed");
        }catch(SQLException err){
            System.out.println("Lỗi hệ thống - insert_mng_orders - MNG_ORDERS");
            err.printStackTrace();
            result  = 0;
        }
        return result;
    }
    public int update_mng_order(String id_ord, HashMap<String, String> ord_change){
        int result = 1;
        try{
            Connection conn = this.getConnection();
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql_query = "SELECT * FROM MNG_ORDERS WHERE id_ord = '"+ id_ord + "';";
            ResultSet rs = stmt.executeQuery(sql_query);
            rs.first();
            for(String key: ord_change.keySet()){
                switch (key){
                    case "admin_state":
                        rs.updateInt(key, Integer.parseInt(ord_change.get(key)));
                        break;
                    case "warehouse_mng_state":
                        rs.updateInt(key, Integer.parseInt(ord_change.get(key)));
                        break;
                    case "state_ord":
                        rs.updateInt(key, Integer.parseInt(ord_change.get(key)));
                        break;
                    case "date_2state_return":
                        rs.updateString(key, ord_change.get(key));
                }
            }
            rs.updateRow();
        }catch (SQLException err){
            result = 0;
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - update_mng_order - MNG_ORDERS");
        }
        return result;
    }

}

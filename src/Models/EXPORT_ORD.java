package Models;

import java.sql.*;
import java.util.Random;

public class EXPORT_ORD extends CONNECT_DB {
    public EXPORT_ORD() { super();}

    public EXPORT_ORD(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }

    public boolean check_IDexportord(Connection con, String id_export_ord){
        boolean check = true;
        try {
            String sql_query = "SELECT * FROM EXPORT_ORD WHERE id_export_ord = ?;";
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, id_export_ord);
            ResultSet res = pstmt.executeQuery();
            if(!res.next()){
                check = false;
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - check_IDexportord - DELETE_ORD");
        }
        return check;
    }
    public String generate_IDexportord(){
        Connection con = this.getConnection();
        Random ran_num = new Random(10004);
        String id_export_ord = "";
        while(true){
            String temp = "EXPORT_ORD" + ran_num.nextInt();
            if(!this.check_IDexportord(con, temp)){
                id_export_ord = temp;
                break;
            }
        }
        return id_export_ord;
    }

    public int insert_export_ord(String id_export_ord,
                                 String id_ord,
                                 int admin_state,
                                 int warehouse_state,
                                 String date_2state_return){
        /* insert data vào table chi tiết đơn hàng
         */
        int result = 1;
        try{
            Connection con = this.getConnection();
            String sql_query = "INSERT INTO EXPORT_ORD VALUES('" + id_export_ord +
                                                              "', (SELECT id_ord FROM MNG_ORDERS WHERE id_ord = '" + id_ord + "'), " +
                                                              admin_state + ", " +
                                                              warehouse_state + ", " +
                                                              date_2state_return + ")";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql_query);
            System.out.println("Insert EXPORT_ORD succeed");
        }catch(SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - insert_export_ord - EXPORT_ORD");
            result = 0;
        }
        return result;
    }
    public void update_exportord_warehouse_state(String id_ord, int warehouse_state){
        try{
            Connection con = this.getConnection();
            String sql_query = "UPDATE EXPORT_ORD\n" +
                               "SET warehouse_state = " + warehouse_state + "\n" +
                               "WHERE id_ord = '" + id_ord + "'";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql_query);
            System.out.println("Update EXPORT_ORD warehouse_state succeed");
        }catch(SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - update_exportord_warehouse_state - EXPORT_ORD");
        }
    }
    public void update_exportord_date_2state_return(String id_ord, String date_2state_return){
        try{
            Connection con = this.getConnection();
            String sql_query = "UPDATE EXPORT_ORD\n" +
                               "SET date_2state_return = '" + date_2state_return + "'\n" +
                               "WHERE id_ord = '" + id_ord + "'";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql_query);
            System.out.println("Update EXPORT_ORD date_2state_return succeed");
        }catch(SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - update_exportord_date_2state_return - EXPORT_ORD");
        }
    }
}

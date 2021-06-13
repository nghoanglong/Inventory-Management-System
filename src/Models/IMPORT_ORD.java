package Models;

import java.sql.*;
import java.util.Random;

public class IMPORT_ORD extends CONNECT_DB{
    public IMPORT_ORD() { super();}

    public IMPORT_ORD(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }

    // variables
    String id_import_ord;
    String id_ord;
    int admin_state;
    int warehouse_state;
    String date_2state_return;

    public boolean check_IDimportord(Connection con, String id_import_ord){
        boolean check = true;
        try {
            String sql_query = "SELECT * FROM IMPORT_ORD WHERE id_import_ord = ?;";
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, id_import_ord);
            ResultSet res = pstmt.executeQuery();
            if(!res.next()){
                check = false;
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - check_IDimportord - IMPORT_ORD");
        }
        return check;
    }
    public String generate_IDimportord(){
        Connection con = this.getConnection();
        Random ran_num = new Random(10004);
        String id_import_ord = "";
        while(true){
            String temp = "IMPORT_ORD" + ran_num.nextInt();
            if(!this.check_IDimportord(con, temp)){
                id_import_ord = temp;
                break;
            }
        }
        return id_import_ord;
    }

    public int insert_import_ord(String id_import_ord,
                                 String id_ord,
                                 int admin_state,
                                 int warehouse_state,
                                 String date_2state_return){
        /* insert data vào table chi tiết đơn hàng
         */
        int result = 1;
        try{
            Connection con = this.getConnection();
            String sql_query = "INSERT INTO IMPORT_ORD VALUES('" + id_import_ord +
                                                              "', (SELECT id_ord FROM MNG_ORDERS WHERE id_ord = '" + id_ord + "'), " +
                                                              admin_state + ", " +
                                                              warehouse_state + ", " +
                                                              date_2state_return + ")";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql_query);
            System.out.println("Insert IMPORT_ORD succeed");
        }catch(SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - insert_import_ord - IMPORT_ORD");
            result = 0;
        }
        return result;
    }
    public int update_importord_admin_state(String id_ord, int admin_state){
        int res = 1;
        try{
            Connection con = this.getConnection();
            String sql_query = "UPDATE IMPORT_ORD\n" +
                               "SET admin_state = " + admin_state + "\n" +
                               "WHERE id_ord = '" + id_ord + "'";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql_query);
            System.out.println("Update IMPORT_ORD admin_state succeed");
        }catch(SQLException err){
            res = 0;
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - update_importord_admin_state - IMPORT_ORD");
        }
        return res;
    }
    public int update_importord_warehouse_state(String id_ord, int warehouse_state){
        int res = 1;
        try{
            Connection con = this.getConnection();
            String sql_query = "UPDATE IMPORT_ORD\n" +
                               "SET warehouse_state = " + warehouse_state + "\n" +
                               "WHERE id_ord = '" + id_ord + "'";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql_query);
            System.out.println("Update IMPORT_ORD warehouse_state succeed");
        }catch(SQLException err){
            res = 0;
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - update_importord_warehouse_state - IMPORT_ORD");
        }
        return res;
    }
    public void update_importord_date_2state_return(String id_ord, String date_2state_return){
        try{
            Connection con = this.getConnection();
            String sql_query = "UPDATE IMPORT_ORD\n" +
                               "SET date_2state_return = '" + date_2state_return + "'\n" +
                               "WHERE id_ord = '" + id_ord + "'";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql_query);
            System.out.println("Update IMPORT_ORD date_2state_return succeed");
        }catch(SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - update_importord_date_2state_return - IMPORT_ORD");
        }
    }
}

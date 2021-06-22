package Models;

import Controllers.Statistical.REVENUE_DAY;
import Controllers.Statistical.REVENUE_MONTH;
import Controllers.Statistical.Statistical_Controller;
import org.jfree.data.xy.DefaultXYDataset;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class EXPORT_ORD extends CONNECT_DB {
    public EXPORT_ORD() { super();}

    public EXPORT_ORD(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }
    // variables
    String id_export_ord;
    String id_ord;
    int admin_state;
    int warehouse_state;
    String date_2state_return;

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
    public int update_exportord_warehouse_state(String id_ord, int warehouse_state){
        int res = 1;
        try{
            Connection con = this.getConnection();
            String sql_query = "UPDATE EXPORT_ORD\n" +
                               "SET warehouse_state = " + warehouse_state + "\n" +
                               "WHERE id_ord = '" + id_ord + "'";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql_query);
            System.out.println("Update EXPORT_ORD warehouse_state succeed");
        }catch(SQLException err){
            res = 0;
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - update_exportord_warehouse_state - EXPORT_ORD");
        }
        return res;
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

    // Get dữ liệu cho thống kê theo ngày
    public ArrayList getREVENUE_DAY(String month, String year){
        Statistical_Controller.sum_money = 0;
        ArrayList<REVENUE_DAY> li_revenue = new ArrayList<REVENUE_DAY>();
        String sql_query = "SELECT date_2state_return, SUM(CAST(PRODUCTION.price*DETAIL_ORD.num_ord AS BIGINT))\n" +
                           "FROM EXPORT_ORD JOIN MNG_ORDERS ON EXPORT_ORD.id_ord = MNG_ORDERS.id_ord\n" +
                           "JOIN DETAIL_ORD ON MNG_ORDERS.id_ord = DETAIL_ORD.id_ord\n" +
                           "JOIN PRODUCTION ON DETAIL_ORD.id_prod = PRODUCTION.id_prod\n" +
                           "WHERE MONTH(date_2state_return) = ? AND YEAR(date_2state_return) = ?\n" +
                           "GROUP BY date_2state_return\n" +
                           "ORDER BY date_2state_return ASC";
        int int_month = Integer.parseInt(month);
        int int_year = Integer.parseInt(year);
        try{
            Connection conn = this.getConnection();
            PreparedStatement pres = conn.prepareStatement(sql_query);
            pres.setInt(1,int_month);
            pres.setInt(2,int_year);
            ResultSet rs = pres.executeQuery();
            while(rs.next()){

                li_revenue.add(new REVENUE_DAY(rs.getString(1),rs.getInt(2)));
                Statistical_Controller.sum_money += rs.getInt(2);
            }
            System.out.println("Kết nối thành công - getREVENUE_DAY - REVENUE_DAY");
        }catch (SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - getREVENUE_DAY - REVENUE_DAY");
        }
        return li_revenue;
    }

    public ArrayList getREVENUE_MONTH(String year){
        Statistical_Controller.sum_money = 0;
        ArrayList<REVENUE_MONTH> li_revenue = new ArrayList<REVENUE_MONTH>();
        String sql_query = "SELECT MONTH(date_2state_return), SUM(CAST(PRODUCTION.price*DETAIL_ORD.num_ord AS BIGINT))\n" +
                "FROM EXPORT_ORD JOIN MNG_ORDERS ON EXPORT_ORD.id_ord = MNG_ORDERS.id_ord\n" +
                "JOIN DETAIL_ORD ON MNG_ORDERS.id_ord = DETAIL_ORD.id_ord\n" +
                "JOIN PRODUCTION ON DETAIL_ORD.id_prod = PRODUCTION.id_prod\n" +
                "WHERE YEAR(date_2state_return) = ?\n" +
                "GROUP BY MONTH(date_2state_return)\n" +
                "ORDER BY MONTH(date_2state_return) ASC";
        int int_year = Integer.parseInt(year);
        try{
            Connection conn = this.getConnection();
            PreparedStatement pres = conn.prepareStatement(sql_query);
            pres.setInt(1,int_year);
            ResultSet rs = pres.executeQuery();
            while(rs.next()){

                li_revenue.add(new REVENUE_MONTH(String.valueOf(rs.getInt(1)),rs.getInt(2)));
                Statistical_Controller.sum_money += rs.getInt(2);
            }
            System.out.println("Kết nối thành công - getREVENUE_MONTH - REVENUE_MONTH");
        }catch (SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - getREVENUE_MONTH - REVENUE_MONTH");
        }
        return li_revenue;
    }

}

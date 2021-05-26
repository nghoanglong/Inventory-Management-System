package Models;

import Controllers.OrderManagement.ORDER;
import Controllers.ProductManagement.SANPHAM;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class MNG_ORDERS extends CONNECT_DB{
    public MNG_ORDERS() { super();}

    public MNG_ORDERS(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }

    public ArrayList getTableORDER(boolean getstate_ord){
        ArrayList<ORDER> li_order = new ArrayList<ORDER>();
        try{
            Connection conn = this.getConnection();
            Statement stmt = conn.createStatement();
            String sql_query = "SELECT * FROM MNG_ORDERS WHERE state_prod = 1";
            ResultSet rs = stmt.executeQuery(sql_query);
            while (rs.next()){
                li_sp.add(new SANPHAM(rs.getString("id_prod"),
                        rs.getString("name_prod"),
                        rs.getString("type_prod"),
                        rs.getInt("price"),
                        rs.getInt("num_exist")));
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - getTablePRODUCTION - PRODUCTION");
        }
        return li_sp;
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

}

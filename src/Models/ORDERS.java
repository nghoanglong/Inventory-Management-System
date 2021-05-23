package Models;

import java.sql.*;
import java.util.Random;

public class ORDERS extends CONNECT_DB{
    public ORDERS() { super();}

    public ORDERS(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }
    public boolean check_IDorder(Connection con, String id_ord){
        boolean check = true;
        try {
            String sql_query = "SELECT * FROM ORDERS WHERE id_ord = ?;";
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, id_ord);
            ResultSet res = pstmt.executeQuery();
            if(!res.next()){
                check = false;
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - check_IDorder - ORDERS");
        }
        return check;
    }
    public String generate_IDorder(){
        Connection con = this.getConnection();
        Random ran_num = new Random(100000000);
        String id_ord = "";
        while(true){
            String temp = "ORDERS" + ran_num.nextInt();
            if(!this.check_IDorder(con, temp)){
                id_ord = temp;
                break;
            }
        }
        return id_ord;
    }
    public int insert_orders(String id_ord,
                             String id_user,
                             String id_cus,
                             String type_ord,
                             String date_ord){
        /* insert data vào table quản lý đơn hàng
         */
        int result = 1;
        try{
            Connection con = this.getConnection();
            String sql_query = "INSERT INTO ORDES VALUES('" + id_ord + "', " +
                                                        "(SELECT id_user FROM USERS WHERE id_user = '" + id_user + "'), " +
                                                        "(SELECT id_cus FROM CUSTOMER_INFO WHERE id_cus = '" + id_cus + "'), '" +
                                                        type_ord + "', '" +
                                                        date_ord + "');";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql_query);
            System.out.println("Insert QLDH succeed");
        }catch(SQLException err){
            System.out.println("Lỗi hệ thống - insert_orders - ORDERS");
            err.printStackTrace();
            result  = 0;
        }
        return result;
    }

}

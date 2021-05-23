package Models;

import java.sql.*;
import java.util.Random;

public class ORD_STATE extends CONNECT_DB {
    public ORD_STATE() { super();}

    public ORD_STATE(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }
    public boolean check_IDordst(Connection con, String id_ordst){
        boolean check = true;
        try {
            String sql_query = "SELECT * FROM ORD_STATE WHERE id_ordst = ?;";
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, id_ordst);
            ResultSet res = pstmt.executeQuery();
            if(!res.next()){
                check = false;
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - check_IDordst - ORD_STATE");
        }
        return check;
    }
    public String generate_IDordst(){
        Connection con = this.getConnection();
        Random ran_num = new Random(100000000);
        String id_ordst = "";
        while(true){
            String temp = "ORDST" + ran_num.nextInt();
            if(!this.check_IDordst(con, temp)){
                id_ordst = temp;
                break;
            }
        }
        return id_ordst;
    }
    public int insert_ord_state(String id_ordst,
                                String id_ord,
                                int admin_state,
                                int warehouse_mng_state,
                                String date_2state_return){
        /* insert data vào table trạng thái đơn hàng
         */
        int result = 1;
        try{
            Connection con = this.getConnection();
            String sql_query = "INSERT INTO ORD_STATE VALUES('" + id_ordst +
                                                             "', (SELECT id_ord FROM ORDERS WHERE id_ord = '" + id_ord + "'), " +
                                                             admin_state + ", " +
                                                             warehouse_mng_state + ", '" +
                                                             date_2state_return + "')";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql_query);
            System.out.println("Insert ORD_STATE succeed");
        }catch(SQLException err){
            System.out.println("Lỗi hệ thống - insert_ord_state - ORD_STATE");
            err.printStackTrace();
            result = 0;
        }
        return result;
    }
}

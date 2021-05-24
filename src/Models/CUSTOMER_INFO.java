package Models;

import java.sql.*;
import java.util.Random;

public class CUSTOMER_INFO extends CONNECT_DB {
    public CUSTOMER_INFO() { super();}

    public CUSTOMER_INFO(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }
    public boolean check_IDcus(Connection con, String id_cus){
        boolean check = true;
        try {
            String sql_query = "SELECT * FROM CUSTOMER_INFO WHERE id_cus = ?;";
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, id_cus);
            ResultSet res = pstmt.executeQuery();
            if(!res.next()){
                check = false;
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - checkIDcustomer - CUSTOMER_INFO");
        }
        return check;
    }
    public String generate_IDcus(){
        Connection con = this.getConnection();
        Random ran_num = new Random(10002);
        String id_cus = "";
        while(true){
            String temp = "CUSTOMER" + ran_num.nextInt();
            if(!this.check_IDcus(con, temp)){
                id_cus = temp;
                break;
            }
        }
        return id_cus;
    }
    public int insert_customer_info(String id_cus, String name_cus, String phone_cus, String address_cus){
         /* insert data vào table thông tin khách hàng
         */
        int result = 1;
        try{
            Connection con = this.getConnection();
            String sql_query = "INSERT INTO CUSTOMER_INFO(id_cus, name_cus, phone_cus, address_cus) VALUES(?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, id_cus);
            pstmt.setString(2, name_cus);
            pstmt.setString(3, phone_cus);
            pstmt.setString(4, address_cus);
            pstmt.executeUpdate();
            System.out.println("Insert TTKH succeed");
        }catch(SQLException err){
            System.out.println("Lỗi hệ thống - insert_customer_info - CUSTOMER_INFO");
            err.printStackTrace();
            result = 0;
        }
        return result;
    }
}

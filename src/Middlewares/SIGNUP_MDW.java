package Middlewares;

import Models.CONNECT_DB;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SIGNUP_MDW {
    public SIGNUP_MDW(){}

    public int insert_data(String username, String pwd, String age, int role_user, String email){
        /* insert data vào database
           return res = 0: insert ko thành công vì username đã exist
                      = 1: insert thành công
         */
        int result = 1;
        try{
            String sql_query = "INSERT INTO USERS \n" +
                               "VALUES('" + username + "', '" +
                               pwd + "', '" +
                               age + "', " +
                               role_user + ", '" +
                               email + "')";
            CONNECT_DB new_connect = new CONNECT_DB("DESKTOP-BHNESJS\\SQLEXPRESS",
                                                    1400,
                                                    "sa",
                                                    "1712",
                                                    "Inventory_Management_System");
            Connection con = new_connect.getConnection();
            if(LOGIN_MDW.check_username(con, username)){
                result = 0;
            } else {
                Statement stmt = con.createStatement();
                stmt.execute(sql_query);
            }
        }catch(SQLException err){
            err.printStackTrace();
        }
        return result;
    }
    public static void main(String[] args){
        SIGNUP_MDW new_signup = new SIGNUP_MDW();
        int res =new_signup.insert_data("demouser",
                                        "aaa123",
                                        "2000-08-07",
                                        2,
                                        "demouser@gmail.com");
        System.out.print(res);
    }
}

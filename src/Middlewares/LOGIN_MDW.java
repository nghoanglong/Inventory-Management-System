package Middlewares;
import Models.CONNECT_DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LOGIN_MDW {
    LOGIN_MDW(){}

    private boolean query_username(Connection con, String username){
        boolean check = true;
        try {
            String query_login = "SELECT *\n" +
                    "FROM USERS\n" +
                    "WHERE username = '" + username + "';";
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery(query_login);
            if(!res.next()){
                check = false;
            }
        }catch (SQLException err){
            err.printStackTrace();
        }
        return check;
    }
    private boolean query_pwd(Connection con, String username, String pwd){
        boolean check = true;
        try {
            String query_login = "SELECT *\n" +
                    "FROM USERS\n" +
                    "WHERE username = '" + username +
                    "' AND USERS.pwd = '" + pwd + "';";
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery(query_login);
            if(!res.next()){
                check = false;
            }
        }catch (SQLException err){
            err.printStackTrace();
        }
        return check;
    }
    public int validate_login(String user_name, String user_pwd) {
        /* Hàm validate login của user
           username: Phải ở dạng VARCHAR(100), tức length < 100
           pwd: Phải ở dạng VARCHAR(100), tức length < 100

           return 1: validate thành công
                  2: username is not exist
                  3: password is incorrect
         */
        int check = 1;
        // chỗ connect này các tham số ứng với database trên local của tui
        // tự sửa lại các tham số ở chỗ này để run thử nhe
        CONNECT_DB new_connect = new CONNECT_DB("DESKTOP-BHNESJS\\SQLEXPRESS",
                                                1400,
                                                "sa",
                                                "1712",
                                                "Inventory_Management_System");
        Connection con = new_connect.getConnection();
        if(this.query_username(con, user_name)){
            if(!this.query_pwd(con, user_name, user_pwd)){
                // username đúng, password sai
                check = 3;
            }
        }else{
            // username sai ngay từ đầu
            check = 2;
        }
        return check;
    }
    public static void main(String[] args){
        // test chức năng

        LOGIN_MDW new_check = new LOGIN_MDW();
        int res = new_check.validate_login("HOANG LONG", "123long");
        System.out.print(res);
    }
}

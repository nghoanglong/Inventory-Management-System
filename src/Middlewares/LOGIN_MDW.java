package Middlewares;
import Models.CONNECT_DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LOGIN_MDW {
    LOGIN_MDW(){}

    public int validate_login(String user_name, String user_pwd) {
        /* Hàm validate login của user
           username: Phải ở dạng VARCHAR(100), tức length < 100
           pwd: Phải ở dạng VARCHAR(100), tức length < 100

           return 0: username hoặc password nhập vào có độ dài > 100
                  1: validate thành công
                  2: password hoặc username is incorrect
         */
        int check = 1;
        if (user_name.length() > 100) {
            // trong database chỉ set username VARCHAR(100)
            check = 0;
        } else if (user_pwd.length() > 100) {
            // trong database chỉ set username VARCHAR(100)
            check = 0;
        } else {
            // chỗ connect này các tham số ứng với database trên local của tui
            // tự sửa lại các tham số ở chỗ này để run thử nhe
            CONNECT_DB new_connect = new CONNECT_DB("DESKTOP-BHNESJS\\SQLEXPRESS",
                                                    1400,
                                                    "sa",
                                                    "1712",
                                                    "Inventory_Management_System");
            Connection con = new_connect.getConnection();
            try {
                String query_login = "SELECT COUNT(*)\n" +
                                     "FROM USERS\n" +
                                     "WHERE username = '" + user_name +
                                     "' AND USERS.pwd = '" + user_pwd + "';";
                Statement stmt = con.createStatement();
                ResultSet res = stmt.executeQuery(query_login);
                if(!res.next()){
                    check = 2;
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return check;
    }
}

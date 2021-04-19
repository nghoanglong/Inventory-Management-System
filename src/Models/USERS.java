package Models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class USERS extends CONNECT_DB {
    public USERS(){
        super("DESKTOP-BHNESJS\\SQLEXPRESS",1400,"sa","1712","Inventory_Management_System");
    }

    public USERS(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }

    public static boolean check_username(Connection con, String username){
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
    public static boolean check_pwd(Connection con, String username, String pwd){
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
        Connection con = this.getConnection();
        if(USERS.check_username(con, user_name)){
            if(!USERS.check_pwd(con, user_name, user_pwd)){
                // username đúng, password sai
                check = 3;
            }
        }else{
            // username sai ngay từ đầu
            check = 2;
        }
        return check;
    }
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

            Connection con = this.getConnection();
            if(this.check_username(con, username)){
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

}

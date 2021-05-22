package Models;

import java.sql.*;
import java.util.Random;

public class ACCOUNT extends CONNECT_DB{
    // constructor
    public ACCOUNT() { super();}

    public ACCOUNT(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }

    // GET SET
    public static boolean check_IDaccount(Connection con, String id_account){
        boolean check = true;
        try {
            String query_id = "SELECT * FROM ACCOUNT WHERE id_account = ?;";
            PreparedStatement pstmt = con.prepareStatement(query_id, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, id_account);
            ResultSet res = pstmt.executeQuery();
            if(!res.next()){
                check = false;
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - check_IDaccount - ACCOUNT");
        }
        return check;
    }

    public String generate_IDaccount(){
        Connection con = this.getConnection();
        Random ran_num = new Random(10001);
        String id_account = "";
        while(true){
            String temp = "ACCOUNT" + ran_num.nextInt();
            if(!ACCOUNT.check_IDaccount(con, temp)){
                id_account = temp;
                break;
            }
        }
        return id_account;
    }

    public static boolean check_username(Connection con, String username){
        boolean check = true;
        try {
            String query_login = "SELECT * FROM ACCOUNT WHERE username = ?;";
            PreparedStatement pstmt = con.prepareStatement(query_login, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, username);
            ResultSet res = pstmt.executeQuery();
            if(!res.next()){
                check = false;
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - check_username - ACCOUNT");
        }
        return check;
    }
    public static boolean check_pwd(Connection con, String username, String pwd){
        boolean check = true;
        try {
            String query_login = "SELECT * FROM ACCOUNT WHERE pwd = ?;";
            PreparedStatement pstmt = con.prepareStatement(query_login);
            pstmt.setString(1, pwd);
            ResultSet res = pstmt.executeQuery();
            if(!res.next()){
                check = false;
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - check_pwd - ACCOUNT");
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
        Connection con = this.getConnection();
        if(ACCOUNT.check_username(con, user_name)){
            if(!ACCOUNT.check_pwd(con, user_name, user_pwd)){
                // username đúng, password sai
                check = 3;
            }
        }else{
            // username sai ngay từ đầu
            check = 2;
        }
        return check;
    }

    public int getAccountRole(String user_name){
        /* Lấy ra loại của user bất kì

           user_name: tên user

           return 0 -> user ko tồn tại
                  result = role_user

         */
        int result = 0;
        try {
            String sql_query = "SELECT * FROM ACCOUNT WHERE username = ?;";
            Connection con = this.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user_name);
            ResultSet res = pstmt.executeQuery();
            if(res.next()){
                result = res.getInt("account_role");
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - getAccountRole - ACCOUNT");
        }
        return result;
    }
    public String getIDAccout(String user_name){
        /* Lấy ra id của user bất kì

         */
        String result = null;
        try {
            String sql_query = "SELECT * FROM ACCOUNT WHERE username = ?;";
            Connection con = this.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user_name);
            ResultSet res = pstmt.executeQuery();
            if(res.next()){
                result = res.getString("id_account");
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - getIDAccount - ACCOUNT");
        }
        return result;
    }



    public int insert_account(String id_account,
                              String username,
                              String pwd,
                              int account_role){
        /* insert data vào database
           return res = 0: insert ko thành công vì username đã exist
                      = 1: insert thành công
         */

        int result = 1;
        try{
            Connection con = this.getConnection();
            if(this.check_username(con, username)){
                // username đã tồn tại
                result = 0;
            } else {
                String sql_query = "INSERT INTO ACCOUNT(id_account, " +
                                                       "username, " +
                                                       "pwd, " +
                                                       "account_role" +
                                                       ") VALUES(?, ?, ?, ?)";
                PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, id_account);
                pstmt.setString(2, username);
                pstmt.setString(3, pwd);
                pstmt.setInt(4, account_role);
                pstmt.executeUpdate();
                System.out.println("Insert ACCOUNT succeed");
            }
        }catch(SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - insert_account - ACCOUNT");
            result = 0;
        }
        return result;
    }
}

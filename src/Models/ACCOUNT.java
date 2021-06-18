package Models;

import java.sql.*;
import java.util.HashMap;
import java.util.Random;

public class ACCOUNT extends CONNECT_DB{
    // constructor
    public ACCOUNT() { super();}

    public ACCOUNT(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }

    // variables
    String id_account;
    String id_user;
    String username;
    String pwd;
    int account_role;

    // methods

    public boolean check_IDaccount(Connection con, String id_account){
        /* Support cho hàm generate ID để kiểm tra xem id_account được truyền vào
           đã tồn tại trong database hay chưa

           return true: đã tồn tại
                  false: chưa tồn tại
         */
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
        /*  Hàm generate ID cho primary key trong table account

            return id_account
         */
        Connection con = this.getConnection();
        Random ran_num = new Random(10001);
        String id_account = "";
        while(true){
            String temp = "ACCOUNT" + ran_num.nextInt();
            if(!this.check_IDaccount(con, temp)){
                id_account = temp;
                break;
            }
        }
        return id_account;
    }

    public static boolean check_username(Connection con, String username){
        /* Mỗi user chỉ có một tài khoản, được định danh bởi một username
           -> Không xảy ra việc trùng username trong database
           -> support việc kiểm tra trùng username

           return true: username đã có
                  false: username chưa có
         */
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

    public int validate_login(String user_name, String user_pwd) {
        /* Hàm validate login của user
           user_name: tài khoản
           user_pwd: mật khẩu

           return 1: validate thành công
                  0: failed
         */
        int check = 1;
        Connection con = this.getConnection();
        try {
            String query_login = "SELECT * FROM ACCOUNT WHERE username = ? AND pwd = ?;";
            PreparedStatement pstmt = con.prepareStatement(query_login);
            pstmt.setString(1, user_name);
            pstmt.setString(2, user_pwd);
            ResultSet res = pstmt.executeQuery();
            if(!res.next()){
                check = 0;
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - validate_login - ACCOUNT");
        }
        return check;
    }

    public int get_account_role(String user_name){
        /* Lấy ra role của một user

            user_name: tên tài khoản

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
            System.out.println("Lỗi hệ thống - get_account_role - ACCOUNT");
        }
        return result;
    }

    public String get_Iduser(String user_name){
        /* Lấy ra id của một user

           user_name: tên tài khoản

           return empty String -> user ko tồn tại
                  result = id_user
         */
        String result = null;
        try {
            String sql_query = "SELECT * FROM ACCOUNT WHERE username = ?;";
            Connection con = this.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user_name);
            ResultSet res = pstmt.executeQuery();
            if(res.next()){
                result = res.getString("id_user");
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - getIDUser - ACCOUNT");
        }
        return result;
    }

    public String get_password(String email){
        /* Lấy ra password của một user

           email: email user

           return empty String -> user ko tồn tại
                  pwd = password
         */
        String pwd = null;
        try{
            String sql_query = "SELECT pwd FROM ACCOUNT INNER JOIN USERS ON ACCOUNT.id_user = USERS.id_user WHERE USERS.email = ?;";
            Connection con = this.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, email);
            ResultSet res = pstmt.executeQuery();
            if(res.next()){
                pwd = res.getString("pwd");
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - get_password - ACCOUNT");
        }
        return pwd;
    }

    public String get_pwd_updatedb(String id_cur_user){
         /* Lấy ra password của user hiện tại để update

           id_cur_user: id của user hiện tại

           return empty String -> user ko tồn tại
                  pwd = password
         */
        String pwd = null;
        try{
            String sql_query = "SELECT pwd FROM ACCOUNT WHERE id_user = ?;";
            Connection con = this.getConnection();
            PreparedStatement pres = con.prepareStatement(sql_query,Statement.RETURN_GENERATED_KEYS);
            pres.setString(1,id_cur_user);
            ResultSet rs = pres.executeQuery();
            if(rs.next()){
                pwd = rs.getString("pwd");
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Lỗi hệ thống - get_pwd_updatedb - ACCOUNT");
        }
        return pwd;
    }

    public int update_account(String id_user, HashMap<String, String> infor_user){
        /*  Method để update thông tin của user

            id_user: mỗi user có một id riêng
            infor_user: ở dạng hashmap với key = tên field muốn thay đổi, ví dụ username
                                           value = giá trị mới

            return 0: update ko thành công
                   1: update thành công

         */
        int result = 1;
        try{
            Connection con = this.getConnection();
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql_query = "SELECT pwd FROM ACCOUNT WHERE id_user='"+ id_user +"';";
            ResultSet rs = stmt.executeQuery(sql_query);
            rs.first();
            rs.updateString("pwd", infor_user.get("pwd"));
            rs.updateRow();
        }catch (SQLException err){
            System.out.print("Lỗi hệ thống - update_user - USERS");
            result = 0;
        }
        return result;
    }

    public int insert_account(String id_account,
                              String id_user,
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
                String sql_query = "INSERT INTO ACCOUNT(id_account, id_user, username, pwd, account_role) VALUES(?, ?, ?, ?, ?)";
                PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, id_account);
                pstmt.setString(2, id_user);
                pstmt.setString(3, username);
                pstmt.setString(4, pwd);
                pstmt.setInt(5, account_role);
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

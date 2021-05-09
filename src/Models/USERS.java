package Models;

import java.sql.*;
import java.util.HashMap;
import java.util.Random;

public class USERS extends CONNECT_DB {
    public USERS() { super();}

    public USERS(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }

    public int getRoleUser(String user_name){
        /* Lấy ra loại của user bất kì

           user_name: tên user

           return 0 -> user ko tồn tại
                  result = role_user

         */
        int result = 0;
        try {
            String sql_query = "SELECT * FROM USERS WHERE username = ?;";
            Connection con = this.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user_name);
            ResultSet res = pstmt.executeQuery();
            if(res.next()){
                result = res.getInt("role_user");
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.print("Lỗi get role user của USERS");
        }
        return result;
    }
    public String getIdUser(String user_name){
        /* Lấy ra id của user bất kì

           user_name: tên user

           return empty String -> user ko tồn tại
                  result = id_user

         */
        String result = "";
        try {
            String sql_query = "SELECT * FROM USERS WHERE username = ?;";
            Connection con = this.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user_name);
            ResultSet res = pstmt.executeQuery();
            if(res.next()){
                result = res.getString("id_user");
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.print("Lỗi get id user của USERS");
        }
        return result;
    }

    public static boolean check_username(Connection con, String username){
        boolean check = true;
        try {
            String query_login = "SELECT * FROM USERS WHERE username = ?;";
            PreparedStatement pstmt = con.prepareStatement(query_login, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, username);
            ResultSet res = pstmt.executeQuery();
            if(!res.next()){
                check = false;
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.print("Lỗi check username của user");
        }
        return check;
    }
    public static boolean check_pwd(Connection con, String username, String pwd){
        boolean check = true;
        try {
            String query_login = "SELECT * FROM USERS WHERE pwd = ?;";
            PreparedStatement pstmt = con.prepareStatement(query_login);
            pstmt.setString(1, pwd);
            ResultSet res = pstmt.executeQuery();
            if(!res.next()){
                check = false;
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.print("Lỗi check password của user");
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

    public static boolean check_IDuser(Connection con, String id_user){
        boolean check = true;
        try {
            String query_id = "SELECT * FROM USERS WHERE id_user = ?;";
            PreparedStatement pstmt = con.prepareStatement(query_id, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, id_user);
            ResultSet res = pstmt.executeQuery();
            if(!res.next()){
                check = false;
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.print("Lỗi check id user của USERS");
        }
        return check;
    }

    public String generate_IDuser(){
        Connection con = this.getConnection();
        Random ran_num = new Random(100000000);
        String id_user = "";
        while(true){
            String temp = "USERS" + ran_num.nextInt();
            if(!USERS.check_IDuser(con, temp)){
                id_user = temp;
                break;
            }
        }
        return id_user;
    }
    public int insert_user(String id_user,
                           String fullname,
                           String username,
                           String pwd,
                           String age,
                           int role_user,
                           String email){
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
                String sql_query = "INSERT INTO USERS(id_user, " +
                                                     "fullname, " +
                                                     "username, " +
                                                     "pwd, " +
                                                     "age, " +
                                                     "role_user, " +
                                                     "email) VALUES(?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, id_user);
                pstmt.setString(2, fullname);
                pstmt.setString(3, username);
                pstmt.setString(4, pwd);
                pstmt.setString(5, age);
                pstmt.setInt(6, role_user);
                pstmt.setString(7, email);
                pstmt.executeUpdate();
            }
        }catch(SQLException err){
            err.printStackTrace();
            System.out.print("Lỗi insert thông tin user của USERS");
        }
        return result;
    }


    public int update_user(String id_user, HashMap<String, String> infor_user){
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
            String SQL_query = "SELECT * FROM USERS WHERE id_user='"+ id_user +"';";
            ResultSet rs = stmt.executeQuery(SQL_query);
            rs.first();
            for(String key: infor_user.keySet()){
                switch (key){
                    case "fullname":
                        rs.updateString(key, infor_user.get(key));
                        break;
                    case "pwd":
                        rs.updateString(key, infor_user.get(key));
                        break;
                    case "age":
                        rs.updateString(key, infor_user.get(key));
                        break;
                    case "email":
                        rs.updateString(key, infor_user.get(key));
                        break;
                }
            }
            rs.updateRow();
        }catch (SQLException err){
            System.out.print("Lỗi update thông tin user của USERS");
            result = 0;
        }
        return result;
    }

    public int delete_user(String id_user){
        /*  Method delete một user nào đó

            id_user: mỗi user có một id riêng

            return 0: delete thành công
                   1: delete thất bại do user ko tồn tại
         */
        int result = 1;
        try{
            Connection con = this.getConnection();
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String SQL_query = "SELECT * FROM USERS WHERE id_user='"+ id_user +"';";
            ResultSet rs = stmt.executeQuery(SQL_query);
            if(rs.first()) {
                rs.deleteRow();
            }else{
                result = 0;
            }
        }catch(SQLException err){
            err.printStackTrace();
            System.out.print("Lỗi delete user");
        }
        return result;
    }

    public static void main(String[] args){
        // demo chức năng
        USERS new_con = new USERS();

    }
}

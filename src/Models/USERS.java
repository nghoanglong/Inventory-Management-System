package Models;

import java.sql.*;
import java.util.HashMap;

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
            String query_login = "SELECT * FROM USERS WHERE username = ?;";
            PreparedStatement pstmt = con.prepareStatement(query_login, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, username);
            ResultSet res = pstmt.executeQuery();
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
            String query_login = "SELECT * FROM USERS WHERE pwd = ?;";
            PreparedStatement pstmt = con.prepareStatement(query_login);
            pstmt.setString(1, pwd);
            ResultSet res = pstmt.executeQuery();
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
    public int insert_user(String username, String pwd, String age, int role_user, String email){
        /* insert data vào database
           return res = 0: insert ko thành công vì username đã exist
                      = 1: insert thành công
         */
        int result = 1;
        try{
            String sql_query = "INSERT INTO USERS(username, " +
                                                 "pwd, " +
                                                 "age, " +
                                                 "role_user, " +
                                                 "email) " + "VALUES(?, ?, ?, ?, ?)";

            Connection con = this.getConnection();
            if(this.check_username(con, username)){
                // kiểm tra xem đã tồn tại user_name này hay chưa
                result = 0;
            } else {
                PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, username);
                pstmt.setString(2, pwd);
                pstmt.setString(3, age);
                pstmt.setInt(4, role_user);
                pstmt.setString(5, email);
                pstmt.executeUpdate();
            }
        }catch(SQLException err){
            err.printStackTrace();
        }
        return result;
    }


    public int update_user(int id_user, HashMap<String, String> infor_user){
        /*  Method để update thông tin của user

            id_user: mỗi user có một id riêng
            infor_user: ở dạng hashmap với key = tên field muốn thay đổi, ví dụ username
                                           value = giá trị mới

            return 0: update ko thành công do username đã tồn tại
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
                    case "username":
                        // kiểm tra xem username đó có bị trùng hay ko
                        if(!check_username(con, infor_user.get(key))) {
                            rs.updateString(key, infor_user.get(key));
                        }
                        else{
                            result = 2;
                        }
                        break;
                    case "pwd":
                        rs.updateString(key, infor_user.get(key));
                        break;
                    case "age":
                        rs.updateString(key, infor_user.get(key));
                        break;
                    case "role_user":
                        rs.updateInt(Integer.parseInt(key), Integer.parseInt(infor_user.get(key)));
                        break;
                    case "email":
                        rs.updateString(key, infor_user.get(key));
                        break;
                }
            }
            if(result != 2) {
                rs.updateRow();
            }
        }catch (SQLException err){
            err.printStackTrace();
        }
        return result;
    }

    public int delete_user(int id_user){
        /*  Method delete một user nào đó

            id_user: mỗi user có một id riêng

            return 0: delete thành công
                   1: delete thất bại
         */
        int result = 1;
        try{
            Connection con = this.getConnection();
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String SQL_query = "SELECT * FROM USERS WHERE id_user='"+ id_user +"';";
            ResultSet rs = stmt.executeQuery(SQL_query);
            if(rs.first()) {
                rs.deleteRow();
                System.out.print("Succeed delete");
            }else{
                result = 0;
            }
        }catch(SQLException err){
            err.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args){
        // demo chức năng
        HashMap<String, String> user_req = new HashMap<String, String>();
        user_req.put("username", "SON");
        USERS new_con = new USERS();
        int res = new_con.update_user(1, user_req);
        System.out.print(res);
    }
}

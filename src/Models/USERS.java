package Models;

import java.sql.*;
import java.util.HashMap;
import java.util.Random;

public class USERS extends CONNECT_DB {
    public USERS() { super();}

    public USERS(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }

    public String getIdUser(String id_account){
        /* Lấy ra id của user bất kì

           user_name: tên user

           return empty String -> user ko tồn tại
                  result = id_user

         */
        String result = null;
        try {
            String sql_query = "SELECT * FROM USERS WHERE id_account = ?;";
            Connection con = this.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, id_account);
            ResultSet res = pstmt.executeQuery();
            if(res.next()){
                result = res.getString("id_user");
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - getIDUser - USERS");
        }
        return result;
    }

    public boolean checkIDuser(Connection con, String id_user){
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
            System.out.println("Lỗi hệ thống - checkIDUser - USERS");
        }
        return check;
    }

    public String generate_IDuser(){
        Connection con = this.getConnection();
        Random ran_num = new Random(10002);
        String id_user = "";
        while(true){
            String temp = "USERS" + ran_num.nextInt();
            if(!this.checkIDuser(con, temp)){
                id_user = temp;
                break;
            }
        }
        return id_user;
    }

    public int insert_user(String id_user,
                           String id_account,
                           String fullname,
                           String age,
                           String email){
        /* insert data vào database
           return res = 0: insert ko thành công vì username đã exist
                      = 1: insert thành công
         */

        int result = 1;
        try{
            String sql_query = "INSERT INTO USERS VALUES('" + id_user +
                                                         "', (SELECT id_account FROM ACCOUNT WHERE id_account = '" +
                                                         id_account + "'), '" +
                                                         fullname + "', '" +
                                                         age + "', '" +
                                                         email + "');";
            Connection conn = this.getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql_query);
            System.out.println("Insert USERS succeed");
        }catch(SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - insert_user - USERS");
            result = 0;
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

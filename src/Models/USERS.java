package Models;

import Controllers.StaffManagement.NHANVIEN;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class USERS extends CONNECT_DB {
    public USERS() { super();}

    public USERS(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }

// Chỉnh lại file USER này vì table USER đã thay đổi nên các field nó thay đổi -> check lại các hàm
//    public ArrayList getTableUSER(){
//        ArrayList<NHANVIEN> li_user = new ArrayList<NHANVIEN>();
//        try{
//            Connection conn = this.getConnection();
//            Statement stmt = conn.createStatement();
//            String sql_query = "SELECT * FROM USERS";
//            ResultSet rs = stmt.executeQuery(sql_query);
//            while(rs.next()){
//                li_user.add(new NHANVIEN(rs.getString("id_user"),
//                                         rs.getString("fullname"),
//                                         rs.getString("dateOfBirth"),
//                                         rs.getString("email"),
//                                         rs.getString("account_role")));
//            }
//        }catch (SQLException e){
//            e.printStackTrace();
//            System.out.println("Lỗi hệ thống - getTableUSERS - USERS");
//        }
//        return li_user;
//    }

    public String getIdUser(String id_account){
        /* Lấy ra id của user bất kì

           user_name: tên user

           return empty String -> user ko tồn tại
                  result = id_user

         */
        String result = null;
        try {
            String sql_query = "SELECT USERS.id_user\n" +
                               "FROM ACCOUNT\n" +
                               "INNER JOIN USERS ON ACCOUNT.id_user = ACCOUNT.id_user\n" +
                               "WHERE ACCOUNT.id_account = '" + id_account + "'";
            Connection con = this.getConnection();
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery(sql_query);
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
        Random ran_num = new Random(10006);
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
                           String fullname,
                           String dateOfBirth,
                           String email){
        /* insert data vào database
           return res = 0: insert ko thành công vì username đã exist
                      = 1: insert thành công
         */

        int result = 1;
        try{
            String sql_query = "INSERT INTO USERS VALUES('" + id_user + "', '" +
                                                         fullname + "', '" +
                                                         dateOfBirth + "', '" +
                                                         email + "')";
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

// phần này đang sai -> cần viết lại cho trang staff

//    public int update_user(String id_user, HashMap<String, String> infor_user){
//        /*  Method để update thông tin của user
//
//            id_user: mỗi user có một id riêng
//            infor_user: ở dạng hashmap với key = tên field muốn thay đổi, ví dụ username
//                                           value = giá trị mới
//
//            return 0: update ko thành công
//                   1: update thành công
//
//         */
//        int result = 1;
//        try{
//            Connection con = this.getConnection();
//            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//            String SQL_query = "SELECT * FROM USERS WHERE id_user='"+ id_user +"';";
//            ResultSet rs = stmt.executeQuery(SQL_query);
//            rs.first();
//            for(String key: infor_user.keySet()){
//                switch (key){
//                    case "pwd":
//                        rs.updateString(key, infor_user.get(key));
//                        break;
//                    case "age":
//                        rs.updateString(key, infor_user.get(key));
//                        break;
//                    case "email":
//                        rs.updateString(key, infor_user.get(key));
//                        break;
//                }
//            }
//            rs.updateRow();
//        }catch (SQLException err){
//            System.out.print("Lỗi update thông tin user của USERS");
//            result = 0;
//        }
//        return result;
//    }
//
//    public int delete_user(String id_user){
//        /*  Method delete một user nào đó
//
//            id_user: mỗi user có một id riêng
//
//            return 0: delete thành công
//                   1: delete thất bại do user ko tồn tại
//         */
//        int result = 1;
//        try{
//            Connection con = this.getConnection();
//            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//            String SQL_query = "SELECT * FROM USERS WHERE id_user='"+ id_user +"';";
//            ResultSet rs = stmt.executeQuery(SQL_query);
//            if(rs.first()) {
//                rs.deleteRow();
//            }else{
//                result = 0;
//            }
//        }catch(SQLException err){
//            err.printStackTrace();
//            System.out.print("Lỗi delete user");
//        }
//        return result;
//    }

}

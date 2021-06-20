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
    // variables
    String id_user;
    String fullname;
    String dateOfBirth;
    String email;

    // Xu ly query data sang String
    public String role_toString(int role){
        String result = null;
        switch (role){
            case 1:
                result = "Admin";
                break;
            case 2:
                result = "Warehouse Manager";
                break;
            case 3:
                result = "Seller";
                break;
            default:
                break;
        }
        return result;
    }

    public ArrayList getTableUSER(){
        ArrayList<NHANVIEN> li_user = new ArrayList<NHANVIEN>();
        try{
            Connection conn =  this.getConnection();
            Statement stmt = conn.createStatement();
            String sql_query = "SELECT U.id_user, U.fullname, A.account_role, U.dateOfBirth, U.email FROM USERS U JOIN ACCOUNT A ON U.id_user = A.id_user WHERE A.account_role != 1";
            ResultSet rs = stmt.executeQuery(sql_query);
            while(rs.next()){
                String role = role_toString(rs.getInt(3));
                li_user.add(new NHANVIEN(rs.getString(1),
                                        rs.getString(2),
                                        role,
                                        rs.getString(4),
                                        rs.getString(5)));
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Lỗi hệ thống - getTableUSERS - USERS");
        }
        return li_user;
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
                           String email) {
        /* insert data vào database
           return res = 0: insert ko thành công vì username đã exist
                      = 1: insert thành công
         */

        int result = 1;
        try {
            String sql_query = "INSERT INTO USERS VALUES('" + id_user + "', '" +
                    fullname + "', '" +
                    dateOfBirth + "', '" +
                    email + "')";
            Connection conn = this.getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql_query);
            System.out.println("Insert USERS succeed");
        } catch (SQLException err) {
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - insert_user - USERS");
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
            System.out.print("Lỗi hệ thống - delete_user - USERS");
        }
        return result;
    }

}

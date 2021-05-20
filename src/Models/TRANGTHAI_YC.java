package Models;

import java.sql.*;
import java.util.Random;

public class TRANGTHAI_YC extends CONNECT_DB {
    public TRANGTHAI_YC() { super();}

    public TRANGTHAI_YC(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }
    public static boolean check_IDttyc(Connection con, String id_ttyc){
        boolean check = true;
        try {
            String sql_query = "SELECT * FROM TRANGTHAI_YC WHERE id_ttyc = ?;";
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, id_ttyc);
            ResultSet res = pstmt.executeQuery();
            if(!res.next()){
                check = false;
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.print("Lỗi hệ thống - check id trạng thái yêu cầu của TRANGTHAI_YC");
        }
        return check;
    }
    public String generate_IDttyc(){
        Connection con = this.getConnection();
        Random ran_num = new Random(100000000);
        String id_ttyc = "";
        while(true){
            String temp = "TTYC" + ran_num.nextInt();
            if(!TRANGTHAI_YC.check_IDttyc(con, temp)){
                id_ttyc = temp;
                break;
            }
        }
        return id_ttyc;
    }
    public int insert_ttyc(String id_ttyc,
                           String id_yc,
                           int admin_state,
                           int qlkho_state,
                           String date_return_2state){
        /* insert data vào table trạng thái yêu cầu
         */
        int result = 1;
        try{
            Connection con = this.getConnection();
            String sql_query = "INSERT INTO TRANGTHAI_YC VALUES('" + id_ttyc +
                                                                "', (SELECT id_yc FROM QLYC WHERE id_yc ='" +
                                                                id_yc + "'), " +
                                                                admin_state + ", " +
                                                                qlkho_state + ", " +
                                                                date_return_2state + ")";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql_query);
            System.out.println("Insert TRANGTHAI_YC succeed");
        }catch(SQLException err){
            System.out.println("Lỗi insert trạng thái yêu cầu của TRANGTHAI_YC");
            err.printStackTrace();
            result = 0;
        }
        return result;
    }
}

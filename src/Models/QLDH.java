package Models;

import java.sql.*;
import java.util.Random;

public class QLDH extends CONNECT_DB{
    public QLDH() { super();}

    public QLDH(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }
    public static boolean check_IDdh(Connection con, String id_dh){
        boolean check = true;
        try {
            String sql_query = "SELECT * FROM QLDH WHERE id_dh = ?;";
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, id_dh);
            ResultSet res = pstmt.executeQuery();
            if(!res.next()){
                check = false;
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.print("Lỗi check id đơn hàng của QLDH");
        }
        return check;
    }
    public String generate_IDdh(){
        Connection con = this.getConnection();
        Random ran_num = new Random(100000000);
        String id_dh = "";
        while(true){
            String temp = "QLDH" + ran_num.nextInt();
            if(!QLDH.check_IDdh(con, temp)){
                id_dh = temp;
                break;
            }
        }
        return id_dh;
    }
    public int insert_qldh(String id_dh,
                           String id_user,
                           String id_kh){
        /* insert data vào table quản lý đơn hàng
         */
        int result = 1;
        try{
            Connection con = this.getConnection();
            String sql_query = "INSERT INTO QLDH VALUES('" + id_dh + "', " +
                                                        "(SELECT id_user FROM USERS WHERE id_user = '" + id_user + "'), " +
                                                        "(SELECT id_kh FROM TTKH WHERE id_kh = '" + id_kh + "'));";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql_query);
            System.out.println("Insert QLDH succeed");
        }catch(SQLException err){
            System.out.println("Lỗi insert đơn hàng của QLDH");
            err.printStackTrace();
            result  = 0;
        }
        return result;
    }

}

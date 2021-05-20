package Models;

import Controllers.ProductManagement.SANPHAM;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class QLYC extends CONNECT_DB{

    public QLYC(){
        super();
    }

    public QLYC(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }


    public static boolean check_IDyc(Connection con, String id_yc){
        boolean check = true;
        try {
            String sql_query = "SELECT * FROM QLYC WHERE id_yc = ?;";
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, id_yc);
            ResultSet res = pstmt.executeQuery();
            if(!res.next()){
                check = false;
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.print("Lỗi hệ thống - check id yêu cầu của QLYC");
        }
        return check;
    }

    public String generate_IDyc(){
        Connection con = this.getConnection();
        Random ran_num = new Random(100000000);
        String id_yc = "";
        while(true){
            String temp = "QLYC" + ran_num.nextInt();
            if(!QLYC.check_IDyc(con, temp)){
                id_yc = temp;
                break;
            }
        }
        return id_yc;
    }

    public int insert_qlyc(String id_yc,
                           String id_dh,
                           String loai_yc,
                           String date_dh){
        /* insert data vào database


         */
        int result = 1;
        try{
            Connection con = this.getConnection();
            Statement stmt = con.createStatement();
            String sql_query = "INSERT INTO QLYC VALUES('"+ id_yc +
                                                        "', (SELECT id_dh FROM QLDH WHERE id_dh = '" + id_dh + "'), '" +
                                                        loai_yc + "', '" +
                                                        date_dh + "');";
            stmt.executeUpdate(sql_query);
            System.out.println("Insert QLYC succeed");
        }catch(SQLException err){
            err.printStackTrace();
            System.out.println("Lỗi hệ thống - insert yêu cầu của QLYC");
        }
        return result;
    }

}

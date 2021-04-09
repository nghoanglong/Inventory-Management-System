package Models;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CONNECT_QLDH_TABLE extends CONNECT_DB{

    public CONNECT_QLDH_TABLE(String user_name, String pwd, String ServerName, int PortNumber, String DatabaseName){
        /* Hàm constructor khi new ra một object connect database
           username: tên user login vào database
           pwd: password
           ServerName: Tên server MS-SQL
           PortNumber: port TCP/IP
           DatabaseName: Invetory-Management-System
        */
        SQLServerDataSource ds = this.setUpDataSource(user_name, pwd, ServerName, PortNumber, DatabaseName);
        try(Connection con = ds.getConnection()){
            this.setAll(user_name, pwd, ServerName, PortNumber, DatabaseName);
            System.out.println("Completed connect");
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    @Override
    public void ShowTable(){
        SQLServerDataSource ds = this.setUpDataSource();
        try(Connection con = ds.getConnection(); Statement stmt = con.createStatement()){
            String SQL = "SELECT * FROM QLDH;";
            ResultSet rs = stmt.executeQuery(SQL);
            while(rs.next()){
                // demo
                System.out.println(rs.getString("...") + '|' + rs.getString("..."));
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }
    public static void main(String[] args){
        CONNECT_QLDH_TABLE new_connect = new CONNECT_QLDH_TABLE("sa",
                "1712",
                "DESKTOP-BHNESJS\\SQLEXPRESS",
                1400,
                "Inventory_Management_System");
        new_connect.ShowTable();
    }
}
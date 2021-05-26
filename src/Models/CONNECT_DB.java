package Models;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;


public abstract class CONNECT_DB {
    private String ServerName;
    private int PortNumber;
    private String UserName;
    private String pwd;
    private String DatabaseName;
    private Connection database_connection;


//PortNumber: 1400
// ServerName: DESKTOP-BHNESJS\\SQLEXPRESS
// username: sa
// pwd: 1712

// ON CLOUD
//    server_name: ims-dbjava.database.windows.net
//    port: 1433
//    username: hoanglong@ims-dbjava
//    pwd: 0919610909aA

    public CONNECT_DB(){
        this.setAll("DESKTOP-BHNESJS\\\\SQLEXPRESS",
                    1400,
                    "sa",
                    "1712",
                    "Inventory_Management_System");
    }
    public CONNECT_DB(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        /* Constructor tạo object kết nối đến database
           ServerName: Mở trong MS-SQL Server, trước khi kết nối có phần Server Name bảng chọn
           PortNumber: Kết nối TCP/IP
           UserName: tên user
           pwd: password kết nối
           DatabaseName: Tên database kết nối tới
         */

        this.setAll(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }

    void setAll(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        // GET-SET
        this.UserName = UserName;
        this.pwd = pwd;
        this.ServerName = ServerName;
        this.PortNumber = PortNumber;
        this.DatabaseName = DatabaseName;
    }

    public Connection getConnection(){
        // tạo kết nối đến database

        String urlConnection = "jdbc:sqlserver://"
                + this.ServerName + ":" + this.PortNumber + ";"
                + "user=" + this.UserName + ";"
                + "password=" + this.pwd + ";"
                + "databaseName=" + this.DatabaseName + ";";
        try{
            this.database_connection = DriverManager.getConnection(urlConnection);
        }catch (SQLException err){
            System.out.println("Kết nối lỗi");
        }
        return this.database_connection;
    }
}
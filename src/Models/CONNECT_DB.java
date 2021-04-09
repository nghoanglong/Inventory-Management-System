package Models;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;


abstract class CONNECT_DB {
    private String user_name;
    private String pwd;
    private String ServerName;
    private int PortNumber;
    private String DatabaseName;

    SQLServerDataSource setUpDataSource(String user_name, String pwd, String ServerName, int PortNumber, String DatabaseName){
        // Hàm setup kết nối với MS-SQL Server khi connect với database lần đầu

        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser(user_name);
        ds.setPassword(pwd);
        ds.setServerName(ServerName);
        ds.setPortNumber(PortNumber);
        ds.setDatabaseName(DatabaseName);
        return ds;
    }

    SQLServerDataSource setUpDataSource(){
        // Hàm setup kết nối với MS-SQL Server khi đã có connect với database từ trước

        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser(this.user_name);
        ds.setPassword(this.pwd);
        ds.setServerName(this.ServerName);
        ds.setPortNumber(this.PortNumber);
        ds.setDatabaseName(this.DatabaseName);
        return ds;
    }

    void setAll(String user_name, String pwd, String ServerName, int PortNumber, String DatabaseName){
        // GET-SET
        this.user_name = user_name;
        this.pwd = pwd;
        this.ServerName = ServerName;
        this.PortNumber = PortNumber;
        this.DatabaseName = DatabaseName;
    }
    abstract void ShowTable();
}
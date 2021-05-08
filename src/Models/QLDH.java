package Models;

public class QLDH extends CONNECT_DB{
    public QLDH() { super();}

    public QLDH(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName){
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }

}

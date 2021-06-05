package Controllers.StaffManagement;

import java.util.Date;

public class NHANVIEN {
    private String id_user;
    private String fullname;
    private String account_role;
    private String dateOfBirth;
    private String email;

    public NHANVIEN(){
        this.id_user = null;
        this.fullname = null;
        this.account_role = null;
        this.dateOfBirth = null;
        this.email = null;
    }

    public NHANVIEN(String id_user, String fullname, String account_role, String dateOfBirth, String email){
        this.id_user = id_user;
        this.fullname = fullname;
        this.account_role = account_role;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
    }

    public String getId_user(){return this.id_user;}
    public String getAccount_role(){return this.account_role;}
    public String getFullname(){return this.fullname;}
    public String getDateOfBirth() { return this.dateOfBirth; }
    public String getEmail(){return this.email;}

    public void setId_user(String id_user){this.id_user = id_user;}
    public void setAccount_role(String account_role) {this.account_role = account_role;}
    public void setFullname(String fullname) {this.fullname = fullname;}
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public void setEmail(String email) { this.email = email; }
}

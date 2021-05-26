package Controllers.StaffManagement;

public class NHANVIEN {
    private String id_user;
    private String id_account;
    private String fullname;
    private String age;
    private String email;

    public NHANVIEN(){
        this.id_user = null;
        this.id_account = null;
        this.fullname = null;
        this.age = null;
        this.email = null;
    }

    public NHANVIEN(String id_user, String id_account, String fullname, String age, String email){
        this.id_user = id_user;
        this.id_account = id_account;
        this.fullname = fullname;
        this.age = age;
        this.email = email;
    }

    public String getId_user(){return this.id_user;}
    public String getId_account(){return this.id_account;}
    public String getFullname(){return this.fullname;}
    public String getAge() { return age; }
    public String getEmail(){return this.email;}

    public void setId_user(String id_user){this.id_user = id_user;}
    public void setId_account(String id_account) {this.id_account = id_account;}
    public void setFullname(String fullname) {this.fullname = fullname;}
    public void setAge(String age) { this.age = age; }
    public void setEmail(String email) { this.email = email; }
}

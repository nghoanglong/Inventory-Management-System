package Controllers.OrderManagement;

public class ORDER {
    private String id_ord;
    private String name_cus;
    private String fullname_user;
    private String type_ord;
    private String date_ord;
    private int state_ord;

    public ORDER(String id_ord, String name_cus, String fullname_user, String type_ord, String date_ord, int state_ord){
        this.id_ord = id_ord;
        this.name_cus = name_cus;
        this.fullname_user = fullname_user;
        this.type_ord = type_ord;
        this.date_ord = date_ord;
        this.state_ord = state_ord;
    }
    public ORDER(String id_ord, String name_cus, String fullname_user, String type_ord, String date_ord){
        this.id_ord = id_ord;
        this.name_cus = name_cus;
        this.fullname_user = fullname_user;
        this.type_ord = type_ord;
        this.date_ord = date_ord;
    }

    public String getId_ord(){return this.id_ord;}
    public String getName_cus(){return this.name_cus;}
    public String getFullname_user(){return this.fullname_user;}
    public String getType_ord(){return this.type_ord;}
    public String getDate_ord(){return this.date_ord;}
    public int getState_ord(){return this.state_ord;}

    public void setId_ord(String id_ord){this.id_ord = id_ord;}
    public void setName_cus(String name_cus){this.name_cus = name_cus;}
    public void setFullname_user(String fullname_user){this.fullname_user = fullname_user;}
    public void setType_ord(String type_ord){this.type_ord = type_ord;}
    public void setDate_ord(String date_ord){this.date_ord = date_ord;}
    public void setState_ord(int state_ord){this.state_ord = state_ord;}

}

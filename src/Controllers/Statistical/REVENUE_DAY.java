package Controllers.Statistical;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class REVENUE_DAY {
    private Date date_ord;
    private int sum_ord;

    public REVENUE_DAY(){}
    public REVENUE_DAY(Date date, int sum){
        this.date_ord = date;
        this.sum_ord = sum;
    }

    public void setDate_ord(Date date){
        this.date_ord = date;
    }
    public void setSum_ord(int sum){
        this.sum_ord = sum;
    }

    public Date getDate_ord(){
        return this.date_ord;
    }
    public int getSum_ord(){
        return this.sum_ord;
    }
}

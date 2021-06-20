package Controllers.Statistical;

import java.util.Date;

public class REVENUE_MONTH {
    private String month;
    private int sum;

    public REVENUE_MONTH(){}
    public REVENUE_MONTH(String month, int sum){
        this.month = month;
        this.sum = sum;
    }

    public void setDate_ord(String month){
        this.month = month;
    }
    public void setSum_ord(int sum){
        this.sum = sum;
    }

    public String getMonth(){
        return this.month;
    }
    public int getSum(){
        return this.sum;
    }
}

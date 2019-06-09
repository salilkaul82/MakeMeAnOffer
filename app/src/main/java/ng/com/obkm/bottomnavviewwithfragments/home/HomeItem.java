package ng.com.obkm.bottomnavviewwithfragments.home;

public class HomeItem {

    private String senderName;
    private String message;
    private String date;

    public String getSavings() {
        return savings;
    }

    public void setSavings(String savings) {
        this.savings = savings;
    }

    private String savings;

    public HomeItem(){

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
/*    public HomeItem(String senderName, String message) {
        this.senderName = senderName;
        this.message = message;
    }*/

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

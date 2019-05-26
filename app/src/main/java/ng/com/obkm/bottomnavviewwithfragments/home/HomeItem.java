package ng.com.obkm.bottomnavviewwithfragments.home;

public class HomeItem {

    public String senderName;
    public String message;

    public HomeItem(){

    }

    public HomeItem(String senderName, String message) {
        this.senderName = senderName;
        this.message = message;
    }

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

package ng.com.obkm.bottomnavviewwithfragments;

public class OffersData {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    private String merchantName;
    private String percentDisc;
    private String couponCode;
    private String merchantLogo;
    private String offerURL;
    private String imageURL;

    public String getImageURL() { return imageURL; }

    public void setImageURL(String imageURL) { this.imageURL = imageURL; }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getPercentDisc() {
        return percentDisc;
    }

    public void setPercentDisc(String percentDisc) {
        this.percentDisc = percentDisc;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getMerchantLogo() {
        return merchantLogo;
    }

    public void setMerchantLogo(String merchantLogo) {
        this.merchantLogo = merchantLogo;
    }

    public String getOfferURL() {
        return offerURL;
    }

    public void setOfferURL(String offerURL) {
        this.offerURL = offerURL;
    }

}

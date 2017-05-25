package company.co.kr.coupon.couponFeed;

/**
 * Created by Dongjin on 2017. 5. 25..
 */

public class Coupon {
    String shop_id;
    String img_src;
    int point;

    public String getShopId() {
        return shop_id;
    }

    public void setShopId(String shopId) {
        this.shop_id = shopId;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getImg_src() {
        return img_src;
    }

    public void setImg_src(String img_src) {
        this.img_src = img_src;
    }
}

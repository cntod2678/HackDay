package company.co.kr.coupon.couponFeed;

/**
 * Created by Dongjin on 2017. 5. 25..
 */

public class Coupon {
    String shop_id;
    String image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String img_src) {
        this.image = img_src;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "shop_id='" + shop_id + '\'' +
                ", image='" + image + '\'' +
                ", point=" + point +
                '}';
    }
}

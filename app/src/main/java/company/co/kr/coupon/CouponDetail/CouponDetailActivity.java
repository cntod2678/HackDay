package company.co.kr.coupon.couponDetail;

/**
 * Created by Dongjin on 2017. 5. 25..
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import company.co.kr.coupon.Application;
import company.co.kr.coupon.R;
import company.co.kr.coupon.couponFeed.Coupon;

public class CouponDetailActivity extends AppCompatActivity {
    private final String IMAGE_URL = Application.URL + "/";

    ImageView imgCouponDetail;
    TextView txtShop_id, txtCoupon_point;

    Coupon coupon = new Coupon();
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_detail);

        Intent intent = getIntent();
        bundle = intent.getBundleExtra("coupon_info");
        setCoupon(bundle);

        initView();
        setView();
    }

    private void initView() {
        imgCouponDetail = (ImageView) findViewById(R.id.imgCouponDetail);
        txtCoupon_point = (TextView) findViewById(R.id.txtCoupon_point);
        txtShop_id = (TextView) findViewById(R.id.txtShop_id);
    }

    private void setView() {
        Glide
                .with(this)
                .load(IMAGE_URL)
                .thumbnail(0.1f)
                .into(imgCouponDetail);

        txtShop_id.setText(coupon.getShopId());
        txtCoupon_point.setText(coupon.getPoint());
    }

    private void setCoupon(Bundle bundle) {
        coupon.setShopId(bundle.getString("shop_id"));
        coupon.setPoint(bundle.getInt("point"));
        coupon.setImage(bundle.getString("image"));
    }
}

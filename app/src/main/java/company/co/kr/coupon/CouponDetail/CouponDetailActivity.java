package company.co.kr.coupon.couponDetail;

/**
 * Created by Dongjin on 2017. 5. 25..
 */
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.util.HashMap;

import company.co.kr.coupon.Application;
import company.co.kr.coupon.R;
import company.co.kr.coupon.couponFeed.Coupon;
import company.co.kr.coupon.network.JSONParser;

public class CouponDetailActivity extends AppCompatActivity {
    private static final String myURL = Application.URL + "/point_check/";
    private static final String IMAGE_URL = Application.URL + "/";

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

    @Override
    protected void onResume() {
        try {
            JSONObject jobj = new reFresh().execute(bundle.getString("uid"), bundle.getString("shop_id")).get();
            coupon.setPoint(jobj.getInt("point"));

        } catch(Exception e) {
            e.printStackTrace();
        }

        super.onResume();
    }

    private void initView() {
        imgCouponDetail = (ImageView) findViewById(R.id.imgCouponDetail);
        txtCoupon_point = (TextView) findViewById(R.id.txtCoupon_point);
        txtShop_id = (TextView) findViewById(R.id.txtShop_id);
    }

    private void setView() {

        txtShop_id.setText(coupon.getShopId());
        txtCoupon_point.setText(Integer.toString(coupon.getPoint()));

        Glide
                .with(this)
                .load(IMAGE_URL+coupon.getImage())
                .centerCrop()
                .thumbnail(0.1f)
                .into(imgCouponDetail);
    }

    private void setCoupon(Bundle bundle) {
        coupon.setShopId(bundle.getString("shop_id"));
        coupon.setPoint(bundle.getInt("point"));
        coupon.setImage(bundle.getString("image"));
    }



    // myAward 부분을 서버에서 JSONObject 형식으로 가지고 옴
    private class reFresh extends AsyncTask<String, String, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected JSONObject doInBackground(String... args) {
            try {
                HashMap<String, String> params = new HashMap<>();
                params.put("uid", args[0]);
                params.put("shop_id", args[1]);


                JSONObject result = jsonParser.makeHttpRequest(
                        myURL, "GET", params);

                if (result != null) {
                    return result;
                } else {
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(JSONObject jObj) {

            super.onPostExecute(jObj);
        }
    } /* list 통신을 위한 AsyncTask 끝 */
}

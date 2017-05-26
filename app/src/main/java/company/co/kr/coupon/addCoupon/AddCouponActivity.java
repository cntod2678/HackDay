package company.co.kr.coupon.addCoupon;

/**
 * Created by Dongjin on 2017. 5. 26..
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import company.co.kr.coupon.Application;
import company.co.kr.coupon.R;

public class AddCouponActivity extends AppCompatActivity {

    private final String ADD_URL = Application.URL + "";

    WebView webView_add;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coupon);

        initView();
        goPage();
    }

    private void initView() {
        webView_add = (WebView) findViewById(R.id.webView_add);
    }

    private void goPage() {
        webView_add.setWebViewClient(new WebViewClient());
        webView_add.loadUrl(ADD_URL);
    }
}

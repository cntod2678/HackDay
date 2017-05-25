package company.co.kr.coupon;

/**
 * Created by Dongjin on 2017. 5. 25..
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AdminActivity extends AppCompatActivity {
    final String ADMIN_URL = Application.URL;

    WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        initView();
        goPage();
    }

    private void initView() {
        webView = (WebView) findViewById(R.id.webView);
    }

    private void goPage() {
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(ADMIN_URL);
    }
}

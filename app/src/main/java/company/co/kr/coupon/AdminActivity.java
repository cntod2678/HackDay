package company.co.kr.coupon;

/**
 * Created by Dongjin on 2017. 5. 25..
 */

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {
    final String ADMIN_URL = Application.URL;

    WebView webView;
    private boolean mFlag = false;

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



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }

        //백할 페이지가 없다면
        if ((keyCode == KeyEvent.KEYCODE_BACK) && (webView.canGoBack() == false)) {

             // 뒤로 버튼을 눌렀을때 해야할 행동
            if (!mFlag) {
                Toast.makeText(this, "'뒤로' 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
                mFlag = true;
                mKillHandler.sendEmptyMessageDelayed(0, 2000);
                return false;
            } else {
                android.os.Process.killProcess(android.os.Process.myPid());
            }

        }

        return super.onKeyDown(keyCode, event);
    }


    // 종료버튼이 한번 더 눌리지 않으면 mFlag 값 복원한다 */
    Handler mKillHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                mFlag = false;
            }
        }
    };

}

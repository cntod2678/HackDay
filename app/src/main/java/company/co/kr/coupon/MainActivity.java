package company.co.kr.coupon;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import company.co.kr.coupon.couponFeed.CouponFeedFragment;

public class MainActivity extends AppCompatActivity {

    private TabLayout main_bottom_tabLayout;
    private boolean mFlag = false;

    CouponFeedFragment mCouponFeedFragment = new CouponFeedFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_fragment, mCouponFeedFragment);
            transaction.commit();
        }

    }

    private void initView() {

    }
}

package company.co.kr.coupon;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import company.co.kr.coupon.addCoupon.AddCouponActivity;
import company.co.kr.coupon.couponFeed.CouponFeedFragment;

public class MainActivity extends AppCompatActivity {

    private TabLayout main_bottom_tabLayout;
    CouponFeedFragment mCouponFeedFragment = new CouponFeedFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_fragment, mCouponFeedFragment);
            transaction.commit();
        }

        initView();
        setEvent();
    }

    private void initView() {
        main_bottom_tabLayout = (TabLayout) findViewById(R.id.main_bottom_tabLayout);
    }

    private void setEvent() {
        main_bottom_tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            int pre = 0;

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // 홈 버튼 눌렀을 시 다른 탭 아이템 선택해제 되는 것을 방지하기 위해 이전에 선택되어 있던 탭 position 기억
                pre = tab.getPosition();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0) {
                    //두번 눌렀을 시 scroll 가장 상단으로 이동
                    CouponFeedFragment.moveScroll();
                }
                else if(tab.getPosition() == 1) {

                    Intent intent = new Intent(getApplicationContext(), AddCouponActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

}

package company.co.kr.coupon;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import company.co.kr.coupon.addCoupon.AddCouponActivity;
import company.co.kr.coupon.couponFeed.CouponFeedFragment;

public class MainActivity extends AppCompatActivity {

    private boolean mFlag = false;

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
                    // 두번 째 탭 클릭시 쿠폰 추가 화면 이동
                    Intent intent = new Intent(getApplicationContext(), AddCouponActivity.class);
                    startActivity(intent);
                }
            }
        });
    }


    /* 액티비티 종료시 더블 더블터치 필요 */
    @Override
    public boolean onKeyDown ( int KeyCode, KeyEvent event )
    {
        if ( event.getAction() == KeyEvent.ACTION_DOWN )
        {
            int depth = getSupportFragmentManager().getBackStackEntryCount();
            if( KeyCode == KeyEvent.KEYCODE_BACK && depth == 0)
            {
                /* 뒤로 버튼을 눌렀을때 해야할 행동 */
                if(!mFlag)
                {
                    Toast.makeText(MainActivity.this, "'뒤로' 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
                    mFlag = true;
                    mKillHandler.sendEmptyMessageDelayed(0, 2000);
                    return false;
                }
                else
                {
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            }

        }

        return super.onKeyDown(KeyCode, event);
    }

    /* 종료버튼이 한번 더 눌리지 않으면 mFlag 값 복원한다 */
    Handler mKillHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if(msg.what == 0)
            {
                mFlag = false;
            }
        }
    };

}

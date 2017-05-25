package company.co.kr.coupon.couponFeed;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import company.co.kr.coupon.R;

/**
 * Created by Dongjin on 2017. 5. 25..
 */

public class CouponFeedFragment extends Fragment implements View.OnTouchListener {

    private static RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(savedInstanceState == null) {
            View view = inflater.inflate(R.layout.fragment_coupon_list, container, false);
            initView();

        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        refreshItems();
        setEvent();
    }


    private void initView() {

    }

    private void setEvent() {

    }

    private void refreshItems() {

    }


    // View.OnTouchEventListener
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //todo CouponFeedFragment onTouch

        if (event.getAction() == MotionEvent.ACTION_UP){
            mRecyclerView.requestDisallowInterceptTouchEvent(false);
        }
        else {
            mRecyclerView.requestDisallowInterceptTouchEvent(true);
        }
        return false;
    }

}

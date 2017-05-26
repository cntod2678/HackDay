package company.co.kr.coupon.couponFeed;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import company.co.kr.coupon.Application;
import company.co.kr.coupon.R;
import company.co.kr.coupon.network.JSONParser;


/**
 * Created by Dongjin on 2017. 5. 25..
 */

public class CouponFeedFragment extends Fragment implements View.OnTouchListener {

    private final String COUPON_URL = Application.URL + "/user/shop_list/";


    private LinearLayoutManager mLinearLayoutManager;
    static private RecyclerView mRecyclerView;
    private CouponFeedAdapter recycler_adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private int start = 0;
    private int end = 5;
    boolean loadingMore = true;

    protected List<Coupon> couponArrayList = new ArrayList<>();
    private JSONObject coupon_json = new JSONObject();

    String firstCoupon;
    String uid = "mango";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getActivity().getIntent();
        uid = intent.getStringExtra("uid");

        String strStart = Integer.toString(start);
        String strEnd = Integer.toString(end);

        try {
            coupon_json = new GetCouponList().execute(uid, strStart, strEnd).get();
            Log.d("coupon", "first" + coupon_json.toString());

            firstCoupon = coupon_json.getString("shop_list");

        } catch(Exception e) {
            Log.i("coupon", "create, 첫 데이터 에러");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(savedInstanceState == null) {
            View view = inflater.inflate(R.layout.fragment_coupon_list, container, false);
            initView(view);
            return view;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recycler_adapter = new CouponFeedAdapter(getContext(), couponArrayList);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(recycler_adapter);

        refreshItems();
        setEvent();
    }


    // 뷰를 연결시킨다.
    private void initView(View v) {
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView_couponList);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefresh_couponList);
    }


    // 이벤트 연결시켜준다
    private void setEvent(){

        // 새롭게 리프레시 시키는 버튼 이벤트
       mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }

        });

        // 스크롤 이벤트 : 페이징을 위해 필요하다
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private static final int HIDE_THRESHOLD = 20;
            private int scrolledDistance = 0;
            private boolean controlsVisible = true;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                /* 마지막 아이템의 위치 계산해서 계속 paging */
                super.onScrolled(recyclerView, dx, dy);

                int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();

                //show views if first item is first visible position and views are hidden
                if (firstVisibleItem == 0) {
                    if (!controlsVisible) {
                        controlsVisible = true;
                    }
                } else {
                    if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
                        controlsVisible = false;
                        scrolledDistance = 0;
                    } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
                        controlsVisible = true;
                        scrolledDistance = 0;
                    }

                    if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
                        scrolledDistance += dy;
                    }
                }

                if (dy > 0) {
                    if (loadingMore) {
                        if ((visibleItemCount + firstVisibleItem) >= totalItemCount) {
                            // Paging
                            try {
                                start += 5;
                                end += 5;

                                String strStart = Integer.toString(start);
                                String strEnd = Integer.toString(end);

                                coupon_json = new GetCouponList().execute(uid,
                                        strStart, strEnd).get();

                                String response = coupon_json.getString("shop_list");
                                Log.d("coupon", Integer.toString(start) + ": initial : " + response);
                                loadList(response);
                            } catch (Exception e) {
                                Log.e("coupon", "error : Coupon, onScroll, 결과 값 받아오는 에러");
                                e.printStackTrace();
                            }

                        }
                    }
                }

            }
        });
    } // !setEvent 끝


    // swipe 를 통해 view를 refresh 시킨다.
    private void refreshItems() {
        couponArrayList.clear();
        loadingMore = true;

        start = 0;
        end = 5;

        try {
            coupon_json = new GetCouponList().execute(uid, Integer.toString(start), Integer.toString(end)).get();
            Log.d("coupon", "first" + coupon_json.toString());
            firstCoupon = coupon_json.getString("shop_list");

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Load complete
        onItemsLoadComplete();
    }

    private void onItemsLoadComplete() {
        // adapter 업데이트
        loadList(firstCoupon);
        recycler_adapter.notifyDataSetChanged();

        //  refresh 애니메이션 끝
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void loadList(String json_result) {
        // 통신 후 받은 결과값을 객체로 변환 후 list로 뿌려주는 함수

        Log.d("coupon", "loadList : " + json_result);
        Gson gson = new Gson();
        try {
            JSONArray coupon_jsonArray = new JSONArray(json_result);

            if(coupon_jsonArray.length() == 0) {
                start -= 5;
                end -= 5;
                Log.d("coupon", "loadList : 비었다");
                loadingMore = false;
            }
            else {
                int arrNum = couponArrayList.size();
                int coupon_jsonArrayLength = coupon_jsonArray.length();

                for(int i = 0; i < coupon_jsonArrayLength; i++) {
                    String couponInfo = coupon_jsonArray.getJSONObject(i).toString();
                    Coupon coupon = gson.fromJson(couponInfo, Coupon.class);

                    Log.d("coupon", "to String : " + coupon.toString());
                    couponArrayList.add(coupon);
                }

//                recycler_adapter.notifyDataSetChanged();
                recycler_adapter.notifyItemRangeInserted(arrNum, coupon_jsonArrayLength);
            }

        } catch(Exception e) {
            Log.e("coupon", "loadList : " + "로딩 에러");
        }
    }

    public static void moveScroll() {
        mRecyclerView.smoothScrollToPosition(0);
    }


    // View.OnTouchEventListener,
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


    // myAward 부분을 서버에서 JSONObject 형식으로 가지고 옴
    private class GetCouponList extends AsyncTask<String, String, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Coupon 리스트를 가져오는 중 입니다.");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            try {
                HashMap<String, String> params = new HashMap<>();
                params.put("uid", args[0]);
                params.put("start", args[1]);
                params.put("end", args[2]);

                JSONObject result = jsonParser.makeHttpRequest(
                        COUPON_URL, "GET", params);

                if (result != null) {
                    Log.d("coupon", "doInBackground : " + result);
                    return result;
                } else {
                    Log.d("coupon", "doInBackground : result, null");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(JSONObject jObj) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            super.onPostExecute(jObj);
        }
    } /* list 통신을 위한 AsyncTask 끝 */
}

//package company.co.kr.coupon.couponFeed;
//
//import android.content.Context;
//import android.support.v4.util.Pools;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AbsListView;
//
//import android.support.v7.widget.CardView;
//import android.support.v7.widget.RecyclerView;
//
//import java.util.ArrayList;
//
///**
// * Created by Dongjin on 2017. 5. 25..
// */
//
//public class CouponFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    private static final int MAX_POOL_SIZE = 10;
//
//    private Context mContext;
//    private Pools.SimplePool<View> mMyViewPool;
//
//    private ArrayList<Coupon> coupon_list;
//
//    public CouponFeedAdapter() {
//
//    }
//
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder) {
//
//    }
//
//    /* item count */
//    public int getBasicItemCount() {
//        return coupon_list == null ? 0 : coupon_list.size();
//    }
//
//    @Override
//    public int getItemCount() {
//        return getBasicItemCount() + 1;
//    }
//
//    /* Returns viewType for a given position (Header or Item) */
//    @Override
//    public int getItemViewType(int position) {
//        if (isPositionHeader(position)) {
//            return TYPE_HEADER;
//        }
//        return TYPE_ITEM;
//    }
//
//}

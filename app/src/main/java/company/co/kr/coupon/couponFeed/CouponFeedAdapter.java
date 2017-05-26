package company.co.kr.coupon.couponFeed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import company.co.kr.coupon.Application;
import company.co.kr.coupon.couponDetail.CouponDetailActivity;
import company.co.kr.coupon.R;

/**
 * Created by Dongjin on 2017. 5. 25..
 */

public class CouponFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String IMAGE_URL = Application.URL + "/";

    private Context mContext;

    int TYPE_ITEM = 1;
    int TYPE_HEADER = 2;

    private List<Coupon> coupon_list = new ArrayList<>();

    public CouponFeedAdapter(Context context, List<Coupon> cList) {
        mContext = context;
        coupon_list = cList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coupon_feed, parent, false);
        return new CouponViewHolder(v);
    }

    // 아이템을 위한 ViewHolder
    public static class CouponViewHolder extends RecyclerView.ViewHolder {
        /* Initiate each data item */
        TextView txtShop_id;
        ImageView imgCoupon;
        CardView item_card;

        public CouponViewHolder(View view) {
            super(view);
            item_card = (CardView) view.findViewById(R.id.item_card);
            imgCoupon = (ImageView)  view.findViewById(R.id.imgCoupon);
            txtShop_id = (TextView) view.findViewById(R.id.txtShop_id);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(position != 0) {
            final Coupon coupon = coupon_list.get(position-1);

            CouponViewHolder vh = (CouponViewHolder) viewHolder;
            vh.txtShop_id.setText(coupon.getShopId());

            Log.d("img", IMAGE_URL + coupon.getImage());

            Glide
                    .with(mContext)
                    .load(IMAGE_URL + coupon.getImage())
                    .centerCrop()
                    .thumbnail(0.1f)
                    .into(vh.imgCoupon);

            // 각각의 아이템을 클릭했을 시 Detail로 넘어간다
            vh.item_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, CouponDetailActivity.class);

                    Bundle couponBundle = new Bundle();

                    couponBundle.putString("shop_id", coupon.getShopId());
                    couponBundle.putInt("point", coupon.getPoint());
                    couponBundle.putString("image", coupon.getImage());

                    intent.putExtra("coupon_info", couponBundle);
                    mContext.startActivity(intent);
                }
            });

        }
    }

    public int getBasicItemCount() {
        return coupon_list == null ? 0 : coupon_list.size();
    }

    @Override
    public int getItemCount() {
        return getBasicItemCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }
}

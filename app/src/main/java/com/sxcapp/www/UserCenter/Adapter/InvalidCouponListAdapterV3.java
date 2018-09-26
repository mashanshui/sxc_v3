package com.sxcapp.www.UserCenter.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.Bean.CouponBeanV3;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wenleitao on 2018/4/4.
 */

public class InvalidCouponListAdapterV3 extends BaseAdapter {
    private Context context;
    private List<CouponBeanV3> list;

    public InvalidCouponListAdapterV3(Context context, List<CouponBeanV3> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size() > 0 ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CouponViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.invalid_coupon_item_v3, null);
            holder = new CouponViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CouponViewHolder) convertView.getTag();
        }
        holder.coupon_cut_tv.setText(list.get(position).getCoupon_cut());

        if (list.get(position).getCoupon_type() == 0) {
            holder.coupon_type_tv.setText("任意型");
        } else {
            holder.coupon_type_tv.setText("满减券");
        }
        holder.coupon_title_tv.setText("使用条件:" + list.get(position).getCoupon_title());
        holder.coupon_termtime_tv.setText("有效期至:" + list.get(position).getCoupon_termtime());
        holder.coupon_type_remark_tv.setText(list.get(position).getCoupon_type_remark());
        if ("1".equals(list.get(position).getCoupon_isuse())) {
            holder.status_iv.setBackgroundResource(R.mipmap.used_corner_mask);
        } else {
            holder.status_iv.setBackgroundResource(R.mipmap.coupon_overdul_v3);
        }
        return convertView;
    }

    static class CouponViewHolder {
        @BindView(R.id.coupon_cut_tv)
        TextView coupon_cut_tv;
        @BindView(R.id.coupon_type_tv)
        TextView coupon_type_tv;
        @BindView(R.id.coupon_title_tv)
        TextView coupon_title_tv;
        @BindView(R.id.coupon_termtime_tv)
        TextView coupon_termtime_tv;
        @BindView(R.id.coupon_type_remark_tv)
        TextView coupon_type_remark_tv;
        @BindView(R.id.status_iv)
        ImageView status_iv;

        public CouponViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

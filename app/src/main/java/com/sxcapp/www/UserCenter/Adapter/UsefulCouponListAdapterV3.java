package com.sxcapp.www.UserCenter.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.Bean.CouponBeanV3;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wenleitao on 2018/4/4.
 */

public class UsefulCouponListAdapterV3 extends BaseAdapter {
    private Context context;
    private List<CouponBeanV3> list;

    public UsefulCouponListAdapterV3(Context context, List<CouponBeanV3> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.useful_coupon_item_v3, null);
            holder = new CouponViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CouponViewHolder) convertView.getTag();
        }
        holder.coupon_cut_tv.setText(list.get(position).getCoupon_cut());
//        优惠券类型 0无限制 1电分 2电长 3油分 4油长 5豪
//        switch (list.get(position).getCoupon_type()) {
//            case 0:
//                holder.coupon_type_tv.setText("任意类型车辆可以使用");
//                break;
//            case 1:
//                holder.coupon_type_tv.setText("仅限电车分时可以使用");
//                break;
//            case 2:
//                holder.coupon_type_tv.setText("仅限电车长租可以使用");
//                break;
//            case 3:
//                holder.coupon_type_tv.setText("仅限油车分时可以使用");
//                break;
//            case 4:
//                holder.coupon_type_tv.setText("仅限油车长租可以使用");
//                break;
//            case 5:
//                holder.coupon_type_tv.setText("仅限豪车可以使用");
//                break;
//        }
        if (list.get(position).getCoupon_type() == 0) {
            holder.coupon_type_tv.setText("任意型");
        } else {
            holder.coupon_type_tv.setText("满减券");
        }
        holder.coupon_title_tv.setText("使用条件:" + list.get(position).getCoupon_title());
        holder.coupon_termtime_tv.setText("有效期至:" + list.get(position).getCoupon_termtime());
        holder.coupon_type_remark_tv.setText(list.get(position).getCoupon_type_remark());
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

        public CouponViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

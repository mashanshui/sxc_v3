package com.sxcapp.www.UserCenter.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.Bean.NewCouponBean;

import java.util.List;

/**
 * Created by wenleitao on 2018/3/14.
 */

public class NewCouponListAdapter extends BaseAdapter {
    private Context context;
    private List<NewCouponBean> list;

    public NewCouponListAdapter(Context context, List<NewCouponBean> list) {
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
        MyHolder myHolder;
        if (convertView == null) {
            myHolder = new MyHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.new_coupon_item, null);
            myHolder.area_tv = (TextView) convertView.findViewById(R.id.area_tv);
            myHolder.limit_time_tv = (TextView) convertView.findViewById(R.id.limit_time_tv);
            myHolder.coupon_iv = (ImageView) convertView.findViewById(R.id.coupon_iv);
            myHolder.discount_tv = (TextView) convertView.findViewById(R.id.discount_tv);
            convertView.setTag(myHolder);
        } else {
            myHolder = (MyHolder) convertView.getTag();
        }
        switch (list.get(position).getCoupon_type()) {
            case "2":
                myHolder.area_tv.setText("使用说明: 限合肥地区,分时租还车时自动使用");
                break;
            case "3":
                myHolder.area_tv.setText("使用说明: 限合肥地区,天天租还车时自动使用");
                break;
            case "4":
                myHolder.area_tv.setText("使用说明: 限天津地区,分时租还车时自动使用");
                break;
            case "5":
                myHolder.area_tv.setText("使用说明: 限天津地区,天天租还车时自动使用");
                break;
            case "6":
                myHolder.area_tv.setText("使用说明: 限内蒙地区,分时租还车时自动使用");
                break;
            case "8":
                myHolder.area_tv.setText("使用说明: 限内蒙地区,长租还车时自动使用");
                break;
            default:
                break;

        }
        myHolder.limit_time_tv.setText("截止日期:" + list.get(position).getTerm_time());
        myHolder.discount_tv.setText(list.get(position).getCoupon_title());
        if ("1".equals(list.get(position).getUse_type())) {
            myHolder.coupon_iv.setBackgroundResource(R.mipmap.new_coupon_unuse);
        } else if ("2".equals(list.get(position).getUse_type())) {
            myHolder.coupon_iv.setBackgroundResource(R.mipmap.new_coupon_used);
        } else if ("3".equals(list.get(position).getUse_type())) {
            myHolder.coupon_iv.setBackgroundResource(R.mipmap.new_coupon_overdue);
        }
        return convertView;
    }

    class MyHolder {
        TextView limit_time_tv, area_tv, discount_tv;
        ImageView coupon_iv;

    }
}

package com.sxcapp.www.UserCenter.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.Bean.OilShareInDayInfo;
import com.sxcapp.www.Util.Properties;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wenleitao on 2017/9/8.
 */

public class OilShareInDayAdapter extends BaseAdapter {
    private Context context;
    private List<OilShareInDayInfo.DataBean> list;

    public OilShareInDayAdapter(Context context, List<OilShareInDayInfo.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    public void loadMore(List<OilShareInDayInfo.DataBean> data) {
        list.addAll(data);
        notifyDataSetChanged();
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
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.lease_order_item, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        OilShareInDayInfo.DataBean.ShareCarInfoBean bean = list.get(position).getShareCarInfo();
        OilShareInDayInfo.DataBean.OrderInfoBean orderInfoBean = list.get(position).getOrderInfo();
        Glide.with(context).load(Properties.baseImageUrl + bean.getShow_image()).into(holder.car_iv);
        holder.car_name_tv.setText(bean.getBrand_code());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(orderInfoBean.getOrder_time());//获取当前时间
        String str = formatter.format(curDate);
        holder.lease_date_tv.setText(str);
        holder.order_no_tv.setText(orderInfoBean.getOrder_no());
        holder.car_info_tv.setText(bean.getGearbox_type() + "|乘坐" + bean.getNumber_seats() + "人");
        if (orderInfoBean.getIs_exception() == 0) {
            if ("0".equals(orderInfoBean.getOrder_state())) {
                holder.order_status_tv.setText("待取车");
            } else if ("1".equals(orderInfoBean.getOrder_state())) {
                holder.order_status_tv.setText("使用中");
            } else if ("2".equals(orderInfoBean.getOrder_state())) {
                holder.order_status_tv.setText("已还车");
            } else if ("4".equals(orderInfoBean.getOrder_state())) {
                holder.order_status_tv.setText("已取消");
            }
        } else {
            holder.order_status_tv.setText("非正常还车");
        }
        return convertView;
    }


    static class Holder {
        @BindView(R.id.car_iv)
        ImageView car_iv;
        @BindView(R.id.car_name_tv)
        TextView car_name_tv;
        @BindView(R.id.car_info_tv)
        TextView car_info_tv;
        @BindView(R.id.order_no_tv)
        TextView order_no_tv;
        @BindView(R.id.order_status_tv)
        TextView order_status_tv;
        @BindView(R.id.lease_date_tv)
        TextView lease_date_tv;

        public Holder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}

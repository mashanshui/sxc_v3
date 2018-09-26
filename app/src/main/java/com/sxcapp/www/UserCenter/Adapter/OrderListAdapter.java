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
import com.sxcapp.www.UserCenter.Bean.OrderListBean;
import com.sxcapp.www.Util.Properties;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by wenleitao on 2017/7/26.
 */

public class OrderListAdapter extends BaseAdapter {
    private Context context;
    private List<OrderListBean> list;

    public OrderListAdapter(Context context, List<OrderListBean> list) {
        this.context = context;
        this.list = list;
    }

    public void loadMore(List<OrderListBean> data) {
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
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(R.layout.lease_order_item, null);
            holder.car_info_tv = (TextView) convertView.findViewById(R.id.car_info_tv);
            holder.car_name_tv = (TextView) convertView.findViewById(R.id.car_name_tv);
            holder.lease_date_tv = (TextView) convertView.findViewById(R.id.lease_date_tv);
            holder.order_no_tv = (TextView) convertView.findViewById(R.id.order_no_tv);
            holder.order_status_tv = (TextView) convertView.findViewById(R.id.order_status_tv);
            holder.car_iv = (ImageView) convertView.findViewById(R.id.car_iv);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        Glide.with(context).load(Properties.baseImageUrl + list.get(position).getImagePath()).into(holder.car_iv);
        holder.car_name_tv.setText(list.get(position).getSerise_name() + list.get(position).getModel_name());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(list.get(position).getOrder_time());//获取当前时间
        String str = formatter.format(curDate);
        holder.lease_date_tv.setText(str);
        holder.order_no_tv.setText(list.get(position).getOrder_no());
        holder.car_info_tv.setText(list.get(position).getDisplacement() + list.get(position).getGearbox_type() + "|乘坐" + list.get(position).getNumberSeats() + "人");
        OrderListBean bean = list.get(position);
        if (bean.getPay_status() == 0) {
            if (bean.getOrder_state() == 11) {
                holder.order_status_tv.setText("交易关闭");
            } else {
                holder.order_status_tv.setText("租金未支付");
            }
        } else {
            if (bean.getDeposit_state() == 0) {
                holder.order_status_tv.setText("押金未支付");
            } else {
                if (bean.getOrder_state() == 0) {
                    holder.order_status_tv.setText("待门店审核");
                } else if (bean.getOrder_state() == 1) {
                    holder.order_status_tv.setText("待平台审核");
                } else if (bean.getOrder_state() == 2) {
                    holder.order_status_tv.setText("待确认车型");
                } else if (bean.getOrder_state() == 3) {
                    holder.order_status_tv.setText("待签合同");
                } else if (bean.getOrder_state() == 4) {
                    holder.order_status_tv.setText("合同待审核");
                } else if (bean.getOrder_state() == 5) {
                    holder.order_status_tv.setText("待财务审核");
                } else if (bean.getOrder_state() == 6) {
                    holder.order_status_tv.setText("待取车");
                } else if (bean.getOrder_state() == 7) {
                    holder.order_status_tv.setText("待还车");
                } else if (bean.getOrder_state() == 8) {
                    holder.order_status_tv.setText("退还违章押金");
                } else if (bean.getOrder_state() == 9) {
                    holder.order_status_tv.setText("交易完成");
                } else if (bean.getOrder_state() == 10) {
                    holder.order_status_tv.setText("审核不通过");
                } else if (bean.getOrder_state() == 11) {
                    holder.order_status_tv.setText("交易关闭");
                }
            }
        }
        return convertView;
    }


    class Holder {
        ImageView car_iv;
        TextView car_name_tv, car_info_tv, lease_date_tv, order_no_tv, order_status_tv;
    }
}

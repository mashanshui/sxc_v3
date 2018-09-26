package com.sxcapp.www.Share.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sxcapp.www.R;
import com.sxcapp.www.Share.Bean.OrderListBeanV3;
import com.sxcapp.www.UserCenter.OrderRemarkActivityV3;

import java.util.List;

/**
 * Created by wenleitao on 2017/7/26.
 */

public class OrderListAdapterV3 extends BaseAdapter {
    private Context context;
    private List<OrderListBeanV3> list;

    public OrderListAdapterV3(Context context, List<OrderListBeanV3> list) {
        this.context = context;
        this.list = list;
    }

    public void loadMore(List<OrderListBeanV3> data) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.order_item_v3, null);
            holder.car_info_tv = convertView.findViewById(R.id.car_info_tv);
            holder.car_name_tv = convertView.findViewById(R.id.car_name_tv);
            holder.lease_date_tv = convertView.findViewById(R.id.order_date_tv);
            holder.order_no_tv = convertView.findViewById(R.id.order_no_tv);
            holder.order_status_tv = convertView.findViewById(R.id.order_status_tv);
            holder.car_iv = convertView.findViewById(R.id.car_iv);
            holder.remark_tv = convertView.findViewById(R.id.remark_tv);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        final OrderListBeanV3 bean = list.get(position);
        Glide.with(context).load(bean.getCar_image()).into(holder.car_iv);
        holder.car_name_tv.setText(bean.getCar_name());
        holder.lease_date_tv.setText(bean.getOrder_time());
        holder.order_no_tv.setText(bean.getOrder_no());

        switch (bean.getOrder_type()) {
            case 1:
                holder.car_info_tv.setText("|新能源|" + "乘坐" + bean.getNumber_seats() + "人");
                switch (bean.getOrder_state()) {
                    case 0:
                        holder.order_status_tv.setText("待取车");
                        break;
                    case 1:
                        holder.order_status_tv.setText("待还车");
                        break;
                    case 2:
                        holder.order_status_tv.setText("已完成");
                        break;
                    case 3:
                        holder.order_status_tv.setText("无效订单");
                        break;
                    case 4:
                        holder.order_status_tv.setText("已取消");
                        break;
                    case 6:
                        holder.order_status_tv.setText("待支付");
                        break;
                    default:
                        break;
                }
                break;
            case 2:
                holder.car_info_tv.setText("|新能源|" + "乘坐" + bean.getNumber_seats() + "人");
                switch (bean.getOrder_state()) {
                    case 0:
                        holder.order_status_tv.setText("待取车");
                        break;
                    case 1:
                        holder.order_status_tv.setText("待还车");
                        break;
                    case 2:
                        holder.order_status_tv.setText("已完成");
                        break;
                    case 3:
                        holder.order_status_tv.setText("无效订单");
                        break;
                    case 4:
                        holder.order_status_tv.setText("已取消");
                        break;
                    default:
                        break;

                }
                break;
            case 3:
                holder.car_info_tv.setText("|燃油车|" + "乘坐" + bean.getNumber_seats() + "人");
                switch (bean.getOrder_state()) {
                    case 0:
                        holder.order_status_tv.setText("待取车");
                        break;
                    case 1:
                        holder.order_status_tv.setText("待还车");
                        break;
                    case 2:
                        holder.order_status_tv.setText("已完成");
                        break;
                    case 3:
                        holder.order_status_tv.setText("无效订单");
                        break;
                    case 4:
                        holder.order_status_tv.setText("已取消");
                        break;
                    case 6:
                        holder.order_status_tv.setText("待支付");
                        break;
                    default:
                        break;
                }
                break;
            case 4:
                holder.car_info_tv.setText("|燃油车|" + "乘坐" + bean.getNumber_seats() + "人");
                switch (bean.getOrder_state()) {
                    case 0:
                        holder.order_status_tv.setText("待取车");
                        break;
                    case 1:
                        holder.order_status_tv.setText("待还车");
                        break;
                    case 2:
                        holder.order_status_tv.setText("已完成");
                        break;
                    case 3:
                        holder.order_status_tv.setText("无效订单");
                        break;
                    case 4:
                        holder.order_status_tv.setText("已取消");
                        break;
                        default:
                            break;

                }
                break;
            case 5:
                switch (bean.getOrder_state()) {
                    case 0:
                        holder.order_status_tv.setText("预约中");
                        break;
                    case 1:
                        holder.order_status_tv.setText("待风控审核");
                        break;
                    case 2:
                        holder.order_status_tv.setText("待用车");
                        break;
                    case 3:
                        holder.order_status_tv.setText("待还车");
                        break;
                    case 4:
                        holder.order_status_tv.setText("已完成");
                        break;
                    case 5:
                        holder.order_status_tv.setText("已取消");
                        break;
                    default:
                        break;

                }
                holder.car_info_tv.setText("|豪车|" + "乘坐" + bean.getNumber_seats() + "人");
                break;
            default:
                break;
        }
        if ("已完成".equals(holder.order_status_tv.getText().toString())) {
            holder.order_status_tv.setTextColor(context.getResources().getColor(R.color.green));
            holder.remark_tv.setVisibility(View.VISIBLE);
            if (bean.getEvaluate_state() == 0) {
                holder.remark_tv.setText("去评价");
                holder.remark_tv.setTextColor(context.getResources().getColor(R.color.orange));
                holder.remark_tv.setBackgroundResource(R.drawable.rect_orange);
                holder.remark_tv.setClickable(true);
                holder.remark_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, OrderRemarkActivityV3.class);
                        intent.putExtra("order_no", bean.getOrder_no());
                        intent.putExtra("order_type", bean.getOrder_type());
                        intent.putExtra("from", "order_list");
                        context.startActivity(intent);
                    }
                });
            } else {
                holder.remark_tv.setText("已评价");
                holder.remark_tv.setTextColor(context.getResources().getColor(R.color.green));
                holder.remark_tv.setBackgroundResource(R.drawable.rect_green_hollow);
                holder.remark_tv.setClickable(false);
            }
        } else {
            holder.remark_tv.setVisibility(View.GONE);
            holder.order_status_tv.setTextColor(context.getResources().getColor(R.color.black_tv_26));
        }

        return convertView;
    }


    class Holder {
        ImageView car_iv;
        TextView car_name_tv, car_info_tv, lease_date_tv, order_no_tv, order_status_tv, remark_tv;
    }
}

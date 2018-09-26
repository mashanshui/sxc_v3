package com.sxcapp.www.UserCenter.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.Bean.CouponBean;

import java.util.List;

/**
 * Created by wenleitao on 2017/12/14.
 */

public class CouponListAdapter extends BaseAdapter {
    private Context context;
    private List<CouponBean> list;

    public CouponListAdapter(Context context, List<CouponBean> list) {
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
        MyHolder holder;
        if (convertView == null) {
            holder = new MyHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.coupon_item, null);
            holder.iv = (ImageView) convertView.findViewById(R.id.coupon_iv);
            holder.tv = (TextView) convertView.findViewById(R.id.limit_time_tv);
            holder.area_tv = (TextView) convertView.findViewById(R.id.area_tv);
            convertView.setTag(holder);
        } else {
            holder = (MyHolder) convertView.getTag();
        }
        holder.tv.setText("有效期:" + list.get(position).getTerm_time());
        if (list.get(position).getCoupon_type() == 1) {
            holder.area_tv.setText("限天津市使用");
        } else if (list.get(position).getCoupon_type() == 3) {
            holder.area_tv.setText("限内蒙地区,需到赤峰门店登记后方可使用");
        } else if (list.get(position).getCoupon_type() == 2) {
            holder.area_tv.setText("限赤峰市使用");
        }
        if (list.get(position).getCoupon_type() == 3) {
            switch (list.get(position).getCoupon_title()) {
                case "租车1天只要50元":
                    if (list.get(position).getUse_type() == 1) {
                        holder.iv.setBackgroundResource(R.mipmap.fifty_oneday);
                    } else if (list.get(position).getUse_type() == 2) {
                        holder.iv.setBackgroundResource(R.mipmap.fifty_oneday_used);
                    } else if (list.get(position).getUse_type() == 3) {
                        holder.iv.setBackgroundResource(R.mipmap.fifty_oneday_overdue);
                    }
                    break;
                case "租车7天只要0元":
                    if (list.get(position).getUse_type() == 1) {
                        holder.iv.setBackgroundResource(R.mipmap.free_use_seven);
                    } else if (list.get(position).getUse_type() == 2) {
                        holder.iv.setBackgroundResource(R.mipmap.free_use_seven_used);
                    } else if (list.get(position).getUse_type() == 3) {
                        holder.iv.setBackgroundResource(R.mipmap.free_use_seven_overdue);
                    }
                    break;
                case "租车3天只要0元":
                    if (list.get(position).getUse_type() == 1) {
                        holder.iv.setBackgroundResource(R.mipmap.free_use_three);
                    } else if (list.get(position).getUse_type() == 2) {
                        holder.iv.setBackgroundResource(R.mipmap.free_use_three_used);
                    } else if (list.get(position).getUse_type() == 3) {
                        holder.iv.setBackgroundResource(R.mipmap.free_use_three_overdue);
                    }
                    break;
                case "租车1天只要0元":
                    if (list.get(position).getUse_type() == 1) {
                        holder.iv.setBackgroundResource(R.mipmap.free_use_one);
                    } else if (list.get(position).getUse_type() == 2) {
                        holder.iv.setBackgroundResource(R.mipmap.free_use_one_used);
                    } else if (list.get(position).getUse_type() == 3) {
                        holder.iv.setBackgroundResource(R.mipmap.free_use_one_overdue);
                    }
                    break;
                default:
                    break;
            }
        } else {
            switch (list.get(position).getCut_cost()) {
                case 1:
                    if (list.get(position).getUse_type() == 1) {
                        holder.iv.setBackgroundResource(R.mipmap.full_one_coupon);
                    } else if (list.get(position).getUse_type() == 2) {
                        holder.iv.setBackgroundResource(R.mipmap.full_one_used);
                    } else if (list.get(position).getUse_type() == 3) {
                        holder.iv.setBackgroundResource(R.mipmap.full_one_overdue);
                    }
                    break;
                case 3:
                    if (list.get(position).getUse_type() == 1) {
                        holder.iv.setBackgroundResource(R.mipmap.full_three_coupon);
                    } else if (list.get(position).getUse_type() == 2) {
                        holder.iv.setBackgroundResource(R.mipmap.full_three_used);
                    } else if (list.get(position).getUse_type() == 3) {
                        holder.iv.setBackgroundResource(R.mipmap.full_three_overdue);
                    }
                    break;
                case 5:
                    if (list.get(position).getCoupon_type() == 1) {
                        if (list.get(position).getUse_type() == 1) {
                            holder.iv.setBackgroundResource(R.mipmap.full_five_coupon);
                        } else if (list.get(position).getUse_type() == 2) {
                            holder.iv.setBackgroundResource(R.mipmap.full_five_used);
                        } else if (list.get(position).getUse_type() == 3) {
                            holder.iv.setBackgroundResource(R.mipmap.full_five_overdue);
                        }
                    } else {
                        if (list.get(position).getUse_type() == 1) {
                            holder.iv.setBackgroundResource(R.mipmap.discount_five_coupon);
                        } else if (list.get(position).getUse_type() == 2) {
                            holder.iv.setBackgroundResource(R.mipmap.discount_five_used);
                        } else if (list.get(position).getUse_type() == 3) {
                            holder.iv.setBackgroundResource(R.mipmap.discount_five_overdue);
                        }
                    }
                    break;
                case 8:
                    if (list.get(position).getUse_type() == 1) {
                        holder.iv.setBackgroundResource(R.mipmap.full_eight_coupon);
                    } else if (list.get(position).getUse_type() == 2) {
                        holder.iv.setBackgroundResource(R.mipmap.full_eight_used);
                    } else {
                        holder.iv.setBackgroundResource(R.mipmap.full_eight_overdue);
                    }
                    break;
                case 10:
                    if (list.get(position).getUse_type() == 1) {
                        holder.iv.setBackgroundResource(R.mipmap.discount_ten_coupon);
                    } else if (list.get(position).getUse_type() == 2) {
                        holder.iv.setBackgroundResource(R.mipmap.discount_ten_used);
                    } else {
                        holder.iv.setBackgroundResource(R.mipmap.discount_ten_overdue);
                    }
                    break;
                case 15:
                    if (list.get(position).getUse_type() == 1) {
                        holder.iv.setBackgroundResource(R.mipmap.discount_fifteen_coupon);
                    } else if (list.get(position).getUse_type() == 2) {
                        holder.iv.setBackgroundResource(R.mipmap.discount_fifteen_used);
                    } else if (list.get(position).getUse_type() == 3) {
                        holder.iv.setBackgroundResource(R.mipmap.discount_fifteen_overdue);
                    }
                    break;
                case 20:
                    if (list.get(position).getUse_type() == 1) {
                        holder.iv.setBackgroundResource(R.mipmap.discount_twenty_coupon);
                    } else if (list.get(position).getUse_type() == 2) {
                        holder.iv.setBackgroundResource(R.mipmap.discount_twenty_used);
                    } else if (list.get(position).getUse_type() == 3) {
                        holder.iv.setBackgroundResource(R.mipmap.discount_twenty_overdue);
                    }
                    break;
                default:
                    break;
            }
        }
        return convertView;
    }

    class MyHolder {
        ImageView iv;
        TextView tv;
        TextView area_tv;
    }
}

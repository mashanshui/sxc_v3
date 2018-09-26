package com.sxcapp.www.Buy;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sxcapp.www.Buy.Bean.OldVechileInformation;
import com.sxcapp.www.R;
import com.sxcapp.www.Util.Properties;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by wenleitao on 2017/7/18.
 */

public class OldCarAdapter extends BaseAdapter {
    private Context context;
    private List<OldVechileInformation> list;
    private final RequestOptions myOption;

    public OldCarAdapter(Context context, List<OldVechileInformation> list) {
        this.context = context;
        this.list = list;
        myOption = new RequestOptions().placeholder(R.mipmap.car_placeholder);
    }

    public void addData(List<OldVechileInformation> data) {
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
        MyHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.oldcarlist_item, null);
            holder = new MyHolder();
            holder.iv = (ImageView) convertView.findViewById(R.id.car_iv);
            holder.name_tv = (TextView) convertView.findViewById(R.id.car_name);
            holder.info_tv = (TextView) convertView.findViewById(R.id.car_info);
            holder.price_tv = (TextView) convertView.findViewById(R.id.car_price);
            holder.old_price_tv = (TextView) convertView.findViewById(R.id.old_price_tv);
            convertView.setTag(holder);
        } else {
            holder = (MyHolder) convertView.getTag();
        }
        DecimalFormat df = new DecimalFormat("#0.00");
        OldVechileInformation bean = list.get(position);
        Glide.with(context).load(Properties.baseImageUrl + bean.getImg()).apply(myOption).into(holder.iv);
        holder.name_tv.setText(bean.getAllBrandInfoName());
        holder.price_tv.setText(df.format(bean.getOwner_quote()) + "");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(bean.getLicensing_time());
        holder.info_tv.setText(date + "上牌|" + "行驶" + bean.getMileage() + "万公里");
        holder.old_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.old_price_tv.setText("新车价：" + df.format(bean.getNew_vehicle_price()) + "万");
        return convertView;
    }

    class MyHolder {
        ImageView iv;
        TextView name_tv, info_tv, price_tv, old_price_tv;

    }
}

package com.sxcapp.www.Share.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sxcapp.www.CustomerView.BatteryView;
import com.sxcapp.www.R;
import com.sxcapp.www.Share.Bean.ElecShortCarInfoBeanV3;
import com.sxcapp.www.Share.Bean.OilShortCarInfoBeanV3;
import com.sxcapp.www.Util.AndroidTool;

import java.util.List;

/**
 * Created by wenleitao on 2017/7/31.
 */

public class OilShortCarAdapterV3 extends PagerAdapter {
    private Context context;
    private List<OilShortCarInfoBeanV3> list;

    public OilShortCarAdapterV3(Context context, List<OilShortCarInfoBeanV3> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size() > 0 ? list.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        OilShortCarInfoBeanV3 bean = list.get(position);
        View view = LayoutInflater.from(context).inflate(R.layout.oil_short_item_v3, null);
        ImageView car_iv = (ImageView) view.findViewById(R.id.car_iv);
        int width = AndroidTool.getDeviceWidth((Activity) context) - AndroidTool.dip2px(context, 74);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, width / 2);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams.setMargins(0, AndroidTool.dip2px(context, 27), 0, 0);
        car_iv.setLayoutParams(layoutParams);
        TextView license_num_tv = (TextView) view.findViewById(R.id.license_num_tv);
        ImageView car_color_iv = (ImageView) view.findViewById(R.id.car_color_iv);
        TextView car_name_tv = (TextView) view.findViewById(R.id.car_name_tv);
        TextView car_info_tv = (TextView) view.findViewById(R.id.car_info_tv);
        TextView oil_mass_tv = (TextView) view.findViewById(R.id.oil_mass_tv);
        Glide.with(context).load(bean.getCar_image()).into(car_iv);
        license_num_tv.setText(bean.getLicense_plate_number());
        car_color_iv.setBackgroundColor(Color.parseColor("#" + bean.getCar_color()));
        car_name_tv.setText(bean.getCar_name());
        car_info_tv.setText("燃油车|" + "乘坐" + bean.getNumber_seats() + "人");
        oil_mass_tv.setText(bean.getDump_energy() + "%");
        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}

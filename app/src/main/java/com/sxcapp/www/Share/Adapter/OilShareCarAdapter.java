package com.sxcapp.www.Share.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sxcapp.www.R;
import com.sxcapp.www.Share.Bean.OilLongBookBean;
import com.sxcapp.www.Util.Properties;

import java.util.List;

/**
 * Created by wenleitao on 2017/7/31.
 */

public class OilShareCarAdapter extends PagerAdapter {
    private Context context;
    private List<OilLongBookBean.ListBean> list;

    public OilShareCarAdapter(Context context, List<OilLongBookBean.ListBean> list) {
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
        OilLongBookBean.ListBean bean = list.get(position);
        View view = LayoutInflater.from(context).inflate(R.layout.share_car_item, null);
        ImageView car_iv = (ImageView) view.findViewById(R.id.car_iv);
        TextView car_name_tv = (TextView) view.findViewById(R.id.car_name_tv);
        Glide.with(context).load(Properties.baseImageUrl + bean.getShow_image()).into(car_iv);
        TextView car_info_tv = (TextView) view.findViewById(R.id.car_info_tv);
        car_info_tv.setText(bean.getGearbox_type() + "|乘坐" + bean.getNumber_seats() + "人");
        car_name_tv.setText(bean.getSeries_code());
        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}

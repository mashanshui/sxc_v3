package com.sxcapp.www.Share.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sxcapp.www.R;
import com.sxcapp.www.Share.LuxuryShare.LuxuryCarListActivityV3;
import com.sxcapp.www.Util.AndroidTool;

import java.util.List;

/**
 * Created by wenleitao on 2017/7/31.
 */

public class LuxuryCarImagePageAdapterV3 extends PagerAdapter {
    private Context context;
    private List<String> list;
    private int index;

    public LuxuryCarImagePageAdapterV3(Context context, List<String> list, int index) {
        this.context = context;
        this.list = list;
        this.index = index;
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
        View v = LayoutInflater.from(context).inflate(R.layout.luxury_carlist_item, null);
        ImageView iv = (ImageView) v.findViewById(R.id.car_iv);
        int width = AndroidTool.getDeviceWidth((Activity) context) - AndroidTool.dip2px(context, 82);
        iv.setLayoutParams(new RelativeLayout.LayoutParams(width, width / 2));
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LuxuryCarListActivityV3) context).onIvItemClick(index);
            }
        });
        Glide.with(context).load(list.get(position)).into(iv);
        container.addView(v, 0);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}

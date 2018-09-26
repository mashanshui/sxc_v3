package com.sxcapp.www.Share.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sxcapp.www.Util.AndroidTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by wenleitao on 2017/7/31.
 */

public class RuleAdapter extends PagerAdapter {
    private Context context;
    private List<List<String>> list;
    private List<Integer> heightList;
    private HashMap<Integer, View> mChildrenViews = new LinkedHashMap<Integer, View>();

    public RuleAdapter(Context context, List<List<String>> list) {
        this.context = context;
        this.list = list;
        heightList = new ArrayList<>();
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
        List<String> ruleList = list.get(position);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(12, 0, 0, 0);
        for (int i = 0; i < ruleList.size(); i++) {
            TextView appointTime_tv = new TextView(context);
            appointTime_tv.setText((i + 1) + "." + ruleList.get(i));
            appointTime_tv.setTextColor(Color.parseColor("#42000000"));
            appointTime_tv.setTextSize(13);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            appointTime_tv.setLayoutParams(params);
            appointTime_tv.setPadding(AndroidTool.dip2px(context, 12), 10, 0, 0);
            linearLayout.addView(appointTime_tv, i);
        }
        heightList.add(linearLayout.getMeasuredHeight());
        mChildrenViews.put(position, linearLayout);
        container.addView(linearLayout, 0);
        return linearLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public List<Integer> getHeightList() {
        return heightList;
    }

    public HashMap<Integer, View> getmChildrenViews() {
        return mChildrenViews;
    }
}

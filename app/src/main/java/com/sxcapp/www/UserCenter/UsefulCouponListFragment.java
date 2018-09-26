package com.sxcapp.www.UserCenter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.Adapter.UsefulCouponListAdapterV3;
import com.sxcapp.www.UserCenter.Bean.CouponBeanV3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenleitao on 2018/4/4.
 */

public class UsefulCouponListFragment extends Fragment {
    private View contentView;
    private List<CouponBeanV3> list;
    private ListView lv;
    private ImageView empty_iv;

    public static UsefulCouponListFragment NewInstance(ArrayList<CouponBeanV3> couponBeanV3List) {
        UsefulCouponListFragment usefulCouponListFragment = new UsefulCouponListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("list", couponBeanV3List);
        usefulCouponListFragment.setArguments(bundle);
        return usefulCouponListFragment;

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(R.layout.activity_mycouponlist_v3, container, false);
        }
        Bundle bundle = getArguments();
        list = bundle.getParcelableArrayList("list");
        lv = (ListView) contentView.findViewById(R.id.lv);
        empty_iv = (ImageView) contentView.findViewById(R.id.empty_iv);
        if (list.size() > 0) {
            UsefulCouponListAdapterV3 adapterV3 = new UsefulCouponListAdapterV3(this.getContext(), list);
            lv.setAdapter(adapterV3);
        } else {
            empty_iv.setVisibility(View.VISIBLE);
            lv.setEmptyView(empty_iv);
        }
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}

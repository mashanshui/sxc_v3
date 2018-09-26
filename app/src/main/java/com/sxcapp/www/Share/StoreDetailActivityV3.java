package com.sxcapp.www.Share;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.navi.model.NaviLatLng;
import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.CustomerView.InfinitePagerAdapter;
import com.sxcapp.www.CustomerView.InfiniteViewPager;
import com.sxcapp.www.CustomerView.XListView.Xcircleindicator;
import com.sxcapp.www.R;
import com.sxcapp.www.Sell.SellActivity;
import com.sxcapp.www.Share.Adapter.StoreDetailPageAdapter;
import com.sxcapp.www.Share.Bean.StoreDetailBeanV3;
import com.sxcapp.www.Share.HttpService.ShareService;
import com.sxcapp.www.Share.Navi.NaviMainActivity;
import com.sxcapp.www.Share.OilShortShare.OilShortShareActivityV3;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManagerV3;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by wenleitao on 2018/4/14.
 */

public class StoreDetailActivityV3 extends BaseActivity {

    @BindView(R.id.store_vp)
    InfiniteViewPager store_vp;
    @BindView(R.id.indicator_lin)
    LinearLayout indicator_lin;
    @BindView(R.id.store_name_tv)
    TextView store_name_tv;
    @BindView(R.id.store_address_tv)
    TextView store_address_tv;
    @BindView(R.id.store_phone_tv)
    TextView store_phone_tv;
    @BindView(R.id.carport_count_tv)
    TextView carport_count_tv;
    @BindView(R.id.navi_btn)
    ImageButton navi_btn;

    private ShareService service;
    private String store_id;
    private InfinitePagerAdapter infinitePagerAdapter;
    private Xcircleindicator mXcircleindicator;
    private String from;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail_v3);
        setTopbarLeftWhiteBackBtn();
        setTopBarTitle("网点详情", null);
        ButterKnife.bind(this);
        store_id = getIntent().getStringExtra("store_id");
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(ShareService.class);
        from = getIntent().getStringExtra("from");
        if ("elec".equals(from)) {
            setStatusView(R.color.green);
            setTopBarColor(R.color.green);
        } else if ("oil".equals(from)) {
            setStatusView(R.color.top_bar_red);
            setTopBarColor(R.color.top_bar_red);
        } else {
            setStatusView(R.color.luxury);
            setTopBarColor(R.color.luxury);
        }
        StatusBarUtil.StatusBarDarkMode(this);
        loadData();
    }

    private void loadData() {
        showProgressDlg();
        Observable<CodeResultV3<StoreDetailBeanV3>> ob = service.getStoreDetailV3(store_id);
        ob.compose(compose(this.<CodeResultV3<StoreDetailBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<StoreDetailBeanV3>(this) {
            @Override
            protected void onHandleSuccess(final StoreDetailBeanV3 storeDetailBeanV3) {
                removeProgressDlg();
                final List<String> list = storeDetailBeanV3.getStore_images();
                if (list.size() > 0) {
                    StoreDetailPageAdapter storeDetailPageAdapter = new StoreDetailPageAdapter(StoreDetailActivityV3.this, list);
                    if (list.size() > 1) {
                        infinitePagerAdapter = new InfinitePagerAdapter(storeDetailPageAdapter);
                        store_vp.setAdapter(infinitePagerAdapter);
                    } else {
                        store_vp.setAdapter(storeDetailPageAdapter);
                    }
                }
                if (list.size() > 1) {
                    mXcircleindicator = new Xcircleindicator(StoreDetailActivityV3.this);
                    mXcircleindicator.setPageTotalCount(list.size());
                    mXcircleindicator.setFillColor(R.color.lightGray3);
                    mXcircleindicator.setStrokeColor(R.color.green);
                    mXcircleindicator.setCircleInterval(12);
                    mXcircleindicator.setRadius(12);
                    indicator_lin.addView(mXcircleindicator);
                    mXcircleindicator.setCurrentPage(0);
                    store_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {
                            mXcircleindicator.setCurrentPage(Math.abs(position - list.size() * 100000) % list.size());
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });
                } else {
                    indicator_lin.setVisibility(View.GONE);
                }

                store_name_tv.setText(storeDetailBeanV3.getStore_name());
                store_address_tv.setText(storeDetailBeanV3.getStore_address_detail());
                store_phone_tv.setText(storeDetailBeanV3.getStore_phone());
                carport_count_tv.setText(storeDetailBeanV3.getStore_park_num() + "");
                navi_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(StoreDetailActivityV3.this, NaviMainActivity.class);
                        intent.putExtra("end", new NaviLatLng(storeDetailBeanV3.getStore_latitude(), storeDetailBeanV3.getStore_longitude()));
                        startActivity(intent);
                    }
                });

            }
        });
    }
}

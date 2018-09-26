package com.sxcapp.www.Share.LuxuryShare;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rd.PageIndicatorView;
import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.CustomerView.ExpandableLayout;
import com.sxcapp.www.R;
import com.sxcapp.www.Share.Adapter.LuxuryAppointCarImagePageAdapterV3;
import com.sxcapp.www.Share.Bean.LuxuryOffOrderDetailBeanV3;
import com.sxcapp.www.Share.Bean.OffOrderDetailBeanV3;
import com.sxcapp.www.Share.HttpService.ShareService;
import com.sxcapp.www.Util.AndroidTool;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManagerV3;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by wenleitao on 2018/5/4.
 */

public class LuxuryOffOrderDetailActivityV3 extends BaseActivity {
    @BindView(R.id.car_vp)
    ViewPager car_vp;
    @BindView(R.id.pageIndicatorView)
    PageIndicatorView mXcircleindicator;
    @BindView(R.id.car_name_tv)
    TextView car_name_tv;
    @BindView(R.id.license_num_tv)
    TextView license_num_tv;
    @BindView(R.id.fetch_store_name_tv)
    TextView fetch_store_name_tv;
    @BindView(R.id.g_store_name_tv)
    TextView g_store_name_tv;
    @BindView(R.id.hide_cost_re)
    RelativeLayout hide_cost_re;
    @BindView(R.id.cost_info_lin)
    LinearLayout cost_info_lin;
    @BindView(R.id.cost_lin)
    LinearLayout cost_lin;
    @BindView(R.id.exception_cost_lin)
    LinearLayout exception_cost_lin;
    @BindView(R.id.exception_lin)
    LinearLayout exception_lin;
    @BindView(R.id.expandable_view)
    ExpandableLayout expandable_view;
    @BindView(R.id.hide_cost_iv)
    ImageView hide_cost_iv;
    @BindView(R.id.total_cost_tv)
    TextView total_cost_tv;
    @BindView(R.id.topbar_rightbtn)
    Button topbar_rightbtn;
    @BindView(R.id.car_info_tv)
    TextView car_info_tv;
    private String order_no;
    private ShareService service;
    private boolean is_hide = false;
    private LuxuryAppointCarImagePageAdapterV3 adapterV3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luxury_order_off_v3);
        setTopBarColor(R.color.luxury);
        setStatusView(R.color.luxury);
        StatusBarUtil.StatusBarDarkMode(this);
        setTopBarTitle("豪车订单详情", null);
        setTopbarLeftWhiteBackBtn();
        ButterKnife.bind(this);
        if (getIntent().getIntExtra("order_state", 0) == 4) {
            topbar_rightbtn.setText("已完成");
        } else if (getIntent().getIntExtra("order_state", 0) == 5) {
            topbar_rightbtn.setText("已取消");
        }
        topbar_rightbtn.setTextColor(getResources().getColor(R.color.top_bar_red));
        topbar_rightbtn.setVisibility(View.VISIBLE);
        order_no = getIntent().getStringExtra("order_no");
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(ShareService.class);
        loadData();
    }

    private void loadData() {
        showProgressDlg();
        Observable<CodeResultV3<LuxuryOffOrderDetailBeanV3>> ob = service.getLuxuryOffOrderDetailV3(order_no);
        ob.compose(compose(this.<CodeResultV3<LuxuryOffOrderDetailBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<LuxuryOffOrderDetailBeanV3>(this) {
            @Override
            protected void onHandleSuccess(LuxuryOffOrderDetailBeanV3 beanV3) {
                removeProgressDlg();
                adapterV3 = new LuxuryAppointCarImagePageAdapterV3(LuxuryOffOrderDetailActivityV3.this, beanV3.getCar_image());
                car_vp.setAdapter(adapterV3);
                mXcircleindicator.setCount(beanV3.getCar_image().size());
                mXcircleindicator.setSelection(0);
                mXcircleindicator.setRadius(5);
                car_name_tv.setText(beanV3.getCar_name());
                license_num_tv.setText(beanV3.getLicense_plate_number());
                fetch_store_name_tv.setText(beanV3.getFetch_store());
                g_store_name_tv.setText(beanV3.getReturn_store());
                car_info_tv.setText("豪车|" + "乘坐" + beanV3.getNumber_seats() + "人");
                if (beanV3.getCost_remark() != null && beanV3.getCost_remark().size() > 0) {
                    for (int i = 0; i < beanV3.getCost_remark().size(); i++) {
                        TextView appointTime_tv = new TextView(LuxuryOffOrderDetailActivityV3.this);
                        appointTime_tv.setText((i + 1) + "." + beanV3.getCost_remark().get(i));
                        appointTime_tv.setTextColor(Color.parseColor("#c4000000"));
                        appointTime_tv.setTextSize(13);
                        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        appointTime_tv.setLayoutParams(params);
                        appointTime_tv.setPadding(AndroidTool.dip2px(LuxuryOffOrderDetailActivityV3.this, 12), AndroidTool.dip2px(LuxuryOffOrderDetailActivityV3.this, 12), 0, 0);
                        cost_info_lin.addView(appointTime_tv, i);
                    }
                }
                total_cost_tv.setText(beanV3.getTotal_cost() + "元");

                hide_cost_re.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (is_hide) {
                            expandable_view.expand();
                            hide_cost_iv.setBackgroundResource(R.mipmap.expand_icon_v3);
                            is_hide = false;
                        } else {
                            expandable_view.collapse();
                            hide_cost_iv.setBackgroundResource(R.mipmap.hide_icon_v3);
                            is_hide = true;
                        }
                    }
                });
                car_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        mXcircleindicator.setSelection(position);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
                if (beanV3.getExceptions() != null && beanV3.getExceptions().size() > 0) {
                    exception_lin.setVisibility(View.VISIBLE);
                    for (int i = 0; i < beanV3.getExceptions().size(); i++) {
                        TextView appointTime_tv = new TextView(LuxuryOffOrderDetailActivityV3.this);
                        appointTime_tv.setText((i + 1) + "." + beanV3.getExceptions().get(i));
                        appointTime_tv.setTextColor(Color.parseColor("#c4000000"));
                        appointTime_tv.setTextSize(13);
                        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        appointTime_tv.setLayoutParams(params);
                        appointTime_tv.setPadding(AndroidTool.dip2px(LuxuryOffOrderDetailActivityV3.this, 12), AndroidTool.dip2px(LuxuryOffOrderDetailActivityV3.this, 12), 0, 0);
                        exception_cost_lin.addView(appointTime_tv, i);
                    }
                } else {
                    exception_lin.setVisibility(View.GONE);
                }
            }
        });

    }
}

package com.sxcapp.www.Share.OilShortShare;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.CustomerView.ExpandableLayout;
import com.sxcapp.www.R;
import com.sxcapp.www.Share.Bean.OffOrderDetailBeanV3;
import com.sxcapp.www.Share.ElectricShortShare.ElecShortOffOrderDetailActivityV3;
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

public class OilShortOffOrderDetailActivityV3 extends BaseActivity {
    @BindView(R.id.car_iv)
    ImageView car_iv;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_off_v3);
        setTopBarColor(R.color.top_bar_red);
        setStatusView(R.color.top_bar_red);
        StatusBarUtil.StatusBarDarkMode(this);
        setTopBarTitle("订单详情", null);
        setTopbarLeftWhiteBackBtn();
        ButterKnife.bind(this);
        if (getIntent().getIntExtra("order_state", 0) == 2) {
            topbar_rightbtn.setText("已完成");
        } else if (getIntent().getIntExtra("order_state", 0) == 4) {
            topbar_rightbtn.setText("已取消");
        }
        topbar_rightbtn.setTextColor(getResources().getColor(R.color.white));
        topbar_rightbtn.setVisibility(View.VISIBLE);
        order_no = getIntent().getStringExtra("order_no");
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(ShareService.class);
        int width = AndroidTool.getDeviceWidth(OilShortOffOrderDetailActivityV3.this) - AndroidTool.dip2px(OilShortOffOrderDetailActivityV3.this, 74);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, width / 2);
        layoutParams.setMargins(0, AndroidTool.dip2px(OilShortOffOrderDetailActivityV3.this, 27), 0, 0);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        car_iv.setLayoutParams(layoutParams);
        loadData();
    }

    private void loadData() {
        showProgressDlg();
        Observable<CodeResultV3<OffOrderDetailBeanV3>> ob = service.getOilShortOffOrderDetailV3(order_no);
        ob.compose(compose(this.<CodeResultV3<OffOrderDetailBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<OffOrderDetailBeanV3>(this) {
            @Override
            protected void onHandleSuccess(OffOrderDetailBeanV3 beanV3) {
                removeProgressDlg();
                Glide.with(OilShortOffOrderDetailActivityV3.this).load(beanV3.getCar_image()).into(car_iv);
                car_name_tv.setText(beanV3.getCar_name());
                license_num_tv.setText(beanV3.getLicense_plate_number());
                fetch_store_name_tv.setText(beanV3.getFetch_store());
                g_store_name_tv.setText(beanV3.getReturn_store());
                car_info_tv.setText("燃油车|" + "乘坐" + beanV3.getNumber_seats() + "人");
                if (beanV3.getCost_remark() != null && beanV3.getCost_remark().size() > 0) {
                    for (int i = 0; i < beanV3.getCost_remark().size(); i++) {
                        TextView appointTime_tv = new TextView(OilShortOffOrderDetailActivityV3.this);
                        appointTime_tv.setText((i + 1) + "." + beanV3.getCost_remark().get(i));
                        appointTime_tv.setTextColor(Color.parseColor("#c4000000"));
                        appointTime_tv.setTextSize(13);
                        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        appointTime_tv.setLayoutParams(params);
                        appointTime_tv.setPadding(AndroidTool.dip2px(OilShortOffOrderDetailActivityV3.this, 12), AndroidTool.dip2px(OilShortOffOrderDetailActivityV3.this, 12), 0, 0);
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
                if (beanV3.getExceptions() != null && beanV3.getExceptions().size() > 0) {
                    exception_lin.setVisibility(View.VISIBLE);
                    for (int i = 0; i < beanV3.getExceptions().size(); i++) {
                        TextView appointTime_tv = new TextView(OilShortOffOrderDetailActivityV3.this);
                        appointTime_tv.setText((i + 1) + "." + beanV3.getExceptions().get(i));
                        appointTime_tv.setTextColor(Color.parseColor("#c4000000"));
                        appointTime_tv.setTextSize(13);
                        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        appointTime_tv.setLayoutParams(params);
                        appointTime_tv.setPadding(AndroidTool.dip2px(OilShortOffOrderDetailActivityV3.this, 12), AndroidTool.dip2px(OilShortOffOrderDetailActivityV3.this, 12), 0, 0);
                        exception_cost_lin.addView(appointTime_tv, i);
                    }
                } else {
                    exception_lin.setVisibility(View.GONE);
                }
            }
        });

    }
}

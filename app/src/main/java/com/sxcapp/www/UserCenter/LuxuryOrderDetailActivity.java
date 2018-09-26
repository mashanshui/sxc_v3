package com.sxcapp.www.UserCenter;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.Bean.ShareInDayOrderDetailBean;
import com.sxcapp.www.UserCenter.HttpService.UserCenterService;
import com.sxcapp.www.Util.BaseObserver;
import com.sxcapp.www.Util.Properties;
import com.sxcapp.www.Util.TimeFormat;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManager;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * 到店租订单详情界面
 * Created by wenleitao on 2017/7/31.
 */

public class LuxuryOrderDetailActivity extends BaseActivity {
    private ViewGroup.LayoutParams params;
    @BindView(R.id.car_iv)
    ImageView car_iv;
    @BindView(R.id.car_name_tv)
    TextView car_name_tv;
    @BindView(R.id.car_info_tv)
    TextView car_info_tv;
    @BindView(R.id.lease_lo_tv)
    TextView lease_lo_tv;
    @BindView(R.id.g_lo_tv)
    TextView g_lo_tv;
    @BindView(R.id.license_num_tv)
    TextView license_num_tv;
    @BindView(R.id.deduction_tv)
    TextView deduction_tv;
    @BindView(R.id.rules_lin)
    LinearLayout rules_lin;
    @BindView(R.id.share_in_day_type_tv)
    TextView type_tv;
    @BindView(R.id.cost_info_tv)
    TextView cost_info_tv;
    @BindView(R.id.add_cost_tv)
    TextView add_cost_tv;
    @BindView(R.id.total_cost_tv)
    TextView total_cost_tv;
    @BindView(R.id.add_time_tv)
    TextView add_time_tv;
    @BindView(R.id.exception_tv)
    TextView exception_tv;
    @BindView(R.id.exception_lin)
    LinearLayout exception_lin;
    private String order_no;
    @BindView(R.id.appoint_date_tv)
    TextView appoint_date_tv;
    @BindView(R.id.appoint_time_tv)
    TextView appoint_time_tv;
    @BindView(R.id.discount_tv)
    TextView discount_tv;
    private UserCenterService service;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luxuryorderdetail);
        ButterKnife.bind(this);
        setTopbarLeftBackBtn();
        setTopBarTitle("豪车租订单详情", null);
        service = RetrofitManager.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
        order_no = getIntent().getStringExtra("order_no");
        loadData();
    }


    private void loadData() {
        showProgressDlg();
        Observable<Result<ShareInDayOrderDetailBean>> ob = service.getInDayOrderDetail(order_no);
        ob.compose(compose(this.<Result<ShareInDayOrderDetailBean>>bindToLifecycle())).subscribe(new BaseObserver<ShareInDayOrderDetailBean>(this) {
            @Override
            protected void onHandleSuccess(ShareInDayOrderDetailBean bean) {
                removeProgressDlg();
                Glide.with(LuxuryOrderDetailActivity.this).load(Properties.baseImageUrl + bean.getShareInformation().getShow_image()).into(car_iv);
                car_name_tv.setText(bean.getShareInformation().getSeries_code());
                car_info_tv.setText(bean.getShareInformation().getGearbox_type() + "|乘坐" + bean.getShareInformation().getNumber_seats() + "人");
                type_tv.setText(bean.getShareCarLongOrder().getDay_cost() + "天租");
                appoint_date_tv.setText(TimeFormat.getDate(new Date(bean.getShareCarLongOrder().getOrder_time())));
                appoint_time_tv.setText(TimeFormat.getDay(new Date(bean.getShareCarLongOrder().getOrder_time())) +
                        TimeFormat.gethour(new Date(bean.getShareCarLongOrder().getOrder_time())));
                DecimalFormat df = new DecimalFormat("#0.00");
                cost_info_tv.setText("租金" + df.format(bean.getShareCarLongOrder().getSingle_cost()) + ",押金" +
                        df.format(bean.getShareCarLongOrder().getDeposit_cost()) + ",不计免赔" + df.format(bean.getShareCarLongOrder().getNo_deductible()));
                lease_lo_tv.setText(bean.getStoreFetch());
                g_lo_tv.setText(bean.getStoreReturn());
                license_num_tv.setText(bean.getShareInformation().getLicense_plate_number());
                add_cost_tv.setText(df.format(bean.getShareCarLongOrder().getAdd_cost()));
                total_cost_tv.setText(df.format(bean.getShareCarLongOrder().getTotal_cost()));
                discount_tv.setText(bean.getCoupon());
                add_time_tv.setText(bean.getShareCarLongOrder().getAdd_usetime() + "");
                for (int i = 0; i < bean.getSettingList().size(); i++) {
                    TextView appointTime_tv = new TextView(LuxuryOrderDetailActivity.this);
                    appointTime_tv.setText((i + 1) + "." + bean.getSettingList().get(i));
                    appointTime_tv.setTextColor(Color.parseColor("#5a5a5a"));
                    appointTime_tv.setTextSize(15);
                    params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    appointTime_tv.setLayoutParams(params);
                    appointTime_tv.setPadding(0, 10, 0, 0);
                    rules_lin.addView(appointTime_tv, i);
                }
                deduction_tv.setText("￥" + df.format(bean.getShareCarLongOrder().getNo_deductible()));
                if ("1".equals(bean.getShareCarLongOrder().getIs_exception())) {
                    exception_lin.setVisibility(View.VISIBLE);
                    exception_tv.setVisibility(View.VISIBLE);
                    List<String> remarkList = bean.getException_remark();
                    for (int i = 0; i < remarkList.size(); i++) {
                        TextView appointTime_tv = new TextView(LuxuryOrderDetailActivity.this);
                        appointTime_tv.setText((remarkList.size() - i) + "." + remarkList.get(i));
                        appointTime_tv.setTextColor(Color.parseColor("#5a5a5a"));
                        appointTime_tv.setTextSize(15);
                        params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        appointTime_tv.setLayoutParams(params);
                        appointTime_tv.setPadding(0, 10, 0, 0);
                        exception_lin.addView(appointTime_tv, 0);
                    }
                }
            }
        });
    }

}

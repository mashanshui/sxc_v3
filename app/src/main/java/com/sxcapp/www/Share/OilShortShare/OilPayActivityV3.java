package com.sxcapp.www.Share.OilShortShare;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
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
import com.sxcapp.www.MyApplication;
import com.sxcapp.www.R;
import com.sxcapp.www.Share.Bean.ElecShortPayInfoBeanV3;
import com.sxcapp.www.Share.Bean.OilShortPayInfoBeanV3;
import com.sxcapp.www.Share.ElectricShortShare.PayActivityV3;
import com.sxcapp.www.Share.HttpService.ShareService;
import com.sxcapp.www.Share.OrderCouponListActivityV3;
import com.sxcapp.www.UserCenter.OrderRemarkActivityV3;
import com.sxcapp.www.Util.AndroidTool;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManagerV3;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by wenleitao on 2018/4/20.
 */

public class OilPayActivityV3 extends BaseActivity {
    @BindView(R.id.car_iv)
    ImageView car_iv;
    @BindView(R.id.license_num_tv)
    TextView license_num_tv;
    @BindView(R.id.car_color_iv)
    ImageView car_color_iv;
    @BindView(R.id.car_name_tv)
    TextView car_name_tv;
    @BindView(R.id.endurance_tv)
    TextView endurance_tv;
    @BindView(R.id.car_info_tv)
    TextView car_info_tv;
    @BindView(R.id.time_tv)
    TextView time_tv;
    @BindView(R.id.fetch_store_name_tv)
    TextView fetch_store_name_tv;
    @BindView(R.id.fetch_time_tv)
    TextView fetch_time_tv;
    @BindView(R.id.g_store_name_tv)
    TextView g_store_name_tv;
    @BindView(R.id.g_time_tv)
    TextView g_time_tv;
    @BindView(R.id.total_cost_tv)
    TextView total_cost_tv;
    @BindView(R.id.cost_lin)
    LinearLayout cost_lin;
    @BindView(R.id.coupon_tv)
    TextView coupon_tv;
    @BindView(R.id.coupon_re)
    RelativeLayout coupon_re;
    @BindView(R.id.pay_btn)
    Button pay_btn;
    @BindView(R.id.pay_remark_lin)
    LinearLayout pay_remark_lin;

    private ShareService service;
    private String order_no;
    private List<String> costList;
    private String coupon_id = "0";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oil_short_pay_v3);
        setTopbarLeftbtn(R.mipmap.back_white, 0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().gotoMainActivity();
            }
        });
        StatusBarUtil.StatusBarDarkMode(this);
        setTopBarTitle("订单支付", null);
        setStatusView(R.color.top_bar_red);
        ButterKnife.bind(this);
        setTopBarColor(R.color.top_bar_red);
        order_no = getIntent().getStringExtra("order_no");
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(ShareService.class);
        costList = new ArrayList<>();
        int width = AndroidTool.getDeviceWidth(OilPayActivityV3.this) - AndroidTool.dip2px(OilPayActivityV3.this, 74);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, width / 2);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams.setMargins(0, AndroidTool.dip2px(OilPayActivityV3.this, 27), 0, 0);
        car_iv.setLayoutParams(layoutParams);
        loadData();
    }

    private void loadData() {
        showProgressDlg();
        Observable<CodeResultV3<OilShortPayInfoBeanV3>> ob = service.getOilShortPayInfoV3(order_no);
        ob.compose(compose(this.<CodeResultV3<OilShortPayInfoBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<OilShortPayInfoBeanV3>(this) {
            @Override
            protected void onHandleSuccess(OilShortPayInfoBeanV3 beanV3) {
                removeProgressDlg();
                Glide.with(OilPayActivityV3.this).load(beanV3.getCar_image()).into(car_iv);
                license_num_tv.setText(beanV3.getLicense_plate_number());
                car_name_tv.setText(beanV3.getCar_name());
                car_info_tv.setText("燃油车|" + "乘坐" + beanV3.getNumber_seats() + "人");
                time_tv.setText("用时:" + beanV3.getUse_time() + "分钟");
                endurance_tv.setText("行程:" + beanV3.getUse_km() + "公里");
                fetch_store_name_tv.setText(beanV3.getFetch_store_name());
                g_store_name_tv.setText(beanV3.getReturn_store_name());
                car_color_iv.setBackgroundColor(Color.parseColor("#" + beanV3.getCar_color()));
                fetch_time_tv.setText(beanV3.getFetch_time());
                g_time_tv.setText(beanV3.getReturn_time());
                total_cost_tv.setText(beanV3.getTotal_cost() + "元");
                costList.addAll(beanV3.getCost_remark());
                if (beanV3.getException_remark().size() > 0) {
                    costList.addAll(beanV3.getException_remark());
                }
                for (int i = 0; i < costList.size(); i++) {
                    TextView appointTime_tv = new TextView(OilPayActivityV3.this);
                    appointTime_tv.setText((i + 1) + "." + costList.get(i));
                    appointTime_tv.setTextColor(Color.parseColor("#42000000"));
                    appointTime_tv.setTextSize(13);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    appointTime_tv.setLayoutParams(params);
                    appointTime_tv.setPadding(AndroidTool.dip2px(OilPayActivityV3.this, 12), 10, 0, 0);
                    cost_lin.addView(appointTime_tv, i);
                }
                if (beanV3.getPay_remark().size() > 0) {
                    for (int i = 0; i < beanV3.getPay_remark().size(); i++) {
                        TextView pay_remark_tv = new TextView(OilPayActivityV3.this);
                        pay_remark_tv.setText((i + 1) + "." + beanV3.getPay_remark().get(i));
                        pay_remark_tv.setTextColor(Color.parseColor("#42000000"));
                        pay_remark_tv.setTextSize(13);
                        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        pay_remark_tv.setLayoutParams(params);
                        pay_remark_tv.setPadding(0, 10, 0, 0);
                        pay_remark_lin.addView(pay_remark_tv, i);
                    }
                }
                if (beanV3.getCoupon_size() == 0) {
                    coupon_tv.setText("无可用优惠券");
                    coupon_tv.setTextColor(getResources().getColor(R.color.black_tv_26));
                } else {
                    coupon_tv.setText(beanV3.getCoupon_size() + "张可用优惠券");
                    coupon_re.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(OilPayActivityV3.this, OrderCouponListActivityV3.class);
                            intent.putExtra("order_no", order_no);
                            intent.putExtra("from", "oil_short");
                            startActivityForResult(intent, 11);
                        }
                    });
                }
                pay_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pay_btn.setClickable(false);
                        showProgressDlg();
                        pay();
                    }
                });

            }
        });
    }

    private void pay() {
        Observable<CodeResultV3<Object>> ob = service.oilShortPayV3(order_no, coupon_id);
        ob.compose(compose(this.<CodeResultV3<Object>>bindToLifecycle())).subscribe(new CodeObserverV3<Object>(this) {
            @Override
            protected void onHandleSuccess(Object o) {
                removeProgressDlg();
                pay_btn.setClickable(true);
                showToast("支付成功");
                Intent intent = new Intent(OilPayActivityV3.this, OrderRemarkActivityV3.class);
                intent.putExtra("order_type", 3);
                intent.putExtra("order_no", order_no);
                intent.putExtra("from","order");
                startActivity(intent);
            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<Object> value) {
                super.onHandleError(msg, value);
                pay_btn.setClickable(true);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                pay_btn.setClickable(true);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            MyApplication.getInstance().gotoMainActivity();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        } else {
            if (requestCode == 11) {
                coupon_id = data.getStringExtra("coupon_id");
                coupon_tv.setText(data.getStringExtra("coupon_tittle"));
            }
        }
    }
}

package com.sxcapp.www.UserCenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.Lease.ConfirmOrderActivity;
import com.sxcapp.www.MyApplication;
import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.Bean.OneOrderDetailBean;
import com.sxcapp.www.UserCenter.Bean.OrderListBean;
import com.sxcapp.www.UserCenter.HttpService.UserCenterService;
import com.sxcapp.www.Util.BaseObserver;
import com.sxcapp.www.Util.Properties;
import com.sxcapp.www.Util.TimeFormat;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManager;

import java.text.DecimalFormat;
import java.util.Date;

import io.reactivex.Observable;

/**
 * Created by wenleitao on 2017/7/27.
 */

public class MyOrderDetailActivity extends BaseActivity implements View.OnClickListener {
    private OrderListBean car;
    private ImageView car_iv, check_iv;
    private TextView car_name_tv, price_tv, lease_date_tv,
            lease_time_tv, g_time_tv, g_date_tv, lease_day_tv,
            lease_lo_tv, g_lo_tv, lease_price_info_tv,
            lease_price_tv, service_charge_tv, instruction_tv,
            instruction_info_tv, deduction_info_tv, deduction_tv, total_cost_tv, car_info_tv;
    private UserCenterService service;
    private RelativeLayout deduction_re;
    private double total_cost;
    private double base_no_count;
    private Button submit_btn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetail);
        this.setTopbarLeftbtn(R.mipmap.back, 0,
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        if (getIntent().hasExtra("from")) {
                            // TODO Auto-generated method stub
                            MyApplication.getInstance().gotoMainActivity();
                        } else {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            //得到InputMethodManager的实例
                            if (imm.isActive()) {
                                //如果开启
                                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                                        0);
                                //关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的

                            }
                            finish();
                        }
                    }
                });
        setTopBarTitle("订单详情", null);
        car = getIntent().getParcelableExtra("car_bean");
        service = RetrofitManager.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
//        if (car.getOrder_state() == 0 || car.getOrder_state() == 1 || car.getOrder_state() == 2 || car.getOrder_state() == 3) {
//            setTopbarRightbtn(0, R.string.cancel_order, new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    final AlertDialog.Builder builder = new AlertDialog.Builder(MyOrderDetailActivity.this);
//                    builder.setTitle("确定取消订单").setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Observable<Result<Object>> resultOb = service.cancelOrder(car.getId());
//                            resultOb.compose(compose(MyOrderDetailActivity.this.<Result<Object>>bindToLifecycle())).subscribe(new BaseObserver<Object>(MyOrderDetailActivity.this) {
//                                @Override
//                                protected void onHandleSuccess(Object o) {
//                                    showToast("取消成功");
//                                }
//                            });
//                        }
//                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    }).setCancelable(true);
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//                }
//            });
//        }
        initViews();
        if (getIntent().hasExtra("from")) {
            showProgressDlg();
            Observable<Result<OneOrderDetailBean>> ob = service.openMyOrderDetail(getIntent().getStringExtra("order_id"));
            ob.compose(compose(this.<Result<OneOrderDetailBean>>bindToLifecycle())).subscribe(new BaseObserver<OneOrderDetailBean>(this) {
                @Override
                protected void onHandleSuccess(OneOrderDetailBean orderListBean) {
                    removeProgressDlg();
                    loadUploadData(orderListBean);
                }

                @Override
                protected void onHandleError(String msg) {
                    super.onHandleError(msg);
                    removeProgressDlg();
                }
            });
        } else {
            loadData(car);
        }

    }

    private void loadData(OrderListBean car) {
        double daily_average_price = car.getCar_rental() / car.getLease_term();
        final DecimalFormat df = new DecimalFormat("######0.00");
        Glide.with(this).load(Properties.baseImageUrl + car.getImagePath()).into(car_iv);
        car_name_tv.setText(car.getBrand_name());
        price_tv.setText("￥" + df.format(daily_average_price));
        lease_date_tv.setText(TimeFormat.getDate(new Date(car.getFetch_time())));
        g_date_tv.setText(TimeFormat.getDate(new Date(car.getReturn_time())));
        lease_time_tv.setText(TimeFormat.getDay(new Date(car.getFetch_time())) + TimeFormat.gethour(new Date(car.getFetch_time())));
        g_time_tv.setText(TimeFormat.getDay(new Date(car.getReturn_time())) + TimeFormat.gethour(new Date(car.getReturn_time())));
        lease_day_tv.setText(car.getLease_term() + "");
        lease_lo_tv.setText(car.getFetch_store_name());
        g_lo_tv.setText(car.getReturn_store_name());

        lease_price_info_tv.setText("(￥" + df.format(daily_average_price) + "*" + car.getLease_term() + "天)");
        lease_price_tv.setText("￥" + df.format(daily_average_price * car.getLease_term()));
        service_charge_tv.setText("￥" + df.format(car.getCounter_fee()));
        instruction_tv.setText("￥" + df.format(car.getBasic_premium()));
        instruction_info_tv.setText("(￥" + df.format(car.getBasic_premium() / car.getLease_term()) + "*" + car.getLease_term() + "天)");
        if (car.getNo_deductible() == null || car.getNo_deductible().isEmpty()) {
            deduction_re.setVisibility(View.GONE);
        } else {
            base_no_count = Double.parseDouble(car.getNo_deductible()) / car.getLease_term();
            deduction_info_tv.setText("(￥" + df.format(base_no_count) + "*" + car.getLease_term() + "天)");
            deduction_tv.setText("￥" + df.format(Double.parseDouble(car.getNo_deductible())));
        }
        car_info_tv.setText(car.getDisplacement() + car.getGearbox_type() + "|" + "乘坐" + car.getNumberSeats() + "人");
        total_cost = car.getTotal_cost();
        total_cost_tv.setText("￥" + df.format(total_cost));
        if (car.getPay_status() == 1) {
            submit_btn.setVisibility(View.GONE);
        } else {
            if (car.getOrder_state() == 11) {
                submit_btn.setVisibility(View.GONE);
            } else {
                submit_btn.setVisibility(View.VISIBLE);
                submit_btn.setOnClickListener(MyOrderDetailActivity.this);
            }
        }

    }

    private void loadUploadData(OneOrderDetailBean car) {
        double daily_average_price = car.getCarOrder().getCar_rental() / car.getCarOrder().getLease_term();
        final DecimalFormat df = new DecimalFormat("######0.00");
        Glide.with(this).load(Properties.baseImageUrl + car.getCarOrder().getImagePath()).into(car_iv);
        car_name_tv.setText(car.getInformation().getBrand_code());
        price_tv.setText("￥" + df.format(daily_average_price));
        lease_date_tv.setText(TimeFormat.getDate(new Date(car.getCarOrder().getFetch_time())));
        g_date_tv.setText(TimeFormat.getDate(new Date(car.getCarOrder().getReturn_time())));
        lease_time_tv.setText(TimeFormat.getDay(new Date(car.getCarOrder().getFetch_time())) + TimeFormat.gethour(new Date(car.getCarOrder().getFetch_time())));
        g_time_tv.setText(TimeFormat.getDay(new Date(car.getCarOrder().getReturn_time())) + TimeFormat.gethour(new Date(car.getCarOrder().getReturn_time())));
        lease_day_tv.setText(car.getCarOrder().getLease_term() + "");
        lease_lo_tv.setText(car.getCarOrder().getFetch_store_name());
        g_lo_tv.setText(car.getCarOrder().getReturn_store_name());
        lease_price_info_tv.setText("(￥" + df.format(daily_average_price) + "*" + car.getCarOrder().getLease_term() + "天)");
        lease_price_tv.setText("￥" + df.format(daily_average_price * car.getCarOrder().getLease_term()));
        service_charge_tv.setText("￥" + df.format(car.getCarOrder().getCounter_fee()));
        //获取到的是总得
//保险费
        instruction_tv.setText("￥" + df.format(car.getCarOrder().getBasic_premium()));
        instruction_info_tv.setText("(￥" + df.format(car.getCarOrder().getBasic_premium() / car.getCarOrder().getLease_term()) + "*" + car.getCarOrder().getLease_term() + "天)");
//不计免赔

        total_cost = car.getCarOrder().getTotal_cost();
        total_cost_tv.setText("￥" + df.format(total_cost));
        if (car.getCarOrder().getNo_deductible() == null ||
                car.getCarOrder().getNo_deductible().isEmpty() ||
                "0".equals(car.getCarOrder().getNo_deductible()) ||
                "0.0".equals(car.getCarOrder().getNo_deductible()) ||
                "0.00".equals(car.getCarOrder().getNo_deductible())) {
            deduction_re.setVisibility(View.GONE);
        } else {
            base_no_count = Double.parseDouble(car.getCarOrder().getNo_deductible()) / car.getCarOrder().getLease_term();
            deduction_info_tv.setText("(￥" + df.format(base_no_count) + "*" + car.getCarOrder().getLease_term() + "天)");
            deduction_tv.setText("￥" + df.format(Double.parseDouble(car.getCarOrder().getNo_deductible())));
        }


        if (car.getCarOrder().getPay_status() == 1) {
            submit_btn.setVisibility(View.GONE);
        } else {
            if (car.getCarOrder().getOrder_state() == 11) {
                submit_btn.setVisibility(View.GONE);
            } else {
                submit_btn.setVisibility(View.VISIBLE);
                submit_btn.setOnClickListener(MyOrderDetailActivity.this);
            }
        }

    }


    private void initViews() {
        car_iv = (ImageView) findViewById(R.id.car_iv);
        car_name_tv = (TextView) findViewById(R.id.car_name_tv);
        price_tv = (TextView) findViewById(R.id.price_tv);
        lease_date_tv = (TextView) findViewById(R.id.lease_date_tv);
        lease_time_tv = (TextView) findViewById(R.id.lease_time_tv);
        g_time_tv = (TextView) findViewById(R.id.g_time_tv);
        g_date_tv = (TextView) findViewById(R.id.g_date_tv);
        lease_day_tv = (TextView) findViewById(R.id.lease_day_tv);
        lease_lo_tv = (TextView) findViewById(R.id.lease_lo_tv);
        g_lo_tv = (TextView) findViewById(R.id.g_lo_tv);
        lease_price_info_tv = (TextView) findViewById(R.id.lease_price_info_tv);
        lease_price_tv = (TextView) findViewById(R.id.lease_price_tv);
        service_charge_tv = (TextView) findViewById(R.id.service_charge_tv);
        instruction_tv = (TextView) findViewById(R.id.instruction_tv);
        instruction_info_tv = (TextView) findViewById(R.id.instruction_info_tv);
        deduction_info_tv = (TextView) findViewById(R.id.deduction_info_tv);
        deduction_tv = (TextView) findViewById(R.id.deduction_tv);
        total_cost_tv = (TextView) findViewById(R.id.total_cost_tv);
        deduction_re = (RelativeLayout) findViewById(R.id.deduction_re);
        check_iv = (ImageView) findViewById(R.id.check_iv);
        submit_btn = (Button) findViewById(R.id.submit_btn);
        car_info_tv = (TextView) findViewById(R.id.car_info_tv);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_btn:
                DecimalFormat ddf = new DecimalFormat("######0.00");
                String order_no = car.getOrder_no();
                Intent intent = new Intent(MyOrderDetailActivity.this, ConfirmOrderActivity.class);
                intent.putExtra("order_no", order_no);
                intent.putExtra("total_cost", ddf.format(total_cost));
                intent.putExtra("car_id", car.getId());
                intent.putExtra("car_name", car.getBrand_name());
                intent.putExtra("img", car.getImagePath());
                intent.putExtra("order_id", getIntent().getStringExtra("order_id"));
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (getIntent().hasExtra("from")) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                MyApplication.getInstance().gotoMainActivity();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}

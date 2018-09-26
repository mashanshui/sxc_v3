package com.sxcapp.www.Lease;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sxcapp.www.Bean.CodeObserver;
import com.sxcapp.www.Bean.CodeResult;
import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.Lease.Bean.LeaseCar;
import com.sxcapp.www.Lease.Bean.LeaseCarSubmitBean;
import com.sxcapp.www.Lease.Bean.LeaseCostBean;
import com.sxcapp.www.Lease.HttpService.LeaseService;
import com.sxcapp.www.R;
import com.sxcapp.www.Util.BaseObserver;
import com.sxcapp.www.Util.Properties;
import com.sxcapp.www.Util.RegexUtil;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.TimeFormat;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManager;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * 提交订单界面
 * Created by wenleitao on 2017/7/22.
 */

public class OrderInfoActivity extends BaseActivity implements View.OnClickListener {
    private LeaseCar car;
    private String picker_time, g_time, lease_day, store_name;
    private LeaseService service;
    private boolean isCheck = true;
    private double total_cost;
    private LeaseCostBean leaseCostBean;
    private String user_id = SharedData.getInstance().getString(SharedData._user_id);
    @BindView(R.id.car_iv)
    ImageView car_iv;
    @BindView(R.id.check_iv)
    ImageView check_iv;
    @BindView(R.id.car_name_tv)
    TextView car_name_tv;
    @BindView(R.id.price_tv)
    TextView price_tv;
    @BindView(R.id.lease_date_tv)
    TextView lease_date_tv;
    @BindView(R.id.lease_time_tv)
    TextView lease_time_tv;
    @BindView(R.id.g_time_tv)
    TextView g_time_tv;
    @BindView(R.id.g_date_tv)
    TextView g_date_tv;
    @BindView(R.id.lease_day_tv)
    TextView lease_day_tv;
    @BindView(R.id.lease_lo_tv)
    TextView lease_lo_tv;
    @BindView(R.id.g_lo_tv)
    TextView g_lo_tv;
    @BindView(R.id.lease_price_info_tv)
    TextView lease_price_info_tv;
    @BindView(R.id.lease_price_tv)
    TextView lease_price_tv;
    @BindView(R.id.service_charge_tv)
    TextView service_charge_tv;
    @BindView(R.id.instruction_tv)
    TextView instruction_tv;
    @BindView(R.id.instruction_info_tv)
    TextView instruction_info_tv;
    @BindView(R.id.deduction_info_tv)
    TextView deduction_info_tv;
    @BindView(R.id.deduction_tv)
    TextView deduction_tv;
    @BindView(R.id.total_cost_tv)
    TextView total_cost_tv;
    @BindView(R.id.car_info_tv)
    TextView car_info_tv;
    @BindView(R.id.deduction_re)
    RelativeLayout deduction_re;
    @BindView(R.id.submit_re)
    RelativeLayout submit_re;
    @BindView(R.id.phone_edit)
    EditText phone_edit;
    @BindView(R.id.name_edit)
    EditText name_edit;
    @BindView(R.id.idCard_edit)
    EditText idCard_edit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderinfo);
        ButterKnife.bind(this);
        setTopbarLeftBackBtn();
        setTopBarTitle("确认订单", null);
        car = (LeaseCar) getIntent().getSerializableExtra("car_bean");
        picker_time = getIntent().getStringExtra("picker_time");
        g_time = getIntent().getStringExtra("g_time");
        lease_day = getIntent().getStringExtra("lease_day");
        store_name = getIntent().getStringExtra("store_name");
        service = RetrofitManager.RetrofitManagerBuild.INSTANCE.getInstance().creat(LeaseService.class);
        loadData(car);

    }

    private void loadData(final LeaseCar car) {
        phone_edit.setText(SharedData.getInstance().getString(SharedData._user_phone));
        Glide.with(this).load(Properties.baseImageUrl + car.getImage()).into(car_iv);
        car_name_tv.setText(car.getBName());
        price_tv.setText("￥" + car.getDaily_average_price());
        lease_date_tv.setText(TimeFormat.getDate(TimeFormat.getDateFromString(picker_time)));
        g_date_tv.setText(TimeFormat.getDate(TimeFormat.getDateFromString(g_time)));
        lease_time_tv.setText(TimeFormat.getDay(TimeFormat.getDateFromString(picker_time)) + TimeFormat.gethour(TimeFormat.getDateFromString(picker_time)));
        g_time_tv.setText(TimeFormat.getDay(TimeFormat.getDateFromString(g_time)) + TimeFormat.gethour(TimeFormat.getDateFromString(g_time)));
        lease_day_tv.setText(lease_day);
        lease_lo_tv.setText(store_name);
        g_lo_tv.setText(store_name);
        car_info_tv.setText(car.getPl() + car.getGearbox_type() + "|" + "乘坐" + car.getNumber_seats() + "人");
        final DecimalFormat df = new DecimalFormat("######0.00");
        String daily_lease_price = df.format(car.getDaily_average_price());
        lease_price_info_tv.setText("(￥" + daily_lease_price + "*" + lease_day + "天)");
        lease_price_tv.setText("￥" + df.format(car.getDaily_average_price() * Integer.parseInt(lease_day)));
        showProgressDlg();
        Observable<CodeResult<LeaseCostBean>> observable = service.getLeaseCarCost(car.getId(), lease_day, user_id);
        observable.compose(compose(this.<CodeResult<LeaseCostBean>>bindToLifecycle())).subscribe(new CodeObserver<LeaseCostBean>(this) {
            @Override
            protected void onHandleSuccess(LeaseCostBean o) {
                removeProgressDlg();
                leaseCostBean = o;
                service_charge_tv.setText("￥" + df.format(leaseCostBean.getHandlingCharge().get(0)));
                instruction_tv.setText("￥" + df.format(leaseCostBean.getPremium().get(0)));
//                基础保险费
                instruction_info_tv.setText("(￥" + df.format(leaseCostBean.getPremium().get(0) / Integer.parseInt(lease_day)) + "*" + lease_day + "天)");

                deduction_info_tv.setText("(￥" + df.format(leaseCostBean.getNoCount().get(0)) + "*" + lease_day + "天)");
                deduction_tv.setText("￥" + df.format(leaseCostBean.getNoCount().get(0) * Integer.parseInt(lease_day)));
                if (isCheck) {
                    Double ts = car.getDaily_average_price() * Integer.parseInt(lease_day) +
                            leaseCostBean.getHandlingCharge().get(0) +
                            leaseCostBean.getNoCount().get(0) * Integer.parseInt(lease_day) +
                            leaseCostBean.getPremium().get(0);
                    total_cost = Double.parseDouble(df.format(ts));
                } else {
                    Double ts = car.getDaily_average_price() * Integer.parseInt(lease_day) +
                            leaseCostBean.getHandlingCharge().get(0) +
                            leaseCostBean.getPremium().get(0);
                    total_cost = Double.parseDouble(df.format(ts));
                }
                total_cost_tv.setText("￥" + total_cost);
                deduction_re.setOnClickListener(OrderInfoActivity.this);
                submit_re.setOnClickListener(OrderInfoActivity.this);

            }

            @Override
            protected void onHandleError(String msg) {
                super.onHandleError(msg);
                removeProgressDlg();
            }
        });

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deduction_re:
                final DecimalFormat df = new DecimalFormat("######0.00");
                if (isCheck) {
                    isCheck = false;
                    check_iv.setBackgroundResource(R.mipmap.uncheck);
                    Double ts = leaseCostBean.getLease().get(0) +
                            leaseCostBean.getHandlingCharge().get(0) +
                            leaseCostBean.getPremium().get(0);
                    total_cost = Double.parseDouble(df.format(ts));
                } else {
                    isCheck = true;
                    check_iv.setBackgroundResource(R.mipmap.check);
                    Double ts = leaseCostBean.getLease().get(0) +
                            leaseCostBean.getHandlingCharge().get(0) +
                            leaseCostBean.getNoCount().get(0) * Integer.parseInt(lease_day) +
                            leaseCostBean.getPremium().get(0);
                    total_cost = Double.parseDouble(df.format(ts));
                }
                total_cost_tv.setText("￥" + df.format(total_cost));
                break;
            case R.id.submit_re:
                if (TextUtils.isEmpty(name_edit.getText().toString().trim())) {
                    showToast("请输入姓名");
                } else if (TextUtils.isEmpty(idCard_edit.getText().toString().trim())) {
                    showToast("请输入身份证号");
                } else {
                    if (RegexUtil.isRealIDCard(idCard_edit.getText().toString().trim())) {
                        Map<String, String> map = new HashMap<>();
                        final DecimalFormat df_ee = new DecimalFormat("######0.00");
                        map.put("source_id", car.getId());
                        map.put("user_id", SharedData.getInstance().getString(SharedData._user_id));
                        map.put("customer_name", name_edit.getText().toString().trim());
                        map.put("customer_idcard", idCard_edit.getText().toString().trim());
                        map.put("fetch_time", Long.parseLong(picker_time) / 1000 + "");
                        map.put("return_time", Long.parseLong(g_time) / 1000 + "");
                        map.put("lease_term", lease_day);
                        map.put("car_rental", leaseCostBean.getLease().get(0) + "");
                        map.put("basic_premium", df_ee.format(leaseCostBean.getPremium().get(0)));
                        if (isCheck) {
                            map.put("no_deductible", df_ee.format(leaseCostBean.getNoCount().get(0) * Integer.parseInt(lease_day)));
                        } else {
                            map.put("no_deductible", "0");
                        }
                        map.put("counter_fee", leaseCostBean.getHandlingCharge().get(0) + "");
                        map.put("total_cost", total_cost + "");
                        map.put("pay_method", "0");
                        Observable<Result<LeaseCarSubmitBean>> reOb = service.leaseCarSubmit(map);
                        showProgressDlg();
                        reOb.compose(compose(this.<Result<LeaseCarSubmitBean>>bindToLifecycle())).subscribe(new BaseObserver<LeaseCarSubmitBean>(this) {
                            @Override
                            protected void onHandleSuccess(LeaseCarSubmitBean leaseCarSubmitBean) {
                                removeProgressDlg();
                                String order_no = leaseCarSubmitBean.getOrder_no();
                                Intent intent = new Intent(OrderInfoActivity.this, ConfirmOrderActivity.class);
                                intent.putExtra("order_no", order_no);
                                intent.putExtra("total_cost", total_cost + "");
                                intent.putExtra("car_id", car.getId());
                                intent.putExtra("car_name", car.getBName());
                                intent.putExtra("img", car.getImage());
                                intent.putExtra("order_id", leaseCarSubmitBean.getOrder_id());
                                startActivity(intent);
                            }

                            @Override
                            protected void onHandleError(String msg) {
                                super.onHandleError(msg);
                                removeProgressDlg();
                            }
                        });
                    } else {
                        showToast("请输入正确的身份证号");
                    }


                }
                break;
            default:
                break;
        }
    }
}

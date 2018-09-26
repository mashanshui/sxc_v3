package com.sxcapp.www.Lease;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sxcapp.www.Bean.CodeObserver;
import com.sxcapp.www.Bean.CodeResult;
import com.sxcapp.www.CustomerView.pickerview.TimePopupWindow;
import com.sxcapp.www.Lease.Bean.LeaseCar;
import com.sxcapp.www.Lease.Bean.RecommendDetailInfo;
import com.sxcapp.www.Lease.HttpService.LeaseService;
import com.sxcapp.www.Login.LoginActivity;
import com.sxcapp.www.R;
import com.sxcapp.www.Util.Properties;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.TimeFormat;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManager;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;


/**
 * Created by wenleitao on 2017/8/7.
 * 租车推荐车源 预订界面
 */

public class BookCarActivity extends BaseActivity implements View.OnClickListener {
    private long lease_day = 1;
    private TimePopupWindow pwTime;
    private Date picker_date, g_date;
    private String car_id, store_name, img;
    private LeaseService service;
    private LeaseCar car;
    private String start_time, end_time;
    @BindView(R.id.picker_time_re)
    RelativeLayout picker_time_re;
    @BindView(R.id.lease_time_re)
    RelativeLayout lease_time_re;
    @BindView(R.id.lease_day_tv)
    TextView lease_day_tv;
    @BindView(R.id.date_tv)
    TextView date_tv;
    @BindView(R.id.day_tv)
    TextView day_tv;
    @BindView(R.id.time_tv)
    TextView time_tv;
    @BindView(R.id.g_date_tv)
    TextView g_date_tv;
    @BindView(R.id.g_day_tv)
    TextView g_day_tv;
    @BindView(R.id.g_time_tv)
    TextView g_time_tv;
    @BindView(R.id.city_name_tv)
    TextView city_name_tv;
    @BindView(R.id.store_name_tv)
    TextView store_name_tv;
    @BindView(R.id.g_city_name_tv)
    TextView g_city_name_tv;
    @BindView(R.id.g_store_name_tv)
    TextView g_store_name_tv;
    @BindView(R.id.car_name_tv)
    TextView car_name_tv;
    @BindView(R.id.car_info_tv)
    TextView car_info_tv;
    @BindView(R.id.price_tv)
    TextView price_tv;
    @BindView(R.id.g_time_re)
    RelativeLayout g_time_re;
    @BindView(R.id.car_iv)
    ImageView car_iv;
    @BindView(R.id.book_btn)
    Button book_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookcar);
        ButterKnife.bind(this);
        setTopbarLeftBackBtn();
        setTopBarTitle("预订车辆", null);
        service = RetrofitManager.RetrofitManagerBuild.INSTANCE.getInstance().creat(LeaseService.class);
        car_id = getIntent().getStringExtra("id");
        store_name = getIntent().getStringExtra("store_name");
        img = getIntent().getStringExtra("img");
        start_time = getIntent().getStringExtra("start_time");
        end_time = getIntent().getStringExtra("end_time");
        ButterKnife.bind(this);
        initViews();
        loadData();

    }

    private void loadData() {
        showProgressDlg();
        Observable<CodeResult<RecommendDetailInfo>> observable = service.getRecommendDetail(car_id);
        observable.compose(compose(this.<CodeResult<RecommendDetailInfo>>bindToLifecycle())).subscribe(new CodeObserver<RecommendDetailInfo>(this) {
            @Override
            protected void onHandleSuccess(final RecommendDetailInfo info) {
                removeProgressDlg();
                car = info.getData().get(0);
                car.setImage(img);
                city_name_tv.setText(info.getCity_name());
                g_city_name_tv.setText(info.getCity_name());
                store_name_tv.setText(store_name);
                g_store_name_tv.setText(store_name);
                car_info_tv.setText(car.getDisplacement() + car.getGearbox_type() + "|" + "乘坐" + car.getNumber_seats() + "人");
                Glide.with(BookCarActivity.this).load(Properties.baseImageUrl + img).into(car_iv);
                car_name_tv.setText(car.getBName());
                price_tv.setText(car.getDaily_average_price() + "");
                book_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (SharedData.getInstance().isLogin()) {
                            if (TimeFormat.isOneDayLater(picker_date, g_date)) {
                                if (!TimeFormat.isInStoreTime(TimeFormat.gethour(picker_date), start_time, end_time) || !TimeFormat.isInStoreTime(TimeFormat.gethour(g_date), start_time, end_time)) {
                                    showToast("营业时间为" + start_time + "-" + end_time);
                                } else {
                                    Intent intent = new Intent(BookCarActivity.this, OrderInfoActivity.class);
                                    intent.putExtra("car_bean", car);
                                    intent.putExtra("lease_day", lease_day + "");
                                    intent.putExtra("store_id", info.getStore_id());
                                    intent.putExtra("store_name", store_name);
                                    intent.putExtra("picker_time", picker_date.getTime() + "");
                                    intent.putExtra("g_time", g_date.getTime() + "");
                                    startActivity(intent);
                                }
                            } else {
                                showToast("租车时间不能小于1天");
                            }
                        } else {
                            startActivityForResult(new Intent(BookCarActivity.this, LoginActivity.class), LOGIN_REQUEST);
                        }
                    }


                });
            }
        });
    }

    private void initViews() {
        pwTime = new TimePopupWindow(this, TimePopupWindow.Type.ALL);
        picker_time_re.setOnClickListener(this);
        g_time_re.setOnClickListener(this);
        date_tv.setText(TimeFormat.getDate(TimeFormat.getNowDate()));
        day_tv.setText(TimeFormat.getDay(TimeFormat.getNowDate()));
        time_tv.setText(TimeFormat.gethour(TimeFormat.getNowDate()));
        g_date_tv.setText(TimeFormat.getDate(TimeFormat.getToDate()));
        g_day_tv.setText(TimeFormat.getDay(TimeFormat.getToDate()));
        g_time_tv.setText(TimeFormat.gethour(TimeFormat.getToDate()));
        picker_date = TimeFormat.getNowDate();
        g_date = TimeFormat.getToDate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.picker_time_re:
                pwTime.setOnTimeSelectListener(new TimePopupWindow.OnTimeSelectListener() {

                    @Override
                    public void onTimeSelect(Date date) {

                        if (date.before(TimeFormat.getNowDate())) {
                            showToast("取车时间不能早于当前时间");
                        } else {

                            if (date.before(g_date)) {
                                if (TimeFormat.isInStoreTime(TimeFormat.gethour(date), start_time, end_time)) {
                                    picker_date = date;
                                    date_tv.setText(TimeFormat.getDate(date));
                                    day_tv.setText(TimeFormat.getDay(date));
                                    time_tv.setText(TimeFormat.gethour(date));
                                    picker_date = date;
                                    lease_day = TimeFormat.getLeaseDay(picker_date, g_date);
                                    lease_day_tv.setText(lease_day + "");
                                } else {
                                    showToast("营业时间为" + start_time + "-" + end_time);
                                }

                            } else {
                                showToast("取车时间不能晚于还车时间");
                            }
                        }
                    }
                });
                pwTime.showAtLocation(picker_time_re, Gravity.BOTTOM, 0, 0, picker_date);
                break;
            case R.id.g_time_re:
                pwTime.setOnTimeSelectListener(new TimePopupWindow.OnTimeSelectListener() {

                    @Override
                    public void onTimeSelect(Date date) {

                        if (date.before(picker_date) || date.equals(picker_date)) {
                            showToast("还车时间不能早于取车时间");
                        } else {
                            if (TimeFormat.isOneDayLater(picker_date, date)) {
                                if (TimeFormat.isInStoreTime(TimeFormat.gethour(date), start_time, end_time)) {
                                    g_date_tv.setText(TimeFormat.getDate(date));
                                    g_day_tv.setText(TimeFormat.getDay(date));
                                    g_time_tv.setText(TimeFormat.gethour(date));
                                    g_date = date;
                                    lease_day = TimeFormat.getLeaseDay(picker_date, g_date);
                                    lease_day_tv.setText(lease_day + "");
                                } else {
                                    showToast("营业时间为" + start_time + "-" + end_time);
                                }
                            } else {
                                showToast("租车时间不能小于1天");
                            }
                        }
                    }
                });
                pwTime.showAtLocation(picker_time_re, Gravity.BOTTOM, 0, 0, g_date);
                break;
            default:
                break;
        }
    }
}

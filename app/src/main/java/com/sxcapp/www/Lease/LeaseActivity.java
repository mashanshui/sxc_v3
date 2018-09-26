package com.sxcapp.www.Lease;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sxcapp.www.Base.SerMap;
import com.sxcapp.www.Bean.BannerBean;
import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.CustomerView.InfinitePagerAdapter;
import com.sxcapp.www.CustomerView.InfiniteViewPager;
import com.sxcapp.www.CustomerView.XListView.Xcircleindicator;
import com.sxcapp.www.CustomerView.pickerview.TimePopupWindow;
import com.sxcapp.www.Lease.Bean.Recommend;
import com.sxcapp.www.Lease.Bean.TypeBean;
import com.sxcapp.www.Lease.HttpService.LeaseService;
import com.sxcapp.www.Login.LoginActivity;
import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.MessageCenterActivity;
import com.sxcapp.www.Util.BaseObserver;
import com.sxcapp.www.Util.Properties;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.TimeFormat;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.activity.MainActivity;
import com.sxcapp.www.webtool.RetrofitManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * 租车主界面
 * Created by wenleitao on 2017/7/4.
 */

public class LeaseActivity extends BaseActivity implements View.OnClickListener {
    private int CITY_REQUEST = 11;
    private int STORE_REQUEST = 12;
    private String city_id, city_name, store_id, store_name;
    private TimePopupWindow pwTime;
    private Date picker_date, g_date;
    private long lease_day = 1;
    private LeaseService service;
    private HashMap<String, String> idMap;
    private Recommend recommend01, recommend02;
    private Xcircleindicator mXcircleindicator;
    private String start_time, end_time;
    private String reco01_start_time, reco01_end_time, reco02_start_time, reco02_end_time;
    @BindView(R.id.banner_vp)
    InfiniteViewPager banner_vp;
    @BindView(R.id.commit_btn)
    Button choose_car_btn;
    @BindView(R.id.select_city_lin)
    LinearLayout select_city_lin;
    @BindView(R.id.select_shop_lin)
    LinearLayout select_store_lin;
    @BindView(R.id.g_select_city_lin)
    LinearLayout g_select_city_lin;
    @BindView(R.id.g_select_shop_lin)
    LinearLayout g_select_store_lin;
    @BindView(R.id.picker_time_re)
    RelativeLayout picker_time_re;
    @BindView(R.id.lease_time_re)
    RelativeLayout lease_time_re;
    @BindView(R.id.lease_day_tv)
    TextView lease_day_tv;
    @BindView(R.id.recommend_iv01)
    ImageView recommend_iv01;
    @BindView(R.id.recommend_iv02)
    ImageView recommend_iv02;
    @BindView(R.id.back_iv)
    ImageView back_iv;
    @BindView(R.id.recommend_tv01)
    TextView reco_tv01;
    @BindView(R.id.recommend_tv02)
    TextView reco_tv02;
    @BindView(R.id.price_tv)
    TextView reco_price_tv01;
    @BindView(R.id.price_tv02)
    TextView reco_price_tv02;
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
    @BindView(R.id.g_time_re)
    RelativeLayout g_time_re;
    @BindView(R.id.user_iv)
    ImageView user_iv;
    @BindView(R.id.message_iv)
    ImageView message_iv;
    @BindView(R.id.indicator_lin)
    LinearLayout indicator_lin;
    @BindView(R.id.recommend_re)
    RelativeLayout recommend_re;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lease);
        ButterKnife.bind(this);
        idMap = new HashMap<>();
        initViews();
        loadData();
    }

    private void loadData() {
//        获取推荐车源
        service = RetrofitManager.RetrofitManagerBuild.INSTANCE.getInstance().creat(LeaseService.class);
        Observable<Result<List<Recommend>>> resultOb = service.getLeaseRecommend(0);
        resultOb.compose(compose(this.<Result<List<Recommend>>>bindToLifecycle())).subscribe(new BaseObserver<List<Recommend>>(this) {
            @Override
            protected void onHandleSuccess(List<Recommend> list) {

                List<Recommend> newList = new ArrayList<>();
                for (Recommend recommend : list) {
                    if (recommend.getVehicle_source_type() == 0) {
                        newList.add(recommend);
                    }
                }
                if (newList.size() == 0) {
                    recommend_re.setVisibility(View.GONE);
                } else if (newList.size() > 0) {
                    Glide.with(LeaseActivity.this).load(Properties.baseImageUrl + newList.get(0).getLeaseImage()).into(recommend_iv01);
                    reco_tv01.setText(newList.get(0).getBrand());
                    recommend01 = newList.get(0);
                    reco_price_tv01.setText("￥" + recommend01.getDailyAveragePrice());
                    recommend_iv01.setOnClickListener(LeaseActivity.this);
                    reco01_start_time = recommend01.getStart_time();
                    reco01_end_time = recommend01.getEnd_time();
                }
                if (newList.size() > 1) {
                    Glide.with(LeaseActivity.this).load(Properties.baseImageUrl + newList.get(1).getLeaseImage()).into(recommend_iv02);
                    reco_tv02.setText(newList.get(1).getBrand());
                    recommend02 = newList.get(1);
                    reco_price_tv02.setText("￥" + recommend02.getDailyAveragePrice());
                    recommend_iv02.setOnClickListener(LeaseActivity.this);
                    reco02_start_time = recommend02.getStart_time();
                    reco02_end_time = recommend02.getEnd_time();
                }
            }
        });
//获取轮播图
        Observable<Result<List<BannerBean>>> ob = service.getAppBanner(2);
        ob.compose(compose(this.<Result<List<BannerBean>>>bindToLifecycle())).subscribe(new BaseObserver<List<BannerBean>>(this) {
            @Override
            protected void onHandleSuccess(final List<BannerBean> bannerBeenList) {
                BannerPageAdapter adapter = new BannerPageAdapter(LeaseActivity.this, bannerBeenList);
                InfinitePagerAdapter infinitePagerAdapter = new InfinitePagerAdapter(adapter);
                if (bannerBeenList.size() > 0) {
                    if (bannerBeenList.size() > 1) {
                        banner_vp.setAdapter(infinitePagerAdapter);
                    } else {
                        banner_vp.setAdapter(adapter);
                    }
                    if (bannerBeenList.size() > 1) {
                        mXcircleindicator = new Xcircleindicator(LeaseActivity.this);
                        mXcircleindicator.setPageTotalCount(bannerBeenList.size());
                        mXcircleindicator.setFillColor(R.color.lightGray3);
                        mXcircleindicator.setStrokeColor(R.color.lightGray2);
                        mXcircleindicator.setCircleInterval(12);
                        mXcircleindicator.setRadius(12);
                        indicator_lin.addView(mXcircleindicator);
                        mXcircleindicator.setCurrentPage(0);
                        banner_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                            }

                            @Override
                            public void onPageSelected(int position) {
                                mXcircleindicator.setCurrentPage(Math.abs(position - bannerBeenList.size() * 100000) % bannerBeenList.size());
                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {

                            }
                        });
                    } else {
                        indicator_lin.setVisibility(View.GONE);
                    }
                }
            }
        });

    }

    private void initViews() {
        pwTime = new TimePopupWindow(this, TimePopupWindow.Type.ALL);
        back_iv.setOnClickListener(this);
        user_iv.setOnClickListener(this);
        message_iv.setOnClickListener(this);
        select_city_lin.setOnClickListener(this);
        select_store_lin.setOnClickListener(this);
        g_select_city_lin.setOnClickListener(this);
        g_select_store_lin.setOnClickListener(this);
        picker_time_re.setOnClickListener(this);
        g_time_re.setOnClickListener(this);
        choose_car_btn.setOnClickListener(this);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.select_city_lin:
                Intent intent_city = new Intent(this, SelectCityActivity.class);
                startActivityForResult(intent_city, CITY_REQUEST);
                break;
            case R.id.select_shop_lin:
                if (TextUtils.isEmpty(city_name)) {
                    showToast("请先选择取车城市");
                } else {
                    Intent intent_store = new Intent(this, SelectStoreActivity.class);
                    intent_store.putExtra("city_id", city_id);
                    startActivityForResult(intent_store, STORE_REQUEST);
                }
                break;
            case R.id.g_select_city_lin:
                Intent intent_g_city = new Intent(this, SelectCityActivity.class);
                startActivityForResult(intent_g_city, CITY_REQUEST);
                break;
            case R.id.g_select_shop_lin:
                if (TextUtils.isEmpty(city_name)) {
                    showToast("请先选择还车城市");
                } else {
                    Intent intent_g_store = new Intent(this, SelectStoreActivity.class);
                    intent_g_store.putExtra("city_id", city_id);
                    startActivityForResult(intent_g_store, STORE_REQUEST);
                }
                break;
            case R.id.picker_time_re:
                if (store_id != null) {
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
                } else {
                    showToast("请先选择门店");
                }
                break;
            case R.id.g_time_re:
                if (store_id != null) {
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
                } else {
                    showToast("请先选择门店");
                }
                break;
            case R.id.commit_btn:
                if (!TextUtils.isEmpty(store_id)) {

                    if (TimeFormat.isOneDayLater(picker_date, g_date)) {
                        if (!TimeFormat.isInStoreTime(TimeFormat.gethour(picker_date), start_time, end_time) || !TimeFormat.isInStoreTime(TimeFormat.gethour(g_date), start_time, end_time)) {
                            showToast("营业时间为" + start_time + "-" + end_time);
                        } else {
                            showProgressDlg();
                            getTypeId();
                        }
                    } else {
                        showToast("租车时间不能小于1天");
                    }
                } else {
                    if (city_name == null) {
                        showToast("请选择取车换车城市");
                    }
                }
                break;
            case R.id.back_iv:
                finish();
                break;
            case R.id.recommend_iv01:
                Intent intent = new Intent(LeaseActivity.this, BookCarActivity.class);
                intent.putExtra("id", recommend01.getLicense_plate_number());
                intent.putExtra("store_name", recommend01.getStore_name());
                intent.putExtra("img", recommend01.getLeaseImage());
                intent.putExtra("start_time", reco01_start_time);
                intent.putExtra("end_time", reco01_end_time);
                startActivity(intent);
                break;
            case R.id.recommend_iv02:
                Intent intent02 = new Intent(LeaseActivity.this, BookCarActivity.class);
                intent02.putExtra("id", recommend02.getLicense_plate_number());
                intent02.putExtra("store_name", recommend02.getStore_name());
                intent02.putExtra("img", recommend02.getLeaseImage());
                intent02.putExtra("start_time", reco02_start_time);
                intent02.putExtra("end_time", reco02_end_time);
                startActivity(intent02);
                break;
            case R.id.user_iv:
                isToLogin();
                break;
            case R.id.message_iv:
                if (SharedData.getInstance().isLogin()) {
                    Intent intent_message = new Intent(this, MessageCenterActivity.class);
                    startActivity(intent_message);
                } else {
                    startActivityForResult(new Intent(this, LoginActivity.class), LOGIN_REQUEST);
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void handleLogin() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("from", "user");
        startActivity(intent);
    }

    /**
     * 获取不同车型分类对应ID
     */
    public void getTypeId() {
        Observable<Result<List<TypeBean>>> observable = service.getLeaseCarTypeId(0);
        observable.compose(compose(this.<Result<List<TypeBean>>>bindToLifecycle())).subscribe(new BaseObserver<List<TypeBean>>(this) {
            @Override
            protected void onHandleSuccess(List<TypeBean> typeBeen) {
                removeProgressDlg();
                for (TypeBean bean : typeBeen) {
                    idMap.put(bean.getVehicle_model_name(), bean.getId());
                }
                SerMap serMap = new SerMap();
                serMap.setMap(idMap);
                Intent intent = new Intent(LeaseActivity.this, ChooseCarActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("map", serMap);
                bundle.putString("store_id", store_id);
                bundle.putString("picker_time", picker_date.getTime() + "");
                bundle.putString("g_time", g_date.getTime() + "");
                bundle.putString("lease_day", lease_day + "");
                bundle.putString("store_name", store_name + "");
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            protected void onHandleError(String msg) {
                super.onHandleError(msg);
                removeProgressDlg();
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CITY_REQUEST && resultCode == Activity.RESULT_OK) {
            if (!TextUtils.isEmpty(city_id)) {
                if (!city_id.equals(data.getStringExtra("id"))) {
                    store_name_tv.setText("");
                    g_store_name_tv.setText("");
                    store_id = "";
                }
            }
            city_name = data.getStringExtra("name");
            city_id = data.getStringExtra("id");
            city_name_tv.setText(city_name);
            g_city_name_tv.setText(city_name);
        } else if (requestCode == STORE_REQUEST && resultCode == Activity.RESULT_OK) {
            store_name = data.getStringExtra("name");
            store_id = data.getStringExtra("id");
            store_name_tv.setText(store_name);
            g_store_name_tv.setText(store_name);
            start_time = data.getStringExtra("start_time");
            end_time = data.getStringExtra("end_time");
        }
    }

}

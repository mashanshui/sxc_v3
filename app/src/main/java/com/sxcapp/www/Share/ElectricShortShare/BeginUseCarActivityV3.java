package com.sxcapp.www.Share.ElectricShortShare;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.CustomerView.BatteryView;
import com.sxcapp.www.Interface.MapLoiIml;
import com.sxcapp.www.MyApplication;
import com.sxcapp.www.R;
import com.sxcapp.www.Share.Bean.ElecShortBeginUseCarBeanV3;
import com.sxcapp.www.Share.Bean.ElecShortPreCostBeanV3;
import com.sxcapp.www.Share.Bean.UnLockCarIsPhoto;
import com.sxcapp.www.Share.HttpService.ShareService;
import com.sxcapp.www.Share.SelectGStoreActivityV3;
import com.sxcapp.www.Share.StoreDetailActivityV3;
import com.sxcapp.www.Share.UploadCarInfoActivityV3;
import com.sxcapp.www.Util.AndroidTool;
import com.sxcapp.www.Util.MapUtilContinue;
import com.sxcapp.www.Util.SoundsUtil;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.Util.TimeFormat;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManagerV3;


import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * Created by wenleitao on 2018/4/19.
 */

public class BeginUseCarActivityV3 extends BaseActivity implements View.OnClickListener, MapLoiIml {
    @BindView(R.id.car_iv)
    ImageView car_iv;
    @BindView(R.id.license_num_tv)
    TextView license_num_tv;
    @BindView(R.id.car_name_tv)
    TextView car_name_tv;
    @BindView(R.id.endurance_tv)
    TextView endurance_tv;
    @BindView(R.id.car_info_tv)
    TextView car_info_tv;
    @BindView(R.id.battery_iv)
    BatteryView battery_iv;
    @BindView(R.id.battery_tv)
    TextView battery_tv;
    @BindView(R.id.fetch_store_name_tv)
    TextView fetch_store_name_tv;
    @BindView(R.id.g_store_name_tv)
    TextView g_store_name_tv;
    @BindView(R.id.cancel_order_lin)
    LinearLayout cancel_order_lin;
    @BindView(R.id.blow_lin)
    LinearLayout blow_lin;
    @BindView(R.id.begin_lin)
    LinearLayout begin_lin;
    @BindView(R.id.count_down_tv)
    TextView count_down_tv;
    @BindView(R.id.total_cost_tv)
    TextView total_cost_tv;
    @BindView(R.id.mileage_info_tv)
    TextView mileage_info_tv;
    @BindView(R.id.mileage_cost_tv)
    TextView mileage_cost_tv;
    @BindView(R.id.duration_info_tv)
    TextView duration_info_tv;
    @BindView(R.id.duration_cost_tv)
    TextView duration_cost_tv;
    @BindView(R.id.no_deductible_cost_tv)
    TextView no_deductible_cost_tv;
    @BindView(R.id.cost_info_lin)
    LinearLayout cost_info_lin;
    @BindView(R.id.end_appoint_btn)
    Button end_appoint_btn;
    @BindView(R.id.short_lock_lin)
    LinearLayout short_lock_lin;
    @BindView(R.id.unlock_car_lin)
    LinearLayout unlock_car_lin;
    @BindView(R.id.change_g_lo_tv)
    TextView change_g_lo_tv;
    @BindView(R.id.car_color_iv)
    ImageView car_color_iv;
    @BindView(R.id.fetch_store_re)
    RelativeLayout fetch_store_re;
    @BindView(R.id.select_g_store_re)
    RelativeLayout select_g_store_re;


    private String order_no, car_id, fetch_store_id, g_store_id;
    private ShareService service;
    private double lat, lng, fetch_store_lat, fetch_store_lng;
    private MapUtilContinue mapUtil;
    private int time_limit;
    private Boolean COUNT_DOWN_GOING = true;
    private long netNowDate, order_date;
    private static final int CHANGE_COUNT_DOWN = 11;
    public static final int COST_STATISTIC = 12;
    public static final int CHANGE_RETURN_STORE = 33;
    private Thread thread_time = new Thread(new Runnable() {
        @Override
        public void run() {
            netNowDate = TimeFormat.getNetNowDate();
            mHandler.sendEmptyMessage(CHANGE_COUNT_DOWN);
        }
    });
    //    1.开锁用车 2.结束用车 3.临时锁车 4.临时开锁
//    order_state(0待取车 1待还车 6待支付)
    private int what_use = -1, order_state;
    private SoundsUtil soundsUtil;


    @Override
    public void handleMsg(Message msg) {
        super.handleMsg(msg);
        switch (msg.what) {
            case CHANGE_COUNT_DOWN:
                if (COUNT_DOWN_GOING) {
                    if (getCountDown(netNowDate, order_date) == null) {
                        count_down_tv.setText("已超出预约时间,请您尽快取车");
                    } else {
                        count_down_tv.setText(getCountDown(netNowDate, order_date) + "开始收费");
                        netNowDate = netNowDate + 1000;
                        mHandler.sendEmptyMessageDelayed(CHANGE_COUNT_DOWN, 1000);
                    }
                }
                break;
            case COST_STATISTIC:
                getShareCarCost();
                break;
            default:
                break;
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elec_short_begin_usecar);
        ButterKnife.bind(this);
        setTopBarColor(R.color.green);
        setTopbarLeftWhiteToMainBtn();
        StatusBarUtil.StatusBarDarkMode(this);
        setStatusView(R.color.green);
        setTopBarTitle("开始用车", null);
        order_no = getIntent().getStringExtra("order_no");
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(ShareService.class);
        mapUtil = new MapUtilContinue(this, this);
        order_state = Integer.parseInt(getIntent().getStringExtra("order_state"));
        soundsUtil = new SoundsUtil(this);
        mapUtil.LoPoi();
        loadData();
    }

    private void loadData() {
        showProgressDlg();
        Observable<CodeResultV3<ElecShortBeginUseCarBeanV3>> ob = service.getBeginUserInfoV3(order_no);
        ob.compose(compose(this.<CodeResultV3<ElecShortBeginUseCarBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<ElecShortBeginUseCarBeanV3>(this) {
            @Override
            protected void onHandleSuccess(final ElecShortBeginUseCarBeanV3 beanV3) {
                removeProgressDlg();
                int width = AndroidTool.getDeviceWidth(BeginUseCarActivityV3.this) - AndroidTool.dip2px(BeginUseCarActivityV3.this, 74);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, width / 2);
                layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                layoutParams.setMargins(0, AndroidTool.dip2px(BeginUseCarActivityV3.this, 27), 0, 0);
                car_iv.setLayoutParams(layoutParams);
                Glide.with(BeginUseCarActivityV3.this).load(beanV3.getCar_image()).into(car_iv);
                license_num_tv.setText(beanV3.getLicense_plate_number());
                car_name_tv.setText(beanV3.getCar_name());
                endurance_tv.setText("续航:" + beanV3.getLife_km() + "公里");
                car_info_tv.setText("新能源|" + "乘坐" + beanV3.getNumber_seats() + "人");
                battery_iv.setPower(beanV3.getDump_energy());
                battery_tv.setText(beanV3.getDump_energy() + "%");
                time_limit = Integer.parseInt(beanV3.getAppoint_time());
                fetch_store_name_tv.setText(beanV3.getFetch_store_name());
                fetch_store_id = beanV3.getFetch_store_id();
                car_color_iv.setBackgroundColor(Color.parseColor("#" + beanV3.getCar_color()));
                fetch_store_lat = beanV3.getFetch_latitude();
                fetch_store_lng = beanV3.getFetch_longitude();
                g_store_id = beanV3.getReturn_store_id();
                g_store_name_tv.setText(beanV3.getReturn_store_name());
                order_date = TimeFormat.getTimeStamp(beanV3.getOrder_time());
                cancel_order_lin.setOnClickListener(BeginUseCarActivityV3.this);
                blow_lin.setOnClickListener(BeginUseCarActivityV3.this);
                begin_lin.setOnClickListener(BeginUseCarActivityV3.this);
                change_g_lo_tv.setOnClickListener(BeginUseCarActivityV3.this);
                car_id = beanV3.getSource_id();
                if (order_state == 0) {
                    thread_time.start();
                } else if (order_state == 1) {
                    mHandler.sendEmptyMessage(COST_STATISTIC);
                    count_down_tv.setVisibility(View.GONE);
                    end_appoint_btn.setVisibility(View.VISIBLE);
                    cancel_order_lin.setVisibility(View.GONE);
                    short_lock_lin.setVisibility(View.VISIBLE);
                    begin_lin.setVisibility(View.GONE);
                    unlock_car_lin.setVisibility(View.VISIBLE);
                    unlock_car_lin.setOnClickListener(BeginUseCarActivityV3.this);
                    short_lock_lin.setOnClickListener(BeginUseCarActivityV3.this);
                    end_appoint_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            what_use = 2;
//                            end_appoint_btn.setClickable(false);
//                            getPermission();
                            if (AndroidTool.isGetLocationPermission(BeginUseCarActivityV3.this)) {
                                showProgressDlg();
                                end_appoint_btn.setClickable(false);
                                endUse();
                            } else {
                                getPermission();
                            }

                        }
                    });
                }
                fetch_store_re.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(BeginUseCarActivityV3.this, StoreDetailActivityV3.class);
                        intent.putExtra("from", "elec");
                        intent.putExtra("store_id", beanV3.getFetch_store_id());
                        startActivity(intent);
                    }
                });
                select_g_store_re.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(BeginUseCarActivityV3.this, StoreDetailActivityV3.class);
                        intent.putExtra("from", "elec");
                        intent.putExtra("store_id", beanV3.getReturn_store_id());
                        startActivity(intent);
                    }
                });
                getPermission();
            }
        });
    }

    private void cancelOrder() {
        showProgressDlg();
        cancel_order_lin.setClickable(false);
        Observable<CodeResultV3<Object>> ob = service.elecShortCancelOrder(order_no);
        ob.compose(compose(this.<CodeResultV3<Object>>bindToLifecycle())).subscribe(new CodeObserverV3<Object>(this) {
            @Override
            protected void onHandleSuccess(Object o) {
                removeProgressDlg();
                cancel_order_lin.setClickable(true);
                finish();
            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<Object> value) {
                super.onHandleError(msg, value);
                cancel_order_lin.setClickable(true);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                cancel_order_lin.setClickable(true);
            }
        });
    }

    /**
     * 鸣笛寻车
     */
    private void blowFindCar() {
        showProgressDlg();
        Observable<CodeResultV3<Object>> ob = service.getCarBlowV3(order_no);
        ob.compose(compose(this.<CodeResultV3<Object>>bindToLifecycle())).subscribe(new CodeObserverV3<Object>(this) {
            @Override
            protected void onHandleSuccess(Object o) {
                removeProgressDlg();
                blow_lin.setClickable(true);
                showToast("鸣笛成功");
            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<Object> value) {
                super.onHandleError(msg, value);
                blow_lin.setClickable(true);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                blow_lin.setClickable(true);
            }
        });
    }

    /**
     * 一键开锁
     */
    private void shortUnlock() {
        Observable<CodeResultV3<Object>> ob = service.shortUnlockV3(order_no, lat + "", lng + "");
        ob.compose(compose(this.<CodeResultV3<Object>>bindToLifecycle())).subscribe(new CodeObserverV3<Object>(this) {
            @Override
            protected void onHandleSuccess(Object o) {
                removeProgressDlg();
                showToast("开锁成功");
                soundsUtil.playOpenSound();
                unlock_car_lin.setClickable(true);

            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<Object> value) {
                super.onHandleError(msg, value);
                unlock_car_lin.setClickable(true);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                unlock_car_lin.setClickable(true);
            }
        });
    }

    /**
     * 临时锁车
     */
    private void shortLock() {
        Observable<CodeResultV3<Object>> ob = service.shortLockV3(order_no, lat + "", lng + "");
        ob.compose(compose(this.<CodeResultV3<Object>>bindToLifecycle())).subscribe(new CodeObserverV3<Object>(this) {
            @Override
            protected void onHandleSuccess(Object o) {
                removeProgressDlg();
                short_lock_lin.setClickable(true);
                showToast("锁车成功");
                soundsUtil.playCloseSound();

            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<Object> value) {
                super.onHandleError(msg, value);
                short_lock_lin.setClickable(true);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                short_lock_lin.setClickable(true);
            }
        });
    }

    /**
     * 开锁用车
     */
    private void unLockCar() {
        Observable<CodeResultV3<UnLockCarIsPhoto>> ob = service.getCarUnLockV3(order_no, lat + "", lng + "");
        ob.compose(compose(this.<CodeResultV3<UnLockCarIsPhoto>>bindToLifecycle())).subscribe(new CodeObserverV3<UnLockCarIsPhoto>(this) {
            @Override
            protected void onHandleSuccess(UnLockCarIsPhoto isPhoto) {
                removeProgressDlg();
                begin_lin.setClickable(true);
                mHandler.sendEmptyMessage(COST_STATISTIC);
                count_down_tv.setVisibility(View.GONE);
                end_appoint_btn.setVisibility(View.VISIBLE);
                cancel_order_lin.setVisibility(View.GONE);
                short_lock_lin.setVisibility(View.VISIBLE);
                begin_lin.setVisibility(View.GONE);
                unlock_car_lin.setVisibility(View.VISIBLE);
                unlock_car_lin.setOnClickListener(BeginUseCarActivityV3.this);
                short_lock_lin.setOnClickListener(BeginUseCarActivityV3.this);
                end_appoint_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (AndroidTool.isGetLocationPermission(BeginUseCarActivityV3.this)) {
                            if (lat != 0) {
                                showProgressDlg();
                                end_appoint_btn.setClickable(false);
                                endUse();
                            } else {
                                showToast("获取定位失败,请稍后重试");
                            }
                        } else {
                            getPermission();
                        }

                    }
                });
            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<UnLockCarIsPhoto> value) {
                super.onHandleError(msg, value);
                begin_lin.setClickable(true);
                if ("994".equals(value.getCode())) {
                    if (value.getObj().getImage_flag() == 0) {
                        Intent intent = new Intent(BeginUseCarActivityV3.this, UploadCarInfoActivityV3.class);
                        intent.putExtra("order_no", order_no);
                        intent.putExtra("car_id", car_id);
                        intent.putExtra("type", 0);
                        intent.putExtra("order_type", 1);
                        startActivityForResult(intent, 23);

                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                begin_lin.setClickable(true);
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_order_lin:
                cancelOrder();
                break;
            case R.id.blow_lin:
                blow_lin.setClickable(false);
                blowFindCar();
                break;
            case R.id.begin_lin:
                if (AndroidTool.isGetLocationPermission(BeginUseCarActivityV3.this)) {
                    showProgressDlg();
                    begin_lin.setClickable(false);
                    unLockCar();
                } else {
                    getPermission();
                }
                break;
            case R.id.short_lock_lin:

                if (AndroidTool.isGetLocationPermission(BeginUseCarActivityV3.this)) {
                    showProgressDlg();
                    short_lock_lin.setClickable(false);
                    shortLock();
                } else {
                    getPermission();
                }
                break;
            case R.id.unlock_car_lin:

                if (AndroidTool.isGetLocationPermission(BeginUseCarActivityV3.this)) {
                    showProgressDlg();
                    unlock_car_lin.setClickable(false);
                    shortUnlock();
                } else {
                    getPermission();
                }
                break;
            case R.id.change_g_lo_tv:
                Intent intent = new Intent(BeginUseCarActivityV3.this, SelectGStoreActivityV3.class);
                intent.putExtra("fetch_store_id", fetch_store_id);
                intent.putExtra("fetch_store_lat", fetch_store_lat);
                intent.putExtra("fetch_store_lng", fetch_store_lng);
                intent.putExtra("from", "elec_short_order");
                intent.putExtra("order_no", order_no);
                intent.putExtra("order_type", 1);
                startActivityForResult(intent, 101);
                break;
                default:
                    break;
        }

    }

    public void getPermission() {
        PackageManager pkgManager = getPackageManager();
        boolean locationPermission =
                pkgManager.checkPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION, getPackageName()) == PackageManager.PERMISSION_GRANTED;
        if (Build.VERSION.SDK_INT >= 23 && !locationPermission) {
            PermissionGen.with(this)
                    .addRequestCode(100)
                    .permissions(
                            android.Manifest.permission.ACCESS_COARSE_LOCATION)
                    .request();
        } else {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 100)
    public void doSomething() {
    }


    @PermissionFail(requestCode = 100)
    public void doFailSomething() {
        showGreenAlertDlg("请开启定位权限", R.string.ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAlertDlg();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.fromParts("package", getPackageName(), null));
                startActivity(intent);
            }
        }, R.string.cancel, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAlertDlg();

            }
        }, true);
    }

    private void getShareCarCost() {
        Observable<CodeResultV3<ElecShortPreCostBeanV3>> ob = service.getElecShorePreCostV3(order_no);
        ob.compose(compose(this.<CodeResultV3<ElecShortPreCostBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<ElecShortPreCostBeanV3>(this) {
            @Override
            protected void onHandleSuccess(ElecShortPreCostBeanV3 costBeanV3) {
                cost_info_lin.setVisibility(View.VISIBLE);
                total_cost_tv.setText(costBeanV3.getTotal_cost() + "元");
                mileage_info_tv.setText("里程费(" +
                        costBeanV3.getUse_km() + "公里*" + costBeanV3.getSingle_km() + "/公里)");
                mileage_cost_tv.setText(costBeanV3.getKm_cost() + "元");
                duration_info_tv.setText("时长费(" + costBeanV3.getUse_cen() + "分钟*" + costBeanV3.getSingle_cen() + "/分钟)");
                duration_cost_tv.setText(costBeanV3.getCen_cost() + "元");
                no_deductible_cost_tv.setText(costBeanV3.getNo_deductible() + "元");
                mHandler.sendEmptyMessageDelayed(COST_STATISTIC, 2 * 60 * 1000);
                endurance_tv.setText("续航:" + costBeanV3.getRemain_mileage() + "公里");
                battery_iv.setPower(Integer.parseInt(costBeanV3.getPower()));
                battery_tv.setText(costBeanV3.getPower() + "%");

            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<ElecShortPreCostBeanV3> value) {
                super.onHandleError(msg, value);
                mHandler.sendEmptyMessageDelayed(COST_STATISTIC, 2 * 60 * 1000);

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mHandler.sendEmptyMessageDelayed(COST_STATISTIC, 2 * 60 * 1000);
            }
        });

    }

    private void endUse() {
        Observable<CodeResultV3<UnLockCarIsPhoto>> ob = service.elecShortEnduse(order_no, lat + "", lng + "");
        ob.compose(compose(this.<CodeResultV3<UnLockCarIsPhoto>>bindToLifecycle())).subscribe(new CodeObserverV3<UnLockCarIsPhoto>(this) {
            @Override
            protected void onHandleSuccess(UnLockCarIsPhoto isPhoto) {
                removeProgressDlg();
                mHandler.removeCallbacksAndMessages(null);
                end_appoint_btn.setClickable(true);
                Intent intent = new Intent(BeginUseCarActivityV3.this, PayActivityV3.class);
                intent.putExtra("order_no", order_no);
                startActivity(intent);
            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<UnLockCarIsPhoto> value) {
                super.onHandleError(msg, value);
                end_appoint_btn.setClickable(true);
                if ("993".equals(value.getCode())) {
                    if (value.getObj() == null) {
                        showToast(value.getMsg());
                    } else {
                        if (value.getObj().getImage_flag() == 0) {
                            Intent intent = new Intent(BeginUseCarActivityV3.this, UploadCarInfoActivityV3.class);
                            intent.putExtra("order_no", order_no);
                            intent.putExtra("car_id", car_id);
                            intent.putExtra("type", 1);
                            intent.putExtra("order_type", 1);
                            startActivityForResult(intent, 24);
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                end_appoint_btn.setClickable(true);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 23) {
                showProgressDlg();
                begin_lin.setClickable(false);
                unLockCar();
            } else if (requestCode == 24) {
                showProgressDlg();
                end_appoint_btn.setClickable(false);
                endUse();
            } else if (requestCode == 101) {
                g_store_name_tv.setText(data.getStringExtra("g_store_name"));
            }
        }
    }

    /**
     * @param nowDate
     * @param order_time
     * @return 倒计时
     */
    public String getCountDown(long nowDate, long order_time) {
        long diff = (nowDate - order_time) / 1000;
        long residue = ((order_time + time_limit * 60 * 1000) - nowDate) / 1000;
        if (((diff / (time_limit * 60) > 1) || (diff / (time_limit * 60) == 1))) {
            COUNT_DOWN_GOING = false;
            return null;
        } else if (residue / 60 < 10) {
            if ((residue % 60) < 10) {
                return "0" + residue / 60 + ":" + "0" + residue % 60;
            } else {
                return "0" + residue / 60 + ":" + residue % 60;
            }
        } else {
            if ((residue % 60) < 10) {
                return residue / 60 + ":" + "0" + residue % 60;
            } else {
                return residue / 60 + ":" + residue % 60;
            }
        }

    }


    @Override
    public void onSuccess() {
        lat = mapUtil.getLat();
        lng = mapUtil.getLng();

    }

    @Override
    public void onFail() {


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        mapUtil.stopLocation();
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
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}

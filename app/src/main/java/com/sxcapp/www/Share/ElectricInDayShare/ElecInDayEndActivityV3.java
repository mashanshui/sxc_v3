package com.sxcapp.www.Share.ElectricInDayShare;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
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
import com.sxcapp.www.CustomerView.BatteryView;
import com.sxcapp.www.CustomerView.ExpandableLayout;
import com.sxcapp.www.Interface.MapLoiIml;
import com.sxcapp.www.MyApplication;
import com.sxcapp.www.R;
import com.sxcapp.www.Share.Bean.ElecInDayEndInfoBeanV3;
import com.sxcapp.www.Share.Bean.UnLockCarIsPhoto;
import com.sxcapp.www.Share.HttpService.ShareService;
import com.sxcapp.www.Share.SelectGStoreActivityV3;
import com.sxcapp.www.Share.StoreDetailActivityV3;
import com.sxcapp.www.Share.UploadCarInfoActivityV3;
import com.sxcapp.www.UserCenter.OrderRemarkActivityV3;
import com.sxcapp.www.Util.AndroidTool;
import com.sxcapp.www.Util.MapUtilContinue;
import com.sxcapp.www.Util.SoundsUtil;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManagerV3;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * 电车整租结束用车界面
 * Created by wenleitao on 2018/4/25.
 */

public class ElecInDayEndActivityV3 extends BaseActivity implements MapLoiIml, View.OnClickListener {

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
    @BindView(R.id.short_lock_lin)
    LinearLayout short_lock_lin;
    @BindView(R.id.unlock_car_lin)
    LinearLayout unlock_car_lin;
    @BindView(R.id.blow_lin)
    LinearLayout blow_lin;
    @BindView(R.id.car_color_iv)
    ImageView car_color_iv;
    @BindView(R.id.cost_info_lin)
    LinearLayout cost_info_lin;
    @BindView(R.id.hide_cost_re)
    RelativeLayout hide_cost_re;
    @BindView(R.id.hide_cost_iv)
    ImageView hide_cost_iv;
    @BindView(R.id.end_appoint_btn)
    Button end_appoint_btn;
    @BindView(R.id.total_cost_tv)
    TextView total_cost_tv;
    @BindView(R.id.expandable_view)
    ExpandableLayout expandable_view;
    @BindView(R.id.change_g_lo_tv)
    TextView change_g_lo_tv;
    @BindView(R.id.fetch_store_re)
    RelativeLayout fetch_store_re;
    @BindView(R.id.select_g_store_re)
    RelativeLayout select_g_store_re;
    private double lat, lng;
    //    1.开锁用车 2.结束用车 3.临时锁车 4.临时开锁
    private int what_use;
    private String order_no, from, car_id, fetch_store_id;
    private MapUtilContinue mapUtil;
    private ShareService service;
    private boolean is_hide = false;
    private double fetch_store_lat, fetch_store_lng;
    private SoundsUtil soundsUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elec_inday_end_v3);
        ButterKnife.bind(this);
        setTopbarLeftWhiteToMainBtn();
        setTopBarTitle("结束用车", null);
        setStatusView(R.color.green);
        StatusBarUtil.StatusBarDarkMode(this);
        setTopBarColor(R.color.green);
        mapUtil = new MapUtilContinue(this, this);
        order_no = getIntent().getStringExtra("order_no");
        from = getIntent().getStringExtra("from");
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(ShareService.class);
        soundsUtil = new SoundsUtil(this);
        int width = AndroidTool.getDeviceWidth(ElecInDayEndActivityV3.this) - AndroidTool.dip2px(ElecInDayEndActivityV3.this, 74);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, width / 2);
        layoutParams.setMargins(0, AndroidTool.dip2px(ElecInDayEndActivityV3.this, 27), 0, 0);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        car_iv.setLayoutParams(layoutParams);
        mapUtil.LoPoi();
        loadData();
    }

    private void loadData() {
        showProgressDlg();
        Observable<CodeResultV3<ElecInDayEndInfoBeanV3>> ob = service.getElecInDayEndInfoV3(order_no);
        ob.compose(compose(this.<CodeResultV3<ElecInDayEndInfoBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<ElecInDayEndInfoBeanV3>(this) {
            @Override
            protected void onHandleSuccess(final ElecInDayEndInfoBeanV3 beanV3) {
                removeProgressDlg();
                Glide.with(ElecInDayEndActivityV3.this).load(beanV3.getCar_image()).into(car_iv);
                car_id = beanV3.getSource_id();
                license_num_tv.setText(beanV3.getLicense_plate_number());
                car_name_tv.setText(beanV3.getCar_name());
                car_color_iv.setBackgroundColor(Color.parseColor("#" + beanV3.getCar_color()));
                endurance_tv.setText("续航:" + beanV3.getLife_km() + "公里");
                car_info_tv.setText("新能源|" + "乘坐" + beanV3.getNumber_seats() + "人");
                battery_iv.setPower(beanV3.getDump_energy());
                battery_tv.setText(beanV3.getDump_energy() + "%");
                fetch_store_name_tv.setText(beanV3.getFetch_store_name());
                g_store_name_tv.setText(beanV3.getReturn_store_name());
                fetch_store_id = beanV3.getFetch_store_id();
                fetch_store_lat = Double.parseDouble(beanV3.getFetch_latitude());
                fetch_store_lng = Double.parseDouble(beanV3.getFetch_longitude());
                for (int i = 0; i < beanV3.getSettings().size(); i++) {
                    TextView appointTime_tv = new TextView(ElecInDayEndActivityV3.this);
                    appointTime_tv.setText((i + 1) + "." + beanV3.getSettings().get(i));
                    appointTime_tv.setTextColor(Color.parseColor("#c4000000"));
                    appointTime_tv.setTextSize(13);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    appointTime_tv.setLayoutParams(params);
                    appointTime_tv.setPadding(AndroidTool.dip2px(ElecInDayEndActivityV3.this, 12), AndroidTool.dip2px(ElecInDayEndActivityV3.this, 12), 0, 0);
                    cost_info_lin.addView(appointTime_tv, i);
                }
                total_cost_tv.setText(beanV3.getTotal_cost() + "元");
                blow_lin.setOnClickListener(ElecInDayEndActivityV3.this);
                short_lock_lin.setOnClickListener(ElecInDayEndActivityV3.this);
                unlock_car_lin.setOnClickListener(ElecInDayEndActivityV3.this);
                hide_cost_re.setOnClickListener(ElecInDayEndActivityV3.this);
                end_appoint_btn.setOnClickListener(ElecInDayEndActivityV3.this);
                expandable_view.setOnClickListener(ElecInDayEndActivityV3.this);
                change_g_lo_tv.setOnClickListener(ElecInDayEndActivityV3.this);
                fetch_store_re.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ElecInDayEndActivityV3.this, StoreDetailActivityV3.class);
                        intent.putExtra("from", "elec");
                        intent.putExtra("store_id", beanV3.getFetch_store_id());
                        startActivity(intent);
                    }
                });
                select_g_store_re.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ElecInDayEndActivityV3.this, StoreDetailActivityV3.class);
                        intent.putExtra("from", "elec");
                        intent.putExtra("store_id", beanV3.getReturn_store_id());
                        startActivity(intent);
                    }
                });
                getPermission();
            }
        });
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            MyApplication.getInstance().gotoMainActivity();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.short_lock_lin:
                if (AndroidTool.isGetLocationPermission(ElecInDayEndActivityV3.this)) {
                    if (lat != 0) {
                        showProgressDlg();
                        short_lock_lin.setClickable(false);
                        shortLock();
                    } else {
                        showToast("获取定位失败,请稍后重试");
                    }
                } else {
                    getPermission();
                }
                break;
            case R.id.unlock_car_lin:
                if (AndroidTool.isGetLocationPermission(ElecInDayEndActivityV3.this)) {
                    if (lat != 0) {
                        showProgressDlg();
                        unlock_car_lin.setClickable(false);
                        shortUnlock();
                    } else {
                        showToast("获取定位失败,请稍后重试");
                    }
                } else {
                    getPermission();
                }
                break;
            case R.id.blow_lin:
                blow_lin.setClickable(false);
                blowFindCar();
                break;
            case R.id.hide_cost_re:
                if (is_hide) {
                    expandable_view.expand();
                    hide_cost_iv.setBackgroundResource(R.mipmap.expand_icon_v3);
                    is_hide = false;
                } else {
                    expandable_view.collapse();
                    hide_cost_iv.setBackgroundResource(R.mipmap.hide_icon_v3);
                    is_hide = true;
                }
                break;
            case R.id.end_appoint_btn:

                if (AndroidTool.isGetLocationPermission(ElecInDayEndActivityV3.this)) {
                    if (lat != 0) {
                        showProgressDlg();
                        end_appoint_btn.setClickable(false);
                        endCar();
                    } else {
                        showToast("获取定位失败,请稍后重试");
                    }
                } else {
                    getPermission();
                }
                break;
            case R.id.change_g_lo_tv:
                Intent intent = new Intent(ElecInDayEndActivityV3.this, SelectGStoreActivityV3.class);
                intent.putExtra("fetch_store_id", fetch_store_id);
                intent.putExtra("fetch_store_lat", fetch_store_lat);
                intent.putExtra("fetch_store_lng", fetch_store_lng);
                intent.putExtra("from", "elec_inday_order");
                intent.putExtra("order_no", order_no);
                intent.putExtra("order_type", 2);
                startActivityForResult(intent, 101);
                break;
            default:
                break;


        }
    }


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

    private void endCar() {
        Observable<CodeResultV3<UnLockCarIsPhoto>> ob = service.elecInDayEndCarV3(order_no);
        ob.compose(compose(this.<CodeResultV3<UnLockCarIsPhoto>>bindToLifecycle())).subscribe(new CodeObserverV3<UnLockCarIsPhoto>(this) {
            @Override
            protected void onHandleSuccess(UnLockCarIsPhoto isPhoto) {
                removeProgressDlg();
                end_appoint_btn.setClickable(true);
                showToast("还车成功");
                Intent intent = new Intent(ElecInDayEndActivityV3.this, OrderRemarkActivityV3.class);
                intent.putExtra("order_type", 2);
                intent.putExtra("order_no", order_no);
                intent.putExtra("from", "order");
                startActivity(intent);
            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<UnLockCarIsPhoto> value) {
                super.onHandleError(msg, value);
                end_appoint_btn.setClickable(true);
                if ("993".equals(value.getCode())) {
                    if (value.getObj().getImage_flag() == 0) {
                        Intent intent = new Intent(ElecInDayEndActivityV3.this, UploadCarInfoActivityV3.class);
                        intent.putExtra("order_no", order_no);
                        intent.putExtra("car_id", car_id);
                        intent.putExtra("type", 1);
                        intent.putExtra("order_type", 2);
                        startActivityForResult(intent, 23);

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
                showToast("鸣笛成功");
                blow_lin.setClickable(true);
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
     * 临时锁车
     */
    private void shortLock() {

        Observable<CodeResultV3<Object>> ob = service.shortLockV3(order_no, lat + "", lng + "");
        ob.compose(compose(this.<CodeResultV3<Object>>bindToLifecycle())).subscribe(new CodeObserverV3<Object>(this) {
            @Override
            protected void onHandleSuccess(Object o) {
                removeProgressDlg();
                showToast("锁车成功");
                soundsUtil.playCloseSound();
                short_lock_lin.setClickable(true);

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

    public void getPermission() {
        PackageManager pkgManager = getPackageManager();
        boolean sdCardWritePermission =
                pkgManager.checkPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION, getPackageName()) == PackageManager.PERMISSION_GRANTED;
        if (Build.VERSION.SDK_INT >= 23 && !sdCardWritePermission) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 23) {
                showProgressDlg();
                end_appoint_btn.setClickable(false);
                endCar();
            } else if (requestCode == 101) {
                g_store_name_tv.setText(data.getStringExtra("g_store_name"));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapUtil.stopLocation();
    }
}

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
import android.text.TextUtils;
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
import com.sxcapp.www.Interface.MapLoiIml;
import com.sxcapp.www.R;
import com.sxcapp.www.Share.Bean.ElecInDayEndInfoBeanV3;
import com.sxcapp.www.Share.Bean.UnLockCarIsPhoto;
import com.sxcapp.www.Share.ElectricShortShare.BeginUseCarActivityV3;
import com.sxcapp.www.Share.HttpService.ShareService;
import com.sxcapp.www.Share.StoreDetailActivityV3;
import com.sxcapp.www.Share.UploadCarInfoActivityV3;
import com.sxcapp.www.Util.AndroidTool;
import com.sxcapp.www.Util.MapUtilContinue;
import com.sxcapp.www.Util.SharedData;
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
 * 电车整租拍照开锁界面
 * Created by wenleitao on 2018/4/25.
 */

public class ElecExistInDayOrderActivityV3 extends BaseActivity implements MapLoiIml {
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
    @BindView(R.id.no_deductible_cost_tv)
    TextView no_deductible_cost_tv;
    @BindView(R.id.no_deductible_re)
    RelativeLayout no_deductible_re;
    @BindView(R.id.appoint_btn)
    Button appoint_btn;
    @BindView(R.id.check_iv)
    ImageView check_iv;
    @BindView(R.id.rent_day_type_re)
    RelativeLayout rent_day_type_re;
    @BindView(R.id.rent_day_type_tv)
    TextView rent_day_type_tv;
    @BindView(R.id.coupon_size_tv)
    TextView coupon_size_tv;
    @BindView(R.id.coupon_re)
    RelativeLayout coupon_re;
    @BindView(R.id.car_color_iv)
    ImageView car_color_iv;
    @BindView(R.id.rules_lin)
    LinearLayout rules_lin;
    @BindView(R.id.rent_day_cost_tv)
    TextView rent_day_cost_tv;
    @BindView(R.id.fetch_store_re)
    RelativeLayout fetch_store_re;
    @BindView(R.id.select_g_store_re)
    RelativeLayout select_g_store_re;

    private ShareService service;
    private String car_id, user_id;
    private int no_deductible = 1;
    private double lat, lng;
    private MapUtilContinue mapUtil;
    private String order_no;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elec_inday_exist_v3);
        setTopbarLeftWhiteBackBtn();
        setTopBarTitle("拍照开锁", null);
        StatusBarUtil.StatusBarDarkMode(this);
        setStatusView(R.color.green);
        setTopBarColor(R.color.green);
        ButterKnife.bind(this);
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(ShareService.class);
        user_id = SharedData.getInstance().getString(SharedData._user_id);
        order_no = getIntent().getStringExtra("order_no");
        mapUtil = new MapUtilContinue(this, this);
        int width = AndroidTool.getDeviceWidth(ElecExistInDayOrderActivityV3.this) - AndroidTool.dip2px(ElecExistInDayOrderActivityV3.this, 74);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, width / 2);
        layoutParams.setMargins(0, AndroidTool.dip2px(ElecExistInDayOrderActivityV3.this, 27), 0, 0);
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
                Glide.with(ElecExistInDayOrderActivityV3.this).load(beanV3.getCar_image()).into(car_iv);
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
                for (int i = 0; i < beanV3.getSettings().size(); i++) {
                    TextView rule_tv = new TextView(ElecExistInDayOrderActivityV3.this);
                    rule_tv.setText((i + 1) + "." + beanV3.getSettings().get(i));
                    rule_tv.setTextColor(Color.parseColor("#c4000000"));
                    rule_tv.setTextSize(13);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    rule_tv.setLayoutParams(params);
                    rule_tv.setPadding(AndroidTool.dip2px(ElecExistInDayOrderActivityV3.this, 12), AndroidTool.dip2px(ElecExistInDayOrderActivityV3.this, 12), 0, 0);
                    rules_lin.addView(rule_tv, i);
                }
                rent_day_type_tv.setText("租车时长" + beanV3.getDay_cost() + "天");
                rent_day_cost_tv.setText("租金" + beanV3.getSingle_cost() + "元、" + "押金" + beanV3.getDeposit_cost() + "元");
                if (TextUtils.isEmpty(beanV3.getCoupon())) {
                    coupon_size_tv.setText("未使用优惠券");
                } else {
                    coupon_size_tv.setText(beanV3.getCoupon());
                }
                no_deductible_cost_tv.setText(beanV3.getNo_deductible_setting());
                if (TextUtils.isEmpty(beanV3.getNo_deductible())) {
                    check_iv.setBackgroundResource(R.mipmap.unchecked_v3);
                } else {
                    check_iv.setBackgroundResource(R.mipmap.green_checkbox_checked_v3);
                }
                appoint_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (AndroidTool.isGetLocationPermission(ElecExistInDayOrderActivityV3.this)) {
                            if (lat != 0) {
                                showProgressDlg();
                                appoint_btn.setClickable(false);
                                unLockCar();
                            } else {
                                showToast("获取定位失败,请稍后重试");
                            }
                        } else {
                            getPermission();
                        }
                    }
                });
                fetch_store_re.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ElecExistInDayOrderActivityV3.this, StoreDetailActivityV3.class);
                        intent.putExtra("from", "elec");
                        intent.putExtra("store_id", beanV3.getFetch_store_id());
                        startActivity(intent);
                    }
                });
                select_g_store_re.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ElecExistInDayOrderActivityV3.this, StoreDetailActivityV3.class);
                        intent.putExtra("from", "elec");
                        intent.putExtra("store_id", beanV3.getReturn_store_id());
                        startActivity(intent);
                    }
                });
                getPermission();

            }
        });
    }


    private void unLockCar() {
        Observable<CodeResultV3<UnLockCarIsPhoto>> ob = service.elecInDayUnLockCarV3(order_no, lat + "", lng + "");
        ob.compose(compose(this.<CodeResultV3<UnLockCarIsPhoto>>bindToLifecycle())).subscribe(new CodeObserverV3<UnLockCarIsPhoto>(this) {
            @Override
            protected void onHandleSuccess(UnLockCarIsPhoto isPhoto) {
                appoint_btn.setClickable(true);
                removeProgressDlg();
                Intent intent = new Intent(ElecExistInDayOrderActivityV3.this, ElecInDayEndActivityV3.class);
                intent.putExtra("order_no", order_no);
                intent.putExtra("from", "unlock");
                startActivityForResult(intent, 11);
            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<UnLockCarIsPhoto> value) {
                super.onHandleError(msg, value);
                appoint_btn.setClickable(true);
                if ("994".equals(value.getCode())) {
                    if (value.getObj().getImage_flag() == 0) {
                        Intent intent = new Intent(ElecExistInDayOrderActivityV3.this, UploadCarInfoActivityV3.class);
                        intent.putExtra("order_no", order_no);
                        intent.putExtra("car_id", car_id);
                        intent.putExtra("type", 0);
                        intent.putExtra("order_type", 2);
                        startActivityForResult(intent, 23);

                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                appoint_btn.setClickable(true);
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
    public void onSuccess() {
        lat = mapUtil.getLat();
        lng = mapUtil.getLng();

    }

    @Override
    public void onFail() {


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 11) {
                finish();
            } else if (requestCode == 23) {
                showProgressDlg();
                appoint_btn.setClickable(false);
                unLockCar();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapUtil.stopLocation();
    }
}

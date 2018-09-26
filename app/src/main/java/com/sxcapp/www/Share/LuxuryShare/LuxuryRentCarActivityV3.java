package com.sxcapp.www.Share.LuxuryShare;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rd.PageIndicatorView;
import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.Interface.MapLoiIml;
import com.sxcapp.www.MyApplication;
import com.sxcapp.www.R;
import com.sxcapp.www.Share.Adapter.LuxuryAppointCarImagePageAdapterV3;
import com.sxcapp.www.Share.Bean.LuxuryRentCarInfoBeanV3;
import com.sxcapp.www.Share.Bean.UnLockCarIsPhoto;
import com.sxcapp.www.Share.HttpService.ShareService;
import com.sxcapp.www.Share.OrderCouponListActivityV3;
import com.sxcapp.www.Share.UploadCarInfoActivityV3;
import com.sxcapp.www.Util.AndroidTool;
import com.sxcapp.www.Util.MapUtilContinue;
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
 * 豪车租租车界面
 *
 * @author wenleitao
 * @date 2018/5/3
 */

public class LuxuryRentCarActivityV3 extends BaseActivity implements View.OnClickListener, MapLoiIml {
    @BindView(R.id.car_vp)
    ViewPager car_vp;
    @BindView(R.id.car_name_tv)
    TextView car_name_tv;
    @BindView(R.id.license_num_tv)
    TextView license_num_tv;
    @BindView(R.id.fetch_store_name_tv)
    TextView fetch_store_name_tv;
    @BindView(R.id.g_store_name_tv)
    TextView g_store_name_tv;
    @BindView(R.id.pageIndicatorView)
    PageIndicatorView mXcircleindicator;
    @BindView(R.id.rent_day_type_tv)
    TextView rent_day_type_tv;
    @BindView(R.id.rent_day_cost_tv)
    TextView rent_day_cost_tv;
    @BindView(R.id.no_deductible_tv)
    TextView no_deductible_tv;
    @BindView(R.id.no_deductible_re)
    RelativeLayout no_deductible_re;
    @BindView(R.id.check_iv)
    ImageView check_iv;
    @BindView(R.id.coupon_tv)
    TextView coupon_tv;
    @BindView(R.id.coupon_re)
    RelativeLayout coupon_re;
    @BindView(R.id.rent_btn)
    Button rent_btn;
    @BindView(R.id.rules_lin)
    LinearLayout rules_lin;
    private int no_deductible = 1;
    private ShareService service;
    private String order_no, car_id;
    private String coupon_id = "0";
    private LuxuryAppointCarImagePageAdapterV3 adapterV3;
    private static final int COUPON_REQUEST = 203;
    private MapUtilContinue mapUtil;
    private double lat, lng;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luxury_rent_car_v3);
        setTopBarColor(R.color.luxury);
        setTopbarLeftWhiteToMainBtn();
        setStatusView(R.color.luxury);
        setTopBarTitle("付费租车", null);
        StatusBarUtil.StatusBarDarkMode(this);
        ButterKnife.bind(this);
        order_no = getIntent().getStringExtra("order_no");
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(ShareService.class);
        mapUtil = new MapUtilContinue(this, this);
        mapUtil.LoPoi();
        loadData();
    }

    private void loadData() {
        showProgressDlg();
        Observable<CodeResultV3<LuxuryRentCarInfoBeanV3>> ob = service.getLuxuryRentCarInfoV3(order_no);
        ob.compose(compose(this.<CodeResultV3<LuxuryRentCarInfoBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<LuxuryRentCarInfoBeanV3>(this) {
            @Override
            protected void onHandleSuccess(LuxuryRentCarInfoBeanV3 beanV3) {
                removeProgressDlg();
                adapterV3 = new LuxuryAppointCarImagePageAdapterV3(LuxuryRentCarActivityV3.this, beanV3.getCar_image());
                car_vp.setAdapter(adapterV3);
                car_id = beanV3.getSource_id();
                mXcircleindicator.setCount(beanV3.getCar_image().size());
                mXcircleindicator.setSelection(0);
                mXcircleindicator.setRadius(5);
                car_name_tv.setText(beanV3.getCar_name());
                license_num_tv.setText(beanV3.getLicense_plate_number());
                fetch_store_name_tv.setText(beanV3.getFetch_store_name());
                g_store_name_tv.setText(beanV3.getReturn_store_name());
                rent_day_type_tv.setText("租车时长" + beanV3.getRent_days() + "天");
                rent_day_cost_tv.setText("租金" + beanV3.getRent_cost() + "元" + " 基础保险费" + beanV3.getBasic_premium() + "元" + " 手续费" + beanV3.getPoundage() + "元");
                no_deductible_tv.setText("不计免赔" + beanV3.getNo_deductible() + "元");
                no_deductible_re.setOnClickListener(LuxuryRentCarActivityV3.this);
                if (beanV3.getCoupon_size() == 0) {
                    coupon_tv.setText("无可用优惠券");
                    coupon_tv.setTextColor(getResources().getColor(R.color.black_tv_26));
                } else if (beanV3.getCoupon_size() > 0) {
                    coupon_tv.setText(beanV3.getCoupon_size() + "张可用优惠券");
                    coupon_re.setOnClickListener(LuxuryRentCarActivityV3.this);
                    coupon_tv.setTextColor(getResources().getColor(R.color.black_tv_87));
                }
                if (beanV3.getSettings().size() > 0) {
                    for (int i = 0; i < beanV3.getSettings().size(); i++) {
                        TextView rules_tv = new TextView(LuxuryRentCarActivityV3.this);
                        rules_tv.setText((i + 1) + "." + beanV3.getSettings().get(i));
                        rules_tv.setTextColor(Color.parseColor("#c4000000"));
                        rules_tv.setTextSize(13);
                        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        rules_tv.setLayoutParams(params);
                        rules_tv.setPadding(AndroidTool.dip2px(LuxuryRentCarActivityV3.this, 12), AndroidTool.dip2px(LuxuryRentCarActivityV3.this, 12), 0, 0);
                        rules_lin.addView(rules_tv, i);
                    }
                }
                rent_btn.setOnClickListener(LuxuryRentCarActivityV3.this);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.no_deductible_re:
                if (no_deductible == 1) {
                    no_deductible = 0;
                    check_iv.setBackgroundResource(R.mipmap.green_checkbox_uncheck_v3);
                } else if (no_deductible == 0) {
                    no_deductible = 1;
                    check_iv.setBackgroundResource(R.mipmap.luxury_checkbox_checked_v3);
                }
                break;
            case R.id.coupon_re:
                Intent intent = new Intent(this, OrderCouponListActivityV3.class);
                intent.putExtra("from", "luxury");
                intent.putExtra("order_no", order_no);
                startActivityForResult(intent, COUPON_REQUEST);
                break;
            case R.id.rent_btn:

                if (AndroidTool.isGetLocationPermission(LuxuryRentCarActivityV3.this)) {
                    if (lat != 0) {
                        showProgressDlg();
                        rent_btn.setClickable(false);
                        rentCar();
                    } else {
                        showToast("获取定位失败,请稍后重试");
                    }
                } else {
                    getPermission();
                }
                break;
            default:
                break;
        }
    }

    private void rentCar() {
        Observable<CodeResultV3<UnLockCarIsPhoto>> ob = service.luxuryRentCarV3(order_no, no_deductible, coupon_id, lat + "", lng + "");
        ob.compose(compose(this.<CodeResultV3<UnLockCarIsPhoto>>bindToLifecycle())).subscribe(new CodeObserverV3<UnLockCarIsPhoto>(this) {
            @Override
            protected void onHandleSuccess(UnLockCarIsPhoto isPhoto) {
                removeProgressDlg();
                rent_btn.setClickable(true);
                Intent intent = new Intent(LuxuryRentCarActivityV3.this, LuxuryEndActivityV3.class);
                intent.putExtra("order_no", order_no);
                startActivity(intent);
            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<UnLockCarIsPhoto> value) {
                super.onHandleError(msg, value);
                rent_btn.setClickable(true);
                if ("994".equals(value.getCode())) {
                    if (value.getObj().getImage_flag() == 0) {
                        Intent intent = new Intent(LuxuryRentCarActivityV3.this, UploadCarInfoActivityV3.class);
                        intent.putExtra("order_no", order_no);
                        intent.putExtra("car_id", car_id);
                        intent.putExtra("type", 0);
                        intent.putExtra("order_type", 5);
                        startActivityForResult(intent, 23);

                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                rent_btn.setClickable(true);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == COUPON_REQUEST) {
                coupon_id = data.getStringExtra("coupon_id");
                coupon_tv.setText(data.getStringExtra("coupon_tittle"));
            } else if (requestCode == 23) {
                showProgressDlg();
                rent_btn.setClickable(false);
                rentCar();

            }
        }

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

        showAlertDlg("请开启定位权限", R.string.ok, new View.OnClickListener() {
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            MyApplication.getInstance().gotoMainActivity();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapUtil.stopLocation();
    }
}

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
import com.sxcapp.www.CustomerView.ExpandableLayout;
import com.sxcapp.www.Interface.MapLoiIml;
import com.sxcapp.www.MyApplication;
import com.sxcapp.www.R;
import com.sxcapp.www.Share.Adapter.LuxuryAppointCarImagePageAdapterV3;
import com.sxcapp.www.Share.Bean.LuxuryReturnCarInfoBeanV3;
import com.sxcapp.www.Share.Bean.UnLockCarIsPhoto;
import com.sxcapp.www.Share.HttpService.ShareService;
import com.sxcapp.www.Share.OilInDayShare.OilInDayRentActivityV3;
import com.sxcapp.www.Share.StoreDetailActivityV3;
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
 * 豪车租还车界面
 * Created by wenleitao on 2018/5/2.
 */

public class LuxuryEndActivityV3 extends BaseActivity implements View.OnClickListener, MapLoiIml {

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
    @BindView(R.id.total_cost_tv)
    TextView total_cost_tv;
    @BindView(R.id.cost_info_lin)
    LinearLayout cost_info_lin;
    @BindView(R.id.hide_cost_re)
    RelativeLayout hide_cost_re;
    @BindView(R.id.hide_cost_iv)
    ImageView hide_cost_iv;
    @BindView(R.id.expandable_view)
    ExpandableLayout expandable_view;
    @BindView(R.id.return_btn)
    Button return_btn;
    @BindView(R.id.fetch_store_re)
    RelativeLayout fetch_store_re;
    @BindView(R.id.select_g_store_re)
    RelativeLayout select_g_store_re;
    private boolean is_hide = false;
    private ShareService service;
    private String order_no, car_id;
    private LuxuryAppointCarImagePageAdapterV3 adapterV3;
    private double lat, lng;
    private MapUtilContinue mapUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luxury_end_v3);
        setStatusView(R.color.luxury);
        setTopBarColor(R.color.luxury);
        setTopBarTitle("车辆使用中", null);
        setTopbarLeftbtn(R.mipmap.back_white, 0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().gotoMainActivity();
            }
        });
        StatusBarUtil.StatusBarDarkMode(this);
        ButterKnife.bind(this);
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(ShareService.class);
        order_no = getIntent().getStringExtra("order_no");
        mapUtil = new MapUtilContinue(this, this);
        mapUtil.LoPoi();
        loadData();
    }

    private void loadData() {
        showProgressDlg();
        Observable<CodeResultV3<LuxuryReturnCarInfoBeanV3>> ob = service.getLuxuryReturnCarInfoV3(order_no);
        ob.compose(compose(this.<CodeResultV3<LuxuryReturnCarInfoBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<LuxuryReturnCarInfoBeanV3>(this) {
            @Override
            protected void onHandleSuccess(final  LuxuryReturnCarInfoBeanV3 beanV3) {
                removeProgressDlg();
                adapterV3 = new LuxuryAppointCarImagePageAdapterV3(LuxuryEndActivityV3.this, beanV3.getCar_image());
                car_vp.setAdapter(adapterV3);
                car_id = beanV3.getSource_id();
                mXcircleindicator.setCount(beanV3.getCar_image().size());
                mXcircleindicator.setSelection(0);
                mXcircleindicator.setRadius(5);
                car_name_tv.setText(beanV3.getCar_name());
                license_num_tv.setText(beanV3.getLicense_plate_number());
                fetch_store_name_tv.setText(beanV3.getFetch_store_name());
                g_store_name_tv.setText(beanV3.getReturn_store_name());
                for (int i = 0; i < beanV3.getSettings().size(); i++) {
                    TextView cost_tv = new TextView(LuxuryEndActivityV3.this);
                    cost_tv.setText((i + 1) + "." + beanV3.getSettings().get(i));
                    cost_tv.setTextColor(Color.parseColor("#c4000000"));
                    cost_tv.setTextSize(13);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    cost_tv.setLayoutParams(params);
                    cost_tv.setPadding(AndroidTool.dip2px(LuxuryEndActivityV3.this, 12), AndroidTool.dip2px(LuxuryEndActivityV3.this, 12), 0, 0);
                    cost_info_lin.addView(cost_tv, i);
                }
                total_cost_tv.setText(beanV3.getTotal_cost() + "元");
                hide_cost_re.setOnClickListener(LuxuryEndActivityV3.this);
                return_btn.setOnClickListener(LuxuryEndActivityV3.this);
                fetch_store_re.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LuxuryEndActivityV3.this, StoreDetailActivityV3.class);
                        intent.putExtra("from", "luxury");
                        intent.putExtra("store_id", beanV3.getFetch_store_id());
                        startActivity(intent);
                    }
                });
                select_g_store_re.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LuxuryEndActivityV3.this, StoreDetailActivityV3.class);
                        intent.putExtra("from", "luxury");
                        intent.putExtra("store_id", beanV3.getReturn_store_id());
                        startActivity(intent);
                    }
                });
                getPermission();
            }
        });

    }

    private void returnCar() {
        Observable<CodeResultV3<UnLockCarIsPhoto>> ob = service.luxuryReturnCarV3(order_no, lat + "", lng + "");
        ob.compose(compose(this.<CodeResultV3<UnLockCarIsPhoto>>bindToLifecycle())).subscribe(new CodeObserverV3<UnLockCarIsPhoto>(this) {
            @Override
            protected void onHandleSuccess(UnLockCarIsPhoto isPhoto) {
                removeProgressDlg();
                return_btn.setClickable(true);
                showLuxuryAlertDlg("请通知门店工作人员完成还车操作", R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeAlertDlg();
                        MyApplication.getInstance().gotoMainActivity();
                    }
                }, 0, null, true);

            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<UnLockCarIsPhoto> value) {
                super.onHandleError(msg, value);
                return_btn.setClickable(true);
                if ("993".equals(value.getCode())) {
                    if (value.getObj().getImage_flag() == 0) {
                        Intent intent = new Intent(LuxuryEndActivityV3.this, UploadCarInfoActivityV3.class);
                        intent.putExtra("order_no", order_no);
                        intent.putExtra("car_id", car_id);
                        intent.putExtra("type", 1);
                        intent.putExtra("order_type", 5);
                        startActivityForResult(intent, 23);

                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                return_btn.setClickable(true);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
            case R.id.return_btn:

                if (AndroidTool.isGetLocationPermission(LuxuryEndActivityV3.this)) {
                    if (lat != 0) {
                        showProgressDlg();
                        return_btn.setClickable(false);
                        returnCar();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 23) {
                showProgressDlg();
                return_btn.setClickable(false);
                returnCar();
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

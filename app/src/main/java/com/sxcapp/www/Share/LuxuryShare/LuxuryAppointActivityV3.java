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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.security.rp.RPSDK;
import com.rd.PageIndicatorView;
import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.Interface.MapLoiIml;
import com.sxcapp.www.R;
import com.sxcapp.www.Share.Adapter.LuxuryAppointCarImagePageAdapterV3;
import com.sxcapp.www.Share.Bean.LuxuryAppointInfoBeanV3;
import com.sxcapp.www.Share.Bean.LuxuryAppointReturnBeanV3;
import com.sxcapp.www.Share.Bean.RPTokenV3;
import com.sxcapp.www.Share.HttpService.ShareService;
import com.sxcapp.www.Share.RepairActivityV3;
import com.sxcapp.www.Share.StoreDetailActivityV3;
import com.sxcapp.www.Util.AndroidTool;
import com.sxcapp.www.Util.MapUtilContinue;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManagerV3;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * 豪车付费预约界面
 * Created by wenleitao on 2018/4/28.
 */

public class LuxuryAppointActivityV3 extends BaseActivity implements MapLoiIml {
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
    @BindView(R.id.rules_lin)
    LinearLayout rules_lin;
    @BindView(R.id.pageIndicatorView)
    PageIndicatorView mXcircleindicator;
    @BindView(R.id.appoint_btn)
    Button appoint_btn;
    @BindView(R.id.fetch_store_re)
    RelativeLayout fetch_store_re;
    @BindView(R.id.select_g_store_re)
    RelativeLayout select_g_store_re;

    private String fetch_store_id, g_store_id, car_id, user_id, order_no, contract_orderno;
    private ShareService service;
    private LuxuryAppointCarImagePageAdapterV3 adapterV3;
    private MapUtilContinue mapUtil;
    private double lat, lng;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luxury_appoint_v3);
        setTopBarColor(R.color.luxury);
        setStatusView(R.color.luxury);
        StatusBarUtil.StatusBarDarkMode(this);
        setTopBarTitle("付费预约", null);
        setTopbarLeftWhiteBackBtn();
        fetch_store_id = getIntent().getStringExtra("fetch_store_id");
        g_store_id = getIntent().getStringExtra("g_store_id");
        car_id = getIntent().getStringExtra("car_id");
        user_id = SharedData.getInstance().getString(SharedData._user_id);
        mapUtil = new MapUtilContinue(this, this);
        ButterKnife.bind(this);
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(ShareService.class);
        mapUtil.LoPoi();
        loadData();
    }

    private void loadData() {
        showProgressDlg();
        Observable<CodeResultV3<LuxuryAppointInfoBeanV3>> ob = service.getLuxuryAppointCarInfoV3(car_id, fetch_store_id, g_store_id);
        ob.compose(compose(this.<CodeResultV3<LuxuryAppointInfoBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<LuxuryAppointInfoBeanV3>(this) {
            @Override
            protected void onHandleSuccess(final LuxuryAppointInfoBeanV3 beanV3) {
                removeProgressDlg();
                adapterV3 = new LuxuryAppointCarImagePageAdapterV3(LuxuryAppointActivityV3.this, beanV3.getCar_image());
                car_vp.setAdapter(adapterV3);
                mXcircleindicator.setCount(beanV3.getCar_image().size());
                mXcircleindicator.setSelection(0);
                mXcircleindicator.setRadius(5);
                car_name_tv.setText(beanV3.getCar_name());
                license_num_tv.setText(beanV3.getLicense_plate_number());
                fetch_store_name_tv.setText(beanV3.getFetch_store_name());
                g_store_name_tv.setText(beanV3.getReturn_store_name());
                for (int i = 0; i < beanV3.getSettings().size(); i++) {
                    TextView rule_tv = new TextView(LuxuryAppointActivityV3.this);
                    rule_tv.setText((i + 1) + "." + beanV3.getSettings().get(i));
                    rule_tv.setTextColor(Color.parseColor("#c4000000"));
                    rule_tv.setTextSize(13);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    rule_tv.setLayoutParams(params);
                    rule_tv.setPadding(AndroidTool.dip2px(LuxuryAppointActivityV3.this, 12), AndroidTool.dip2px(LuxuryAppointActivityV3.this, 12), 0, 0);
                    rules_lin.addView(rule_tv, i);
                }
                car_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        mXcircleindicator.setSelection(position);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
                appoint_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        showLuxuryAlertDlg("预约后不可取消,确定预约?", R.string.ok, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                appoint_btn.setClickable(false);
                                doCheckUser();
                                removeAlertDlg();
                            }
                        }, R.string.cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                removeAlertDlg();
                            }
                        }, true);
                    }
                });
                fetch_store_re.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LuxuryAppointActivityV3.this, StoreDetailActivityV3.class);
                        intent.putExtra("from", "luxury");
                        intent.putExtra("store_id", beanV3.getFetch_store());
                        startActivity(intent);
                    }
                });
                select_g_store_re.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LuxuryAppointActivityV3.this, StoreDetailActivityV3.class);
                        intent.putExtra("from", "luxury");
                        intent.putExtra("store_id", beanV3.getReturn_store());
                        startActivity(intent);
                    }
                });
                getPermission();
            }
        });
    }


    private void appointCar() {
        Observable<CodeResultV3<LuxuryAppointReturnBeanV3>> ob = service.luxuryAppointV3(user_id, fetch_store_id, g_store_id, car_id, lat + "", lng + "");
        ob.compose(compose(this.<CodeResultV3<LuxuryAppointReturnBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<LuxuryAppointReturnBeanV3>(this) {
            @Override
            protected void onHandleSuccess(LuxuryAppointReturnBeanV3 luxuryAppointReturnBeanV3) {
                removeProgressDlg();
                showToast("预约成功");
                appoint_btn.setClickable(true);
                order_no = luxuryAppointReturnBeanV3.getOrder_no();
                contract_orderno = luxuryAppointReturnBeanV3.getContract_orderno();
                Intent intent = new Intent(LuxuryAppointActivityV3.this, LuxuryPreAuditActivityV3.class);
                intent.putExtra("order_no", order_no);
                startActivity(intent);
            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<LuxuryAppointReturnBeanV3> value) {
                super.onHandleError(msg, value);
                appoint_btn.setClickable(true);
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

    private void doCheckUser() {
        showProgressDlg();
        Observable<CodeResultV3<Object>> ob = service.commonDoCheckUser(SharedData.getInstance().getString(SharedData._user_id),
                5, "0", 0, car_id, 0 + "");
        ob.compose(compose(this.<CodeResultV3<Object>>bindToLifecycle())).subscribe(new CodeObserverV3<Object>(this) {
            @Override
            protected void onHandleSuccess(Object object) {

                doRp();
            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<Object> value) {
                super.onHandleError(msg, value);
                appoint_btn.setClickable(true);
                if ("712".equals(value.getCode()) || "714".equals(value.getCode()) || "718".equals(value.getCode()) ||
                        "720".equals(value.getCode()) || "722".equals(value.getCode())) {
                    try {
                        JSONObject jsonObject = new JSONObject(value.getObj().toString());
                        int add_type = jsonObject.getInt("add_type");
                        String order_no = jsonObject.getString("order_no");
                        String add_cost = jsonObject.getString("add_cost");
                        Intent intent = new Intent(LuxuryAppointActivityV3.this, RepairActivityV3.class);
                        intent.putExtra("add_cost", add_cost);
                        intent.putExtra("order_no", order_no);
                        intent.putExtra("add_type", add_type);
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
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

    private void doRp() {
        Observable<CodeResultV3<RPTokenV3>> ob = service.getRPTokenV3(user_id);
        ob.compose(compose(this.<CodeResultV3<RPTokenV3>>bindToLifecycle())).subscribe(new CodeObserverV3<RPTokenV3>(this) {
            @Override
            protected void onHandleSuccess(RPTokenV3 rpTokenV3) {
                removeProgressDlg();
                RPSDK.start(rpTokenV3.getToken(), LuxuryAppointActivityV3.this,
                        new RPSDK.RPCompletedListener() {
                            @Override
                            public void onAuditResult(RPSDK.AUDIT audit) {
                                if (audit == RPSDK.AUDIT.AUDIT_PASS) { //认证通过
                                    appoint_btn.setClickable(true);
                                    if (AndroidTool.isGetLocationPermission(LuxuryAppointActivityV3.this)) {
                                        if (lat != 0) {
                                            showProgressDlg();
                                            appoint_btn.setClickable(false);
                                            appointCar();
                                        } else {
                                            showToast("获取定位失败,请稍后重试");
                                        }
                                    } else {
                                        getPermission();
                                    }
                                } else if (audit == RPSDK.AUDIT.AUDIT_FAIL) { //认证不通过
                                    appoint_btn.setClickable(true);
                                } else if (audit == RPSDK.AUDIT.AUDIT_IN_AUDIT) { //认证中，通常不会出现，只有在认证审核系统内部出现超时，未在限定时间内返回认证结果时出现。此时提示用户系统处理中，稍后查看认证结果即可。
                                } else if (audit == RPSDK.AUDIT.AUDIT_NOT) { //未认证，用户取消
                                    appoint_btn.setClickable(true);
                                } else if (audit == RPSDK.AUDIT.AUDIT_EXCEPTION) { //系统异常
                                    appoint_btn.setClickable(true);
                                }
                            }
                        });
            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<RPTokenV3> value) {
                super.onHandleError(msg, value);
                appoint_btn.setClickable(true);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                appoint_btn.setClickable(true);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapUtil.stopLocation();
    }
}

package com.sxcapp.www.Share;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.CustomerView.pickerview.lib.DensityUtil;
import com.sxcapp.www.Interface.MapLoiIml;
import com.sxcapp.www.R;
import com.sxcapp.www.Share.Bean.GMarkerBeanV3;
import com.sxcapp.www.Share.Bean.SelectGStoreBeanV3;
import com.sxcapp.www.Share.ElectricInDayShare.ElectricInDayShareActivityV3;
import com.sxcapp.www.Share.HttpService.ShareService;
import com.sxcapp.www.Share.OilInDayShare.OilInDayShareActivityV3;
import com.sxcapp.www.Share.OilShortShare.OilShortShareActivityV3;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManagerV3;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * Created by wenleitao on 2018/4/14.
 */

public class SelectGStoreActivityV3 extends BaseActivity implements MapLoiIml,
        AMap.OnMapLoadedListener {
    @BindView(R.id.map)
    MapView mMapView;
    private ShareService service;
    private AMap mAMap;
    private float level;
    private String fetch_store_id;
    private int FETCH_TYPE = 1;
    private int G_TYPE = 2;
    private int SAME_TYPE = 3;
    private Map<Integer, Drawable> mBackDrawAbles = new HashMap<>();
    private double fetch_store_lat, fetch_store_lng, g_lat, g_lng;
    private String city_name, g_store_name, g_store_id, g_store_phone;
    //    private List<GMarkerBeanV3> beanV3List;
    private DecimalFormat decimalFormat;
    private Dialog dialog;
    private List<Marker> markerList;
    private String from, order_no;
    private int color_resource_id;
    private String phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_g_store_v3);
        ButterKnife.bind(this);
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(ShareService.class);
        //获取地图控件引用
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        if (mAMap == null) {
            mAMap = mMapView.getMap();
        }


        StatusBarUtil.StatusBarDarkMode(this);
        setTopbarLeftWhiteBackBtn();
        setTopBarTitle("选择还车网点", null);
        level = mAMap.getCameraPosition().zoom;
        mAMap.getUiSettings().setZoomControlsEnabled(false);
        mAMap.getUiSettings().setTiltGesturesEnabled(false);// 禁用倾斜手势。
        mAMap.getUiSettings().setRotateGesturesEnabled(false);// 禁用旋转手势。
        fetch_store_id = getIntent().getStringExtra("fetch_store_id");
        fetch_store_lat = getIntent().getDoubleExtra("fetch_store_lat", 0);
        fetch_store_lng = getIntent().getDoubleExtra("fetch_store_lng", 0);
        city_name = getIntent().getStringExtra("city_name");
//        beanV3List = new ArrayList<>();
        mAMap.setOnMapLoadedListener(this);
        markerList = new ArrayList<>();
        decimalFormat = new DecimalFormat("#.00");
        from = getIntent().getStringExtra("from");
        if ("elec_short_map".equals(from) || "elec_short_order".equals(from) || "elec_inday_map".equals(from) || "elec_inday_order".equals(from)) {
            color_resource_id = R.color.green;
        } else {
            color_resource_id = R.color.top_bar_red;
        }

        setStatusView(color_resource_id);
        setTopBarColor(color_resource_id);
        getPermission();
    }

    public void getPermission() {
        PackageManager pkgManager = getPackageManager();
        boolean sdCardWritePermission =
                pkgManager.checkPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION, getPackageName()) == PackageManager.PERMISSION_GRANTED;
        if (Build.VERSION.SDK_INT >= 23 && !sdCardWritePermission) {
            PermissionGen.with(SelectGStoreActivityV3.this)
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
    public void onMapLoaded() {
        LatLng fetch_lo = new LatLng(fetch_store_lat, fetch_store_lng);
        //设置中心点和缩放比例
        mAMap.moveCamera(CameraUpdateFactory.changeLatLng(fetch_lo));
        if ("oil_short_map".equals(from) || "oil_short_order".equals(from)) {
            Observable<CodeResultV3<SelectGStoreBeanV3>> ob = service.getOilReturnStoreListV3(fetch_store_id);
            ob.compose(compose(this.<CodeResultV3<SelectGStoreBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<SelectGStoreBeanV3>(this) {
                @Override
                protected void onHandleSuccess(SelectGStoreBeanV3 markerBeanV3List) {
                    setMapDot(markerBeanV3List.getList());
                }
            });
        } else if ("elec_short_order".equals(from) || "elec_short_map".equals(from)) {
            Observable<CodeResultV3<SelectGStoreBeanV3>> ob = service.getReturnStoreListV3(fetch_store_id);
            ob.compose(compose(this.<CodeResultV3<SelectGStoreBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<SelectGStoreBeanV3>(this) {
                @Override
                protected void onHandleSuccess(SelectGStoreBeanV3 markerBeanV3List) {
                    setMapDot(markerBeanV3List.getList());
                }
            });
        } else if ("elec_inday_order".equals(from) || "elec_inday_map".equals(from)) {
            Observable<CodeResultV3<SelectGStoreBeanV3>> ob = service.getElecInDayReturnStoreListV3(fetch_store_id);
            ob.compose(compose(this.<CodeResultV3<SelectGStoreBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<SelectGStoreBeanV3>(this) {
                @Override
                protected void onHandleSuccess(SelectGStoreBeanV3 markerBeanV3List) {
                    setMapDot(markerBeanV3List.getList());
                }
            });
        } else if ("oil_inday_map".equals(from) || "oil_inday_order".equals(from)) {
            Observable<CodeResultV3<SelectGStoreBeanV3>> ob = service.getOilInDayReturnStoreListV3(fetch_store_id);
            ob.compose(compose(this.<CodeResultV3<SelectGStoreBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<SelectGStoreBeanV3>(this) {
                @Override
                protected void onHandleSuccess(SelectGStoreBeanV3 markerBeanV3List) {
                    setMapDot(markerBeanV3List.getList());
                }
            });
        }

    }

    private void setMapDot(final List<GMarkerBeanV3> beanV3List) {
        for (GMarkerBeanV3 markerBean : beanV3List) {
            LatLng latLng = new LatLng(markerBean.getStore_latitude(), markerBean.getStore_longitude());
            int type;
            if (fetch_store_id.equals(markerBean.getStore_id())) {
                type = FETCH_TYPE;
            } else {
                type = 0;
            }
            float distance = (AMapUtils.calculateLineDistance(new LatLng(fetch_store_lat, fetch_store_lng), new LatLng(markerBean.getStore_latitude(), markerBean.getStore_longitude()))) / 1000;

            Marker marker01 = mAMap.addMarker(new MarkerOptions().anchor(0.5f, 1)
                    .position(latLng)

                    .title(markerBean.getStore_name())

                    .icon(getBitmapDes(markerBean.getPark_num(), type)).snippet("距离取车点" + decimalFormat.format(distance) + "公里"));
            markerList.add(marker01);
        }
        AMap.OnMarkerClickListener onMarkerClickListener = new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                for (GMarkerBeanV3 markerBeanV3 : beanV3List) {
                    if (markerBeanV3.getStore_name().equals(marker.getTitle())) {
                        g_store_name = markerBeanV3.getStore_name();
                        g_store_id = markerBeanV3.getStore_id();
                        g_lat = markerBeanV3.getStore_latitude();
                        g_lng = markerBeanV3.getStore_latitude();
                        if (markerBeanV3.getPark_num() > 0) {
                            showSelectStoreDialog();
                        }
                    }
                }
                mAMap.clear();
                markerList.clear();
                for (GMarkerBeanV3 markerBean : beanV3List) {
                    LatLng latLng = new LatLng(markerBean.getStore_latitude(), markerBean.getStore_longitude());
                    int type;
                    if (fetch_store_id.equals(g_store_id)) {
                        if (fetch_store_id.equals(markerBean.getStore_id())) {
                            type = SAME_TYPE;
                        } else {
                            type = 0;
                        }
                    } else {
                        if (fetch_store_id.equals(markerBean.getStore_id())) {
                            type = FETCH_TYPE;
                        } else if (g_store_id.equals(markerBean.getStore_id())) {
                            type = G_TYPE;
                        } else {
                            type = 0;
                        }
                    }
                    float distance = (AMapUtils.calculateLineDistance(new LatLng(fetch_store_lat, fetch_store_lng), new LatLng(markerBean.getStore_latitude(), markerBean.getStore_longitude()))) / 1000;
                    Marker marker01 = mAMap.addMarker(new MarkerOptions().anchor(0.5f, 1)
                            .position(latLng)

                            .title(markerBean.getStore_name())

                            .icon(getBitmapDes(markerBean.getPark_num(), type)).snippet("距离取车点" + decimalFormat.format(distance) + "公里"));
                    markerList.add(marker01);
                    if (g_store_id.equals(markerBean.getStore_id())) {
                        mAMap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
                            @Override
                            public View getInfoWindow(final Marker marker) {
                                final View infoWindow = getLayoutInflater().inflate(R.layout.custom_infowindow, null);
                                TextView tittle_tv = (TextView) infoWindow.findViewById(R.id.title_tv);
                                TextView address_tv = (TextView) infoWindow.findViewById(R.id.address_tv);
                                tittle_tv.setText((marker.getTitle()));
                                address_tv.setText((marker.getSnippet()));
                                return infoWindow;
                            }

                            @Override
                            public View getInfoContents(Marker marker) {
                                return null;
                            }
                        });
                        marker01.showInfoWindow();
                    }
                }
                return true;
            }

        };
        mAMap.setOnMarkerClickListener(onMarkerClickListener);
    }


    private void showSelectStoreDialog() {
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        View view = LayoutInflater.from(this).inflate(R.layout.select_g_store_dialog_layout, null);
        TextView g_store_name_tv = (TextView) view.findViewById(R.id.g_store_name_tv);
        g_store_name_tv.setText(g_store_name);
        g_store_name_tv.setText(g_store_name);
        Button user_car_btn = (Button) view.findViewById(R.id.user_car_btn);
        if ("elec_short_map".equals(from) || "oil_short_map".equals(from) || "elec_inday_map".equals(from) || "oil_inday_map".equals(from)) {
            user_car_btn.setText("确认还车网点");
        } else {
            user_car_btn.setText("确认更换网点");
        }
        if ("elec_short_map".equals(from) || "elec_short_order".equals(from) || "elec_inday_map".equals(from) || "elec_inday_order".equals(from)) {
            user_car_btn.setBackgroundResource(R.drawable.selector_btn_click_bg_green);
        } else {
            user_car_btn.setBackgroundResource(R.drawable.selector_btn_click_bg_red);
        }
        user_car_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if ("elec_short_map".equals(from)) {
                    Intent intent = new Intent(SelectGStoreActivityV3.this, ElectricShareActivityV3.class);
                    intent.putExtra("g_store_name", g_store_name);
                    intent.putExtra("g_store_id", g_store_id);
                    intent.putExtra("g_lat", g_lat);
                    intent.putExtra("g_lng", g_lng);
                    setResult(RESULT_OK, intent);
                    finish();
                } else if ("elec_short_order".equals(from)) {
                    changeStore();
                } else if ("oil_short_map".equals(from)) {
                    Intent intent = new Intent(SelectGStoreActivityV3.this, OilShortShareActivityV3.class);
                    intent.putExtra("g_store_name", g_store_name);
                    intent.putExtra("g_store_id", g_store_id);
                    intent.putExtra("g_lat", g_lat);
                    intent.putExtra("g_lng", g_lng);
                    setResult(RESULT_OK, intent);
                    finish();
                } else if ("oil_short_order".equals(from)) {
                    changeStore();
                } else if ("elec_inday_map".equals(from)) {
                    Intent intent = new Intent(SelectGStoreActivityV3.this, ElectricInDayShareActivityV3.class);
                    intent.putExtra("g_store_name", g_store_name);
                    intent.putExtra("g_store_id", g_store_id);
                    intent.putExtra("g_lat", g_lat);
                    intent.putExtra("g_lng", g_lng);
                    setResult(RESULT_OK, intent);
                    finish();
                } else if ("elec_inday_order".equals(from)) {
                    changeStore();
                } else if ("oil_inday_map".equals(from)) {
                    Intent intent = new Intent(SelectGStoreActivityV3.this, OilInDayShareActivityV3.class);
                    intent.putExtra("g_store_name", g_store_name);
                    intent.putExtra("g_store_id", g_store_id);
                    intent.putExtra("g_lat", g_lat);
                    intent.putExtra("g_lng", g_lng);
                    setResult(RESULT_OK, intent);
                    finish();
                } else if ("oil_inday_order".equals(from)) {
                    changeStore();
                }

            }
        });
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.y = 0;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialogWindow.setWindowAnimations(R.style.mypopwindow_anim_style);
        dialog.show();
    }

    private void changeStore() {
        showProgressDlg();
        order_no = getIntent().getStringExtra("order_no");
        Observable<CodeResultV3<Object>> ob = service.getChangeReturnStoreListV3(order_no,
                getIntent().getIntExtra("order_type", 0), g_store_id);
        ob.compose(compose(this.<CodeResultV3<Object>>bindToLifecycle())).subscribe(new CodeObserverV3<Object>(this) {
            @Override
            protected void onHandleSuccess(Object o) {
                removeProgressDlg();
                Intent intent = new Intent(SelectGStoreActivityV3.this, ElectricShareActivityV3.class);
                intent.putExtra("g_store_name", g_store_name);
                intent.putExtra("g_store_id", g_store_id);
                intent.putExtra("g_lat", g_lat);
                intent.putExtra("g_lng", g_lng);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }


    @Override
    public void onSuccess() {


    }

    @Override
    public void onFail() {

    }

    private BitmapDescriptor getBitmapDes(int num, int type) {
        BitmapDescriptor bitmapDescriptor;
        TextView textView = new TextView(this);
        int ra;
        if (type == FETCH_TYPE) {
            textView.setText(num + "");
            ra = DensityUtil.dip2px(this, 50);
        } else {
            textView.setText(num + "");
            ra = DensityUtil.dip2px(this, 50);
        }
        textView.setPadding(0, 0, 0, 10);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        ViewGroup.LayoutParams p = new ViewGroup.LayoutParams(ra, ra);
        textView.setLayoutParams(p);
        textView.setBackgroundDrawable(getDrawAble(type, num));
        bitmapDescriptor = BitmapDescriptorFactory.fromView(textView);

        return bitmapDescriptor;
    }


    public Drawable getDrawAble(int no, int num) {
        if (no == FETCH_TYPE) {
            Drawable bitmapDrawable = mBackDrawAbles.get(1);
            if (bitmapDrawable == null) {
                if ("elec_short_map".equals(from) || "elec_short_order".equals(from) || "elec_inday_map".equals(from) || "elec_inday_order".equals(from)) {
                    bitmapDrawable =
                            getApplication().getResources().getDrawable(
                                    R.mipmap.green_fetch_icon_v3);
                } else {
                    bitmapDrawable =
                            getApplication().getResources().getDrawable(
                                    R.mipmap.red_fetch_icon_v3);
                }
                bitmapDrawable.setBounds(0, 0, 20, 20);
                mBackDrawAbles.put(1, bitmapDrawable);
            }

            return bitmapDrawable;
        } else if (no == G_TYPE) {

            Drawable bitmapDrawable = mBackDrawAbles.get(2);
            if (bitmapDrawable == null) {
                if ("elec_short_map".equals(from) || "elec_short_order".equals(from) || "elec_inday_map".equals(from) || "elec_inday_order".equals(from)) {
                    bitmapDrawable =
                            getApplication().getResources().getDrawable(
                                    R.mipmap.green_g_icon_v3);
                } else {
                    bitmapDrawable =
                            getApplication().getResources().getDrawable(
                                    R.mipmap.red_g_icon_v3);
                }
                bitmapDrawable.setBounds(0, 0, 20, 20);
                mBackDrawAbles.put(2, bitmapDrawable);
            }
            return bitmapDrawable;
        } else if (no == SAME_TYPE) {
            Drawable bitmapDrawable = mBackDrawAbles.get(3);
            if (bitmapDrawable == null) {
                if ("elec_short_map".equals(from) || "elec_short_order".equals(from) || "elec_inday_map".equals(from) || "elec_inday_order".equals(from)) {
                    bitmapDrawable =
                            getApplication().getResources().getDrawable(
                                    R.mipmap.green_fetch_g_same_icon);
                } else {
                    bitmapDrawable =
                            getApplication().getResources().getDrawable(
                                    R.mipmap.red_fetch_g_same_icon_v3);
                }
                bitmapDrawable.setBounds(0, 0, 20, 20);
                mBackDrawAbles.put(3, bitmapDrawable);
            }
            return bitmapDrawable;
        } else {
            if (num == 0) {
                Drawable bitmapDrawable = mBackDrawAbles.get(0);
                if (bitmapDrawable == null) {
                    bitmapDrawable =
                            getApplication().getResources().getDrawable(
                                    R.mipmap.no_car_circle_solid_v3);
                    bitmapDrawable.setBounds(0, 0, 50, 50);
                    mBackDrawAbles.put(0, bitmapDrawable);
                }

                return bitmapDrawable;
            } else {
                Drawable bitmapDrawable = mBackDrawAbles.get(4);
                if (bitmapDrawable == null) {
                    if ("elec_short_map".equals(from) || "elec_short_order".equals(from) || "elec_inday_map".equals(from) || "elec_inday_order".equals(from)) {
                        bitmapDrawable =
                                getApplication().getResources().getDrawable(
                                        R.mipmap.green_circle_sloid_v3);
                    } else {
                        bitmapDrawable =
                                getApplication().getResources().getDrawable(
                                        R.mipmap.red_circle_sloid_v3);
                    }
                    bitmapDrawable.setBounds(0, 0, 20, 20);

                    mBackDrawAbles.put(4, bitmapDrawable);
                }
                return bitmapDrawable;
            }
        }
    }
}

package com.sxcapp.www.Share.ElectricInDayShare;

import android.app.Activity;
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
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.navi.model.NaviLatLng;
import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.CustomerView.pickerview.lib.DensityUtil;
import com.sxcapp.www.Interface.MapLoiIml;
import com.sxcapp.www.Login.LoginActivity;
import com.sxcapp.www.R;
import com.sxcapp.www.Share.Bean.CityAreaBeanV3;
import com.sxcapp.www.Share.Bean.ElectricMapStoreListBeanV3;
import com.sxcapp.www.Share.Bean.MarkerBeanV3;
import com.sxcapp.www.Share.ElectricShareActivityV3;
import com.sxcapp.www.Share.HttpService.ShareService;
import com.sxcapp.www.Share.MapNavigationActivity;
import com.sxcapp.www.Share.Navi.NaviMainActivity;
import com.sxcapp.www.Share.ProblemFeedbackActivity;
import com.sxcapp.www.Share.SelectGStoreActivityV3;
import com.sxcapp.www.Share.StoreDetailActivityV3;
import com.sxcapp.www.Util.MapUtil;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManagerV3;

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
 * 电动车地图
 * Created by wenleitao on 2017/10/30.
 */

public class ElectricInDayShareActivityV3 extends BaseActivity implements MapLoiIml,
        AMap.OnMapLoadedListener, View.OnClickListener, AMap.OnCameraChangeListener {
    @BindView(R.id.service_iv)
    ImageView service_iv;
    @BindView(R.id.lopoi_iv)
    ImageView lopoi_iv;
    @BindView(R.id.zoom_in_iv)
    ImageView zoom_in_iv;
    @BindView(R.id.zoom_out_iv)
    ImageView zoom_out_iv;

    private MapView mMapView;
    private AMap mAMap;
    private Button user_car_btn;
    private TextView g_store_name_tv;
    private int STORE_REQUEST = 11;
    private ShareService service;
    private ImageView check_iv;
    private Boolean is_check = false;
    private double lat, lon;
    private boolean isGet = false;
    //    还车门店经纬度
    private double g_lat, g_lng;
    private double fetch_store_lat, fetch_store_lng;
    private Map<Integer, Drawable> mBackDrawAbles = new HashMap<>();
    private Dialog dialog;
    private String fetch_store_name, g_store_name, fetch_store_id, g_store_id;
    //地图缩放级别
    private float level;
    private List<MarkerBeanV3> list_store;
    private List<CityAreaBeanV3> list_area;
    private List<CityAreaBeanV3> list_city;
    private List<Marker> store_marker_list;
    private List<Marker> area_marker_list;
    private List<Marker> city_marker_list;
    private static final int CITYAREA_NO = 111;
    private static final int STORE_NO = 112;
    private MapUtil mapUtil = new MapUtil(this, this);
    private String city_name;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);
        setTopBarTitle("共享电动长租", null);
        setStatusView(R.color.green);
        setTopBarColor(R.color.green);
        StatusBarUtil.StatusBarDarkMode(this);
        setTopbarLeftWhiteBackBtn();
        zoom_out_iv.setBackgroundResource(R.mipmap.map_zoom_out_green_v3);
        zoom_in_iv.setBackgroundResource(R.mipmap.map_zoom_in_green_v3);
        lopoi_iv.setBackgroundResource(R.mipmap.lopoi_pic_green_v3);
        service_iv.setBackgroundResource(R.mipmap.green_service_center_v3);
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(ShareService.class);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        if (mAMap == null) {
            mAMap = mMapView.getMap();
        }
        level = mAMap.getCameraPosition().zoom;
        mAMap.getUiSettings().setZoomControlsEnabled(false);
        mAMap.getUiSettings().setTiltGesturesEnabled(false);// 禁用倾斜手势。
        mAMap.getUiSettings().setRotateGesturesEnabled(false);// 禁用旋转手势。
        mAMap.setOnCameraChangeListener(this);
        store_marker_list = new ArrayList<>();
        area_marker_list = new ArrayList<>();
        city_marker_list = new ArrayList<>();
        list_store = new ArrayList<>();
        list_area = new ArrayList<>();
        list_city = new ArrayList<>();
        getPermission();
        LoCurrentPoi();
        LoPoi();
        init();
    }

    /**
     * 显示当前位置蓝点
     */
    private void LoCurrentPoi() {
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        mAMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        mAMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
    }

    /**
     * 设置按钮点击监听
     */
    private void init() {
        service_iv.setOnClickListener(this);
        lopoi_iv.setOnClickListener(this);
        zoom_in_iv.setOnClickListener(this);
        zoom_out_iv.setOnClickListener(this);
        mAMap.setOnMapLoadedListener(this);
    }

    /**
     * 定位显示当前蓝点
     */
    private void LoPoi() {
        mapUtil.LoPoi();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onMapLoaded() {
        mapDot();
    }


    /**
     * 处理数据，将数据处理成city，area，store三个list ，根据这三个list显示marker
     * 设置不同list的marker的点击事件
     */
    private void mapDot() {

        Observable<CodeResultV3<ElectricMapStoreListBeanV3>> ob = service.getElecInDayMapStoreListV3();
        ob.compose(compose(this.<CodeResultV3<ElectricMapStoreListBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<ElectricMapStoreListBeanV3>(this) {
            @Override
            protected void onHandleSuccess(ElectricMapStoreListBeanV3 listBeanV3) {
                removeProgressDlg();
                for (ElectricMapStoreListBeanV3.ListBeanXX listBeanXX : listBeanV3.getList()) {
                    CityAreaBeanV3 cityAreaBean = new CityAreaBeanV3(listBeanXX.getLatitude(), listBeanXX.getLongitude(), listBeanXX.getCity_name());
                    list_city.add(cityAreaBean);
                    for (ElectricMapStoreListBeanV3.ListBeanXX.ListBeanX listBeanX : listBeanXX.getList()) {
                        CityAreaBeanV3 cityAreaBeanV3 = new CityAreaBeanV3(listBeanX.getLatitude(), listBeanX.getLongitude(), listBeanX.getCity_name());
                        list_area.add(cityAreaBeanV3);
                        for (ElectricMapStoreListBeanV3.ListBeanXX.ListBeanX.ListBean listBean : listBeanX.getList()) {
                            MarkerBeanV3 markerBean = new MarkerBeanV3(listBean.getCar_num(), listBean.getStore_id(), listBean.getLatitude(), listBean.getLongitude(), listBeanXX.getCity_name(), listBean.getStore_name());
                            list_store.add(markerBean);
                        }
                    }
                }

                for (CityAreaBeanV3 cityAreaBean : list_city) {
                    LatLng latLng = new LatLng(cityAreaBean.getLat(), cityAreaBean.getLng());
                    Marker marker01 = mAMap.addMarker(new MarkerOptions().anchor(0.5f, 1)
                            .position(latLng)

                            .title(cityAreaBean.getName())

                            .icon(getBitmapDes(0, CITYAREA_NO, cityAreaBean.getName())).snippet("city"));

                    city_marker_list.add(marker01);
                }
                for (CityAreaBeanV3 cityAreaBean : list_area) {
                    LatLng latLng = new LatLng(cityAreaBean.getLat(), cityAreaBean.getLng());
                    Marker marker01 = mAMap.addMarker(new MarkerOptions().anchor(0.5f, 1)
                            .position(latLng)

                            .title(cityAreaBean.getName())

                            .icon(getBitmapDes(0, CITYAREA_NO, cityAreaBean.getName())).snippet("area"));
                    marker01.setVisible(false);
                    area_marker_list.add(marker01);
                }
                for (MarkerBeanV3 markerBean : list_store) {
                    LatLng latLng = new LatLng(markerBean.getLat(), markerBean.getLng());
                    Marker marker01 = mAMap.addMarker(new MarkerOptions().anchor(0.5f, 1)
                            .position(latLng)
                            .title(markerBean.getCity_name())
                            .icon(getBitmapDes(markerBean.getCar_num(), STORE_NO, "store")).snippet(markerBean.getStore_name()));
                    marker01.setVisible(false);
                    store_marker_list.add(marker01);
                }
                AMap.OnMarkerClickListener markerClickListener = new AMap.OnMarkerClickListener() {
                    // marker 对象被点击时回调的接口
                    // 返回 true 则表示接口已响应事件，否则返回false
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        LatLng latLngmarker = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
                        if ("city".equals(marker.getSnippet())) {
                            //设置中心点和缩放比例
                            mAMap.moveCamera(CameraUpdateFactory.changeLatLng(latLngmarker));
                            try {
                                Thread.sleep(100);
                                mAMap.animateCamera(CameraUpdateFactory.zoomTo(11));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

//                            mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngmarker, 11));
                        } else if ("area".equals(marker.getSnippet())) {
                            //设置中心点和缩放比例
                            mAMap.moveCamera(CameraUpdateFactory.changeLatLng(latLngmarker));
                            try {
                                Thread.sleep(100);
                                mAMap.animateCamera(CameraUpdateFactory.zoomTo(13));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            for (MarkerBeanV3 bean : list_store) {
                                if (marker.getSnippet().equals(bean.getStore_name())) {
                                    fetch_store_name = bean.getStore_name();
                                    fetch_store_id = bean.getId();
                                    fetch_store_lat = bean.getLat();
                                    fetch_store_lng = bean.getLng();
                                    city_name = bean.getCity_name();
                                    if (bean.getCar_num() > 0) {
                                        showSelectStoreDialog(fetch_store_name);
                                    }
                                }
                            }
                            mAMap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
                                @Override
                                public View getInfoWindow(final Marker marker) {
                                    final View infoWindow = getLayoutInflater().inflate(R.layout.custom_infowindow, null);
                                    TextView tittle_tv = (TextView) infoWindow.findViewById(R.id.title_tv);
                                    TextView address_tv = (TextView) infoWindow.findViewById(R.id.address_tv);
                                    ImageView navigation_iv = (ImageView) infoWindow.findViewById(R.id.navigation_iv);
                                    tittle_tv.setText((marker.getTitle()));
                                    address_tv.setText((marker.getSnippet()));
                                    navigation_iv.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (isGet) {
                                                Intent intent = new Intent(ElectricInDayShareActivityV3.this, NaviMainActivity.class);
                                                intent.putExtra("end", new NaviLatLng(marker.getPosition().latitude, marker.getPosition().longitude));
                                                startActivity(intent);
                                            } else {
                                                showToast("没有获取到当前位置");
                                            }
                                        }
                                    });
                                    return infoWindow;
                                }

                                @Override
                                public View getInfoContents(Marker marker) {
                                    return null;
                                }
                            });
                            marker.showInfoWindow();
                        }
                        return true;
                    }
                };
                mAMap.setOnMarkerClickListener(markerClickListener);
            }

        });

    }

    private BitmapDescriptor getBitmapDes(int num, int no, String city_name) {
        BitmapDescriptor bitmapDescriptor;
        TextView textView = new TextView(this);
        int ra;
        if (no == CITYAREA_NO) {
            textView.setText(city_name);
            ra = DensityUtil.dip2px(this, 70);
        } else {
            textView.setText(num + "");
            ra = DensityUtil.dip2px(this, 50);
        }

        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView.setPadding(0, 0, 0, 12);
        ViewGroup.LayoutParams p = new ViewGroup.LayoutParams(ra, ra);
        textView.setLayoutParams(p);

        textView.setBackgroundDrawable(getDrawAble(no, num));


        bitmapDescriptor = BitmapDescriptorFactory.fromView(textView);

        return bitmapDescriptor;
    }


    public Drawable getDrawAble(int no, int num) {

        if (no == STORE_NO) {
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
                Drawable bitmapDrawable = mBackDrawAbles.get(1);
                if (bitmapDrawable == null) {
                    bitmapDrawable =
                            getApplication().getResources().getDrawable(
                                    R.mipmap.green_circle_sloid_v3);
                    bitmapDrawable.setBounds(0, 0, 50, 50);
                    mBackDrawAbles.put(1, bitmapDrawable);
                }

                return bitmapDrawable;
            }
        } else if (no == CITYAREA_NO) {

            Drawable bitmapDrawable = mBackDrawAbles.get(2);
            if (bitmapDrawable == null) {

                bitmapDrawable =
                        getApplication().getResources().getDrawable(
                                R.mipmap.green_defaultcluster_v3);
                bitmapDrawable.setBounds(0, 0, 20, 20);
                mBackDrawAbles.put(2, bitmapDrawable);
            }
            return bitmapDrawable;
        }

        return null;


    }


    public void getPermission() {
        PackageManager pkgManager = getPackageManager();
        boolean sdCardWritePermission =
                pkgManager.checkPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION, getPackageName()) == PackageManager.PERMISSION_GRANTED;
        if (Build.VERSION.SDK_INT >= 23 && !sdCardWritePermission) {
            PermissionGen.with(ElectricInDayShareActivityV3.this)
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
        LoCurrentPoi();
        LoPoi();
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


    public void showServiceDialog() {
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        View view = LayoutInflater.from(this).inflate(R.layout.service_pop, null);
        RelativeLayout use_car_guide_re = (RelativeLayout) view.findViewById(R.id.user_car_guide_re);
        RelativeLayout faq_re = (RelativeLayout) view.findViewById(R.id.faq_re);
        RelativeLayout customer_service_re = (RelativeLayout) view.findViewById(R.id.customer_service_re);
        RelativeLayout problem_feedback_re = (RelativeLayout) view.findViewById(R.id.problem_feedback_re);
        TextView cancel_btn = (TextView) view.findViewById(R.id.cancel_btn);
        cancel_btn.setOnClickListener(this);
        use_car_guide_re.setOnClickListener(this);
        faq_re.setOnClickListener(this);
        customer_service_re.setOnClickListener(this);
        problem_feedback_re.setOnClickListener(this);
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

    private void showSelectStoreDialog(final String storeName) {
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        View view = LayoutInflater.from(this).inflate(R.layout.select_store_dialog_layout, null);
        TextView fetch_store_name_tv = (TextView) view.findViewById(R.id.fetch_store_name_tv);
        fetch_store_name_tv.setText("请选择取车网点");
        RelativeLayout select_g_store_re = (RelativeLayout) view.findViewById(R.id.select_g_store_re);
        check_iv = (ImageView) view.findViewById(R.id.check_iv);
        select_g_store_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ElectricInDayShareActivityV3.this, SelectGStoreActivityV3.class);
                intent.putExtra("fetch_store_id", fetch_store_id);
                intent.putExtra("fetch_store_lat", fetch_store_lat);
                intent.putExtra("fetch_store_lng", fetch_store_lng);
                intent.putExtra("city_name", city_name);
                intent.putExtra("from", "elec_inday_map");
                startActivityForResult(intent, STORE_REQUEST);
            }
        });
        RelativeLayout fetch_store_detail_re = (RelativeLayout) view.findViewById(R.id.fetch_store_detail_re);
        fetch_store_detail_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ElectricInDayShareActivityV3.this, StoreDetailActivityV3.class);
                intent.putExtra("from", "elec");
                intent.putExtra("store_id", fetch_store_id);
                startActivity(intent);
            }
        });
        g_store_name_tv = (TextView) view.findViewById(R.id.g_store_name_tv);
        g_store_name_tv.setText("请选择还车网点");
        TextView same_store_tv = (TextView) view.findViewById(R.id.same_store_tv);
        same_store_tv.setText("同取车网点");
        RelativeLayout same_store_re = (RelativeLayout) view.findViewById(R.id.same_store_re);
        same_store_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_check = true;
                check_iv.setBackgroundResource(R.mipmap.green_checked_v3);
                g_store_name_tv.setText(storeName);
                g_store_id = fetch_store_id;
                g_store_name = fetch_store_name;
            }
        });

        user_car_btn = (Button) view.findViewById(R.id.user_car_btn);
        user_car_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(g_store_name)) {
                    showToast("请先选择还车网点");
                } else {
                    if (SharedData.getInstance().isLogin()) {
                        dialog.dismiss();
                        Intent intent = new Intent(ElectricInDayShareActivityV3.this, ElecInDayRentActivityV3.class);
                        intent.putExtra("fetch_store_name", fetch_store_name);
                        intent.putExtra("fetch_store_id", fetch_store_id);
                        intent.putExtra("g_store_name", g_store_name);
                        intent.putExtra("g_store_id", g_store_id);
                        intent.putExtra("g_lat", g_lat);
                        intent.putExtra("g_lng", g_lng);
                        startActivity(intent);
                    } else {
                        startActivityForResult(new Intent(ElectricInDayShareActivityV3.this, LoginActivity.class), LOGIN_REQUEST);
                    }
                }
            }
        });
        fetch_store_name_tv.setText(storeName);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == STORE_REQUEST && Activity.RESULT_OK == resultCode) {
            g_store_name = data.getStringExtra("g_store_name");
            g_store_id = data.getStringExtra("g_store_id");
            g_store_name_tv.setText(g_store_name);
            g_lat = data.getDoubleExtra("g_lat", 0);
            g_lng = data.getDoubleExtra("g_lng", 0);
            if (!g_store_name.equals(fetch_store_name)) {
                is_check = false;
                check_iv.setBackgroundResource(R.mipmap.uncheck);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.service_iv:
                showServiceDialog();
                break;
            case R.id.user_car_guide_re:
                openWebView("http://106.14.135.110/SxcH5/operate.html", "电动车辆指引", true);
                break;
            case R.id.faq_re:
                openWebView("http://106.14.135.110/SxcH5/index.html", "常见问题", true);
                break;
            case R.id.customer_service_re:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "4000077000"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.problem_feedback_re:
                if (SharedData.getInstance().isLogin()) {
                    Intent intent1 = new Intent(ElectricInDayShareActivityV3.this, ProblemFeedbackActivity.class);
                    intent1.putExtra("from", "elec");
                    startActivity(intent1);
                } else {
                    Intent intent_register = new Intent(ElectricInDayShareActivityV3.this, LoginActivity.class);
                    startActivityForResult(intent_register, LOGIN_REQUEST);
                }
                break;
            case R.id.cancel_btn:
                dialog.dismiss();
                break;
            case R.id.lopoi_iv:
                LoCurrentPoi();
                break;
            case R.id.zoom_in_iv:
                mAMap.animateCamera(CameraUpdateFactory.zoomIn());
                break;
            case R.id.zoom_out_iv:
                mAMap.animateCamera(CameraUpdateFactory.zoomOut());
                break;
            default:
                break;
        }
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        if (Double.compare(cameraPosition.zoom, 10.00) < 0 || Double.compare(cameraPosition.zoom, 10.00) == 0) {
            for (Marker marker : area_marker_list) {
                marker.setVisible(false);
            }
            for (Marker marker : store_marker_list) {
                marker.setVisible(false);
            }
            for (Marker marker : city_marker_list) {
                marker.setVisible(true);
            }
        } else if (Double.compare(cameraPosition.zoom, 10.00) > 0 && Double.compare(cameraPosition.zoom, 13) < 0) {
            for (Marker marker : area_marker_list) {
                marker.setVisible(true);
            }
            for (Marker marker : store_marker_list) {
                marker.setVisible(false);
            }
            for (Marker marker : city_marker_list) {
                marker.setVisible(false);
            }
        } else if (Double.compare(cameraPosition.zoom, 13) > 0 || Double.compare(cameraPosition.zoom, 13) == 0) {
            for (Marker marker : area_marker_list) {
                marker.setVisible(false);
            }
            for (Marker marker : store_marker_list) {
                marker.setVisible(true);
            }
        } else {
            for (Marker marker : store_marker_list) {
                marker.setVisible(false);
            }
            for (Marker marker : area_marker_list) {
                marker.setVisible(true);
            }
        }
    }

    @Override
    public void onSuccess() {
        isGet = true;
        lat = mapUtil.getLat();//获取纬度
        lon = mapUtil.getLng();//获取经度
    }

    @Override
    public void onFail() {

    }
}


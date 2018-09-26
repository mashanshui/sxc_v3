package com.sxcapp.www.UserCenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.TranslateAnimation;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.orhanobut.logger.Logger;
import com.sxcapp.www.Interface.MapLoiIml;
import com.sxcapp.www.R;
import com.sxcapp.www.Util.MapUtil;
import com.sxcapp.www.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sxcapp.www.Util.AndroidTool.dip2px;

/**
 * Created by wenleitao on 2018/5/23.
 */

public class RecommendStoreMapActivityV3 extends BaseActivity implements
        GeocodeSearch.OnGeocodeSearchListener, PoiSearch.OnPoiSearchListener, MapLoiIml {
    @BindView(R.id.map)
    MapView mapView;
    @BindView(R.id.address_tv)
    TextView address_tv;
    @BindView(R.id.address_lv)
    ListView address_lv;
    @BindView(R.id.search_edit)
    EditText search_edit;
    @BindView(R.id.recommend_btn)
    Button recommend_btn;
    @BindView(R.id.cancel_tv)
    TextView cancel_tv;
    @BindView(R.id.delete_iv)
    ImageView delete_iv;
    private AMap aMap;
    private LatLonPoint searchLatlonPoint;
    private List<PoiItem> resultData;
    private Marker locationMarker;
    private GeocodeSearch geocoderSearch;
    //    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private List<Tip> autoTips;
    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;
    private List<PoiItem> poiItems;// poi数据
    private MapUtil mapUtil = new MapUtil(this,this);
    private String searchType = "地名地址信息|道路门牌|汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|金融保险服务|公司企业|道路附属设施|公共设施";
    Inputtips.InputtipsListener inputtipsListener = new Inputtips.InputtipsListener() {
        @Override
        public void onGetInputtips(List<Tip> list, int rCode) {
            if (rCode == AMapException.CODE_AMAP_SUCCESS) {// 正确返回
                autoTips = list;
                List<String> listString = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    listString.add(list.get(i).getName());
                }
                if (listString.size() > 0) {
                    address_lv.setVisibility(View.VISIBLE);
                    address_lv.setAdapter(new ArrayAdapter<>(RecommendStoreMapActivityV3.this, android.R.layout.simple_list_item_1, listString));
                    address_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (autoTips != null && autoTips.size() > position) {
                                Tip tip = autoTips.get(position);
                                address_lv.setVisibility(View.GONE);
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                //得到InputMethodManager的实例
                                if (imm.isActive()) {
                                    //如果开启
                                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                                            0);
                                    //关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的

                                }
                                searchPoi(tip);
                            }
                        }
                    });
                }
            } else {

            }
        }
    };
    private String searchKey;
    private String inputSearchKey;
    private String address;
    private double lat, lng;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_map);
        ButterKnife.bind(this);
        mapView.onCreate(savedInstanceState);
        cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.moveCamera(CameraUpdateFactory.zoomTo(16f));
            setUpMap();
        }

        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                geoAddress();
                startJumpAnimation();
                searchLatlonPoint = new LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude);

            }
        });
        delete_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_edit.setText("");
            }
        });

        aMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
                addMarkerInScreenCenter(null);
            }
        });
        search_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = s.toString().trim();
                if (newText.length() > 0) {
                    delete_iv.setVisibility(View.VISIBLE);
                    InputtipsQuery inputquery = new InputtipsQuery(newText, "");
                    Inputtips inputTips = new Inputtips(RecommendStoreMapActivityV3.this, inputquery);
                    inputquery.setCityLimit(true);
                    inputTips.setInputtipsListener(inputtipsListener);
                    inputTips.requestInputtipsAsyn();
                } else {
                    address_lv.setVisibility(View.GONE);
                    delete_iv.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 响应逆地理编码
     */
    public void geoAddress() {
//        Log.i("MY", "geoAddress"+ searchLatlonPoint.toString());
//        showProgressDlg();
//        searchText.setText("");
        showProgressDlg();
        if (searchLatlonPoint != null) {
            RegeocodeQuery query = new RegeocodeQuery(searchLatlonPoint, 200, GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
            geocoderSearch.getFromLocationAsyn(query);
        }
    }

    private void addMarkerInScreenCenter(LatLng locationLatLng) {
        LatLng latLng = aMap.getCameraPosition().target;
        Point screenPosition = aMap.getProjection().toScreenLocation(latLng);
        locationMarker = aMap.addMarker(new MarkerOptions()
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.purple_pin)));
        //设置Marker在屏幕上,不跟随地图移动
        locationMarker.setPositionByPixels(screenPosition.x, screenPosition.y);
        locationMarker.setZIndex(1);

    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        removeProgressDlg();
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                String address = result.getRegeocodeAddress().getProvince() + result.getRegeocodeAddress().getCity() + result.getRegeocodeAddress().getDistrict() + result.getRegeocodeAddress().getTownship();
                doSearchQuery();
            }
        } else {
//            Toast.makeText(RecommendStoreActivityV3`.this, "error code is " + rCode, Toast.LENGTH_SHORT).show();
        }
    }

    private void searchPoi(Tip result) {

        inputSearchKey = result.getName();//getAddress(); // + result.getRegeocodeAddress().getCity() + result.getRegeocodeAddress().getDistrict() + result.getRegeocodeAddress().getTownship();
        searchLatlonPoint = result.getPoint();
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(searchLatlonPoint.getLatitude(), searchLatlonPoint.getLongitude()), 16f));

    }
    /**
     * 开始进行poi搜索
     */
    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery() {
        currentPage = 0;
        query = new PoiSearch.Query(searchKey, searchType, "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setCityLimit(true);
        query.setPageSize(20);
        query.setPageNum(currentPage);

        if (searchLatlonPoint != null) {
            poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.setBound(new PoiSearch.SearchBound(searchLatlonPoint, 1000, true));//
            poiSearch.searchPOIAsyn();
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    /**
     * 屏幕中心marker 跳动
     */
    public void startJumpAnimation() {

        if (locationMarker != null) {
            //根据屏幕距离计算需要移动的目标点
            final LatLng latLng = locationMarker.getPosition();
            Point point = aMap.getProjection().toScreenLocation(latLng);
            point.y -= dip2px(this, 125);
            LatLng target = aMap.getProjection()
                    .fromScreenLocation(point);
            //使用TranslateAnimation,填写一个需要移动的目标点
            Animation animation = new TranslateAnimation(target);
            animation.setInterpolator(new Interpolator() {
                @Override
                public float getInterpolation(float input) {
                    // 模拟重加速度的interpolator
                    if (input <= 0.5) {
                        return (float) (0.5f - 2 * (0.5 - input) * (0.5 - input));
                    } else {
                        return (float) (0.5f - Math.sqrt((input - 0.5f) * (1.5f - input)));
                    }
                }
            });
            //整个移动所需要的时间
            animation.setDuration(600);
            //设置动画
            locationMarker.setAnimation(animation);
            //开始动画
            locationMarker.startAnimation();

        } else {
            Log.e("ama", "screenMarker is null");
        }
    }


    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {

        aMap.getUiSettings().setZoomControlsEnabled(false);
//        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
//        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
//        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        LoCurrentPoi();
        mapUtil.LoPoi();

    }

//    @Override
//    public void onLocationChanged(AMapLocation amapLocation) {
//        if (mListener != null && amapLocation != null) {
//            if (amapLocation != null
//                    && amapLocation.getErrorCode() == 0) {
//                mListener.onLocationChanged(amapLocation);
//
//                LatLng curLatlng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
//
//                searchLatlonPoint = new LatLonPoint(curLatlng.latitude, curLatlng.longitude);
//
//                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLatlng, 16f));
//
//            } else {
//                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
//                showToast("定位失败");
//                Log.e("AmapErr", errText);
//            }
//        }
//    }

    private void LoCurrentPoi() {
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
    }

//    @Override
//    public void activate(OnLocationChangedListener listener) {
//        mListener = listener;
//        if (mlocationClient == null) {
//            mlocationClient = new AMapLocationClient(this);
//            mlocationClient = new AMapLocationClient(this);
//            mLocationOption = new AMapLocationClientOption();
//            //设置定位监听
//            mlocationClient.setLocationListener(this);
//            mLocationOption = new AMapLocationClientOption();
//            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//            //获取一次定位结果：
////该方法默认为false。
//            mLocationOption.setOnceLocation(true);
////获取最近3s内精度最高的一次定位结果：
////设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
//            mLocationOption.setOnceLocationLatest(true);
//            mLocationOption.setNeedAddress(true);
//            mLocationOption.setHttpTimeOut(20000);
//            mLocationOption.setLocationCacheEnable(false);
//            mlocationClient.setLocationOption(mLocationOption);
////启动定位
//            mlocationClient.startLocation();
//
//
//        }
//    }
//
//    @Override
//    public void deactivate() {
//        mListener = null;
//        if (mlocationClient != null) {
//            mlocationClient.stopLocation();
//            mlocationClient.onDestroy();
//        }
//        mlocationClient = null;
//    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int resultCode) {
        if (resultCode == AMapException.CODE_AMAP_SUCCESS) {
            if (poiResult != null && poiResult.getQuery() != null) {
                if (poiResult.getQuery().equals(query)) {
                    poiItems = poiResult.getPois();
                    if (poiItems != null && poiItems.size() > 0) {
                        address = poiItems.get(0).getCityName() + poiItems.get(0).getDirection() + poiItems.get(0).toString();
                        address_tv.setText(address);
                        lat = poiItems.get(0).getLatLonPoint().getLatitude();
                        lng = poiItems.get(0).getLatLonPoint().getLongitude();
                        recommend_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.putExtra("address", address);
                                intent.putExtra("lat", lat);
                                intent.putExtra("lng", lng);
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        });
                    } else {
                        Toast.makeText(RecommendStoreMapActivityV3.this, "无搜索结果", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(RecommendStoreMapActivityV3.this, "无搜索结果", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
//        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    @Override
    public void onSuccess() {
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mapUtil.getLat(), mapUtil.getLng()), 16f));
    }

    @Override
    public void onFail() {

    }
}

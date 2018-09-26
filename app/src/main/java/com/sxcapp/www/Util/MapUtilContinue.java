package com.sxcapp.www.Util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.orhanobut.logger.Logger;
import com.sxcapp.www.Interface.MapLoiIml;


/**
 * 地图工具类
 * Created by wenleitao on 2018/1/30.
 */

public class MapUtilContinue implements AMapLocationListener {
    private int count = 0;
    private AMapLocationClient mLocationClient;
    public AMapLocationClientOption mLocationOption = null;
    private double lat;
    private double lng;

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    private Context context;
    private MapLoiIml mapLoiIml;

    public MapUtilContinue(Context context, MapLoiIml mapLoiIml) {
        this.context = context;
        this.mapLoiIml = mapLoiIml;
    }

    public void LoPoi() {

//声
//初始化定位
        mLocationClient = new AMapLocationClient(context);
//设置定位回调监听
//初始化定位参数
        mLocationOption = new AMapLocationClientOption();
//设置定位监听
        mLocationClient.setLocationListener(this);
//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(3000);
//设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        mLocationOption.setLocationCacheEnable(false);
// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
// 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
// 在定位结束后，在合适的生命周期调用onDestroy()方法
// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
//启动定位
        mLocationClient.startLocation();
    }


    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                lat = amapLocation.getLatitude();//获取纬度
                lng = amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
//                Logger.e(amapLocation.getLatitude() + "经度" + "定位模式" + amapLocation.getLocationType());
//                Toast.makeText(context, amapLocation.getLatitude() + "经度" + "定位模式" + amapLocation.getLocationType(), Toast.LENGTH_SHORT).show();
                mapLoiIml.onSuccess();
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Logger.e("AmapError" + "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    public void stopLocation() {
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
    }
}

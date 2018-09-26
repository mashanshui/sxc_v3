package com.sxcapp.www.Util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.sxcapp.www.Interface.MapLoiIml;


/**
 * 地图工具类
 *
 * @author wenleitao
 * @date 2018/1/30
 */

public class MapUtil implements AMapLocationListener{
    private int count = 0;
    private  AMapLocationClient mLocationClient;
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

    public MapUtil(Context context, MapLoiIml mapLoiIml) {
        this.context = context;
        this.mapLoiIml = mapLoiIml;
    }

    public void LoPoi() {
//初始化定位
        mLocationClient = new AMapLocationClient(context);
//设置定位回调监听
        mLocationClient.setLocationListener(this);

        //声明AMapLocationClientOption对象
        AMapLocationClientOption mLocationOption = null;
//初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
//该方法默认为false。
        mLocationOption.setOnceLocation(true);
//获取最近3s内精度最高的一次定位结果：
//设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        mLocationOption.setNeedAddress(false);
        mLocationOption.setHttpTimeOut(15000);
        mLocationOption.setLocationCacheEnable(false);
        mLocationClient.setLocationOption(mLocationOption);
//启动定位
        mLocationClient.startLocation();
    }

    public void destroy() {
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
//可在其中解析amapLocation获取相应内容。
                //获取纬度
                lat = aMapLocation.getLatitude();
                //获取经度
                lng = aMapLocation.getLongitude();
                int type = aMapLocation.getLocationType();
                count++;
                if (count == 1) {
                    if (type == 1) {
                        count = 0;
                        mapLoiIml.onSuccess();
                    } else {
                        LoPoi();
                    }

                } else if (count == 2) {
                    count = 0;
                    mapLoiIml.onSuccess();
                }

            }

        } else {
            mapLoiIml.onFail();
            Toast.makeText(context, "定位失败", Toast.LENGTH_SHORT).show();
            //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
            Log.e("AmapError", "location Error, ErrCode:"
                    + aMapLocation.getErrorCode() + ", errInfo:"
                    + aMapLocation.getErrorInfo());
        }
    }
}

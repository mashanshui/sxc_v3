package com.sxcapp.www.Share.Bean;

/**
 * Created by wenleitao on 2018/1/12.
 */

public class CityAreaBeanV3 {

    private double lat;
    private double lng;
    private String  name;




    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CityAreaBeanV3(double lat, double lng, String name) {

        this.lat = lat;
        this.lng = lng;
        this.name = name;
    }
}

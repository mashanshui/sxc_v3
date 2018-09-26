package com.sxcapp.www.Share.Bean;

/**
 * Created by wenleitao on 2018/1/8.
 */

public class MarkerBeanV3 {
    private int car_num;
    private String id;
    private double lat;
    private double lng;
    private String city_name;
    private String store_name;


    public int getCar_num() {
        return car_num;
    }

    public void setCar_num(int car_num) {
        this.car_num = car_num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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


    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public MarkerBeanV3(int usecarnum, String id, double lat, double lng, String city_name,  String store_name) {
        this.car_num = usecarnum;
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.city_name = city_name;
        this.store_name = store_name;
    }
}

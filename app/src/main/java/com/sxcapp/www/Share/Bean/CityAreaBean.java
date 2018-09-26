package com.sxcapp.www.Share.Bean;

/**
 * Created by wenleitao on 2018/1/12.
 */

public class CityAreaBean {
    private int usecarnum;
    private double lat;
    private double lng;
    private String  name;


    public int getUsecarnum() {
        return usecarnum;
    }

    public void setUsecarnum(int usecarnum) {
        this.usecarnum = usecarnum;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CityAreaBean(int usecarnum, double lat, double lng, String name) {
        this.usecarnum = usecarnum;
        this.lat = lat;
        this.lng = lng;
        this.name = name;
    }
}

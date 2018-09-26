package com.sxcapp.www.Share.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2018/4/14.
 */

public class StoreDetailBeanV3 {

    /**
     * store_id : ee8e536a06054cce8656bbc4a443c4ef
     * store_phone : 15155969495
     * store_latitude : 31.786872
     * store_longitude : 117.332798
     * store_images : ["http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/11/7d68ff8eddfc4bc0a03786f5bd208daa.jpg?Expires=1838797420&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=w5lr4z93AjwMt61L3k6Ok4LNmuU%3D","http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/11/7d68ff8eddfc4bc0a03786f5bd208daa.jpg?Expires=1838797420&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=w5lr4z93AjwMt61L3k6Ok4LNmuU%3D"]
     * store_name : 合肥门店
     * store_address_detail : 徽商总部大厦
     * store_park_num : 11
     * description :
     * labels : []
     */

    private String store_id;
    private String store_phone;
    private double store_latitude;
    private double store_longitude;
    private String store_name;
    private String store_address_detail;
    private int store_park_num;
    private String description;
    private List<String> store_images;
    private List<?> labels;

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getStore_phone() {
        return store_phone;
    }

    public void setStore_phone(String store_phone) {
        this.store_phone = store_phone;
    }

    public double getStore_latitude() {
        return store_latitude;
    }

    public void setStore_latitude(double store_latitude) {
        this.store_latitude = store_latitude;
    }

    public double getStore_longitude() {
        return store_longitude;
    }

    public void setStore_longitude(double store_longitude) {
        this.store_longitude = store_longitude;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_address_detail() {
        return store_address_detail;
    }

    public void setStore_address_detail(String store_address_detail) {
        this.store_address_detail = store_address_detail;
    }

    public int getStore_park_num() {
        return store_park_num;
    }

    public void setStore_park_num(int store_park_num) {
        this.store_park_num = store_park_num;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getStore_images() {
        return store_images;
    }

    public void setStore_images(List<String> store_images) {
        this.store_images = store_images;
    }

    public List<?> getLabels() {
        return labels;
    }

    public void setLabels(List<?> labels) {
        this.labels = labels;
    }
}

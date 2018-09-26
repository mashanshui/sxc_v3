package com.sxcapp.www.Share.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2018/4/18.
 */

public class ElecShortCarInfoBeanV3 {
    /**
     * car_name : 奇瑞eQ
     * settings : ["预约15分钟免费，超过15分钟，自动计费","首次使用前180分钟3.00元，前30公里免费","非首次使用前0分钟免费","基本费用：0.15元/分钟，0.25元/公里","余额不低于25元","押金不低于799元","不计免赔：10.00元","还车时电量低于70%，扣款20元","还车时电量低于50%，扣款50元","分时租不可转为长租","各优惠不累计，最终解释权归本平台所有"]
     * gearbox_type : 固定齿比变速箱
     * color : 18C6EB
     * car_image : http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/18/cc2578d0d9a345ba843654432b7e7122.jpg?Expires=1839382824&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=r4xGVtYUnSUoxEpQbcVzpJh5Uzk%3D
     * number_seats : 4
     * dump_energy : 100
     * no_deductible : 10.00
     * source_id : 4028801362d6d5510162d6d79af80001
     * license_plate_number : 测LAX107
     * life_km : 0
     */

    private String car_name;
    private String gearbox_type;
    private String car_color;
    private String car_image;
    private int number_seats;
    private String dump_energy;
    private String no_deductible;
    private String source_id;
    private String license_plate_number;
    private String life_km;
    private List<String> settings;

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getGearbox_type() {
        return gearbox_type;
    }

    public void setGearbox_type(String gearbox_type) {
        this.gearbox_type = gearbox_type;
    }

    public String getCar_color() {
        return car_color;
    }

    public void setCar_color(String car_color) {
        this.car_color = car_color;
    }

    public String getCar_image() {
        return car_image;
    }

    public void setCar_image(String car_image) {
        this.car_image = car_image;
    }

    public int getNumber_seats() {
        return number_seats;
    }

    public void setNumber_seats(int number_seats) {
        this.number_seats = number_seats;
    }

    public String getDump_energy() {
        return dump_energy;
    }

    public void setDump_energy(String dump_energy) {
        this.dump_energy = dump_energy;
    }

    public String getNo_deductible() {
        return no_deductible;
    }

    public void setNo_deductible(String no_deductible) {
        this.no_deductible = no_deductible;
    }

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    public String getLicense_plate_number() {
        return license_plate_number;
    }

    public void setLicense_plate_number(String license_plate_number) {
        this.license_plate_number = license_plate_number;
    }

    public String getLife_km() {
        return life_km;
    }

    public void setLife_km(String life_km) {
        this.life_km = life_km;
    }

    public List<String> getSettings() {
        return settings;
    }

    public void setSettings(List<String> settings) {
        this.settings = settings;
    }

}

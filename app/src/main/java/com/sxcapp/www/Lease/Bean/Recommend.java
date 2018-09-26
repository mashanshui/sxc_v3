package com.sxcapp.www.Lease.Bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by wenleitao on 2017/7/14.
 */

public class Recommend implements Parcelable {

    /**
     * map : {}
     * relationModelList : []
     * orderByModelList : []
     * cnd_type : default
     * ob_type : default
     * id : 5d06e3d1555911e7a0c600163e000e21
     * vehicle_source_type : 0
     * vehicle_source_number : null
     * license_plate_number : 4f86cb9d501e11e7a0c600163e000e21
     * sort : 1
     * recommend_user_id : 607dd46e481711e6b208408d5c04d4aa
     * recommend_time : 1497922795000
     * source_number : null
     * number : null
     * oldImage : null
     * brand : 玛莎拉蒂
     * series : Ghibli
     * model : 2014款 3.0T S Q4
     * ownerQuote : null
     * newVehiclePrice : null
     * licensingTime : null
     * mileage : null
     * leaseImage : upload/image/20170613/thumbnails/WAgdY6PeaNzBXF0T97Uuo0LwFoX5HiIx184639141.jpg
     * dailyAveragePrice : 437
     * numberSeats : 5
     * displacement : 3.0T
     * engine_intake_form : 双涡轮增压
     * gearbox_type : 手自一体变速箱(AT)
     * brand_name : null
     * series_name : 豪华型
     * model_name : null
     * store_name : 铜陵店
     * store_id : f2fc7b8c9c0d430f904e0cdc9a5cea33
     * owner_quote : null
     * day_quote : null
     */

    private MapBean map;
    private String cnd_type;
    private String ob_type;
    private String id;
    private int vehicle_source_type;
    private Object vehicle_source_number;
    private String license_plate_number;
    private int sort;
    private String recommend_user_id;
    private long recommend_time;
    private Object source_number;
    private Object number;
    private Object oldImage;
    private String brand;
    private String series;
    private String model;
    private double ownerQuote;
    private double newVehiclePrice;
    private long licensingTime;
    private Object mileage;
    private String leaseImage;
    private double dailyAveragePrice;
    private int numberSeats;
    private String displacement;
    private String engine_intake_form;
    private String gearbox_type;
    private Object brand_name;
    private String series_name;
    private Object model_name;
    private String store_name;
    private String store_id;
    private Object owner_quote;
    private Object day_quote;
    private List<?> relationModelList;
    private List<?> orderByModelList;
    private String start_time;
    private String end_time;

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    protected Recommend(Parcel in) {
        cnd_type = in.readString();
        ob_type = in.readString();
        id = in.readString();
        vehicle_source_type = in.readInt();
        license_plate_number = in.readString();
        sort = in.readInt();
        recommend_user_id = in.readString();
        recommend_time = in.readLong();
        brand = in.readString();
        series = in.readString();
        model = in.readString();
        leaseImage = in.readString();
        dailyAveragePrice = in.readDouble();
        numberSeats = in.readInt();
        displacement = in.readString();
        engine_intake_form = in.readString();
        gearbox_type = in.readString();
        series_name = in.readString();
        store_name = in.readString();
        store_id = in.readString();
        newVehiclePrice = in.readDouble();
        ownerQuote = in.readDouble();
        licensingTime = in.readLong();
        start_time = in.readString();
        end_time = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cnd_type);
        dest.writeString(ob_type);
        dest.writeString(id);
        dest.writeInt(vehicle_source_type);
        dest.writeString(license_plate_number);
        dest.writeInt(sort);
        dest.writeString(recommend_user_id);
        dest.writeLong(recommend_time);
        dest.writeString(brand);
        dest.writeString(series);
        dest.writeString(model);
        dest.writeString(leaseImage);
        dest.writeDouble(dailyAveragePrice);
        dest.writeInt(numberSeats);
        dest.writeString(displacement);
        dest.writeString(engine_intake_form);
        dest.writeString(gearbox_type);
        dest.writeString(series_name);
        dest.writeString(store_name);
        dest.writeString(store_id);
        dest.writeDouble(newVehiclePrice);
        dest.writeDouble(ownerQuote);
        dest.writeLong(licensingTime);
        dest.writeString(start_time);
        dest.writeString(end_time);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Recommend> CREATOR = new Creator<Recommend>() {
        @Override
        public Recommend createFromParcel(Parcel in) {
            return new Recommend(in);
        }

        @Override
        public Recommend[] newArray(int size) {
            return new Recommend[size];
        }
    };

    public MapBean getMap() {
        return map;
    }

    public void setMap(MapBean map) {
        this.map = map;
    }

    public String getCnd_type() {
        return cnd_type;
    }

    public void setCnd_type(String cnd_type) {
        this.cnd_type = cnd_type;
    }

    public String getOb_type() {
        return ob_type;
    }

    public void setOb_type(String ob_type) {
        this.ob_type = ob_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVehicle_source_type() {
        return vehicle_source_type;
    }

    public void setVehicle_source_type(int vehicle_source_type) {
        this.vehicle_source_type = vehicle_source_type;
    }

    public Object getVehicle_source_number() {
        return vehicle_source_number;
    }

    public void setVehicle_source_number(Object vehicle_source_number) {
        this.vehicle_source_number = vehicle_source_number;
    }

    public String getLicense_plate_number() {
        return license_plate_number;
    }

    public void setLicense_plate_number(String license_plate_number) {
        this.license_plate_number = license_plate_number;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getRecommend_user_id() {
        return recommend_user_id;
    }

    public void setRecommend_user_id(String recommend_user_id) {
        this.recommend_user_id = recommend_user_id;
    }

    public long getRecommend_time() {
        return recommend_time;
    }

    public void setRecommend_time(long recommend_time) {
        this.recommend_time = recommend_time;
    }

    public Object getSource_number() {
        return source_number;
    }

    public void setSource_number(Object source_number) {
        this.source_number = source_number;
    }

    public Object getNumber() {
        return number;
    }

    public void setNumber(Object number) {
        this.number = number;
    }

    public Object getOldImage() {
        return oldImage;
    }

    public void setOldImage(Object oldImage) {
        this.oldImage = oldImage;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getOwnerQuote() {
        return ownerQuote;
    }

    public void setOwnerQuote(double ownerQuote) {
        this.ownerQuote = ownerQuote;
    }

    public double getNewVehiclePrice() {
        return newVehiclePrice;
    }

    public void setNewVehiclePrice(double newVehiclePrice) {
        this.newVehiclePrice = newVehiclePrice;
    }

    public long getLicensingTime() {
        return licensingTime;
    }

    public void setLicensingTime(long licensingTime) {
        this.licensingTime = licensingTime;
    }

    public Object getMileage() {
        return mileage;
    }

    public void setMileage(Object mileage) {
        this.mileage = mileage;
    }

    public String getLeaseImage() {
        return leaseImage;
    }

    public void setLeaseImage(String leaseImage) {
        this.leaseImage = leaseImage;
    }

    public double getDailyAveragePrice() {
        return dailyAveragePrice;
    }

    public void setDailyAveragePrice(double dailyAveragePrice) {
        this.dailyAveragePrice = dailyAveragePrice;
    }

    public int getNumberSeats() {
        return numberSeats;
    }

    public void setNumberSeats(int numberSeats) {
        this.numberSeats = numberSeats;
    }

    public String getDisplacement() {
        return displacement;
    }

    public void setDisplacement(String displacement) {
        this.displacement = displacement;
    }

    public String getEngine_intake_form() {
        return engine_intake_form;
    }

    public void setEngine_intake_form(String engine_intake_form) {
        this.engine_intake_form = engine_intake_form;
    }

    public String getGearbox_type() {
        return gearbox_type;
    }

    public void setGearbox_type(String gearbox_type) {
        this.gearbox_type = gearbox_type;
    }

    public Object getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(Object brand_name) {
        this.brand_name = brand_name;
    }

    public String getSeries_name() {
        return series_name;
    }

    public void setSeries_name(String series_name) {
        this.series_name = series_name;
    }

    public Object getModel_name() {
        return model_name;
    }

    public void setModel_name(Object model_name) {
        this.model_name = model_name;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public Object getOwner_quote() {
        return owner_quote;
    }

    public void setOwner_quote(Object owner_quote) {
        this.owner_quote = owner_quote;
    }

    public Object getDay_quote() {
        return day_quote;
    }

    public void setDay_quote(Object day_quote) {
        this.day_quote = day_quote;
    }

    public List<?> getRelationModelList() {
        return relationModelList;
    }

    public void setRelationModelList(List<?> relationModelList) {
        this.relationModelList = relationModelList;
    }

    public List<?> getOrderByModelList() {
        return orderByModelList;
    }

    public void setOrderByModelList(List<?> orderByModelList) {
        this.orderByModelList = orderByModelList;
    }

    public static class MapBean {
    }
}

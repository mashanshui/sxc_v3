package com.sxcapp.www.Share.Bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by wenleitao on 2017/7/31.
 */

public class ShareStoreBean implements Parcelable {


    /**
     * map : {}
     * relationModelList : []
     * orderByModelList : []
     * cnd_type : default
     * ob_type : default
     * id : 9355013eb32047b58045325585e4c53d
     * store_name : 乐乐小童工门店
     * address_prov : a3cbfa00f8a011e69c07408d5c04d4aa
     * address_city : a3cc3845f8a011e69c07408d5c04d4aa
     * address_district : a3e1440ef8a011e69c07408d5c04d4aa
     * address_details : 徽商总部广场
     * start_time : 08:00
     * end_time : 18:00
     * phone : 055111113333
     * type : 1
     * manage : null
     * manage_phone : 18505512347
     * store_psw : null
     * longitude : 117.20
     * latitude : 39.13
     * create_time : 1500536862000
     * provname : 安徽省
     * cityname : 合肥市
     * districtname : 包河区
     * usecarnum : 6
     */

    private MapBean map;
    private String cnd_type;
    private String ob_type;
    private String id;
    private String store_name;
    private String address_prov;
    private String address_city;
    private String address_district;
    private String address_details;
    private String start_time;
    private String end_time;
    private String phone;
    private String type;
    private Object manage;
    private String manage_phone;
    private Object store_psw;
    private double longitude;
    private double latitude;
    private long create_time;
    private String provname;
    private String cityname;
    private String districtname;
    private int usecarnum;
    private List<?> relationModelList;
    private List<?> orderByModelList;

    protected ShareStoreBean(Parcel in) {
        cnd_type = in.readString();
        ob_type = in.readString();
        id = in.readString();
        store_name = in.readString();
        address_prov = in.readString();
        address_city = in.readString();
        address_district = in.readString();
        address_details = in.readString();
        start_time = in.readString();
        end_time = in.readString();
        phone = in.readString();
        type = in.readString();
        manage_phone = in.readString();
        longitude = in.readDouble();
        latitude = in.readDouble();
        create_time = in.readLong();
        provname = in.readString();
        cityname = in.readString();
        districtname = in.readString();
        usecarnum = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cnd_type);
        dest.writeString(ob_type);
        dest.writeString(id);
        dest.writeString(store_name);
        dest.writeString(address_prov);
        dest.writeString(address_city);
        dest.writeString(address_district);
        dest.writeString(address_details);
        dest.writeString(start_time);
        dest.writeString(end_time);
        dest.writeString(phone);
        dest.writeString(type);
        dest.writeString(manage_phone);
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
        dest.writeLong(create_time);
        dest.writeString(provname);
        dest.writeString(cityname);
        dest.writeString(districtname);
        dest.writeInt(usecarnum);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShareStoreBean> CREATOR = new Creator<ShareStoreBean>() {
        @Override
        public ShareStoreBean createFromParcel(Parcel in) {
            return new ShareStoreBean(in);
        }

        @Override
        public ShareStoreBean[] newArray(int size) {
            return new ShareStoreBean[size];
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

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getAddress_prov() {
        return address_prov;
    }

    public void setAddress_prov(String address_prov) {
        this.address_prov = address_prov;
    }

    public String getAddress_city() {
        return address_city;
    }

    public void setAddress_city(String address_city) {
        this.address_city = address_city;
    }

    public String getAddress_district() {
        return address_district;
    }

    public void setAddress_district(String address_district) {
        this.address_district = address_district;
    }

    public String getAddress_details() {
        return address_details;
    }

    public void setAddress_details(String address_details) {
        this.address_details = address_details;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getManage() {
        return manage;
    }

    public void setManage(Object manage) {
        this.manage = manage;
    }

    public String getManage_phone() {
        return manage_phone;
    }

    public void setManage_phone(String manage_phone) {
        this.manage_phone = manage_phone;
    }

    public Object getStore_psw() {
        return store_psw;
    }

    public void setStore_psw(Object store_psw) {
        this.store_psw = store_psw;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getProvname() {
        return provname;
    }

    public void setProvname(String provname) {
        this.provname = provname;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getDistrictname() {
        return districtname;
    }

    public void setDistrictname(String districtname) {
        this.districtname = districtname;
    }

    public int getUsecarnum() {
        return usecarnum;
    }

    public void setUsecarnum(int usecarnum) {
        this.usecarnum = usecarnum;
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

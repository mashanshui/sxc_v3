package com.sxcapp.www.Share.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2017/11/17.
 */

public class RulesBean {


    /**
     * map : {}
     * relationModelList : []
     * orderByModelList : []
     * cnd_type : default
     * ob_type : default
     * id : 465c21fb924111e7a8c7d43d7eb1d148
     * day_cost : 4
     * single_cost : 4.1
     * deposit_cost : 3.23
     * no_deductible : 2585855
     * remark : 测试
     * is_show : 1
     * createtime : null
     * updatetime : 2017-11-13 15:51:59
     * store_id : 2d1aabc5378a4f109cbb8bebfd8a3f44
     * type : 1
     */

    private MapBean map;
    private String cnd_type;
    private String ob_type;
    private String id;
    private int day_cost;
    private double single_cost;
    private double deposit_cost;
    private double no_deductible;
    private String remark;
    private String is_show;
    private String createtime;
    private String updatetime;
    private String store_id;
    private String type;
    private List<?> relationModelList;
    private List<?> orderByModelList;
    private String fetch_store;

    public String getFetch_store() {
        return fetch_store;
    }

    public void setFetch_store(String fetch_store) {
        this.fetch_store = fetch_store;
    }

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

    public int getDay_cost() {
        return day_cost;
    }

    public void setDay_cost(int day_cost) {
        this.day_cost = day_cost;
    }

    public double getSingle_cost() {
        return single_cost;
    }

    public void setSingle_cost(double single_cost) {
        this.single_cost = single_cost;
    }

    public double getDeposit_cost() {
        return deposit_cost;
    }

    public void setDeposit_cost(double deposit_cost) {
        this.deposit_cost = deposit_cost;
    }

    public double getNo_deductible() {
        return no_deductible;
    }

    public void setNo_deductible(int no_deductible) {
        this.no_deductible = no_deductible;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIs_show() {
        return is_show;
    }

    public void setIs_show(String is_show) {
        this.is_show = is_show;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

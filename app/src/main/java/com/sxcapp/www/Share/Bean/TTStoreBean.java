package com.sxcapp.www.Share.Bean;

/**
 * Created by wenleitao on 2018/3/14.
 */

public class TTStoreBean {
    private String store_name;
    private String store_id;

    public TTStoreBean(String store_name, String store_id) {
        this.store_name = store_name;
        this.store_id = store_id;
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
}

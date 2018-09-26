package com.sxcapp.www.Share.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2018/4/27.
 */

public class LuxuryCarListByStoreBeanV3 {

    private List<CarListBean> car_list;

    public List<CarListBean> getCar_list() {
        return car_list;
    }

    public void setCar_list(List<CarListBean> car_list) {
        this.car_list = car_list;
    }

    public static class CarListBean {
        /**
         * car_name : 奥迪A8
         * settings : 活动价：租金520.00,押金5000.00,手续费10.00
         * fetch_store : ee8e536a06054cce8656bbc4a443c4ef
         * car_image : [""]
         * source_id : 3513a89afce011e7b022ecf4bbbfe9f8
         * return_store : ee8e536a06054cce8656bbc4a443c4ef
         */

        private String car_name;
        private String settings;
        private String fetch_store;
        private String source_id;
        private String return_store;
        private List<String> car_image;

        public String getCar_name() {
            return car_name;
        }

        public void setCar_name(String car_name) {
            this.car_name = car_name;
        }

        public String getSettings() {
            return settings;
        }

        public void setSettings(String settings) {
            this.settings = settings;
        }

        public String getFetch_store() {
            return fetch_store;
        }

        public void setFetch_store(String fetch_store) {
            this.fetch_store = fetch_store;
        }

        public String getSource_id() {
            return source_id;
        }

        public void setSource_id(String source_id) {
            this.source_id = source_id;
        }

        public String getReturn_store() {
            return return_store;
        }

        public void setReturn_store(String return_store) {
            this.return_store = return_store;
        }

        public List<String> getCar_image() {
            return car_image;
        }

        public void setCar_image(List<String> car_image) {
            this.car_image = car_image;
        }
    }
}

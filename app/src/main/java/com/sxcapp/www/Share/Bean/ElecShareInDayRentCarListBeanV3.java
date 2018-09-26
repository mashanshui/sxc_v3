package com.sxcapp.www.Share.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2018/4/25.
 */

public class ElecShareInDayRentCarListBeanV3 {

    /**
     * return_store_id : ee8e536a06054cce8656bbc4a443c4ef
     * fetch_store_id : ee8e536a06054cce8656bbc4a443c4ef
     * fetch_store_name : 合肥门店
     * car_list : [{"settings":["租金与不计免赔金额根据实际租赁天数计算，押金根据车型确定","确认后，费用直接扣除","提前还车，不存在费用返还","超过租赁天数按0.15元/分计费","还车时电量低于15%，扣款25.00元","还车时电量低于10%，扣款50.00元","各优惠不累计，最终解释权归本平台所有"],"gearbox_type":"固定齿比变速箱","car_image":"http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/21/c5eecdcc564649bca22f1412213e4a8e.png?Expires=1839636607&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=%2FO4KC0wS3IiNTPcooJG7QfJx2L4%3D","long_setting_list":[{"deposit_cost":"799","single_cost":"90","day_cost":"1","setting_id":"40330c24968b11e7957500163e000e22","no_deductible_setting":"30"},{"deposit_cost":"799","single_cost":"180","day_cost":"2","setting_id":"6498dc70a1ac11e7957500163e000e2e","no_deductible_setting":"60"},{"deposit_cost":"799","single_cost":"270","day_cost":"3","setting_id":"5263a887a5c111e7b022ecf4bbbfe9f9","no_deductible_setting":"90"},{"deposit_cost":"799","single_cost":"360","day_cost":"4","setting_id":"67646be8a5c111e7b022ecf4bbbfe9ff","no_deductible_setting":"120"},{"deposit_cost":"799","single_cost":"450","day_cost":"5","setting_id":"73f7a347a5c111e7b022ecf4bbbfe9fa","no_deductible_setting":"150"},{"deposit_cost":"799","single_cost":"540","day_cost":"6","setting_id":"9591a2c0a5c111e7b022ecf4bbbfe9fc","no_deductible_setting":"180"},{"deposit_cost":"799","single_cost":"630","day_cost":"7","setting_id":"a4809628a5c111e7b022ecf4bbbfe9fd","no_deductible_setting":"210"},{"deposit_cost":"5000","single_cost":"2100","day_cost":"30","setting_id":"27bba9acaf0e11e7b022ecf4bbbfe9f7","no_deductible_setting":"900"}],"setting_type":"奇瑞EQ合肥","number_seats":"4","dump_energy":"38","no_deductible":"60.00","source_id":"4028801362cd26390162cd2e2caf0009","counpon_list":[],"license_plate_number":"测LAX103","life_km":"0"},{"settings":["租金与不计免赔金额根据实际租赁天数计算，押金根据车型确定","确认后，费用直接扣除","提前还车，不存在费用返还","超过租赁天数按0.15元/分计费","还车时电量低于15%，扣款25.00元","还车时电量低于10%，扣款50.00元","各优惠不累计，最终解释权归本平台所有"],"gearbox_type":"固定齿比变速箱","car_image":"http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/21/81458d99f7fa44f3a0a664675e566f57.png?Expires=1839636673&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=xoUZysMcUh%2BwV4Kw07iE3SRgfjk%3D","long_setting_list":[{"deposit_cost":"799","single_cost":"90","day_cost":"1","setting_id":"40330c24968b11e7957500163e000e22","no_deductible_setting":"30"},{"deposit_cost":"799","single_cost":"180","day_cost":"2","setting_id":"6498dc70a1ac11e7957500163e000e2e","no_deductible_setting":"60"},{"deposit_cost":"799","single_cost":"270","day_cost":"3","setting_id":"5263a887a5c111e7b022ecf4bbbfe9f9","no_deductible_setting":"90"},{"deposit_cost":"799","single_cost":"360","day_cost":"4","setting_id":"67646be8a5c111e7b022ecf4bbbfe9ff","no_deductible_setting":"120"},{"deposit_cost":"799","single_cost":"450","day_cost":"5","setting_id":"73f7a347a5c111e7b022ecf4bbbfe9fa","no_deductible_setting":"150"},{"deposit_cost":"799","single_cost":"540","day_cost":"6","setting_id":"9591a2c0a5c111e7b022ecf4bbbfe9fc","no_deductible_setting":"180"},{"deposit_cost":"799","single_cost":"630","day_cost":"7","setting_id":"a4809628a5c111e7b022ecf4bbbfe9fd","no_deductible_setting":"210"},{"deposit_cost":"5000","single_cost":"2100","day_cost":"30","setting_id":"27bba9acaf0e11e7b022ecf4bbbfe9f7","no_deductible_setting":"900"}],"setting_type":"奇瑞EQ合肥","number_seats":"4","dump_energy":"61","no_deductible":"60.00","source_id":"4028801362cd26390162cd2ef1ab000c","counpon_list":[],"license_plate_number":"测LAX104","life_km":"0"}]
     * return_store_name : 合肥门店
     */

    private String return_store_id;
    private String fetch_store_id;
    private String fetch_store_name;
    private String return_store_name;
    private List<CarListBean> car_list;

    public String getReturn_store_id() {
        return return_store_id;
    }

    public void setReturn_store_id(String return_store_id) {
        this.return_store_id = return_store_id;
    }

    public String getFetch_store_id() {
        return fetch_store_id;
    }

    public void setFetch_store_id(String fetch_store_id) {
        this.fetch_store_id = fetch_store_id;
    }

    public String getFetch_store_name() {
        return fetch_store_name;
    }

    public void setFetch_store_name(String fetch_store_name) {
        this.fetch_store_name = fetch_store_name;
    }

    public String getReturn_store_name() {
        return return_store_name;
    }

    public void setReturn_store_name(String return_store_name) {
        this.return_store_name = return_store_name;
    }

    public List<CarListBean> getCar_list() {
        return car_list;
    }

    public void setCar_list(List<CarListBean> car_list) {
        this.car_list = car_list;
    }

    public static class CarListBean {
        /**
         * settings : ["租金与不计免赔金额根据实际租赁天数计算，押金根据车型确定","确认后，费用直接扣除","提前还车，不存在费用返还","超过租赁天数按0.15元/分计费","还车时电量低于15%，扣款25.00元","还车时电量低于10%，扣款50.00元","各优惠不累计，最终解释权归本平台所有"]
         * gearbox_type : 固定齿比变速箱
         * car_image : http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/21/c5eecdcc564649bca22f1412213e4a8e.png?Expires=1839636607&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=%2FO4KC0wS3IiNTPcooJG7QfJx2L4%3D
         * long_setting_list : [{"deposit_cost":"799","single_cost":"90","day_cost":"1","setting_id":"40330c24968b11e7957500163e000e22","no_deductible_setting":"30"},{"deposit_cost":"799","single_cost":"180","day_cost":"2","setting_id":"6498dc70a1ac11e7957500163e000e2e","no_deductible_setting":"60"},{"deposit_cost":"799","single_cost":"270","day_cost":"3","setting_id":"5263a887a5c111e7b022ecf4bbbfe9f9","no_deductible_setting":"90"},{"deposit_cost":"799","single_cost":"360","day_cost":"4","setting_id":"67646be8a5c111e7b022ecf4bbbfe9ff","no_deductible_setting":"120"},{"deposit_cost":"799","single_cost":"450","day_cost":"5","setting_id":"73f7a347a5c111e7b022ecf4bbbfe9fa","no_deductible_setting":"150"},{"deposit_cost":"799","single_cost":"540","day_cost":"6","setting_id":"9591a2c0a5c111e7b022ecf4bbbfe9fc","no_deductible_setting":"180"},{"deposit_cost":"799","single_cost":"630","day_cost":"7","setting_id":"a4809628a5c111e7b022ecf4bbbfe9fd","no_deductible_setting":"210"},{"deposit_cost":"5000","single_cost":"2100","day_cost":"30","setting_id":"27bba9acaf0e11e7b022ecf4bbbfe9f7","no_deductible_setting":"900"}]
         * setting_type : 奇瑞EQ合肥
         * number_seats : 4
         * dump_energy : 38
         * no_deductible : 60.00
         * source_id : 4028801362cd26390162cd2e2caf0009
         * counpon_list : []
         * license_plate_number : 测LAX103
         * life_km : 0
         */

        private String gearbox_type;
        private String car_image;
        private String setting_type;
        private int number_seats;
        private String dump_energy;
        private String no_deductible;
        private String source_id;
        private String license_plate_number;
        private String life_km;
        private List<String> settings;
        private List<LongSettingListBean> long_setting_list;
        private List<?> counpon_list;
        private String car_name;
        private String car_color;

        public String getCar_name() {
            return car_name;
        }

        public void setCar_name(String car_name) {
            this.car_name = car_name;
        }

        public String getCar_color() {
            return car_color;
        }

        public void setCar_color(String car_color) {
            this.car_color = car_color;
        }

        public String getGearbox_type() {
            return gearbox_type;
        }

        public void setGearbox_type(String gearbox_type) {
            this.gearbox_type = gearbox_type;
        }

        public String getCar_image() {
            return car_image;
        }

        public void setCar_image(String car_image) {
            this.car_image = car_image;
        }

        public String getSetting_type() {
            return setting_type;
        }

        public void setSetting_type(String setting_type) {
            this.setting_type = setting_type;
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

        public List<LongSettingListBean> getLong_setting_list() {
            return long_setting_list;
        }

        public void setLong_setting_list(List<LongSettingListBean> long_setting_list) {
            this.long_setting_list = long_setting_list;
        }

        public List<?> getCounpon_list() {
            return counpon_list;
        }

        public void setCounpon_list(List<?> counpon_list) {
            this.counpon_list = counpon_list;
        }

        public static class LongSettingListBean {
            /**
             * deposit_cost : 799
             * single_cost : 90
             * day_cost : 1
             * setting_id : 40330c24968b11e7957500163e000e22
             * no_deductible_setting : 30
             */

            private String deposit_cost;
            private String single_cost;
            private String day_cost;
            private String setting_id;
            private String no_deductible_setting;

            public String getDeposit_cost() {
                return deposit_cost;
            }

            public void setDeposit_cost(String deposit_cost) {
                this.deposit_cost = deposit_cost;
            }

            public String getSingle_cost() {
                return single_cost;
            }

            public void setSingle_cost(String single_cost) {
                this.single_cost = single_cost;
            }

            public String getDay_cost() {
                return day_cost;
            }

            public void setDay_cost(String day_cost) {
                this.day_cost = day_cost;
            }

            public String getSetting_id() {
                return setting_id;
            }

            public void setSetting_id(String setting_id) {
                this.setting_id = setting_id;
            }

            public String getNo_deductible_setting() {
                return no_deductible_setting;
            }

            public void setNo_deductible_setting(String no_deductible_setting) {
                this.no_deductible_setting = no_deductible_setting;
            }
        }
    }
}

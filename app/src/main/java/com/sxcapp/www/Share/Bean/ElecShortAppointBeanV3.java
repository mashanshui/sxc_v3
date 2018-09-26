package com.sxcapp.www.Share.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2018/4/18.
 */

public class ElecShortAppointBeanV3 {

    /**
     * return_store_id : ee8e536a06054cce8656bbc4a443c4ef
     * fetch_store_id : ee8e536a06054cce8656bbc4a443c4ef
     * fetch_store_name : 合肥门店
     * list : [{"car_name":"奇瑞eQ","settings":["预约15分钟免费，超过15分钟，自动计费","首次使用前180分钟3.00元，前30公里免费","非首次使用前0分钟免费","基本费用：0.15元/分钟，0.25元/公里","余额不低于25元","押金不低于799元","不计免赔：10.00元","还车时电量低于70%，扣款20元","还车时电量低于50%，扣款50元","分时租不可转为长租","各优惠不累计，最终解释权归本平台所有"],"gearbox_type":"固定齿比变速箱","color":"88571F","car_image":"http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/16/664922fd1fd0483980b18c1b7b84f4e9.jpg?Expires=1839220495&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=DceOeRG%2F7Q9a3kg5zY8yA2PPIkw%3D","number_seats":"4","dump_energy":"100","no_deductible":"10.00","source_id":"4028801362cd26390162cd2ab1840001","license_plate_number":"测LAX102","life_km":"0"},{"car_name":"奇瑞eQ","settings":["预约15分钟免费，超过15分钟，自动计费","首次使用前180分钟3.00元，前30公里免费","非首次使用前0分钟免费","基本费用：0.15元/分钟，0.25元/公里","余额不低于25元","押金不低于799元","不计免赔：10.00元","还车时电量低于70%，扣款20元","还车时电量低于50%，扣款50元","分时租不可转为长租","各优惠不累计，最终解释权归本平台所有"],"gearbox_type":"固定齿比变速箱","color":"F33B7B","car_image":"http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/17/e9efca4cf9c747a99fe828ec35c8a79b.jpg?Expires=1839309525&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=SPmfF3eENYQR%2BkiKa68YvW%2FEIlM%3D","number_seats":"4","dump_energy":"100","no_deductible":"10.00","source_id":"4028801362d265db0162d27a14110009","license_plate_number":"测LAX106","life_km":"0"},{"car_name":"奇瑞eQ","settings":["预约15分钟免费，超过15分钟，自动计费","首次使用前180分钟3.00元，前30公里免费","非首次使用前0分钟免费","基本费用：0.15元/分钟，0.25元/公里","余额不低于25元","押金不低于799元","不计免赔：10.00元","还车时电量低于70%，扣款20元","还车时电量低于50%，扣款50元","分时租不可转为长租","各优惠不累计，最终解释权归本平台所有"],"gearbox_type":"固定齿比变速箱","color":"18C6EB","car_image":"http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/18/cc2578d0d9a345ba843654432b7e7122.jpg?Expires=1839382824&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=r4xGVtYUnSUoxEpQbcVzpJh5Uzk%3D","number_seats":"4","dump_energy":"100","no_deductible":"10.00","source_id":"4028801362d6d5510162d6d79af80001","license_plate_number":"测LAX107","life_km":"0"},{"car_name":"奇瑞eQ","settings":["全新色彩随心共享车上线啦啦啦啦啦啦啦","预约15分钟免费，超过15分钟，自动计费","首次使用前180分钟3.00元，前30公里免费","非首次使用前0分钟免费","基本费用：0.15元/分钟，0.25元/公里","余额不低于25元","押金不低于799元","不计免赔：10.00元","还车时电量低于70%，扣款20元","还车时电量低于50%，扣款50元","分时租不可转为长租","各优惠不累计，最终解释权归本平台所有"],"gearbox_type":"固定齿比变速箱","color":"F95414","car_image":"http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/18/67e67c36d84d40449b194b8714bf9577.png?Expires=1839395896&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=dpt1nc2%2BwQCMriV51APG0rQnMWU%3D","number_seats":"4","dump_energy":"100","no_deductible":"10.00","source_id":"4028801362d6d5510162d6d835f40004","license_plate_number":"测LAX108","life_km":"0"}]
     * return_store_name : 合肥门店
     */

    private String return_store_id;
    private String fetch_store_id;
    private String fetch_store_name;
    private String return_store_name;
    private List<ElecShortCarInfoBeanV3>list;

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

    public List<ElecShortCarInfoBeanV3> getList() {
        return list;
    }

    public void setList(List<ElecShortCarInfoBeanV3> list) {
        this.list = list;
    }
}

package com.sxcapp.www.UserCenter.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2017/7/26.
 * 租车订单
 */

public class OrderInfo  {

    /**
     * totalNum : 1
     * orderList : [{"map":{},"relationModelList":[],"orderByModelList":[],"cnd_type":"default","ob_type":"default","id":"8121b1b971eb11e7a94d00163e1ae70a","order_no":"17072618161663","order_time":1501064195000,"order_state":"0","customer_id":"a4050f2f705211e7a94d00163e1ae70a","customer_phone":"18356085323","customer_name":"急急急","customer_idcard":"56","fetch_time":1501064160000,"return_time":1501150560000,"lease_term":1,"fetch_store":"7249ec8ee3a548bc8fde23b96862ab00","return_store":"7249ec8ee3a548bc8fde23b96862ab00","lease_remark":null,"brand":"别克","car_series":"a9a3eb5fcfa84912999f208b0f102b47","model":"a21143b7b2464e229a894a2ee3760637","car_type":"c29836fd402d11e78c5300163e000e21","car_rental":160,"basic_premium":55,"no_deductible":55,"counter_fee":20,"total_cost":290,"pay_method":"0","pay_channel":"2","pay_status":"1","appointment_type":"0","vehicle_state":"0","deposit_state":null,"fetch_store_name":"合肥店","return_store_name":"合肥店","car_type_name":null,"deal_state":null,"audit_result":null,"brand_name":"别克","serise_name":"别克GL8","model_name":"2011款 3.0L XT豪华商务旗舰版","return_condition":null,"take_date":null,"return_date":null,"take_id":null,"commit_time":null,"take_validate_time":null,"return_id":null,"return_validate_time":null,"leaseBrand":null,"leaseSeries":"别克GL8","leaseModel":"2011款 3.0L XT豪华商务旗舰版","lease_management":"商务型","numberSeats":7,"imagePath":"upload/image/20170615/thumbnails/NzGj2VUOn0l4CLuAjLBAJ6qbI4Mc5mn3185313253.jpg","displacement":"3.0","engine_intake_form":"自然吸气","gearbox_type":"手自一体变速箱(AT)","source_id":"f2cbe09651b811e7a0c600163e000e21","order_pay_time":1501064206000,"is_overdue":"0","vehicle_monitoring":"0","return_deposit":null,"deposit_time":null,"license_plate_number":"苏EQ07K2"}]
     * totalPageNum : 1
     */

    private int totalNum;
    private int totalPageNum;
    private List<OrderListBean> orderList;

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public List<OrderListBean> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderListBean> orderList) {
        this.orderList = orderList;
    }


}

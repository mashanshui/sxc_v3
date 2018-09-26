package com.sxcapp.www.UserCenter.Bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by wenleitao on 2017/7/27.
 */

public class OrderListBean implements Parcelable {
    /**
     * map : {}
     * relationModelList : []
     * orderByModelList : []
     * cnd_type : default
     * ob_type : default
     * id : 8121b1b971eb11e7a94d00163e1ae70a
     * order_no : 17072618161663
     * order_time : 1501064195000
     * order_state : 0
     * customer_id : a4050f2f705211e7a94d00163e1ae70a
     * customer_phone : 18356085323
     * customer_name : 急急急
     * customer_idcard : 56
     * fetch_time : 1501064160000
     * return_time : 1501150560000
     * lease_term : 1
     * fetch_store : 7249ec8ee3a548bc8fde23b96862ab00
     * return_store : 7249ec8ee3a548bc8fde23b96862ab00
     * lease_remark : null
     * brand : 别克
     * car_series : a9a3eb5fcfa84912999f208b0f102b47
     * model : a21143b7b2464e229a894a2ee3760637
     * car_type : c29836fd402d11e78c5300163e000e21
     * car_rental : 160
     * basic_premium : 55
     * no_deductible : 55
     * counter_fee : 20
     * total_cost : 290
     * pay_method : 0
     * pay_channel : 2
     * pay_status : 1
     * appointment_type : 0
     * vehicle_state : 0
     * deposit_state : null
     * fetch_store_name : 合肥店
     * return_store_name : 合肥店
     * car_type_name : null
     * deal_state : null
     * audit_result : null
     * brand_name : 别克
     * serise_name : 别克GL8
     * model_name : 2011款 3.0L XT豪华商务旗舰版
     * return_condition : null
     * take_date : null
     * return_date : null
     * take_id : null
     * commit_time : null
     * take_validate_time : null
     * return_id : null
     * return_validate_time : null
     * leaseBrand : null
     * leaseSeries : 别克GL8
     * leaseModel : 2011款 3.0L XT豪华商务旗舰版
     * lease_management : 商务型
     * numberSeats : 7
     * imagePath : upload/image/20170615/thumbnails/NzGj2VUOn0l4CLuAjLBAJ6qbI4Mc5mn3185313253.jpg
     * displacement : 3.0
     * engine_intake_form : 自然吸气
     * gearbox_type : 手自一体变速箱(AT)
     * source_id : f2cbe09651b811e7a0c600163e000e21
     * order_pay_time : 1501064206000
     * is_overdue : 0
     * vehicle_monitoring : 0
     * return_deposit : null
     * deposit_time : null
     * license_plate_number : 苏EQ07K2
     */

    private MapBean map;
    private String cnd_type;
    private String ob_type;
    private String id;
    private String order_no;
    private long order_time;
    private int order_state;
    private String customer_id;
    private String customer_phone;
    private String customer_name;
    private String customer_idcard;
    private long fetch_time;
    private long return_time;
    private int lease_term;
    private String fetch_store;
    private String return_store;
    private Object lease_remark;
    private String brand;
    private String car_series;
    private String model;
    private String car_type;
    private double car_rental;
    private double basic_premium;
    private String no_deductible;
    private double counter_fee;
    private double total_cost;
    private int pay_method;
    private String pay_channel;
    private int pay_status;
    private String appointment_type;
    private String vehicle_state;
    private int deposit_state;
    private String fetch_store_name;
    private String return_store_name;
    private Object car_type_name;
    private Object deal_state;
    private Object audit_result;
    private String brand_name;
    private String serise_name;
    private String model_name;
    private Object return_condition;
    private Object take_date;
    private Object return_date;
    private Object take_id;
    private Object commit_time;
    private Object take_validate_time;
    private Object return_id;
    private Object return_validate_time;
    private Object leaseBrand;
    private String leaseSeries;
    private String leaseModel;
    private String lease_management;
    private int numberSeats;
    private String imagePath;
    private String displacement;
    private String engine_intake_form;
    private String gearbox_type;
    private String source_id;
    private long order_pay_time;
    private String is_overdue;
    private String vehicle_monitoring;
    private Object return_deposit;
    private Object deposit_time;
    private String license_plate_number;
    private List<?> relationModelList;
    private List<?> orderByModelList;


    protected OrderListBean(Parcel in) {
        cnd_type = in.readString();
        ob_type = in.readString();
        id = in.readString();
        order_no = in.readString();
        order_time = in.readLong();
        order_state = in.readInt();
        customer_id = in.readString();
        customer_phone = in.readString();
        customer_name = in.readString();
        customer_idcard = in.readString();
        fetch_time = in.readLong();
        return_time = in.readLong();
        lease_term = in.readInt();
        fetch_store = in.readString();
        return_store = in.readString();
        brand = in.readString();
        car_series = in.readString();
        model = in.readString();
        car_type = in.readString();
        car_rental = in.readDouble();
        basic_premium = in.readDouble();
        no_deductible = in.readString();
        counter_fee = in.readDouble();
        total_cost = in.readDouble();
        pay_method = in.readInt();
        pay_channel = in.readString();
        pay_status = in.readInt();
        appointment_type = in.readString();
        vehicle_state = in.readString();
        deposit_state = in.readInt();
        fetch_store_name = in.readString();
        return_store_name = in.readString();
        brand_name = in.readString();
        serise_name = in.readString();
        model_name = in.readString();
        leaseSeries = in.readString();
        leaseModel = in.readString();
        lease_management = in.readString();
        numberSeats = in.readInt();
        imagePath = in.readString();
        displacement = in.readString();
        engine_intake_form = in.readString();
        gearbox_type = in.readString();
        source_id = in.readString();
        order_pay_time = in.readLong();
        is_overdue = in.readString();
        vehicle_monitoring = in.readString();
        license_plate_number = in.readString();
    }

    public static final Creator<OrderListBean> CREATOR = new Creator<OrderListBean>() {
        @Override
        public OrderListBean createFromParcel(Parcel in) {
            return new OrderListBean(in);
        }

        @Override
        public OrderListBean[] newArray(int size) {
            return new OrderListBean[size];
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

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public long getOrder_time() {
        return order_time;
    }

    public void setOrder_time(long order_time) {
        this.order_time = order_time;
    }

    public int getOrder_state() {
        return order_state;
    }

    public void setOrder_state(int order_state) {
        this.order_state = order_state;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_idcard() {
        return customer_idcard;
    }

    public void setCustomer_idcard(String customer_idcard) {
        this.customer_idcard = customer_idcard;
    }

    public long getFetch_time() {
        return fetch_time;
    }

    public void setFetch_time(long fetch_time) {
        this.fetch_time = fetch_time;
    }

    public long getReturn_time() {
        return return_time;
    }

    public void setReturn_time(long return_time) {
        this.return_time = return_time;
    }

    public int getLease_term() {
        return lease_term;
    }

    public void setLease_term(int lease_term) {
        this.lease_term = lease_term;
    }

    public String getFetch_store() {
        return fetch_store;
    }

    public void setFetch_store(String fetch_store) {
        this.fetch_store = fetch_store;
    }

    public String getReturn_store() {
        return return_store;
    }

    public void setReturn_store(String return_store) {
        this.return_store = return_store;
    }

    public Object getLease_remark() {
        return lease_remark;
    }

    public void setLease_remark(Object lease_remark) {
        this.lease_remark = lease_remark;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCar_series() {
        return car_series;
    }

    public void setCar_series(String car_series) {
        this.car_series = car_series;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCar_type() {
        return car_type;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }

    public double getCar_rental() {
        return car_rental;
    }

    public void setCar_rental(double car_rental) {
        this.car_rental = car_rental;
    }

    public double getBasic_premium() {
        return basic_premium;
    }

    public void setBasic_premium(double basic_premium) {
        this.basic_premium = basic_premium;
    }

    public String getNo_deductible() {
        return no_deductible;
    }

    public void setNo_deductible(String no_deductible) {
        this.no_deductible = no_deductible;
    }

    public double getCounter_fee() {
        return counter_fee;
    }

    public void setCounter_fee(double counter_fee) {
        this.counter_fee = counter_fee;
    }

    public double getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(int total_cost) {
        this.total_cost = total_cost;
    }

    public int getPay_method() {
        return pay_method;
    }

    public void setPay_method(int pay_method) {
        this.pay_method = pay_method;
    }

    public String getPay_channel() {
        return pay_channel;
    }

    public void setPay_channel(String pay_channel) {
        this.pay_channel = pay_channel;
    }

    public int getPay_status() {
        return pay_status;
    }

    public void setPay_status(int pay_status) {
        this.pay_status = pay_status;
    }

    public String getAppointment_type() {
        return appointment_type;
    }

    public void setAppointment_type(String appointment_type) {
        this.appointment_type = appointment_type;
    }

    public String getVehicle_state() {
        return vehicle_state;
    }

    public void setVehicle_state(String vehicle_state) {
        this.vehicle_state = vehicle_state;
    }

    public int getDeposit_state() {
        return deposit_state;
    }

    public void setDeposit_state(int deposit_state) {
        this.deposit_state = deposit_state;
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

    public Object getCar_type_name() {
        return car_type_name;
    }

    public void setCar_type_name(Object car_type_name) {
        this.car_type_name = car_type_name;
    }

    public Object getDeal_state() {
        return deal_state;
    }

    public void setDeal_state(Object deal_state) {
        this.deal_state = deal_state;
    }

    public Object getAudit_result() {
        return audit_result;
    }

    public void setAudit_result(Object audit_result) {
        this.audit_result = audit_result;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getSerise_name() {
        return serise_name;
    }

    public void setSerise_name(String serise_name) {
        this.serise_name = serise_name;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public Object getReturn_condition() {
        return return_condition;
    }

    public void setReturn_condition(Object return_condition) {
        this.return_condition = return_condition;
    }

    public Object getTake_date() {
        return take_date;
    }

    public void setTake_date(Object take_date) {
        this.take_date = take_date;
    }

    public Object getReturn_date() {
        return return_date;
    }

    public void setReturn_date(Object return_date) {
        this.return_date = return_date;
    }

    public Object getTake_id() {
        return take_id;
    }

    public void setTake_id(Object take_id) {
        this.take_id = take_id;
    }

    public Object getCommit_time() {
        return commit_time;
    }

    public void setCommit_time(Object commit_time) {
        this.commit_time = commit_time;
    }

    public Object getTake_validate_time() {
        return take_validate_time;
    }

    public void setTake_validate_time(Object take_validate_time) {
        this.take_validate_time = take_validate_time;
    }

    public Object getReturn_id() {
        return return_id;
    }

    public void setReturn_id(Object return_id) {
        this.return_id = return_id;
    }

    public Object getReturn_validate_time() {
        return return_validate_time;
    }

    public void setReturn_validate_time(Object return_validate_time) {
        this.return_validate_time = return_validate_time;
    }

    public Object getLeaseBrand() {
        return leaseBrand;
    }

    public void setLeaseBrand(Object leaseBrand) {
        this.leaseBrand = leaseBrand;
    }

    public String getLeaseSeries() {
        return leaseSeries;
    }

    public void setLeaseSeries(String leaseSeries) {
        this.leaseSeries = leaseSeries;
    }

    public String getLeaseModel() {
        return leaseModel;
    }

    public void setLeaseModel(String leaseModel) {
        this.leaseModel = leaseModel;
    }

    public String getLease_management() {
        return lease_management;
    }

    public void setLease_management(String lease_management) {
        this.lease_management = lease_management;
    }

    public int getNumberSeats() {
        return numberSeats;
    }

    public void setNumberSeats(int numberSeats) {
        this.numberSeats = numberSeats;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    public long getOrder_pay_time() {
        return order_pay_time;
    }

    public void setOrder_pay_time(long order_pay_time) {
        this.order_pay_time = order_pay_time;
    }

    public String getIs_overdue() {
        return is_overdue;
    }

    public void setIs_overdue(String is_overdue) {
        this.is_overdue = is_overdue;
    }

    public String getVehicle_monitoring() {
        return vehicle_monitoring;
    }

    public void setVehicle_monitoring(String vehicle_monitoring) {
        this.vehicle_monitoring = vehicle_monitoring;
    }

    public Object getReturn_deposit() {
        return return_deposit;
    }

    public void setReturn_deposit(Object return_deposit) {
        this.return_deposit = return_deposit;
    }

    public Object getDeposit_time() {
        return deposit_time;
    }

    public void setDeposit_time(Object deposit_time) {
        this.deposit_time = deposit_time;
    }

    public String getLicense_plate_number() {
        return license_plate_number;
    }

    public void setLicense_plate_number(String license_plate_number) {
        this.license_plate_number = license_plate_number;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cnd_type);
        dest.writeString(ob_type);
        dest.writeString(id);
        dest.writeString(order_no);
        dest.writeLong(order_time);
        dest.writeInt(order_state);
        dest.writeString(customer_id);
        dest.writeString(customer_phone);
        dest.writeString(customer_name);
        dest.writeString(customer_idcard);
        dest.writeLong(fetch_time);
        dest.writeLong(return_time);
        dest.writeInt(lease_term);
        dest.writeString(fetch_store);
        dest.writeString(return_store);
        dest.writeString(brand);
        dest.writeString(car_series);
        dest.writeString(model);
        dest.writeString(car_type);
        dest.writeDouble(car_rental);
        dest.writeDouble(basic_premium);
        dest.writeString(no_deductible);
        dest.writeDouble(counter_fee);
        dest.writeDouble(total_cost);
        dest.writeInt(pay_method);
        dest.writeString(pay_channel);
        dest.writeInt(pay_status);
        dest.writeString(appointment_type);
        dest.writeString(vehicle_state);
        dest.writeInt(deposit_state);
        dest.writeString(fetch_store_name);
        dest.writeString(return_store_name);
        dest.writeString(brand_name);
        dest.writeString(serise_name);
        dest.writeString(model_name);
        dest.writeString(leaseSeries);
        dest.writeString(leaseModel);
        dest.writeString(lease_management);
        dest.writeInt(numberSeats);
        dest.writeString(imagePath);
        dest.writeString(displacement);
        dest.writeString(engine_intake_form);
        dest.writeString(gearbox_type);
        dest.writeString(source_id);
        dest.writeLong(order_pay_time);
        dest.writeString(is_overdue);
        dest.writeString(vehicle_monitoring);
        dest.writeString(license_plate_number);
    }

    public static class MapBean {
    }
}
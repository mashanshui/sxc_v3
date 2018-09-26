package com.sxcapp.www.UserCenter.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2017/7/26.
 */

public class UserInfo {

    /**
     * carRental : 0
     * gradeType : 0
     * carRentalId :
     * illegalRental : 0
     * userGarade : VIP会员
     * illegalRentalId :
     * customer : {"map":{},"relationModelList":[],"orderByModelList":[],"cnd_type":"default","ob_type":"default","id":"a4050f2f705211e7a94d00163e1ae70a","user_name":"18356085323","password":null,"nick_name":null,"type":null,"sex":null,"birthday":null,"profession":null,"phone":"18356085323","email":null,"address_dict":null,"address_city":null,"address_prov":null,"adress_details":null,"head_image":null,"login_ip":null,"login_time":null,"live_indt":"0","salt":null,"register_type":"0","id_card":null,"curr_total_money":290,"grade":"04b23f16024d11e7b91c1c872c63f5fc","work_unit":null,"licensing_time":null,"curr_total_integration":290,"money":10000,"regist_time":1500888589000,"address_prov_name":null,"address_city_name":null,"address_dict_name":null,"grade_name":null,"regist_time_ex":null}
     */

    private double carRental;
    private String gradeType;
    private String carRentalId;
    private int illegalRental;
    private String userGarade;
    private String illegalRentalId;
    private CustomerBean customer;

    public double getCarRental() {
        return carRental;
    }

    public void setCarRental(double carRental) {
        this.carRental = carRental;
    }

    public String getGradeType() {
        return gradeType;
    }

    public void setGradeType(String gradeType) {
        this.gradeType = gradeType;
    }

    public String getCarRentalId() {
        return carRentalId;
    }

    public void setCarRentalId(String carRentalId) {
        this.carRentalId = carRentalId;
    }

    public int getIllegalRental() {
        return illegalRental;
    }

    public void setIllegalRental(int illegalRental) {
        this.illegalRental = illegalRental;
    }

    public String getUserGarade() {
        return userGarade;
    }

    public void setUserGarade(String userGarade) {
        this.userGarade = userGarade;
    }

    public String getIllegalRentalId() {
        return illegalRentalId;
    }

    public void setIllegalRentalId(String illegalRentalId) {
        this.illegalRentalId = illegalRentalId;
    }

    public CustomerBean getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerBean customer) {
        this.customer = customer;
    }

    public static class CustomerBean {
        /**
         * map : {}
         * relationModelList : []
         * orderByModelList : []
         * cnd_type : default
         * ob_type : default
         * id : a4050f2f705211e7a94d00163e1ae70a
         * user_name : 18356085323
         * password : null
         * nick_name : null
         * type : null
         * sex : null
         * birthday : null
         * profession : null
         * phone : 18356085323
         * email : null
         * address_dict : null
         * address_city : null
         * address_prov : null
         * adress_details : null
         * head_image : null
         * login_ip : null
         * login_time : null
         * live_indt : 0
         * salt : null
         * register_type : 0
         * id_card : null
         * curr_total_money : 290
         * grade : 04b23f16024d11e7b91c1c872c63f5fc
         * work_unit : null
         * licensing_time : null
         * curr_total_integration : 290
         * money : 10000
         * regist_time : 1500888589000
         * address_prov_name : null
         * address_city_name : null
         * address_dict_name : null
         * grade_name : null
         * regist_time_ex : null
         */

        private MapBean map;
        private String cnd_type;
        private String ob_type;
        private String id;
        private String user_name;
        private Object password;
        private String nick_name;
        private Object type;
        private Object sex;
        private Object birthday;
        private Object profession;
        private String phone;
        private Object email;
        private Object address_dict;
        private Object address_city;
        private Object address_prov;
        private Object adress_details;
        private Object head_image;
        private Object login_ip;
        private Object login_time;
        private String live_indt;
        private Object salt;
        private String register_type;
        private String id_card;
        private double curr_total_money;
        private String grade;
        private Object work_unit;
        private Object licensing_time;
        private int curr_total_integration;
        private double money;
        private long regist_time;
        private Object address_prov_name;
        private Object address_city_name;
        private Object address_dict_name;
        private Object grade_name;
        private Object regist_time_ex;
        private List<?> relationModelList;
        private List<?> orderByModelList;

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

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public Object getPassword() {
            return password;
        }

        public void setPassword(Object password) {
            this.password = password;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public Object getSex() {
            return sex;
        }

        public void setSex(Object sex) {
            this.sex = sex;
        }

        public Object getBirthday() {
            return birthday;
        }

        public void setBirthday(Object birthday) {
            this.birthday = birthday;
        }

        public Object getProfession() {
            return profession;
        }

        public void setProfession(Object profession) {
            this.profession = profession;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public Object getAddress_dict() {
            return address_dict;
        }

        public void setAddress_dict(Object address_dict) {
            this.address_dict = address_dict;
        }

        public Object getAddress_city() {
            return address_city;
        }

        public void setAddress_city(Object address_city) {
            this.address_city = address_city;
        }

        public Object getAddress_prov() {
            return address_prov;
        }

        public void setAddress_prov(Object address_prov) {
            this.address_prov = address_prov;
        }

        public Object getAdress_details() {
            return adress_details;
        }

        public void setAdress_details(Object adress_details) {
            this.adress_details = adress_details;
        }

        public Object getHead_image() {
            return head_image;
        }

        public void setHead_image(Object head_image) {
            this.head_image = head_image;
        }

        public Object getLogin_ip() {
            return login_ip;
        }

        public void setLogin_ip(Object login_ip) {
            this.login_ip = login_ip;
        }

        public Object getLogin_time() {
            return login_time;
        }

        public void setLogin_time(Object login_time) {
            this.login_time = login_time;
        }

        public String getLive_indt() {
            return live_indt;
        }

        public void setLive_indt(String live_indt) {
            this.live_indt = live_indt;
        }

        public Object getSalt() {
            return salt;
        }

        public void setSalt(Object salt) {
            this.salt = salt;
        }

        public String getRegister_type() {
            return register_type;
        }

        public void setRegister_type(String register_type) {
            this.register_type = register_type;
        }

        public String getId_card() {
            return id_card;
        }

        public void setId_card(String id_card) {
            this.id_card = id_card;
        }

        public double getCurr_total_money() {
            return curr_total_money;
        }

        public void setCurr_total_money(double curr_total_money) {
            this.curr_total_money = curr_total_money;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public Object getWork_unit() {
            return work_unit;
        }

        public void setWork_unit(Object work_unit) {
            this.work_unit = work_unit;
        }

        public Object getLicensing_time() {
            return licensing_time;
        }

        public void setLicensing_time(Object licensing_time) {
            this.licensing_time = licensing_time;
        }

        public int getCurr_total_integration() {
            return curr_total_integration;
        }

        public void setCurr_total_integration(int curr_total_integration) {
            this.curr_total_integration = curr_total_integration;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public long getRegist_time() {
            return regist_time;
        }

        public void setRegist_time(long regist_time) {
            this.regist_time = regist_time;
        }

        public Object getAddress_prov_name() {
            return address_prov_name;
        }

        public void setAddress_prov_name(Object address_prov_name) {
            this.address_prov_name = address_prov_name;
        }

        public Object getAddress_city_name() {
            return address_city_name;
        }

        public void setAddress_city_name(Object address_city_name) {
            this.address_city_name = address_city_name;
        }

        public Object getAddress_dict_name() {
            return address_dict_name;
        }

        public void setAddress_dict_name(Object address_dict_name) {
            this.address_dict_name = address_dict_name;
        }

        public Object getGrade_name() {
            return grade_name;
        }

        public void setGrade_name(Object grade_name) {
            this.grade_name = grade_name;
        }

        public Object getRegist_time_ex() {
            return regist_time_ex;
        }

        public void setRegist_time_ex(Object regist_time_ex) {
            this.regist_time_ex = regist_time_ex;
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
}

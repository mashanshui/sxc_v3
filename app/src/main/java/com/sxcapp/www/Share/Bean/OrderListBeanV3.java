package com.sxcapp.www.Share.Bean;

/**
 * Created by wenleitao on 2018/5/4.
 */

public class OrderListBeanV3 {

    /**
     * order_no : 18050410321364
     * car_name : 奇瑞eQ
     * car_image : http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/21/c5eecdcc564649bca22f1412213e4a8e.png?Expires=1839636607&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=%2FO4KC0wS3IiNTPcooJG7QfJx2L4%3D
     * number_seats : 4
     * order_time : 2018-05-04 10:32:24
     * order_state : 4
     * evaluate_state:0;
     */

    private String order_no;
    private String car_name;
    private String car_image;
    private int number_seats;
    private String order_time;
    private int order_state;
    private int order_type;
    private int evaluate_state;

    public int getEvaluate_state() {
        return evaluate_state;
    }

    public void setEvaluate_state(int evaluate_state) {
        this.evaluate_state = evaluate_state;
    }

    public int getOrder_type() {
        return order_type;
    }

    public void setOrder_type(int order_type) {
        this.order_type = order_type;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getCar_image() {
        return car_image;
    }

    public void setCar_image(String car_image) {
        this.car_image = car_image;
    }

    public int getNumber_seats() {
        return number_seats;
    }

    public void setNumber_seats(int number_seats) {
        this.number_seats = number_seats;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public int getOrder_state() {
        return order_state;
    }

    public void setOrder_state(int order_state) {
        this.order_state = order_state;
    }
}

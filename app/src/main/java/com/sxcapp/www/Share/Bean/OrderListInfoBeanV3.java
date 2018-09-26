package com.sxcapp.www.Share.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2018/5/4.
 */

public class OrderListInfoBeanV3 {

    /**
     * total_page : 5
     * list : [{"order_no":"18050410321364","car_name":"奇瑞eQ","car_image":"http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/21/c5eecdcc564649bca22f1412213e4a8e.png?Expires=1839636607&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=%2FO4KC0wS3IiNTPcooJG7QfJx2L4%3D","number_seats":4,"order_time":"2018-05-04 10:32:24","order_state":"4","order_type":2},{"order_no":"18050410189745","car_name":"奇瑞eQ","car_image":"http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/21/c5eecdcc564649bca22f1412213e4a8e.png?Expires=1839636607&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=%2FO4KC0wS3IiNTPcooJG7QfJx2L4%3D","number_seats":4,"order_time":"2018-05-04 10:18:31","order_state":"4","order_type":2},{"order_no":"18050410144499","car_name":"奇瑞eQ","car_image":"http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/21/c5eecdcc564649bca22f1412213e4a8e.png?Expires=1839636607&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=%2FO4KC0wS3IiNTPcooJG7QfJx2L4%3D","number_seats":4,"order_time":"2018-05-04 10:14:37","order_state":"4","order_type":2},{"order_no":"18050410129492","car_name":"奇瑞eQ","car_image":"http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/21/c5eecdcc564649bca22f1412213e4a8e.png?Expires=1839636607&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=%2FO4KC0wS3IiNTPcooJG7QfJx2L4%3D","number_seats":4,"order_time":"2018-05-04 10:12:36","order_state":"4","order_type":2},{"order_no":"18050410109428","car_name":"奇瑞eQ","car_image":"http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/21/c5eecdcc564649bca22f1412213e4a8e.png?Expires=1839636607&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=%2FO4KC0wS3IiNTPcooJG7QfJx2L4%3D","number_seats":4,"order_time":"2018-05-04 10:10:05","order_state":"4","order_type":2},{"order_no":"18050410061155","car_name":"奇瑞eQ","car_image":"http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/21/c5eecdcc564649bca22f1412213e4a8e.png?Expires=1839636607&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=%2FO4KC0wS3IiNTPcooJG7QfJx2L4%3D","number_seats":4,"order_time":"2018-05-04 10:06:25","order_state":"4","order_type":2},{"order_no":"18050322305670","car_name":"奇瑞eQ","car_image":"http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/21/c5eecdcc564649bca22f1412213e4a8e.png?Expires=1839636607&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=%2FO4KC0wS3IiNTPcooJG7QfJx2L4%3D","number_seats":4,"order_time":"2018-05-03 22:30:04","order_state":"4","order_type":2},{"order_no":"18050322245128","car_name":"奇瑞eQ","car_image":"http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/21/c5eecdcc564649bca22f1412213e4a8e.png?Expires=1839636607&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=%2FO4KC0wS3IiNTPcooJG7QfJx2L4%3D","number_seats":4,"order_time":"2018-05-03 22:24:21","order_state":"4","order_type":2},{"order_no":"18050322246929","car_name":"奇瑞eQ","car_image":"http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/21/c5eecdcc564649bca22f1412213e4a8e.png?Expires=1839636607&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=%2FO4KC0wS3IiNTPcooJG7QfJx2L4%3D","number_seats":4,"order_time":"2018-05-03 22:24:07","order_state":"4","order_type":2},{"order_no":"18050322238984","car_name":"奇瑞eQ","car_image":"http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareCar/2018/04/21/c5eecdcc564649bca22f1412213e4a8e.png?Expires=1839636607&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=%2FO4KC0wS3IiNTPcooJG7QfJx2L4%3D","number_seats":4,"order_time":"2018-05-03 22:23:33","order_state":"4","order_type":2}]
     */

    private int total_page;
    private List<OrderListBeanV3> list;

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public List<OrderListBeanV3> getList() {
        return list;
    }

    public void setList(List<OrderListBeanV3> list) {
        this.list = list;
    }


}

package com.sxcapp.www.UserCenter.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2018/5/17.
 */

public class EventListByTypeBeanV3 {


    private List<ActivityBean> activity;

    public List<ActivityBean> getActivity() {
        return activity;
    }

    public void setActivity(List<ActivityBean> activity) {
        this.activity = activity;
    }

    public static class ActivityBean {
        /**
         * path :
         * expireTime : null
         * createTime : null
         * imageUrl : http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareActivity/2018/05/10/55dfe8cf7d544d2684d7f91298ba349c.jpg?Expires=1841324476&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=Rx4Ii9lue5eTZN4z49WjqHqRV0s%3D
         * id : 13781be3e23811e7b022ecf4bbbfe9f8
         * title : 抽奖活动
         * type : 1
         */

        private String path;
        private String expireTime;
        private String createTime;
        private String imageUrl;
        private String id;
        private String title;
        private String type;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(String expireTime) {
            this.expireTime = expireTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}

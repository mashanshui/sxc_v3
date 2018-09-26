package com.sxcapp.www.UserCenter.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2017/12/14.
 */

public class ActivityListBean {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * map : {}
         * relationModelList : []
         * orderByModelList : []
         * cnd_type : default
         * ob_type : default
         * id : 82ae76fee07911e7ac4900163e1ae70a
         * title : 测试
         * image_url : upload/shareimage/20171214/zoeUA70afHC8tRjcJdUFLbyciKMSsPaK105008698.jpg
         * path : 123456
         * create_time : 2017-12-14 10:49:34
         * update_time : 2017-12-14 10:50:11
         * state : 1
         * creator : sharecar
         */

        private MapBean map;
        private String cnd_type;
        private String ob_type;
        private String id;
        private String title;
        private String image_url;
        private String path;
        private String create_time;
        private String update_time;
        private String state;
        private String creator;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
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

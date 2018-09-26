package com.sxcapp.www.UserCenter.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2017/8/12.
 */

public class MessageInfo {

    /**
     * totalNum : 37
     * pageIndex : 0
     * totalPageNum : 4
     * list : [{"map":{},"relationModelList":[],"orderByModelList":[],"cnd_type":"default","ob_type":"default","id":"46abe8f27e6111e7a43900163e1ae70a","title":"taowenlei","push_state":"1","user_id":null,"content_type":"1","image_path":null,"content":"8784213214320","url":null,"timing":"0","create_time":1502434187000,"update_time":1502434187000,"push_time":1502434187000,"estimate_push_time":null,"visited":"1","order_id":null,"assortment":"1"},{"map":{},"relationModelList":[],"orderByModelList":[],"cnd_type":"default","ob_type":"default","id":"1f4729c77e6111e7a43900163e1ae70a","title":null,"push_state":"1","user_id":null,"content_type":"1","image_path":"upload/image/20170811/OOByswodoifhsPDqmm8aveBdtcoNypUA144755014.JPG","content":"www","url":null,"timing":"0","create_time":1502434098000,"update_time":1502434098000,"push_time":1502434098000,"estimate_push_time":null,"visited":"1","order_id":null,"assortment":"1"},{"map":{},"relationModelList":[],"orderByModelList":[],"cnd_type":"default","ob_type":"default","id":"05fa32d67e6011e7a43900163e1ae70a","title":"我是标题！！！","push_state":"1","user_id":null,"content_type":"0","image_path":null,"content":null,"url":"http://www.baidu.com","timing":"0","create_time":1502433649000,"update_time":1502433649000,"push_time":1502433649000,"estimate_push_time":null,"visited":"1","order_id":null,"assortment":"1"},{"map":{},"relationModelList":[],"orderByModelList":[],"cnd_type":"default","ob_type":"default","id":"bc13eb777e5f11e7a43900163e1ae70a","title":"我是标题！！！","push_state":"1","user_id":null,"content_type":"1","image_path":null,"content":"我是内容！！！！","url":null,"timing":"0","create_time":1502433525000,"update_time":1502433525000,"push_time":1502433525000,"estimate_push_time":null,"visited":"1","order_id":null,"assortment":"1"},{"map":{},"relationModelList":[],"orderByModelList":[],"cnd_type":"default","ob_type":"default","id":"64e010977e5f11e7a43900163e1ae70a","title":"我是可爱的标题哦~","push_state":"1","user_id":null,"content_type":"1","image_path":null,"content":"我是可爱的文本哦~~","url":null,"timing":"0","create_time":1502433379000,"update_time":1502433379000,"push_time":1502433379000,"estimate_push_time":null,"visited":"1","order_id":null,"assortment":"1"},{"map":{},"relationModelList":[],"orderByModelList":[],"cnd_type":"default","ob_type":"default","id":"f4b3cd2e7e5e11e7a43900163e1ae70a","title":"我是可爱的标题哦~","push_state":"1","user_id":null,"content_type":"1","image_path":null,"content":"我是可爱的文本哦~~","url":null,"timing":"0","create_time":1502433190000,"update_time":1502433190000,"push_time":1502433190000,"estimate_push_time":null,"visited":"1","order_id":null,"assortment":"1"},{"map":{},"relationModelList":[],"orderByModelList":[],"cnd_type":"default","ob_type":"default","id":"3d416da07e5e11e7a43900163e1ae70a","title":"我是标题","push_state":"1","user_id":null,"content_type":"0","image_path":null,"content":null,"url":"http://www.baidu.com","timing":"0","create_time":1502432883000,"update_time":1502432883000,"push_time":1502432883000,"estimate_push_time":null,"visited":"1","order_id":null,"assortment":"1"},{"map":{},"relationModelList":[],"orderByModelList":[],"cnd_type":"default","ob_type":"default","id":"7fe605257e5d11e7a43900163e1ae70a","title":"我是标题","push_state":"1","user_id":null,"content_type":"0","image_path":null,"content":null,"url":"http://www.baidu.com","timing":"0","create_time":1502432565000,"update_time":1502432565000,"push_time":1502432565000,"estimate_push_time":null,"visited":"1","order_id":null,"assortment":"1"},{"map":{},"relationModelList":[],"orderByModelList":[],"cnd_type":"default","ob_type":"default","id":"58dff0b07e5d11e7a43900163e1ae70a","title":"随心车","push_state":"1","user_id":null,"content_type":"0","image_path":null,"content":null,"url":"http://www.baidu.com","timing":"0","create_time":1502432500000,"update_time":1502432500000,"push_time":1502432500000,"estimate_push_time":null,"visited":"1","order_id":null,"assortment":"1"},{"map":{},"relationModelList":[],"orderByModelList":[],"cnd_type":"default","ob_type":"default","id":"40d6e2247e5a11e7a43900163e1ae70a","title":null,"push_state":"1","user_id":null,"content_type":"1","image_path":"upload/image/20170811/Tu0s2oWRel5mBbR8aKDEU6SeqGmwKLtD135859737.JPG","content":"<p> 123456 <\/p> <p> <br /> <\/p>","url":null,"timing":"0","create_time":1502431148000,"update_time":1502431148000,"push_time":1502431148000,"estimate_push_time":null,"visited":"1","order_id":null,"assortment":"1"}]
     */

    private int totalNum;
    private int pageIndex;
    private int totalPageNum;
    private List<ListBean> list;

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

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
         * id : 46abe8f27e6111e7a43900163e1ae70a
         * title : taowenlei
         * push_state : 1
         * user_id : null
         * content_type : 1
         * image_path : null
         * content : 8784213214320
         * url : null
         * timing : 0
         * create_time : 1502434187000
         * update_time : 1502434187000
         * push_time : 1502434187000
         * estimate_push_time : null
         * visited : 1
         * order_id : null
         * assortment : 1
         */

        private MapBean map;
        private String cnd_type;
        private String ob_type;
        private String id;
        private String title;
        private String push_state;
        private Object user_id;
        private int content_type;
        private Object image_path;
        private String content;
        private String url;
        private String timing;
        private long create_time;
        private long update_time;
        private long push_time;
        private Object estimate_push_time;
        private int visited;
        private Object order_id;
        private String assortment;
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

        public String getPush_state() {
            return push_state;
        }

        public void setPush_state(String push_state) {
            this.push_state = push_state;
        }

        public Object getUser_id() {
            return user_id;
        }

        public void setUser_id(Object user_id) {
            this.user_id = user_id;
        }

        public int getContent_type() {
            return content_type;
        }

        public void setContent_type(int content_type) {
            this.content_type = content_type;
        }

        public Object getImage_path() {
            return image_path;
        }

        public void setImage_path(Object image_path) {
            this.image_path = image_path;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTiming() {
            return timing;
        }

        public void setTiming(String timing) {
            this.timing = timing;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public long getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(long update_time) {
            this.update_time = update_time;
        }

        public long getPush_time() {
            return push_time;
        }

        public void setPush_time(long push_time) {
            this.push_time = push_time;
        }

        public Object getEstimate_push_time() {
            return estimate_push_time;
        }

        public void setEstimate_push_time(Object estimate_push_time) {
            this.estimate_push_time = estimate_push_time;
        }

        public int getVisited() {
            return visited;
        }

        public void setVisited(int visited) {
            this.visited = visited;
        }

        public Object getOrder_id() {
            return order_id;
        }

        public void setOrder_id(Object order_id) {
            this.order_id = order_id;
        }

        public String getAssortment() {
            return assortment;
        }

        public void setAssortment(String assortment) {
            this.assortment = assortment;
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

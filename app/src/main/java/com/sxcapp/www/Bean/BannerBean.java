package com.sxcapp.www.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2017/8/2.
 * 买车卖车租车 实体类
 */

public class BannerBean {

    /**
     * image_path : upload/image/20170616/thumbnails/HI4YmKlpLLi67grzxyfA4qNtN1pLWsnC210241931.jpg
     */

    private String image_path;
    /**
     * map : {}
     * relationModelList : []
     * orderByModelList : []
     * cnd_type : default
     * ob_type : default
     * id : b5b876f278b611e7a43900163e1ae70a
     * banner_name : 租车测试
     * create_user_id : 607dd46e481711e6b208408d5c04d4aa
     * create_time : 1501811130000
     * start_time : 1501811100000
     * end_time : 1533347100000
     * live_indt : null
     * sort : 2
     * banner_type : 0
     * link_url : www.baidu.com
     * rich_text : null
     * terminal_type : 2
     */

    private MapBean map;
    private String cnd_type;
    private String ob_type;
    private String id;
    private String banner_name;
    private String create_user_id;
    private long create_time;
    private long start_time;
    private long end_time;
    private Object live_indt;
    private int sort;
    private String banner_type;
    private String link_url;
    private Object rich_text;
    private String terminal_type;
    private List<?> relationModelList;
    private List<?> orderByModelList;

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

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

    public String getBanner_name() {
        return banner_name;
    }

    public void setBanner_name(String banner_name) {
        this.banner_name = banner_name;
    }

    public String getCreate_user_id() {
        return create_user_id;
    }

    public void setCreate_user_id(String create_user_id) {
        this.create_user_id = create_user_id;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public Object getLive_indt() {
        return live_indt;
    }

    public void setLive_indt(Object live_indt) {
        this.live_indt = live_indt;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getBanner_type() {
        return banner_type;
    }

    public void setBanner_type(String banner_type) {
        this.banner_type = banner_type;
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }

    public Object getRich_text() {
        return rich_text;
    }

    public void setRich_text(Object rich_text) {
        this.rich_text = rich_text;
    }

    public String getTerminal_type() {
        return terminal_type;
    }

    public void setTerminal_type(String terminal_type) {
        this.terminal_type = terminal_type;
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

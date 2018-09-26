package com.sxcapp.www.UserCenter.Bean;

/**
 * Created by wenleitao on 2018/5/17.
 */

public class EventCountBeanV3 {


    /**
     * oilTime : 0
     * oilLong : 0
     * luxury : 0
     * eleTime : 2
     * eleLong : 1
     */

    private int oilTime;
    private int oilLong;
    private int luxury;
    private int eleTime;
    private int eleLong;
    /**
     * theme : {"term_flag":"1","theme_id":"402880e4638d068001638d07a7f10001","title":"毕业季"}
     */

    private ThemeBean theme;


    public int getOilTime() {
        return oilTime;
    }

    public void setOilTime(int oilTime) {
        this.oilTime = oilTime;
    }

    public int getOilLong() {
        return oilLong;
    }

    public void setOilLong(int oilLong) {
        this.oilLong = oilLong;
    }

    public int getLuxury() {
        return luxury;
    }

    public void setLuxury(int luxury) {
        this.luxury = luxury;
    }

    public int getEleTime() {
        return eleTime;
    }

    public void setEleTime(int eleTime) {
        this.eleTime = eleTime;
    }

    public int getEleLong() {
        return eleLong;
    }

    public void setEleLong(int eleLong) {
        this.eleLong = eleLong;
    }

    public ThemeBean getTheme() {
        return theme;
    }

    public void setTheme(ThemeBean theme) {
        this.theme = theme;
    }

    public static class ThemeBean {
        /**
         * term_flag : 1
         * theme_id : 402880e4638d068001638d07a7f10001
         * title : 毕业季
         */

        private int term_flag;
        private String theme_id;
        private String title;

        public int getTerm_flag() {
            return term_flag;
        }

        public void setTerm_flag(int term_flag) {
            this.term_flag = term_flag;
        }

        public String getTheme_id() {
            return theme_id;
        }

        public void setTheme_id(String theme_id) {
            this.theme_id = theme_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}

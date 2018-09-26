package com.sxcapp.www.UserCenter.Bean;

import java.util.List;

/**
 * Created by wenleitao on 2018/5/28.
 */

public class SnapShotDetailBeanV3 {

    /**
     * term_time : 2018-05-23~2018-06-30
     * theme_image : http://hjxnysxc.oss-cn-shanghai.aliyuncs.com/upload/shareTheme/2018/05/24/7a237160bb1449c9b14a19cb9431c670.jpg?Expires=1842486824&OSSAccessKeyId=LTAIG5XMJzasA3o5&Signature=fUZAQBGkKZmKd47zJ2YOusgb7ho%3D
     * theme_id : 402880e4638d068001638d07a7f10001
     * award_title : 活动进行中
     * title : 毕业季
     * content : ["毕业季毕业季毕业季","摆拍福利大发送","赶快参与吧"]
     * award_remark : ["毕业季活动当前火热进行中,快来参加吧","最终解释权归随心车平台所有"]
     */

    private String term_time;
    private String theme_image;
    private String theme_id;
    private String award_title;
    private String title;
    private List<String> content;
    private List<String> award_remark;
    private int theme_flag;

    public int getTheme_flag() {
        return theme_flag;
    }

    public void setTheme_flag(int theme_flag) {
        this.theme_flag = theme_flag;
    }

    public String getTerm_time() {
        return term_time;
    }

    public void setTerm_time(String term_time) {
        this.term_time = term_time;
    }

    public String getTheme_image() {
        return theme_image;
    }

    public void setTheme_image(String theme_image) {
        this.theme_image = theme_image;
    }

    public String getTheme_id() {
        return theme_id;
    }

    public void setTheme_id(String theme_id) {
        this.theme_id = theme_id;
    }

    public String getAward_title() {
        return award_title;
    }

    public void setAward_title(String award_title) {
        this.award_title = award_title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public List<String> getAward_remark() {
        return award_remark;
    }

    public void setAward_remark(List<String> award_remark) {
        this.award_remark = award_remark;
    }
}

package com.sxcapp.www.Download.Bean;

/**
 * Created by wenleitao on 2017/8/18.
 */

public class VersionBean {


    /**
     * adminVersions : 22
     * appUrl : http://www.sxzcar.com/static/apk/sxcar.apk
     * id : 1
     * upgrade : 0
     */

    private int adminVersions;
    private String appUrl;
    private String id;
    private int upgrade;

    public int getAdminVersions() {
        return adminVersions;
    }

    public void setAdminVersions(int adminVersions) {
        this.adminVersions = adminVersions;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUpgrade() {
        return upgrade;
    }

    public void setUpgrade(int upgrade) {
        this.upgrade = upgrade;
    }
}

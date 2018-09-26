package com.sxcapp.www.Base;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by wenleitao on 2017/7/14.
 */

public class SerMap implements Serializable{
    private HashMap<String,String>map;
    public  SerMap(){

    }

    public HashMap<String, String> getMap() {
        return map;
    }

    public void setMap(HashMap<String, String> map) {
        this.map = map;

    }

}

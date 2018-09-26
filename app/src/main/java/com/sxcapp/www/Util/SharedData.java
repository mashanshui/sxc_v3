package com.sxcapp.www.Util;

import android.content.SharedPreferences;
import android.util.Log;

import com.sxcapp.www.MyApplication;

import java.util.List;
import java.util.Observable;

public class SharedData extends Observable {
    public static final String TAG = "SharedData";

    public static final String PREFS_NAME = "com.sxcapp.helper";
    public final static String _user_id = "user_id";
    public final static String _user_phone = "user_phone";
    public final static String _user_name = "user_name";
    public final static String _user_id_card = "user_id_card";
    public final static String _avatar = "avatar";
    public final static String _is_able_lease = "is_able_lease";
    public final static String _first_start = "first_start";
    public final static String _order_time = "order_time";
    public final static String _phone_type = "phone_type";
    public final static String _everyday_login = "everyday_login";


    public static enum SavedValues {
        ONE_DATA_ADDED,
        ONE_DATA_REMOVED,
        ALL_DATA_REMOVED
    }

    private static SharedData _instance;

    SharedPreferences _settings;

    public boolean isLogin() {
        if (getString(_user_id) != null && getString(_user_id).length() > 0) {
            return true;
        }

        return false;
    }

    private SharedData() {
        // Restore preferences
        Log.e(TAG, "init");
        Log.e(TAG, "SharedData: " + MyApplication.getInstance().getApplicationContext());
        _settings = MyApplication.getInstance().getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
    }

    public static SharedData getInstance() {
        if (_instance == null) {
            _instance = new SharedData();
        }
        return _instance;
    }

    public void cancel() {
        Log.e(TAG, "cancel");
        _instance = null;
    }

    public boolean set(String key, Object value) {
        SharedPreferences.Editor editor = _settings.edit();
        set(key, value, editor);
        boolean ret = editor.commit();
        if (ret == true) {
            setChanged();
            notifyObservers(SavedValues.ONE_DATA_ADDED);
        }
        return ret;
    }


    private void set(String key, Object value, SharedPreferences.Editor editor) {
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Boolean) {
            boolean b = (Boolean) value;
            editor.putBoolean(key, b);
        } else if (value instanceof Float) {
            float f = (Float) value;
            editor.putFloat(key, f);
        } else if (value instanceof Integer) {
            int i = (Integer) value;
            editor.putInt(key, i);
            Log.i(TAG, "Databasekey " + key);
            Log.i(TAG, "value " + value);
        } else if (value instanceof Long) {
            long l = (Long) value;
            editor.putLong(key, l);
        } else if (value instanceof List) {

        }
    }

    public String getString(String key) {
        return _settings.getString(key, "");
    }

    public boolean getBooleanDefValueFalse(String key) {
        return _settings.getBoolean(key, false);
    }

    public boolean getBooleanDefValueTrue(String key) // php
    {
        return _settings.getBoolean(key, true);
    }

    public float getFloat(String key) {
        return _settings.getFloat(key, -1);
    }

    public int getInt(String key) {
        return _settings.getInt(key, -1);
    }

    public long getLong(String key) {
        return _settings.getLong(key, -1);
    }


    public boolean removeAll() {
        SharedPreferences.Editor editor = _settings.edit();
        editor.clear();
        boolean ret = editor.commit();
        if (ret == true) {
            setChanged();
            notifyObservers(SavedValues.ALL_DATA_REMOVED);
        }
        return ret;
    }

    public boolean removeDB(String key) {
        SharedPreferences.Editor editor = _settings.edit();
        editor.remove(key);
        boolean ret = editor.commit();
        if (ret == true) {
            setChanged();
            notifyObservers(SavedValues.ONE_DATA_REMOVED);
        }
        return ret;
    }

    public void logout() {
        set(_user_id, "");
        set(_avatar, "");
        set(_user_phone, "");
        set(_user_name, "");


    }

}
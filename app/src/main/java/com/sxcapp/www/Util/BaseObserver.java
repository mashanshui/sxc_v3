package com.sxcapp.www.Util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.activity.BaseActivity;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wenleitao on 2017/7/10.
 */

public abstract class BaseObserver<T> implements Observer<Result<T>> {

    private static final String TAG = "BaseObserver";
    private Context mContext;

    protected BaseObserver(Context context) {
        this.mContext = context;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Result<T> value) {

        if (value.isFlag()) {
            T t = value.getObj();
            onHandleSuccess(t);
        } else {
            onHandleError(value.getMsg());
        }
    }

    @Override
    public void onError(Throwable e) {
        Logger.e(e.toString());
        if ("java.net.SocketTimeoutException: timeout".equals(e.toString())) {
            ((BaseActivity) mContext).showToast("网络连接超时");
        } else {
            ((BaseActivity) mContext).showToast("网络连接失败");
        }
        ((BaseActivity) mContext).removeProgressDlg();
    }


    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete");
    }


    protected abstract void onHandleSuccess(T t);

    protected void onHandleError(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        ((BaseActivity) mContext).removeProgressDlg();
    }


}
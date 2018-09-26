package com.sxcapp.www.Lease.Bean;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wenleitao on 2017/7/10.
 */

public abstract class PrepayObserver<T> implements Observer<PrepayResult<T>> {

    private static final String TAG = "LoginObserver";
    private Context mContext;

    protected PrepayObserver(Context context) {
        this.mContext = context.getApplicationContext();
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(PrepayResult<T> value) {
        if ("1".equals(value.getResultCode())) {
            T t = value.getData();
            onHandleSuccess(t);
        } else {
            onHandleError(value.getMsg());
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "error:" + e.toString());
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete");
    }


    protected abstract void onHandleSuccess(T t);

    protected void onHandleError(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
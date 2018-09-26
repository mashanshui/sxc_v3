package com.sxcapp.www.Base;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.sxcapp.www.activity.BaseActivity;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wenleitao on 2017/7/10.
 * code类型的观察者
 */

public abstract class CodeObserverV3<T> implements Observer<CodeResultV3<T>> {

    private static final String TAG = "CodeObserverV3";
    private Context mContext;

    protected CodeObserverV3(Context context) {
        this.mContext = context;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(CodeResultV3<T> value) {
        if ("666".equals(value.getCode())) {
            T t = value.getObj();
            onHandleSuccess(t);
        } else {
            onHandleError(value.getMsg(), value);
        }

    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "error:" + e.toString());
        if ("java.net.SocketTimeoutException: timeout".equals(e.toString())){
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

    protected void onHandleError(String msg, CodeResultV3<T> value) {
        ((BaseActivity) mContext).removeProgressDlg();
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    protected void onHandleEx(CodeResultV3<T> value) {

    }
}
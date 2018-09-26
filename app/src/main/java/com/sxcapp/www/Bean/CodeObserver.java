package com.sxcapp.www.Bean;

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

public abstract class CodeObserver<T> implements Observer<CodeResult<T>> {

    private static final String TAG = "CodeObserver";
    private Context mContext;

    protected CodeObserver(Context context) {
        this.mContext = context.getApplicationContext();
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(CodeResult<T> value) {
        if ("200".equals(value.getResult())) {
            T t = value.getDataT();
            onHandleSuccess(t);
        } else {
            onHandleError(value.getMsg());
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "error:" + e.toString());
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
    }
}
package com.sxcapp.www.Util;

import android.app.Activity;
import android.widget.Toast;

import com.sxcapp.www.activity.BaseActivity;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wenleitao on 2018/5/17.
 */

public class RxSchedulers {
//    private WeakReference<Activity> mActivity;
//
//    public RxSchedulers(Activity mActivity) {
//        this.mActivity = new WeakReference<>(mActivity);
//    }
//
//    public  <T> ObservableTransformer<T, T> compose() {
//        return new ObservableTransformer<T, T>() {
//            @Override
//            public ObservableSource<T> apply(Observable<T> observable) {
//                return observable
//                        .subscribeOn(Schedulers.io())
//                        .doOnSubscribe(new Consumer<Disposable>() {
//                            @Override
//                            public void accept(Disposable disposable) throws Exception {
//                                if (!NetUtil.isConnected(this)) {
//                                    Toast.makeText(mActivity, "网络连接异常", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        })
//                        .observeOn(AndroidSchedulers.mainThread());
//            }
//        };
//    }


}

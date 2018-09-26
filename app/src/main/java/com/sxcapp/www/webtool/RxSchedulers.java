package com.sxcapp.www.webtool;

import android.widget.Toast;

import com.sxcapp.www.MyApplication;
import com.sxcapp.www.R;
import com.sxcapp.www.Util.NetUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wenleitao on 2017/7/10.
 */

public class RxSchedulers {


    public static <T> ObservableTransformer<T, T> compose() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                if (!NetUtil.isConnected(MyApplication.GetAppContext())) {
                                    Toast.makeText(MyApplication.GetAppContext(), R.string.toast_network_error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}

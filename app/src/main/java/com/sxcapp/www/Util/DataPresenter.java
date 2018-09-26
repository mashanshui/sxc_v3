package com.sxcapp.www.Util;

import android.app.Activity;

import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.UserCenter.Bean.BalanceInfoBeanV3;
import com.sxcapp.www.UserCenter.HttpService.UserCenterService;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManagerV3;

import io.reactivex.Observable;

/**
 * Created by wenleitao on 2018/5/17.
 */

public class DataPresenter {
    private BaseActivity activity;

    public DataPresenter(BaseActivity activity) {
        this.activity = activity;
    }

    private void loadData() {
        UserCenterService service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
        Observable<CodeResultV3<BalanceInfoBeanV3>> ob = service.getBalanceInfoV3(SharedData.getInstance().getString(SharedData._user_id));
        ob.compose(activity.compose(activity.<CodeResultV3<BalanceInfoBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<BalanceInfoBeanV3>(activity) {
            @Override
            protected void onHandleSuccess(final BalanceInfoBeanV3 balanceInfoBeanV3) {

            }
        });

    }
}

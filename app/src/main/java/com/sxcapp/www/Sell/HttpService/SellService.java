package com.sxcapp.www.Sell.HttpService;

import com.sxcapp.www.Bean.BannerBean;
import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.Sell.Bean.ConsultPhoneBean;
import com.sxcapp.www.Util.Properties;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by wenleitao on 2017/7/22.
 */

public interface SellService {
    @POST(Properties.getConsultPhone)
    Observable<Result<ConsultPhoneBean>> getConsultPhone();

    @POST(Properties.sellSubmit)
    Observable<Result<Object>> sellSubmit(
            @Query("phone") String phone
    );

    @POST(Properties.getAppBanner)
    Observable<Result<List<BannerBean>>> getAppBanner(
            @Query("type") int type
    );


}

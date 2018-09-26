package com.sxcapp.www.Buy.HttpService;

import com.sxcapp.www.Bean.BannerBean;
import com.sxcapp.www.Bean.CodeResult;
import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.Buy.Bean.OldCarDetail;
import com.sxcapp.www.Buy.Bean.OldCarListResult;
import com.sxcapp.www.Lease.Bean.Recommend;
import com.sxcapp.www.Util.Properties;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by wenleitao on 2017/7/17.
 */

public interface BuyService {
    @POST(Properties.getOldCarList)
    Observable<Result<OldCarListResult>> getOldCarList(
            @QueryMap() Map<String, String> map
    );

    @POST(Properties.getOldCarDetail)
    Observable<CodeResult<OldCarDetail>> getOldCarDetail(
            @Query("carId") String carId
    );

    //    校验验证码是否正确
    @POST(Properties.checkCode)
    Observable<Result<Object>> checkCode(
            @Query("user_name") String phone,
            @Query("user_code") String code
    );

    //    预约看车接口
    @POST(Properties.appointment)
    Observable<CodeResult<Object>> appointment(
            @Query("userId") String user_id,
            @Query("carId") String car_id,
            @Query("phone") String phone
    );

    @POST(Properties.getAppBanner)
    Observable<Result<List<BannerBean>>> getAppBanner(
            @Query("type") int type
    );

    @POST(Properties.getLeaseRecommend)
    Observable<Result<List<Recommend>>> getLeaseRecommend(
            @Query("source_type") int id
    );
}

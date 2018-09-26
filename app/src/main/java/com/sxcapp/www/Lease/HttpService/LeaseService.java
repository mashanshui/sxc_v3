package com.sxcapp.www.Lease.HttpService;

import com.sxcapp.www.Bean.BannerBean;
import com.sxcapp.www.Bean.CodeResult;
import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.Lease.Bean.Area;
import com.sxcapp.www.Lease.Bean.City;
import com.sxcapp.www.Lease.Bean.DepositInfo;
import com.sxcapp.www.Lease.Bean.DepositNo;
import com.sxcapp.www.Lease.Bean.LeaseCarSubmitBean;
import com.sxcapp.www.Lease.Bean.LeaseCostBean;
import com.sxcapp.www.Lease.Bean.LeaseResult;
import com.sxcapp.www.Lease.Bean.PrepayIdBean;
import com.sxcapp.www.Lease.Bean.PrepayResult;
import com.sxcapp.www.Lease.Bean.Recommend;
import com.sxcapp.www.Lease.Bean.RecommendDetailInfo;
import com.sxcapp.www.Lease.Bean.Store;
import com.sxcapp.www.Lease.Bean.TypeBean;
import com.sxcapp.www.Util.Properties;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by wenleitao on 2017/7/10.
 */

public interface LeaseService {
    @POST(Properties.getArea)
    Observable<Result<List<Area>>> getArea(
            @Query("id") String id
    );

    @POST(Properties.getCity)
    Observable<Result<List<City>>> getCity();

    @POST(Properties.getStore)
    Observable<Result<List<Store>>> getStore(
            @Query("district_id") String id
    );

    @GET(Properties.getCarList)
    Observable<CodeResult<LeaseResult>> getLeaseCarList(
            @QueryMap() Map<String, String> map
    );

    @POST(Properties.getLeaseRecommend)
    Observable<Result<List<Recommend>>> getLeaseRecommend(
            @Query("source_type") int id
    );

    @POST(Properties.getLeaseCarTypeId)
    Observable<Result<List<TypeBean>>> getLeaseCarTypeId(
            @Query("pageType") int id
    );

    @POST(Properties.leaseCarCost)
    Observable<CodeResult<LeaseCostBean>> getLeaseCarCost(
            @Query("carId") String carId,
            @Query("day") String day,
            @Query("user_id") String user_id
    );

    @POST(Properties.leaseCarSubmit)
    Observable<Result<LeaseCarSubmitBean>> leaseCarSubmit(
            @QueryMap() Map<String, String> map
    );

    @POST(Properties.getWxPrepayId)
    Observable<PrepayResult<PrepayIdBean>> getPrepayId(
            @Query("out_trade_no") String order_no,
            @Query("money") String money,
            @Query("subject") String subject,
            @Query("type") String type
    );

    @POST(Properties.balancePay)
    Observable<Result<Object>> balancePay(
            @Query("userId") String userId,
            @Query("orderNo") String orderNo
    );

    @POST(Properties.getDepositInfo)
    Observable<Result<DepositInfo>> getDepositInfo(
            @Query("customer_id") String user_id,
            @Query("source_id") String car_id
    );

    @POST(Properties.getDepositNO)
    Observable<Result<DepositNo>> getDepositNo(
            @Query("order_no") String order_no
    );

    @POST(Properties.getAppBanner)
    Observable<Result<List<BannerBean>>> getAppBanner(
            @Query("type") int type
    );

    @POST(Properties.getRecommendDetail)
    Observable<CodeResult<RecommendDetailInfo>> getRecommendDetail(
            @Query("carId") String id
    );

    @POST(Properties.checkCanLease)
    Observable<Result<Object>> checkCanLease(
            @Query("user_id") String user_id
    );


}

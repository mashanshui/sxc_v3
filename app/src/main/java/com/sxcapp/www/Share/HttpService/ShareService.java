package com.sxcapp.www.Share.HttpService;

import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.Share.Bean.ConfirmDayTypeCarInfoBeanV3;
import com.sxcapp.www.Share.Bean.ElecInDayAppointReturnBeanV3;
import com.sxcapp.www.Share.Bean.ElecInDayEndInfoBeanV3;
import com.sxcapp.www.Share.Bean.ElecShareInDayRentCarListBeanV3;
import com.sxcapp.www.Share.Bean.ElecShortAppointBeanV3;
import com.sxcapp.www.Share.Bean.ElecShortAppointReturnBeanV3;
import com.sxcapp.www.Share.Bean.ElecShortBeginUseCarBeanV3;
import com.sxcapp.www.Share.Bean.ElecShortPayInfoBeanV3;
import com.sxcapp.www.Share.Bean.ElecShortPreCostBeanV3;
import com.sxcapp.www.Share.Bean.ElectricMapStoreListBeanV3;
import com.sxcapp.www.Share.Bean.ExistOrderBeanV3;
import com.sxcapp.www.Share.Bean.LuxuryAppointInfoBeanV3;
import com.sxcapp.www.Share.Bean.LuxuryAppointReturnBeanV3;
import com.sxcapp.www.Share.Bean.LuxuryCarListByStoreBeanV3;
import com.sxcapp.www.Share.Bean.LuxuryMapStoreListBeanV3;
import com.sxcapp.www.Share.Bean.LuxuryOffOrderDetailBeanV3;
import com.sxcapp.www.Share.Bean.LuxuryPreAuditInfoBeanV3;
import com.sxcapp.www.Share.Bean.LuxuryRentCarInfoBeanV3;
import com.sxcapp.www.Share.Bean.LuxuryReturnCarInfoBeanV3;
import com.sxcapp.www.Share.Bean.MapStoreListBean;
import com.sxcapp.www.Share.Bean.OffOrderDetailBeanV3;
import com.sxcapp.www.Share.Bean.OilInDayAppointReturnBeanV3;
import com.sxcapp.www.Share.Bean.OilInDayEndInfoBeanV3;
import com.sxcapp.www.Share.Bean.OilMapStoreListBeanV3;
import com.sxcapp.www.Share.Bean.OilShareInDayRentCarListBeanV3;
import com.sxcapp.www.Share.Bean.OilShortAppointBeanV3;
import com.sxcapp.www.Share.Bean.OilShortAppointReturnBeanV3;
import com.sxcapp.www.Share.Bean.OilShortBeginUseCarBeanV3;
import com.sxcapp.www.Share.Bean.OilShortPayInfoBeanV3;
import com.sxcapp.www.Share.Bean.OilShortPreCostBeanV3;
import com.sxcapp.www.Share.Bean.OrderCouponBeanV3;
import com.sxcapp.www.Share.Bean.OrderCouponSizeBeanV3;
import com.sxcapp.www.Share.Bean.OrderListInfoBeanV3;
import com.sxcapp.www.Share.Bean.RPTokenV3;
import com.sxcapp.www.Share.Bean.RepairRemarkInfoBeanV3;
import com.sxcapp.www.Share.Bean.SelectGStoreBeanV3;
import com.sxcapp.www.Share.Bean.StoreDetailBeanV3;
import com.sxcapp.www.Share.Bean.TTLottoTypeBean;
import com.sxcapp.www.Share.Bean.UnLockCarIsPhoto;
import com.sxcapp.www.UserCenter.Bean.OrderNoBean;
import com.sxcapp.www.Util.Properties;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 共享租车相关接口
 * Created by wenleitao on 2017/7/31.
 */

public interface ShareService {


    @POST(Properties.checkIsHaveShareOrderV3)
    Observable<CodeResultV3<ExistOrderBeanV3>> checkIsHaveShareOrderV3(
            @Query("customer_id") String user_id
    );


    @POST(Properties.problemFeedbackV3)
    Observable<CodeResultV3<Object>> problemFeedbackV3(
            @Query("user_id") String userId,
            @Query("license_plate_number") String license_plate_number,
            @Query("problem_explains") String problem_explains,
            @Query("problem_image") String problem_image,
            @Query("problem_image_oss") String problem_image_oss,
            @Query("problem_type_id") String problem_type_id
    );

    @POST(Properties.getShareStoreListNew)
    Observable<Result<MapStoreListBean>> getShareStoreListNew(
    );


    @POST(Properties.getLuckyStoreList)
    Observable<Result<TTLottoTypeBean>> getTTLottoType();

    @POST(Properties.getElecMapStoreListV3)
    Observable<CodeResultV3<ElectricMapStoreListBeanV3>> getElecMapStoreListV3();

    @POST(Properties.getElecInDayMapStoreListV3)
    Observable<CodeResultV3<ElectricMapStoreListBeanV3>> getElecInDayMapStoreListV3();

    @POST(Properties.getStoreDetailV3)
    Observable<CodeResultV3<StoreDetailBeanV3>> getStoreDetailV3(
            @Query("store_id") String store_id
    );

    @POST(Properties.getReturnStoreListV3)
    Observable<CodeResultV3<SelectGStoreBeanV3>> getReturnStoreListV3(
            @Query("fetch_store") String fetch_store_id

    );

    @POST(Properties.getElecInDayReturnStoreListV3)
    Observable<CodeResultV3<SelectGStoreBeanV3>> getElecInDayReturnStoreListV3(
            @Query("fetch_store") String fetch_store_id

    );


    @POST(Properties.getCarList_elec_shortV3)
    Observable<CodeResultV3<ElecShortAppointBeanV3>> getCarList_elec_shortV3(
            @Query("fetch_store") String fetch_store_id,
            @Query("return_store") String return_store
    );

    @POST(Properties.elecShort_appointV3)
    Observable<CodeResultV3<ElecShortAppointReturnBeanV3>> elecShort_appointV3(
            @Query("customer_id") String customer_id,
            @Query("fetch_store") String fetch_store,
            @Query("return_store") String return_store,
            @Query("source_id") String source_id,
            @Query("no_deductible") int no_deductible
    );

    @POST(Properties.getBeginUserInfoV3)
    Observable<CodeResultV3<ElecShortBeginUseCarBeanV3>> getBeginUserInfoV3(
            @Query("order_no") String order_no
    );
//    customer_id（用户ID），setting_type（1电车分时，2电车天天，3油车分时，4油车长租，5豪车，无法确定传0），setting_id（规则ID，无法确定传0），no_deductibe（不计免赔，0不勾选1勾选），source_id（车源ID，无法确定传0），coupon_id（优惠券ID，没有传0）

    @POST(Properties.commonDoCheckUser)
    Observable<CodeResultV3<Object>> commonDoCheckUser(
            @Query("customer_id") String customer_id,
            @Query("setting_type") int rent_type,
            @Query("setting_id") String setting_id,
            @Query("no_deductibe") int no_deductibe,
            @Query("source_id") String source_id,
            @Query("coupon_id") String coupon_id
    );

    @POST(Properties.elecShortCancelOrder)
    Observable<CodeResultV3<Object>> elecShortCancelOrder(
            @Query("order_no") String order_no
    );

    @POST(Properties.getCarBlowV3)
    Observable<CodeResultV3<Object>> getCarBlowV3(
            @Query("order_no") String order_no
    );

    @POST(Properties.geCarUnLockV3)
    Observable<CodeResultV3<UnLockCarIsPhoto>> getCarUnLockV3(
            @Query("order_no") String order_no,
            @Query("latitude") String latitude,
            @Query("longitude") String longitude
    );

    @POST(Properties.orderAuditImageV3)
    Observable<CodeResultV3<Object>> orderAuditImageV3(
            @Query("customer_id") String customer_id,
            @Query("order_no") String order_no,
            @Query("source_id") String source_id,
            @Query("right_image") String right_image,
            @Query("left_image") String left_image,
            @Query("driverseat_image") String driverseat_image,
            @Query("behind_image") String behind_image,
            @Query("right_image_view") String right_image_view,
            @Query("left_image_view") String left_image_view,
            @Query("driverseat_image_view") String driverseat_image_view,
            @Query("behind_image_view") String behind_image_view,
            @Query("broken_image") String broken_image,
            @Query("broken_image_view") String broken_image_view,
            @Query("type") int type,
            @Query("order_type") int order_type

    );

    @POST(Properties.getElecShorePreCostV3)
    Observable<CodeResultV3<ElecShortPreCostBeanV3>> getElecShorePreCostV3(
            @Query("order_no") String order_no
    );

    @POST(Properties.elecShortEnduse)
    Observable<CodeResultV3<UnLockCarIsPhoto>> elecShortEnduse(
            @Query("order_no") String order_no,
            @Query("latitude") String latitude,
            @Query("longitude") String longitude
    );

    @POST(Properties.shortUnlockV3)
    Observable<CodeResultV3<Object>> shortUnlockV3(
            @Query("order_no") String order_no,
            @Query("latitude") String latitude,
            @Query("longitude") String longitude

    );

    @POST(Properties.shortLockV3)
    Observable<CodeResultV3<Object>> shortLockV3(
            @Query("order_no") String order_no,
            @Query("latitude") String latitude,
            @Query("longitude") String longitude
    );

    @POST(Properties.getElecShortPayInfoV3)
    Observable<CodeResultV3<ElecShortPayInfoBeanV3>> getElecShortPayInfoV3(
            @Query("order_no") String order_no

    );

    @POST(Properties.elecShortPayV3)
    Observable<CodeResultV3<Object>> elecShortPayV3(
            @Query("order_no") String order_no,
            @Query("coupon_id") String coupon_id
    );

    @POST(Properties.getPayCouponListV3)
    Observable<CodeResultV3<List<OrderCouponBeanV3>>> getPayCouponListV3(
            @Query("order_no") String order_no
    );

    @POST(Properties.getChangeReturnStoreListV3)
    Observable<CodeResultV3<Object>> getChangeReturnStoreListV3(
            @Query("order_no") String order_no,
            @Query("order_type") int order_type,
            @Query("change_store") String change_store
    );

    @POST(Properties.getElecInDayCarListV3)
    Observable<CodeResultV3<ElecShareInDayRentCarListBeanV3>> getElecInDayCarListV3(
            @Query("fetch_store") String fetch_store,
            @Query("return_store") String return_store,
            @Query("customer_id") String customer_id
    );

    @POST(Properties.getUsefulCouponListByDayTypeV3)
    Observable<CodeResultV3<List<OrderCouponBeanV3>>> getUsefulCouponListByDayTypeV3(
            @Query("customer_id") String customer_id,
            @Query("setting_id") String setting_id
    );

    @POST(Properties.getElecIndayOrderCouponSizeV3)
    Observable<CodeResultV3<OrderCouponSizeBeanV3>> getElecIndayOrderCouponSizeV3(
            @Query("customer_id") String customer_id,
            @Query("setting_id") String setting_id
    );

    @POST(Properties.elecInDayRentCarV3)
    Observable<CodeResultV3<ElecInDayAppointReturnBeanV3>> elecInDayRentCarV3(
            @Query("customer_id") String customer_id,
            @Query("fetch_store") String fetch_store,
            @Query("return_store") String return_store,
            @Query("source_id") String source_id,
            @Query("no_deductible") int no_deductible,
            @Query("setting_id") String setting_id,
            @Query("coupon_id") String coupon_id,
            @Query("latitude") String latitude,
            @Query("longitude") String longitude


    );

    @POST(Properties.elecInDayUnLockCarV3)
    Observable<CodeResultV3<UnLockCarIsPhoto>> elecInDayUnLockCarV3(
            @Query("order_no") String order_no,
            @Query("latitude") String latitude,
            @Query("longitude") String longitude
    );

    @POST(Properties.getElecInDayEndInfoV3)
    Observable<CodeResultV3<ElecInDayEndInfoBeanV3>> getElecInDayEndInfoV3(
            @Query("order_no") String order_no
    );

    @POST(Properties.elecInDayEndCarV3)
    Observable<CodeResultV3<UnLockCarIsPhoto>> elecInDayEndCarV3(
            @Query("order_no") String order_no
    );

    @POST(Properties.getOilShortMapStoreListV3)
    Observable<CodeResultV3<OilMapStoreListBeanV3>> getOilShortMapStoreListV3();

    @POST(Properties.getOilReturnStoreListV3)
    Observable<CodeResultV3<SelectGStoreBeanV3>> getOilReturnStoreListV3(
            @Query("fetch_store") String fetch_store_id
    );

    @POST(Properties.getOilInDayReturnStoreListV3)
    Observable<CodeResultV3<SelectGStoreBeanV3>> getOilInDayReturnStoreListV3(
            @Query("fetch_store") String fetch_store_id
    );


    @POST(Properties.getCarList_oil_shortV3)
    Observable<CodeResultV3<OilShortAppointBeanV3>> getCarList_oil_shortV3(
            @Query("fetch_store") String fetch_store_id,
            @Query("return_store") String return_store
    );

    @POST(Properties.oilShort_appointV3)
    Observable<CodeResultV3<OilShortAppointReturnBeanV3>> oilShort_appointV3(
            @Query("customer_id") String customer_id,
            @Query("fetch_store") String fetch_store,
            @Query("return_store") String return_store,
            @Query("source_id") String source_id,
            @Query("no_deductible") int no_deductible
    );

    @POST(Properties.getOilBeginUserInfoV3)
    Observable<CodeResultV3<OilShortBeginUseCarBeanV3>> getOilBeginUserInfoV3(
            @Query("order_no") String order_no
    );

    @POST(Properties.getOilCarUnLockV3)
    Observable<CodeResultV3<UnLockCarIsPhoto>> getOilCarUnLockV3(
            @Query("order_no") String order_no,
            @Query("latitude") String latitude,
            @Query("longitude") String longitude
    );

    @POST(Properties.oilShortCancelOrderV3)
    Observable<CodeResultV3<Object>> oilShortCancelOrderV3(
            @Query("order_no") String order_no
    );

    @POST(Properties.oilShortEndUseV3)
    Observable<CodeResultV3<UnLockCarIsPhoto>> oilShortEndUseV3(
            @Query("order_no") String order_no,
            @Query("latitude") String latitude,
            @Query("longitude") String longitude
    );

    @POST(Properties.getOilShorePreCostV3)
    Observable<CodeResultV3<OilShortPreCostBeanV3>> getOilShorePreCostV3(
            @Query("order_no") String order_no
    );

    @POST(Properties.uploadOilMassImageV3)
    Observable<CodeResultV3<Object>> uploadOilMassImageV3(
            @Query("customer_id") String customer_id,
            @Query("source_id") String source_id,
            @Query("order_no") String order_no,
            @Query("dashboard_image") String dashboard_image,
            @Query("dashboard_image_view") String dashboard_image_view
    );

    @POST(Properties.getOilShortPayInfoV3)
    Observable<CodeResultV3<OilShortPayInfoBeanV3>> getOilShortPayInfoV3(
            @Query("order_no") String order_no

    );

    @POST(Properties.getOilShareShortPayCouponListV3)
    Observable<CodeResultV3<List<OrderCouponBeanV3>>> getOilShareShortPayCouponListV3(
            @Query("order_no") String order_no
    );

    @POST(Properties.oilShortPayV3)
    Observable<CodeResultV3<Object>> oilShortPayV3(
            @Query("order_no") String order_no,
            @Query("coupon_id") String coupon_id
    );

    @POST(Properties.getOilInDayMapStoreListV3)
    Observable<CodeResultV3<ElectricMapStoreListBeanV3>> getOilInDayMapStoreListV3();


    @POST(Properties.getOilInDayCarListV3)
    Observable<CodeResultV3<OilShareInDayRentCarListBeanV3>> getOilInDayCarListV3(
            @Query("fetch_store") String fetch_store,
            @Query("return_store") String return_store,
            @Query("customer_id") String customer_id
    );

    @POST(Properties.oilInDayRentCarV3)
    Observable<CodeResultV3<OilInDayAppointReturnBeanV3>> oilInDayRentCarV3(
            @Query("customer_id") String customer_id,
            @Query("fetch_store") String fetch_store,
            @Query("return_store") String return_store,
            @Query("source_id") String source_id,
            @Query("no_deductible") int no_deductible,
            @Query("setting_id") String setting_id,
            @Query("coupon_id") String coupon_id,
            @Query("latitude") String latitude,
            @Query("longitude") String longitude
    );

    @POST(Properties.OilInDayUnLockCarV3)
    Observable<CodeResultV3<UnLockCarIsPhoto>> OilInDayUnLockCarV3(
            @Query("order_no") String order_no,
            @Query("latitude") String latitude,
            @Query("longitude") String longitude
    );

    @POST(Properties.getOilIndayOrderCouponSizeV3)
    Observable<CodeResultV3<OrderCouponSizeBeanV3>> getOilIndayOrderCouponSizeV3(
            @Query("customer_id") String customer_id,
            @Query("setting_id") String setting_id
    );

    @POST(Properties.getOilShareIndayPayCouponListV3)
    Observable<CodeResultV3<List<OrderCouponBeanV3>>> getOilShareIndayPayCouponListV3(
            @Query("customer_id") String customer_id,
            @Query("setting_id") String setting_id
    );

    @POST(Properties.getOilInDayEndInfoV3)
    Observable<CodeResultV3<OilInDayEndInfoBeanV3>> getOilInDayEndInfoV3(
            @Query("order_no") String order_no
    );

    @POST(Properties.oilInDayEndCarV3)
    Observable<CodeResultV3<UnLockCarIsPhoto>> oilInDayEndCarV3(
            @Query("order_no") String order_no
    );

    @POST(Properties.getLuxuryMapStoreListV3)
    Observable<CodeResultV3<LuxuryMapStoreListBeanV3>> getLuxuryMapStoreListV3();

    @POST(Properties.getLuxuryCarListByStoreV3)
    Observable<CodeResultV3<LuxuryCarListByStoreBeanV3>> getLuxuryCarListByStoreV3(
            @Query("customer_id") String customer_id,
            @Query("fetch_store") String fetch_store_id,
            @Query("return_store") String return_store_id
    );

    @POST(Properties.getLuxuryAppointCarInfoV3)
    Observable<CodeResultV3<LuxuryAppointInfoBeanV3>> getLuxuryAppointCarInfoV3(
            @Query("source_id") String source_id,
            @Query("fetch_store") String fetch_store,
            @Query("return_store") String return_store
    );

    @POST(Properties.luxuryAppointV3)
    Observable<CodeResultV3<LuxuryAppointReturnBeanV3>> luxuryAppointV3(
            @Query("customer_id") String user_id,
            @Query("fetch_store") String fetch_id,
            @Query("return_store") String return_store,
            @Query("source_id") String source_id,
            @Query("latitude") String latitude,
            @Query("longitude") String longitude
    );

    @POST(Properties.getLuxuryPreAuditInfoV3)
    Observable<CodeResultV3<LuxuryPreAuditInfoBeanV3>> getLuxuryPreAuditInfoV3(
            @Query("order_no") String order_no
    );

    @POST(Properties.uploadCreditReportImageV3)
    Observable<CodeResultV3<Object>> uploadCreditReportImageV3(
            @Query("customer_id") String customer_id,
            @Query("credit_image") String credit_image,
            @Query("credit_image_view") String credit_image_view
    );

    @POST(Properties.getPriAuditInfoV3)
    Observable<CodeResultV3<Object>> getPriAuditInfoV3(
            @Query("customer_id") String customer_id,
            @Query("order_no") String order_no
    );

    @POST(Properties.uploadLuxuyInfoV3)
    Observable<CodeResultV3<Object>> uploadLuxuyInfoV3(
            @QueryMap() Map<String, String> map
    );

    @POST(Properties.getLuxuryConfirmDayTypeCarInfoV3)
    Observable<CodeResultV3<ConfirmDayTypeCarInfoBeanV3>> getLuxuryConfirmDayTypeCarInfoV3(
            @Query("order_no") String order_no
    );

    @POST(Properties.luxuryCheckGuarantorV3)
    Observable<CodeResultV3<Object>> luxuryCheckGuarantorV3(
            @Query("order_no") String order_no
    );

    @POST(Properties.confirmLuxuryDayTypeV3)
    Observable<CodeResultV3<Object>> confirmLuxuryDayTypeV3(
            @Query("order_no") String order_no,
            @Query("rent_days_id") String rent_days_id
    );

    @POST(Properties.getLuxuryRentCarInfoV3)
    Observable<CodeResultV3<LuxuryRentCarInfoBeanV3>> getLuxuryRentCarInfoV3(
            @Query("order_no") String order_no
    );

    @POST(Properties.getLuxuryPayCouponListV3)
    Observable<CodeResultV3<List<OrderCouponBeanV3>>> getLuxuryPayCouponListV3(
            @Query("order_no") String order_no
    );

    @POST(Properties.luxuryRentCarV3)
    Observable<CodeResultV3<UnLockCarIsPhoto>> luxuryRentCarV3(
            @Query("order_no") String order_no,
            @Query("no_deductible") int no_deductible,
            @Query("coupon_id") String coupon_id,
            @Query("latitude") String latitude,
            @Query("longitude") String longitude
    );

    @POST(Properties.getLuxuryReturnCarInfoV3)
    Observable<CodeResultV3<LuxuryReturnCarInfoBeanV3>> getLuxuryReturnCarInfoV3(
            @Query("order_no") String order_no
    );

    @POST(Properties.luxuryReturnCarV3)
    Observable<CodeResultV3<UnLockCarIsPhoto>> luxuryReturnCarV3(
            @Query("order_no") String order_no,
            @Query("latitude") String latitude,
            @Query("longitude") String longitude
    );

    @POST(Properties.getElecShortOrderListV3)
    Observable<CodeResultV3<OrderListInfoBeanV3>> getElecShortOrderListV3(
            @Query("customer_id") String customer_id,
            @Query("page_index") int pageIndex
    );

    @POST(Properties.getElecInDayOrderListV3)
    Observable<CodeResultV3<OrderListInfoBeanV3>> getElecInDayOrderListV3(
            @Query("customer_id") String customer_id,
            @Query("page_index") int pageIndex
    );

    @POST(Properties.getOilShortOrderListV3)
    Observable<CodeResultV3<OrderListInfoBeanV3>> getOilShortOrderListV3(
            @Query("customer_id") String customer_id,
            @Query("page_index") int pageIndex
    );

    @POST(Properties.getOilInDayOrderListV3)
    Observable<CodeResultV3<OrderListInfoBeanV3>> getOilInDayOrderListV3(
            @Query("customer_id") String customer_id,
            @Query("page_index") int pageIndex
    );

    @POST(Properties.getLuxuryOrderListV3)
    Observable<CodeResultV3<OrderListInfoBeanV3>> getLuxuryOrderListV3(
            @Query("customer_id") String customer_id,
            @Query("page_index") int pageIndex
    );

    @POST(Properties.getElecShortOffOrderDetailV3)
    Observable<CodeResultV3<OffOrderDetailBeanV3>> getElecShortOffOrderDetailV3(
            @Query("order_no") String order_no
    );

    @POST(Properties.getElecInDayOffOrderDetailV3)
    Observable<CodeResultV3<OffOrderDetailBeanV3>> getElecInDayOffOrderDetailV3(
            @Query("order_no") String order_no
    );

    @POST(Properties.getOilShortOffOrderDetailV3)
    Observable<CodeResultV3<OffOrderDetailBeanV3>> getOilShortOffOrderDetailV3(
            @Query("order_no") String order_no
    );

    @POST(Properties.getOilInDayOffOrderDetailV3)
    Observable<CodeResultV3<OffOrderDetailBeanV3>> getOilInDayOffOrderDetailV3(
            @Query("order_no") String order_no
    );

    @POST(Properties.getLuxuryOffOrderDetailV3)
    Observable<CodeResultV3<LuxuryOffOrderDetailBeanV3>> getLuxuryOffOrderDetailV3(
            @Query("order_no") String order_no
    );

    @POST(Properties.getRepairRemarkV3)
    Observable<CodeResultV3<RepairRemarkInfoBeanV3>> getRepairRemarkV3(
            @Query("customer_id") String customer_id,
            @Query("order_no") String order_no,
            @Query("add_type") int add_type,
            @Query("add_cost") String add_cost
    );

    @POST(Properties.getRepairOrderNoV3)
    Observable<CodeResultV3<OrderNoBean>> getRepairOrderNoV3(
            @Query("customer_id") String customer_id,
            @Query("order_no") String order_no,
            @Query("add_type") int add_type,
            @Query("add_cost") String add_cost,
            @Query("pay_channel") int pay_channel
    );

    @POST(Properties.getRPTokenV3)
    Observable<CodeResultV3<RPTokenV3>> getRPTokenV3(
            @Query("customer_id") String customer_id
    );
}

package com.sxcapp.www.UserCenter.HttpService;

import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.Bean.CodeResult;
import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.Lease.Bean.PrepayIdBean;
import com.sxcapp.www.Lease.Bean.PrepayResult;
import com.sxcapp.www.Share.Bean.TTCouponListBean;
import com.sxcapp.www.UserCenter.Bean.AliSign;
import com.sxcapp.www.UserCenter.Bean.AliSignV3;
import com.sxcapp.www.UserCenter.Bean.BalanceInfoBeanV3;
import com.sxcapp.www.UserCenter.Bean.CouponBean;
import com.sxcapp.www.UserCenter.Bean.CouponRemarkBean;
import com.sxcapp.www.UserCenter.Bean.CreditAuthenStateBeanV3;
import com.sxcapp.www.UserCenter.Bean.EventCountBeanV3;
import com.sxcapp.www.UserCenter.Bean.EventListBean;
import com.sxcapp.www.UserCenter.Bean.EventListByTypeBeanV3;
import com.sxcapp.www.UserCenter.Bean.InDayEndOrderBean;
import com.sxcapp.www.UserCenter.Bean.InviteInfo;
import com.sxcapp.www.UserCenter.Bean.IsHaveActivity;
import com.sxcapp.www.UserCenter.Bean.LeaseDepositInfoBeanV3;
import com.sxcapp.www.UserCenter.Bean.LotteryResultBean;
import com.sxcapp.www.UserCenter.Bean.LotteryRuleBean;
import com.sxcapp.www.UserCenter.Bean.LuckyStoreBean;
import com.sxcapp.www.UserCenter.Bean.MessageInfo;
import com.sxcapp.www.UserCenter.Bean.MyCouponCountBean;
import com.sxcapp.www.UserCenter.Bean.MyCouponListBeanv3;
import com.sxcapp.www.UserCenter.Bean.NewCouponBean;
import com.sxcapp.www.UserCenter.Bean.OilInDayOrderDetail;
import com.sxcapp.www.UserCenter.Bean.OilShareInDayInfo;
import com.sxcapp.www.UserCenter.Bean.OneOrderDetailBean;
import com.sxcapp.www.UserCenter.Bean.OrderInfo;
import com.sxcapp.www.UserCenter.Bean.OrderNoBean;
import com.sxcapp.www.UserCenter.Bean.RemarkLabelV3;
import com.sxcapp.www.UserCenter.Bean.ShareCarInfo;
import com.sxcapp.www.UserCenter.Bean.ShareDepositInfo;
import com.sxcapp.www.UserCenter.Bean.ShareDepositInfoBeanV3;
import com.sxcapp.www.UserCenter.Bean.ShareDepositWithdrawInfoBeanV3;
import com.sxcapp.www.UserCenter.Bean.ShareDepositRechargeListBean;
import com.sxcapp.www.UserCenter.Bean.ShareInDayInfo;
import com.sxcapp.www.UserCenter.Bean.ShareInDayOrderDetailBean;
import com.sxcapp.www.UserCenter.Bean.SnapShotDetailBeanV3;
import com.sxcapp.www.UserCenter.Bean.UserCodeResult;
import com.sxcapp.www.UserCenter.Bean.UserInfo;
import com.sxcapp.www.UserCenter.Bean.UserInfoBeanV3;
import com.sxcapp.www.UserCenter.Bean.WXPrepayInfoBeanV3;
import com.sxcapp.www.UserCenter.Bean.WalletInfo;
import com.sxcapp.www.UserCenter.Bean.WalletInfoBeanV3;
import com.sxcapp.www.Util.Properties;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by wenleitao on 2017/7/26.
 */

public interface UserCenterService {
    @POST(Properties.getUserInfo)
    Observable<Result<UserInfo>> getUserInfo(
            @Query("userId") String user_id
    );

    @POST(Properties.getOrderList)
    Observable<Result<OrderInfo>> getOrderList(
            @Query("userId") String user_id,
            @Query("pageIndex") int pageIndex,
            @Query("pageSize") int pageSize
    );

    @POST(Properties.getShareOrderList)
    Observable<Result<ShareCarInfo>> getShareOrderList(
            @Query("customer_id") String user_id,
            @Query("pageIndex") int pageIndex,
            @Query("pageSize") int pageSize
    );

    @POST(Properties.cancelOrder)
    Observable<Result<Object>> cancelOrder(
            @Query("id") String order_no
    );

    @POST(Properties.doauthentication)
    Observable<Result<Object>> doauthentication(
            @QueryMap() Map<String, String> map
    );

    @POST(Properties.getWxPrepayId)
    Observable<PrepayResult<PrepayIdBean>> getPrepayId(
            @Query("out_trade_no") String order_no,
            @Query("money") String money,
            @Query("subject") String subject,
            @Query("type") String type
    );


    @POST(Properties.getWxPrepayIdV3)
    Observable<CodeResultV3<WXPrepayInfoBeanV3>> getWXPrepayIdV3(
            @Query("order_no") String order_no,
            @Query("amount") String money,
            @Query("subject") String subject,
            @Query("type") String type
    );

    @POST(Properties.getRechargeNo)
    Observable<Result<OrderNoBean>> getOrderNo(
            @Query("userId") String user_id,
            @Query("amount") String amount,
            @Query("payType") int payType
    );

    @POST(Properties.getBalanceRechargeOrderNoV3)
    Observable<CodeResultV3<OrderNoBean>> getBalanceRechargeOrderNoV3(
            @Query("customer_id") String user_id,
            @Query("amount") String amount,
            @Query(" pay_channel") int payType
    );

    @POST(Properties.getShareRechargeOrderNoV3)
    Observable<CodeResultV3<OrderNoBean>> getShareRechargeOrderNoV3(
            @Query("customer_id") String user_id,
            @Query("amount") String amount,
            @Query("channel") int payType
    );

    @POST(Properties.doAuthenCheck)
    Observable<UserCodeResult<Object>> doCheckUser(
            @Query("customer_id") String user_id
    );

    @POST(Properties.balanceWithDraw)
    Observable<Result<Object>> balanceWithdraw(
            @QueryMap() Map<String, String> map
    );

    @POST(Properties.balanceWithDrawV3)
    Observable<CodeResultV3<Object>> balanceWithdrawV3(
            @QueryMap() Map<String, String> map
    );

    @POST(Properties.shareWithDrawV3)
    Observable<CodeResultV3<Object>> shareWithDrawV3(
            @QueryMap() Map<String, String> map
    );

    @POST(Properties.leaseWithDrawV3)
    Observable<CodeResultV3<Object>> leaseWithDrawV3(
            @QueryMap() Map<String, String> map
    );

    @POST(Properties.editUser)
    Observable<Result<Object>> editUser(
            @Query("userId") String user_id,
            @Query("name") String name
    );

    @POST(Properties.getUserBalanceDeposit)
    Observable<Result<WalletInfo>> getUserBalanceDeposit(
            @Query("customer_id") String user_id
    );

    @POST(Properties.createShareDepositNo)
    Observable<Result<OrderNoBean>> creatShareDepositNo(
            @Query("customer_id") String user_id,
            @Query("total_cost") String total_cost,
            @Query("pay_type") int type
    );

    @POST(Properties.getNeedShareDeposit)
    Observable<Result<ShareDepositInfo>> getNeedShareDeposit(
            @Query("customer_id") String user_id
    );

    @POST(Properties.getInviteData)
    Observable<Result<InviteInfo>> getInviteData(
    );

    @POST(Properties.getMessage)
    Observable<Result<MessageInfo>> getMessage(
            @QueryMap() Map<String, String> map
    );

    @POST(Properties.checkCanWithdraw)
    Observable<Result<Object>> checkCanWithdraw(
            @Query("customer_id") String customer_id,
            @Query("type") int type
    );

    @POST(Properties.openMyOrderDetail)
    Observable<Result<OneOrderDetailBean>> openMyOrderDetail(
            @Query("id") String order_id
    );

    @POST(Properties.getShareInDayOrderList)
    Observable<Result<ShareInDayInfo>> getShareInDayOrderList(
            @Query("customer_id") String customer_id,
            @Query("pageIndex") int pageIndex
    );

    @POST(Properties.getShareOilCarLongOrderList)
    Observable<Result<OilShareInDayInfo>> getShareOilCarLongOrderList(
            @Query("customer_id") String customer_id,
            @Query("pageIndex") int pageIndex
    );

    @POST(Properties.getInDayOrderDetail)
    Observable<Result<ShareInDayOrderDetailBean>> getInDayOrderDetail(
            @Query("order_no") String order_no
    );

    @POST(Properties.getShareOilCarLongOrderDetail)
    Observable<Result<OilInDayOrderDetail>> getShareOilCarLongOrderDetail(
            @Query("order_no") String order_no
    );

    @POST(Properties.endIndayOrder)
    Observable<Result<InDayEndOrderBean>> endInDayOrder(
            @Query("order_no") String order_no,
            @Query("longitude") String longitude,
            @Query("latitude") String latitude,
            @Query("device_type") int device_type
    );

    @POST(Properties.doOilCarLongEndAppoint)
    Observable<Result<InDayEndOrderBean>> doOilCarLongEndAppoint(
            @Query("order_no") String order_no,
            @Query("longitude") String longitude,
            @Query("latitude") String latitude,
            @Query("device_type") int device_type
    );

    @POST(Properties.unLockCar)
    Observable<Result<Object>> unLockCar(
            @Query("order_no") String order_no,
            @Query("latitude") String latitude,
            @Query("longitude") String longitude,
            @Query("device_type") int device_type
    );

    @POST(Properties.lockCar)
    Observable<Result<Object>> LockCar(
            @Query("order_no") String order_no,
            @Query("latitude") String latitude,
            @Query("longitude") String longitude,
            @Query("device_type") int device_type
    );

    @POST(Properties.getAliSign)
    Observable<Result<AliSign>> getALiSign(
            @Query("out_trade_no") String out_trade_no,
            @Query("money") String money,
            @Query("subject") String subject,
            @Query("type") String type
    );

    @POST(Properties.getAliSignV3)
    Observable<CodeResultV3<AliSignV3>> getALiSignV3(
            @Query("order_no") String out_trade_no,
            @Query("amount") String money,
            @Query("subject") String subject,
            @Query("type") String type
    );

    @POST(Properties.inDayUnLockCar)
    Observable<Result<Object>> inDayUnLockCar(
            @Query("order_no") String order_no,
            @Query("latitude") String latitude,
            @Query("longitude") String longitude,
            @Query("source_id") String source_id,
            @Query("device_type") int device_type
    );

    @POST(Properties.doOilLongUse)
    Observable<Result<Object>> doOilLongUse(
            @Query("order_no") String order_no,
            @Query("source_id") String source_id
    );

    @POST(Properties.getActivityList)
    Observable<Result<EventListBean>> getActivityList();

    @POST(Properties.isHaveActivity)
    Observable<Result<IsHaveActivity>> isHaveActivity();

    @POST(Properties.getLuckyStoreList)
    Observable<Result<LuckyStoreBean>> getLuckyStoreList();

    @POST(Properties.getCustomerCoupon)
    Observable<Result<MyCouponCountBean>> getCustomerCoupon(
            @Query("customer_id") String customer_id,
            @Query("store_id") String store_id
    );

    @POST(Properties.uploadLuckyResult)
    Observable<Result<Object>> uploadLuckyResult(
            @Query("customer_id") String customer_id,
            @Query("coupon_id") String coupon_id,
            @Query("store_id") String store_id
    );

    @POST(Properties.getMyCouponList)
    Observable<Result<List<CouponBean>>> getMyCouponList(
            @Query("customer_id") String customer_id
    );

    @POST(Properties.getMyCouponList)
    Observable<Result<List<NewCouponBean>>> getNewMyCouponList(
            @Query("customer_id") String customer_id
    );


    @POST(Properties.getCouponRemark)
    Observable<Result<CouponRemarkBean>> getCouponRemark(
            @Query("store_id") String store_id
    );

    @POST(Properties.getCouponRemark)
    Observable<Result<TTCouponListBean>> getTTCouponList(
            @Query("store_id") String store_id,
            @Query("type") String type_id
    );

    @POST(Properties.getLotteryRule)
    Observable<Result<LotteryRuleBean>> getLotteryRule(
    );

    @POST(Properties.oilLongOrdercheckPosition)
    Observable<Result<Object>> oilLongOrdercheckPosition(
            @Query("customer_id") String customer_id,
            @Query("order_no") String order_no,
            @Query("source_id") String source_id,
            @Query("latitude") String latitude,
            @Query("longitude") String longitude,
            @Query("device_type") int device_type
    );

    @POST(Properties.doCarBlow)
    Observable<Result<Object>> doCarBlow(
            @Query("order_no") String order_no,
            @Query("source_id") String source_id
    );

    @POST(Properties.getLotteryResult)
    Observable<Result<LotteryResultBean>> getLotteryResult(
            @Query("customer_id") String customer_id
    );

    @POST(Properties.getWalletInfoV3)
    Observable<CodeResultV3<WalletInfoBeanV3>> getWalletInfoV3(
            @Query("customer_id") String customer_id
    );

    @POST(Properties.getBalanceInfoV3)
    Observable<CodeResultV3<BalanceInfoBeanV3>> getBalanceInfoV3(
            @Query("customer_id") String customer_id
    );

    @POST(Properties.getShareDepositInfoV3)
    Observable<CodeResultV3<ShareDepositInfoBeanV3>> getShareDepositInfoV3(
            @Query("customer_id") String customer_id
    );

    @POST(Properties.getLeaseDepositInfoV3)
    Observable<CodeResultV3<LeaseDepositInfoBeanV3>> getLeaseDepositInfoV3(
            @Query("customer_id") String customer_id
    );

    @POST(Properties.getShareDepositWithdrawInfoV3)
    Observable<CodeResultV3<ShareDepositWithdrawInfoBeanV3>> getShareDepositWithdrawInfoV3(
            @Query("customer_id") String customer_id
    );

    @POST(Properties.getLeaseDepositWithdrawInfoV3)
    Observable<CodeResultV3<ShareDepositWithdrawInfoBeanV3>> getLeaseDepositWithdrawInfoV3(
            @Query("customer_id") String customer_id
    );

    @POST(Properties.getMyCouponV3)
    Observable<CodeResultV3<MyCouponListBeanv3>> getMyCouponV3(
            @Query("customer_id") String customer_id
    );

    @POST(Properties.getShareDepositRechargeCountListV3)
    Observable<CodeResultV3<ShareDepositRechargeListBean>> getShareDepositRechargeCountListV3(
            @Query("customer_id") String customer_id
    );

    @POST(Properties.signTestV3)
    Observable<CodeResultV3<Object>> signTestV3(
            @Query("nonce_str") String nonce_str,
            @Query("sign") String sigh
    );

    @POST(Properties.getCreditAuthenStateV3)
    Observable<CodeResultV3<CreditAuthenStateBeanV3>> getCreditAuthenStateV3(
            @Query("customer_id") String customer_id
    );

    @POST(Properties.creditCardAuditV3)
    Observable<CodeResultV3<Object>> creditCardAuditV3(
            @Query("customer_id") String customer_id,
            @Query("bank_card") String bank_card,
            @Query("bank_card_image") String bank_card_image,
            @Query("bank_card_image_view") String bank_card_image_view

    );

    @POST(Properties.getUserInfoV3)
    Observable<CodeResultV3<UserInfoBeanV3>> getUserInfoV3(
            @Query("customer_id") String customer_id
    );

    @POST(Properties.doauthenticationV3)
    Observable<CodeResultV3<Object>> doauthenticationV3(
            @QueryMap() Map<String, String> map
    );

    @POST(Properties.doAuthenCheckV3)
    Observable<CodeResultV3<Object>> doAuthenCheckV3(
            @Query("customer_id") String user_id
    );

    @POST(Properties.editUserV3)
    Observable<CodeResultV3<Object>> editUserV3(
            @Query("customer_id") String user_id,
            @Query("nick_name") String name,
            @Query("head_image") String head_image,
            @Query("head_image_view") String head_image_view
    );

    @POST(Properties.getEventCountV3)
    Observable<CodeResultV3<EventCountBeanV3>> getEventCountV3(

    );

    @POST(Properties.getEventListByTypeV3)
    Observable<CodeResultV3<EventListByTypeBeanV3>> getEventListByTypeV3(
            @Query("type") int type
    );

    @POST(Properties.isHaveActivityV3)
    Observable<CodeResultV3<List<String>>> isHaveActivityV3(
    );

    @POST(Properties.getSnapShotDetailV3)
    Observable<CodeResultV3<SnapShotDetailBeanV3>> getSnapShotDetailV3(
            @Query("theme_id") String theme_id,
            @Query("customer_id") String customer_id
    );

    @POST(Properties.snapshotUploadInfoV3)
    Observable<CodeResultV3<Object>> snapshotUploadInfoV3(
            @QueryMap() Map<String, String> map
    );

    @POST(Properties.uploadRecommendInfoV3)
    Observable<CodeResultV3<Object>> uploadRecommendInfoV3(
            @QueryMap() Map<String, String> map
    );

    @POST(Properties.getRemarkLabelV3)
    Observable<CodeResultV3<RemarkLabelV3>> getRemarkLabelV3(
            @Query("order_no") String order_no,
            @Query("order_type") int order_type
    );

    @POST(Properties.remarkOrderV3)
    Observable<CodeResultV3<Object>> remarkOrderV3(
            @Query("order_no") String order_no,
            @Query("order_type") int order_type,
            @Query("evaluate_star") int evaluate_star,
            @Query("service_star") int service_star,
            @Query("label") String label,
            @Query("feedback") String feedback,
            @Query("complaint") String complaint,
            @Query("customer_id") String customer_id
    );

}

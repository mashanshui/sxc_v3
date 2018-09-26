package com.sxcapp.www.Util;

/**
 * 接口列表
 * Created by wenleitao on 2017/7/6.
 */

public class Properties {
    //测试服
//    public static final String baseUrl = "http://106.14.98.148:9090/sxc_admin/";
    //    本地服务器
//    public static final String baseUrl = "http://192.168.0.104:8080/sxc_admin_yx/";
//    public static final String baseImageUrl = "http://106.14.98.148:8080/";
    //    正式服
//    public static final String baseUrl = "http://106.15.47.15/sxc_admin_api/";
//    public static final String baseUrl = "http://106.14.135.110/sxc_admin_api/";
    public static final String baseUrl = "http://106.14.135.110/sxc_admin_api/";
    public static final String baseUrlV3 = "http://106.14.135.110:8383/SxcApiV3/";
    //        public static final String baseUrlV3 = "http://47.100.180.90:8080/SxcApiV3/";
    //    public static final String baseUrl = "http://192.168.0.125:8080/sxc_admin/";
    //    public static final String baseUrl = "http://192.168.0.104:8080/sxc_admin_v1.0/";
    //    public static final String baseUrl = "http://192.168.0.115:8080/sxc_admin/";
//    public static final String baseImageUrl = "http://106.15.47.15/";
    public static final String baseImageUrl = "http://106.14.135.110/";
    //    版本号
    public static final String version_code = "3.0.6";
    //获取已开通城市
    public static final String getCity = "app/homeApi/cityManagement";
    public static final String getArea = "app/homeApi/getAddress.do";
    //    获取门店
    public static final String getStore = "app/homeApi/store";
    //    根据城市名称获取ID
    public static final String getCityName = "app/homeApi/cityIdByName";
    //    获取租车车源列表
    public static final String getCarList = "app/leaseCarApi/list";
    //    获取租车推荐车源
    public static final String getLeaseRecommend = "app/homeApi/vehicleSourceRecommend";
    //    获取车辆类型ID
    public static final String getLeaseCarTypeId = "app/homeApi/vehicleModel";
    //    注册
    public static final String register = "app/homeApi/register";
    //    发送验证码
    public static final String sendCode = "api/user/sendCode";
    //    获取二手车车辆列表
    public static final String getOldCarList = "app/oldCarApi/listOldCar";
    //    获取二手车车辆详情
    public static final String getOldCarDetail = "app/oldCarApi/detail";
    //    校验验证码
    public static final String checkCode = "app/homeApi/chkPhoneCode";
    //    预约看车接口
    public static final String appointment = "app/oldCarApi/appointment";
    //    支付宝支付回调接口
    public static final String alipayNotifyUrl = "http://www.sxzcar.com/notify/pay/handle/alipayNotifyUrl?type=";
    //    public static final String alipayNotifyUrl = "http://106.14.98.148:8080/sxc_web/pay/handle/alipayNotifyUrl?type=";
    //    登录接口
    public static final String login = "api/user/login";
    //    获取咨询电话
    public static final String getConsultPhone = "app/homeApi/platformSetting";
    //    预约卖车
    public static final String sellSubmit = "app/homeApi/sell/submit";
    //    租车详情接口
    public static final String leaseCarInfo = "app/leaseCarApi/detail";
    //    租车费用计算接口
    public static final String leaseCarCost = "app/leaseCarApi/costs";
    //    提交租车订单接口
    public static final String leaseCarSubmit = "app/leaseCarApi/submit";
    //    微信获取预支付订单接口
    public static final String getWxPrepayId = "app/wxApi/getPrepayId";
    //    余额支付
    public static final String balancePay = "app/userApi/balancePay";
    //    获取用户信息
    public static final String getUserInfo = "app/userApi/userList";
    //    获取押金订单信息
    public static final String getDepositInfo = "app/depositApi/getdeposit.do";
    //    获取押金订单号
    public static final String getDepositNO = "app/depositApi/createDepositOrder.do";
    //    获取订单列表
    public static final String getOrderList = "app/userApi/rentalCarList";
    //    获取共享订单列表
    public static final String getShareOrderList = "app/shareCarOrder/orderlist";
    //    取消订单
    public static final String cancelOrder = "app/userApi/cancelOrder";
    //    上传头像图片
    public static final String uploadImage = "app/userApi/upload";
    //    上传认证图片
    public static final String uploadImageAuth = "app/userApi/uploadauthentication";
    //    实名认证
    public static final String doauthentication = "app/userApi/doauthentication";
    //    获取账户充值订单号接口
    public static final String getRechargeNo = "app/userApi/personRecharge";
    //    验证共享是否能租车
    public static final String doCheckUser = "app/userApi/docheckuser";
    //    验证实名认证审核情况
    public static final String doAuthenCheck = "app/userApi/checkRealNameAuthen";
    //    地图可用车辆数量
    public static final String useableCarNum = "app/shareCar/list";
    //    根据门店获取所有可用车源
    public static final String getCarByStoreId = "app/shareCar/getCarByStoreId";
    //    燃油车根据门店获取所有可用车源
    public static final String getOilCarByStoreId = "app/shareCar/getOilCarByStoreId";
    //    整租根据门店获取所有可用车源
    public static final String getLongCarByStoreId = "app/shareCarOrderLong/getLongCarByStoreId";
    //    预约共享汽车
    public static final String appointCar = "app/shareCarOrder/doappointV2";
    //    整租预约共享汽车
    public static final String inDayAppointCar = "app/shareCarOrderLong/doLongAppointV2";
    //    整租开锁用车
    public static final String inDayUnLockCar = "app/shareCarOrderLong/doLongUseV2";
    //    燃油车长租开锁
    public static final String doOilLongUse = "app/shareOilCarOrderLong/doOilLongUse";
    //    取消预约共享汽车
    public static final String cancelAppointCar = "app/shareCarOrder/cancelAppoint";
    //    余额提现
    public static final String balanceWithDraw = "app/userApi/withdrawApplyNew";
    //    修改个人信息
    public static final String editUser = "app/userApi/editUser";
    //    获取钱包信息
    public static final String getUserBalanceDeposit = "app/userApi/getUserBalanceDeposit";
    //    生成共享押金订单
    public static final String createShareDepositNo = "app/depositApi/createShareDepositOrder";
    //    应交共享押金
    public static final String getNeedShareDeposit = "app/depositApi/getShareDeposit";
    //    获取顶部banner
    public static final String getAppBanner = "app/homeApi/getAppBanner";
    //    获取共享车订单详情
    public static final String getShareOrderDetail = "app/shareCarOrder/orderDetail";
    //    请求推荐车源
    public static final String getRecommendDetail = "app/leaseCarApi/detail";
    //    请求邀请好友数据
    public static final String getInviteData = "app/userApi/getIntegralRulesNew";
    //    消息中心请求接口
    public static final String getMessage = "app/userApi/pushMessage";
    //    验证是否能租车
    public static final String checkCanLease = "app/leaseCarApi/checkOneOrder";
    //    验证能否提现
    public static final String checkCanWithdraw = "app/depositApi/getAllLock";
    //    查看租车订单详情
    public static final String openMyOrderDetail = "app/userApi/detailOrder";
    //    获取app版本
    public static final String getVersions = "api/common/getAndroidVersions";
    //    上传崩溃日志
    public static final String uploadCrash = "app/homeApi/uploadBugText";
    //整租接口
    public static final String getShareInDay = "app/shareCarOrderLong/getLongList";
    //    获取共享车门店列表
    public static final String getShareStoreList = "app/shareCar/storeList";
    //    到店租订单列表接口
    public static final String getShareInDayOrderList = "app/shareCarOrderLong/getShareCarLongOrderList";
    //    燃油车长租订单列表接口
    public static final String getShareOilCarLongOrderList = "app/shareOilCarOrderLong/getShareOilCarLongOrderList";
    //    到店租订单详情接口
    public static final String getInDayOrderDetail = "app/shareCarOrderLong/getShareCarLongOrderDetail";
    //    燃油车长租订单详情接口
    public static final String getShareOilCarLongOrderDetail = "app/shareOilCarOrderLong/getShareOilCarLongOrderDetail";
    //    共享车鸣笛
    public static final String getCarBlow = "app/shareCar/getCarMingdi";
    //    共享车开锁
    public static final String getCarUnLock = "app/shareCar/getUnlockCarV2";
    //    共享车锁车
    public static final String getCarLock = "app/shareCar/getCarLock";
    //    共享车定时器计算费用
    public static final String getShareCarCost = "app/shareCarOrder/getPreCost";
    //    共享车判断是否熄火
    public static final String checkIsFlameOut = "app/shareCar/getAccCar";
    //    共享车结束用车
    public static final String endAppoint = "app/shareCarOrder/endAppointV2";
    //    检查是否有订单
    public static final String checkIsHaveShareOrder = "app/shareCar/getUsingOrderV2";
    //    上传车辆图片信息
    public static final String uploadCarImage = "app/shareCarOrder/uploadCarImage";
    //    反馈问题上传图片
    public static final String uploadProblemImage = "app/feedbackApi/uploadProblemImage";
    //    上传车况信息
    public static final String orderAuditImage = "app/shareCarOrder/orderAuditImage";
    //    获取更改还车门店列表
    public static final String getStoreReturnList = "app/shareCar/storeReturnList";
    //    到店租门店列表
    public static final String getInDayStoreList = "app/shareCar/storeLongList";
    //    更改还车网点
    public static final String changeReturnStore = "app/shareCar/getChangeStore";
    //    临时锁车
    public static final String unLockCar = "app/shareCarHandle/unlockCar";
    //    一键开锁
    public static final String lockCar = "app/shareCarHandle/lockCar";
    //    整租还车
    public static final String endIndayOrder = "app/shareCarOrderLong/doLongEndAppointV2";
    //    燃油车整租还车
    public static final String doOilCarLongEndAppoint = "app/shareOilCarOrderLong/doOilCarLongEndAppoint";
    //    支付宝签名获取接口
    public static final String getAliSign = "app/aliApi/getAliSign";
    //    反馈问题
    public static final String problemFeedback = "app/feedbackApi/doFeedback";
    //    获取整租开通城市列表
    public static final String getInDayCityList = "app/shareCar/alreadyCityList";
    //    获取豪车租开通城市列表
    public static final String getLuxuryCarAlreadyCityList = "app/shareOil/luxuryCarAlreadyCityList";
    //    获取整租规则列表
    public static final String getInDayRulesList = "app/shareCar/cityLongList";
    //    整租根据城市获取门店列表
    public static final String getStoreLongByCity = "app/shareCar/getStoreLongByCity";
    //    豪车租根据城市获取门店列表
    public static final String getLuxuryCarStore = "app/shareOil/getLuxuryCarStore";
    //    豪车租根据门店获取车源列表
    public static final String getLuxuryCarList = "app/shareOil/luxuryCar";
    //    根据门店获取车型
    public static final String getLongCarByStore = "app/shareCar/getLongCarByStore";
    //    根据所选车型获取规则列表
    public static final String getLongListByStoreCar = "app/shareCar/getLongListByStoreCar";
    //    获取活动列表
    public static final String getActivityList = "app/shareCoupon/getActivityList";
    //    获取活动标志位
    public static final String isHaveActivity = "app/shareCoupon/getCouponFlag";
    //    获取抽奖门店列表
    public static final String getLuckyStoreList = "app/shareCoupon/getCouponStoreList";
    //    获取剩余券数和抽奖次数
    public static final String getCustomerCoupon = "app/shareCoupon/getCustomerCoupon";
    //    上传抽奖结果
    public static final String uploadLuckyResult = "app/shareCoupon/getCoupon";
    //    获取我的优惠券列表
    public static final String getMyCouponList = "app/shareCoupon/getMyCoupon";
    //    获取优惠券规则
    public static final String getCouponRemark = "app/shareCoupon/getCouponList";
    //    获取免费券抽奖规则
    public static final String getLotteryRule = "app/shareCoupon/getNewCouponList";
    //    根据规则id和type获取还车门店列表
    public static final String getStoreLongByFeeSetting = "app/shareCar/getStoreLongByFeeSetting";
    //    新共享地图获取门店列表接口
    public static final String getShareStoreListNew = "app/shareCar/listShare";
    //    燃油车共享地图获取门店列表接口
    public static final String getOilShareStoreList = "app/shareOil/listShareOil";
    //    油车长租短租规则列表
    public static final String getOilRentTypeList = "app/shareOil/listRentType";
    //    获取油车100公里外车源列表
    public static final String getOilCarLongRentByStoreId = "app/shareOilCarOrderLong/getOilCarByStoreId";
    //    长租订车车接口
    public static final String doOilLongAppoint = "app/shareOilCarOrderLong/doOilLongAppoint";
    //    长租天数规则列表
    public static final String getOilLongFeeSettingList = "app/shareOilCarOrderLong/getOilLongFeeSettingList";
    //    豪车租天数规则列表
    public static final String getLuxuryCarFeeSeeting = "app/shareOil/luxuryCarFeeSeeting";
    //    油车长租开锁
    public static final String unLockOilShareInday = "app/shareOilCarOrderLong/doOilLongUse";
    //    油车付费租车
    public static final String doLuxuryLongAppointV2 = "app/shareCarOrderLong/doLuxuryLongAppointV2";
    //    油车结束订单检查是否在网点还车
    public static final String oilLongOrdercheckPosition = "app/shareOilCarOrderLong/oilLongOrdercheckPosition";
    //    鸣笛接口
    public static final String doCarBlow = "app/shareCarOrder/findCar";
    //    油车整租更换还车门店
    public static final String getChangeOilStore = "app/shareOilCarOrderLong/getChangeOilStore";
    //    获取抽奖结果接口
    public static final String getLotteryResult = "app/shareCoupon/getNewCoupon";
    //    V3获取钱包信息
    public static final String getWalletInfoV3 = "api/wallet/myWallet";
    //    V3获取余额信息
    public static final String getBalanceInfoV3 = "api/wallet/myBalance";
    //    V3获取共享押金信息
    public static final String getShareDepositInfoV3 = "api/wallet/myShareDeposit";
    //    V3获取随心车押金信息
    public static final String getLeaseDepositInfoV3 = "api/wallet/mySxcDeposit";
    //    V3获取共享车押金提现信息
    public static final String getShareDepositWithdrawInfoV3 = "api/withdrawals/toShareDepositWithdrawals";
    //    V3获取随心车押金提现信息
    public static final String getLeaseDepositWithdrawInfoV3 = "api/withdrawals/toSxcDepositWithdrawals";
    //    V3获取优惠券信息
    public static final String getMyCouponV3 = "api/wallet/myCoupon";
    //    V3获取共享押金充值金额列表
    public static final String getShareDepositRechargeCountListV3 = "api/pay/toAddDeposit";
    //    V3获取押金充值订单号
    public static final String getShareRechargeOrderNoV3 = "api/pay/doAddDeposit";
    //    V3微信获取预支付订单接口
    public static final String getWxPrepayIdV3 = "api/pay/getWxPrepayId";
    //    V3支付宝签名获取接口
    public static final String getAliSignV3 = "api/pay/getAliSign";
    //    V3测试加密接口
    public static final String signTestV3 = "api/common/testSign";
    //    V3余额提现
    public static final String balanceWithDrawV3 = "api/withdrawals/doBalanceWithdrawals";
    //    V3共享提现
    public static final String shareWithDrawV3 = "api/withdrawals/doShareDepositWithdrawals";
    //    V3随心车提现
    public static final String leaseWithDrawV3 = "api/withdrawals/doSxcDepositWithdrawals";
    //    V3余额充值获取订单号
    public static final String getBalanceRechargeOrderNoV3 = "api/pay/doRecharge";
    //    V3获取地图电动分时门店列表
    public static final String getElecMapStoreListV3 = "api/electric/time/getCarMapNum";
    //    V3获取地图电动长租门店列表
    public static final String getElecInDayMapStoreListV3 = "api/electric/long/getCarMapNum";
    //    V3获取门店详情
    public static final String getStoreDetailV3 = "api/common/getStoreDetail";
    //    V3电车短租获取还车更换网点门店列表
    public static final String getReturnStoreListV3 = "api/electric/time/getReturnStoreList";
    //    V3电车长租获取还车更换网点门店列表
    public static final String getElecInDayReturnStoreListV3 = "api/electric/long/getReturnStoreList";
    //    V3电车分时租获取车源列表
    public static final String getCarList_elec_shortV3 = "api/electric/time/getCarListByStore";
    //    V3电车分时租预约
    public static final String elecShort_appointV3 = "api/electric/time/doAppoint";
    //    v3电车分时开始用车界面数据接口
    public static final String getBeginUserInfoV3 = "api/electric/time/toUse";
    //    V3验证用户是否能租车
    public static final String commonDoCheckUser = "api/user/doCheckUser";
    //V3电车分时取消预约
    public static final String elecShortCancelOrder = "api/electric/time/cancelAppoint";
    //    V3鸣笛寻车
    public static final String getCarBlowV3 = "api/common/whistle";
    //    V3电车分时租开锁用车
    public static final String geCarUnLockV3 = "api/electric/time/doUse";
    //    V3统一上传照片接口
    public static final String uploadImageV3 = "api/photos/upload/common";
    //    上传车况信息
    public static final String orderAuditImageV3 = "api/photos/upload/orderAuditImage";
    //    V3电车分时租获取费用
    public static final String getElecShorePreCostV3 = "api/electric/time/getPreCost";
    //    V3电车分时结束用车
    public static final String elecShortEnduse = "api/electric/time/doReturn";
    //    V3临时开锁
    public static final String shortUnlockV3 = "api/common/unlock";
    //    V3临时锁车
    public static final String shortLockV3 = "api/common/lock";
    //    V3电车分时进入支付界面获取信息
    public static final String getElecShortPayInfoV3 = "api/electric/time/toPay";
    //    V3检查是否有订单
    public static final String checkIsHaveShareOrderV3 = "api/common/getUsingOrder";
    //    V3电车分时租支付
    public static final String elecShortPayV3 = "api/electric/time/doPay";
    //    V3电车分时支付获取优惠券列表
    public static final String getPayCouponListV3 = "api/electric/time/orderCouponList";
    //    V3更改还车网点
    public static final String getChangeReturnStoreListV3 = "api/common/doChangeStore";
    //V3获取电车长租获取车源列表
    public static final String getElecInDayCarListV3 = "api/electric/long/getCarListByStore";
    //    V3电车整租根据选择天数获取优惠券列表
    public static final String getUsefulCouponListByDayTypeV3 = "api/electric/long/getCouponListBySetting";
    //    V3电车长租可用优惠券数量
    public static final String getElecIndayOrderCouponSizeV3 = "api/electric/long/orderCouponSize";
    //    V3电车长租付费租车
    public static final String elecInDayRentCarV3 = "api/electric/long/doAppoint";
    //    V3电车长租拍照开锁
    public static final String elecInDayUnLockCarV3 = "api/electric/long/doUse";
    //    V3电车长租开锁后结束用车界面
    public static final String getElecInDayEndInfoV3 = "api/electric/long/toUse";
    //    V3电车长租结束用车
    public static final String elecInDayEndCarV3 = "api/electric/long/doReturn";
    //    V3油车短租获取地图门店列表
    public static final String getOilShortMapStoreListV3 = "api/oil/time/getCarMapNum";
    //    V3油车分时获取还车更换网点门店列表
    public static final String getOilReturnStoreListV3 = "api/oil/time/getReturnStoreList";
    //    V3油车长租获取还车更换网点门店列表
    public static final String getOilInDayReturnStoreListV3 = "api/oil/long/getReturnStoreList";
    //    V3油车分时租获取车源列表
    public static final String getCarList_oil_shortV3 = "api/oil/time/getCarListByStore";
    //    V3油车分时租预约
    public static final String oilShort_appointV3 = "api/oil/time/doAppoint";
    //    v3油车分时开始用车界面数据接口
    public static final String getOilBeginUserInfoV3 = "api/oil/time/toUse";
    //    V3油车分时开锁用车
    public static final String getOilCarUnLockV3 = "api/oil/time/doUse";
    //V3油车分时取消预约
    public static final String oilShortCancelOrderV3 = "api/oil/time/cancelAppoint";
    //    v3油车分时结束用车
    public static final String oilShortEndUseV3 = "api/oil/time/doReturn";
    //    V3油车分时租获取费用
    public static final String getOilShorePreCostV3 = "api/oil/time/getPreCost";
    //    V3油车上传中控油量照片
    public static final String uploadOilMassImageV3 = "api/photos/upload/dashboardImage";
    //    V3油车短租支付界面信息
    public static final String getOilShortPayInfoV3 = "api/oil/time/toPay";
    //    V3油车分时支付获取优惠券列表
    public static final String getOilShareShortPayCouponListV3 = "api/oil/time/orderCouponList";
    //    V3油车分时租支付
    public static final String oilShortPayV3 = "api/oil/time/doPay";
    //    V3油车长租地图获取门店列表
    public static final String getOilInDayMapStoreListV3 = "api/oil/long/getCarMapNum";
    //V3获取油车长租获取车源列表
    public static final String getOilInDayCarListV3 = "api/oil/long/getCarListByStore";
    //    V3油车长租付费租车
    public static final String oilInDayRentCarV3 = "api/oil/long/doAppoint";
    //    V3油车长租拍照开锁
    public static final String OilInDayUnLockCarV3 = "api/oil/long/doUse";
    //    V3电车长租可用优惠券数量
    public static final String getOilIndayOrderCouponSizeV3 = "api/oil/long/orderCouponSize";
    //    V3油车长租支付获取优惠券列表
    public static final String getOilShareIndayPayCouponListV3 = "api/oil/long/getCouponListBySetting";
    //    V3油车长租开锁后结束用车界面
    public static final String getOilInDayEndInfoV3 = "api/oil/long/toUse";
    //    V3油车长租结束用车
    public static final String oilInDayEndCarV3 = "api/oil/long/doReturn";
    //    V3获取地图电动分时门店列表
    public static final String getLuxuryMapStoreListV3 = "api/luxury/getCarMapNum";
    //    V3豪车根据门店获取车源列表
    public static final String getLuxuryCarListByStoreV3 = "api/luxury/getCarListByStore";
    //    V3豪车预约界面获取车辆信息
    public static final String getLuxuryAppointCarInfoV3 = "api/luxury/getSettingList";
    //    V3豪车付费预约租车
    public static final String luxuryAppointV3 = "api/luxury/doPayAppoint";
    //    V3豪车去审核界面信息
    public static final String getLuxuryPreAuditInfoV3 = "api/luxury/toAppointView";
    //    V3获取用户是否绑定信用卡,诚信报告审核状态
    public static final String getCreditAuthenStateV3 = "api/user/doCheckCreditAuthen";
    //    V3提交信用卡审核
    public static final String creditCardAuditV3 = "api/user/doBindCredit";
    //V3侧边栏用户信息
    public static final String getUserInfoV3 = "api/user/myUser";
    //    V3上传用户征信报告
    public static final String uploadCreditReportImageV3 = "api/photos/upload/creditReportImage";
    //    V3去审核界面 去审核接口
    public static final String getPriAuditInfoV3 = "api/luxury/doReady";
    //    V3上传豪车所需资料
    public static final String uploadLuxuyInfoV3 = "api/photos/upload/guarantorImage";
    //    V3豪车选择时长界面车辆信息
    public static final String getLuxuryConfirmDayTypeCarInfoV3 = "api/luxury/toChoiceView";
    //    V3豪车查询审核状态
    public static final String luxuryCheckGuarantorV3 = "api/user/doCheckGuarantor";
    //    V3豪车确定选择时长
    public static final String confirmLuxuryDayTypeV3 = "api/luxury/toChoiceDays";
    //    V3豪车租付费租车界面信息
    public static final String getLuxuryRentCarInfoV3 = "api/luxury/toUseView";
    //    V3豪车租支付获取优惠券列表
    public static final String getLuxuryPayCouponListV3 = "api/luxury/orderCouponList";
    //V3豪车付费租车
    public static final String luxuryRentCarV3 = "api/luxury/toUse";
    //    V3豪车获取还车界面信息
    public static final String getLuxuryReturnCarInfoV3 = "api/luxury/toReturnView";
    //    V3豪车还车
    public static final String luxuryReturnCarV3 = "api/luxury/toReturn";
    //    V3实名认证
    public static final String doauthenticationV3 = "api/user/doAuthentication";
    //    V3验证实名认证审核情况
    public static final String doAuthenCheckV3 = "api/user/doCheckRealnameAuthen";
    //    V3获取电车分时订单列表
    public static final String getElecShortOrderListV3 = "api/electric/time/orderList";
    //    V3获取电车整租订单列表
    public static final String getElecInDayOrderListV3 = "api/electric/long/orderList";
    //    V3获取油车分时订单列表
    public static final String getOilShortOrderListV3 = "api/oil/time/orderList";
    //    V3获取油车长租订单列表
    public static final String getOilInDayOrderListV3 = "api/oil/long/orderList";
    //    V3获取豪车长租订单列表
    public static final String getLuxuryOrderListV3 = "api/luxury/orderList";
    //    V3获取电车分时已结束或者取消订单详情
    public static final String getElecShortOffOrderDetailV3 = "api/electric/time/orderOkDetail";
    //    V3获取电车长租已结束或者取消订单详情
    public static final String getElecInDayOffOrderDetailV3 = "api/electric/long/orderOkDetail";
    //    V3获取油车分时已结束或者取消订单详情
    public static final String getOilShortOffOrderDetailV3 = "api/oil/time/orderOkDetail";
    //    V3获取油车长租已结束或者取消订单详情
    public static final String getOilInDayOffOrderDetailV3 = "api/oil/long/orderOkDetail";
    //    V3获取豪车长租已结束或者取消订单详情
    public static final String getLuxuryOffOrderDetailV3 = "api/luxury/orderOkDetail";
    //    V3修改个人信息
    public static final String editUserV3 = "api/user/editUser";
    //    V3反馈问题
    public static final String problemFeedbackV3 = "api/feedbackApi/doFeedback";
    //    V3获取各类活动数量
    public static final String getEventCountV3 = "api/activity/myActivity";
    //    V3根据类型获取活动列表
    public static final String getEventListByTypeV3 = "api/activity/activityDetail";
    //    V3获取补款充值说明
    public static final String getRepairRemarkV3 = "api/pay/toAddRecharge";
    //  v3获取补款充值订单号
    public static final String getRepairOrderNoV3 = "api/pay/doAddRecharge";
    //    获取活动标志位
    public static final String isHaveActivityV3 = "api/activity/homeActivity";
    //    随手拍活动详情
    public static final String getSnapShotDetailV3 = "api/theme/themeDetail";
    //    随手拍上传信息
    public static final String snapshotUploadInfoV3 = "api/theme/doPose";
    //    网点推荐信息上传
    public static final String uploadRecommendInfoV3 = "api/recommend/recommend";
    //    获取实人认证token
    public static final String getRPTokenV3 = "api/user/getRPToken";
    //    获取订单评价界面评价标签
    public static final String getRemarkLabelV3 = "api/evaluate/toEvaluate";
    //    评价订单
    public static final String remarkOrderV3 = "api/evaluate/doEvaluate";

}

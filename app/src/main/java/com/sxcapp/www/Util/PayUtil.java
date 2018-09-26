package com.sxcapp.www.Util;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.sxcapp.www.AliPay.PartnerConfig;
import com.sxcapp.www.AliPay.SignUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/4.
 */
public class PayUtil {
    private static int SDK_PAY_FLAG = 1;

    public void doPay(final Activity activity, final Handler handler, String aliNO, String finalPrice, String subject, String type) {
        try {

            String orderInfo = getOrderInfo(aliNO, finalPrice, subject, type);
            String sign = sign(orderInfo);
            try {
                // 仅需对sign 做URL编码
                sign = URLEncoder.encode(sign, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            final String payInfo = orderInfo + "&sign=" + "\"" + sign + "\""
                    + "&" + getSignType();
            Runnable payRunnable = new Runnable() {

                @Override
                public void run() {
                    // 构造PayTask 对象
                    PayTask alipay = new PayTask(activity);
                    Map<String, String> result = alipay.payV2(payInfo, true);
                    Log.i("msp", result.toString());

                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    handler.sendMessage(msg);
                }
            };

            // 必须异步调用
            Thread payThread = new Thread(payRunnable);
            payThread.start();

        } catch (Exception ex) {

        }
    }


    public void doWebPay(final Activity activity, final Handler handler, final String payInfo) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(payInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    public static String getOrderInfo(String aliNO, String finalPrice, String subject, String type) {
        String name = subject;
        String body = subject;

        String strOrderInfo = "partner=" + "\"" + PartnerConfig.PARTNER + "\"";

        strOrderInfo += "&seller_id=" + "\"" + PartnerConfig.SELLER + "\"";

        strOrderInfo += "&out_trade_no=" + "\"" + aliNO + "\"";

        strOrderInfo += "&subject=" + "\"" + name + "\"";
        strOrderInfo += "&body=" + "\"" + body + "\"";

        strOrderInfo += "&total_fee=" + "\"" + finalPrice + "\"";

        strOrderInfo += "&notify_url=" + "\"" + Properties.alipayNotifyUrl + type
                + "\"";

        // 服务接口名称， 固定值
        strOrderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        strOrderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        strOrderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        strOrderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        strOrderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // strOrderInfo += "&paymethod=\"expressGateway\"";

        return strOrderInfo;
    }

    public static String sign(String content) {
        return SignUtils.sign(content, PartnerConfig.RSA_PRIVATE, false);
    }

    public static String getSignType() {
        String getSignType = "sign_type=" + "\"" + "RSA" + "\"";
        return getSignType;
    }


}

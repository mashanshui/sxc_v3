package com.sxcapp.www.webtool;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;
import com.sxcapp.www.MyApplication;
import com.sxcapp.www.Util.NetUtil;
import com.sxcapp.www.Util.Properties;
import com.sxcapp.www.Util.RsaEncryptUtil;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.StringUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wenleitao on 2016/8/23.
 */
public class RetrofitManagerV3 {

    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
    //(假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
    private static final String CACHE_CONTROL_NETWORK = "max-age=0";

    private Retrofit retrofit;

    private static RetrofitManagerV3 instance;

    private static volatile OkHttpClient sOkHttpClient;


    public enum RetrofitManagerBuild {
        INSTANCE;
        private RetrofitManagerV3 instance;

        RetrofitManagerBuild() {
            instance = new RetrofitManagerV3();
        }

        public RetrofitManagerV3 getInstance() {
            return instance;
        }
    }

    public RetrofitManagerV3() {
        retrofitCreate();
    }

    private void retrofitCreate() {

        retrofit = new Retrofit.Builder()
                .baseUrl(Properties.baseUrlV3)
                .client(getOkHttpClient())
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private OkHttpClient getOkHttpClient() {
        if (sOkHttpClient == null) {
            synchronized (RetrofitManagerV3.class) {
                if (sOkHttpClient == null) {
                    // OkHttpClient配置是一样的,静态创建一次即可
                    // 指定缓存路径,缓存大小100Mb
//                    Cache cache = new Cache(new File(MyApplication.GetAppContext().getCacheDir(), "HttpCache"),
//                            1024 * 1024 * 100);

                    sOkHttpClient = new OkHttpClient
                            .Builder()
                            .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                            .readTimeout(10000L, TimeUnit.MILLISECONDS)
//                            .cache(cache)
//                            .addNetworkInterceptor(mRewriteCacheControlInterceptor)
//                            .addInterceptor(mRewriteCacheControlInterceptor)
//                            .addInterceptor(mTokenInterceptor)
                            .addInterceptor(mLoggingInterceptor)
                            .addInterceptor(new AddCookiesInterceptor(MyApplication.getInstance().getApplicationContext()))
                            .addInterceptor(new SaveCookiesInterceptor(MyApplication.getInstance().getApplicationContext()))
                            .addInterceptor(new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    Request original = chain.request();
                                    HttpUrl originalHttpUrl = original.url();
                                    String randomStr = StringUtil.getRandomNum(10);
                                    String insertRandomStr = StringUtil.getRandomLetterAndNum(16);
                                    StringBuffer stringBuffer = new StringBuffer(randomStr);
                                    stringBuffer.insert(5, insertRandomStr);
                                    String reservesStr = stringBuffer.reverse().toString();
                                    String signStr = "";
                                    try {
                                        signStr = RsaEncryptUtil.encryptByPrivateKey(randomStr);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    HttpUrl url = originalHttpUrl.newBuilder()
                                            .addQueryParameter("version", "Android " + Properties.version_code + " " + SharedData.getInstance().getString(SharedData._phone_type))
                                            .addQueryParameter("nonce_str", reservesStr)
                                            .addQueryParameter("sign", signStr)
                                            .build();

                                    // Request customization: add request headers
                                    Request.Builder requestBuilder = original.newBuilder()
                                            .url(url);

                                    Request request = requestBuilder.build();
                                    return chain.proceed(request);
                                }
                            })
                            .build();

                }
            }
        }
        return sOkHttpClient;
    }

    public <T> T creat(Class<?> clazz) {
        return (T) retrofit.create(clazz);
    }

    public static String getCacheControl() {
        return NetUtil.isConnected(MyApplication.GetAppContext()) ? CACHE_CONTROL_NETWORK : CACHE_CONTROL_CACHE;
    }


    // 打印返回的json数据拦截器
    private Interceptor mLoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            final Request request = chain.request();
            final Response response = chain.proceed(request);

            final ResponseBody responseBody = response.body();
            final long contentLength = responseBody.contentLength();
            BufferedSource source = responseBody.source();
            Logger.e("发送请求\n" + "method:" + request.method() + "\nurl:" + request.url() + "\nheaders:" + request.headers());
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(charset);
                } catch (UnsupportedCharsetException e) {
                    Logger.e("Couldn't decode the response body; charset is likely malformed.");
                    return response;
                }
            }

            if (contentLength != 0) {
                Log.e("response", buffer.clone().readString(charset));
            }

            return response;
        }
    };


    // 云端响应头拦截器，用来配置缓存策略
    private Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetUtil.isConnected(MyApplication.GetAppContext())) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                Log.e("tag", "no network");
            }
            Response originalResponse = chain.proceed(request);
            if (NetUtil.isConnected(MyApplication.GetAppContext())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder().header("Cache-Control", cacheControl)
                        .removeHeader("Pragma").build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached," + CACHE_STALE_SEC)
                        .removeHeader("Pragma").build();
            }
        }
    };

    Interceptor mTokenInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            String value = "";
//            String value = "Token token="+ SharedData.getInstance().getString(SharedData._token)+",phone="+SharedData.getInstance().getString(SharedData._phone);
            if (value == null) {
                return chain.proceed(originalRequest);
            }
            Request authorised = originalRequest.newBuilder()
                    .header("Authorization", value)
                    .build();
            return chain.proceed(authorised);
        }
    };


}

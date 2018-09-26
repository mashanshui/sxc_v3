package com.sxcapp.www;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.security.rp.RPSDK;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.squareup.leakcanary.LeakCanary;
import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.UserCenter.HttpService.FileUploadService;
import com.sxcapp.www.Util.BaseObserver;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.activity.MainActivity;
import com.sxcapp.www.activity.StartActivity;
import com.sxcapp.www.webtool.RetrofitManager;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MyApplication extends Application {
    private static Context _context;
    private static MyApplication _instance;
    private List<Activity> _runningComponents;
    private String TAG = "MyApplication";
    private String packgeName;
    protected boolean isNeedCaughtExeption = false;// 是否捕获未知异常
    private PendingIntent restartIntent;
    private SRUncaughtExceptionHandler uncaughtExceptionHandler;

    public MyApplication() {
        _instance = this;
        _runningComponents = new ArrayList<>();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Config.DEBUG = true;
        UMShareAPI.get(this);
        packgeName = getPackageName();
        PlatformConfig.setWeixin("wx67f1fa1c43711bb9", "wxdbe9ac7b27ed324b");
        PlatformConfig.setQQZone("1106100585", "h00C6KEj3dA1kAn1");
        CrashReport.initCrashReport(getApplicationContext());
        RPSDK.initialize(getApplicationContext());
        String model = android.os.Build.MODEL;
        SharedData.getInstance().set(SharedData._phone_type, model);
        JPushInterface.setDebugMode(false);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  //（可选）是否显示线程信息。 默认值为true
                .methodCount(2)         // （可选）要显示的方法行数。 默认2
                .methodOffset(7)        // （可选）隐藏内部方法调用到偏移量。 默认5
                //（可选）更改要打印的日志策略。 默认LogCat
                .tag("suixinche")   //（可选）每个日志的全局标记。 默认PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return false;
            }
        });
        if (isNeedCaughtExeption) {
            cauchException();
        }
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }


    // -------------------异常捕获-----捕获异常后重启系统-----------------//

    @SuppressLint("WrongConstant")
    private void cauchException() {
        Intent intent = new Intent(getApplicationContext(), StartActivity.class);
        // 参数1：包名，参数2：程序入口的activity
        restartIntent = PendingIntent.getActivity(getApplicationContext(), -1,
                intent, Intent.FLAG_ACTIVITY_NEW_TASK);

//        // 程序崩溃时触发线程
        uncaughtExceptionHandler = new SRUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
    }


    // 创建服务用于捕获崩溃异常
    private class SRUncaughtExceptionHandler implements
            Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            // 保存错误日志
            Log.e(TAG, "uncaughtException: " + ex);
            saveCatchInfo2File(ex);
            // 1秒钟后重启应用
            AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 50000,
                    restartIntent);
            for (Activity activity : _runningComponents) {
                if (activity != null) {
                    activity.finish();
                }
            }
            android.os.Process.killProcess(android.os.Process.myPid());
            // 关闭当前应用

        }
    }


    /**
     * 保存错误信息到文件中
     *
     * @return 返回文件名称
     */
    private String saveCatchInfo2File(Throwable ex) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String sb = writer.toString();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(
                    "yyyy-MM-dd-HH-mm-ss");
            String time = formatter.format(new Date());
            String fileName = time + ".txt";
            System.out.println("fileName:" + fileName);
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                String filePath = Environment.getExternalStorageDirectory()
                        + "/Suixinche/" + packgeName + "/crash/";
                File dir = new File(filePath);
                if (!dir.exists()) {
                    if (!dir.mkdirs()) {
                        // 创建目录失败: 一般是因为SD卡被拔出了
                        return "";
                    }
                }
                FileOutputStream fos = new FileOutputStream(filePath + fileName);
                fos.write(sb.getBytes());
                fos.close();
                // 文件保存完了之后,在应用下次启动的时候去检查错误日志,发现新的错误日志,就发送给开发者
                uploadCrash(filePath + fileName);
            }
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file..."
                    + e.getMessage());
        }
        return null;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    public static MyApplication getInstance() {
        return _instance;
    }


    public static Context GetAppContext() {
        return _context;
    }

    public void systemExit() {

        for (Activity activity : _runningComponents) {
            if (activity != null) {
                activity.finish();
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    // ------------------------------activity管理-----------------------//
    public void addComponent(Activity component) {
        if (_runningComponents.size() == 0) {
            Log.i(TAG, "First app entry component");

//            init();
        }
        _runningComponents.add(component);
    }

    public void deleteComponent(Context component) {
        _runningComponents.remove(component);
        if (_runningComponents.size() == 0) {
            Log.i(TAG, "Last app entry component, exit");
            systemExit();
        }
    }

    public int getComponentCount() {
        if (_runningComponents == null) {
            return 0;
        } else {
            return _runningComponents.size();
        }
    }

    public void gotoMainActivity() {
        Activity activity;
        for (int i = _runningComponents.size() - 1; i >= 0; i--) {
            activity = _runningComponents.get(i);
            activity.finish();
        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
        Intent intent1 = new Intent(this, MainActivity.class);
        intent1.putExtra("from", "logout");
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent1);
    }

    public void confirmLogout(Context context) {
        SharedData.getInstance().logout();
        MyApplication.getInstance().gotoMainActivity();
    }

    private void uploadCrash(String fileName) {
        FileUploadService fileUploadService = RetrofitManager.RetrofitManagerBuild.INSTANCE.getInstance().creat(FileUploadService.class);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text"), new File(fileName));
        Observable<Result<Object>> ob = fileUploadService.uploadCrash(requestBody);
        ob.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<Object>(getApplicationContext()) {

                    @Override
                    protected void onHandleSuccess(Object o) {

                    }

                    @Override
                    protected void onHandleError(String msg) {


                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });


    }
}

package com.sxcapp.www.Util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.text.Html.ImageGetter;
import android.text.format.Time;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.sxcapp.www.MyApplication;

import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AndroidTool {


    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static int getStatusBarHeight(Context context) {
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    // 不同大小屏幕用不同大小的图片，low:medium:high:extra-high图片大小的比例为3:4:6:8；
    // 举例来说，对于中等密度(medium)的屏幕你的图片像素大小为48×48，那么低密度(low)屏幕的图片大小应为36×36，高(high)的为72×72，extra-high为96×96。

    // 四种屏幕尺寸分类：: small, normal, large, and xlarge
    // 四种密度分类: ldpi (low), mdpi (medium), hdpi (high), and xhdpi (extra high)
    // 需要注意的是: xhdpi是从 Android 2.2 (API Level 8)才开始增加的分类.
    // xlarge是从Android 2.3 (API Level 9)才开始增加的分类.
    // DPI是“dot per inch”的缩写，每英寸像素数。

    // 一般情况下的普通屏幕：ldpi是160，mdpi是240，hdpi是320，xhdpi是480(720p)，xxhdpi是640(1080p)。

    public static String getRootDir() {
        String path;
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            path = android.os.Environment.getExternalStorageDirectory()
                    .toString() + "/suixinche/";
        } else {
            path = MyApplication.GetAppContext().getCacheDir().toString()
                    + "/suixinche/";
        }

        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        return path;
    }

    public static File GetImageCacheDir() {
        File fCacheDir = null;// g_Context.getExternalCacheDir();
        fCacheDir = new File(getRootDir() + "/img/");
        if ((fCacheDir == null)
                || ((!fCacheDir.exists()) && !fCacheDir.mkdirs())) {
            fCacheDir = MyApplication.GetAppContext().getCacheDir();
        }
        return fCacheDir;
    }

    public static File GetSaveImageDir() {
        File fCacheDir = null;// g_Context.getExternalCacheDir();
        fCacheDir = new File(getRootDir() + "/saveimg/");
        if ((fCacheDir == null)
                || ((!fCacheDir.exists()) && !fCacheDir.mkdirs())) {
            fCacheDir = MyApplication.GetAppContext().getCacheDir();
        }
        return fCacheDir;
    }

    public static File GetDatabaseDir() {
        File fCacheDir = null;// g_Context.getExternalCacheDir();
        fCacheDir = new File(getRootDir() + "/db/");
        if ((fCacheDir == null)
                || ((!fCacheDir.exists()) && !fCacheDir.mkdirs())) {
            fCacheDir = MyApplication.GetAppContext().getCacheDir();
        }
        return fCacheDir;
    }

    public static File GetWebViewCacheDir() {
        File fCacheDir = null;// g_Context.getExternalCacheDir();
        fCacheDir = new File(getRootDir() + "/webView/");
        if ((fCacheDir == null)
                || ((!fCacheDir.exists()) && !fCacheDir.mkdirs())) {
            fCacheDir = MyApplication.GetAppContext().getCacheDir();
        }
        return fCacheDir;
    }

    public static File getAlbumDir() {
        File fCacheDir = null;// g_Context.getExternalCacheDir();
        fCacheDir = new File(getRootDir() + "/album/");
        if ((fCacheDir == null)
                || ((!fCacheDir.exists()) && !fCacheDir.mkdirs())) {
            fCacheDir = MyApplication.GetAppContext().getCacheDir();
        }
        return fCacheDir;
    }

    /**
     * 获得某个目录占用的空间大小
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        File[] fileList = file.listFiles();
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isDirectory()) {
                size = size + getFolderSize(fileList[i]);
            } else {
                size = size + fileList[i].length();
            }
        }
        return size;
    }

    public static String getParamsString(String[] params) {
        String url = "";
        for (int i = 0; i < params.length; i++) {
            url += params[i];
            if (i < params.length - 1) {
                url += "#";
            }
        }
        return url;
    }

    public static boolean isEmail(String strEmail) {
        String strPattern = "^[0-9a-zA-Z]([-_.~]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,4}$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }

    public static boolean isMobileNO(String strMobileNO) {
        String strPattern = "^[1][0-9]{10}$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strMobileNO);
        return m.matches();
    }

    public static boolean isPwd(String pwd) {
        String strPattern = "^[0-9a-zA-Z!@#$%*-_^]{6,18}$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(pwd);
        return m.matches();
    }

    public static boolean isPayPwd(String paypwd) {
        String strPattern = "^[0-9]{6}$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(paypwd);
        return m.matches();
    }

    public static boolean isTrue(String str) {
        String strPattern = "^[0-9]{1}$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static String getNumber(String barcodeDesc) {
        Pattern p;
       //在这里，编译 成一个正则。
        p = Pattern.compile("\\d+");
        Matcher m;
        m = p.matcher(barcodeDesc);//获得匹配
        String res = "";
        while (m.find()) { //注意这里，是while不是if
            String xxx = m.group();
            res += xxx;
        }
        return res;
    }

    public static int getAge(String birthday) {
        if (birthday == null || birthday.length() == 0) {
            return 0;
        }

        int age = Integer.parseInt(birthday);
        Time t = new Time();
        t.setToNow();
        return t.year - age;
    }

    public static int getVersion() {
        try {
            PackageManager manager = MyApplication.getInstance().getApplicationContext()
                    .getPackageManager();
            PackageInfo info = manager.getPackageInfo(MyApplication
                    .getInstance().getApplicationContext().getPackageName(), 0);
            int version = info.versionCode;
            Log.e("AndroidTool", version + "");
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("AndroidTool", e.toString());
            return 1;
        }
    }

    public static String getVersionName() {
        try {
            PackageManager manager = MyApplication.getInstance().getApplicationContext()
                    .getPackageManager();
            PackageInfo info = manager.getPackageInfo(MyApplication
                    .getInstance().getApplicationContext().getPackageName(), 0);
            String name = info.versionName;
            return name;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("AndroidTool", e.toString());
            return "1.0.0";
        }
    }

    public static String getImageUUID() {
        UUID uuid = UUID.randomUUID();
        String uniqueId = uuid.toString();

        return uniqueId;
    }

    public static String getChannelCode() {
        String code = getMetaData("UMENG_CHANNEL");
        if (code != null) {
            return code;
        }
        return "suber360";
    }

    private static String getMetaData(String key) {
        try {
            ApplicationInfo ai = MyApplication
                    .GetAppContext()
                    .getPackageManager()
                    .getApplicationInfo(
                            MyApplication.GetAppContext().getPackageName(),
                            PackageManager.GET_META_DATA);
            Object value = ai.metaData.get(key);
            if (value != null) {
                return value.toString();
            }
        } catch (Exception e) {
            //
        }
        return null;
    }

    @SuppressLint("SimpleDateFormat")
    public static String parseDate(long time) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = new Date(time);

        return df.format(date);
    }

    @SuppressLint("SimpleDateFormat")
    public static String parseDate2(long time) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date(time);

        return df.format(date);
    }


    public static ImageGetter getImageGetterInstance() {
        ImageGetter imgGetter = new ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {

                int id = Integer.parseInt(source);
                Drawable d = MyApplication.GetAppContext().getResources()
                        .getDrawable(id);
                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                return d;
            }
        };
        return imgGetter;
    }

    //截取当前屏幕
    public static Bitmap myShot(Activity activity) {
        // 获取windows中最顶层的view
        View view = activity.getWindow().getDecorView();
        view.buildDrawingCache();

        // 获取状态栏高度
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeights = rect.top;
        Display display = activity.getWindowManager().getDefaultDisplay();

        // 获取屏幕宽和高
        int widths = display.getWidth();
        int heights = display.getHeight();

        // 允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);

        // 去掉状态栏
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0,
                statusBarHeights, widths, heights - statusBarHeights);
        // Bitmap bmp = Bitmap.createBitmap( widths,
        // heights,android.graphics.Bitmap.Config.ARGB_8888);

        // 销毁缓存信息
        view.destroyDrawingCache();

        return bmp;
    }


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
        } else {
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isGpsEnabled(Context context) {
        LocationManager lm = ((LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE));
        List<String> accessibleProviders = lm.getProviders(true);
        return accessibleProviders != null && accessibleProviders.size() > 0;
    }

    public static boolean isWifiEnable(Context ctx) {
        boolean ret = true;
        WifiManager wifiManager = (WifiManager) ctx
                .getSystemService(Context.WIFI_SERVICE);
        if (wifiManager == null || !wifiManager.isWifiEnabled()) {
            ret = false;
        }
        return ret;
    }

    public static String settime(long time) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm ");
        return format1.format(time * 1000);

    }

    public static String settime2(long time) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        return format1.format(time * 1000);

    }

    public static String setDecimals(double money) {
        DecimalFormat df = new DecimalFormat("0.00");
        return String.valueOf(df.format((money)));
    }

    public static void clearCookies() {
        // Edge case: an illegal state exception is thrown if an instance of
        // CookieSyncManager has not be created. CookieSyncManager is normally
        // created by a WebKit view, but this might happen if you start the
        // app, restore saved state, and click logout before running a UI
        // dialog in a WebView -- in which case the app crashes
        @SuppressWarnings("unused")
        CookieSyncManager cookieSyncMngr = CookieSyncManager
                .createInstance(MyApplication.GetAppContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
    }

    public static String FormetFileSize(long fileS) {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }


    /**
     * 校验银行卡卡号
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId
                .substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null
                || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            // 如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    /**
     * 获取设备屏幕的宽
     *
     * @param context
     * @return
     */
    public static int getDeviceWidth(Activity context) {
        Display display = context.getWindowManager().getDefaultDisplay();
        Point p = new Point();
        display.getSize(p);
        return p.x;
    }

    /**
     * 获取屏幕的高
     */
    public static int getDeviceHeight(Activity context) {
        Display display = context.getWindowManager().getDefaultDisplay();
        Point p = new Point();
        display.getSize(p);
        return p.y;
    }

    public static boolean isGetLocationPermission(Context context) {
        PackageManager pkgManager = context.getPackageManager();
        boolean locationPermission =
                pkgManager.checkPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION, context.getPackageName()) == PackageManager.PERMISSION_GRANTED;
        return locationPermission;
    }

}

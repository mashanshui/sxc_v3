package com.sxcapp.www.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.CustomerView.CustomProgressBar;
import com.sxcapp.www.Download.Bean.VersionBean;
import com.sxcapp.www.Download.HttpService.DownloadService;
import com.sxcapp.www.R;
import com.sxcapp.www.Util.AndroidTool;
import com.sxcapp.www.Util.BaseObserver;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.webtool.RetrofitManager;
import com.sxcapp.www.webtool.RetrofitManagerV3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * Created by wenleitao on 2017/8/9.
 */

public class StartActivity extends BaseActivity {
    private Thread thread;
    @Nullable
    @BindView(R.id.update_progress)
    CustomProgressBar updateProgress;
    public static final int STATE_SUCC = 2;
    public static final int STATE_NETWORK_ERR = 3;
    public static final int STATE_REPORT_PROGRESS = 4;
    public static final int MSG_LOAD_APK = 5;
    public static final int START_LOAD_APK = 6;
    public static final int APP_NAME = 203;
    @BindView(R.id.update_text)
    TextView updateText;
    String updateUrl;
    private HttpURLConnection m_hLoadConn = null; // 下载HTTP链接
    protected Handler m_hMsgHandle = null;
    protected Message message = null;
    private String TAG = "StartActivity";
    private DownloadService service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        m_hMsgHandle = new Handler(new Handler.Callback() {

            @Override
            public boolean handleMessage(Message msg) {
                // TODO Auto-generated method stub
                return onHandleMessage(msg.what, msg.peekData());
            }
        });
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(DownloadService.class);
        showProgressDlg();
//        请求最新版本信息  判断是否升级
        Observable<CodeResultV3<VersionBean>> ob = service.getVersions();
        ob.compose(compose(this.<CodeResultV3<VersionBean>>bindToLifecycle())).subscribe(new CodeObserverV3<VersionBean>(this) {
            @Override
            protected void onHandleSuccess(VersionBean versionBean) {
                removeProgressDlg();
                if (versionBean.getAdminVersions() > AndroidTool.getVersion()) {
                    updateUrl = versionBean.getAppUrl();
                    int upgrade = versionBean.getUpgrade();
                    if (upgrade == 0) {
                        showUpdateDlg();
                    } else {
                        showForceUpdateDlg();
                    }
                } else {
                    next();
                }
            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<VersionBean> value) {
                super.onHandleError(msg, value);
                next();
            }
        });
    }

    public boolean onHandleMessage(final int MSG_ID, final Bundle bundle) {
        try {
            switch (MSG_ID) {
                case StartActivity.MSG_LOAD_APK: // 处理升级包下载
                    showLoadApk(bundle);
                    break;
                case StartActivity.START_LOAD_APK:
                    dealLoadApk(null);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
        }
        return false;
    }

    private void showLoadApk(final Bundle bundle) {
        try {
            int nState = bundle.getInt("state", StartActivity.STATE_SUCC);
            switch (nState) {
                case StartActivity.STATE_NETWORK_ERR: // 升级失败
                    showAlertDlg(getResources().getString(R.string.updatefail),
                            R.string.ok, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    onBackPressed();
                                }
                            }, 0, null, false);
                    break;
                case StartActivity.STATE_SUCC: // 下载完成
                    Uri uri = Uri.fromFile(new File(bundle.getString("file")));
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    if (Build.VERSION.SDK_INT >= 24) {
                        //provider authorities
                        Uri apkUri = FileProvider.getUriForFile(this, "com.sxcapp.www.fileprovider", new File(bundle.getString("file")));
                        //Granting Temporary Permissions to a URI
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                    } else {
                        intent.setDataAndType(uri,
                                "application/vnd.android.package-archive");
                    }
                    startActivity(intent);

                    onBackPressed();
                    break;
                case StartActivity.STATE_REPORT_PROGRESS: // 下载进度
                    int nProgress = bundle.getInt("progress");
                    updateProgress.setProgress(nProgress);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
        }
    }


    public void getPermission() {


        PermissionGen.with(StartActivity.this)
                .addRequestCode(100)
                .permissions(
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 100)
    public void doSomething() {
        removeAlertDlg();
        update();
    }


    @PermissionFail(requestCode = 100)
    public void doFailSomething() {
        showAlertDlg("请读取手机储存权限", R.string.set, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAlertDlg();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.fromParts("package", getPackageName(), null));
                startActivity(intent);
            }
        }, R.string.refuse, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAlertDlg();
            }
        }, true);
    }

    private void dealLoadApk(Message msg) {
        try {
            String szUri = updateUrl;
            Log.e("####", szUri);
            String szFilePath = Environment.getExternalStorageDirectory()
                    + szUri.substring(szUri.lastIndexOf("/"));
            // Log.i("--------------", "dealLoadApk() ApkUri:"
            // + szUri + " FilePath:" + szFilePath);
            URL fileUri = new URL(updateUrl);
            m_hLoadConn = (HttpURLConnection) fileUri.openConnection();
            m_hLoadConn.setUseCaches(false);
            m_hLoadConn.connect();
            InputStream inStream = m_hLoadConn.getInputStream();
            final int FILE_SIZE = m_hLoadConn.getContentLength();
            if (FILE_SIZE <= 0) {
                throw new Exception("文件长度错误");
            }
            if (null == inStream) {
                throw new Exception("文件流错误");
            }

            FileOutputStream fileStream = new FileOutputStream(szFilePath,
                    false);
            byte[] buf = new byte[1024];
            int nRead = 0;
            int nCount = 0;
            float fWrited = 0;

            int len = -1;
            while ((len = inStream.read(buf)) != -1) {
//                nRead = inStream.read(buf);
//                if (nRead <= 0) {
//                    break;
//                }
//                fileStream.write(buf, 0, nRead);
                fileStream.write(buf, 0, len);
                fWrited += len;
                nCount += len;

                if (nCount >= buf.length || FILE_SIZE == (int) fWrited) {
                    nCount = 0;
                    Message prgrssMsg = message.getTarget().obtainMessage(
                            StartActivity.MSG_LOAD_APK);
                    Bundle bdPrgrss = prgrssMsg.getData();
                    bdPrgrss.putInt("state", StartActivity.STATE_REPORT_PROGRESS);
                    bdPrgrss.putInt("progress",
                            (int) (fWrited / FILE_SIZE * 100));
                    prgrssMsg.sendToTarget();
                }
            }
//
//            if ((int) fWrited != FILE_SIZE) {
//                throw new Exception("文件长度不匹配");
//            }

            Message prgrssMsg = message.getTarget().obtainMessage(
                    StartActivity.MSG_LOAD_APK);
            Bundle bdPrgrss = prgrssMsg.getData();
            bdPrgrss.putInt("state", StartActivity.STATE_SUCC);
            bdPrgrss.putString("file", szFilePath);
            prgrssMsg.sendToTarget();
            fileStream.close();
            inStream.close();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            Message prgrssMsg = message.getTarget().obtainMessage(
                    StartActivity.MSG_LOAD_APK);
            Bundle bdPrgrss = prgrssMsg.getData();
            bdPrgrss.putInt("state", StartActivity.STATE_NETWORK_ERR);
            prgrssMsg.sendToTarget();
        }

        try {
            m_hLoadConn.disconnect();
        } catch (Exception e) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void showForceUpdateDlg() {
        showAlertDlg(
                getResources().getString(
                        R.string.updateinfo), R.string.ok,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Build.VERSION.SDK_INT >= 23) {
                            getPermission();
                        } else {
                            removeAlertDlg();
                            update();
                        }
                    }
                }
                , 0, null, false);
    }

    private void showUpdateDlg() {
        showAlertDlg(getResources().getString(R.string.updateinfo),
                R.string.ok,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Build.VERSION.SDK_INT >= 23) {
                            getPermission();
                        } else {
                            removeAlertDlg();
                            update();
                        }
                    }
                }, R.string.cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        removeAlertDlg();
                        next();
                    }
                }, false);
    }

    private void update() {
        removeAlertDlg();
        updateProgress.setVisibility(View.VISIBLE);
        updateText.setVisibility(View.VISIBLE);
        updateText.setText(R.string.updateing);
        message = m_hMsgHandle.obtainMessage(StartActivity.MSG_LOAD_APK);
        new Thread(new Runnable() {
            @Override
            public void run() {
                dealLoadApk(null);
            }
        }).start();

    }

    private void next() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                if (SharedData.getInstance().getBooleanDefValueTrue(
                        SharedData._first_start)) {
                    SharedData.getInstance().set(SharedData._first_start, false);
                    Intent intent = new Intent(StartActivity.this, GuideActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
        thread.start();
    }

}

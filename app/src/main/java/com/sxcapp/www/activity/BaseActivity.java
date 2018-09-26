package com.sxcapp.www.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.sxcapp.www.CustomerView.AppLoadingView;
import com.sxcapp.www.Login.LoginActivity;
import com.sxcapp.www.MyApplication;
import com.sxcapp.www.R;
import com.sxcapp.www.Util.AndroidTool;
import com.sxcapp.www.Util.NetUtil;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.StatusBarUtil;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * @author wenleitao
 * @date 2017/7/4
 */


public class BaseActivity extends RxAppCompatActivity implements LoginInterface {

    private Dialog mProgressDialog;
    private AlertDialog mAlertDialog;
    public int LOGIN_REQUEST = 129;
    private AppLoadingView progress;

    public class BaseHandler extends Handler {
        private WeakReference<BaseActivity> mActivity;

        public BaseHandler(BaseActivity mActivity) {
            this.mActivity = new WeakReference<>(mActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            handleMsg(msg);
        }
    }

    public void handleMsg(Message msg) {

    }

    public BaseHandler mHandler;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            try {
                Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                field.setAccessible(true);
                field.setInt(getWindow().getDecorView(), Color.TRANSPARENT);  //改为透明
            } catch (Exception e) {}
        }

        mHandler = new BaseHandler(this);
        Logger.e("onCreate");
        StatusBarUtil.StatusBarLightMode(this);
        MyApplication.getInstance().addComponent(this);
    }


    public void setStatusView(int color) {
        View statusBar = findViewById(R.id.statusBarView);
        statusBar.setVisibility(View.VISIBLE);
        statusBar.setBackgroundColor(getResources().getColor(color));
        ViewGroup.LayoutParams layoutParams_st = statusBar.getLayoutParams();
        layoutParams_st.height = AndroidTool.getStatusBarHeight(this);
        statusBar.setLayoutParams(layoutParams_st);
    }

    /**
     * @param titleid
     * @param listener 设置标题
     */
    public void setTopBarTitle(int titleid, View.OnClickListener listener) {
        TextView tv = (TextView) findViewById(R.id.topbar_title);
        if (tv != null) {
            if (titleid > 0) {
                tv.setText(titleid);
                tv.setOnClickListener(listener);
                tv.setVisibility(View.VISIBLE);
            } else {
                tv.setVisibility(View.GONE);
            }
        }
    }

    /**
     * @param colorId 设置topbar颜色
     */
    public void setTopBarColor(int colorId) {
        RelativeLayout re = (RelativeLayout) findViewById(R.id.top_bar_re);
        if (re != null) {
            if (colorId > 0) {
                re.setBackgroundColor(getResources().getColor(colorId));
            } else {
                re.setVisibility(View.GONE);
            }
        }

    }

    /**
     * @param title
     * @param listener 设置标题
     */
    public void setTopBarTitle(String title, View.OnClickListener listener) {
        TextView tv = (TextView) findViewById(R.id.topbar_title);
        if (tv != null) {
            tv.setText(title);
            tv.setOnClickListener(listener);
        }
    }

    /**
     * 线程调度
     * 网络请求与view生命周期绑定 防止内存泄漏
     */
    public <T> ObservableTransformer<T, T> compose(final LifecycleTransformer<T> lifecycle) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                // 可添加网络连接判断等
                                if (!NetUtil.isConnected(BaseActivity.this)) {
                                    Toast.makeText(BaseActivity.this, R.string.toast_network_error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(lifecycle);
            }
        };
    }

    /**
     * @param rid      图案
     * @param textid   文字
     * @param listener 监听
     *                 设置左边按钮
     */
    public void setTopbarLeftbtn(int rid, int textid, View.OnClickListener listener) {
        Button btn = (Button) findViewById(R.id.topbar_leftbtn);
        if (btn != null) {
            if (textid > 0) {
                btn.setText(textid);
            }
            if (rid > 0) {
                btn.setCompoundDrawablesWithIntrinsicBounds(getResources()
                        .getDrawable(rid), null, null, null);
            }
            btn.setOnClickListener(listener);
        }
    }

    /**
     * 设置左边按钮返回
     */
    public void setTopbarLeftBackBtn() {
        this.setTopbarLeftbtn(R.mipmap.back, 0,
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        //得到InputMethodManager的实例
                        if (imm.isActive()) {
                            //如果开启
                            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                                    0);
                            //关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的

                        }
                        finish();
                    }
                });
    }

    /**
     * 设置左边按钮返回
     */
    public void setTopbarLeftWhiteBackBtn() {
        this.setTopbarLeftbtn(R.mipmap.back_white, 0,
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        //得到InputMethodManager的实例
                        if (imm.isActive()) {
                            //如果开启
                            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                                    0);
                            //关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的

                        }
                        finish();
                    }
                });
    }

    /**
     * 设置左边按钮返回
     */
    public void setTopbarLeftWhiteToMainBtn() {
        this.setTopbarLeftbtn(R.mipmap.back_white, 0,
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        //得到InputMethodManager的实例
                        if (imm.isActive()) {
                            //如果开启
                            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                                    0);
                            //关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的

                        }
                        MyApplication.getInstance().gotoMainActivity();
                    }
                });
    }

    /**
     * 设置返回
     */
    public void setBack() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //得到InputMethodManager的实例
        if (imm.isActive()) {
            //如果开启
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                    0);
            //关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的

        }
        finish();
    }

    /**
     * @param str 弹出吐司
     */
    public void showToast(String str) {
        Toast toast = new Toast(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.toast_default_style, null);
        TextView content = (TextView) view.findViewById(R.id.toast_content_tv);
        content.setText(str);
        view.getBackground().setAlpha(150);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public void showToast(int strid) {
        Toast toast = new Toast(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.toast_default_style, null);
        TextView content = (TextView) view.findViewById(R.id.toast_content_tv);
        content.setText(strid);
        view.getBackground().setAlpha(150);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * @param rid
     * @param textid
     * @param listener 设置右边按钮
     */
    public void setTopbarRightbtn(int rid, int textid, View.OnClickListener listener) {
        Button btn = (Button) findViewById(R.id.topbar_rightbtn);
        if (btn != null) {
            if (textid > 0) {
                btn.setText(textid);
            }
            if (rid > 0) {
                btn.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        getResources().getDrawable(rid), null);
            }
            btn.setOnClickListener(listener);
        }
    }

    public void setTopbarRightbtnGone() {
        Button btn = (Button) findViewById(R.id.topbar_rightbtn);
        btn.setVisibility(View.GONE);
    }

    public void showProgressDlg() {
        if (mProgressDialog != null) {
            if (mProgressDialog.isShowing()) {
                return;
            } else {
                mProgressDialog.show();
                progress.startLoad();
                return;
            }
        }

        mProgressDialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        mProgressDialog.getWindow().setBackgroundDrawableResource(
                R.color.transparent);

        View view = getLayoutInflater().inflate(R.layout.loading_layout, null);
        progress = (AppLoadingView) view.findViewById(R.id.loading);
        progress.startLoad();
        mProgressDialog.setContentView(view);
        mProgressDialog.show();
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    public void removeProgressDlg() {
        if (mProgressDialog != null) {
            progress.stopLoad();
            mProgressDialog.dismiss();
        }
    }

    /**
     * @param url          跳转网址
     * @param title        标题
     * @param isTopBarBack webview是否有返回键
     *                     跳转到webView
     */
    public void openWebView(String url, String title, boolean isTopBarBack) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        intent.putExtra("isTopBarBack", isTopBarBack);
        startActivity(intent);
    }

    /**
     * @param msg
     * @param pTitleId       右边按钮
     * @param pClickListener
     * @param cTitleId
     * @param cClickListener
     * @param cancel
     */
    public void showAlertDlg(String msg, int pTitleId,
                             View.OnClickListener pClickListener, int cTitleId,
                             View.OnClickListener cClickListener, boolean cancel) {
        AlertDialog dialog = new AlertDialog.Builder(BaseActivity.this)
                .setCancelable(cancel).create();
        dialog.setInverseBackgroundForced(true);
        dialog.show();
        dialog.getWindow().setContentView(R.layout.dialog_prompt);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        TextView dialog_prompt_content = (TextView) dialog.getWindow().findViewById(R.id.dialog_prompt_content);
        TextView dialog_prompt_false = (TextView) dialog.getWindow().findViewById(R.id.dialog_prompt_false);
        TextView dialog_prompt_true = (TextView) dialog.getWindow().findViewById(R.id.dialog_prompt_true);
        if (msg.length() > 40) {
            dialog_prompt_content.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        }
        dialog_prompt_content.setText(msg);
        if (pTitleId > 0) {
            dialog_prompt_true.setText(pTitleId);
        } else {
            dialog_prompt_true.setVisibility(View.GONE);
        }
        if (pClickListener != null) {
            dialog_prompt_true.setOnClickListener(pClickListener);
        }
        if (cTitleId > 0) {
            dialog_prompt_false.setText(cTitleId);
        } else {
            dialog_prompt_false.setVisibility(View.GONE);
        }
        if (cClickListener != null) {
            dialog_prompt_false.setOnClickListener(cClickListener);
        }
        mAlertDialog = dialog;
    }


    public void showGreenAlertDlg(String msg, int pTitleId,
                                  View.OnClickListener pClickListener, int cTitleId,
                                  View.OnClickListener cClickListener, boolean cancel) {
        AlertDialog dialog = new AlertDialog.Builder(BaseActivity.this)
                .setCancelable(cancel).create();
        dialog.setInverseBackgroundForced(true);
        dialog.show();
        dialog.getWindow().setContentView(R.layout.green_dialog_prompt);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        TextView dialog_prompt_content = (TextView) dialog.getWindow().findViewById(R.id.dialog_prompt_content);
        TextView dialog_prompt_false = (TextView) dialog.getWindow().findViewById(R.id.dialog_prompt_false);
        TextView dialog_prompt_true = (TextView) dialog.getWindow().findViewById(R.id.dialog_prompt_true);
        if (msg.length() > 40) {
            dialog_prompt_content.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        }
        dialog_prompt_content.setText(msg);
        if (pTitleId > 0) {
            dialog_prompt_true.setText(pTitleId);
        } else {
            dialog_prompt_true.setVisibility(View.GONE);
        }
        if (pClickListener != null) {
            dialog_prompt_true.setOnClickListener(pClickListener);
        }
        if (cTitleId > 0) {
            dialog_prompt_false.setText(cTitleId);
        } else {
            dialog_prompt_false.setVisibility(View.GONE);
        }
        if (cClickListener != null) {
            dialog_prompt_false.setOnClickListener(cClickListener);
        }
        mAlertDialog = dialog;
    }

    public void removeAlertDlg() {
        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
        }
    }

    /**
     * @param msg
     * @param pTitleId       右边按钮
     * @param pClickListener
     * @param cTitleId
     * @param cClickListener
     * @param cancel
     */
    public void showLuxuryAlertDlg(String msg, int pTitleId,
                                   View.OnClickListener pClickListener, int cTitleId,
                                   View.OnClickListener cClickListener, boolean cancel) {
        AlertDialog dialog = new AlertDialog.Builder(BaseActivity.this)
                .setCancelable(cancel).create();
        dialog.setInverseBackgroundForced(true);
        dialog.show();
        dialog.getWindow().setContentView(R.layout.luxury_dialog_prompt);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        TextView dialog_prompt_content = (TextView) dialog.getWindow().findViewById(R.id.dialog_prompt_content);
        TextView dialog_prompt_false = (TextView) dialog.getWindow().findViewById(R.id.dialog_prompt_false);
        TextView dialog_prompt_true = (TextView) dialog.getWindow().findViewById(R.id.dialog_prompt_true);
        if (msg.length() > 40) {
            dialog_prompt_content.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        }
        dialog_prompt_content.setText(msg);
        if (pTitleId > 0) {
            dialog_prompt_true.setText(pTitleId);
        } else {
            dialog_prompt_true.setVisibility(View.GONE);
        }
        if (pClickListener != null) {
            dialog_prompt_true.setOnClickListener(pClickListener);
        }
        if (cTitleId > 0) {
            dialog_prompt_false.setText(cTitleId);
        } else {
            dialog_prompt_false.setVisibility(View.GONE);
        }
        if (cClickListener != null) {
            dialog_prompt_false.setOnClickListener(cClickListener);
        }
        mAlertDialog = dialog;
    }


    /**
     * 判断是否去登录
     */
    public void isToLogin() {
        boolean isLogin = SharedData.getInstance().isLogin();
        if (isLogin) {
            handleLogin();
        } else {
            handleNotLogin();
        }
    }

    @Override
    public void handleLogin() {

    }

    @Override
    public void handleNotLogin() {
        startActivityForResult(new Intent(this, LoginActivity.class), LOGIN_REQUEST);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Logger.e("onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.e("onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.e("onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.e("onDestroy");
        MyApplication.getInstance().deleteComponent(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Logger.e("onRestart");
    }
}

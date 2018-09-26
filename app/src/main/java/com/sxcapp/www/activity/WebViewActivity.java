package com.sxcapp.www.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.orhanobut.logger.Logger;
import com.sxcapp.www.R;
import com.sxcapp.www.Util.StatusBarUtil;

/**
 * Created by wenleitao on 2017/8/9.
 */

public class WebViewActivity extends BaseActivity {
    private String url, title;
    private WebView webView;
    private Boolean isTopBarBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        setStatusView(R.color.top_bar_red);
        webView = (WebView) findViewById(R.id.web);
        StatusBarUtil.StatusBarDarkMode(this);
        //如果不设置WebViewClient，请求会跳转系统浏览器
        url = getIntent().getStringExtra("url");
        Logger.e(url);
        title = getIntent().getStringExtra("title");
        isTopBarBack = getIntent().getBooleanExtra("isTopBarBack", true);
        setTopBarTitle(title, null);
        if (isTopBarBack) {
            if ("积分商城".equals(title)) {
                Button btn = (Button) findViewById(R.id.topbar_leftbtn);
                btn.setCompoundDrawablesWithIntrinsicBounds(getResources()
                        .getDrawable(R.mipmap.back_white), null, null, null);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (webView.canGoBack()) {
                            webView.goBack();
                        } else {
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
                    }
                });
            } else {
                setTopbarLeftWhiteBackBtn();
            }
        }

        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String target) {
//                //该方法在Build.VERSION_CODES.LOLLIPOP以前有效，从Build.VERSION_CODES.LOLLIPOP起，建议使用shouldOverrideUrlLoading(WebView, WebResourceRequest)} instead
//                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
//                //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242
//                view.loadUrl(target);
//                return false;
//            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
                return false;
            }

        });


        webView.getSettings().setJavaScriptEnabled(true);
        //方法
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//        webView.addJavascriptInterface(new JsInteration(), "android");
        webView.setWebChromeClient(new WebChromeClient() {

        });
        webView.loadUrl(url);
        if ("会员特权".equals(title)) {
            setTopbarRightbtn(0, R.string.level_grow, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openWebView("106.14.135.110/SxcH5/howtoupgrade.html", "如何升级", true);
                }
            });
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ("积分商城".equals(title)) {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
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
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

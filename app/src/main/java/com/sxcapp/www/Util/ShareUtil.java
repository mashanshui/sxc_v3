package com.sxcapp.www.Util;

import android.app.Activity;
import android.widget.Toast;

import com.sxcapp.www.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * Created by wenleitao on 2016/12/20.
 */

public class ShareUtil {
    private Activity activity;

    public ShareUtil(Activity activity) {
        this.activity = activity;
    }

    //    public void shareTaskDetail(String shareContent, String shareIconUrl, String shareTitle, String shareWebsiteUrl) {
//        // TODO Auto-generated method stub
//
//        shareConfig(shareContent, shareIconUrl, shareTitle, shareWebsiteUrl);
//
//    }
    public void shareTaskDetail(String title,String content,String target_url) {
        // TODO Auto-generated method stub

        shareConfig(title, content, target_url);

    }

    public void shareConfig(String title, String content, String target_url) {


        final UMImage localImg;

        localImg = new UMImage(activity,
                R.mipmap.icon);


        UMWeb web = new UMWeb(target_url);
        web.setTitle(title);//标题
        web.setThumb(localImg);  //缩略图
        web.setDescription(content);//描述

        new ShareAction(activity).setDisplayList(SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
                .withMedia(web)
                .setCallback(shareListener)
                .open();
    }


    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(activity, "成功了", Toast.LENGTH_SHORT).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(activity, "失败" + t.getMessage(), Toast.LENGTH_SHORT).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(activity, "取消了", Toast.LENGTH_SHORT).show();

        }
    };


}

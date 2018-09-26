package com.sxcapp.www.UserCenter;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.R;
import com.sxcapp.www.Share.Bean.ImageBeanV3;
import com.sxcapp.www.Share.HttpService.ShareService;
import com.sxcapp.www.UserCenter.HttpService.FileUploadService;
import com.sxcapp.www.Util.FileUtils;
import com.sxcapp.www.Util.ImageTool;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManagerV3;

import java.io.File;
import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


/**
 * Created by wenleitao on 2017/9/16.
 */

public class UploadCreditReportActivityV3 extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.one_iv)
    ImageView one_iv;
    @BindView(R.id.two_iv)
    ImageView two_iv;
    @BindView(R.id.three_iv)
    ImageView three_iv;
    @BindView(R.id.four_iv)
    ImageView four_iv;
    @BindView(R.id.commit_btn)
    Button commit_btn;
    @BindView(R.id.take_photo_iv01)
    ImageView take_photo_iv01;
    @BindView(R.id.take_photo_iv02)
    ImageView take_photo_iv02;
    @BindView(R.id.take_photo_iv03)
    ImageView take_photo_iv03;
    @BindView(R.id.take_photo_iv04)
    ImageView take_photo_iv04;
    private String fileName;
    private String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + File.separator;
    private static final int ONE_REQUEST = 11, TWO_REQUEST = 12, THREE_REQUEST = 13, FOUR_REQUEST = 14;
    private FileUploadService service;
    private ShareService shareService;
    private String one_image, one_image_key, two_image, two_image_key, three_image, three_image_key, four_image, four_image_key, user_id;
    private String type;
    private int CAMERA_REQUEST;
    private String image_str, image_key;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_credit_report_v3);
        setTopbarLeftWhiteBackBtn();
        setStatusView(R.color.luxury);
        setTopBarColor(R.color.luxury);
        StatusBarUtil.StatusBarDarkMode(this);
        setTopBarTitle("豪车征信报告审核", null);
        ButterKnife.bind(this);
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(FileUploadService.class);
        shareService = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(ShareService.class);
        one_iv.setOnClickListener(this);
        two_iv.setOnClickListener(this);
        three_iv.setOnClickListener(this);
        four_iv.setOnClickListener(this);
        commit_btn.setOnClickListener(this);
        type = "creditReport";

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.one_iv:
                CAMERA_REQUEST = ONE_REQUEST;
                getPermission();
                break;
            case R.id.two_iv:
                CAMERA_REQUEST = TWO_REQUEST;
                getPermission();
                break;
            case R.id.three_iv:
                CAMERA_REQUEST = THREE_REQUEST;
                getPermission();
                break;
            case R.id.four_iv:
                CAMERA_REQUEST = FOUR_REQUEST;
                getPermission();
                break;
            case R.id.commit_btn:
                if (TextUtils.isEmpty(one_image)) {
                    showToast("请上传第一页照片");
                    return;
                } else if (TextUtils.isEmpty(two_image)) {
                    showToast("请上传第二页照片");
                    return;
                } else if (TextUtils.isEmpty(three_image)) {
                    showToast("请上传第三页照片");
                    return;
                } else if (TextUtils.isEmpty(four_image)) {
                    showToast("请上传第四页照片");
                    return;
                } else {
                    image_str = one_image + "," + two_image + "," + three_image + "," + four_image;
                    image_key = one_image_key + "," + two_image_key + "," + three_image_key + "," + four_image_key;
                    uploadReportInfo();
                }
                break;
            default:
                break;

        }
    }

    private void uploadReportInfo() {
        showProgressDlg();
        Observable<CodeResultV3<Object>> ob = shareService.uploadCreditReportImageV3(SharedData.getInstance().getString(SharedData._user_id), image_str, image_key);
        ob.compose(compose(this.<CodeResultV3<Object>>bindToLifecycle())).subscribe(new CodeObserverV3<Object>(this) {
            @Override
            protected void onHandleSuccess(Object o) {
                removeProgressDlg();
                showToast("上传成功");
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }


    public void goCamera(int requestcode) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
        if (Build.VERSION.SDK_INT > 23) {
            FileUtils.startActionCapture(this, new File(path + fileName), requestcode);

        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path + fileName)));
            startActivityForResult(intent, requestcode);
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

                Luban.with(this)
                        .load(path + fileName)
                        .ignoreBy(100)
                        .setTargetDir(ImageTool.getPath())
                        .setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {
                            }

                            @Override
                            public void onSuccess(File file) {
                                uploadImage(requestCode, file.getAbsolutePath());
                                deleteImage();
                            }

                            @Override
                            public void onError(Throwable e) {
                            }
                        }).launch();
            }

    }


    public void deleteImage() {
        File file = new File(path + fileName);
        file.delete();
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = UploadCreditReportActivityV3.this.getContentResolver();
        String where = MediaStore.Images.Media.DATA + "='" + path + fileName + "'";
//删除图片
        mContentResolver.delete(mImageUri, where, null);
//发送广播
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        UploadCreditReportActivityV3.this.sendBroadcast(intent);
    }

    public void uploadImage(final int code, final String url) {
        showProgressDlg();
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/jpg"), new File(url));
        Observable<CodeResultV3<ImageBeanV3>> observable = service.uploadImageV3(type, requestFile);
        observable.compose(compose(this.<CodeResultV3<ImageBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<ImageBeanV3>(this) {
            @Override
            protected void onHandleSuccess(ImageBeanV3 imageBean) {
                removeProgressDlg();
                switch (code) {
                    case ONE_REQUEST:
                        one_image = imageBean.getSave_path();
                        one_image_key = imageBean.getView_path();
                        take_photo_iv01.setVisibility(View.GONE);
                        Glide.with(UploadCreditReportActivityV3.this).load(url).into(one_iv);
                        break;
                    case TWO_REQUEST:
                        two_image = imageBean.getSave_path();
                        two_image_key = imageBean.getView_path();
                        take_photo_iv02.setVisibility(View.GONE);
                        Glide.with(UploadCreditReportActivityV3.this).load(url).into(two_iv);
                        break;
                    case THREE_REQUEST:
                        three_image = imageBean.getSave_path();
                        three_image_key = imageBean.getView_path();
                        take_photo_iv03.setVisibility(View.GONE);
                        Glide.with(UploadCreditReportActivityV3.this).load(url).into(three_iv);
                        break;
                    case FOUR_REQUEST:
                        four_image = imageBean.getSave_path();
                        four_image_key = imageBean.getView_path();
                        take_photo_iv04.setVisibility(View.GONE);
                        Glide.with(UploadCreditReportActivityV3.this).load(url).into(four_iv);
                        break;
                    default:
                        break;
                }
            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<ImageBeanV3> value) {
                super.onHandleError(msg, value);
            }

            @Override
            public void onError(Throwable e) {
                removeProgressDlg();
                showToast("上传失败");
            }
        });
    }


    public void getPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            PermissionGen.with(UploadCreditReportActivityV3.this)
                    .addRequestCode(100)
                    .permissions(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    .request();
        } else {
            goCamera(CAMERA_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 100)
    public void doSomething() {
        goCamera(CAMERA_REQUEST);
    }


    @PermissionFail(requestCode = 100)
    public void doFailSomething() {
        showAlertDlg("使用拍照功能，请开启拍照和读取手机储存权限", R.string.set, new View.OnClickListener() {
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
}

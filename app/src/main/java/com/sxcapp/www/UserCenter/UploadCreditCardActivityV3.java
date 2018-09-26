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
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.R;
import com.sxcapp.www.Share.Bean.ImageBeanV3;
import com.sxcapp.www.Share.HttpService.ShareService;
import com.sxcapp.www.Share.UploadCarInfoActivityV3;
import com.sxcapp.www.UserCenter.HttpService.FileUploadService;
import com.sxcapp.www.UserCenter.HttpService.UserCenterService;
import com.sxcapp.www.Util.AndroidTool;
import com.sxcapp.www.Util.FileUtils;
import com.sxcapp.www.Util.ImageTool;
import com.sxcapp.www.Util.MessageEvent;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManagerV3;

import org.greenrobot.eventbus.EventBus;

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
 * Created by wenleitao on 2018/4/26.
 */

public class UploadCreditCardActivityV3 extends BaseActivity {
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.upload_btn)
    Button upload_btn;
    @BindView(R.id.card_edit)
    EditText card_edit;
    @BindView(R.id.delete_iv)
    ImageView delete_iv;
    @BindView(R.id.take_photo_iv)
    ImageView take_photo_iv;
    private String image_str, image_str_key, card_number, user_id;
    private UserCenterService service;
    private FileUploadService uploadService;
    private static final int IMAGE_REQUEST = 102;
    public static final int CAMERA_REQUEST = 11;
    private String fileName;
    private String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + File.separator;
    private int phoneWidth, phoneHeight;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_creditcard_v3);
        ButterKnife.bind(this);
        setTopbarLeftWhiteBackBtn();
        setTopBarColor(R.color.top_bar_red);
        setStatusView(R.color.top_bar_red);
        setTopBarTitle("豪车信用卡审核", null);
        StatusBarUtil.StatusBarDarkMode(this);
        user_id = SharedData.getInstance().getString(SharedData._user_id);
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
        uploadService = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(FileUploadService.class);
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        phoneWidth = dm.widthPixels;
        phoneHeight = dm.heightPixels;
        int width = phoneWidth - AndroidTool.dip2px(this, 12) * 2;

        iv.setLayoutParams(new RelativeLayout.LayoutParams(width, (int) (width * 0.62)));
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPermission();
            }
        });
        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(image_str)) {
                    showToast("请先上传照片");
                } else if (TextUtils.isEmpty(card_edit.getText().toString().trim())) {
                    showToast("请输入信用卡号");
                } else if (!AndroidTool.checkBankCard(card_edit.getText().toString().trim())) {
                    showToast("请输入正确的信用卡号");
                } else {
                    card_number = card_edit.getText().toString().trim();
                    uploadCreditCard();
                }
            }
        });
        delete_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card_edit.setText("");
            }
        });

    }

    private void uploadCreditCard() {
        showProgressDlg();
        Observable<CodeResultV3<Object>> ob = service.creditCardAuditV3(user_id, card_number, image_str, image_str_key);
        ob.compose(compose(this.<CodeResultV3<Object>>bindToLifecycle())).subscribe(new CodeObserverV3<Object>(this) {
            @Override
            protected void onHandleSuccess(Object o) {
                removeProgressDlg();
                showToast("提交成功");
                EventBus.getDefault().post(new MessageEvent("success"));
                finish();
            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<Object> value) {
                super.onHandleError(msg, value);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST) {
            setResult(RESULT_OK);
            finish();
        } else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
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
                            uploadImage(file.getAbsolutePath());
                            deleteImage();

                        }

                        @Override
                        public void onError(Throwable e) {
                        }
                    }).launch();

        }
    }

    public void uploadImage(final String url) {
        showProgressDlg();
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/jpg"), new File(url));
        Observable<CodeResultV3<ImageBeanV3>> observable = uploadService.uploadImageV3("creditCard", requestFile);
        observable.compose(compose(this.<CodeResultV3<ImageBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<ImageBeanV3>(this) {
            @Override
            protected void onHandleSuccess(ImageBeanV3 imageBeanV3) {
                removeProgressDlg();
                image_str = imageBeanV3.getSave_path();
                image_str_key = imageBeanV3.getView_path();
                Glide.with(UploadCreditCardActivityV3.this).load(url).into(iv);
                take_photo_iv.setVisibility(View.GONE);
                showToast("上传成功");

            }
        });
    }


    public void getPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            PermissionGen.with(UploadCreditCardActivityV3.this)
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


    public void deleteImage() {
        File file = new File(path + fileName);
        file.delete();
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = UploadCreditCardActivityV3.this.getContentResolver();
        String where = MediaStore.Images.Media.DATA + "='" + path + fileName + "'";
//删除图片
        mContentResolver.delete(mImageUri, where, null);
//发送广播
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        UploadCreditCardActivityV3.this.sendBroadcast(intent);
    }

}

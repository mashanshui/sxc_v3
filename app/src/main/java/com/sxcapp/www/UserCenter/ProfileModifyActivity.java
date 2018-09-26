package com.sxcapp.www.UserCenter;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.CustomerView.CircleTransformt;
import com.sxcapp.www.R;
import com.sxcapp.www.Share.Bean.ImageBeanV3;
import com.sxcapp.www.UserCenter.HttpService.FileUploadService;
import com.sxcapp.www.UserCenter.HttpService.UserCenterService;
import com.sxcapp.www.Util.FileUtils;
import com.sxcapp.www.Util.ImageTool;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.UserInfoMessageEvent;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManagerV3;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import io.reactivex.Observable;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 个人信息修改界面
 * Created by wenleitao on 2017/8/2.
 */

public class ProfileModifyActivity extends BaseActivity implements View.OnClickListener {
    private UserCenterService service;
    private FileUploadService fileUploadService;
    private ImageView avatar_iv;
    private EditText name_edit;
    private RelativeLayout avatar_re;
    private Dialog dialog;
    private String fileName;
    public static final int PHOTORESOULT = 3;// 结果
    public static final int CAMERA_REQUEST = 11;
    public static final int FROM_ALBUM = 16;
    public static final String IMAGE_UNSPECIFIED = "image/*";
    private String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + File.separator;
    private int phoneWidth;
    private int phoneHeight;
    private String imgPath;
    private String head_image = "", head_image_key = "";
    private String user_id, name = "";
    private static final String IMAGE_FILE_LOCATION = "file:///sdcard/temp.jpg";// temp
    // file
    private Uri albumUri = null;
    private RequestOptions options;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_profile);
        setTopbarLeftBackBtn();
        setTopBarTitle("个人资料修改", null);
        initViews();
        FileUtils.init();
        user_id = SharedData.getInstance().getString(SharedData._user_id);
        setTopbarRightbtn(0, R.string.save, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name_edit.getText().toString().trim().isEmpty() && TextUtils.isEmpty(imgPath)) {
                    showToast("请更改头像或者昵称");
                } else {
                    name = name_edit.getText().toString().trim();
                    if (TextUtils.isEmpty(imgPath)) {
                        modify();
                    } else {
                        uploadImage();
                    }
                }
            }
        });
        options = new RequestOptions();
        options.transform(new CircleTransformt(ProfileModifyActivity.this));
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
        fileUploadService = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(FileUploadService.class);

    }

    private void modify() {
        showProgressDlg();
        Observable<CodeResultV3<Object>> observable = service.editUserV3(user_id, name, head_image, head_image_key);
        observable.compose(compose(this.<CodeResultV3<Object>>bindToLifecycle())).subscribe(new CodeObserverV3<Object>(this) {
            @Override
            protected void onHandleSuccess(Object o) {
                if (!TextUtils.isEmpty(imgPath)) {
                    showToast("修改成功");
                    if (!TextUtils.isEmpty(name)) {
                        SharedData.getInstance().set(SharedData._user_name, name);
                        EventBus.getDefault().post(new UserInfoMessageEvent(head_image_key, name));
                    } else {
                        EventBus.getDefault().post(new UserInfoMessageEvent(head_image_key, ""));
                    }
                    SharedData.getInstance().set(SharedData._avatar, head_image_key);
                    Intent intent = new Intent(ProfileModifyActivity.this, UserProfilActivity.class);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                } else {
                    showToast("修改成功");
                    SharedData.getInstance().set(SharedData._user_name, name);
                    EventBus.getDefault().post(new UserInfoMessageEvent("", name));
                    Intent intent = new Intent(ProfileModifyActivity.this, UserProfilActivity.class);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<Object> value) {
                removeProgressDlg();
                super.onHandleError(msg, value);
            }
        });
    }

    private void initViews() {
        avatar_iv = (ImageView) findViewById(R.id.avatar_iv);
        name_edit = (EditText) findViewById(R.id.name_tv);
        avatar_re = (RelativeLayout) findViewById(R.id.avatar_re);
        avatar_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPermission();
            }
        });
        name_edit.setHint("请输入");


    }

    public void showTakePhotoDialog() {
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        View view = LayoutInflater.from(this).inflate(R.layout.popupwindow_photo, null);
        RelativeLayout pop_camera_re = (RelativeLayout) view.findViewById(R.id.pop_camera_re);
        RelativeLayout pop_album_re = (RelativeLayout) view.findViewById(R.id.pop_album_re);
        RelativeLayout pop_cancel_re = (RelativeLayout) view.findViewById(R.id.pop_cancel_relative);
        pop_camera_re.setOnClickListener(this);
        pop_album_re.setOnClickListener(this);
        pop_cancel_re.setOnClickListener(this);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.y = 0;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialogWindow.setWindowAnimations(R.style.mypopwindow_anim_style);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pop_camera_re:
                goCamera();
                dialog.dismiss();
                break;
            case R.id.pop_album_re:
                goAlbums();
                dialog.dismiss();
                break;
            case R.id.pop_cancel_relative:
                dialog.dismiss();
                break;
            default:
                break;
        }
    }

    public void uploadImage() {
        showProgressDlg();
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/jpg"), new File(imgPath));
        Observable<CodeResultV3<ImageBeanV3>> observable = fileUploadService.uploadImageV3("customerHeadImage",
                requestFile);
        observable.compose(compose(this.<CodeResultV3<ImageBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<ImageBeanV3>(this) {
            @Override
            protected void onHandleSuccess(ImageBeanV3 imageBean) {
                deleteImageByPath(imgPath);
                head_image = imageBean.getSave_path();
                head_image_key = imageBean.getView_path();
//                SharedData.getInstance().set(SharedData._user_name, name);
//                SharedData.getInstance().set(SharedData._avatar, imageBean.getView_path());
//                EventBus.getDefault().post(new UserInfoMessageEvent(imageBean.getView_path(), name));
//                Intent intent = new Intent(ProfileModifyActivity.this, UserProfilActivity.class);
//                setResult(Activity.RESULT_OK, intent);
//                finish();
                modify();
            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<ImageBeanV3> value) {
                removeProgressDlg();
                super.onHandleError(msg, value);


            }
        });
    }

    public void deleteImageByPath(String imageUrl) {
        File file = new File(imageUrl);
        file.delete();
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = ProfileModifyActivity.this.getContentResolver();
        String where = MediaStore.Images.Media.DATA + "='" + imageUrl + "'";
//删除图片
        mContentResolver.delete(mImageUri, where, null);
//发送广播
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        ProfileModifyActivity.this.sendBroadcast(intent);
    }

    private void goCamera() {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
        if (Build.VERSION.SDK_INT > 23) {
            FileUtils.startActionCapture(this, new File(path + fileName), CAMERA_REQUEST);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path + fileName)));
            startActivityForResult(intent, CAMERA_REQUEST);
        }
    }

    /**
     * 调用相册
     */
    private void goAlbums() {
        fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT,
                null);
        intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                IMAGE_UNSPECIFIED);
        startActivityForResult(intent, FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST) {
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
                                imgPath = file.getAbsolutePath();
                                Glide.with(ProfileModifyActivity.this).load(imgPath).apply(options).into(avatar_iv);
                                deleteImage();
                            }

                            @Override
                            public void onError(Throwable e) {
                            }
                        }).launch();

            }
        } else if (requestCode == FROM_ALBUM) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Uri uri = data.getData();
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = managedQuery(uri, proj, null, null, null);

                    // 按我个人理解 这个是获得用户选择的图片的索引值
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    // 将光标移至开头 ，这个很重要，不小心很容易引起越界
                    cursor.moveToFirst();
                    // 最后根据索引值获取图片路径
                    final String path = cursor.getString(column_index);
                    Luban.with(this).load(path).ignoreBy(100).setTargetDir(ImageTool.getPath()).setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {


                        }

                        @Override
                        public void onSuccess(File file) {
                            imgPath = file.getAbsolutePath();
                            Glide.with(ProfileModifyActivity.this).load(file.getAbsolutePath()).apply(options).into(avatar_iv);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    }).launch();


                }

            }
        }

    }


    public void deleteImage() {
        File file = new File(path + fileName);
        file.delete();
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = ProfileModifyActivity.this.getContentResolver();
        String where = MediaStore.Images.Media.DATA + "='" + path + fileName + "'";
//删除图片
        mContentResolver.delete(mImageUri, where, null);
//发送广播
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        ProfileModifyActivity.this.sendBroadcast(intent);
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }


    public void getPermission() {

        if (Build.VERSION.SDK_INT >= 23) {
            PermissionGen.with(ProfileModifyActivity.this)
                    .addRequestCode(100)
                    .permissions(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    .request();
        } else {
           showTakePhotoDialog();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 100)
    public void doSomething() {
        showTakePhotoDialog();
    }


    @PermissionFail(requestCode = 100)
    public void doFailSomething() {
        showAlertDlg("使用相册和拍照功能，请开启拍照和读取手机储存权限", R.string.set, new View.OnClickListener() {
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

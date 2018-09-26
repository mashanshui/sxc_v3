package com.sxcapp.www.Util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.orhanobut.logger.Logger;
import com.sxcapp.www.Share.UploadCarInfoActivityV3;
import com.sxcapp.www.UserCenter.ProfileModifyActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ImageTool {

    public static Bitmap rotate(Bitmap b, float degree) {
        if (b == null) {
            return null;
        }
        int w = b.getWidth();
        int h = b.getHeight();
        float ratio = 1;

        Matrix m = new Matrix();

        m.setScale(ratio, ratio);
        m.postRotate(degree);
        int x = 0;
        int y = 0;
//		if(w <= h) {
//			y = (h - w) / 2;
//			h = w;
//		} else {
//			x = (w - h) / 2;
//			w = h;
//		}
        Bitmap tmp = Bitmap.createBitmap(b, x, y, w, h, m, true);
        b.recycle();
        return tmp;
    }

    public static Bitmap rotate(Bitmap b, float degree, double size) {
        if (b == null) {
            return null;
        }
        int w = b.getWidth();
        int h = b.getHeight();
        int min = Math.min(w, h);
        float ratio = (float) size / (float) min;

        Matrix m = new Matrix();

        m.setScale(ratio, ratio);
        m.postRotate(degree);
        int x = 0;
        int y = 0;
//		if(w <= h) {
//			y = (h - w) / 2;
//			h = w;
//		} else {
//			x = (w - h) / 2;
//			w = h;
//		}
        Bitmap tmp = Bitmap.createBitmap(b, x, y, w, h, m, true);
        b.recycle();
        return tmp;
    }

    public static String saveCamera(Bitmap theBmp, boolean recycle) {
        byte[] data = null;
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            theBmp.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            data = bytes.toByteArray();
            bytes.close();
            if (recycle) {
                theBmp.recycle();
            }
        } catch (Exception e) {
            if (theBmp != null) {
                theBmp.recycle();
            }
            theBmp = null;
            return null;
        }
        //make URI to save image
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
        String filename = timeStampFormat.format(new Date());
        filename = filename + ".jpg";
        String filepath = AndroidTool.getAlbumDir() + "/" + filename;

        File f = new File(filepath);
        OutputStream os;
        try {
            os = new FileOutputStream(f);
            os.write(data);
            os.close();
        } catch (Throwable t) {
            // TODO Auto-generated catch block
            t.printStackTrace();
        }

        //release memory
        data = null;
        System.gc();
        return filepath;
    }

    public static void deleteImage(String path, Activity activity) {
        File file = new File(path);
        file.delete();
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = activity.getContentResolver();
        String where = MediaStore.Images.Media.DATA + "='" + path + "'";
//删除图片
        mContentResolver.delete(mImageUri, where, null);
//发送广播
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        activity.sendBroadcast(intent);
    }

    public static String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/Luban/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    public static String saveSplash(Bitmap theBmp, String name, boolean recycle) {
        byte[] data = null;
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            theBmp.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            data = bytes.toByteArray();
            bytes.close();
            if (recycle) {
                theBmp.recycle();
            }
        } catch (Exception e) {
            if (theBmp != null) {
                theBmp.recycle();
            }
            theBmp = null;
            return null;
        }
        //make URI to save image
        String filename = name;
        filename = filename + ".jpg";
        String filepath = AndroidTool.GetImageCacheDir() + "/" + filename;

        File f = new File(filepath);
        OutputStream os;
        try {
            os = new FileOutputStream(f);
            os.write(data);
            os.close();
        } catch (Throwable t) {
            // TODO Auto-generated catch block
            t.printStackTrace();
        }

        //release memory
        data = null;
        System.gc();
        return filepath;
    }


    public static Bitmap getBitmapFromFile(String fileName) {
        Bitmap imgBitmap = null;

        Log.i("getBitmapFromFile", fileName);

        if (fileName != null) {
            File file = new File(fileName);
            if (file.exists()) {
                Log.e("getBitmapFromFile", "exists");
            } else {
                Log.e("getBitmapFromFile", "not exists");
                return null;
            }

            try {
                imgBitmap = BitmapFactory.decodeFile(fileName);
            } catch (Exception e) {
                Log.i("getBitmapFromFile", e.toString());
                if (imgBitmap != null) {
                    imgBitmap.recycle();
                }
                return null;
            } catch (OutOfMemoryError oome) {
                com.orhanobut.logger.Logger.e("oom啦");
                if (imgBitmap != null && !imgBitmap.isRecycled()) {
                    imgBitmap.recycle();
                }
                imgBitmap = null;
                System.gc();
                imgBitmap = BitmapFactory.decodeFile(fileName);
            }

            int degree = getImageOrientationDegree(fileName);
            if (degree > 0) {
                imgBitmap = rotate(imgBitmap, degree);
            }
        }

        return imgBitmap;
    }

    public static Bitmap getBitmapFromFile(String fileName, double sizeWidth) {
        Bitmap imgBitmap = getBitmapFromFile2(fileName, sizeWidth);
        if (imgBitmap == null) {
            return null;
        }

        Bitmap scaled = null;

        int h = imgBitmap.getHeight();
        int w = imgBitmap.getWidth();

        if ((h <= (int) sizeWidth) && (w <= (int) sizeWidth)) {
            scaled = imgBitmap;
        } else {
            if (w >= h) {
                double ratio = (double) sizeWidth / (double) w;
                w = (int) sizeWidth;
                h = (int) (ratio * h);
            } else {
                double ratio = (double) sizeWidth / (double) h;
                h = (int) sizeWidth;
                w = (int) (ratio * w);
            }

            scaled = Bitmap.createScaledBitmap(imgBitmap, w, h, true);
            imgBitmap.recycle();
            imgBitmap = null;
        }

        return scaled;
    }

    public static Bitmap getBitmapFromFile2(String fileName, double sizeWidth) {
        Bitmap imgBitmap = null;

        Log.i("getBitmapFromFile", fileName);

        if (fileName != null) {
            File file = new File(fileName);
            if (file.exists()) {
                Log.e("getBitmapFromFile", "exists");
            } else {
                Log.e("getBitmapFromFile", "not exists");
                return null;
            }

            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inJustDecodeBounds = true;

            try {
                BitmapFactory.decodeFile(fileName, opt);
            } catch (Exception e) {
                Log.i("getBitmapFromFile", e.toString());
                return null;
            }

            int width = opt.outWidth;
            int height = opt.outHeight;
            int sampleFactor = 1;

            Log.i("getBitmapFromFile", "width=" + width + " height=" + height);

            if (width >= height) {
                sampleFactor = (int) Math.round(width / sizeWidth);
            } else {
                sampleFactor = (int) Math.round(height / sizeWidth);
            }

            if (sampleFactor < 1) {
                sampleFactor = 1;
            }

            opt.inSampleSize = sampleFactor;
            opt.inJustDecodeBounds = false;

            try {
                imgBitmap = BitmapFactory.decodeFile(fileName, opt);
            } catch (Exception e) {
                Log.i("getBitmapFromFile", e.toString());
                if (imgBitmap != null) {
                    imgBitmap.recycle();
                }
                return null;
            } catch (OutOfMemoryError oome) {
                if (imgBitmap != null && !imgBitmap.isRecycled()) {
                    imgBitmap.recycle();
                }
                imgBitmap = null;
                System.gc();
                imgBitmap = BitmapFactory.decodeFile(fileName, opt);
            }

            int degree = getImageOrientationDegree(fileName);
            if (degree > 0) {
                imgBitmap = rotate(imgBitmap, degree);
            }
        }

        return imgBitmap;
    }

    public static void removeBitmapFromFile(String filePath) {
        File f = new File(filePath);

        if (f.exists() && !f.isDirectory()) {
            f.delete();
        }
    }

    public static int getImageOrientationDegree(String filePath) {
        int degree = 0;
        ExifInterface exifInterface;
        try {
            exifInterface = new ExifInterface(filePath);
            int tag = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            if (tag == ExifInterface.ORIENTATION_ROTATE_90) {
                degree = 90;
            } else if (tag == ExifInterface.ORIENTATION_ROTATE_180) {
                degree = 180;
            } else if (tag == ExifInterface.ORIENTATION_ROTATE_270) {
                degree = 270;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return degree;
    }


    /**
     * 处理旋转后的图片
     *
     * @param originpath 原图路径
     * @return 返回修复完毕后的图片路径
     */
    public static String amendRotatePhoto(String originpath, Bitmap bmp, boolean recycle) {

        // 取得图片旋转角度
        int angle = getImageOrientationDegree(originpath);
        // 修复图片被旋转的角度
        Bitmap bitmap = rotaingImageView(angle, bmp);
        Logger.e("旋转后大小:" + bitmap.getByteCount());

        // 保存修复后的图片并返回保存后的图片路径
        return saveCamera(bitmap, recycle);
    }

    /**
     * 旋转图片
     *
     * @param angle  被旋转角度
     * @param bitmap 图片对象
     * @return 旋转后的图片
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        Bitmap returnBm = null;
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            Logger.e("旋转后的尺寸" + bitmap.getByteCount());
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bitmap;
        }
        if (bitmap != returnBm) {
            bitmap.recycle();
        }
        return returnBm;
    }
    public static Bitmap decodeUriAsBitmap(Uri uri, Activity activity) {

        Bitmap bitmap;

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inSampleSize = 2;
            bitmap = BitmapFactory.decodeStream(activity.getContentResolver()
                    .openInputStream(uri), null, options);
            Logger.e("原始尺寸" + bitmap.getByteCount());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return bitmap;

    }


    public static String getImagePathFromURI(Context thisContext, Uri imgUri) {

        // can post image
        String fileName = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = thisContext.getContentResolver().query(imgUri,
                proj, // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)

        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            if (column_index < cursor.getColumnCount()) {
                fileName = cursor.getString(column_index);
            }
            cursor.close();
        }

        return fileName;
    }

    public static Bitmap getBitmapFromUri(Context thisContext, Uri imgUri) {
        Uri stringUri = Uri.parse(imgUri.toString());
        Bitmap imgBitmap = null;

        String fileName = getImagePathFromURI(thisContext, stringUri);

        Log.i("getBitmapFromUri", fileName);

        if (fileName != null) {
            imgBitmap = getBitmapFromFile(fileName);

            int degree = getImageOrientationDegree(fileName);
            if (degree > 0) {
                imgBitmap = rotate(imgBitmap, degree);
            }
            return imgBitmap;
        }

        return null;
    }

    public static Bitmap getBitmapFromUri(Context thisContext, Uri imgUri, double sizeWidth) {
        Uri stringUri = Uri.parse(imgUri.toString());
        Bitmap imgBitmap = null;

        String fileName = getImagePathFromURI(thisContext, stringUri);

        Log.i("getBitmapFromUri", fileName);

        if (fileName != null) {
            imgBitmap = getBitmapFromFile(fileName);

            int degree = getImageOrientationDegree(fileName);
            if (degree > 0) {
                imgBitmap = rotate(imgBitmap, degree, sizeWidth);
            }
            return imgBitmap;
        }

        return null;
    }

    /**
     * 高斯模糊
     *
     * @param bmp
     * @return
     */
    public static Bitmap convertToBlur(Bitmap bmp) {
        // 高斯矩阵
        int[] gauss = new int[]{1, 2, 1, 2, 4, 2, 1, 2, 1};
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        Bitmap newBmp = Bitmap.createBitmap(width, height,
                Bitmap.Config.RGB_565);
        int pixR = 0;
        int pixG = 0;
        int pixB = 0;
        int pixColor = 0;
        int newR = 0;
        int newG = 0;
        int newB = 0;
        int delta = 16; // 值越小图片会越亮，越大则越暗
        int idx = 0;
        int[] pixels = new int[width * height];
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 1, length = height - 1; i < length; i++) {
            for (int k = 1, len = width - 1; k < len; k++) {
                idx = 0;
                for (int m = -1; m <= 1; m++) {
                    for (int n = -1; n <= 1; n++) {
                        pixColor = pixels[(i + m) * width + k + n];
                        pixR = Color.red(pixColor);
                        pixG = Color.green(pixColor);
                        pixB = Color.blue(pixColor);
                        newR = newR + pixR * gauss[idx];
                        newG = newG + pixG * gauss[idx];
                        newB = newB + pixB * gauss[idx];
                        idx++;
                    }
                }
                newR /= delta;
                newG /= delta;
                newB /= delta;
                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));
                pixels[i * width + k] = Color.argb(255, newR, newG, newB);
                newR = 0;
                newG = 0;
                newB = 0;
            }
        }
        newBmp.setPixels(pixels, 0, width, 0, 0, width, height);
        return newBmp;
    }

    public void deleteImage(Activity activity, String path) {
        File file = new File(path);
        file.delete();
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = activity.getContentResolver();
        String where = MediaStore.Images.Media.DATA + "='" + path + "'";
//删除图片
        mContentResolver.delete(mImageUri, where, null);
//发送广播
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        activity.sendBroadcast(intent);
    }

    public static String getLubanPath() {
        String path = Environment.getExternalStorageDirectory() + "/Luban/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }


}

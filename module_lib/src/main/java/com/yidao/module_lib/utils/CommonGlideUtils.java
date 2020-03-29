package com.yidao.module_lib.utils;
/**
 * Created by zhaolong on 2017/9/29.
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestFutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.yidao.module_lib.R;

import java.io.File;

import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * class infation
 */
public class CommonGlideUtils {

    private static String interceptUrl = "http://media.mxingm.com/upload/2018/05/64f4a24c-e647-4c77-83cd-6c52f7145083.0";

    public static void showImageView(Context context, String url, ImageView imgeview) {
        if(intercept(url)){
            return;
        }
        Glide.with(context).load(url).into(imgeview);
    }

    public static void showImageViewSkipCache(Context context, String url, ImageView imgeview) {
        if(intercept(url)){
            return;
        }
        Glide.with(context).load(url).apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).into(imgeview);
    }

    public static void showImageView(Context context, String url, ImageView imgeview,int errorImg) {
        if(intercept(url)){
            return;
        }
        Glide.with(context).load(url).apply(new RequestOptions().placeholder(errorImg).error(errorImg)).into(imgeview);
    }

    public static void showCirclePhotoSkipCache(Context context, String url, ImageView imageView,int errorImg) {
        if(intercept(url)){
            return;
        }
        Glide.with(context).load(url).apply(RequestOptions.bitmapTransform(new CircleCrop())).apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE).error(errorImg)).into(imageView);
    }


    public static void showCirclePhoto(Context context, String url, ImageView imageView) {
        if(intercept(url)){
            return;
        }
        Glide.with(context).load(url).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageView);
    }

    public static void showImageCorner(Context context, String url, ImageView imgeview,int pxValue) {
        if(intercept(url)){
            return;
        }
        Glide.with(context).load(url).apply(RequestOptions.bitmapTransform(new RoundedCorners(pxValue))).into(imgeview);
    }

    public static void showImageCorner(Context context, Uri uri, ImageView imgeview, int pxValue) {
        Glide.with(context).load(uri).apply(RequestOptions.bitmapTransform(new RoundedCorners(pxValue))).into(imgeview);
    }

    public static void showImageCorner(Context context, String url, ImageView imgeview, int pxValue,int errorImg) {
        Glide.with(context).load(url).apply(RequestOptions.bitmapTransform(new RoundedCorners(pxValue)).placeholder(errorImg).error(errorImg)).into(imgeview);
    }

    public static void showCirclePhoto(final Context context, String url, final ImageView imageView, int errorImg) {
        if(intercept(url)){
            return;
        }
        if (TextUtils.isEmpty(url)) {
            Glide.with(context).load(errorImg).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageView);
            return;
        }
        Glide.with(context).load(url).apply(RequestOptions.bitmapTransform(new CircleCrop()).placeholder(errorImg).error(errorImg)).into(imageView);
    }

    public static void lubanCompress(Context context, String path, final ICompressListener compressListener){
        Luban.with(context)
                .load(path)
                .ignoreBy(200)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        if(compressListener!=null){
                            compressListener.compressSuccess(file);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                    }
                }).launch();
    }

    public static interface ICompressListener{
        void compressSuccess(File file);
    }

    private static boolean intercept(String url){
        if(TextUtils.equals(interceptUrl,url)){
            return true;
        }
        return false;
    }

    private static RoundedBitmapDrawable bindCircleBitmapToImageView(Context context,int errorImg){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),errorImg);
        RoundedBitmapDrawable bitmapDrawable =  RoundedBitmapDrawableFactory.create(context.getResources(),bitmap);
        bitmapDrawable.setCircular(true);
        return bitmapDrawable;
    }

}

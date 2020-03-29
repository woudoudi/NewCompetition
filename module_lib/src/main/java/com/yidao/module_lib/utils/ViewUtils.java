package com.yidao.module_lib.utils;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/13
 */
public class ViewUtils {

    public static void countDownText(final TextView textView){
        new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onFinish() {
                textView.setClickable(true);
                textView.setEnabled(true);
                textView.setText("获取");
                cancel();
            }

            @Override
            public void onTick(long millisUntilFinished) {
                textView.setClickable(false);
                textView.setEnabled(false);
                textView.setText(millisUntilFinished / 1000 + "s");
            }
        }.start();
    }

    public static void forbidenFastClick(final View view){
        view.setClickable(false);
        view.setEnabled(false);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setClickable(true);
                view.setEnabled(true);
            }
        },1000);
    }

    public static void setWebview(WebView webview){
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setDisplayZoomControls(false);
        settings.setSupportZoom(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDomStorageEnabled(true);
        webview.setDrawingCacheEnabled(false);
    }
}

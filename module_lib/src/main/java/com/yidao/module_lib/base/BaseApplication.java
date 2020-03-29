package com.yidao.module_lib.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.yidao.module_lib.BuildConfig;


public class BaseApplication extends Application {
    private static BaseApplication mApplication;
    private Thread.UncaughtExceptionHandler sUncaughtExceptionHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;

        if (!BuildConfig.DEBUG) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    //主线程异常拦截
                    while (true) {
                        try {
                            Looper.loop();//主线程的异常会从这里抛出
                        } catch (Throwable e) {

                        }
                    }
                }
            });

            sUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
            //所有线程异常拦截，由于主线程的异常都被我们catch住了，所以下面的代码拦截到的都是子线程的异常
            Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread t, Throwable e) {

                }
            });
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static Context getApplication(){
        return mApplication;
    }
}

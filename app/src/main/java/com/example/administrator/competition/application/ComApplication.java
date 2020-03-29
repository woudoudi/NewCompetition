package com.example.administrator.competition.application;

import com.tencent.rtmp.TXLiveBase;
import com.yidao.module_lib.base.BaseApplication;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;

public class ComApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        JMessageClient.setDebugMode(true);
        JMessageClient.init(this);

        TXLiveBase.setConsoleEnabled(true);


        WXInfo.getInstance().init(this);
    }
}

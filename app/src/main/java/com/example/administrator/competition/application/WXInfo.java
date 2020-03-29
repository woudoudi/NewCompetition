package com.example.administrator.competition.application;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/15
 */
public class WXInfo {

    // APP_ID 替换为你的应用从官方网站申请到的合法appID
    public static final String APP_ID = "wx8c9f6d3071edba08";
    public static final String APP_SECRET = "a48785ce479b2b2081570b9ca7a10975";
    // IWXAPI 是第三方app和微信通信的openApi接口
    private IWXAPI api;
    private static WXInfo mWXInfo;


    public static WXInfo getInstance() {
        if (mWXInfo == null) {
            synchronized (WXInfo.class) {
                if (mWXInfo == null)
                    mWXInfo = new WXInfo();
            }
        }
        return mWXInfo;
    }

    public void init(Context context){
        if (api != null) return;
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(context, APP_ID, true);

        // 将应用的appId注册到微信
        api.registerApp(APP_ID);

        //建议动态监听微信启动广播进行注册到微信
        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // 将该app注册到微信
                api.registerApp(APP_ID);
            }
        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));

    }

    public IWXAPI getApi() {
        return api;
    }

    public void setApi(IWXAPI api) {
        this.api = api;
    }
}

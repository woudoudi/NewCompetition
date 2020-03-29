package com.example.administrator.competition.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.example.http_lib.utils.UserCacheHelper;
import com.example.http_lib.websocket.JWebSocketClient;
import com.yidao.module_lib.utils.LogUtils;

import java.net.URI;

public class WebsocketService extends Service {


    private String url = "ws://yc.leafabs.faith:8181/ws/Message?token=%s";
    private  URI uri;
    public JWebSocketClient client;
    private JWebSocketClientBinder mBinder = new JWebSocketClientBinder();

    //用于Activity和service通讯
    public class JWebSocketClientBinder extends Binder {

        public JWebSocketClient getService() {
            if(client==null){
                try {
                    uri = URI.create(String.format(url,UserCacheHelper.getToken()));
                    LogUtils.d("socketUrl:"+String.format(url,UserCacheHelper.getToken()));
                    client = new JWebSocketClient(uri);
                    client.connect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return client;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}

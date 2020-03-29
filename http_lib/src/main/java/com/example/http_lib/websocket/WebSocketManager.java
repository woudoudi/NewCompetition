package com.example.http_lib.websocket;

import android.annotation.SuppressLint;

import com.yidao.module_lib.utils.LogUtils;

import java.net.URI;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class WebSocketManager {

    private static WebSocketManager sSocketManager = new WebSocketManager();

    private JWebSocketClient mJWebSocketClient;

    public static WebSocketManager getSocketManager() {
        return sSocketManager;
    }

    public void setJWebSocketClient(JWebSocketClient JWebSocketClient) {
        mJWebSocketClient = JWebSocketClient;
    }

    /**
     * 发送byte[]
     */
    @SuppressLint("CheckResult")
    public void send(final byte[] bytes) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                if (mJWebSocketClient != null&&mJWebSocketClient.isOpen()) {
                    mJWebSocketClient.send(bytes);
                    LogUtils.d("websocket send success:"+new String(bytes));
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                    }
                });
    }

    /**
     * 发送text
     */
    @SuppressLint("CheckResult")
    public void send(final String text) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                if (mJWebSocketClient != null&&mJWebSocketClient.isOpen()) {
                    mJWebSocketClient.send(text);
                    LogUtils.d("websocket send success:"+text);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                    }
                });
    }

    /**
     * 断开连接
     */
    public void closeConnect() {
        try {
            if (null != mJWebSocketClient) {
                mJWebSocketClient.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mJWebSocketClient = null;
        }
    }

}

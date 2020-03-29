package com.example.administrator.competition.view;////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.example.administrator.competition.view;
//
//import android.app.Activity;
//import android.app.AppOpsManager;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Binder;
//import android.os.Build;
//import android.os.Build.VERSION;
//import android.os.Bundle;
//import android.provider.Settings;
//import android.text.TextUtils;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.ImageView;
//import android.widget.PopupWindow;
//import android.widget.RelativeLayout;
//import android.widget.Toast;
//
//import com.tencent.liteav.basic.log.TXCLog;
//import com.tencent.liteav.demo.play.R.id;
//import com.tencent.liteav.demo.play.R.layout;
//import com.tencent.liteav.demo.play.SuperPlayerGlobalConfig;
//import com.tencent.liteav.demo.play.SuperPlayerGlobalConfig.TXRect;
//import com.tencent.liteav.demo.play.SuperPlayerModel;
//import com.tencent.liteav.demo.play.SuperPlayerModel.SuperPlayerURL;
//import com.tencent.liteav.demo.play.bean.TCPlayImageSpriteInfo;
//import com.tencent.liteav.demo.play.bean.TCVideoQuality;
//import com.tencent.liteav.demo.play.controller.IControllerCallback;
//import com.tencent.liteav.demo.play.controller.TCControllerFloat;
//import com.tencent.liteav.demo.play.controller.TCControllerFullScreen;
//import com.tencent.liteav.demo.play.controller.TCControllerWindow;
//import com.tencent.liteav.demo.play.net.TCLogReport;
//import com.tencent.liteav.demo.play.protocol.IPlayInfoProtocol;
//import com.tencent.liteav.demo.play.protocol.IPlayInfoRequestCallback;
//import com.tencent.liteav.demo.play.protocol.TCPlayInfoParams;
//import com.tencent.liteav.demo.play.protocol.TCPlayInfoProtocolV2;
//import com.tencent.liteav.demo.play.utils.TCImageUtil;
//import com.tencent.liteav.demo.play.utils.TCNetWatcher;
//import com.tencent.liteav.demo.play.utils.TCUrlUtil;
//import com.tencent.liteav.demo.play.utils.TCVideoQualityUtil;
//import com.tencent.liteav.demo.play.view.TCDanmuView;
//import com.tencent.rtmp.ITXLivePlayListener;
//import com.tencent.rtmp.ITXVodPlayListener;
//import com.tencent.rtmp.TXBitrateItem;
//import com.tencent.rtmp.TXLivePlayConfig;
//import com.tencent.rtmp.TXLivePlayer;
//import com.tencent.rtmp.TXLivePlayer.ITXSnapshotListener;
//import com.tencent.rtmp.TXVodPlayConfig;
//import com.tencent.rtmp.TXVodPlayer;
//import com.tencent.rtmp.ui.TXCloudVideoView;
//
//import java.io.File;
//import java.lang.reflect.Constructor;
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Iterator;
//import java.util.List;
//
//public class CustomSuperPlayerView extends RelativeLayout implements ITXVodPlayListener, ITXLivePlayListener {
//    private static final String TAG = "CustomSuperPlayerView";
//    private Context mContext;
//    private ViewGroup mRootView;
//    private TXCloudVideoView mTXCloudVideoView;
//    private TCControllerFullScreen mControllerFullScreen;
//    private TCControllerWindow mControllerWindow;
//    private TCControllerFloat mControllerFloat;
//    private TCDanmuView mDanmuView;
//    private LayoutParams mLayoutParamWindowMode;
//    private LayoutParams mLayoutParamFullScreenMode;
//    private android.widget.RelativeLayout.LayoutParams mVodControllerWindowParams;
//    private android.widget.RelativeLayout.LayoutParams mVodControllerFullScreenParams;
//    private WindowManager mWindowManager;
//    private android.view.WindowManager.LayoutParams mWindowParams;
//    private SuperPlayerModel mCurrentModel;
//    private IPlayInfoProtocol mCurrentProtocol;
//    private TXVodPlayer mVodPlayer;
//    private TXVodPlayConfig mVodPlayConfig;
//    private TXLivePlayer mLivePlayer;
//    private TXLivePlayConfig mLivePlayConfig;
//    private CustomSuperPlayerView.OnCustomSuperPlayerViewCallback mPlayerViewCallback;
//    private TCNetWatcher mWatcher;
//    private String mCurrentPlayVideoURL;
//    private int mCurrentPlayType;
//    private int mCurrentPlayMode = 1;
//    private int mCurrentPlayState = 1;
//    private boolean mIsMultiBitrateStream;
//    private boolean mIsPlayWithFileId;
//    private long mReportLiveStartTime = -1L;
//    private long mReportVodStartTime = -1L;
//    private boolean mDefaultQualitySet;
//    private boolean mLockScreen = false;
//    private boolean mChangeHWAcceleration;
//    private int mSeekPos;
//    private long mMaxLiveProgressTime;
//    private CustomSuperPlayerView.PLAYER_TYPE mCurPlayType;
//    private final int OP_SYSTEM_ALERT_WINDOW;
//    private IControllerCallback mControllerCallback;
//
//    public CustomSuperPlayerView(Context context) {
//        super(context);
//        this.mCurPlayType = CustomSuperPlayerView.PLAYER_TYPE.PLAYER_TYPE_NULL;
//        this.OP_SYSTEM_ALERT_WINDOW = 24;
//        this.mControllerCallback = new IControllerCallback() {
//            public void onSwitchPlayMode(int requestPlayMode) {
//                if (CustomSuperPlayerView.this.mCurrentPlayMode != requestPlayMode) {
//                    if (!CustomSuperPlayerView.this.mLockScreen) {
//                        if (requestPlayMode == 2) {
//                            CustomSuperPlayerView.this.fullScreen(true);
//                        } else {
//                            CustomSuperPlayerView.this.fullScreen(false);
//                        }
//
//                        CustomSuperPlayerView.this.mControllerFullScreen.hide();
//                        CustomSuperPlayerView.this.mControllerWindow.hide();
//                        CustomSuperPlayerView.this.mControllerFloat.hide();
//                        if (requestPlayMode == 2) {
//                            if (CustomSuperPlayerView.this.mLayoutParamFullScreenMode == null) {
//                                return;
//                            }
//
//                            CustomSuperPlayerView.this.removeView(CustomSuperPlayerView.this.mControllerWindow);
//                            CustomSuperPlayerView.this.addView(CustomSuperPlayerView.this.mControllerFullScreen, CustomSuperPlayerView.this.mVodControllerFullScreenParams);
//                            CustomSuperPlayerView.this.setLayoutParams(CustomSuperPlayerView.this.mLayoutParamFullScreenMode);
//                            CustomSuperPlayerView.this.rotateScreenOrientation(1);
//                            if (CustomSuperPlayerView.this.mPlayerViewCallback != null) {
//                                CustomSuperPlayerView.this.mPlayerViewCallback.onStartFullScreenPlay();
//                            }
//                        } else {
//                            Intent intent;
//                            if (requestPlayMode == 1) {
//                                if (CustomSuperPlayerView.this.mCurrentPlayMode == 3) {
//                                    try {
//                                        Context viewContext = CustomSuperPlayerView.this.getContext();
//                                        intent = null;
//                                        if (!(viewContext instanceof Activity)) {
//                                            Toast.makeText(viewContext, "悬浮播放失败", Toast.LENGTH_SHORT).show();
//                                            return;
//                                        }
//
//                                        intent = new Intent(CustomSuperPlayerView.this.getContext(), viewContext.getClass());
//                                        CustomSuperPlayerView.this.mContext.startActivity(intent);
//                                        CustomSuperPlayerView.this.pause();
//                                        if (CustomSuperPlayerView.this.mLayoutParamWindowMode == null) {
//                                            return;
//                                        }
//
//                                        CustomSuperPlayerView.this.mWindowManager.removeView(CustomSuperPlayerView.this.mControllerFloat);
//                                        if (CustomSuperPlayerView.this.mCurrentPlayType == 1) {
//                                            CustomSuperPlayerView.this.mVodPlayer.setPlayerView(CustomSuperPlayerView.this.mTXCloudVideoView);
//                                        } else {
//                                            CustomSuperPlayerView.this.mLivePlayer.setPlayerView(CustomSuperPlayerView.this.mTXCloudVideoView);
//                                        }
//
//                                        CustomSuperPlayerView.this.resume();
//                                    } catch (Exception var6) {
//                                        var6.printStackTrace();
//                                    }
//                                } else if (CustomSuperPlayerView.this.mCurrentPlayMode == 2) {
//                                    if (CustomSuperPlayerView.this.mLayoutParamWindowMode == null) {
//                                        return;
//                                    }
//
//                                    CustomSuperPlayerView.this.removeView(CustomSuperPlayerView.this.mControllerFullScreen);
//                                    CustomSuperPlayerView.this.addView(CustomSuperPlayerView.this.mControllerWindow, CustomSuperPlayerView.this.mVodControllerWindowParams);
//                                    CustomSuperPlayerView.this.setLayoutParams(CustomSuperPlayerView.this.mLayoutParamWindowMode);
//                                    CustomSuperPlayerView.this.rotateScreenOrientation(2);
//                                    if (CustomSuperPlayerView.this.mPlayerViewCallback != null) {
//                                        CustomSuperPlayerView.this.mPlayerViewCallback.onStopFullScreenPlay();
//                                    }
//                                }
//                            } else if (requestPlayMode == 3) {
//                                TXCLog.i("CustomSuperPlayerView", "requestPlayMode Float :" + Build.MANUFACTURER);
//                                SuperPlayerGlobalConfig prefs = SuperPlayerGlobalConfig.getInstance();
//                                if (!prefs.enableFloatWindow) {
//                                    return;
//                                }
//
//                                if (VERSION.SDK_INT >= 23) {
//                                    if (!Settings.canDrawOverlays(CustomSuperPlayerView.this.mContext)) {
//                                        intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
//                                        intent.setData(Uri.parse("package:" + CustomSuperPlayerView.this.mContext.getPackageName()));
//                                        CustomSuperPlayerView.this.mContext.startActivity(intent);
//                                        return;
//                                    }
//                                } else if (!CustomSuperPlayerView.this.checkOp(CustomSuperPlayerView.this.mContext, 24)) {
//                                    Toast.makeText(CustomSuperPlayerView.this.mContext, "进入设置页面失败,请手动开启悬浮窗权限", Toast.LENGTH_SHORT).show();
//                                    return;
//                                }
//
//                                CustomSuperPlayerView.this.pause();
//                                CustomSuperPlayerView.this.mWindowManager = (WindowManager)CustomSuperPlayerView.this.mContext.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
//                                CustomSuperPlayerView.this.mWindowParams = new android.view.WindowManager.LayoutParams();
//                                if (VERSION.SDK_INT >= 26) {
//                                    CustomSuperPlayerView.this.mWindowParams.type = 2038;
//                                } else {
//                                    CustomSuperPlayerView.this.mWindowParams.type = 2002;
//                                }
//
//                                CustomSuperPlayerView.this.mWindowParams.flags = 40;
//                                CustomSuperPlayerView.this.mWindowParams.format = -3;
//                                CustomSuperPlayerView.this.mWindowParams.gravity = 51;
//                                TXRect rect = prefs.floatViewRect;
//                                CustomSuperPlayerView.this.mWindowParams.x = rect.x;
//                                CustomSuperPlayerView.this.mWindowParams.y = rect.y;
//                                CustomSuperPlayerView.this.mWindowParams.width = rect.width;
//                                CustomSuperPlayerView.this.mWindowParams.height = rect.height;
//
//                                try {
//                                    CustomSuperPlayerView.this.mWindowManager.addView(CustomSuperPlayerView.this.mControllerFloat, CustomSuperPlayerView.this.mWindowParams);
//                                } catch (Exception var5) {
//                                    Toast.makeText(CustomSuperPlayerView.this.getContext(), "悬浮播放失败", Toast.LENGTH_SHORT).show();
//                                    return;
//                                }
//
//                                TXCloudVideoView videoView = CustomSuperPlayerView.this.mControllerFloat.getFloatVideoView();
//                                if (videoView != null) {
//                                    if (CustomSuperPlayerView.this.mCurrentPlayType == 1) {
//                                        CustomSuperPlayerView.this.mVodPlayer.setPlayerView(videoView);
//                                    } else {
//                                        CustomSuperPlayerView.this.mLivePlayer.setPlayerView(videoView);
//                                    }
//
//                                    CustomSuperPlayerView.this.resume();
//                                }
//
//                                TCLogReport.getInstance().uploadLogs("floatmode", 0L, 0);
//                            }
//                        }
//
//                        CustomSuperPlayerView.this.mCurrentPlayMode = requestPlayMode;
//                    }
//                }
//            }
//
//            public void onBackPressed(int playMode) {
//                switch(playMode) {
//                    case 1:
//                        if (CustomSuperPlayerView.this.mPlayerViewCallback != null) {
//                            CustomSuperPlayerView.this.mPlayerViewCallback.onClickSmallReturnBtn();
//                        }
//
//                        if (CustomSuperPlayerView.this.mCurrentPlayState == 1) {
//                            this.onSwitchPlayMode(3);
//                        }
//                        break;
//                    case 2:
//                        this.onSwitchPlayMode(1);
//                        break;
//                    case 3:
//                        CustomSuperPlayerView.this.mWindowManager.removeView(CustomSuperPlayerView.this.mControllerFloat);
//                        if (CustomSuperPlayerView.this.mPlayerViewCallback != null) {
//                            CustomSuperPlayerView.this.mPlayerViewCallback.onClickFloatCloseBtn();
//                        }
//                }
//
//            }
//
//            public void onFloatPositionChange(int x, int y) {
//                CustomSuperPlayerView.this.mWindowParams.x = x;
//                CustomSuperPlayerView.this.mWindowParams.y = y;
//                CustomSuperPlayerView.this.mWindowManager.updateViewLayout(CustomSuperPlayerView.this.mControllerFloat, CustomSuperPlayerView.this.mWindowParams);
//            }
//
//            public void onPause() {
//                if (CustomSuperPlayerView.this.mCurrentPlayType == 1) {
//                    if (CustomSuperPlayerView.this.mVodPlayer != null) {
//                        CustomSuperPlayerView.this.mVodPlayer.pause();
//                    }
//                } else {
//                    if (CustomSuperPlayerView.this.mLivePlayer != null) {
//                        CustomSuperPlayerView.this.mLivePlayer.pause();
//                    }
//
//                    if (CustomSuperPlayerView.this.mWatcher != null) {
//                        CustomSuperPlayerView.this.mWatcher.stop();
//                    }
//                }
//
//                CustomSuperPlayerView.this.updatePlayState(2);
//            }
//
//            public void onResume() {
//                if (CustomSuperPlayerView.this.mCurrentPlayState == 4) {
//                    if (CustomSuperPlayerView.this.mCurPlayType == CustomSuperPlayerView.PLAYER_TYPE.PLAYER_TYPE_LIVE) {
//                        if (TCUrlUtil.isRTMPPlay(CustomSuperPlayerView.this.mCurrentPlayVideoURL)) {
//                            CustomSuperPlayerView.this.playLiveURL(CustomSuperPlayerView.this.mCurrentPlayVideoURL, 0);
//                        } else if (TCUrlUtil.isFLVPlay(CustomSuperPlayerView.this.mCurrentPlayVideoURL)) {
//                            CustomSuperPlayerView.this.playTimeShiftLiveURL(CustomSuperPlayerView.this.mCurrentModel);
//                            if (CustomSuperPlayerView.this.mCurrentModel.multiURLs != null && !CustomSuperPlayerView.this.mCurrentModel.multiURLs.isEmpty()) {
//                                CustomSuperPlayerView.this.startMultiStreamLiveURL(CustomSuperPlayerView.this.mCurrentPlayVideoURL);
//                            }
//                        }
//                    } else {
//                        CustomSuperPlayerView.this.playVodURL(CustomSuperPlayerView.this.mCurrentPlayVideoURL);
//                    }
//                } else if (CustomSuperPlayerView.this.mCurrentPlayState == 2) {
//                    if (CustomSuperPlayerView.this.mCurrentPlayType == 1) {
//                        if (CustomSuperPlayerView.this.mVodPlayer != null) {
//                            CustomSuperPlayerView.this.mVodPlayer.resume();
//                        }
//                    } else if (CustomSuperPlayerView.this.mLivePlayer != null) {
//                        CustomSuperPlayerView.this.mLivePlayer.resume();
//                    }
//                }
//
//                CustomSuperPlayerView.this.updatePlayState(1);
//            }
//
//            public void onSeekTo(int position) {
//                if (CustomSuperPlayerView.this.mCurrentPlayType == 1) {
//                    if (CustomSuperPlayerView.this.mVodPlayer != null) {
//                        CustomSuperPlayerView.this.mVodPlayer.seek(position);
//                    }
//                } else {
//                    CustomSuperPlayerView.this.updatePlayType(3);
//                    TCLogReport.getInstance().uploadLogs("timeshift", 0L, 0);
//                    if (CustomSuperPlayerView.this.mLivePlayer != null) {
//                        CustomSuperPlayerView.this.mLivePlayer.seek(position);
//                    }
//
//                    if (CustomSuperPlayerView.this.mWatcher != null) {
//                        CustomSuperPlayerView.this.mWatcher.stop();
//                    }
//                }
//
//            }
//
//            public void onResumeLive() {
//                if (CustomSuperPlayerView.this.mLivePlayer != null) {
//                    CustomSuperPlayerView.this.mLivePlayer.resumeLive();
//                }
//
//                CustomSuperPlayerView.this.updatePlayType(2);
//            }
//
//            public void onDanmuToggle(boolean isOpen) {
//                if (CustomSuperPlayerView.this.mDanmuView != null) {
//                    CustomSuperPlayerView.this.mDanmuView.toggle(isOpen);
//                }
//
//            }
//
//            public void onSnapshot() {
//                if (CustomSuperPlayerView.this.mCurrentPlayType == 1) {
//                    if (CustomSuperPlayerView.this.mVodPlayer != null) {
//                        CustomSuperPlayerView.this.mVodPlayer.snapshot(new ITXSnapshotListener() {
//                            public void onSnapshot(Bitmap bmp) {
//                                CustomSuperPlayerView.this.showSnapshotWindow(bmp);
//                            }
//                        });
//                    }
//                } else if (CustomSuperPlayerView.this.mCurrentPlayType == 3) {
//                    Toast.makeText(CustomSuperPlayerView.this.getContext(), "时移直播时暂不支持截图", Toast.LENGTH_SHORT).show();
//                } else if (CustomSuperPlayerView.this.mLivePlayer != null) {
//                    CustomSuperPlayerView.this.mLivePlayer.snapshot(new ITXSnapshotListener() {
//                        public void onSnapshot(Bitmap bmp) {
//                            CustomSuperPlayerView.this.showSnapshotWindow(bmp);
//                        }
//                    });
//                }
//
//            }
//
//            public void onQualityChange(TCVideoQuality quality) {
//                CustomSuperPlayerView.this.mControllerFullScreen.updateVideoQuality(quality);
//                if (CustomSuperPlayerView.this.mCurrentPlayType == 1) {
//                    if (CustomSuperPlayerView.this.mVodPlayer != null) {
//                        if (quality.index == -1) {
//                            float currentTime = CustomSuperPlayerView.this.mVodPlayer.getCurrentPlaybackTime();
//                            CustomSuperPlayerView.this.mVodPlayer.stopPlay(true);
//                            TXCLog.i("CustomSuperPlayerView", "onQualitySelect quality.url:" + quality.url);
//                            CustomSuperPlayerView.this.mVodPlayer.setStartTime(currentTime);
//                            CustomSuperPlayerView.this.mVodPlayer.startPlay(quality.url);
//                        } else {
//                            TXCLog.i("CustomSuperPlayerView", "setBitrateIndex quality.index:" + quality.index);
//                            CustomSuperPlayerView.this.mVodPlayer.setBitrateIndex(quality.index);
//                        }
//                    }
//                } else if (CustomSuperPlayerView.this.mLivePlayer != null && !TextUtils.isEmpty(quality.url)) {
//                    int result = CustomSuperPlayerView.this.mLivePlayer.switchStream(quality.url);
//                    if (result < 0) {
//                        Toast.makeText(CustomSuperPlayerView.this.getContext(), "切换" + quality.title + "清晰度失败，请稍候重试", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(CustomSuperPlayerView.this.getContext(), "正在切换到" + quality.title + "...", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                TCLogReport.getInstance().uploadLogs("change_resolution", 0L, 0);
//            }
//
//            public void onSpeedChange(float speedLevel) {
//                if (CustomSuperPlayerView.this.mVodPlayer != null) {
//                    CustomSuperPlayerView.this.mVodPlayer.setRate(speedLevel);
//                }
//
//                TCLogReport.getInstance().uploadLogs("change_speed", 0L, 0);
//            }
//
//            public void onMirrorToggle(boolean isMirror) {
//                if (CustomSuperPlayerView.this.mVodPlayer != null) {
//                    CustomSuperPlayerView.this.mVodPlayer.setMirror(isMirror);
//                }
//
//                if (isMirror) {
//                    TCLogReport.getInstance().uploadLogs("mirror", 0L, 0);
//                }
//
//            }
//
//            public void onHWAccelerationToggle(boolean isAccelerate) {
//                if (CustomSuperPlayerView.this.mCurrentPlayType == 1) {
//                    CustomSuperPlayerView.this.mChangeHWAcceleration = true;
//                    if (CustomSuperPlayerView.this.mVodPlayer != null) {
//                        CustomSuperPlayerView.this.mVodPlayer.enableHardwareDecode(isAccelerate);
//                        CustomSuperPlayerView.this.mSeekPos = (int)CustomSuperPlayerView.this.mVodPlayer.getCurrentPlaybackTime();
//                        TXCLog.i("CustomSuperPlayerView", "save pos:" + CustomSuperPlayerView.this.mSeekPos);
//                        CustomSuperPlayerView.this.stopPlay();
//                        CustomSuperPlayerView.this.playModeVideo(CustomSuperPlayerView.this.mCurrentProtocol);
//                    }
//                } else if (CustomSuperPlayerView.this.mLivePlayer != null) {
//                    CustomSuperPlayerView.this.mLivePlayer.enableHardwareDecode(isAccelerate);
//                    CustomSuperPlayerView.this.playWithModel(CustomSuperPlayerView.this.mCurrentModel);
//                }
//
//                if (isAccelerate) {
//                    TCLogReport.getInstance().uploadLogs("hw_decode", 0L, 0);
//                } else {
//                    TCLogReport.getInstance().uploadLogs("soft_decode", 0L, 0);
//                }
//
//            }
//        };
//        this.initView(context);
//    }
//
//    public CustomSuperPlayerView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        this.mCurPlayType = CustomSuperPlayerView.PLAYER_TYPE.PLAYER_TYPE_NULL;
//        this.OP_SYSTEM_ALERT_WINDOW = 24;
//        this.mControllerCallback = new IControllerCallback() {
//            public void onSwitchPlayMode(int requestPlayMode) {
//                if (CustomSuperPlayerView.this.mCurrentPlayMode != requestPlayMode) {
//                    if (!CustomSuperPlayerView.this.mLockScreen) {
//                        if (requestPlayMode == 2) {
//                            CustomSuperPlayerView.this.fullScreen(true);
//                        } else {
//                            CustomSuperPlayerView.this.fullScreen(false);
//                        }
//
//                        CustomSuperPlayerView.this.mControllerFullScreen.hide();
//                        CustomSuperPlayerView.this.mControllerWindow.hide();
//                        CustomSuperPlayerView.this.mControllerFloat.hide();
//                        if (requestPlayMode == 2) {
//                            if (CustomSuperPlayerView.this.mLayoutParamFullScreenMode == null) {
//                                return;
//                            }
//
//                            CustomSuperPlayerView.this.removeView(CustomSuperPlayerView.this.mControllerWindow);
//                            CustomSuperPlayerView.this.addView(CustomSuperPlayerView.this.mControllerFullScreen, CustomSuperPlayerView.this.mVodControllerFullScreenParams);
//                            CustomSuperPlayerView.this.setLayoutParams(CustomSuperPlayerView.this.mLayoutParamFullScreenMode);
//                            CustomSuperPlayerView.this.rotateScreenOrientation(1);
//                            if (CustomSuperPlayerView.this.mPlayerViewCallback != null) {
//                                CustomSuperPlayerView.this.mPlayerViewCallback.onStartFullScreenPlay();
//                            }
//                        } else {
//                            Intent intent;
//                            if (requestPlayMode == 1) {
//                                if (CustomSuperPlayerView.this.mCurrentPlayMode == 3) {
//                                    try {
//                                        Context viewContext = CustomSuperPlayerView.this.getContext();
//                                        intent = null;
//                                        if (!(viewContext instanceof Activity)) {
//                                            Toast.makeText(viewContext, "悬浮播放失败", Toast.LENGTH_SHORT).show();
//                                            return;
//                                        }
//
//                                        intent = new Intent(CustomSuperPlayerView.this.getContext(), viewContext.getClass());
//                                        CustomSuperPlayerView.this.mContext.startActivity(intent);
//                                        CustomSuperPlayerView.this.pause();
//                                        if (CustomSuperPlayerView.this.mLayoutParamWindowMode == null) {
//                                            return;
//                                        }
//
//                                        CustomSuperPlayerView.this.mWindowManager.removeView(CustomSuperPlayerView.this.mControllerFloat);
//                                        if (CustomSuperPlayerView.this.mCurrentPlayType == 1) {
//                                            CustomSuperPlayerView.this.mVodPlayer.setPlayerView(CustomSuperPlayerView.this.mTXCloudVideoView);
//                                        } else {
//                                            CustomSuperPlayerView.this.mLivePlayer.setPlayerView(CustomSuperPlayerView.this.mTXCloudVideoView);
//                                        }
//
//                                        CustomSuperPlayerView.this.resume();
//                                    } catch (Exception var6) {
//                                        var6.printStackTrace();
//                                    }
//                                } else if (CustomSuperPlayerView.this.mCurrentPlayMode == 2) {
//                                    if (CustomSuperPlayerView.this.mLayoutParamWindowMode == null) {
//                                        return;
//                                    }
//
//                                    CustomSuperPlayerView.this.removeView(CustomSuperPlayerView.this.mControllerFullScreen);
//                                    CustomSuperPlayerView.this.addView(CustomSuperPlayerView.this.mControllerWindow, CustomSuperPlayerView.this.mVodControllerWindowParams);
//                                    CustomSuperPlayerView.this.setLayoutParams(CustomSuperPlayerView.this.mLayoutParamWindowMode);
//                                    CustomSuperPlayerView.this.rotateScreenOrientation(2);
//                                    if (CustomSuperPlayerView.this.mPlayerViewCallback != null) {
//                                        CustomSuperPlayerView.this.mPlayerViewCallback.onStopFullScreenPlay();
//                                    }
//                                }
//                            } else if (requestPlayMode == 3) {
//                                TXCLog.i("CustomSuperPlayerView", "requestPlayMode Float :" + Build.MANUFACTURER);
//                                SuperPlayerGlobalConfig prefs = SuperPlayerGlobalConfig.getInstance();
//                                if (!prefs.enableFloatWindow) {
//                                    return;
//                                }
//
//                                if (VERSION.SDK_INT >= 23) {
//                                    if (!Settings.canDrawOverlays(CustomSuperPlayerView.this.mContext)) {
//                                        intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
//                                        intent.setData(Uri.parse("package:" + CustomSuperPlayerView.this.mContext.getPackageName()));
//                                        CustomSuperPlayerView.this.mContext.startActivity(intent);
//                                        return;
//                                    }
//                                } else if (!CustomSuperPlayerView.this.checkOp(CustomSuperPlayerView.this.mContext, 24)) {
//                                    Toast.makeText(CustomSuperPlayerView.this.mContext, "进入设置页面失败,请手动开启悬浮窗权限", Toast.LENGTH_SHORT).show();
//                                    return;
//                                }
//
//                                CustomSuperPlayerView.this.pause();
//                                CustomSuperPlayerView.this.mWindowManager = (WindowManager)CustomSuperPlayerView.this.mContext.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
//                                CustomSuperPlayerView.this.mWindowParams = new android.view.WindowManager.LayoutParams();
//                                if (VERSION.SDK_INT >= 26) {
//                                    CustomSuperPlayerView.this.mWindowParams.type = 2038;
//                                } else {
//                                    CustomSuperPlayerView.this.mWindowParams.type = 2002;
//                                }
//
//                                CustomSuperPlayerView.this.mWindowParams.flags = 40;
//                                CustomSuperPlayerView.this.mWindowParams.format = -3;
//                                CustomSuperPlayerView.this.mWindowParams.gravity = 51;
//                                TXRect rect = prefs.floatViewRect;
//                                CustomSuperPlayerView.this.mWindowParams.x = rect.x;
//                                CustomSuperPlayerView.this.mWindowParams.y = rect.y;
//                                CustomSuperPlayerView.this.mWindowParams.width = rect.width;
//                                CustomSuperPlayerView.this.mWindowParams.height = rect.height;
//
//                                try {
//                                    CustomSuperPlayerView.this.mWindowManager.addView(CustomSuperPlayerView.this.mControllerFloat, CustomSuperPlayerView.this.mWindowParams);
//                                } catch (Exception var5) {
//                                    Toast.makeText(CustomSuperPlayerView.this.getContext(), "悬浮播放失败", Toast.LENGTH_SHORT).show();
//                                    return;
//                                }
//
//                                TXCloudVideoView videoView = CustomSuperPlayerView.this.mControllerFloat.getFloatVideoView();
//                                if (videoView != null) {
//                                    if (CustomSuperPlayerView.this.mCurrentPlayType == 1) {
//                                        CustomSuperPlayerView.this.mVodPlayer.setPlayerView(videoView);
//                                    } else {
//                                        CustomSuperPlayerView.this.mLivePlayer.setPlayerView(videoView);
//                                    }
//
//                                    CustomSuperPlayerView.this.resume();
//                                }
//
//                                TCLogReport.getInstance().uploadLogs("floatmode", 0L, 0);
//                            }
//                        }
//
//                        CustomSuperPlayerView.this.mCurrentPlayMode = requestPlayMode;
//                    }
//                }
//            }
//
//            public void onBackPressed(int playMode) {
//                switch(playMode) {
//                    case 1:
//                        if (CustomSuperPlayerView.this.mPlayerViewCallback != null) {
//                            CustomSuperPlayerView.this.mPlayerViewCallback.onClickSmallReturnBtn();
//                        }
//
//                        if (CustomSuperPlayerView.this.mCurrentPlayState == 1) {
//                            this.onSwitchPlayMode(3);
//                        }
//                        break;
//                    case 2:
//                        this.onSwitchPlayMode(1);
//                        break;
//                    case 3:
//                        CustomSuperPlayerView.this.mWindowManager.removeView(CustomSuperPlayerView.this.mControllerFloat);
//                        if (CustomSuperPlayerView.this.mPlayerViewCallback != null) {
//                            CustomSuperPlayerView.this.mPlayerViewCallback.onClickFloatCloseBtn();
//                        }
//                }
//
//            }
//
//            public void onFloatPositionChange(int x, int y) {
//                CustomSuperPlayerView.this.mWindowParams.x = x;
//                CustomSuperPlayerView.this.mWindowParams.y = y;
//                CustomSuperPlayerView.this.mWindowManager.updateViewLayout(CustomSuperPlayerView.this.mControllerFloat, CustomSuperPlayerView.this.mWindowParams);
//            }
//
//            public void onPause() {
//                if (CustomSuperPlayerView.this.mCurrentPlayType == 1) {
//                    if (CustomSuperPlayerView.this.mVodPlayer != null) {
//                        CustomSuperPlayerView.this.mVodPlayer.pause();
//                    }
//                } else {
//                    if (CustomSuperPlayerView.this.mLivePlayer != null) {
//                        CustomSuperPlayerView.this.mLivePlayer.pause();
//                    }
//
//                    if (CustomSuperPlayerView.this.mWatcher != null) {
//                        CustomSuperPlayerView.this.mWatcher.stop();
//                    }
//                }
//
//                CustomSuperPlayerView.this.updatePlayState(2);
//            }
//
//            public void onResume() {
//                if (CustomSuperPlayerView.this.mCurrentPlayState == 4) {
//                    if (CustomSuperPlayerView.this.mCurPlayType == CustomSuperPlayerView.PLAYER_TYPE.PLAYER_TYPE_LIVE) {
//                        if (TCUrlUtil.isRTMPPlay(CustomSuperPlayerView.this.mCurrentPlayVideoURL)) {
//                            CustomSuperPlayerView.this.playLiveURL(CustomSuperPlayerView.this.mCurrentPlayVideoURL, 0);
//                        } else if (TCUrlUtil.isFLVPlay(CustomSuperPlayerView.this.mCurrentPlayVideoURL)) {
//                            CustomSuperPlayerView.this.playTimeShiftLiveURL(CustomSuperPlayerView.this.mCurrentModel);
//                            if (CustomSuperPlayerView.this.mCurrentModel.multiURLs != null && !CustomSuperPlayerView.this.mCurrentModel.multiURLs.isEmpty()) {
//                                CustomSuperPlayerView.this.startMultiStreamLiveURL(CustomSuperPlayerView.this.mCurrentPlayVideoURL);
//                            }
//                        }
//                    } else {
//                        CustomSuperPlayerView.this.playVodURL(CustomSuperPlayerView.this.mCurrentPlayVideoURL);
//                    }
//                } else if (CustomSuperPlayerView.this.mCurrentPlayState == 2) {
//                    if (CustomSuperPlayerView.this.mCurrentPlayType == 1) {
//                        if (CustomSuperPlayerView.this.mVodPlayer != null) {
//                            CustomSuperPlayerView.this.mVodPlayer.resume();
//                        }
//                    } else if (CustomSuperPlayerView.this.mLivePlayer != null) {
//                        CustomSuperPlayerView.this.mLivePlayer.resume();
//                    }
//                }
//
//                CustomSuperPlayerView.this.updatePlayState(1);
//            }
//
//            public void onSeekTo(int position) {
//                if (CustomSuperPlayerView.this.mCurrentPlayType == 1) {
//                    if (CustomSuperPlayerView.this.mVodPlayer != null) {
//                        CustomSuperPlayerView.this.mVodPlayer.seek(position);
//                    }
//                } else {
//                    CustomSuperPlayerView.this.updatePlayType(3);
//                    TCLogReport.getInstance().uploadLogs("timeshift", 0L, 0);
//                    if (CustomSuperPlayerView.this.mLivePlayer != null) {
//                        CustomSuperPlayerView.this.mLivePlayer.seek(position);
//                    }
//
//                    if (CustomSuperPlayerView.this.mWatcher != null) {
//                        CustomSuperPlayerView.this.mWatcher.stop();
//                    }
//                }
//
//            }
//
//            public void onResumeLive() {
//                if (CustomSuperPlayerView.this.mLivePlayer != null) {
//                    CustomSuperPlayerView.this.mLivePlayer.resumeLive();
//                }
//
//                CustomSuperPlayerView.this.updatePlayType(2);
//            }
//
//            public void onDanmuToggle(boolean isOpen) {
//                if (CustomSuperPlayerView.this.mDanmuView != null) {
//                    CustomSuperPlayerView.this.mDanmuView.toggle(isOpen);
//                }
//
//            }
//
//            public void onSnapshot() {
//                if (CustomSuperPlayerView.this.mCurrentPlayType == 1) {
//                    if (CustomSuperPlayerView.this.mVodPlayer != null) {
//                        CustomSuperPlayerView.this.mVodPlayer.snapshot(new ITXSnapshotListener() {
//                            public void onSnapshot(Bitmap bmp) {
//                                CustomSuperPlayerView.this.showSnapshotWindow(bmp);
//                            }
//                        });
//                    }
//                } else if (CustomSuperPlayerView.this.mCurrentPlayType == 3) {
//                    Toast.makeText(CustomSuperPlayerView.this.getContext(), "时移直播时暂不支持截图", Toast.LENGTH_SHORT).show();
//                } else if (CustomSuperPlayerView.this.mLivePlayer != null) {
//                    CustomSuperPlayerView.this.mLivePlayer.snapshot(new ITXSnapshotListener() {
//                        public void onSnapshot(Bitmap bmp) {
//                            CustomSuperPlayerView.this.showSnapshotWindow(bmp);
//                        }
//                    });
//                }
//
//            }
//
//            public void onQualityChange(TCVideoQuality quality) {
//                CustomSuperPlayerView.this.mControllerFullScreen.updateVideoQuality(quality);
//                if (CustomSuperPlayerView.this.mCurrentPlayType == 1) {
//                    if (CustomSuperPlayerView.this.mVodPlayer != null) {
//                        if (quality.index == -1) {
//                            float currentTime = CustomSuperPlayerView.this.mVodPlayer.getCurrentPlaybackTime();
//                            CustomSuperPlayerView.this.mVodPlayer.stopPlay(true);
//                            TXCLog.i("CustomSuperPlayerView", "onQualitySelect quality.url:" + quality.url);
//                            CustomSuperPlayerView.this.mVodPlayer.setStartTime(currentTime);
//                            CustomSuperPlayerView.this.mVodPlayer.startPlay(quality.url);
//                        } else {
//                            TXCLog.i("CustomSuperPlayerView", "setBitrateIndex quality.index:" + quality.index);
//                            CustomSuperPlayerView.this.mVodPlayer.setBitrateIndex(quality.index);
//                        }
//                    }
//                } else if (CustomSuperPlayerView.this.mLivePlayer != null && !TextUtils.isEmpty(quality.url)) {
//                    int result = CustomSuperPlayerView.this.mLivePlayer.switchStream(quality.url);
//                    if (result < 0) {
//                        Toast.makeText(CustomSuperPlayerView.this.getContext(), "切换" + quality.title + "清晰度失败，请稍候重试", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(CustomSuperPlayerView.this.getContext(), "正在切换到" + quality.title + "...", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                TCLogReport.getInstance().uploadLogs("change_resolution", 0L, 0);
//            }
//
//            public void onSpeedChange(float speedLevel) {
//                if (CustomSuperPlayerView.this.mVodPlayer != null) {
//                    CustomSuperPlayerView.this.mVodPlayer.setRate(speedLevel);
//                }
//
//                TCLogReport.getInstance().uploadLogs("change_speed", 0L, 0);
//            }
//
//            public void onMirrorToggle(boolean isMirror) {
//                if (CustomSuperPlayerView.this.mVodPlayer != null) {
//                    CustomSuperPlayerView.this.mVodPlayer.setMirror(isMirror);
//                }
//
//                if (isMirror) {
//                    TCLogReport.getInstance().uploadLogs("mirror", 0L, 0);
//                }
//
//            }
//
//            public void onHWAccelerationToggle(boolean isAccelerate) {
//                if (CustomSuperPlayerView.this.mCurrentPlayType == 1) {
//                    CustomSuperPlayerView.this.mChangeHWAcceleration = true;
//                    if (CustomSuperPlayerView.this.mVodPlayer != null) {
//                        CustomSuperPlayerView.this.mVodPlayer.enableHardwareDecode(isAccelerate);
//                        CustomSuperPlayerView.this.mSeekPos = (int)CustomSuperPlayerView.this.mVodPlayer.getCurrentPlaybackTime();
//                        TXCLog.i("CustomSuperPlayerView", "save pos:" + CustomSuperPlayerView.this.mSeekPos);
//                        CustomSuperPlayerView.this.stopPlay();
//                        CustomSuperPlayerView.this.playModeVideo(CustomSuperPlayerView.this.mCurrentProtocol);
//                    }
//                } else if (CustomSuperPlayerView.this.mLivePlayer != null) {
//                    CustomSuperPlayerView.this.mLivePlayer.enableHardwareDecode(isAccelerate);
//                    CustomSuperPlayerView.this.playWithModel(CustomSuperPlayerView.this.mCurrentModel);
//                }
//
//                if (isAccelerate) {
//                    TCLogReport.getInstance().uploadLogs("hw_decode", 0L, 0);
//                } else {
//                    TCLogReport.getInstance().uploadLogs("soft_decode", 0L, 0);
//                }
//
//            }
//        };
//        this.initView(context);
//    }
//
//    public CustomSuperPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        this.mCurPlayType = CustomSuperPlayerView.PLAYER_TYPE.PLAYER_TYPE_NULL;
//        this.OP_SYSTEM_ALERT_WINDOW = 24;
//        this.mControllerCallback = new IControllerCallback() {
//            public void onSwitchPlayMode(int requestPlayMode) {
//                if (CustomSuperPlayerView.this.mCurrentPlayMode != requestPlayMode) {
//                    if (!CustomSuperPlayerView.this.mLockScreen) {
//                        if (requestPlayMode == 2) {
//                            CustomSuperPlayerView.this.fullScreen(true);
//                        } else {
//                            CustomSuperPlayerView.this.fullScreen(false);
//                        }
//
//                        CustomSuperPlayerView.this.mControllerFullScreen.hide();
//                        CustomSuperPlayerView.this.mControllerWindow.hide();
//                        CustomSuperPlayerView.this.mControllerFloat.hide();
//                        if (requestPlayMode == 2) {
//                            if (CustomSuperPlayerView.this.mLayoutParamFullScreenMode == null) {
//                                return;
//                            }
//
//                            CustomSuperPlayerView.this.removeView(CustomSuperPlayerView.this.mControllerWindow);
//                            CustomSuperPlayerView.this.addView(CustomSuperPlayerView.this.mControllerFullScreen, CustomSuperPlayerView.this.mVodControllerFullScreenParams);
//                            CustomSuperPlayerView.this.setLayoutParams(CustomSuperPlayerView.this.mLayoutParamFullScreenMode);
//                            CustomSuperPlayerView.this.rotateScreenOrientation(1);
//                            if (CustomSuperPlayerView.this.mPlayerViewCallback != null) {
//                                CustomSuperPlayerView.this.mPlayerViewCallback.onStartFullScreenPlay();
//                            }
//                        } else {
//                            Intent intent;
//                            if (requestPlayMode == 1) {
//                                if (CustomSuperPlayerView.this.mCurrentPlayMode == 3) {
//                                    try {
//                                        Context viewContext = CustomSuperPlayerView.this.getContext();
//                                        intent = null;
//                                        if (!(viewContext instanceof Activity)) {
//                                            Toast.makeText(viewContext, "悬浮播放失败", Toast.LENGTH_SHORT).show();
//                                            return;
//                                        }
//
//                                        intent = new Intent(CustomSuperPlayerView.this.getContext(), viewContext.getClass());
//                                        CustomSuperPlayerView.this.mContext.startActivity(intent);
//                                        CustomSuperPlayerView.this.pause();
//                                        if (CustomSuperPlayerView.this.mLayoutParamWindowMode == null) {
//                                            return;
//                                        }
//
//                                        CustomSuperPlayerView.this.mWindowManager.removeView(CustomSuperPlayerView.this.mControllerFloat);
//                                        if (CustomSuperPlayerView.this.mCurrentPlayType == 1) {
//                                            CustomSuperPlayerView.this.mVodPlayer.setPlayerView(CustomSuperPlayerView.this.mTXCloudVideoView);
//                                        } else {
//                                            CustomSuperPlayerView.this.mLivePlayer.setPlayerView(CustomSuperPlayerView.this.mTXCloudVideoView);
//                                        }
//
//                                        CustomSuperPlayerView.this.resume();
//                                    } catch (Exception var6) {
//                                        var6.printStackTrace();
//                                    }
//                                } else if (CustomSuperPlayerView.this.mCurrentPlayMode == 2) {
//                                    if (CustomSuperPlayerView.this.mLayoutParamWindowMode == null) {
//                                        return;
//                                    }
//
//                                    CustomSuperPlayerView.this.removeView(CustomSuperPlayerView.this.mControllerFullScreen);
//                                    CustomSuperPlayerView.this.addView(CustomSuperPlayerView.this.mControllerWindow, CustomSuperPlayerView.this.mVodControllerWindowParams);
//                                    CustomSuperPlayerView.this.setLayoutParams(CustomSuperPlayerView.this.mLayoutParamWindowMode);
//                                    CustomSuperPlayerView.this.rotateScreenOrientation(2);
//                                    if (CustomSuperPlayerView.this.mPlayerViewCallback != null) {
//                                        CustomSuperPlayerView.this.mPlayerViewCallback.onStopFullScreenPlay();
//                                    }
//                                }
//                            } else if (requestPlayMode == 3) {
//                                TXCLog.i("CustomSuperPlayerView", "requestPlayMode Float :" + Build.MANUFACTURER);
//                                SuperPlayerGlobalConfig prefs = SuperPlayerGlobalConfig.getInstance();
//                                if (!prefs.enableFloatWindow) {
//                                    return;
//                                }
//
//                                if (VERSION.SDK_INT >= 23) {
//                                    if (!Settings.canDrawOverlays(CustomSuperPlayerView.this.mContext)) {
//                                        intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
//                                        intent.setData(Uri.parse("package:" + CustomSuperPlayerView.this.mContext.getPackageName()));
//                                        CustomSuperPlayerView.this.mContext.startActivity(intent);
//                                        return;
//                                    }
//                                } else if (!CustomSuperPlayerView.this.checkOp(CustomSuperPlayerView.this.mContext, 24)) {
//                                    Toast.makeText(CustomSuperPlayerView.this.mContext, "进入设置页面失败,请手动开启悬浮窗权限", Toast.LENGTH_SHORT).show();
//                                    return;
//                                }
//
//                                CustomSuperPlayerView.this.pause();
//                                CustomSuperPlayerView.this.mWindowManager = (WindowManager)CustomSuperPlayerView.this.mContext.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
//                                CustomSuperPlayerView.this.mWindowParams = new android.view.WindowManager.LayoutParams();
//                                if (VERSION.SDK_INT >= 26) {
//                                    CustomSuperPlayerView.this.mWindowParams.type = 2038;
//                                } else {
//                                    CustomSuperPlayerView.this.mWindowParams.type = 2002;
//                                }
//
//                                CustomSuperPlayerView.this.mWindowParams.flags = 40;
//                                CustomSuperPlayerView.this.mWindowParams.format = -3;
//                                CustomSuperPlayerView.this.mWindowParams.gravity = 51;
//                                TXRect rect = prefs.floatViewRect;
//                                CustomSuperPlayerView.this.mWindowParams.x = rect.x;
//                                CustomSuperPlayerView.this.mWindowParams.y = rect.y;
//                                CustomSuperPlayerView.this.mWindowParams.width = rect.width;
//                                CustomSuperPlayerView.this.mWindowParams.height = rect.height;
//
//                                try {
//                                    CustomSuperPlayerView.this.mWindowManager.addView(CustomSuperPlayerView.this.mControllerFloat, CustomSuperPlayerView.this.mWindowParams);
//                                } catch (Exception var5) {
//                                    Toast.makeText(CustomSuperPlayerView.this.getContext(), "悬浮播放失败", Toast.LENGTH_SHORT).show();
//                                    return;
//                                }
//
//                                TXCloudVideoView videoView = CustomSuperPlayerView.this.mControllerFloat.getFloatVideoView();
//                                if (videoView != null) {
//                                    if (CustomSuperPlayerView.this.mCurrentPlayType == 1) {
//                                        CustomSuperPlayerView.this.mVodPlayer.setPlayerView(videoView);
//                                    } else {
//                                        CustomSuperPlayerView.this.mLivePlayer.setPlayerView(videoView);
//                                    }
//
//                                    CustomSuperPlayerView.this.resume();
//                                }
//
//                                TCLogReport.getInstance().uploadLogs("floatmode", 0L, 0);
//                            }
//                        }
//
//                        CustomSuperPlayerView.this.mCurrentPlayMode = requestPlayMode;
//                    }
//                }
//            }
//
//            public void onBackPressed(int playMode) {
//                switch(playMode) {
//                    case 1:
//                        if (CustomSuperPlayerView.this.mPlayerViewCallback != null) {
//                            CustomSuperPlayerView.this.mPlayerViewCallback.onClickSmallReturnBtn();
//                        }
//
//                        if (CustomSuperPlayerView.this.mCurrentPlayState == 1) {
//                            this.onSwitchPlayMode(3);
//                        }
//                        break;
//                    case 2:
//                        this.onSwitchPlayMode(1);
//                        break;
//                    case 3:
//                        CustomSuperPlayerView.this.mWindowManager.removeView(CustomSuperPlayerView.this.mControllerFloat);
//                        if (CustomSuperPlayerView.this.mPlayerViewCallback != null) {
//                            CustomSuperPlayerView.this.mPlayerViewCallback.onClickFloatCloseBtn();
//                        }
//                }
//
//            }
//
//            public void onFloatPositionChange(int x, int y) {
//                CustomSuperPlayerView.this.mWindowParams.x = x;
//                CustomSuperPlayerView.this.mWindowParams.y = y;
//                CustomSuperPlayerView.this.mWindowManager.updateViewLayout(CustomSuperPlayerView.this.mControllerFloat, CustomSuperPlayerView.this.mWindowParams);
//            }
//
//            public void onPause() {
//                if (CustomSuperPlayerView.this.mCurrentPlayType == 1) {
//                    if (CustomSuperPlayerView.this.mVodPlayer != null) {
//                        CustomSuperPlayerView.this.mVodPlayer.pause();
//                    }
//                } else {
//                    if (CustomSuperPlayerView.this.mLivePlayer != null) {
//                        CustomSuperPlayerView.this.mLivePlayer.pause();
//                    }
//
//                    if (CustomSuperPlayerView.this.mWatcher != null) {
//                        CustomSuperPlayerView.this.mWatcher.stop();
//                    }
//                }
//
//                CustomSuperPlayerView.this.updatePlayState(2);
//            }
//
//            public void onResume() {
//                if (CustomSuperPlayerView.this.mCurrentPlayState == 4) {
//                    if (CustomSuperPlayerView.this.mCurPlayType == CustomSuperPlayerView.PLAYER_TYPE.PLAYER_TYPE_LIVE) {
//                        if (TCUrlUtil.isRTMPPlay(CustomSuperPlayerView.this.mCurrentPlayVideoURL)) {
//                            CustomSuperPlayerView.this.playLiveURL(CustomSuperPlayerView.this.mCurrentPlayVideoURL, 0);
//                        } else if (TCUrlUtil.isFLVPlay(CustomSuperPlayerView.this.mCurrentPlayVideoURL)) {
//                            CustomSuperPlayerView.this.playTimeShiftLiveURL(CustomSuperPlayerView.this.mCurrentModel);
//                            if (CustomSuperPlayerView.this.mCurrentModel.multiURLs != null && !CustomSuperPlayerView.this.mCurrentModel.multiURLs.isEmpty()) {
//                                CustomSuperPlayerView.this.startMultiStreamLiveURL(CustomSuperPlayerView.this.mCurrentPlayVideoURL);
//                            }
//                        }
//                    } else {
//                        CustomSuperPlayerView.this.playVodURL(CustomSuperPlayerView.this.mCurrentPlayVideoURL);
//                    }
//                } else if (CustomSuperPlayerView.this.mCurrentPlayState == 2) {
//                    if (CustomSuperPlayerView.this.mCurrentPlayType == 1) {
//                        if (CustomSuperPlayerView.this.mVodPlayer != null) {
//                            CustomSuperPlayerView.this.mVodPlayer.resume();
//                        }
//                    } else if (CustomSuperPlayerView.this.mLivePlayer != null) {
//                        CustomSuperPlayerView.this.mLivePlayer.resume();
//                    }
//                }
//
//                CustomSuperPlayerView.this.updatePlayState(1);
//            }
//
//            public void onSeekTo(int position) {
//                if (CustomSuperPlayerView.this.mCurrentPlayType == 1) {
//                    if (CustomSuperPlayerView.this.mVodPlayer != null) {
//                        CustomSuperPlayerView.this.mVodPlayer.seek(position);
//                    }
//                } else {
//                    CustomSuperPlayerView.this.updatePlayType(3);
//                    TCLogReport.getInstance().uploadLogs("timeshift", 0L, 0);
//                    if (CustomSuperPlayerView.this.mLivePlayer != null) {
//                        CustomSuperPlayerView.this.mLivePlayer.seek(position);
//                    }
//
//                    if (CustomSuperPlayerView.this.mWatcher != null) {
//                        CustomSuperPlayerView.this.mWatcher.stop();
//                    }
//                }
//
//            }
//
//            public void onResumeLive() {
//                if (CustomSuperPlayerView.this.mLivePlayer != null) {
//                    CustomSuperPlayerView.this.mLivePlayer.resumeLive();
//                }
//
//                CustomSuperPlayerView.this.updatePlayType(2);
//            }
//
//            public void onDanmuToggle(boolean isOpen) {
//                if (CustomSuperPlayerView.this.mDanmuView != null) {
//                    CustomSuperPlayerView.this.mDanmuView.toggle(isOpen);
//                }
//
//            }
//
//            public void onSnapshot() {
//                if (CustomSuperPlayerView.this.mCurrentPlayType == 1) {
//                    if (CustomSuperPlayerView.this.mVodPlayer != null) {
//                        CustomSuperPlayerView.this.mVodPlayer.snapshot(new ITXSnapshotListener() {
//                            public void onSnapshot(Bitmap bmp) {
//                                CustomSuperPlayerView.this.showSnapshotWindow(bmp);
//                            }
//                        });
//                    }
//                } else if (CustomSuperPlayerView.this.mCurrentPlayType == 3) {
//                    Toast.makeText(CustomSuperPlayerView.this.getContext(), "时移直播时暂不支持截图", Toast.LENGTH_SHORT).show();
//                } else if (CustomSuperPlayerView.this.mLivePlayer != null) {
//                    CustomSuperPlayerView.this.mLivePlayer.snapshot(new ITXSnapshotListener() {
//                        public void onSnapshot(Bitmap bmp) {
//                            CustomSuperPlayerView.this.showSnapshotWindow(bmp);
//                        }
//                    });
//                }
//
//            }
//
//            public void onQualityChange(TCVideoQuality quality) {
//                CustomSuperPlayerView.this.mControllerFullScreen.updateVideoQuality(quality);
//                if (CustomSuperPlayerView.this.mCurrentPlayType == 1) {
//                    if (CustomSuperPlayerView.this.mVodPlayer != null) {
//                        if (quality.index == -1) {
//                            float currentTime = CustomSuperPlayerView.this.mVodPlayer.getCurrentPlaybackTime();
//                            CustomSuperPlayerView.this.mVodPlayer.stopPlay(true);
//                            TXCLog.i("CustomSuperPlayerView", "onQualitySelect quality.url:" + quality.url);
//                            CustomSuperPlayerView.this.mVodPlayer.setStartTime(currentTime);
//                            CustomSuperPlayerView.this.mVodPlayer.startPlay(quality.url);
//                        } else {
//                            TXCLog.i("CustomSuperPlayerView", "setBitrateIndex quality.index:" + quality.index);
//                            CustomSuperPlayerView.this.mVodPlayer.setBitrateIndex(quality.index);
//                        }
//                    }
//                } else if (CustomSuperPlayerView.this.mLivePlayer != null && !TextUtils.isEmpty(quality.url)) {
//                    int result = CustomSuperPlayerView.this.mLivePlayer.switchStream(quality.url);
//                    if (result < 0) {
//                        Toast.makeText(CustomSuperPlayerView.this.getContext(), "切换" + quality.title + "清晰度失败，请稍候重试", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(CustomSuperPlayerView.this.getContext(), "正在切换到" + quality.title + "...", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                TCLogReport.getInstance().uploadLogs("change_resolution", 0L, 0);
//            }
//
//            public void onSpeedChange(float speedLevel) {
//                if (CustomSuperPlayerView.this.mVodPlayer != null) {
//                    CustomSuperPlayerView.this.mVodPlayer.setRate(speedLevel);
//                }
//
//                TCLogReport.getInstance().uploadLogs("change_speed", 0L, 0);
//            }
//
//            public void onMirrorToggle(boolean isMirror) {
//                if (CustomSuperPlayerView.this.mVodPlayer != null) {
//                    CustomSuperPlayerView.this.mVodPlayer.setMirror(isMirror);
//                }
//
//                if (isMirror) {
//                    TCLogReport.getInstance().uploadLogs("mirror", 0L, 0);
//                }
//
//            }
//
//            public void onHWAccelerationToggle(boolean isAccelerate) {
//                if (CustomSuperPlayerView.this.mCurrentPlayType == 1) {
//                    CustomSuperPlayerView.this.mChangeHWAcceleration = true;
//                    if (CustomSuperPlayerView.this.mVodPlayer != null) {
//                        CustomSuperPlayerView.this.mVodPlayer.enableHardwareDecode(isAccelerate);
//                        CustomSuperPlayerView.this.mSeekPos = (int)CustomSuperPlayerView.this.mVodPlayer.getCurrentPlaybackTime();
//                        TXCLog.i("CustomSuperPlayerView", "save pos:" + CustomSuperPlayerView.this.mSeekPos);
//                        CustomSuperPlayerView.this.stopPlay();
//                        CustomSuperPlayerView.this.playModeVideo(CustomSuperPlayerView.this.mCurrentProtocol);
//                    }
//                } else if (CustomSuperPlayerView.this.mLivePlayer != null) {
//                    CustomSuperPlayerView.this.mLivePlayer.enableHardwareDecode(isAccelerate);
//                    CustomSuperPlayerView.this.playWithModel(CustomSuperPlayerView.this.mCurrentModel);
//                }
//
//                if (isAccelerate) {
//                    TCLogReport.getInstance().uploadLogs("hw_decode", 0L, 0);
//                } else {
//                    TCLogReport.getInstance().uploadLogs("soft_decode", 0L, 0);
//                }
//
//            }
//        };
//        this.initView(context);
//    }
//
//    private void initView(Context context) {
//        this.mContext = context;
//        this.mRootView = (ViewGroup)LayoutInflater.from(context).inflate(layout.super_vod_player_view, (ViewGroup)null);
//        this.mTXCloudVideoView = (TXCloudVideoView)this.mRootView.findViewById(id.cloud_video_view);
//        this.mControllerFullScreen = (TCControllerFullScreen)this.mRootView.findViewById(id.controller_large);
//        this.mControllerWindow = (TCControllerWindow)this.mRootView.findViewById(id.controller_small);
//        this.mControllerFloat = (TCControllerFloat)this.mRootView.findViewById(id.controller_float);
//        this.mDanmuView = (TCDanmuView)this.mRootView.findViewById(id.danmaku_view);
//        this.mVodControllerWindowParams = new android.widget.RelativeLayout.LayoutParams(-1, -1);
//        this.mVodControllerFullScreenParams = new android.widget.RelativeLayout.LayoutParams(-1, -1);
//        this.mControllerFullScreen.setCallback(this.mControllerCallback);
//        this.mControllerWindow.setCallback(this.mControllerCallback);
//        this.mControllerFloat.setCallback(this.mControllerCallback);
//        this.removeAllViews();
//        this.mRootView.removeView(this.mDanmuView);
//        this.mRootView.removeView(this.mTXCloudVideoView);
//        this.mRootView.removeView(this.mControllerWindow);
//        this.mRootView.removeView(this.mControllerFullScreen);
//        this.mRootView.removeView(this.mControllerFloat);
//        this.addView(this.mTXCloudVideoView);
//        if (this.mCurrentPlayMode == 2) {
//            this.addView(this.mControllerFullScreen);
//            this.mControllerFullScreen.hide();
//        } else if (this.mCurrentPlayMode == 1) {
//            this.addView(this.mControllerWindow);
//            this.mControllerWindow.hide();
//        }
//
//        this.addView(this.mDanmuView);
//        this.post(new Runnable() {
//            public void run() {
//                if (CustomSuperPlayerView.this.mCurrentPlayMode == 1) {
//                    CustomSuperPlayerView.this.mLayoutParamWindowMode = (LayoutParams) CustomSuperPlayerView.this.getLayoutParams();
//                }
//
//                try {
//                    Class parentLayoutParamClazz = CustomSuperPlayerView.this.getLayoutParams().getClass();
//                    Constructor constructor = parentLayoutParamClazz.getDeclaredConstructor(Integer.TYPE, Integer.TYPE);
//                    CustomSuperPlayerView.this.mLayoutParamFullScreenMode = (LayoutParams)constructor.newInstance(-1, -1);
//                } catch (Exception var3) {
//                    var3.printStackTrace();
//                }
//
//            }
//        });
//        TCLogReport.getInstance().setAppName(context);
//        TCLogReport.getInstance().setPackageName(context);
//    }
//
//    private void initVodPlayer(Context context) {
//        if (this.mVodPlayer == null) {
//            this.mVodPlayer = new TXVodPlayer(context);
//            SuperPlayerGlobalConfig config = SuperPlayerGlobalConfig.getInstance();
//            this.mVodPlayConfig = new TXVodPlayConfig();
//            File sdcardDir = context.getExternalFilesDir((String)null);
//            if (sdcardDir != null) {
//                this.mVodPlayConfig.setCacheFolderPath(sdcardDir.getPath() + "/txcache");
//            }
//
//            this.mVodPlayConfig.setMaxCacheItems(config.maxCacheItem);
//            this.mVodPlayer.setConfig(this.mVodPlayConfig);
//            this.mVodPlayer.setRenderMode(config.renderMode);
//            this.mVodPlayer.setVodListener(this);
//            this.mVodPlayer.enableHardwareDecode(config.enableHWAcceleration);
//        }
//    }
//
//    private void initLivePlayer(Context context) {
//        if (this.mLivePlayer == null) {
//            this.mLivePlayer = new TXLivePlayer(context);
//            SuperPlayerGlobalConfig config = SuperPlayerGlobalConfig.getInstance();
//            this.mLivePlayConfig = new TXLivePlayConfig();
//            this.mLivePlayer.setConfig(this.mLivePlayConfig);
//            this.mLivePlayer.setRenderMode(config.renderMode);
//            this.mLivePlayer.setRenderRotation(0);
//            this.mLivePlayer.setPlayListener(this);
//            this.mLivePlayer.enableHardwareDecode(config.enableHWAcceleration);
//        }
//    }
//
//    public void playWithModel(final SuperPlayerModel model) {
//        this.mCurrentModel = model;
//        this.stopPlay();
//        this.initLivePlayer(this.getContext());
//        this.initVodPlayer(this.getContext());
//        this.mControllerFullScreen.updateImageSpriteInfo((TCPlayImageSpriteInfo)null);
//        this.mControllerFullScreen.updateKeyFrameDescInfo((List)null);
//        TCPlayInfoParams params = new TCPlayInfoParams();
//        params.appId = model.appId;
//        if (model.videoId != null) {
//            params.fileId = model.videoId.fileId;
//            params.timeout = model.videoId.timeout;
//            params.us = model.videoId.us;
//            params.exper = model.videoId.exper;
//            params.sign = model.videoId.sign;
//        }
//
//        this.mCurrentProtocol = new TCPlayInfoProtocolV2(params);
//        if (model.videoId != null) {
//            this.mCurrentProtocol.sendRequest(new IPlayInfoRequestCallback() {
//                public void onSuccess(IPlayInfoProtocol protocol, TCPlayInfoParams param) {
//                    TXCLog.i("CustomSuperPlayerView", "onSuccess: protocol params = " + param.toString());
//                    CustomSuperPlayerView.this.mReportVodStartTime = System.currentTimeMillis();
//                    CustomSuperPlayerView.this.mVodPlayer.setPlayerView(CustomSuperPlayerView.this.mTXCloudVideoView);
//                    CustomSuperPlayerView.this.playModeVideo(CustomSuperPlayerView.this.mCurrentProtocol);
//                    CustomSuperPlayerView.this.updatePlayType(1);
//                    String title = !TextUtils.isEmpty(model.title) ? model.title : (CustomSuperPlayerView.this.mCurrentProtocol.getName() != null && !TextUtils.isEmpty(CustomSuperPlayerView.this.mCurrentProtocol.getName()) ? CustomSuperPlayerView.this.mCurrentProtocol.getName() : "");
//                    CustomSuperPlayerView.this.updateTitle(title);
//                    CustomSuperPlayerView.this.updateVideoProgress(0L, 0L);
//                    CustomSuperPlayerView.this.mControllerFullScreen.updateImageSpriteInfo(CustomSuperPlayerView.this.mCurrentProtocol.getImageSpriteInfo());
//                    CustomSuperPlayerView.this.mControllerFullScreen.updateKeyFrameDescInfo(CustomSuperPlayerView.this.mCurrentProtocol.getKeyFrameDescInfo());
//                }
//
//                public void onError(int errCode, String message) {
//                    TXCLog.i("CustomSuperPlayerView", "onFail: errorCode = " + errCode + " message = " + message);
//                    Toast.makeText(CustomSuperPlayerView.this.getContext(), "播放视频文件失败 code = " + errCode + " msg = " + message, Toast.LENGTH_SHORT).show();
//                }
//            });
//        } else {
//            String videoURL = null;
//            List<TCVideoQuality> videoQualities = new ArrayList();
//            TCVideoQuality defaultVideoQuality = null;
//            if (model.multiURLs != null && !model.multiURLs.isEmpty()) {
//                int i = 0;
//
//                SuperPlayerURL superPlayerURL;
//                for(Iterator var7 = model.multiURLs.iterator(); var7.hasNext(); videoQualities.add(new TCVideoQuality(i++, superPlayerURL.qualityName, superPlayerURL.url))) {
//                    superPlayerURL = (SuperPlayerURL)var7.next();
//                    if (i == model.playDefaultIndex) {
//                        videoURL = superPlayerURL.url;
//                    }
//                }
//
//                defaultVideoQuality = (TCVideoQuality)videoQualities.get(model.playDefaultIndex);
//            } else if (!TextUtils.isEmpty(model.url)) {
//                videoQualities.add(new TCVideoQuality(0, model.qualityName, model.url));
//                defaultVideoQuality = (TCVideoQuality)videoQualities.get(0);
//                videoURL = model.url;
//            }
//
//            if (TextUtils.isEmpty(videoURL)) {
//                Toast.makeText(this.getContext(), "播放视频失败，播放连接为空", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            if (TCUrlUtil.isRTMPPlay(videoURL)) {
//                this.mReportLiveStartTime = System.currentTimeMillis();
//                this.mLivePlayer.setPlayerView(this.mTXCloudVideoView);
//                this.playLiveURL(videoURL, 0);
//            } else if (TCUrlUtil.isFLVPlay(videoURL)) {
//                this.mReportLiveStartTime = System.currentTimeMillis();
//                this.mLivePlayer.setPlayerView(this.mTXCloudVideoView);
//                this.playTimeShiftLiveURL(model);
//                if (model.multiURLs != null && !model.multiURLs.isEmpty()) {
//                    this.startMultiStreamLiveURL(videoURL);
//                }
//            } else {
//                this.mReportVodStartTime = System.currentTimeMillis();
//                this.mVodPlayer.setPlayerView(this.mTXCloudVideoView);
//                this.playVodURL(videoURL);
//            }
//
//            boolean isLivePlay = TCUrlUtil.isRTMPPlay(videoURL) || TCUrlUtil.isFLVPlay(videoURL);
//            this.updatePlayType(isLivePlay ? 2 : 1);
//            this.updateTitle(model.title);
//            this.updateVideoProgress(0L, 0L);
//            this.mControllerFullScreen.setVideoQualityList(videoQualities);
//            this.mControllerFullScreen.updateVideoQuality(defaultVideoQuality);
//        }
//
//    }
//
//    private void playModeVideo(IPlayInfoProtocol protocol) {
//        this.playVodURL(protocol.getUrl());
//        List<TCVideoQuality> videoQualityArrayList = protocol.getVideoQualityList();
//        if (videoQualityArrayList != null) {
//            this.mControllerFullScreen.setVideoQualityList(videoQualityArrayList);
//        }
//
//        TCVideoQuality defaultVideoQuality = protocol.getDefaultVideoQuality();
//        if (defaultVideoQuality != null) {
//            this.mControllerFullScreen.updateVideoQuality(defaultVideoQuality);
//        }
//
//    }
//
//    private void playLiveURL(String url, int playType) {
//        this.mCurrentPlayVideoURL = url;
//        if (this.mLivePlayer != null) {
//            this.mLivePlayer.setPlayListener(this);
//            int result = this.mLivePlayer.startPlay(url, playType);
//            if (result != 0) {
//                TXCLog.e("CustomSuperPlayerView", "playLiveURL videoURL:" + url + ",result:" + result);
//            } else {
//                this.mCurrentPlayState = 1;
//                this.mCurPlayType = CustomSuperPlayerView.PLAYER_TYPE.PLAYER_TYPE_LIVE;
//                TXCLog.e("CustomSuperPlayerView", "playLiveURL mCurrentPlayState:" + this.mCurrentPlayState);
//            }
//        }
//
//    }
//
//    private void playVodURL(String url) {
//        if (url != null && !"".equals(url)) {
//            this.mCurrentPlayVideoURL = url;
//            if (url.contains(".m3u8")) {
//                this.mIsMultiBitrateStream = true;
//            }
//
//            if (this.mVodPlayer != null) {
//                this.mDefaultQualitySet = false;
//                this.mVodPlayer.setAutoPlay(true);
//                this.mVodPlayer.setVodListener(this);
//                int ret = this.mVodPlayer.startPlay(url);
//                if (ret == 0) {
//                    this.mCurrentPlayState = 1;
//                    this.mCurPlayType = CustomSuperPlayerView.PLAYER_TYPE.PLAYER_TYPE_VOD;
//                    TXCLog.e("CustomSuperPlayerView", "playVodURL mCurrentPlayState:" + this.mCurrentPlayState);
//                }
//            }
//
//            this.mIsPlayWithFileId = false;
//        }
//    }
//
//    private void playTimeShiftLiveURL(SuperPlayerModel model) {
//        String liveURL = model.url;
//        String bizid = liveURL.substring(liveURL.indexOf("//") + 2, liveURL.indexOf("."));
//        String domian = SuperPlayerGlobalConfig.getInstance().playShiftDomain;
//        String streamid = liveURL.substring(liveURL.lastIndexOf("/") + 1, liveURL.lastIndexOf("."));
//        int appid = model.appId;
//        TXCLog.i("CustomSuperPlayerView", "bizid:" + bizid + ",streamid:" + streamid + ",appid:" + appid);
//        this.playLiveURL(liveURL, 1);
//
//        try {
//            int bizidNum = Integer.valueOf(bizid);
//            this.mLivePlayer.prepareLiveSeek(domian, bizidNum);
//        } catch (NumberFormatException var8) {
//            var8.printStackTrace();
//            TXCLog.e("CustomSuperPlayerView", "playTimeShiftLiveURL: bizidNum 错误 = %s " + bizid);
//        }
//
//    }
//
//    private void startMultiStreamLiveURL(String url) {
//        this.mLivePlayConfig.setAutoAdjustCacheTime(false);
//        this.mLivePlayConfig.setMaxAutoAdjustCacheTime(5.0F);
//        this.mLivePlayConfig.setMinAutoAdjustCacheTime(5.0F);
//        this.mLivePlayer.setConfig(this.mLivePlayConfig);
//        if (this.mWatcher == null) {
//            this.mWatcher = new TCNetWatcher(this.mContext);
//        }
//
//        this.mWatcher.start(url, this.mLivePlayer);
//    }
//
//    private void updateTitle(String title) {
//        this.mControllerWindow.updateTitle(title);
//        this.mControllerFullScreen.updateTitle(title);
//    }
//
//    private void updateVideoProgress(long current, long duration) {
//        this.mControllerWindow.updateVideoProgress(current, duration);
//        this.mControllerFullScreen.updateVideoProgress(current, duration);
//    }
//
//    private void updatePlayType(int playType) {
//        this.mCurrentPlayType = playType;
//        this.mControllerWindow.updatePlayType(playType);
//        this.mControllerFullScreen.updatePlayType(playType);
//    }
//
//    private void updatePlayState(int playState) {
//        this.mCurrentPlayState = playState;
//        this.mControllerWindow.updatePlayState(playState);
//        this.mControllerFullScreen.updatePlayState(playState);
//    }
//
//    public void onResume() {
//        if (this.mDanmuView != null && this.mDanmuView.isPrepared() && this.mDanmuView.isPaused()) {
//            this.mDanmuView.resume();
//        }
//
//        this.resume();
//    }
//
//    private void resume() {
//        if (this.mCurrentPlayType == 1 && this.mVodPlayer != null) {
//            this.mVodPlayer.resume();
//        }
//
//    }
//
//    public void onPause() {
//        if (this.mDanmuView != null && this.mDanmuView.isPrepared()) {
//            this.mDanmuView.pause();
//        }
//
//        this.pause();
//    }
//
//    private void pause() {
//        if (this.mCurrentPlayType == 1 && this.mVodPlayer != null) {
//            this.mVodPlayer.pause();
//        }
//
//    }
//
//    public void resetPlayer() {
//        if (this.mDanmuView != null) {
//            this.mDanmuView.release();
//            this.mDanmuView = null;
//        }
//
//        this.stopPlay();
//    }
//
//    private void stopPlay() {
//        if (this.mVodPlayer != null) {
//            this.mVodPlayer.setVodListener((ITXVodPlayListener)null);
//            this.mVodPlayer.stopPlay(false);
//        }
//
//        if (this.mLivePlayer != null) {
//            this.mLivePlayer.setPlayListener((ITXLivePlayListener)null);
//            this.mLivePlayer.stopPlay(false);
//            this.mTXCloudVideoView.removeVideoView();
//        }
//
//        if (this.mWatcher != null) {
//            this.mWatcher.stop();
//        }
//
//        this.mCurrentPlayState = 2;
//        TXCLog.e("CustomSuperPlayerView", "stopPlay mCurrentPlayState:" + this.mCurrentPlayState);
//        this.reportPlayTime();
//    }
//
//    private void reportPlayTime() {
//        long reportEndTime;
//        long diff;
//        if (this.mReportLiveStartTime != -1L) {
//            reportEndTime = System.currentTimeMillis();
//            diff = (reportEndTime - this.mReportLiveStartTime) / 1000L;
//            TCLogReport.getInstance().uploadLogs("superlive", diff, 0);
//            this.mReportLiveStartTime = -1L;
//        }
//
//        if (this.mReportVodStartTime != -1L) {
//            reportEndTime = System.currentTimeMillis();
//            diff = (reportEndTime - this.mReportVodStartTime) / 1000L;
//            TCLogReport.getInstance().uploadLogs("supervod", diff, this.mIsPlayWithFileId ? 1 : 0);
//            this.mReportVodStartTime = -1L;
//        }
//
//    }
//
//    public void setPlayerViewCallback(CustomSuperPlayerView.OnCustomSuperPlayerViewCallback callback) {
//        this.mPlayerViewCallback = callback;
//    }
//
//    private void fullScreen(boolean isFull) {
//        if (this.getContext() instanceof Activity) {
//            Activity activity = (Activity)this.getContext();
//            View decorView;
//            if (isFull) {
//                decorView = activity.getWindow().getDecorView();
//                if (decorView == null) {
//                    return;
//                }
//
//                if (VERSION.SDK_INT > 11 && VERSION.SDK_INT < 19) {
//                    decorView.setSystemUiVisibility(8);
//                } else if (VERSION.SDK_INT >= 19) {
//                    int uiOptions = 4102;
//                    decorView.setSystemUiVisibility(uiOptions);
//                }
//            } else {
//                decorView = activity.getWindow().getDecorView();
//                if (decorView == null) {
//                    return;
//                }
//
//                if (VERSION.SDK_INT > 11 && VERSION.SDK_INT < 19) {
//                    decorView.setSystemUiVisibility(0);
//                } else if (VERSION.SDK_INT >= 19) {
//                    decorView.setSystemUiVisibility(0);
//                }
//            }
//        }
//
//    }
//
//    private void showSnapshotWindow(final Bitmap bmp) {
//        if (bmp != null) {
//            final PopupWindow popupWindow = new PopupWindow(this.mContext);
//            popupWindow.setWidth(-2);
//            popupWindow.setHeight(-2);
//            View view = LayoutInflater.from(this.mContext).inflate(layout.layout_new_vod_snap, (ViewGroup)null);
//            ImageView imageView = (ImageView)view.findViewById(id.iv_snap);
//            imageView.setImageBitmap(bmp);
//            popupWindow.setContentView(view);
//            popupWindow.setOutsideTouchable(true);
//            popupWindow.showAtLocation(this.mRootView, 48, 1800, 300);
//            AsyncTask.execute(new Runnable() {
//                public void run() {
//                    TCImageUtil.save2MediaStore(CustomSuperPlayerView.this.mContext, bmp);
//                }
//            });
//            this.postDelayed(new Runnable() {
//                public void run() {
//                    popupWindow.dismiss();
//                }
//            }, 3000L);
//        }
//    }
//
//    private void rotateScreenOrientation(int orientation) {
//        switch(orientation) {
//            case 1:
//                ((Activity)this.mContext).setRequestedOrientation(0);
//                break;
//            case 2:
//                ((Activity)this.mContext).setRequestedOrientation(1);
//        }
//    }
//
//    public void onPlayEvent(TXVodPlayer player, int event, Bundle param) {
//        if (event != 2005) {
//            String playEventLog = "TXVodPlayer onPlayEvent event: " + event + ", " + param.getString("EVT_MSG");
//            TXCLog.d("CustomSuperPlayerView", playEventLog);
//        }
//
//        switch(event) {
//            case 2003:
//                if (this.mChangeHWAcceleration) {
//                    TXCLog.i("CustomSuperPlayerView", "seek pos:" + this.mSeekPos);
//                    this.mControllerCallback.onSeekTo(this.mSeekPos);
//                    this.mChangeHWAcceleration = false;
//                }
//                break;
//            case 2004:
//                this.updatePlayState(1);
//                break;
//            case 2005:
//                int progress = param.getInt("EVT_PLAY_PROGRESS_MS");
//                int duration = param.getInt("EVT_PLAY_DURATION_MS");
//                this.updateVideoProgress((long)(progress / 1000), (long)(duration / 1000));
//                break;
//            case 2006:
//                this.updatePlayState(4);
//            case 2007:
//            case 2008:
//            case 2009:
//            case 2010:
//            case 2011:
//            case 2012:
//            default:
//                break;
//            case 2013:
//                this.mControllerWindow.hideBackground();
//                this.updatePlayState(1);
//                if (this.mIsMultiBitrateStream) {
//                    List<TXBitrateItem> bitrateItems = this.mVodPlayer.getSupportedBitrates();
//                    if (bitrateItems == null || bitrateItems.size() == 0) {
//                        return;
//                    }
//
//                    Collections.sort(bitrateItems);
//                    List<TCVideoQuality> videoQualities = new ArrayList();
//                    int size = bitrateItems.size();
//
//                    TXBitrateItem bitrateItem;
//                    TCVideoQuality defaultVideoQuality;
//                    for(int i = 0; i < size; ++i) {
//                        bitrateItem = (TXBitrateItem)bitrateItems.get(i);
//                        defaultVideoQuality = TCVideoQualityUtil.convertToVideoQuality(bitrateItem, i);
//                        videoQualities.add(defaultVideoQuality);
//                    }
//
//                    if (!this.mDefaultQualitySet) {
//                        TXBitrateItem defaultItem = (TXBitrateItem)bitrateItems.get(bitrateItems.size() - 1);
//                        this.mVodPlayer.setBitrateIndex(defaultItem.index);
//                        bitrateItem = (TXBitrateItem)bitrateItems.get(bitrateItems.size() - 1);
//                        defaultVideoQuality = TCVideoQualityUtil.convertToVideoQuality(bitrateItem, bitrateItems.size() - 1);
//                        this.mControllerFullScreen.updateVideoQuality(defaultVideoQuality);
//                        this.mDefaultQualitySet = true;
//                    }
//
//                    this.mControllerFullScreen.setVideoQualityList(videoQualities);
//                }
//        }
//
//        if (event < 0) {
//            this.mVodPlayer.stopPlay(true);
//            this.updatePlayState(2);
//            Toast.makeText(this.mContext, param.getString("EVT_MSG"), Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//    public void onNetStatus(TXVodPlayer player, Bundle status) {
//    }
//
//    public void onPlayEvent(int event, Bundle param) {
//        if (event != 2005) {
//            String playEventLog = "TXLivePlayer onPlayEvent event: " + event + ", " + param.getString("EVT_MSG");
//            TXCLog.d("CustomSuperPlayerView", playEventLog);
//        }
//
//        switch(event) {
//            case -2307:
//                Toast.makeText(this.mContext, "清晰度切换失败", Toast.LENGTH_SHORT).show();
//                break;
//            case -2301:
//            case 2006:
//                if (this.mCurrentPlayType == 3) {
//                    this.mControllerCallback.onResumeLive();
//                    Toast.makeText(this.mContext, "时移失败,返回直播", Toast.LENGTH_SHORT).show();
//                    this.updatePlayState(1);
//                } else {
//                    this.stopPlay();
//                    this.updatePlayState(4);
//                    if (event == -2301) {
//                        Toast.makeText(this.mContext, "网络不给力,点击重试", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(this.mContext, param.getString("EVT_MSG"), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            case 2003:
//            default:
//                break;
//            case 2004:
//                this.updatePlayState(1);
//                if (this.mWatcher != null) {
//                    this.mWatcher.exitLoading();
//                }
//                break;
//            case 2005:
//                int progress = param.getInt("EVT_PLAY_PROGRESS_MS");
//                this.mMaxLiveProgressTime = (long)progress > this.mMaxLiveProgressTime ? (long)progress : this.mMaxLiveProgressTime;
//                this.updateVideoProgress((long)(progress / 1000), this.mMaxLiveProgressTime / 1000L);
//                break;
//            case 2007:
//            case 2103:
//                this.updatePlayState(3);
//                if (this.mWatcher != null) {
//                    this.mWatcher.enterLoading();
//                }
//                break;
//            case 2013:
//                this.updatePlayState(1);
//                break;
//            case 2015:
//                Toast.makeText(this.mContext, "清晰度切换成功", Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//    public void onNetStatus(Bundle status) {
//    }
//
//    public void requestPlayMode(int playMode) {
//        if (playMode == 1) {
//            if (this.mControllerCallback != null) {
//                this.mControllerCallback.onSwitchPlayMode(1);
//            }
//        } else if (playMode == 3) {
//            if (this.mPlayerViewCallback != null) {
//                this.mPlayerViewCallback.onStartFloatWindowPlay();
//            }
//
//            if (this.mControllerCallback != null) {
//                this.mControllerCallback.onSwitchPlayMode(3);
//            }
//        }
//
//    }
//
//    private boolean checkOp(Context context, int op) {
//        if (VERSION.SDK_INT >= 19) {
//            AppOpsManager manager = (AppOpsManager)context.getSystemService(Context.APP_OPS_SERVICE);
//
//            try {
//                Method method = AppOpsManager.class.getDeclaredMethod("checkOp", Integer.TYPE, Integer.TYPE, String.class);
//                return 0 == (Integer)method.invoke(manager, op, Binder.getCallingUid(), context.getPackageName());
//            } catch (Exception var5) {
//                TXCLog.e("CustomSuperPlayerView", Log.getStackTraceString(var5));
//            }
//        }
//
//        return true;
//    }
//
//    public int getPlayMode() {
//        return this.mCurrentPlayMode;
//    }
//
//    public int getPlayState() {
//        return this.mCurrentPlayState;
//    }
//
//    public void release() {
//        if (this.mControllerWindow != null) {
//            this.mControllerWindow.release();
//        }
//
//        if (this.mControllerFullScreen != null) {
//            this.mControllerFullScreen.release();
//        }
//
//        if (this.mControllerFloat != null) {
//            this.mControllerFloat.release();
//        }
//
//    }
//
//    protected void finalize() throws Throwable {
//        super.finalize();
//
//        try {
//            this.release();
//        } catch (Exception var2) {
//            TXCLog.e("CustomSuperPlayerView", Log.getStackTraceString(var2));
//        } catch (Error var3) {
//            TXCLog.e("CustomSuperPlayerView", Log.getStackTraceString(var3));
//        }
//
//    }
//
//    public interface OnCustomSuperPlayerViewCallback {
//        void onStartFullScreenPlay();
//
//        void onStopFullScreenPlay();
//
//        void onClickFloatCloseBtn();
//
//        void onClickSmallReturnBtn();
//
//        void onStartFloatWindowPlay();
//    }
//
//    private static enum PLAYER_TYPE {
//        PLAYER_TYPE_NULL,
//        PLAYER_TYPE_VOD,
//        PLAYER_TYPE_LIVE;
//
//        private PLAYER_TYPE() {
//        }
//    }
//}

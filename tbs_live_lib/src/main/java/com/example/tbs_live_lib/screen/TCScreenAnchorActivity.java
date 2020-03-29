package com.example.tbs_live_lib.screen;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tbs_live_lib.R;
import com.example.tbs_live_lib.config.TCConstants;
import com.example.tbs_live_lib.entity.LiveBean;
import com.example.tbs_live_lib.screen.widget.FloatingCameraView;
import com.example.tbs_live_lib.screen.widget.FloatingView;
import com.tencent.rtmp.ITXLivePushListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.yidao.module_lib.base.BaseView;
import com.yidao.module_lib.utils.LogUtils;

/**
 * Module:   TCScreenAnchorActivity
 * <p>
 * Function: 屏幕录制推流的页面
 * <p>
 * <p>
 * 注：Android 在 API 21+ 的版本才支持屏幕录制功能
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class TCScreenAnchorActivity extends BaseView implements View.OnClickListener {

    public static int OVERLAY_PERMISSION_REQ_CODE = 1234;

    //悬浮摄像窗以及悬浮球
    private FloatingView mFloatingView;              // 悬浮球
    private FloatingCameraView mFloatingCameraView;        // 悬浮摄像框
    private ImageView mCameraBtn;                 // 开启-关闭摄像头按钮
    private boolean mInCamera = false;          // 摄像头是否打开
    private Intent serviceIntent;              // 后台服务

    private TXLivePusher mMLivePusher;

    @Override
    protected int getView() {
        return R.layout.activity_screen_anchor;
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (mFloatingView.isShown()) {
            mFloatingView.dismiss();
        }

        if (null != mFloatingCameraView && mFloatingCameraView.isShown()) {
            mFloatingCameraView.dismiss();
            mCameraBtn.setImageResource(R.mipmap.camera_off);
            mInCamera = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        requestDrawOverLays();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFloatingView.isShown()) {
            mFloatingView.dismiss();
        }

        if (null != mFloatingCameraView) {
            if (mFloatingCameraView.isShown()) {
                mFloatingCameraView.dismiss();
            }
            mFloatingCameraView.release();
        }

        mMLivePusher.stopScreenCapture();
        mMLivePusher.setPushListener(null);
        mMLivePusher.stopPusher();
    }

    @Override
    public void init() {
        LiveBean liveBean = (LiveBean) getIntent().getSerializableExtra(TCConstants.liveKey);

        mMLivePusher = new TXLivePusher(getCtx());
        TXLivePushConfig mLivePushConfig = new TXLivePushConfig();
        mLivePushConfig.setPauseImg(R.mipmap.pause_publish, 10);
        mLivePushConfig.setVideoEncodeGop(2);
        mMLivePusher.setVideoQuality(TXLiveConstants.VIDEO_QUALITY_HIGH_DEFINITION, false, false);
        mMLivePusher.setConfig(mLivePushConfig);

        mMLivePusher.setPushListener(new ITXLivePushListener() {
            @Override
            public void onPushEvent(int i, Bundle bundle) {
                if(i== TXLiveConstants.PUSH_EVT_CONNECT_SUCC ){
                    LogUtils.d("推流成功");

                }

                LogUtils.d("onPushEvent：i"+i+"");
            }

            @Override
            public void onNetStatus(Bundle bundle) {
                LogUtils.d("onNetStatus");
            }
        });

//        String rtmpUrl = "rtmp://2157.livepush.myqcloud.com/live/xxxxxx";
        String rtmpUrl = liveBean.getPushURL();
        int code = mMLivePusher.startPusher(rtmpUrl);
        mMLivePusher.startScreenCapture();

        LogUtils.d("startPusher：code="+code+"");


        mFloatingView = new FloatingView(getApplicationContext(), R.layout.view_floating_default);
        mFloatingView.setPopupWindow(R.layout.popup_layout);

        mCameraBtn = mFloatingView.getPopupView().findViewById(R.id.btn_camera);
        mFloatingCameraView = new FloatingCameraView(getApplicationContext());
        mFloatingView.setOnPopupItemClickListener(this);
    }


    public void requestDrawOverLays() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N && !Settings.canDrawOverlays(TCScreenAnchorActivity.this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + TCScreenAnchorActivity.this.getPackageName()));
            startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
        } else {
            showFloatingView();
        }
    }

    private void showFloatingView() {
        if (!mFloatingView.isShown()) {
            mFloatingView.show();
            mFloatingView.setOnPopupItemClickListener(this);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_return:
                //悬浮球返回主界面按钮
                Toast.makeText(getApplicationContext(), "返回主界面", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), TCScreenAnchorActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                getApplicationContext().startActivity(intent);
                try {
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
                    pendingIntent.send();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_camera:
                //camera悬浮窗
                triggerFloatingCameraView();
                break;
            case R.id.btn_close:
//                showExitInfoDialog("当前正在直播，是否退出直播？", false);
                break;
            default:
                break;
        }
    }


    /**
     * 处理cameraview初始化、权限申请 以及 cameraview的显示与隐藏
     */
    public void triggerFloatingCameraView() {
        //trigger
        if (mInCamera) {
            Toast.makeText(getApplicationContext(), "关闭摄像头", Toast.LENGTH_SHORT).show();
            mCameraBtn.setImageResource(R.mipmap.camera_off);
            mFloatingCameraView.dismiss();
        } else {
            //show失败显示错误信息
            if (!mFloatingCameraView.show()) {
                Toast.makeText(getApplicationContext(), "打开摄像头权限失败,请在系统设置打开摄像头权限", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(getApplicationContext(), "打开摄像头", Toast.LENGTH_SHORT).show();
            mCameraBtn.setImageResource(R.mipmap.camera_on);
        }
        mInCamera = !mInCamera;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N && !Settings.canDrawOverlays(TCScreenAnchorActivity.this)) {
                Toast.makeText(getApplicationContext(), "请在设置-权限设置里打开悬浮窗权限", Toast.LENGTH_SHORT).show();
            } else {
                showFloatingView();
            }
        }
    }

}

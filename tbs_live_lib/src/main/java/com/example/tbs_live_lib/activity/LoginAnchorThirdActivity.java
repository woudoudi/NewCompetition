package com.example.tbs_live_lib.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.http_lib.bean.StartLiveRequestBean;
import com.example.http_lib.utils.UserCacheHelper;
import com.example.live_room_lib.liveroom.MLVBLiveRoomImpl;
import com.example.live_room_lib.liveroom.roomutil.http.HttpRequests;
import com.example.live_room_lib.liveroom.roomutil.http.HttpResponse;
import com.example.tbs_live_lib.R;
import com.example.tbs_live_lib.base.BaseUrlView;
import com.example.tbs_live_lib.config.TCConstants;
import com.example.tbs_live_lib.entity.LiveBean;
import com.example.tbs_live_lib.screen.TCScreenAnchorActivity;
import com.tencent.liteav.basic.log.TXCLog;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.LogUtils;
import com.yidao.module_lib.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

import static com.example.live_room_lib.liveroom.MLVBLiveRoomImpl.mServerDomain;

public class LoginAnchorThirdActivity extends BaseUrlView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_live_title)
    TextView tvLiveTitle;
    @BindView(R.id.tv_good_hero)
    TextView tvGoodHero;
    @BindView(R.id.tv_win_rate)
    TextView tvWinRate;
    @BindView(R.id.iv_live_cover)
    ImageView ivLiveCover;

    private HttpRequests mHttpRequest;


    @Override
    protected int getView() {
        return R.layout.activity_login_anchor_third;
    }

    @Override
    public void init() {
        tvTitle.setText("我要直播");

        mHttpRequest = new HttpRequests(mServerDomain);

    }

    @OnClick({R.id.iv_back, R.id.tv_start_live})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                ViewManager.getInstance().finishView();
                break;
            case R.id.tv_start_live:

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N && !Settings.canDrawOverlays(LoginAnchorThirdActivity.this)) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + LoginAnchorThirdActivity.this.getPackageName()));
                    startActivityForResult(intent, 11);
                } else {
                    request();
                }
                break;
        }
    }

    private void request() {
        StartLiveRequestBean requestBean = new StartLiveRequestBean();
        requestBean.title = "王者荣耀";
        requestBean.goodHero = "妖姬";
        requestBean.winPercent = 60;
        requestBean.cover = "https://cdn.pixabay.com/photo/2020/03/21/19/27/sea-4955005_960_720.jpg";
        requestBean.messageBoard = "今晚打老虎";
        requestBean.lastWin = true;
        requestBean.lastHeadRatio = "10:3";

        mPresenter.startLive(requestBean);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N && !Settings.canDrawOverlays(LoginAnchorThirdActivity.this)) {
                Toast.makeText(getApplicationContext(), "请在设置-权限设置里打开悬浮窗权限", Toast.LENGTH_SHORT).show();
            } else {
                request();
            }
        }
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if (success) {
            if (requestCls == StartLiveRequestBean.class) {
                LiveBean liveBean = JSON.parseObject(responseBean.getData(), LiveBean.class);

                String roomInfo = liveBean.getTitle();
                try {
                    roomInfo = new JSONObject()
                            .put("title", liveBean.getTitle())
                            .put("frontcover", liveBean.getCover())
                            .put("location", "")
                            .toString();
                } catch (JSONException e) {
                    roomInfo = liveBean.getTitle();
                }
                mHttpRequest.setUserID(liveBean.getUserId());
                mHttpRequest.setToken(UserCacheHelper.getToken());
                doCreateRoom(liveBean.getUserId(), liveBean.getUserId(), roomInfo, new MLVBLiveRoomImpl.StandardCallback() {
                    @Override
                    public void onError(int errCode, String errInfo) {

                    }

                    @Override
                    public void onSuccess() {

                    }
                });


//                Bundle bundle = new Bundle();
//                bundle.putSerializable(TCConstants.liveKey, liveBean);
//                skipActivity(TCScreenAnchorActivity.class, bundle);

            }
        } else {
            ToastUtil.showShortToast(responseBean.getRetMsg());
        }
    }


    protected void doCreateRoom(String userID,final String roomID, String roomInfo, final MLVBLiveRoomImpl.StandardCallback callback){
        mHttpRequest.createRoom(roomID, userID, roomInfo,
                new HttpRequests.OnResponseCallback<HttpResponse.CreateRoom>() {
                    @Override
                    public void onResponse(int retcode, String retmsg, HttpResponse.CreateRoom data) {
                        if (retcode != HttpResponse.CODE_OK || data == null || data.roomID == null) {
                            String msg = "[LiveRoom] 创建房间错误[" + retmsg + ":" + retcode + "]";
                            LogUtils.e(msg);
                            callback.onError(retcode, msg);
                        } else {
                            LogUtils.d("[LiveRoom] 创建直播间 ID[" + data.roomID + "] 成功 ");
//                            mCurrRoomID = data.roomID;
                            callback.onSuccess();
                        }
                    }
                });
    }
}

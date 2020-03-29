package com.qgc.user.live.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.example.administrator.competition.R;
import com.example.administrator.competition.application.WXInfo;
import com.example.administrator.competition.entity.CommonConfig;
import com.example.http_lib.HttpClient;
import com.example.http_lib.bean.TelBindtripartiteBean;
import com.example.http_lib.bean.TripartiteLoginBean;
import com.example.http_lib.bean.WXTokenBean;
import com.example.http_lib.bean.WXUserInfoBean;
import com.example.http_lib.response.UserInfoBean;
import com.example.http_lib.response.WXTokenResp;
import com.example.http_lib.response.WXUserInfoResp;
import com.example.http_lib.utils.UserCacheHelper;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.opensdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.base.http.callback.IHttpCallBack;
import com.yidao.module_lib.utils.LogUtils;
import com.yidao.module_lib.utils.PhoneUtils;
import com.yidao.module_lib.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/15
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler{
    private static String TAG = "MicroMsg.WXEntryActivity";

    private IWXAPI api;
    private MyHandler handler;
    private TripartiteLoginBean tripartiteLoginBean;

    private static boolean isLogin = true;

    public static boolean isIsLogin() {
        return isLogin;
    }

    public static void setIsLogin(boolean isLogin) {
        WXEntryActivity.isLogin = isLogin;
    }

    private static class MyHandler extends Handler {
        private final WeakReference<WXEntryActivity> wxEntryActivityWeakReference;

        public MyHandler(WXEntryActivity wxEntryActivity){
            wxEntryActivityWeakReference = new WeakReference<WXEntryActivity>(wxEntryActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            int tag = msg.what;
            switch (tag) {
                /*case NetworkUtil.GET_TOKEN: {
                    Bundle data = msg.getData();
                    JSONObject json = null;
                    try {
                        json = new JSONObject(data.getString("result"));
                        String openId, accessToken, refreshToken, scope;
                        openId = json.getString("openid");
                        accessToken = json.getString("access_token");
                        refreshToken = json.getString("refresh_token");
                        scope = json.getString("scope");
                        Intent intent = new Intent(wxEntryActivityWeakReference.get(), SendToWXActivity.class);
                        intent.putExtra("openId", openId);
                        intent.putExtra("accessToken", accessToken);
                        intent.putExtra("refreshToken", refreshToken);
                        intent.putExtra("scope", scope);
                        wxEntryActivityWeakReference.get().startActivity(intent);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }*/
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();
        api = WXInfo.getInstance().getApi();
        handler = new MyHandler(this);
        try {
            Intent intent = getIntent();
            api.handleIntent(intent, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                goToGetMsg();
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                goToShowMsg((ShowMessageFromWX.Req) req);
                break;
            default:
                break;
        }
        finish();
    }

    @Override
    public void onResp(BaseResp resp) {
        int result = 0;

        switch (resp.errCode) {

            case BaseResp.ErrCode.ERR_OK:
                result = R.string.errcode_success;

                LogUtils.d("wx ok");
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = R.string.errcode_cancel;
                LogUtils.d("wx cancel");
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = R.string.errcode_deny;
                LogUtils.d("wx denied");
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                result = R.string.errcode_unsupported;
                LogUtils.d("wx unsupport");
                break;
            default:
                LogUtils.d("wx unknown");
                result = R.string.errcode_unknown;
                break;

        }



        if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
            SendAuth.Resp authResp = (SendAuth.Resp)resp;
            final String code = authResp.code;
            LogUtils.d(" wx COMMAND_SENDAUTH  code : "+code);
            WXTokenBean wxTokenBean = new WXTokenBean();
            wxTokenBean.setAppid(WXInfo.APP_ID);
            wxTokenBean.setCode(code);
            wxTokenBean.setSecret(WXInfo.APP_SECRET);
            wxTokenBean.setGrant_type("authorization_code");
            HttpClient.request(wxTokenBean, false,new IHttpCallBack() {
                @Override
                public void success(ResponseBean responseBean) {
                    LogUtils.d("wx success access_token : "+responseBean.toString());
                    WXTokenResp wxTokenResp = JSON.parseObject(responseBean.getData(), WXTokenResp.class);
                    getWxUserInfo(wxTokenResp);
                }

                @Override
                public void failed(ResponseBean responseBean) {
                    ToastUtil.showLongToast(responseBean.getRetMsg());
                    LogUtils.d("wx fail access_token : "+responseBean.getRetMsg());
                }
            });
        }


        if(resp.getType()==ConstantsAPI.COMMAND_PAY_BY_WX){
            LogUtils.d("onPayFinish,errCode="+resp.errCode);
          //支付回调
            ToastUtil.showLongToast("支付完成");
        }

        if (resp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX){
            ToastUtil.showLongToast("分享成功");
        }

        finish();


    }

    private void getWxUserInfo(WXTokenResp wxTokenResp) {
        final WXUserInfoBean wxUserInfoBean = new WXUserInfoBean();
        wxUserInfoBean.setAccess_token(wxTokenResp.getAccess_token());
        wxUserInfoBean.setOpenid(wxTokenResp.getOpenid());
        HttpClient.request(wxUserInfoBean, false,new IHttpCallBack() {
            @Override
            public void success(ResponseBean responseBean) {
                WXUserInfoResp wxUserInfoResp = JSON.parseObject(responseBean.getData(), WXUserInfoResp.class);
                if (isLogin) {
                    login(wxUserInfoResp.getUnionid(), CommonConfig.WXLOGIN, PhoneUtils.getPhoneSign(WXEntryActivity.this), wxUserInfoResp.getHeadimgurl(), String.valueOf(wxUserInfoResp.getSex()),
                            wxUserInfoResp.getNickname(), null, wxUserInfoResp.getProvince());
                }else {
                    bind(wxUserInfoResp.getUnionid(), CommonConfig.WXLOGIN, PhoneUtils.getPhoneSign(WXEntryActivity.this), wxUserInfoResp.getHeadimgurl(), String.valueOf(wxUserInfoResp.getSex()),
                            wxUserInfoResp.getNickname(), null, wxUserInfoResp.getProvince());
                }
                /* UserInfoModel userInfoModel = new UserInfoModel(new WXLoginPress());
                LoginBean loginBean = new LoginBean();
                loginBean.setUserType(String.valueOf(Config.OTHERLOGIN));
                loginBean.setUserAccount(wxUserInfoResp.getUnionid());
                loginBean.setUserHeadPortrait(wxUserInfoResp.getHeadimgurl());
                loginBean.setUserSex(String.valueOf(wxUserInfoResp.getSex()));
                loginBean.setUserNickName(wxUserInfoResp.getNickname());
                userInfoModel.setBean(loginBean);
                userInfoModel.request(false);*/
            }

            @Override
            public void failed(ResponseBean responseBean) {
                ToastUtil.showLongToast(responseBean.getRetMsg());
            }
        });

    }

    private void bind(String openid, int wxlogin, String phoneSign, String headimgurl, String sex, String nickname, String userBirthday, String province) {
        TelBindtripartiteBean tripartiteBindTelBean = new TelBindtripartiteBean();
        tripartiteBindTelBean.openId = openid;
        tripartiteBindTelBean.type = wxlogin;
        tripartiteBindTelBean.userDeviceId = phoneSign;
        tripartiteBindTelBean.userHeadPortrait = headimgurl;
        tripartiteBindTelBean.userSex = sex;
        tripartiteBindTelBean.userBirthday = userBirthday;
        tripartiteBindTelBean.userCity = province;
        tripartiteBindTelBean.userNickName = nickname;
        HttpClient.request(tripartiteBindTelBean, false,new IHttpCallBack() {
            @Override
            public void success(ResponseBean responseBean) {
                LogUtils.d("wx login = "+responseBean.getRetMsg());
                UserInfoBean userInfoBean = JSON.parseObject(responseBean.getData(), UserInfoBean.class);
                UserCacheHelper.saveUserInfo(JSON.toJSONString(userInfoBean));
                ToastUtil.showLongToast("微信授权成功");
                EventBus.getDefault().post(new WXTokenBean());
            }

            @Override
            public void failed(ResponseBean responseBean) {
                ToastUtil.showLongToast(responseBean.getRetMsg());
            }
        });

    }

    private void login(String openId,int type,String userDeviceId, String headUrl, String sex, String nickName,String userBirthday,String userCity) {
        tripartiteLoginBean = new TripartiteLoginBean();
        tripartiteLoginBean.openId = openId;
        tripartiteLoginBean.type = type;
        tripartiteLoginBean.userDeviceId = userDeviceId;
        tripartiteLoginBean.userHeadPortrait = headUrl;
        tripartiteLoginBean.userSex = sex;
        tripartiteLoginBean.userBirthday = userBirthday;
        tripartiteLoginBean.userCity = userCity;
        tripartiteLoginBean.userNickName = nickName;
        HttpClient.request(tripartiteLoginBean, false,new IHttpCallBack() {
            @Override
            public void success(ResponseBean responseBean) {
                LogUtils.d("wx login = "+responseBean.getRetMsg());
                UserInfoBean userInfoBean = JSON.parseObject(responseBean.getData(), UserInfoBean.class);
                ToastUtil.showLongToast("微信授权成功");
                UserCacheHelper.saveUserInfo(JSON.toJSONString(userInfoBean));
                if (TextUtils.isEmpty(userInfoBean.getWechatOpenId())) {

                  /*  Intent intent = new Intent(WXEntryActivity.this, RegisterView.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt(Config.REGISTERTYPE, Config.OHTERREGISTER);
                    bundle.putSerializable(Config.BEAN, tripartiteLoginBean);
                    intent.putExtras(bundle);
                    startActivity(intent);*/

                    EventBus.getDefault().post(new WXBindEventBean(false,tripartiteLoginBean));
                }else {

                    EventBus.getDefault().post(new WXBindEventBean(true,null));
                   /*
                    Intent intent = new Intent(WXEntryActivity.this, MainView.class);
                    startActivity(intent);
                    ViewManager.getInstance().finishView(LoginView.class);*/
                }

            }

            @Override
            public void failed(ResponseBean responseBean) {
                if (responseBean.getRetCode() == 300101){
                    EventBus.getDefault().post(new WXBindEventBean(false,tripartiteLoginBean));
                  /*  Intent intent = new Intent(WXEntryActivity.this, RegisterView.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt(Config.REGISTERTYPE, Config.OHTERREGISTER);
                    bundle.putSerializable(Config.BEAN, tripartiteLoginBean);
                    intent.putExtras(bundle);
                    startActivity(intent);*/
                }
                ToastUtil.showLongToast(responseBean.getRetMsg());
            }
        });
    }



    private void goToGetMsg() {
       /* Intent intent = new Intent(this, GetFromWXActivity.class);
        intent.putExtras(getIntent());
        startActivity(intent);
        finish();*/
    }

    private void goToShowMsg(ShowMessageFromWX.Req showReq) {
        WXMediaMessage wxMsg = showReq.message;
        WXAppExtendObject obj = (WXAppExtendObject) wxMsg.mediaObject;

        StringBuffer msg = new StringBuffer();
        msg.append("description: ");
        msg.append(wxMsg.description);
        msg.append("\n");
        msg.append("extInfo: ");
        msg.append(obj.extInfo);
        msg.append("\n");
        msg.append("filePath: ");
        msg.append(obj.filePath);

       /* Intent intent = new Intent(this, ShowFromWXActivity.class);
        intent.putExtra(Constants.ShowMsgActivity.STitle, wxMsg.title);
        intent.putExtra(Constants.ShowMsgActivity.SMessage, msg.toString());
        intent.putExtra(Constants.ShowMsgActivity.BAThumbData, wxMsg.thumbData);
        startActivity(intent);
        finish();*/
    }
}
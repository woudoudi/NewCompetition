package com.example.administrator.competition.activity.login;

import com.example.administrator.competition.HomeActivity;
import com.example.administrator.competition.R;
import com.example.administrator.competition.base.BaseUrlView;
import com.example.http_lib.bean.GetTokenRequestBean;
import com.example.http_lib.bean.LoginRequestBean;
import com.example.http_lib.response.UserInfoBean;
import com.example.http_lib.utils.UserCacheHelper;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.manager.PermissionManager;
import com.yidao.module_lib.utils.ToastUtil;

import java.util.List;

import cn.jpush.im.android.api.ChatRoomManager;
import cn.jpush.im.android.api.callback.RequestCallback;
import cn.jpush.im.android.api.model.ChatRoomInfo;


public class WelcomeActivity extends BaseUrlView {



    @Override
    protected int getView() {
        return R.layout.activity_welcome;
    }

    @Override
    public void init() {
        PermissionManager.getInstance().setIPermissionLiatener(new PermissionManager.IPermissionListener() {
            @Override
            public void getPermissionSuccess() {
//                mPresenter.getToken();

//                skipActivityByFinish(HomeActivity.class);

                UserInfoBean userInfo = UserCacheHelper.getUserInfo();
                if(userInfo==null || !userInfo.isLogin()){
                    skipActivityByFinish(LoginUserFirstActivity.class);
                } else {
                    mPresenter.login(userInfo.getUserName(),userInfo.getPassword());
                }
            }
        });
        PermissionManager.getInstance().requestPermissions(this);
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if (success) {
            if (requestCls == GetTokenRequestBean.class) {
                UserCacheHelper.setToken(responseBean.getData());

                UserInfoBean userInfo = UserCacheHelper.getUserInfo();
                if(userInfo==null || !userInfo.isLogin()){
                    skipActivityByFinish(LoginUserFirstActivity.class);
                } else {
                    mPresenter.login(userInfo.getUserName(),userInfo.getPassword());
                }
            }
            if(requestCls==LoginRequestBean.class){
                skipActivityByFinish(HomeActivity.class);
            }
        } else {
            ToastUtil.showShortToast(responseBean.getRetMsg());
        }
    }

}

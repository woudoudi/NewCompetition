package com.example.tbs_live_lib.activity;

import android.Manifest;

import com.example.http_lib.bean.GetTokenRequestBean;
import com.example.http_lib.utils.UserCacheHelper;
import com.example.tbs_live_lib.R;
import com.example.tbs_live_lib.base.BaseUrlView;
import com.yidao.module_lib.base.BaseView;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.manager.PermissionManager;
import com.yidao.module_lib.utils.ToastUtil;


public class WelcomeActivity extends BaseUrlView {

    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.FOREGROUND_SERVICE
    };

    @Override
    protected int getView() {
        return R.layout.activity_welcome;
    }

    @Override
    public void init() {
        PermissionManager.getInstance().setIPermissionLiatener(new PermissionManager.IPermissionListener() {
            @Override
            public void getPermissionSuccess() {
                mPresenter.getToken();
            }
        });
        PermissionManager.getInstance().requestPermissions(this,permissions);
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if(success){
            if(requestCls==GetTokenRequestBean.class){
                UserCacheHelper.setToken(responseBean.getData());
                skipActivityByFinish(LoginAnchorFirstActivity.class);
            }
        } else {
            ToastUtil.showShortToast(responseBean.getRetMsg());
        }
    }
}

package com.example.administrator.competition.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.competition.R;
import com.example.administrator.competition.activity.my.MyBetActivity;
import com.example.administrator.competition.activity.my.MyIntegralActivity;
import com.example.administrator.competition.activity.my.MyInviteActivity;
import com.example.administrator.competition.activity.my.MyRechargeActivity;
import com.example.administrator.competition.activity.my.MySettingActivity;
import com.example.administrator.competition.base.BaseUrlFragment;
import com.example.administrator.competition.entity.CommonConfig;
import com.example.http_lib.bean.UserInfoRequestBean;
import com.example.http_lib.response.OverUserInfoBean;
import com.example.http_lib.utils.UserCacheHelper;
import com.yidao.module_lib.base.BaseFragment;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.utils.CommonGlideUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class MyFragment extends BaseUrlFragment {

    @BindView(R.id.iv_head)
    ImageView ivHead;

    @BindView(R.id.tv_name)
    TextView tvName;

    @Override
    protected int getLayoutId() {
        return R.layout.framelayout_my;
    }

    @Override
    protected void initPress() {
    }

    @Override
    protected void init() {
        mPresenter.getUserInfo();
    }

    @OnClick({R.id.rl_integral, R.id.rl_recharge,  R.id.rl_invite, R.id.rl_bet,R.id.rl_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_integral:
                skipActivity(MyIntegralActivity.class);
                break;
            case R.id.rl_recharge:
                skipActivity(MyRechargeActivity.class);
                break;
            case R.id.rl_invite:
                skipActivity(MyInviteActivity.class);
                break;
            case R.id.rl_bet:
                Bundle bundle = new Bundle();
                bundle.putInt(CommonConfig.betRecordKey,CommonConfig.betBothValue);
                skipActivity(MyBetActivity.class,bundle);
                break;
            case R.id.rl_setting:
                skipActivity(MySettingActivity.class);
                break;
        }
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if(success){
            if(requestCls==UserInfoRequestBean.class){
                UserCacheHelper.saveOverseaUserInfo(responseBean.getData());

                OverUserInfoBean userInfoBean = JSON.parseObject(responseBean.getData(), OverUserInfoBean.class);
                tvName.setText(userInfoBean.getNickname());
                CommonGlideUtils.showCirclePhoto(getCtx(),userInfoBean.getAvatar(),ivHead);
            }
        }
    }
}

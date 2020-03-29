package com.example.administrator.competition.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.competition.R;
import com.example.administrator.competition.activity.home.HomeGuessActivity;
import com.example.administrator.competition.activity.home.HomeKindActivity;
import com.example.administrator.competition.activity.my.MyRechargeActivity;
import com.example.administrator.competition.base.BaseUrlFragment;
import com.example.http_lib.bean.GetIntegralInfoRequestBean;
import com.example.http_lib.bean.GetKingLevelListRequestBean;
import com.example.http_lib.bean.UploadFileRequestBean;
import com.example.http_lib.bean.UserInfoRequestBean;
import com.example.http_lib.websocket.WebSocketManager;
import com.yidao.module_lib.base.BaseFragment;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.utils.CommonGlideUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFirstFragment extends BaseUrlFragment {

    @BindView(R.id.iv_head)
    ImageView ivHead;

    @BindView(R.id.tv_integral)
    TextView tvIntegral;

    @BindView(R.id.tv_trumpet)
    TextView tvTrumpet;

    @BindView(R.id.tv_king_title)
    TextView tvKingTitle;

    @BindView(R.id.tv_king_content)
    TextView tvKingContent;

    @BindView(R.id.tv_trumpet_title)
    TextView tvTrumpetTitle;

    @BindView(R.id.tv_trumpet_content)
    TextView tvTrumpetContent;

    @Override
    protected int getLayoutId() {
        return R.layout.framelayout_home_first;
    }

    @Override
    protected void initPress() {

    }

    @Override
    protected void init() {
        mPresenter.getUserInfo();

        mPresenter.getIntegralInfo();

        mPresenter.getLevelList();
    }

    @OnClick({R.id.iv_add, R.id.iv_custom, R.id.rl_king_mode, R.id.rl_guess_mode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_add:
                skipActivity(MyRechargeActivity.class);
                break;
            case R.id.iv_custom:
                WebSocketManager.getSocketManager().send("测试数据发送");
                break;
            case R.id.rl_king_mode:
                skipActivity(HomeKindActivity.class);
                break;
            case R.id.rl_guess_mode:
                skipActivity(HomeGuessActivity.class);
                break;
        }
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if(success){
            if(requestCls==UserInfoRequestBean.class){
                JSONObject object = JSON.parseObject(responseBean.getData());
                String avatar = object.getString("avatar");
                CommonGlideUtils.showCirclePhoto(getCtx(),avatar,ivHead);
            }
            if(requestCls==GetIntegralInfoRequestBean.class){
                JSONObject object = JSON.parseObject(responseBean.getData());
                Double score = object.getDouble("score");
                tvIntegral.setText(score+"");
            }
            if(requestCls==GetKingLevelListRequestBean.class){

            }
        }
    }
}

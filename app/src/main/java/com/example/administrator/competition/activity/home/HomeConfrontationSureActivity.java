package com.example.administrator.competition.activity.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.competition.R;
import com.example.administrator.competition.base.BaseUrlView;
import com.example.administrator.competition.dialog.GuessDialog;
import com.example.administrator.competition.entity.CommonConfig;
import com.example.administrator.competition.mvp.presenter.JCLoginPress;
import com.example.http_lib.bean.ConfirmResultRequestBean;
import com.example.http_lib.bean.DisputeRequestBean;
import com.example.http_lib.response.InviteBean;
import com.example.http_lib.utils.UserCacheHelper;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeConfrontationSureActivity extends BaseUrlView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_first_nick)
    TextView tvFirstNick;
    @BindView(R.id.tv_first_phone)
    TextView tvFirstPhone;
    @BindView(R.id.tv_first_number)
    TextView tvFirstNumber;
    @BindView(R.id.tv_second_nick)
    TextView tvSecondNick;
    @BindView(R.id.tv_second_phone)
    TextView tvSecondPhone;
    @BindView(R.id.tv_second_number)
    TextView tvSecondNumber;
    @BindView(R.id.tv_confrontation_sure)
    TextView tvConfrontationSure;
    @BindView(R.id.iv_first_state)
    ImageView ivFirstState;
    @BindView(R.id.iv_second_state)
    ImageView ivSecondState;

    private InviteBean mInviteBean;

    @Override
    protected int getView() {
        return R.layout.activity_home_confrontation_sure;
    }

    @Override
    public void init() {
        tvTitle.setText("对抗确认");

        mInviteBean = (InviteBean) getIntent().getSerializableExtra(CommonConfig.guessInviteKey);
    }


    @OnClick({R.id.iv_back, R.id.tv_confrontation_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                ViewManager.getInstance().finishView();
                break;
            case R.id.tv_confrontation_sure:
                // TODO: 2020/3/21 1.进行放分   2.进行仲裁
                GuessDialog guessDialog = new GuessDialog(getCtx());
                guessDialog.setCheckListener(new GuessDialog.OnItemClick() {
                    @Override
                    public void win() {
                        mPresenter.confirmResult(mInviteBean.getId(),true);
                    }
                    @Override
                    public void lose() {
                        mPresenter.confirmResult(mInviteBean.getId(),false);
                    }
                });
                guessDialog.show();
                break;
        }
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if (success) {
            if (requestCls == DisputeRequestBean.class) {
                JCLoginPress.jumpChatActivity(CommonConfig.Service,"客服");
            }
            if (requestCls == ConfirmResultRequestBean.class) {

            }
        } else {
            ToastUtil.showShortToast(responseBean.getRetMsg());
        }
    }
}

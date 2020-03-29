package com.example.tbs_live_lib.activity;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.tbs_live_lib.R;
import com.example.tbs_live_lib.base.BaseUrlView;
import com.yidao.module_lib.base.BaseView;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginAnchorSecondActivity extends BaseUrlView {

    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;

    @Override
    protected int getView() {
        return R.layout.activity_login_anchor_second;
    }


    @Override
    public void init() {

    }

    @OnClick(R.id.tv_start_live)
    public void onViewClicked() {
        skipActivity(LoginAnchorThirdActivity.class);
    }
}

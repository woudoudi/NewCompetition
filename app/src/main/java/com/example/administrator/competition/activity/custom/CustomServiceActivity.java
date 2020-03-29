package com.example.administrator.competition.activity.custom;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.competition.R;
import com.yidao.module_lib.base.BaseView;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.PhoneUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomServiceActivity extends BaseView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.tv_album)
    TextView tvAlbum;
    @BindView(R.id.tv_taking)
    TextView tvTaking;
    @BindView(R.id.ll_more)
    LinearLayout llMore;
    @BindView(R.id.iv_send)
    ImageView ivSend;


    @Override
    protected int getView() {
        return R.layout.activity_custom;
    }

    @Override
    public void init() {
        tvTitle.setText("客服");

        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                ivSend.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
    }


    @OnClick({R.id.iv_back, R.id.iv_talk_more, R.id.tv_album, R.id.tv_taking, R.id.iv_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                ViewManager.getInstance().finishView();
                break;
            case R.id.iv_talk_more:
                if (llMore.getVisibility() == View.VISIBLE) {
                    llMore.setVisibility(View.GONE);
                } else {
                    llMore.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_album:
                break;
            case R.id.tv_taking:
                break;
            case R.id.iv_send:
                break;
        }
    }
}

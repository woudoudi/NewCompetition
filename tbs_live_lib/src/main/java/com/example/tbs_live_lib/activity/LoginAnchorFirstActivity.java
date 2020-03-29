package com.example.tbs_live_lib.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.http_lib.bean.StartLiveRequestBean;
import com.example.tbs_live_lib.R;
import com.example.tbs_live_lib.base.BaseUrlView;
import com.yidao.module_lib.base.BaseView;
import com.yidao.module_lib.utils.PhoneUtils;
import com.yidao.module_lib.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginAnchorFirstActivity extends BaseUrlView {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.iv_next)
    ImageView ivNext;

    @Override
    protected int getView() {
        return R.layout.activity_login_anchor_first;
    }


    @Override
    public void init() {
        etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(PhoneUtils.isPhone(etPhone.getText().toString()) && !TextUtils.isEmpty(s.toString())){
                    ivNext.setSelected(true);
                } else {
                    ivNext.setSelected(false);
                }
            }
        });
    }


    @OnClick(R.id.iv_next)
    public void onViewClicked() {
        skipActivityByFinish(LoginAnchorSecondActivity.class);
        String phone = etPhone.getText().toString();
        if (!PhoneUtils.isPhone(phone)) {
            ToastUtil.showShortToast("请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(etPwd.getText())) {
            ToastUtil.showShortToast("请先输入密码");
            return;
        }
        skipActivityByFinish(LoginAnchorSecondActivity.class);
    }
}

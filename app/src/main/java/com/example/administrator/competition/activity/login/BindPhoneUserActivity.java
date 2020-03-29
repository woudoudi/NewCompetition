package com.example.administrator.competition.activity.login;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.competition.HomeActivity;
import com.example.administrator.competition.R;
import com.example.administrator.competition.base.BaseUrlView;
import com.example.http_lib.bean.BindPhoneRequestBean;
import com.example.http_lib.bean.SendCodeRequestBean;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.PhoneUtils;
import com.yidao.module_lib.utils.ToastUtil;
import com.yidao.module_lib.utils.ViewUtils;

import butterknife.BindView;
import butterknife.OnClick;
import jiguang.chat.activity.MainActivity;

public class BindPhoneUserActivity extends BaseUrlView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_vCode)
    EditText etVCode;
    @BindView(R.id.tv_get_vCode)
    TextView tvGetVCode;
    @BindView(R.id.iv_next)
    ImageView ivNext;

    @Override
    protected int getView() {
        return R.layout.activity_bind_phone_user;
    }


    @Override
    public void init() {
        tvTitle.setText("绑定手机号");
    }


    @OnClick({R.id.iv_back, R.id.tv_get_vCode, R.id.iv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                ViewManager.getInstance().finishView();
                break;
            case R.id.tv_get_vCode:
                String phone = etPhone.getText().toString();
                if (!PhoneUtils.isPhone(phone)) {
                    ToastUtil.showShortToast("请输入正确的手机号");
                    return;
                }
                mPresenter.sendVCode(phone);
                ViewUtils.countDownText(tvGetVCode);
                break;
            case R.id.iv_next:
                phone = etPhone.getText().toString();
                String vCode = etVCode.getText().toString();
                if (!PhoneUtils.isPhone(phone)) {
                    ToastUtil.showShortToast("请输入正确的手机号");
                    return;
                }
                if (TextUtils.isEmpty(vCode)) {
                    ToastUtil.showShortToast("请先输入验证码");
                    return;
                }
                mPresenter.bindPhone(phone,vCode);
                break;
        }
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if(success){
            if(requestCls==SendCodeRequestBean.class){
                ToastUtil.showShortToast("验证码下发成功");
            }
            if(requestCls==BindPhoneRequestBean.class){
                ToastUtil.showShortToast("手机号绑定成功");
                ViewManager.getInstance().finishAllView();
                skipActivity(HomeActivity.class);
            }
        } else {
            ToastUtil.showShortToast(responseBean.getRetMsg());
        }
    }
}

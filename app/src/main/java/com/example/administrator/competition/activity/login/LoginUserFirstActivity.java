package com.example.administrator.competition.activity.login;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.administrator.competition.HomeActivity;
import com.example.administrator.competition.R;
import com.example.administrator.competition.application.WXInfo;
import com.example.administrator.competition.base.BaseUrlView;
import com.example.http_lib.bean.LoginRequestBean;
import com.example.http_lib.bean.TripartiteLoginBean;
import com.example.http_lib.response.UserInfoBean;
import com.example.http_lib.utils.UserCacheHelper;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.utils.PhoneUtils;
import com.yidao.module_lib.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginUserFirstActivity extends BaseUrlView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.iv_next)
    ImageView ivNext;
    @BindView(R.id.tv_forget_pwd)
    TextView tvForgetPwd;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_third_tip)
    TextView tvThirdTip;
    @BindView(R.id.iv_back)
    ImageView ivBack;

    @Override
    protected int getView() {
        return R.layout.activity_login_user_first;
    }

    @Override
    public void init() {
        ivBack.setVisibility(View.GONE);
        tvTitle.setText("登录");
        etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (PhoneUtils.isPhone(etPhone.getText().toString()) && !TextUtils.isEmpty(s.toString())) {
                    ivNext.setSelected(true);
                } else {
                    ivNext.setSelected(false);
                }
            }
        });
    }

    @OnClick({R.id.iv_next, R.id.iv_third_login, R.id.tv_forget_pwd, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_next:
                String phone = etPhone.getText().toString();
                String pwd = etPwd.getText().toString();
                if (!PhoneUtils.isPhone(phone)) {
                    ToastUtil.showShortToast("请输入正确的手机号");
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    ToastUtil.showShortToast("请先输入密码");
                    return;
                }
                mPresenter.login(phone, pwd);
                break;
            case R.id.iv_third_login:
                mPresenter.wechatLogin("15757829477");
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "Star_dream";
                WXInfo.getInstance().getApi().sendReq(req);
                break;
            case R.id.tv_forget_pwd:
                skipActivity(UpdateUserActivity.class);
                break;
            case R.id.tv_register:
                skipActivity(RegisterUserActivity.class);
                break;
        }
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if (success) {
            if (requestCls == LoginRequestBean.class) {
                UserInfoBean userInfoBean = new UserInfoBean();
                userInfoBean.setLogin(true);
                userInfoBean.setUserName(etPhone.getText().toString());
                userInfoBean.setPassword(etPwd.getText().toString());
                UserCacheHelper.saveUserInfo(JSON.toJSONString(userInfoBean));
                skipActivityByFinish(HomeActivity.class);
            }
            if(requestCls==TripartiteLoginBean.class){

            }
        } else {
            ToastUtil.showShortToast(responseBean.getRetMsg());
        }
    }
}

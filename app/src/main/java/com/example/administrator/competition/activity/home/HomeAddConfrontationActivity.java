package com.example.administrator.competition.activity.home;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.administrator.competition.R;
import com.example.administrator.competition.base.BaseUrlView;
import com.example.http_lib.bean.SendKingFightRequestBean;
import com.example.http_lib.bean.UserInfoRequestBean;
import com.example.http_lib.response.OverUserInfoBean;
import com.yidao.module_lib.base.BaseView;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.PhoneUtils;
import com.yidao.module_lib.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeAddConfrontationActivity extends BaseUrlView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_first_nick)
    EditText etFirstNick;
    @BindView(R.id.et_first_phone)
    EditText etFirstPhone;
    @BindView(R.id.et_first_number)
    EditText etFirstNumber;
    @BindView(R.id.et_second_nick)
    EditText etSecondNick;
    @BindView(R.id.et_second_phone)
    EditText etSecondPhone;
    @BindView(R.id.et_second_number)
    EditText etSecondNumber;
    @BindView(R.id.tv_send_confrontation)
    TextView tvSendConfrontation;

    @Override
    protected int getView() {
        return R.layout.activity_home_add_confrontation;
    }

    @Override
    public void init() {
        tvTitle.setText("添加邀请");
        setkeyAble();

        mPresenter.getUserInfo();
    }


    @OnClick({R.id.iv_back, R.id.tv_send_confrontation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                ViewManager.getInstance().finishView();
                break;
            case R.id.tv_send_confrontation:
                if(TextUtils.isEmpty(etFirstNick.getText())){
                    ToastUtil.showShortToast("请先输入甲方昵称");
                    return;
                }
                if(!PhoneUtils.isPhone(etFirstPhone.getText().toString())){
                    ToastUtil.showShortToast("请输入甲方正确的手机号");
                    return;
                }
                if(TextUtils.isEmpty(etFirstNumber.getText())){
                    ToastUtil.showShortToast("甲方对抗分数不能为空");
                    return;
                }
                if(TextUtils.isEmpty(etSecondNick.getText())){
                    ToastUtil.showShortToast("请先输入乙方昵称");
                    return;
                }
                if(!PhoneUtils.isPhone(etSecondPhone.getText().toString())){
                    ToastUtil.showShortToast("请输入乙方正确的手机号");
                    return;
                }
                if(TextUtils.isEmpty(etSecondNumber.getText())){
                    ToastUtil.showShortToast("乙方对抗分数不能为空");
                    return;
                }
                SendKingFightRequestBean requestBean = new SendKingFightRequestBean();
                requestBean.nickInviter = etFirstNick.getText().toString();
                requestBean.phoneInviter = etFirstPhone.getText().toString();
                requestBean.nickReceiver = etSecondNick.getText().toString();
                requestBean.phoneReceiver = etSecondPhone.getText().toString();
                requestBean.fscoreA = Double.parseDouble(etFirstNumber.getText().toString());
                requestBean.fscoreB = Double.parseDouble(etSecondNumber.getText().toString());
                mPresenter.startKingFight(requestBean);
                break;
        }
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if(success){
            if(requestCls==SendKingFightRequestBean.class){
                ToastUtil.showShortToast("发送对抗邀请成功");
                onBackPressed();
            }
            if(requestCls==UserInfoRequestBean.class){
                OverUserInfoBean userInfoBean = JSON.parseObject(responseBean.getData(), OverUserInfoBean.class);
                etFirstNick.setText(userInfoBean.getNickname());
                if(PhoneUtils.isPhone(userInfoBean.getUsername())){
                    etFirstPhone.setText(userInfoBean.getUsername());
                }
            }
        } else {
            ToastUtil.showShortToast(responseBean.getRetMsg());
        }
    }
}

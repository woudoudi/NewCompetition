package com.example.administrator.competition.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.competition.R;
import com.example.administrator.competition.base.BaseUrlView;
import com.example.http_lib.bean.UpdateUserInfoRequestBean;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditPersonalNickActivity extends BaseUrlView {


    @BindView(R.id.et_nick)
    EditText etNick;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private String mNickName;

    @Override
    protected int getView() {
        return R.layout.activity_personal_info_nick;
    }

    @Override
    public void init() {
        mNickName = getIntent().getStringExtra(MySettingActivity.NickName);
        etNick.setText(mNickName);
        tvTitle.setText("更改昵称");
        tvRight.setText("保存");
    }


    @OnClick({R.id.iv_back, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                ViewManager.getInstance().finishView();
                break;
            case R.id.tv_right:
                String nick = etNick.getText().toString();
                if (TextUtils.isEmpty(nick)) {
                    ToastUtil.showShortToast("昵称不能为空");
                    return;
                }
                UpdateUserInfoRequestBean requestBean = new UpdateUserInfoRequestBean();
                requestBean.nickname = nick;
                mPresenter.updateUserInfol(requestBean);
                break;
        }
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if (success) {
            if (requestCls == UpdateUserInfoRequestBean.class) {
                ToastUtil.showShortToast("修改成功");
                Intent intent = new Intent();
                intent.putExtra(MySettingActivity.NickName, etNick.getText().toString());
                setResult(RESULT_OK, intent);
                ViewManager.getInstance().finishView();
            }
        } else {
            ToastUtil.showShortToast(responseBean.getRetMsg());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

package com.example.administrator.competition.activity.my;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.competition.R;
import com.example.administrator.competition.base.BaseUrlView;
import com.example.http_lib.bean.GetInviteListRequestBean;
import com.example.http_lib.response.OverUserInfoBean;
import com.example.http_lib.utils.UserCacheHelper;
import com.yidao.module_lib.base.BaseView;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.DensityUtil;
import com.yidao.module_lib.utils.QRCodeUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class MyInviteActivity extends BaseUrlView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_qrCode)
    ImageView ivQrCode;
    @BindView(R.id.tv_invite_code)
    TextView tvInviteCode;
    @BindView(R.id.iv_right)
    ImageView ivRight;

    @Override
    protected int getView() {
        return R.layout.activity_my_invite;
    }

    @Override
    public void init() {
        tvTitle.setText("邀请");
        ivRight.setImageResource(R.mipmap.invite_share);

//        mPresenter.getInviteList();

        OverUserInfoBean overseaUserInfo = UserCacheHelper.getOverseaUserInfo();

        Bitmap bitmap = QRCodeUtil.createQRCodeBitmap("4456", DensityUtil.dip2px(getCtx(),190), DensityUtil.dip2px(getCtx(),190));

        ivQrCode.setImageBitmap(bitmap);
        tvInviteCode.setText("邀请码：4456");
    }


    @OnClick({R.id.iv_back,R.id.iv_right})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                ViewManager.getInstance().finishView();
                break;
            case R.id.iv_right:
                break;
        }
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if(success){
            if(requestCls==GetInviteListRequestBean.class){

            }
        }
    }
}
